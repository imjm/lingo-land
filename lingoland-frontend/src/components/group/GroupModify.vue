<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import imageSource from "@/assets/sampleImg.jpg";

function modifyGroup() {
    console.log("그룹 수정해");
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
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
                        <GenericInput type="text" data="그룹명" id="userId" />

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
    </v-main>
</template>

<style scoped></style>
