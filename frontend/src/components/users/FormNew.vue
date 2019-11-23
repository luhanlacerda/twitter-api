<template>
  <div>
    <Form title="Novo User" :model="model" :callbackSave="fnCallback"></Form>
  </div>
</template>

<script>
import Form from '@/components/users/Form';
import HttpRequest from '@/api/HttpRequest';
import Events, { EventName } from '@/util/Events';

export default {
  components: {
    Form
  },
  data: () => ({
    model: {}
  }),
  computed: {},
  methods: {
    fnCallback: async function(model) {
      const [err] = await HttpRequest.post('/users', model);
      if (err)
        Events.$emit(
          EventName.SNACK_ERROR,
          'Erro ao cadastrar o usuário'
        );
      else {
        Events.$emit(
          EventName.SNACK_SUCCESS,
          'Usuário cadastrado com sucesso!'
        );
        this.$router.push('/users');
      }
    }
  }
};
</script>
