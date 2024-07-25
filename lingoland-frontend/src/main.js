// import './assets/main.css'
import { createPinia } from "pinia";
import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";

// Vuetify
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import "vuetify/styles";

import { instance } from "@/apis/axios";

const vuetify = createVuetify({
    components,
    directives,
});

const app = createApp(App);

app.provide("axios", instance);
app.use(vuetify);
app.use(createPinia());
app.use(router);

app.mount("#app");
