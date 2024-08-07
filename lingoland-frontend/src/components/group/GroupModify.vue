<script setup>
import { useGroupStore } from "@/stores/groups";
import { storeToRefs } from "pinia";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import NameTag from "../common/NameTag.vue";
import SubmitButton from "../common/SubmitButton.vue";

const route = useRoute();
const groupStore = useGroupStore();

const { passwordFormat, passwordCheck, groupDiscriptionFormat } =
    storeToRefs(groupStore);

//보낼 DTO
const updateGroup = ref(null);

let imageSource = ref();
function handleFileChange(event) {
    const file = event.target.files[0];
    // console.log('file', file)
    if (file) {
        updateGroup.value.groupImage = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            imageSource.value = e.target.result;
        };
        reader.readAsDataURL(file);
        console.log("Selected file:", file);
    }
}

function modifyGroup() {
    groupStore.modifyGroup(updateGroup.value, updateGroup.value.groupImage);
}

onMounted(() => {
    groupStore.getGroup(route.params.groupId).then((responseValue) => {
        updateGroup.value = responseValue;
        console.log(updateGroup.value);
    });
});
</script>

<template>
    <v-main v-if="updateGroup" class="d-flex align-center justify-center">
        <br />
        <div>
            <NameTag data="그룹 수정하기" :style="{ width: '220px' }" />
            <v-card width="1200">
                <!-- 하나의 행을 만듬 -->
                <v-row>
                    <v-col
                        cols="6"
                        class="d-flex flex-column align-center justify-center"
                    >
                        <ImageBox
                            :source="imageSource"
                            setting="align-center justify-center rounded-circle"
                        />
                        <v-file-input
                            prepend-icon="mdi-camera"
                            hide-input
                            @change="handleFileChange"
                        ></v-file-input>
                    </v-col>

                    <v-col cols="6">
                        <div class="ma-10">
                            <div>그룹 명</div>
                            <v-text-field
                                variant="outlined"
                                :placeholder="updateGroup.name"
                                :readonly="true"
                            ></v-text-field>

                            <GenericInputArea
                                data="그룹 소개"
                                placeholder="그룹 소개를 입력하세요"
                                v-model="updateGroup.description"
                                @blur-event="
                                    groupStore.validateGroupDiscriptionFormat(
                                        updateGroup.description
                                    )
                                "
                            />

                            <div
                                v-if="groupDiscriptionFormat"
                                class="text-red mb-3"
                            >
                                그룹 소개는 200자 이내로 작성 가능해요
                            </div>

                            <GenericInput
                                type="password"
                                data="비밀번호 변경"
                                id="groupPassword"
                                hint="4자리 숫자를 입력해주세요"
                                v-model="updateGroup.password"
                                @blur-event="
                                    groupStore.validatePasswordFormat(
                                        updateGroup.password
                                    )
                                "
                            />

                            <div v-if="passwordFormat" class="text-red mb-3">
                                숫자 4자리만 사용가능해요.
                            </div>

                            <GenericInput
                                type="password"
                                data="비밀번호확인"
                                id="groupPasswordCheck"
                                v-model="updateGroup.checkedPassword"
                                @blur-event="
                                    groupStore.checkPassword(
                                        updateGroup.checkedPassword,
                                        updateGroup.password
                                    )
                                "
                            />

                            <div v-if="passwordCheck" class="text-red mb-3">
                                비밀번호가 같지 않아요
                            </div>

                            <SubmitButton
                                id="signUp"
                                data="그룹 수정"
                                width="100%"
                                @click-event="modifyGroup"
                            />
                        </div>
                    </v-col>
                </v-row>
            </v-card>
        </div>
    </v-main>
</template>

<style scoped></style>
