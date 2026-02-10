import { createApp } from 'vue'
import { createPinia } from 'pinia'

import '@/assets/scss/main.scss'
import App from './App.vue'
import router from './router'
import {i18n} from "@/i18n/i18n.ts";

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.use(i18n)
app.mount('#app')

