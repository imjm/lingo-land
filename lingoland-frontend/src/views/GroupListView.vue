<script setup>
import GroupList from "@/components/group/GroupList.vue";
import SearchInput from "@/components/common/SearchInput.vue";
</script>

<template>
  <br />
  <main>
    <v-sheet rounded class="ma-10">
      <v-row>
        <h1 class="my-5 mx-10">그룹 목록</h1>
        <v-col>
          <v-autocomplete
            :items="items"
            append-inner-icon="mdi-microphone"
            class="mx-auto"
            density="comfortable"
            menu-icon=""
            placeholder="그룹 이름을 입력하세요."
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
      <GroupList />
    </v-sheet>
  </main>
</template>
