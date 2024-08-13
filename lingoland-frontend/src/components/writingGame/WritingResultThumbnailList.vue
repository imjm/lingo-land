<script setup>
import { ref } from "vue";
import WritingResultList from "./WritingResultList.vue";
import freeImage from "@/assets/free.png";
import router from "@/router";
import { useRoute } from "vue-router";
const route = useRoute();

const drawer = ref(true);
const rail = ref(true);

const tails = ref([
    {
        title: "집에 가고 싶어요",
        cover: "/balloon.png",
    },
]);
</script>

<template>
    <v-layout>
        <v-navigation-drawer
            v-model="drawer"
            :rail="rail"
            permanent
            @click="rail = false"
        >
            <v-list density="compact" nav>
                <v-list-item
                    prepend-avatar="https://randomuser.me/api/portraits/men/85.jpg"
                    title="다른 동화보기"
                    value="home"
                >
                    <template v-slot:append>
                        <v-btn variant="text" @click.stop="rail = !rail">
                            <span
                                style="color: gray"
                                class="material-symbols-outlined"
                            >
                                logout
                            </span>
                        </v-btn>
                    </template>
                </v-list-item>
                <v-list-item
                    v-for="tail in tails"
                    :key="tail"
                    :title="tail.title"
                >
                    <v-img :src="tail.cover" cover></v-img>
                </v-list-item>
            </v-list>
            <button
                @click="
                    () => {
                        router.push({
                            name: 'gameRoom',
                            params: { roomId: route.params.roomId },
                        });
                    }
                "
                style="position: absolute; bottom: 10px; left:15px;"
            >
                <span class="material-symbols-outlined"> door_open </span>
                <!-- <span>방으로</span> -->
            </button>
        </v-navigation-drawer>
        <v-main>
            <WritingResultList />
        </v-main>
    </v-layout>
    <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
    <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@24,400,0,0"
    />
</template>

<style scoped></style>
