<template>
  <v-layout row wrap>
    <v-flex sm12 class="border-table">
      <v-card class="mb-12">
        <v-toolbar flat color="white">
          <v-toolbar-title>
            <Breadcumb/>
          </v-toolbar-title>
          <v-spacer></v-spacer>
        </v-toolbar>
        <v-divider></v-divider>
        <v-card-text class>
          <v-container>
            <v-form ref="form" v-model="valid" lazy-validation>
              <v-layout row>
               <v-flex xs6 mr-4>
                  <v-text-field label="Name" v-model="model.nome" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                 <v-flex xs20>
                  <v-text-field label="Message" v-model="model.message" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs12>
                  <div class="form-btn">
                    <v-btn outline @click="submit" :disabled="!valid" color="primary">Tweetar</v-btn>
                    <v-btn outline @click="clear">Limpar</v-btn>
                  </div>
                </v-flex>
              </v-layout>
            </v-form>
          </v-container>
        </v-card-text>
      </v-card>
    </v-flex>
  </v-layout>
</template>

<script>
import VWidget from '@/components/VWidget';
import Rules from '@/util/Rules';
import HttpRequest from '@/api/HttpRequest';
import Breadcumb from '@/components/Breadcumb';
import Events, { EventName } from '@/util/Events';

export default {
  components: {
    VWidget,
    Breadcumb
  },
  props: {
    title: {
      type: String,
      required: true
    },
    id: {
      type: String,
      required: true
    },
    model: {
      type: Object,
      required: true
    },
    callbackSave: {
      type: Function,
      required: true
    }
  },
  data: () => ({
    valid: true,
    rules: Rules
  }),
  mounted() {
    this.getUser();
  },
  watch: {

  },
  methods: {
    getUser: async function() {
      console.log(this.id);
      const [err, data] = await HttpRequest.get(`/users/${this.id}`);
      if (err) {
        Events.$emit(EventName.SNACK_ERROR, 'Erro ao buscar usuario');
        return;
      }
      console.log(data.data);
      this.model = data.data;
    },
    clear () {
      this.$refs.form.reset();
    },
    async submit () {
      console.log(this.model);
      this.$refs.form.validate() && this.callbackSave(this.model);
    }
  }
};
</script>

<style>
.border-table {
  border-style: dashed;
  background-color: #e7e9ea;
  border-radius: 5px;
  border-color: #e7e9ea;
}
</style>
