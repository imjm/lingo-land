<script setup>
import { useGroupStore } from "@/stores/groups";
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import GenericInputArea from "../common/GenericInputArea.vue";
import ImageBox from "../common/ImageBox.vue";
import SubmitButton from "../common/SubmitButton.vue";
import NameTag from "../common/NameTag.vue";
import { onMounted, ref } from "vue";
import { storeToRefs } from "pinia";

const groupStore = useGroupStore();
const { myGroup } = storeToRefs(groupStore);

//보낼 DTO
const groupImage = ref(); // 기본은 원래 적용되어 있는 것으로
const updateGroup = ref({
    id:0,
    name:'',
    password:'',
    description:''
});

let imageSource = ref();
function handleFileChange(event) {
    const file = event.target.files[0];
    // console.log('file', file)
    if (file) {
        groupImage.value = file;
        const reader = new FileReader();
        reader.onload = (e) => {
            imageSource.value = e.target.result;
        };
        reader.readAsDataURL(file);
        console.log("Selected file:", file);
    }
}

function modifyGroup() {
    updateGroup.value.id=groupStore.myGroup.value.id
    updateGroup.value.name=groupStore.myGroup.value.name
    updateGroup.value.password=groupStore.myGroup.value.password
    updateGroup.value.description=groupStore.myGroup.value.description  
    groupStore.modifyGroup(updateGroup.value, groupImage.value);
    
}

console.log(groupStore.myGroup.value.name);

onMounted(() => {
    console.log(groupStore.myGroup.value);
});
</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <br />
        <div>
            <NameTag data="그룹 수정하기" :style="{ width: '220px' }" />
            <v-card width="1200">
                <!-- 하나의 행을 만듬 -->
                <v-row>
                    <v-col cols="6" class="d-flex flex-column align-center justify-center">
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
                                :placeholder="myGroup.value.name"
                                readonly="true"
                            ></v-text-field>

                            <!-- :value="value" :readonly="true" -->
                            <GenericInputArea
                                data="그룹 소개"
                                placeholder="그룹 소개를 입력하세요"
                                v-model="myGroup.value.description"
                            />

                            <GenericInput   
                                type="password"
                                data="비밀번호 변경"
                                id="groupPassword"
                                v-model="myGroup.value.password"
                            />
                            <GenericInput
                                type="password"
                                data="비밀번호확인"
                                id="groupPasswordCheck"
                            />

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
