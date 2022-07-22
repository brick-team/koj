<template>
  <a-layout style="min-height: 100vh">
    <a-layout-sider v-model:collapsed="collapsed" collapsible>
      <a-menu v-model:selectedKeys="current" mode="inline">

        <a-menu-item key="3">
          <a href="/#/action">Action</a>
        </a-menu-item>
        <a-menu-item key="4">
          <a href="/#/flow">Flow</a>
        </a-menu-item>
        <a-menu-item key="5">
          <a href="/#/swagger">swagger</a>
        </a-menu-item>

      </a-menu>
    </a-layout-sider>
    <a-layout-content style="margin: 0 16px">
      <router-view/>
    </a-layout-content>
  </a-layout>

</template>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

#nav {
  padding: 30px;
}

#nav a {
  font-weight: bold;
  color: #2c3e50;
}

#nav a.router-link-exact-active {
  color: #42b983;
}
</style>
