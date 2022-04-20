import { createApp } from 'vue'
import App from './App.vue'
import router from './router'
import { ElButton, ElContainer, ElAside, ElMain, ElMenu, ElMenuItem, ElMenuItemGroup, ElSubMenu } from 'element-plus'
import 'element-plus/dist/index.css'
const elementPlusComponents = [
    ElButton, ElContainer, ElAside, ElMain, ElMenu, ElMenuItem, ElSubMenu, ElMenuItemGroup
]

const app = createApp(App)
elementPlusComponents.forEach(component => {
    app.component(component.name, component)
})
app.use(router).mount('#app')
