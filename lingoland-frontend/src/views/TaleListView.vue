<script setup>
import TaleList from "@/components/tale/TaleList.vue";
</script>

<template>
    <v-sheet rounded class="ma-10">
        <v-row>
            <v-col cols="2" class="my-5 ml-10">

                <h1 >동화 목록</h1>
            </v-col>
            <v-col>
                <div class="my-5 d-flex justify-start">

                    <v-autocomplete
                    :items="items"
                    append-inner-icon="mdi-microphone"
                    class="mx-auto"
                    density="comfortable"
                    menu-icon=""
                    placeholder="동화 이름을 입력하세요."
                    prepend-inner-icon="mdi-magnify"
                    style="max-width: 350px"
                    theme="light"
                    variant="solo"
                    auto-select-first
                    item-props
                    rounded
                    ></v-autocomplete>
                </div>
            </v-col>
        </v-row>

        <TaleList />
    </v-sheet>
</template>

<style scoped></style>
