<script setup>
import { useGroupStore } from "@/stores/groups";
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import imageSource from "@/assets/sampleImg.jpg";
import NameTag from "../common/NameTag.vue";

const groupStore = useGroupStore();
function modifyGroup() {
    groupStore.modifyGroup;
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <div>
            <NameTag data="그룹 수정하기" :style="{width : '220px'}"/>
            <v-card width="1200">
                <!-- 하나의 행을 만듬 -->
                <v-row>
                    <v-col cols="6" class="d-flex align-center justify-center">
                        <ImageBox
                            :source="imageSource"
                            setting="align-center justify-center rounded-circle"
                        />
                    </v-col>

                    <v-col cols="6">
                        <div class="ma-10">
                            <GenericInput
                                type="text"
                                data="그룹명"
                                id="userId"
                            />
                            <!-- :value="value" :readonly="true" -->
                            <GenericInputArea
                                data="그룹 소개"
                                placeholder="그룹 소개를 입력하세요"
                            />

                            <GenericInput
                                type="password"
                                data="비밀번호 변경"
                                id="userPassword"
                            />
                            <GenericInput
                                type="password"
                                data="비밀번호확인"
                                id="userPasswordCheck"
                            />

                            <SubmitButton
                                id="signUp"
                                data="그룹 수정"
                                width="100%"
                                @click-event="modifyGroup"
                            />
                        </div>
                    </v-col>
                </v-row>
            </v-card>
        </div>
    </v-main>
</template>

<style scoped></style>
