<script setup>
import { onMounted } from "vue";
import TaleListItem from "./TaleListItem.vue";
import { useTaleStore } from "@/stores/tales";

const store = useTaleStore();
</script>
<meta name="viewport" content="width=device-width, initial-scale=1"></meta>

<template>
    <v-expansion-panels class="pa-4" variant="popout">
        <v-expansion-panel>
            <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
            <TaleListItem
                v-for="(tale, i) in store.tales"
                :key="i"
                :tale="tale"
            />
        </v-expansion-panel>
    </v-expansion-panels>

    <!-- 아직 변수 값을 몰라 임의로 작성하였습니다. 데이터는 store에 임의로 작성하여 구성했습니다. -->
</template>

<style scoped>
/* width */
::-webkit-scrollbar {
  width: 10px;
}

/* Track */
::-webkit-scrollbar-track {
  box-shadow: inset 0 0 5px grey;
  border-radius: 10px;
}

/* Handle */
::-webkit-scrollbar-thumb {
  background: rgb(252, 194, 85);
  border-radius: 10px;
}
</style>
