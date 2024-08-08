<script setup>
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import IncorrectList from "@/components/incorrect/IncorrectList.vue";
import Swal from "sweetalert2";
import { ref, defineProps } from "vue";
import GenericButton from "../common/GenericButton.vue";
import NameTag from "../common/NameTag.vue";

const dialog = ref(false);
const props = defineProps({
    groupId : Number,
    memberId : String,

})
</script>

<template>
    <PageNavigationButton
        background-color="#BF5847"
        data="오답노트"
        source="src\assets\오답노트.png"
        @click-event="
            () => {
                dialog = true;
            }
        "
    />
    <v-dialog v-model="dialog" width="50%">
        <NameTag data="오답노트" />
        <IncorrectList :group-id="groupId" :member-id="memberId"/>

        <div class="d-flex justify-end bg-white">
            <GenericButton
                class="ma-3"
                @click-event="
                    () => {
                        dialog = false;
                    }
                "
                data="닫기"
                :style="{
                    width: '40px',
                    backgroundColor: '#4C4637',
                    color: 'white',
                }"
            />
        </div>
    </v-dialog>
</template>

<style scoped></style>
