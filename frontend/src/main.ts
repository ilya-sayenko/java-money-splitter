import { createApp } from 'vue'
import { createPinia } from 'pinia'

import '@/assets/scss/main.scss'
import App from './App.vue'
import router from './router'
import clickOutside from "@/directives/clickOutside.ts";

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.directive('clickOutside', clickOutside)

app.mount('#app')
