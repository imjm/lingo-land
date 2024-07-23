<script setup>
import imageSource from '@/assets/sampleImg.jpg';
import GenericInput from "../common/GenericInput.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";


function modify() {
    console.log("수정해");
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <v-card width="1200">
            <!-- 하나의 행을 만듬 -->
            <v-row>
                <v-col cols="6" class="d-flex align-center justify-center">
                    <ImageBox :source="imageSource" />
                </v-col>

                <v-col cols="6">
                    <div class="ma-10">
                        <GenericInput type="text" data="이름" id="userName" />

                        <GenericInput type="text" data="아이디" id="userId" />

                        <GenericInput
                            type="password"
                            data="비밀번호 변경"
                            id="userPassword"
                        />
                        <GenericInput
                            type="password"
                            data="비밀번호 확인"
                            id="userPasswordCheck"
                        />

                        <SubmitButton
                            id="signUp"
                            data="수정하기"
                            width="100%"
                            @click-event="modify"
                        />
                    </div>
                </v-col>
            </v-row>
        </v-card>
    </v-main>
</template>

<style scoped></style>
