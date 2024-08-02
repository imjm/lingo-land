// import './assets/main.css'
import { createPinia } from "pinia";
import { createApp } from "vue";

import App from "./App.vue";
import router from "./router";

// Vuetify
import "@mdi/font/css/materialdesignicons.css"; // mdi icon 추가
import "material-design-icons-iconfont/dist/material-design-icons.css";
import { createVuetify } from "vuetify";
import * as components from "vuetify/components";
import * as directives from "vuetify/directives";
import { fa } from "vuetify/iconsets/fa";
import { aliases, mdi } from "vuetify/lib/iconsets/mdi";
import "vuetify/styles";

import { instance } from "@/apis/axios";

const vuetify = createVuetify({
    icons: {
        defaultSet: "mdi",
        aliases,
        sets: {
            fa,
            mdi,
        },
    },
    components,
    directives,

});

const app = createApp(App);

app.provide("axios", instance);
app.use(vuetify);
app.use(createPinia());
app.use(router);

app.mount("#app");
