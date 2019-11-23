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
                  <v-text-field label="Name" v-model="model.name" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs12>
                  <div class="form-btn">
                    <v-btn outline @click="submit" :disabled="!valid" color="primary">Salvar</v-btn>
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
import Breadcumb from '@/components/Breadcumb';

export default {
  async created () {
  },
  components: {
    VWidget,
    Breadcumb
  },
  data: () => ({
    valid: true,
    rules: Rules,
    showPassword: false
  }),
  watch: {

  },
  methods: {
    clear () {
      this.$refs.form.reset();
    },
    async submit () {
      this.$refs.form.validate() && this.callbackSave(this.model);
    }
  },
  props: {
    title: {
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
