<script setup>
import GroupMemberListItem from "./GroupMemberListItem.vue";
import { useMemberStore } from "@/stores/members";
import GenericButton from "@/components/common/GenericButton.vue";
const store = useMemberStore();
import { ref } from "vue";
import GenericInput from "@/components/common/GenericInput.vue"
const dialog = ref(false);
</script>

<template>
  <v-expansion-panels class="pa-4" variant="popout">
    <v-expansion-panel
      v-for="(member, i) in store.members"
      :key="i"
      hide-actions
    >
      <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
      <GroupMemberListItem :member="member" />
    </v-expansion-panel>
  </v-expansion-panels>

  <v-col cols="6">
    <GenericButton
      data="그룹 가입하기"
      @click="
        () => {
          dialog = true;
        }
      "
    />
    <v-dialog  v-model="dialog" width="50%">
      <v-sheet height="500" class="d-flex align-center pa-15">
        <v-row>
          <v-col>
            <v-row >
            <img src="@/assets/sampleImg.jpg" alt="" width="50%">
          </v-row>
          <v-row>
<h1>그룹이름</h1>
        </v-row>
          </v-col>
          <v-col height="300">
            <h3>자기소개</h3>
            <GenericInput/>
            <h3>비밀번호</h3>
            <v-text-field type="password" :id="id" variant="outlined"></v-text-field>

          </v-col>
        </v-row>
      </v-sheet>
      <v-btn
        class="ms-auto"
        text="Ok"
        @click="
          () => {
            dialog = false;
          }
        "
        data="닫기"
      ></v-btn>
    </v-dialog>
  </v-col>
</template>

<style scoped></style>
