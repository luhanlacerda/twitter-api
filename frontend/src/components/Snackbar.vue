<template>
  <v-snackbar v-model="active" :timeout="timeout" :color="color" top multi-line center>
    {{ text }}
    <v-btn dark flat @click="active = false">
      Fechar
    </v-btn>
  </v-snackbar>
</template>

<script>
import Events, { EventName } from '@/util/Events';

const Defaults = {
  TIMEOUT: 4000,
  ACTIVE: false,
  Colors: {
    SUCCESS: 'success',
    ERROR: 'error',
    INFO: 'info'
  }
};

export default {
  mounted() {
    const colors = {};
    colors[EventName.SNACK_SUCCESS] = Defaults.Colors.SUCCESS;
    colors[EventName.SNACK_ERROR] = Defaults.Colors.ERROR;
    colors[EventName.SNACK_INFO] = Defaults.Colors.INFO;

    Object.keys(colors).forEach(eventName => {
      Events.$on(eventName, text => {
        this.show(colors[eventName], text);
      });
    });
  },
  data() {
    return {
      active: Defaults.ACTIVE,
      color: Defaults.Colors.SUCCESS,
      timeout: Defaults.TIMEOUT,
      text: ''
    };
  },
  methods: {
    show(color, text) {
      this.active = true;
      this.color = color;
      this.text = text;
    }
  }
};
</script>
