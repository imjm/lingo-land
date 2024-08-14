<template>
  <div :class="['main', { dimmed: isDimmed }]">
    <!-- @click="toggleDim" -->
    <BackButton />

    <TaleDetail class="gowun-batang-regular" />
  </div>
  <!-- <v-carousel :show-arrows="false">
    <v-carousel-item
      id="full"
      v-for="(item, i) in items"
      :key="i"
      :src="item.src"
      cover
    ></v-carousel-item>
  </v-carousel> -->
</template>

<script setup>
import { ref } from "vue";
import BackButton from "../../../src/components/common/BackButton.vue";
import TaleDetail from "./lodingbooks.vue";
import bookStore from "/bookStore.jpg";

// 이미지들을 import로 가져옵니다
import localImage1 from "../../../public/loading/0e695e11-7316-414e-bf32-793ea624dcb1.webp";
import localImage2 from "../../../public/loading/5eb606a2-ef51-4a68-8fbd-208f1afcfb2e.webp";
import localImage3 from "../../../public/loading/374e42c6-292f-47ee-836c-2d3e5cdec385.webp";
import localImage4 from "../../../public/loading/85752535-cebf-4f07-a1eb-0a817d938ce3.webp";
import localImage5 from "../../../public/loading/7aa4caa8-31c8-4786-adeb-ac1f33e7e773.webp";

const items = ref([
  {
    src: localImage2,
  },
  {
    src: localImage3,
  },
  {
    src: localImage4,
  },
  {
    src: localImage5,
  },
]);

// 밝기 상태를 관리하는 ref 생성
const isDimmed = ref(false);

// 배경을 어둡게/밝게 전환하는 함수
// function toggleDim() {
//   isDimmed.value = !isDimmed.value;
// }
</script>
<style>
#full {
  height: 100%;
}

.main {
  background-image: url(/bookStore.jpg);
  background-size: cover;
  height: 100vh;
  width: 100vw;
  transition: filter 1s ease; /* 필터 애니메이션 적용 */
}

/* 어두운 상태에 적용될 클래스 */
.dimmed {
  filter: brightness(50%);
}
</style>
