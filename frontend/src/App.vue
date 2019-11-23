<template>
  <v-app id="app">
    <Header />
    <Sidebar />
    <v-content>
      <v-container fluid>
        <router-view />
      </v-container>
    </v-content>
    <Footer />
  </v-app>
</template>

<script>
import Header from '@/components/Header';
import Sidebar from '@/components/Sidebar';
import Footer from '@/components/Footer';

export default {
  name: 'App',
  data: () => ({}),
  components: {
    Sidebar,
    Header,
    Footer
  },
  methods: {}
};
</script>
