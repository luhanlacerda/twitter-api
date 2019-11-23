<template>
  <v-navigation-drawer :clipped="$vuetify.breakpoint.lgAndUp" v-model="drawer" fixed app>
    <v-list dense>
      <template v-for="item in items">
        <v-layout v-if="item.heading" :key="item.heading" row align-center>
          <v-flex xs6>
            <v-subheader v-if="item.heading">{{ item.heading }}</v-subheader>
          </v-flex>
        </v-layout>
        <v-list-group
          @click="redirect(item)"
          v-else-if="item.children"
          v-model="item.model"
          :key="item.text"
          :prepend-icon="item.model ? item.icon : item['icon-alt']"
          append-icon
        >
          <v-list-tile slot="activator">
            <v-list-tile-content>
              <v-list-tile-title>{{ item.text }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
          <v-list-tile v-for="(child, i) in item.children" :key="i">
            <v-list-tile-action v-if="child.icon">
              <v-icon>{{ child.icon }}</v-icon>
            </v-list-tile-action>
            <v-list-tile-content>
              <v-list-tile-title>{{ child.text }}</v-list-tile-title>
            </v-list-tile-content>
          </v-list-tile>
        </v-list-group>
        <v-list-tile @click="redirect(item)" @click.ctrl="openNewTab(item)" v-else :key="item.text">
          <v-list-tile-action>
            <v-icon>{{ item.icon }}</v-icon>
          </v-list-tile-action>
          <v-list-tile-content>
            <v-list-tile-title>{{ item.text }}</v-list-tile-title>
          </v-list-tile-content>
        </v-list-tile>
      </template>
    </v-list>
  </v-navigation-drawer>
</template>

<script>
import Events from '@/util/Events';

export default {
  mounted () {
    Events.$on('updateDrawer', data => {
      this.drawer = !this.drawer;
    });
  },
  data: () => ({
    drawer: null,
    items: [
      { heading: 'Cadastros' },
      { icon: 'storage', text: 'Tweeters', linkTo: '/tweeters' },
      { icon: 'devices', text: 'Users', linkTo: '/users' }
    ]
  }),
  methods: {
    redirect (route) {
      this.$router.push(route.linkTo);
    },
    openNewTab (route) {
      window.open(route.linkTo, '_blank');
    }
  }
};
</script>
