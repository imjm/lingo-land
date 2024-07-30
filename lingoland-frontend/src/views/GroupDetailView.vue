<script setup>
import GroupMemberList from "@/components/group/GroupMemberList.vue";

const items = [
    {
        prependIcon: "mdi-clock-outline",
        title: "recipe with chicken",
    },
    {
        prependIcon: "mdi-clock-outline",
        title: "best hiking trails near me",
    },
    {
        prependIcon: "mdi-clock-outline",
        title: "how to learn a new language",
    },
    {
        prependIcon: "mdi-clock-outline",
        title: "DIY home organization ideas",
    },
    {
        prependIcon: "mdi-clock-outline",
        title: "latest fashion trends",
    },
];
const shortcuts = [
    {
        icon: "mdi-github",
        title: "Master ",
        href: "https://github.com/vuetifyjs/vuetify",
    },
    {
        icon: "mdi-github",
        title: "Dev",
        href: "https://github.com/vuetifyjs/vuetify/tree/dev",
    },
    {
        icon: "mdi-github",
        title: "Stable",
        href: "https://github.com/vuetifyjs/vuetify/tree/v2-stable",
    },
    {
        icon: "mdi-github",
        title: "My Pull Requests",
        href: "https://github.com/vuetifyjs/vuetify/pulls/johnleider",
    },
];
</script>

<template>
    <br />
    <main>
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
