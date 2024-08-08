<script setup>
import { RouterView } from "vue-router";
</script>

<template>
  <RouterLink :to="{ name: 'login' }"> login</RouterLink> |
  <RouterLink :to="{ name: 'gameRoom', params: { roomId: '1' } }">
    gameRoom</RouterLink
  >
  | <RouterLink :to="{ name: 'signUp' }"> signup</RouterLink> |
  <RouterLink :to="{ name: 'writingGame', params: { roomId: '1' } }">
    writingGame</RouterLink
  >
  |
  <RouterLink :to="{ name: 'writingGameResult', params: { roomId: '1' } }">
    writingGameResult</RouterLink
  >
  |
  <RouterLink :to="{ name: 'runningGame', params: { roomId: '1' } }">
    runningGame</RouterLink
  >
  |
  <RouterLink :to="{ name: 'mainPage' }"> mainPage</RouterLink> |
  <RouterLink :to="{ name: 'myPage' }">myPage</RouterLink> |
  <RouterLink :to="{ name: 'myPageModify' }">mypageModify</RouterLink> |

  <RouterLink :to="{ name: 'groupList' }">groupList</RouterLink> |
  
  <RouterLink
    :to="{ name: 'runningGameResult', params: { roomId: '1' } }"
    >runningGameResult</RouterLink
  >
  |

  <v-app class="bg-amber-lighten-3 gamja-flower-regular">
    <RouterView></RouterView>
  </v-app>
</template>

<style>
@import url("https://fonts.googleapis.com/css2?family=Gamja+Flower&family=Gowun+Dodum&family=Hahmlet:wght@100..900&display=swap");
.gamja-flower-regular {
  font-family: "Gamja Flower", sans-serif;
  font-weight: 500;
  font-style: normal;
  font-size: x-large;
}

.do-hyeon-regular {
  font-family: "Do Hyeon", sans-serif;
  font-weight: 300;
  font-style: normal;
  font-size: large;
}
.gowun-dodum-regular {
  font-family: "Gowun Dodum", sans-serif;
  font-weight: 400;
  font-style: normal;
}
</style>
