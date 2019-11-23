<template>
  <div class="border-table">
    <v-toolbar flat color="white">
      <v-toolbar-title>APPS</v-toolbar-title>
      <v-divider
        class="mx-2"
        inset
        vertical
      ></v-divider>
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
      <v-dialog v-model="dialog" max-width="900px">
        <v-btn slot="activator" color="indigo" dark class="mb-2">New Item</v-btn>
        <v-card>
          <v-card-title>
            <span class="headline">{{ formTitle }}</span>
          </v-card-title>

          <v-card-text>
            <v-container grid-list-md>
              <v-layout row wrap>
                <v-flex xs12 sm6 md4>
                    <v-combobox
                      color="light-blue lighten-2"
                      chips
                      label="Project"
                    ></v-combobox>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.name" label="Name" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.environment" label="Environment" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.host" label="Host" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.notify_ok" label="Notify" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.path" label="Path" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.port" label="Port" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.protocol" label="Protocol" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.message_err_id" label="Message Error" color="light-blue lighten-2"></v-text-field>
                </v-flex>
                <v-flex xs12 sm6 md4>
                  <v-text-field v-model="editedItem.message_ok_id" label="Message Success" color="light-blue lighten-2"></v-text-field>
                </v-flex>

              </v-layout>
            </v-container>
          </v-card-text>

          <v-card-actions>
            <v-spacer></v-spacer>
            <v-btn color="blue darken-1" flat @click.native="close">Cancel</v-btn>
            <v-btn color="blue darken-1" flat @click.native="save">Save</v-btn>
          </v-card-actions>
        </v-card>
      </v-dialog>
    </v-toolbar>
    <v-data-table
      :headers="headers"
      :items="tableData"
      :pagination.sync="pagination"
      hide-actions
      class="elevation-1"
      :search="search"
    >

      <template slot="items" slot-scope="props">
        <td class="text-xs-center">{{ props.item.id }}</td>
        <td class="text-xs-center">{{ props.item.project.name }}</td>
        <td class="text-xs-center">{{ props.item.name }}</td>
        <td class="text-xs-center">{{ props.item.environment }}</td>
        <td class="text-xs-center">{{ props.item.host }}</td>
        <td class="text-xs-center"><v-icon v-bind:color="itemNotifyOk(props.item.notifyOk).color">{{ itemNotifyOk(props.item.notifyOk).icon }}</v-icon></td>
        <td class="text-xs-center">{{ props.item.path }}</td>
        <td class="text-xs-center">{{ props.item.port }}</td>
        <td class="text-xs-center">{{ props.item.protocol }}</td>
        <td class="text-xs-center">{{ props.item.messageErr.message }}</td>
        <td class="text-xs-center">{{ props.item.messageOk.message }}</td>


        <td class="text-xs-center layout px-0">
          <v-icon
            small
            class="mr-2"
            @click="editItem(props.item)"
          >
            edit
          </v-icon>
          <v-icon
            small
            @click="deleteItem(props.item)"
          >
            delete
          </v-icon>
        </td>
      </template>
      <template slot="no-data">
        <v-btn color="indigo" @click="getCustomers">Reset</v-btn>
      </template>
    </v-data-table>
    <div class="text-xs-center pt-2">
      <v-pagination v-model="pagination.page" :length="pages" color="indigo" ></v-pagination>
    </div>
  </div>
</template>

<script>
  import Bus from '../eventBus'

  export default {
    data: () => ({
      tableData: [],
      select_items: ['teste1','teste2'],
      apiUrl: 'http://127.0.0.1:8043/apps',
      dialog: false,
      search: '',
      pagination: {},
      headers: [
        { text: 'Id', value: 'id'},
        { text: 'Project', value: 'project_id'},
        { text: 'Name', value: 'name'},
        { text: 'Environment', value: 'environment'},
        { text: 'Host', value: 'host'},
        { text: 'Notify', value: 'notify_ok'},
        { text: 'Path', value: 'path'},
        { text: 'Port', value: 'port'},
        { text: 'Protocol', value: 'protocol'},
        { text: 'Message Error', value: 'message_err_id'},
        { text: 'Message Success', value: 'message_ok_id'},
        { text: 'Actions', value: 'name'}
      ],
      editedIndex: -1,
      editedItem: {
        project_id: '',
        name: '',
        environment: '',
        host: '',
        notify_ok: '',
        path: '',
        port: '',
        protocol: '',
        message_err_id: '',
        message_ok_id: ''
      },
      defaultItem: {
        id: '',
        project_id: '',
        name: '',
        environment: '',
        host: '',
        notify_ok: '',
        path: '',
        port: '',
        protocol: '',
        message_err_id: '',
        message_ok_id: ''
      }
    }),
   watch: {
      dialog (val) {
        val || this.close()
      }
    },
    mounted () {
        this.getCustomers();
        Bus.$on('filterResultData', (data) => {
            this.tableData = data.results;
             console.log(this.tableData);
        });
    },
    methods: {

      getCustomers: async function() {

        try {
            const response = await this.$axios.get(this.apiUrl);

            //console.log(response.data);
            this.tableData = response.data;
            this.total = response.data;
            this.pagination.totalItems = response.data.length;

        } catch (response) {
            //console.log(response);
        }
      },
      editItem (item) {
        this.editedIndex = this.tableData.indexOf(item)
        this.editedItem = Object.assign({}, item)
        this.dialog = true
      },

      itemNotifyOk (item) {
       if(item){
         return {icon: 'notifications_active', color: 'blue'};
       }else{
         return {icon: 'notifications_off', color: 'red'};
       }
      },

      deleteItem (item) {
        const index = this.tableData.indexOf(item)
        confirm('Are you sure you want to delete this item?') && this.tableData.splice(index, 1)
      },

      close () {
        this.dialog = false
        setTimeout(() => {
          this.editedItem = Object.assign({}, this.defaultItem)
          this.editedIndex = -1
        }, 300)
      },

      save () {
        if (this.editedIndex > -1) {
          Object.assign(this.tableData[this.editedIndex], this.editedItem)
        } else {
          this.tableData.push(this.editedItem)
        }
        this.close()
      }
    },
    computed: {
      formTitle () {
        return this.editedIndex === -1 ? 'New Item' : 'Edit Item'
      }, pages () {
        if (this.pagination.rowsPerPage == null ||
          this.pagination.totalItems == null
        ) return 0
        //console.log(this.pagination.totalItems +" & "+ this.pagination.rowsPerPage)
        return Math.ceil(this.pagination.totalItems / this.pagination.rowsPerPage)
      }
    },
  }
</script>
<style>
.border-table{
    border-style: dashed;
    background-color: #e7e9ea;
    border-radius: 5px;
    border-color: #e7e9ea;
}


</style>


