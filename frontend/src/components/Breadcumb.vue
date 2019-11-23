<template>
  <v-breadcrumbs>
    <v-icon slot="divider">chevron_right</v-icon>
    <v-breadcrumbs-item to="/">
      <v-icon larg>home</v-icon>
    </v-breadcrumbs-item>
    <v-breadcrumbs-item v-for="(item,key) in breadcrumbs" :key="key">
      <v-layout row>
        <v-flex mr-2>
          <v-icon larg v-if="item.icon">{{item.icon}}</v-icon>
        </v-flex>
        <v-flex>
          <router-link :to="item.path" exact>{{item.meta.title}}</router-link>
        </v-flex>
      </v-layout>
    </v-breadcrumbs-item>
  </v-breadcrumbs>
</template>

<script>
import { routes } from '@/router';
export default {
  data: () => ({
    breadcrumbs: []
  }),
  mounted() {
    this.breadcrumbs = this.getBreadCrumbsForRoute();
  },
  methods: {
    getBreadCrumbsForRoute() {
      const currentRoute = this.$route;
      const routesWithItems = routes.filter(r => !!r.items);

      const withItem = routesWithItems.find(r =>
        r.items.includes(currentRoute.name)
      );
      const itemMatched = routes.find(r => r.name === currentRoute.name);

      itemMatched.path = currentRoute.path;

      if (withItem) {
        return [withItem, itemMatched];
      }

      return [itemMatched];
    }
  }
};
</script>

<style>
.router-link-active {
  text-decoration: none;
  color: #7b8188;
  font-weight: bold;
}
.v-breadcrumbs li {
  font-size: 16px !important;
}
</style>
