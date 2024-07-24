<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import imageSource from "@/assets/sampleImg.jpg"


function createGroup() {
    console.log("그룹만들어");
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
                        <v-row class="d-flex align-center justify-center">
                            <v-col cols="10">
                                <GenericInput
                                    type="text"
                                    data="그룹명"
                                    id="userId"
                                />
                            </v-col>
                            <v-col cols="2" class="px-0">
                                <GenericButton
                                    data="중복확인"
                                    id="checkDuplicate"
                                    height="56"
                                />
                            </v-col>
                        </v-row>

                        <GenericInputArea data="그룹 소개" placeholder="그룹 소개를 입력하세요"/>


                        <GenericInput
                            type="password"
                            data="비밀번호"
                            id="userPassword"
                        />
                        <GenericInput
                            type="password"
                            data="비밀번호확인"
                            id="userPasswordCheck"
                        />

                        <SubmitButton
                            id="signUp"
                            data="그룹 생성"
                            width="100%"
                            @click-event="createGroup"
                        />
                    </div>
                </v-col>
            </v-row>
        </v-card>
    </v-main>
</template>

<style scoped></style>
