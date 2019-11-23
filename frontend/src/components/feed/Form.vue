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
                  <v-select
                    label="Turmas"
                    :items="turmas"
                    v-model="model.turmaId"
                    :rules="[rules.required]"
                  ></v-select>
                </v-flex>
                 <v-flex xs6>
                  <v-text-field label="Nome" v-model="model.nome" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Idade" v-model="model.idade" :rules="[rules.integer]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    label="Sexo"
                    v-model="model.sexo"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Data de Nacimento" v-model="model.dataNascimento" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field
                    label="Tipo Sanguineo"
                    v-model="model.tipoSanguineo"
                    :rules="[rules.required]"
                  ></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Alergico (Medicamento)" v-model="model.alergicoMedicamento" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Alergico (Alimentação)" v-model="model.alergicoAlimentacao" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Toma Medicação" v-model="model.tomaMedicacao" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Portador de Necessidade" v-model="model.portadorNecessidade" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Direito de Imagem" v-model="model.direitoDeImagem" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Nome do Pai" v-model="model.nomePai" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
                <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Endereço (Pai)" v-model="model.enderecoPai" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="E-Mail (Pai)" v-model="model.emailPai" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Estado Civil (Pai)" v-model="model.estadoCivilPai" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Contato (Pai)" v-model="model.contatoPai" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Pai Membro Da RIO?" v-model="model.paiMembroRio" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Nome da Mãe" v-model="model.nomeMae" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Endereço (Mãe)" v-model="model.enderecoMae" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="Estado Civil (Mãe)" v-model="model.estadoCivilMae" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Contato (Mãe)" v-model="model.contatoMae" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="E-Mail Mãe" v-model="model.emailMae" :rules="[rules.required]"></v-text-field>
                </v-flex>
              </v-layout>
              <v-layout row>
                <v-flex xs6 mr-4>
                  <v-text-field label="Mãe membro da RIO?" v-model="model.maeMembroRio" :rules="[rules.required]"></v-text-field>
                </v-flex>
                <v-flex xs6>
                  <v-text-field label="????" v-model="model.alergicoAlimentacao" :rules="[rules.required]"></v-text-field>
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
import HttpRequest from '@/api/HttpRequest';
import Breadcumb from '@/components/Breadcumb';
import Events, { EventName } from '@/util/Events';

export default {
  async created () {
    this.loadTurmas();
  },
  components: {
    VWidget,
    Breadcumb
  },
  data: () => ({
    valid: true,
    rules: Rules,
    criancas: [],
    turmas: []
  }),
  watch: {

  },
  methods: {
    clear () {
      this.$refs.form.reset();
    },
    async submit () {
      this.$refs.form.validate() && this.callbackSave(this.model);
    },
    async loadTurmas () {
      const [err, data] = await HttpRequest.get('/criancas/turmas');
      if (err) Events.$emit(EventName.SNACK_ERROR, 'Erro ao buscar as turmas');
      else this.turmas = data.data;
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
