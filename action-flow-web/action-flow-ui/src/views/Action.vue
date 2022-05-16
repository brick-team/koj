<template>
  <a-tabs v-model:activeKey="activeKey">
    <a-tab-pane key="1" tab="Rest Api">
      <a-table :columns="columns" :data-source="data">
        <template #name="{ text }">
          <a>{{ text }}</a>
        </template>
        <template #customTitle>
      <span>
        <smile-outlined/>
        Name
      </span>
        </template>
        <template #tags="{ text: tags }">
      <span>
        <a-tag
            v-for="tag in tags"
            :key="tag"
            :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
        >
          {{ tag.toUpperCase() }}
        </a-tag>
      </span>
        </template>
        <template #action="{ record }">
      <span>
        <a>Invite 一 {{ record.name }}</a>
        <a-divider type="vertical"/>
        <a>Delete</a>
        <a-divider type="vertical"/>
        <a class="ant-dropdown-link">
          More actions
          <down-outlined/>
        </a>
      </span>
        </template>
      </a-table>
    </a-tab-pane>
    <a-tab-pane key="2" tab="Java Method" force-render>
      <a-table :columns="columns" :data-source="data">
        <template #name="{ text }">
          <a>{{ text }}</a>
        </template>
        <template #customTitle>
      <span>
        <smile-outlined/>
        Name
      </span>
        </template>
        <template #tags="{ text: tags }">
      <span>
        <a-tag
            v-for="tag in tags"
            :key="tag"
            :color="tag === 'loser' ? 'volcano' : tag.length > 5 ? 'geekblue' : 'green'"
        >
          {{ tag.toUpperCase() }}
        </a-tag>
      </span>
        </template>
        <template #action="{ record }">
      <span>
        <a>Invite 一 {{ record.name }}</a>
        <a-divider type="vertical"/>
        <a>Delete</a>
        <a-divider type="vertical"/>
        <a class="ant-dropdown-link">
          More actions
          <down-outlined/>
        </a>
      </span>
        </template>
      </a-table>
    </a-tab-pane>
  </a-tabs>
</template>

<script>
import {defineComponent, ref} from 'vue';
import {DownOutlined, SmileOutlined} from '@ant-design/icons-vue';

const columns = [
  {
    dataIndex: 'name',
    key: 'name',
    title: "接口名称",
  },
  {
    title: '接口地址',
    dataIndex: 'age',
    key: 'age',
  },

];
const data = [
  {
    key: '1',
    name: 'John Brown',
    age: 32,
    address: 'New York No. 1 Lake Park',
    tags: ['nice', 'developer'],
  },
  {
    key: '2',
    name: 'Jim Green',
    age: 42,
    address: 'London No. 1 Lake Park',
    tags: ['loser'],
  },
  {
    key: '3',
    name: 'Joe Black',
    age: 32,
    address: 'Sidney No. 1 Lake Park',
    tags: ['cool', 'teacher'],
  },
];
export default defineComponent({
  setup() {
    return {
      activeKey: ref('1'),
      data,
      columns
    };

  },
  components: {
    SmileOutlined,
    DownOutlined,
  },
});
</script>

<style scoped>

</style>