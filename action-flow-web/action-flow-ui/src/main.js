import {createApp} from 'vue'
import App from './App.vue'
import router from './router'
import {DatePicker, Button, Popconfirm,Modal} from 'ant-design-vue';
import 'ant-design-vue/dist/antd.css';


const app = createApp(App);
app.use(router).use(DatePicker).use(Button).use(Popconfirm).use(Modal).mount('#app')
