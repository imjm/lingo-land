<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import SubmitButton from "../common/SubmitButton.vue";
import { ref } from "vue";

const model = defineModel();

const props = defineProps({
    groupInfo: Object,
});
</script>

<template>
    <v-dialog v-model="model" width="850" height="600">
        <v-sheet width="850" height="600" class="d-flex align-center pa-15">
            <v-row>
                <v-col>
                    <v-row>
                        <img src="@/assets/sampleImg.jpg" alt="" width="100%" />
                    </v-row>
                    <v-row>
                        <h1>그룹이름</h1>
                    </v-row>
                </v-col>
                <v-col>
                    <h3>자기소개</h3>
                    <GenericInputArea />
                    <h3>비밀번호</h3>
                    <GenericInput type="password" />
                    <SubmitButton
                        class="ms-auto d-flex align-left"
                        data="가입하기"
                        width="100%"
                        @click-event="
                            () => {
                                model = false;
                            }
                        "
                    />
                    <GenericButton
                        class="mt-4 ms-auto d-flex align-left"
                        data="취소"
                        width="100%"
                        @click-event="
                            () => {
                                model = false;
                            }
                        "
                    />
                </v-col>
            </v-row>
        </v-sheet>
    </v-dialog>
</template>

<style scoped></style>
