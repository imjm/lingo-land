<script setup>
import GroupMemberListItem from "./GroupMemberListItem.vue";
import { useMemberStore } from "@/stores/members";
import GenericButton from "@/components/common/GenericButton.vue";
const store = useMemberStore();
import { ref } from "vue";
import router from "@/router";
import GenericInput from "@/components/common/GenericInput.vue";
const dialog = ref(false);

function modify() {
    console.log("modify");
    router.push({ name: "groupModify" });
}
</script>

<template>
    <v-expansion-panels class="pa-4" variant="popout">
        <v-expansion-panel
            v-for="(member, i) in store.members"
            :key="i"
            hide-actions
        >
            <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
            <GroupMemberListItem :member="member" />
        </v-expansion-panel>
    </v-expansion-panels>

    <v-row>
        <div class="d-flex space-between">
            <v-col>
                <v-dialog v-model="dialog" width="500">
                    <v-sheet width="500" class="d-flex align-center pa-15">
                        <v-row>
                            <v-col>
                                <v-row>
                                    <img
                                        src="@/assets/sampleImg.jpg"
                                        alt=""
                                        width="100%"
                                    />
                                </v-row>
                                <v-row>
                                    <h1>그룹이름</h1>
                                </v-row>
                                <v-row>
                                    <div class="d-flex justify-start">
                                        <v-btn
                                            class="ms-auto d-flex align-left"
                                            text="Ok"
                                            @click="
                                                () => {
                                                    dialog = false;
                                                }
                                            "
                                            data="닫기"
                                        ></v-btn>
                                    </div>
                                </v-row>
                            </v-col>
                            <v-col>
                                <h3>자기소개</h3>
                                <GenericInput />
                                <h3>비밀번호</h3>
                                <v-text-field
                                    type="password"
                                    :id="id"
                                    variant="outlined"
                                ></v-text-field>
                            </v-col>
                        </v-row>

                    </v-sheet>
                </v-dialog>
            </v-col>
            </div>
            </v-row>
            <v-row>

                <v-col>
                    <div class="d-flex justify-end ma-2">
                        <GenericButton
                        data="그룹 가입하기"
                        @click="
                            () => {
                                dialog = true;
                            }
                            "
                    />
                    <GenericButton class="mx-5"
                        data="그룹 수정"
                        @click-event="modify"
                    />
                    <!-- <div justify="end" class="mx-10 my-3">
                        <v-btn>그룹 수정</v-btn>
                    </div> -->
                </div>
            </v-col>
        </v-row>
        
</template>

<style scoped></style>
