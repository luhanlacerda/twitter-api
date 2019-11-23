<template>
  <div>
    <Form title="Editar User" :model="model" :callbackSave="fnCallback"></Form>
  </div>
</template>

<script>
import Form from '@/components/users/Form';
import HttpRequest from '@/api/HttpRequest';
import Events, { EventName } from '@/util/Events';

export default {
  async created () {
    const [err, data] = await HttpRequest.get(`/users/${this.id}`);

    if (err)
      Events.$emit(EventName.SNACK_ERROR, 'Erro ao buscar o usuário');
    else {
      this.model = data.data;
    }
  },
  components: {
    Form
  },
  data: () => ({
    model: {}
  }),
  props: ['id'],
  methods: {
    fnCallback: async function (model) {
      const [err] = await HttpRequest.put(`/users/${this.id}`, model);

      if (err)
        Events.$emit(EventName.SNACK_ERROR, 'Erro ao alterar o usuário');
      else {
        Events.$emit(
          EventName.SNACK_SUCCESS,
          'Usuário alterado com sucesso!'
        );
        this.$router.push('/users');
      }
    }
  }
};
</script>
