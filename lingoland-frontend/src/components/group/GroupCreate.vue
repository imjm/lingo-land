<script setup>
import { useGroupStore } from "@/stores/groups";
import swal from "sweetalert2";
import { ref } from "vue";
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import imageSource from "@/assets/sampleImg.jpg";

window.Swal = swal;

const groupStore = useGroupStore();
const groupInfo = ref({
    name : "",
    password : "",
    description : "",
});

const checkedPassword = ref("");

const groupNameFormat = ref(false);
const passwordFormat = ref(false);
const nameDuplicate = ref(false);
const passwordCheck = ref(false);

function createGroup() {
    if (
        !groupNameFormat.value &&
        !nameDuplicate.value &&
        !passwordFormat.value &&
        !passwordCheck.value

        
    ) {

        groupStore.createGroup(groupInfo.value);
        console.log(groupNameFormat.value);
        console.log(nameDuplicate.value);
        console.log(passwordFormat.value);
        console.log(passwordCheck.value);
    } else {
        console.log(groupNameFormat.value);
        console.log(nameDuplicate.value);
        console.log(passwordFormat.value);
        console.log(passwordCheck.value);
        Swal.fire({
            title: "그룹 생성 양식을 확인해주세요",
            icon: "error",
        });
    }
}

function checkDuplicate() {
    groupStore.checkDuplicate(groupInfo.value.name).then((response) => {
        if (response) {
            nameDuplicate.value = false;
            Swal.fire({
                title: "중복확인 완료",
                icon: "success",
                confirmButtonText: "완료",
            });
        } else {
            nameDuplicate.value = true;
            Swal.fire({
                title: "중복된 그룹 이름이 있어요",
                icon: "error",
            });
        }
    });
}

function checkPassword(checkPassword, originPassword = groupInfo.value.password) {
    if (originPassword === checkPassword) {
        passwordCheck.value = false;
    } else {
        passwordCheck.value = true;
    }
}

// 비밀번호 포멧체크
function validatePasswordFormat(password) {
    // 비밀번호 최대길이 25

    // 형식이 일치함
    if (password.length <= 25) {
        passwordFormat.value = false;
    } else {
        passwordFormat.value = true;
    }
}

// 닉네임 포맷 체크
function validateGroupNameFormat(groupName) {
    // 닉네임 길이 3- 20 한글,영어,숫자/-/_
    const groupNameRegex = /^[ㄱ-ㅎ가-힣a-z0-9_-]{3,20}$/;

    // 형식이 일치함
    if (groupNameRegex.test(groupName)) {
        groupNameFormat.value = false;
    } else {
        groupNameFormat.value = true;
    }
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
                                    id="name"
                                    v-model="groupInfo.name"
                                    @blur-event="
                                        validateNickNameFormat(groupInfo.name)
                                    "
                                />
                            </v-col>
                            <v-col cols="2" class="px-0">
                                <GenericButton
                                data="중복확인"
                                id="checkDuplicate"
                                height="56"
                                @click-event="checkDuplicate"
                                />
                            </v-col>
                        </v-row>
                        <div v-if="groupNameFormat" class="text-red mb-3">
                            3~20자의 한글, 영문 소문자, 숫자와 특수기호(_),(-)만 사용
                            가능해요.
                        </div>

                        <GenericInputArea
                            data="그룹 소개"
                            v-model="groupInfo.description"
                            placeholder="그룹 소개를 입력하세요"
                        />


                        <GenericInput
                            type="password"
                            data="비밀번호"
                            id="password"
                            v-model="groupInfo.password"
                            @blur-event="
                                validatePasswordFormat(groupInfo.password)
                            "
                        />

                        <div v-if="passwordFormat" class="text-red mb-3">
                            최대 25자만 사용가능해요.
                        </div>

                        <GenericInput
                            type="password"
                            data="비밀번호확인"
                            id="groupPasswordCheck"
                            v-model="checkedPassword"
                            @blur-event="
                                    checkPassword(checkedPassword)
                                "
                            
                        />

                        <div v-if="passwordCheck" class="text-red mb-3">
                            비밀번호가 같지 않아요
                        </div>

                        <SubmitButton
                            id="createGroup"
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
