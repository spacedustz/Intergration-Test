import { createApp } from 'vue'
import { createPinia } from 'pinia'
import 'chartjs-adapter-moment';

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.component('base-dialog');

app.mount('#app')
