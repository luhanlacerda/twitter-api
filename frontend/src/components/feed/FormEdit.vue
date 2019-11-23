<template>
  <div>
    <Form title="Editar Tweet" :model="model" :callbackSave="fnCallback"></Form>
  </div>
</template>

<script>
import Form from '@/components/feed/Form';
import HttpRequest from '@/api/HttpRequest';
import Events, { EventName } from '@/util/Events';

export default {
  async created () {
    const [err, data] = await HttpRequest.get(`/tweeters/${this.id}`);

    if (err)
      Events.$emit(EventName.SNACK_ERROR, 'Erro ao buscar tweet');
    else {
      this.model = data.data;
      this.model = Object.assign(this.model, {
        turmaId: this.model.turma.id
      });
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
      const [err] = await HttpRequest.put(`/tweeters/${this.id}`, model);

      if (err)
        Events.$emit(EventName.SNACK_ERROR, 'Erro ao alterar a tweet');
      else {
        Events.$emit(
          EventName.SNACK_SUCCESS,
          'tweet alterada com sucesso!'
        );
        this.$router.push('/tweeters');
      }
    }
  }
};
</script>
