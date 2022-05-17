<template>
  <div>

    <!--    <button @click="console_log_data">输出</button>-->

    <span>接口地址= {{ rest_api_return.url }}}</span>
    <span>请求方式= {{ rest_api_return.method }}</span>
    <span>接口描述= {{ rest_api_return.desc }}</span>

    <a-table childrenColumnName="paramEntities" :columns="columns" :data-source="data" row-key="id"
             defaultExpandAllRows="true">
      <template #value="{ text, record ,index}">
        <div class="editable-cell">
          <div class="editable-cell-input-wrapper">
            <a-input :value="record.value" @change="(e) =>change_param_node(text, record ,index,e)"/>
          </div>
        </div>
      </template>
    </a-table>
  </div>
</template>

<script>
import {defineComponent, reactive} from 'vue';

export default defineComponent({

  setup() {
    const columns = [
      {
        title: '参数名称',
        dataIndex: 'name',
        key: 'name',
      },
      {
        title: '数据类型',
        dataIndex: 'type',
        key: 'type',
        width: '12%',
      },
      {
        title: '取值表达式',
        dataIndex: 'value',
        key: 'value',
        width: '12%',
        slots: {
          customRender: 'value',
        },
      },


    ];


    function console_log_data() {
      console.log(data);
    }

    function change_param_node(text, record, index, e) {
      record.value = e.target.value;
      console.log(text, record, index, e.target.value);
    }

    const rest_api_return = {
      "url": "/getAge",
      "method": "get",
      "desc": "getAge",
      "params": [
        {
          "in": "query",
          "name": "uid",
          "require": true,
          "type": "string",
          "paramEntities": [
            {
              "in": "query",
              "name": "id",
              "require": true,
              "type": "string"
            }
          ]
        }
      ],
      "res": [
        {
          "require": false,
          "type": "integer",
          "paramEntities": []
        }
      ],
      "_class": "com.github.brick.apiflow.model.rest.ApiEntity"
    };
    const data = reactive(rest_api_return.params);
    const rowSelection = {
      onChange: (selectedRowKeys, selectedRows) => {
        console.log(`selectedRowKeys: ${selectedRowKeys}`, 'selectedRows: ', selectedRows);
      },
      onSelect: (record, selected, selectedRows) => {
        console.log(record, selected, selectedRows);
      },
      onSelectAll: (selected, selectedRows, changeRows) => {
        console.log(selected, selectedRows, changeRows);
      },
    };
    return {
      data,
      columns,
      rowSelection,
      console_log_data,
      change_param_node,
      rest_api_return,


    };

  },
})


</script>

<style scoped>

</style>