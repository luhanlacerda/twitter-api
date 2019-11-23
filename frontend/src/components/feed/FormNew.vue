<template>
  <div>
    <Form title="Novo Tweet" :model="model" :callbackSave="fnCallback"></Form>
  </div>
</template>

<script>
import Form from '@/components/feed/Form';
import HttpRequest from '@/api/HttpRequest';
import Events, { EventName } from '@/util/Events';

export default {
  components: {
    Form
  },
  props: {
    id: {
      type: String,
      required: true
    }
  },
  data: () => ({
    model: {}
  }),
  computed: {},
  methods: {
    fnCallback: async function(model) {
      const [err] = await HttpRequest.post('/tweeters', model);
      if (err)
        Events.$emit(
          EventName.SNACK_ERROR,
          'Erro ao cadastrar tweet'
        );
      else {
        Events.$emit(
          EventName.SNACK_SUCCESS,
          'Tweet cadastrado com sucesso!'
        );
        this.$router.push('/tweeters');
      }
    }
  }
};
</script>
