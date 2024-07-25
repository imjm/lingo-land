<script setup>
import { useUserStore } from "@/stores/user";
import swal from "sweetalert2";
import { ref } from "vue";
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";

const imageSource = "src\\assets\\sampleImg.jpg";

window.Swal = swal;

const userStore = useUserStore();

const userInfo = ref({
    loginId: "",
    password: "",
    nickname: "",
});

const passwordCheck = ref("");

// ID 길이 3 - 20, 영어와 숫자만
// 닉네임 길이 3- 20 한글,영어,숫자/-/_ 
// 비밀번호 최대 25

function signUp() {
    // 비밀번호와 비밀번호 확인 일치한지 확인 후
    // 회원가입 실행
    if (userStore.checkPassword(userInfo.password.value, passwordCheck.value)) {
        userStore.signUp(userInfo.value);
    } else {
        Swal.fire({
            title: "비밀번호가 일치하지 않습니다.",
            icon: "error",
        });
    }
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
                        <GenericInput
                            v-model="userInfo.nickname"
                            type="text"
                            data="닉네임"
                            id="userName"
                        />

                        <v-row class="d-flex align-center justify-center">
                            <v-col cols="10">
                                <GenericInput
                                    v-model="userInfo.loginId"
                                    type="text"
                                    data="아이디"
                                    id="loginId"
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

                        <GenericInput
                            v-model="userInfo.password"
                            type="password"
                            data="비밀번호"
                            id="password"
                        />
                        <GenericInput
                            v-model="passwordCheck"
                            type="password"
                            data="비밀번호확인"
                            id="passwordCheck"
                        />

                        <SubmitButton
                            id="signUp"
                            data="회원가입"
                            width="100%"
                            @click-event="signUp"
                        />
                    </div>
                </v-col>
            </v-row>
        </v-card>
    </v-main>
</template>

<style scoped></style>
