<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import Profile from "@/components/common/Profile.vue";
import incorrectDialog from "@/components/incorrect/IncorrectDialog.vue";
import SearchInput from "../common/SearchInput.vue";
import GroupList from "../group/GroupList.vue";
import { useGroupStore } from "@/stores/groups";

import { ref } from "vue";

const groupStore = useGroupStore();

const incorrectList = ref([
    "문제 1",
    "문제 2",
    "문제 3",
    "문제 4",
    "문제 5",
    "문제 6",
    "문제 7",
    "문제 8",
    "문제 9",
    "문제 10",
    // 데이터 받아오기
]);

function getGroupList() {
    groupStore.getGroups();
}
</script>

<template>
    <v-main class="d-flex mt-10 justify-center">
        <v-container>
            <v-row>
                <v-col cols="5" class="d-flex align-center justify-center">
                    <Profile source="src\\assets\\sampleImg.jpg" />
                </v-col>

        <v-col cols="7">
          <v-row>
            <v-sheet
              rounded
              class="ma-3"
              :style="{ backgroundColor: '#CCCBFF' }"
            >
            <v-row class="mt-0 ml-0 d-flex">
              <h1 class="mt-3 ml-4 mr-10">그룹 목록</h1>
              <v-col class="mb-0 pa-0 mt-4 mr-0">
                    <v-autocomplete
                        :items="items"
                        append-inner-icon="mdi-microphone"
                        class="mx-auto"
                        density="comfortable"
                        menu-icon=""
                        placeholder="그룹 이름을 입력하세요."
                        prepend-inner-icon="mdi-magnify"
                        style="max-width: 350px"
                        theme="light"
                        variant="solo"
                        auto-select-first
                        item-props
                        rounded
                    ></v-autocomplete>
                </v-col>
              </v-row>
              <!-- <SearchInput /> -->
              <GroupList />
            </v-sheet>
          </v-row>
          <v-row class="mr-1 d-flex justify-end">
            <GenericButton
              class="mr-3"
              data="그룹 가입하기"
              :style="{ backgroundColor: '#4C4637', color: 'white' }"
            />
            <GenericButton
              data="그룹 만들기"
              :style="{ backgroundColor: '#4C4637', color: 'white' }"
            />
          </v-row>
          <v-row>
            <v-col cols="6">
                <incorrectDialog :incorrects="incorrectList"/>
     
            </v-col>
            <v-col cols="6">
              <PageNavigationButton
                background-color="#537960"
                data="ㅇㅇㅇ의 동화"
                source="src\assets\내동화.png"
              />
            </v-col>
          </v-row>
        </v-col>
      </v-row>
    </v-container>
  </v-main>
</template>

<style scoped>
.room-code {
    background-color: #d2f0ff;
    width: 100%;
    height: 300px;
    border-radius: 4px;
    font-size: x-large;
}
</style>
