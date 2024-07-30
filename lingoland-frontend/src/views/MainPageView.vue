<script setup>
import GenericButton from "@/components/common/GenericButton.vue";
import GenericInput from "@/components/common/GenericInput.vue";
import PageNavigationButton from "@/components/common/PageNavigationButton.vue";
import Profile from "@/components/common/Profile.vue";
import RankList from "@/components/rank/RankList.vue";

</script>

<template>
    <v-main class="d-flex align-center justify-center">
        <v-container>
            <v-row>
                <v-col cols="5" class="d-flex align-center justify-center">
                    <Profile source="src\\assets\\sampleImg.jpg" />
                </v-col>

                <v-col cols="7">
                    <v-row>
                        <v-col
                            cols="6"
                            class="d-flex flex-column align-content-space-center"
                        >
                            <PageNavigationButton
                                background-color="#CCCBFF"
                                data="방 만들기"
                                @click-event="
                                    $router.push({
                                        name: 'testGameRoom',
                                    })
                                "
                            />

                            <div
                                class="room-code d-flex flex-column justify-center align-center"
                            >
                                <div class="ma-3 text-h4 font-weight-black">
                                    방 코드
                                </div>
                                <GenericInput :style="{ width: '90%' }" />
                                <GenericButton data="입력" />
                            </div>
                        </v-col>

                        <v-col cols="6">
                            <RankList />
                        </v-col>
                    </v-row>
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
