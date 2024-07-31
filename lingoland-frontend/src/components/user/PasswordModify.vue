<script setup>
import GenericInput from "../common/GenericInput.vue";
import SubmitButton from "../common/SubmitButton.vue";
import CancelButton from "../common/CancelButton.vue";
import { useRouter } from "vue-router";
import NameTag from "../common/NameTag.vue";

const router = useRouter();

function modify() {
    console.log("새 비밀번호와 확인 비밀번호가 일치한지 확인 후 변경");
}

function cancel() {
    console.log("비밀번호 변경 취소");
    router.push({ name: "myPageModify" });
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <div>
            <NameTag data="비밀번호 변경" :style="{ width: '220px' }" />

            <v-card width="600" height="400">
                <v-row>
                    <v-col class="ma-10 align-content-space-between">
                        <GenericInput
                            type="password"
                            data="변경할 비밀번호 "
                            id="newPassword"
                            hint="새 비밀번호"
                        />

                        <GenericInput
                            type="password"
                            data="변경할 비밀번호 확인"
                            id="newPasswordCheck"
                            hint="새 비밀번호 확인"
                        />

                        <SubmitButton
                            id="modify"
                            data="확인"
                            width="100%"
                            @click-event="modify"
                        />

                        <CancelButton
                            id="cancel"
                            data="취소"
                            width="100%"
                            @click-event="cancel"
                        />
                    </v-col>
                </v-row>
            </v-card>
        </div>
    </v-main>
</template>

<style scoped></style>
