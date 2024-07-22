<script setup>
import GroupList from "@/components/group/GroupList.vue";
import SearchInput from "@/components/common/SearchInput.vue";
</script>

<template>
  <br />
  <main>
    <v-sheet rounded class="ma-10">
      <h1 class="mx-10">그룹 목록</h1>
      <SearchInput />
      <GroupList />
    </v-sheet>
  </main>
</template>
