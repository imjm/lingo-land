<script setup>
import Profile from "@/components/common/Profile.vue";
import TaleList from "../tale/TaleList.vue";
import { onMounted, ref } from "vue";
import { useRoute } from "vue-router";
const route = useRoute()
const userId = ref(null)

onMounted(()=> {
    userId.value= route.params.memberId
    console.log('유저아이디',userId.value)
})

</script>

<template>
    <v-main v-if="userId" class="d-flex mt-10 justify-center">
        <v-container>
            <v-row>
                <v-col cols="5">
                    <Profile :others="true" :id="userId"/>
                </v-col>

                <v-col cols="7">
                    <TaleList :others="true" :id="userId"/>
                </v-col>
            </v-row>
        </v-container>
    </v-main>
</template>

<style scoped>
.room-code {
    background-color: #d2f0ff;
    width: 100%;
    height: 300px;
    border-radius: 4px;
    font-size: x-large;
}
</style>
