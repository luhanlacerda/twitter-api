<template>
  <div>
    <v-toolbar :clipped-left="$vuetify.breakpoint.lgAndUp" color="teal lighten-2" dark app fixed>
      <v-toolbar-title style="width: 300px" class="ml-0 pl-3">
        <v-toolbar-side-icon v-on:click.stop="emitChange" class="toolbar-side-icon"></v-toolbar-side-icon>
        <!-- <img src="./../assets/img/teste.png" class="imgs-header" /> -->
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-btn icon @click="handleFullScreen()">
        <v-icon>fullscreen</v-icon>
      </v-btn>
      <v-btn icon>
        <v-icon>notifications</v-icon>
      </v-btn>
    </v-toolbar>
    <Snackbar />
  </div>
</template>

<script>
import Util from './../util';
import Events from './../util/Events';
import Snackbar from '@/components/Snackbar';

export default {
  components: { Snackbar },
  methods: {
    handleFullScreen() {
      Util.toggleFullScreen();
    },
    emitChange() {
      Events.$emit('updateDrawer');
    }
  }
};
</script>

<style>
.imgs-header {
  width: 50px;
  margin-left: 26px;
  margin-top: 10px;
}

.toolbar-side-icon {
  margin-top: -30px !important;
}
</style>

