<template>
  <div>
    <el-container>
      <el-aside width="200px">
        <el-menu router :default-active="$route.path">
          <template v-for="m,index in menus" :key="index" :index="m.path">
            <el-menu-item
              :index="m.path"
            >
              <span>{{m.name}}</span>
            </el-menu-item>
          </template>
        </el-menu>
      </el-aside>
        <router-view></router-view>
    </el-container>
  </div>
</template>

<script>

export default {
  data() {
    return {
      menus: [
        {name: '菜单1', path: '1'},
        {name: '菜单2', path: '2'},
        {name: '菜单3', path: '3'},
      ]
    }
  }
}
</script>
