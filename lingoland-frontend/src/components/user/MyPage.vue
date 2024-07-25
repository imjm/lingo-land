<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import Profile from "@/components/common/Profile.vue";
import GroupList from "../group/GroupList.vue";
import SearchInput from "../common/SearchInput.vue";
import IncorrectList from "@/components/incorrect/IncorrectList.vue";
import incorrectDialog from "@/components/incorrect/IncorrectDialog.vue";

import { ref } from "vue";

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
</script>

<template>
    <v-main class="d-flex justify-center">
        <v-container>
            <h1>MainPage View</h1>

            <v-row>
                <v-col cols="5">
                    <Profile source="src\\assets\\sampleImg.jpg" />
                </v-col>

        <v-col cols="7">
          <v-row>
            <v-sheet
              rounded
              class="ma-3"
              :style="{ backgroundColor: '#CCCBFF' }"
            >
              <h1 class="mx-10">그룹 목록</h1>
              <SearchInput />
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
