<script setup>
import GroupMemberList from "@/components/group/GroupMemberList.vue";

</script>

<template>
    <main class="mt-10">
        <v-row>
            <v-col cols="2">

            </v-col>
            <v-col cols="8">
                <v-sheet class="g-10">
                    <v-row>
                        <v-col cols="5">
                            <h1 class="mx-5 my-0">group.name</h1>
                        </v-col>
                        <v-col >
                            <v-autocomplete
                                :items="items"
                                append-inner-icon="mdi-microphone"
                                class="mx-5 mt-1"
                                density="comfortable"
                                menu-icon=""
                                placeholder="이름을 입력하세요."
                                prepend-inner-icon="mdi-magnify"
                                style="max-width: 350px"
                                theme="light"
                                variant="solo"
                                auto-select-first
                                item-props
                                rounded
                            ></v-autocomplete>
                        </v-col>
                    </v-row>

                    <GroupMemberList />

                    <v-row height="10"></v-row>
                </v-sheet>
            </v-col>
        </v-row>
    </main>
</template>
