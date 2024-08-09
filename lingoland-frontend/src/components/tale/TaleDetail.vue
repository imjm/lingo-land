<script setup>
import { useTaleStore } from "@/stores/tales";
import { onMounted, ref, watch } from "vue";
import { useRoute } from "vue-router";
import router from "@/router";
import GenericButton from "../common/GenericButton.vue";
import BackButton from "../common/BackButton.vue";

const route = useRoute();
const taleStore = useTaleStore();
const tale = ref(null);
const activePage = ref(1);

const nextPage = () => {
    if (activePage.value < tale.value.content.length + 1) {
        activePage.value++;
    }
};

const prevPage = () => {
    if (activePage.value > 1) {
        activePage.value--;
    }
};

const handlePageClick = (event, pageNumber) => {
    if (pageNumber === activePage.value) {
        nextPage();
    } else if (pageNumber === activePage.value - 1) {
        prevPage();
    }
};

onMounted(() => {
    taleStore.oneTaleById(route.params.bookId).then((responseValue) => {
        tale.value = responseValue;
        console.log("tale", tale.value);
    });
});

watch(activePage, (newPage) => {
    const sceneElement = document.querySelector(".scene");
    if (newPage === 1) {
        sceneElement.classList.add("centered");
    } else {
        sceneElement.classList.remove("centered");
    }
});
</script>

<template>
    <!-- 뒤로가기 -->
    <!-- 책 -->
    <div v-if="tale" class="scene centered gowun-batang-regular ">
        <div class="book" ref="book">
            <!-- 첫 번째 페이지 (커버) -->
            <div
                class="page"
                :class="{
                    active: activePage === 1,
                    flipped: activePage > 1,
                }"
                @click="(event) => handlePageClick(event, 1)"
            >
                <div class="front">
                    <h1>집에 가고 싶어요</h1>
                    <img class="qr" :src="tale.cover" />
                    <div id="carbon-block"></div>
                </div>
                <div class="back">
                    <img class="qr" :src="tale.content[0].illustration" />
                    <h3>– 1 –</h3>
                </div>
            </div>

            <!-- 나머지 페이지 -->
            <div
                v-for="(content, index) in tale.content"
                :key="index"
                class="page"
                :class="{
                    active: activePage === index + 2,
                    flipped: activePage > index + 2,
                }"
                @click="(event) => handlePageClick(event, index + 2)"
            >
                <div class="front d-flex justify-center align-center">
                    <p>
                        {{ content.story }}Lorem ipsum dolor sit amet,
                        consectetur adipiscing elit. Ut porta placerat lacus nec
                        dapibus. In rhoncus dui metus, ut efficitur urna aliquam
                        ut. In hac habitasse platea dictumst. Cras dictum
                        commodo pellentesque. Donec placerat massa sit amet
                        sapien sollicitudin, id molestie est malesuada.
                        Suspendisse potenti. Proin leo mi, eleifend at nibh id,
                        dignissim luctus sem.
                    </p>
                </div>
                <div class="back">
                    <img class="qr" :src="content.illustration" />
                    <h3>– {{ index + 2 }} –</h3>
                </div>
            </div>
        </div>
    </div>
    <link
        rel="stylesheet"
        href="https://fonts.googleapis.com/css2?family=Material+Symbols+Outlined:opsz,wght,FILL,GRAD@20..48,100..700,0..1,-50..200"
    />
</template>

<style scoped>
/* .material-symbols-outlined {
    font-variation-settings:
    'FILL' 1,
    'wght' 700,
    'GRAD' 0,
    'opsz' 48
    } */

@import url("https://fonts.googleapis.com/css2?family=Gowun+Batang&display=swap");
.gowun-batang-regular {
    font-family: "Gowun Batang", serif;
    font-weight: 500;
    font-style: normal;
    font-size: large;
}

h1 {
    text-align: center;
    margin-top: 10px;
}

h3 {
    position: absolute;
    bottom: 20px;
    left: 50%;
    transform: translateX(-50%);
    text-align: center;
    margin: 0;
}

.scene {
    width: 45%;
    height: 80%;
    margin: 2% 2% 2% 50%;
    perspective: 1000px;
    transition: transform 1.5s ease;
}

.scene.centered {
    position: fixed;
    transform: translateX(-50%);
}

.book {
    position: relative;
    width: 100%;
    height: 100%;
    transform-style: preserve-3d;
}

.page {
    cursor: pointer;
    position: absolute;
    color: black;
    width: 100%;
    height: 100%;
    transition: 1s transform;
    transform-style: preserve-3d;
    transform-origin: left center;
}

.front,
.back {
    position: absolute;
    width: 100%;
    height: 100%;
    padding: 2% 5% 5%;
    box-sizing: border-box;
    backface-visibility: hidden;
    background: linear-gradient(to bottom right, #fff1c2, #ffffff);
}

.back {
    transform: rotateY(180deg);
}

.page.active {
    z-index: 1;
}

.page.flipped {
    transform: rotateY(-180deg);
}

.page.flipped:last-of-type {
    z-index: 1;
}

p {
    margin: 50px;
    text-indent: 1em;
    line-height: 1.7;
}

.qr {
    display: block;
    margin: 50px auto;
    max-width: 100%;
    height: 70%;
    max-height: 70%;
}
</style>
