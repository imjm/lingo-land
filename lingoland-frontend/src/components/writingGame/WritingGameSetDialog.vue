<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import SubmitButton from "../common/SubmitButton.vue";

const model = defineModel();

function setPageCount() {
    model.value.isOpen = false;
}
</script>

<template>
    <v-dialog v-model="model.isOpen" width="850" height="600">
        <v-sheet width="500" height="500" class="d-flex justify-center align-center pa-15">
            <v-row>
                <v-col>
                    <div>몇 페이지 작성할 것인지 써주세요</div>
                    <h3>비밀번호</h3>
                    <GenericInput type="password" />
                    <SubmitButton
                        class="ms-auto d-flex align-left"
                        data="가입하기"
                        width="100%"
                        @click-event="setPageCount()"
                    />
                </v-col>
            </v-row>
        </v-sheet>
    </v-dialog>
</template>

<style scoped></style>
