<script setup>
import { onMounted, ref, defineProps } from "vue";
import TaleListItem from "./Not-use-TaleListItem.vue";
import { useTaleStore } from "@/stores/tales";
import { useRoute } from "vue-router";
const route = useRoute()
const taleStore = useTaleStore();
const items = ref([]);

const search = ref("");


const props = defineProps({
    others : Boolean,
    id : String,

})

const headers = ref([
    { title: "번호", value: "id" },
    { title: "제목", value: "title" },
    { title: "Thumbnail", value: "cover" },
    { title: "한 줄 소개", value: "summary" },
    // 필요에 따라 추가 컬럼 정의
]);

const rowClick = (event, { item }) => {
    console.log("나는 눌리긴 하는중!@!@><");
    // console.log();
    taleStore.oneTaleById(item.id);
};


onMounted(() => {
    let talesListPromise;
    if(props.others) {
        console.log('다른 유저 동화')
        talesListPromise=taleStore.otherTalesList(props.id);
        
    }else if(route.params.memberId) {
        console.log('다른 유저 동화')
        talesListPromise=taleStore.otherTalesList(route.params.memberId);
    }else {
        console.log('내 동화')
        talesListPromise=taleStore.myTalesList();
    }

    talesListPromise.then((promiseValue) => {
        items.value=promiseValue;
    })

});
</script>

<template>
    <!-- <button @click="rowClick">asdfasdf</button> -->
    <v-card
        flat
        class="gowun-batang-regular mx-5 px-5"
        :style="{ width: '80%' }"
    >
        <v-card-title class="d-flex align-center pa-3">
            <span class="material-icons"> menu_book </span>
            <span style="font-size: xx-large; font-weight: bold">
                &nbsp; 동화 목록</span
            >

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
        </v-data-table>
    </v-card>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Gowun+Batang&display=swap");
.gowun-batang-regular {
    font-family: "Gowun Batang", serif;
    font-weight: 500;
    font-style: normal;
    font-size: large;
}

thead span {
    font-weight: bold;
    font-size: large;
}
/* width */
::-webkit-scrollbar {
    width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
    box-shadow: inset 0 0 5px grey;
    border-radius: 10px;
}

/* Handle */
::-webkit-scrollbar-thumb {
    background: rgb(252, 194, 85);
    border-radius: 10px;
}
</style>
