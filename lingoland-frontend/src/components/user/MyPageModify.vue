<script setup>
import imageSource from "@/assets/sampleImg.jpg";
import GenericInput from "../common/GenericInput.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import GenericButton from "../common/GenericButton.vue";
import { useRouter } from "vue-router";
import NameTag from "../common/NameTag.vue";

const router = useRouter();

function modify() {
    console.log("현재 비밀번호가 맞는지 확인 후에 비밀번호 변경 페이지로 이동");
    router.push({ name: "myPageModifyPassword" });
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <div>
            <NameTag data="마이페이지 수정" :style="{width:'250px'}" />
            <v-card
                width="1200"
                height="500"
                class="d-flex align-center justify-center"
            >
                <v-row>
                    <v-col cols="6" class="d-flex align-center justify-center">
                        <ImageBox :source="imageSource" />
                    </v-col>

                    <v-col cols="6">
                        <div class="ma-10">
                            <v-row class="d-flex align-center justify-center">
                                <v-col cols="10">
                                    <GenericInput
                                        type="text"
                                        data="닉네임"
                                        id="userId"
                                        hint="변경할 닉네임을 입력하세요"
                                    />
                                </v-col>
                                <v-col cols="2" class="px-0">
                                    <GenericButton
                                        data="변경하기"
                                        id="modifyUserNickName"
                                        height="56"
                                    />
                                </v-col>
                            </v-row>

                            <GenericInput
                                type="password"
                                data="현재 비밀번호 "
                                id="userPassword"
                                hint="현재 비밀번호를 입력하세요"
                            />

                            <SubmitButton
                                id="signUp"
                                data="비밀번호 변경하러 가기"
                                width="100%"
                                @click-event="modify"
                            />
                        </div>
                    </v-col>
                </v-row>
            </v-card>
        </div>
    </v-main>
</template>

<style scoped></style>
