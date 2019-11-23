<template>
  <div class="border-table">
    <v-toolbar flat color="white">
      <v-toolbar-title>
        <Breadcumb/>
      </v-toolbar-title>
      <v-spacer></v-spacer>
      <v-text-field
        color="indigo"
        v-model="search"
        append-icon="search"
        label="Search"
        single-line
        hide-details
      ></v-text-field>
      <v-spacer></v-spacer>
      <v-btn color="indigo" dark class="mb-2" @click="newItem('/users/new')">Novo</v-btn>
    </v-toolbar>

    <v-divider></v-divider>

    <v-data-table
      :headers="headers"
      :items="tableData"
      :pagination.sync="pagination"
      hide-actions
      class="elevation-1"
      :search="search"
    >
      <template slot="items" slot-scope="props">
        <td
          class="text-xs-left"
          @mouseover="showInfoDoubleClick = true"
          @mouseout="showInfoDoubleClick = false"
          @dblclick="viewFeed(props.item)"
        >{{ props.item.id }}</td>
        <td
          class="text-xs-left"
          @mouseover="showInfoDoubleClick = true"
          @mouseout="showInfoDoubleClick = false"
          @dblclick="viewFeed(props.item)"
        >{{ props.item.name }}</td>

      </template>
    </v-data-table>
    <v-layout row justify-space-between mt-2>
      <v-flex xs4 pl-2 pt-1>
        <div v-if="showInfoDoubleClick">
          <v-icon>info</v-icon>
          <span pb-4>Dê duplo clique na linha para visualizar o feed.</span>
        </div>
      </v-flex>
      <v-flex xs4 pl-2 pt-1 text-md-center>
        <v-pagination v-model="pagination.page" :length="pages" color="indigo">asasdasdd</v-pagination>
      </v-flex>
      <v-flex xs4></v-flex>
    </v-layout>
  </div>
</template>

<script>
import HttpRequest from '@/api/HttpRequest';
import Breadcumb from '@/components/Breadcumb';
import Events, { EventName } from '@/util/Events';

export default {
  components: { Breadcumb },
  data: () => ({
    title: 'Users',
    tableData: [],
    search: '',
    showInfoDoubleClick: false,
    pagination: {
      rowsPerPage: 10
    },
    headers: [
      { text: 'Id', value: 'id' },
      { text: 'Name', value: 'name' }
    ]
  }),
  mounted () {
    this.getAll();
  },
  methods: {
    getAll: async function () {
      const [err, data] = await HttpRequest.get('/users');
      if (err) {
        Events.$emit(EventName.SNACK_ERROR, 'Erro ao buscar os usuários');
        return;
      }
      this.tableData = data.data;
      this.pagination.totalItems = data.data.length;
    },
    viewFeed (item) {
      HttpRequest.get(`/tweeters/feed/${item.id}`);
    },
    newItem (path) {
      this.$router.push(path);
    }
  },
  filters: {
  },
  computed: {
    pages () {
      if (
        this.pagination.rowsPerPage == null ||
        this.pagination.totalItems == null
      ) {
        return 0;
      }
      return Math.ceil(
        this.pagination.totalItems / this.pagination.rowsPerPage
      );
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
