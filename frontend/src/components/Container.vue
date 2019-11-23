<template>
 <v-content>
      <v-container fluid fill-height>
        <v-layout justify-center align-center>
            <Table></Table>
        </v-layout>
      </v-container>
    </v-content>
    
</template>

<script>

 import Table  from './Table.vue'


export default {
     name: 'Container',
     components: {
            Table
     }
}
</script>
<style>
.layout {
  display: inline-block;
  width: 100%;
}
</style>

