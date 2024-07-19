<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";

const imageSource = "src\\assets\\sampleImg.jpg";

function signUp() {
    console.log("회원가입해");
}
</script>

<template>
    <!-- 
        1개에 행
        2개에 열
        1번 열에는 회원가입할 보여줄 이미지 
        2번 열에는 사용자의 정보를 넣어줄 card

    -->
    <v-main class="d-flex align-center justify-center">
        <v-card width="1200">
            <!-- 하나의 행을 만듬 -->
            <v-row>
                <v-col cols="6" class="d-flex align-center justify-center">
                    <ImageBox
                        aspectRatio="1/1"
                        :width="300"
                        :height="300"
                        :source="imageSource"
                        setting="align-center justify-center rounded-circle"
                    />
                </v-col>

                <v-col cols="6">
                    <div class="ma-10">
                        <GenericInput type="text" data="이름" id="userName" />

                        <v-row class="d-flex align-center justify-center">
                            <v-col cols="10">
                                <GenericInput
                                    type="text"
                                    data="아이디"
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
                            data="로그인"
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
