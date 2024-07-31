<script setup>
import { ref } from "vue";
import TaleListItem from "./Not-use-TaleListItem.vue";
import { useTaleStore } from "@/stores/tales";

const taleStore = useTaleStore();
const items = taleStore.tales;

const search = ref("");
const headers = ref([
    { title: "No.", value: "id" },
    { title: "제목", value: "title" },
    { title: "Thumbnail", value: "cover" },
    { title: "한 줄 소개", value: "summary" },
    // 필요에 따라 추가 컬럼 정의
]);

const rowClick = (event,{item}) => {
    console.log("나는 눌리긴 하는중!@!@><");
    // console.log();
    taleStore.oneTaleById(item.id);
};
</script>

<template>
    <!-- <button @click="rowClick">asdfasdf</button> -->
    <v-card flat class="gowun-batang-regular">
        <v-card-title class="d-flex align-center pe-2">
            <v-icon icon="mdi-video-input-component"></v-icon> &nbsp; 동화 목록

            <v-spacer></v-spacer>
            <!-- 검색 -->
            <v-text-field
                v-model="search"
                density="compact"
                label="제목을 입력해 주세요"
                prepend-inner-icon="mdi-magnify"
                variant="solo-filled"
                flat
                hide-details
                single-line
            ></v-text-field>
        </v-card-title>

        <v-divider></v-divider>

        <!-- 데이터 내용 -->
        <v-data-table
            v-model:search="search"
            :headers="headers"
            :items="items"
            @click:row="rowClick"
            items-per-page="6"
        >
            <template v-slot:item.cover="{ item }">
                <v-img
                    class="my-2"
                    :src="item.cover"
                    height="100px"
                    max-width="100px"
                    width="100%"
                    contain
                ></v-img>
            </template>

            <!-- <template v-slot:item="{ item }">
                <tr @click="goToDetail(item)">
                    <td v-for="val in item">{{ val }}</td>
                </tr>
            </template> -->
        </v-data-table>
    </v-card>
</template>

<style >

@import url('https://fonts.googleapis.com/css2?family=Gowun+Batang&display=swap');
.gowun-batang-regular {
  font-family: "Gowun Batang", serif;
  font-weight: 500;
  font-style: normal;
  font-size : large;
}
</style>
