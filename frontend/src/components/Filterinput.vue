<template>
    <el-form :inline="true" :model="formInline">

        <el-form-item label="Protocol">
            <el-select v-model="formInline.protocol" clearable placeholder="select protocol"
                       v-on:visible-change="selectDemo">
                <el-option
                        v-for="(item, index) in type_options"
                        :key = "index"
                        :label="item.label"
                        :value="item.value">
                </el-option>
            </el-select>
        </el-form-item>

        <el-form-item v-if='formInline.protocol' label="Description">
            <el-input v-model="formInline.nome" placeholder="Please input suffix of nome"></el-input>
        </el-form-item>

        <el-form-item v-else='formInline.protocol' label="Description">
            <el-input v-model="formInline.nome" disabled placeholder="Please input suffix of nome"></el-input>
        </el-form-item>

    </el-form>
</template>

<script>
    import lodash from 'lodash'
    import Bus from '../eventBus'

    export default {
        name: 'Filterinput',
        data() {
            return {
                type_options: [],
                formInline: {
                    protocol: '',
                    nome: ''
                },
                formLabelWidth: '120px'
            }
        },

        watch: {
            'formInline.protocol': 'filterResultData',
            'formInline.nome': 'filterResultData'
        },

        methods: {
            selectDemo: function (params) {
                if (params) {
                    this.$axios.get("http://127.0.0.1:8043/api/persons/sex")
                        .then((response) => {
                            this.type_options = response.data;
                            console.log(response.data);
                        }).catch(function (response) {
                        console.log(response)
                    });
                }

            },
            filterResultData: _.debounce(
                function () {
                    this.$axios.get("http://127.0.0.1:8043/api/persons", {
                        params: {
                            sex: this.formInline.protocol,
                            nome: this.formInline.nome,
                        }
                    }).then((response) => {
                        response.data['protocol'] = this.formInline.protocol;
                        response.data['nome'] = this.formInline.nome;
                        Bus.$emit('filterResultData', response.data);
                        console.log(response.data);
                    }).catch(function (response) {
                        console.log(response)
                    });

                },
                500
            ),
        }
    }


</script>
