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
    checkedPassword: "",
    nickname: "",
});

const IdDuplicate = ref(false);
const IdFormat = ref(false);
const nicknameFormat = ref(false);
const passwordFormat = ref(false);

// 아이디 포맷 체크
function validateIDFormat(loginId) {
    // ID 길이 3 - 20, 영어와 숫자만
    const idRegex = /^[a-zA-Z0-9]{3,20}$/;

    // 형식이 일치함
    if (idRegex.test(loginId)) {
        IdFormat.value = false;
    } else {
        IdFormat.value = true;
    }
}

// 닉네임 포맷 체크
function validateNickNameFormat(nickname) {
    // 닉네임 길이 3- 20 한글,영어,숫자/-/_
    const nicknameRegex = /^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$/;

    // 형식이 일치함
    if (nicknameRegex.test(nickname)) {
        nicknameFormat.value = false;
    } else {
        nicknameFormat.value = true;
    }
}

// 비밀번호 포멧체크
function validatePasswordFormat(password) {
    // 비밀번호 최대길이 25

    const passwordRegex = /^[ㄱ-ㅎ가-힣a-z0-9_-]{6,20}$/;

    // 형식이 일치함
    if (passwordRegex.test(password)) {
        passwordFormat.value = false;
    } else {
        passwordFormat.value = true;
    }
}

// 아이디 중복 확인
function checkIdDuplicate() {
    userStore.checkDuplicate(userInfo.value.loginId).then((response) => {
        if (response) {
            IdDuplicate.value = true;
            Swal.fire({
                title: "중복확인 완료",
                icon: "success",
                confirmButtonText: "완료",
            });
        } else {
            IdDuplicate.value = false;
            Swal.fire({
                title: "중복된 아이디가 있어요",
                icon: "error",
            });
        }
    });
}

function signUp() {
    // 회원가입 실행
    if (
        !IdFormat.value &&
        IdDuplicate.value &&
        !passwordFormat.value &&
        !nicknameFormat.value
    ) {
        userStore.signUp(userInfo.value);
    } else {
        Swal.fire({
            title: "회원가입 양식을 확인해주세요",
            icon: "error",
        });
    }
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <div>
            <SubmitButton
                data="회원가입"
            />
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
                                @blur-event="
                                    validateNickNameFormat(userInfo.nickname)
                                "
                            />
                            <div v-if="nicknameFormat" class="text-red mb-3">
                                3~20자의 한글, 영문 소문자, 숫자와
                                특수기호(_),(-)만 사용 가능해요.
                            </div>

                            <v-row class="d-flex align-center justify-center">
                                <v-col cols="10">
                                    <GenericInput
                                        v-model="userInfo.loginId"
                                        type="text"
                                        data="아이디"
                                        id="loginId"
                                        @blur-event="
                                            validateIDFormat(userInfo.loginId)
                                        "
                                    />
                                </v-col>
                                <v-col cols="2" class="px-0">
                                    <GenericButton
                                        data="중복확인"
                                        id="checkDuplicate"
                                        height="56"
                                        @click-event="checkIdDuplicate"
                                    />
                                </v-col>
                            </v-row>

                            <div v-if="IdFormat" class="text-red mb-3">
                                3~20자의 영문 소문자, 숫자와 특수기호(_),(-)만
                                사용 가능해요.
                            </div>

                            <GenericInput
                                v-model="userInfo.password"
                                type="password"
                                data="비밀번호"
                                id="password"
                                @blur-event="
                                    validatePasswordFormat(userInfo.password)
                                "
                            />

                            <div v-if="passwordFormat" class="text-red mb-3">
                                6~20자의 영문 소문자, 숫자와 특수기호(_),(-)만
                                사용 가능해요.
                            </div>

                            <GenericInput
                                v-model="userInfo.checkedPassword"
                                type="password"
                                data="비밀번호확인"
                                id="checkedPassword"
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
        </div>
    </v-main>
</template>

<style scoped></style>
