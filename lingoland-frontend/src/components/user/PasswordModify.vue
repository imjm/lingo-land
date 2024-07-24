<script setup>
import GenericInput from "../common/GenericInput.vue";
import SubmitButton from "../common/SubmitButton.vue";
import CancelButton from "../common/CancelButton.vue";

function modify() {
    console.log("새 비밀번호와 확인 비밀번호가 일치한지 확인 후 변경");
}

function cancel() {
    console.log("비밀번호 변경 취소");
}
</script>

<template>
    <v-main class="d-flex align-center justify-center">
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
    </v-main>
</template>

<style scoped></style>
