// import './assets/main.css'
import Vuetify from 'vuetify'

import { createApp } from 'vue'
import { createPinia } from 'pinia'

import App from './App.vue'
import router from './router'

const app = createApp(App)

app.use(Vuetify)
app.use(createPinia())
app.use(router)

app.mount('#app')
