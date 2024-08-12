<script setup>
import { ref } from "vue";
import WritingResultList from "./WritingResultList.vue";
import freeImage from "@/assets/free.png";

const drawer = ref(true);
const rail = ref(true);

</script>

<template>
    <v-layout>
        <v-navigation-drawer v-model="drawer" :rail="rail" permanent @click="rail = false">
            <v-list density="compact" nav>
                <v-list-item prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg" title="다른 동화보기"
                    value="home">
                    <template v-slot:append>
                        <v-btn variant="text" @click.stop="rail = !rail"><span class="material-symbols-outlined">
                                logout
                            </span></v-btn>
                    </template>
                </v-list-item>

                <v-list-item title="My Account" value="account">
                    <v-avatar rounded="0">
                        <v-img :src="freeImage" cover></v-img>
                    </v-avatar>
                </v-list-item>

                <v-list-item title="My Account" value="account">
                    <v-avatar rounded="0">
                        <v-img :src="freeImage" cover></v-img>
                    </v-avatar>
                </v-list-item>

                <v-list-item title="My Account" value="account">
                    <v-avatar rounded="0">
                        <v-img :src="freeImage" cover></v-img>
                    </v-avatar>
                </v-list-item>
            </v-list>
        </v-navigation-drawer>
        <v-main>
            <WritingResultList />
        </v-main>
    </v-layout>

    <link rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0" />
</template>

<style scoped></style>
