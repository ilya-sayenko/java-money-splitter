import { createApp } from 'vue'
import { createPinia } from 'pinia'

import '@/assets/scss/main.scss'
import App from './App.vue'
import router from './router'
import {useAuthStore} from "@/stores/authStore.ts";
import {ACCESS_TOKEN_KEY} from "@/constants/cookie.ts";
import Cookies from "js-cookie";

const app = createApp(App)

app.use(createPinia())
app.use(router)
app.mount('#app')


// const authStore = useAuthStore();
//
// if (Cookies.get(ACCESS_TOKEN_KEY)) {
//   authStore.loadProfile().finally(() => {
//     app.mount('#app');
//   })
// } else {
//   app.mount('#app')
// }
