<script setup>
import GenericButton from "../common/GenericButton.vue";
import GenericInput from "../common/GenericInput.vue";
import SubmitButton from "../common/SubmitButton.vue";
import NameTag from "../common/NameTag.vue";
import ImageBox from "../common/ImageBox.vue";
import SearchInput from "../common/SearchInput.vue";
import Dropbox from "../common/DropBox.vue";
import PageNavigationButton from "../common/PageNavigationButton.vue";

const imageSource = "src\\assets\\sampleImg.jpg";

const dropBoxItem = [
    "California",
    "Colorado",
    "Florida",
    "Georgia",
    "Texas",
    "Wyoming",
];

function signUp() {
    console.log("회원가입해");
}
</script>

<template>
    <ImageBox aspectRatio="16/9" :width="200" :source="imageSource" />
    <NameTag type="text" data="회원가입" id="signInTag" />
    <GenericInput type="text" data="이름" id="userName" />
    <GenericInput type="text" data="아이디" id="userId" />
    <GenericButton data="중복확인" id="checkDuplicate" />
    <GenericInput type="password" data="비밀번호" id="userPassword" />
    <GenericInput type="password" data="비밀번호확인" id="userPasswordCheck" />
    <SubmitButton id="signUp" data="회원가입" @click-event="signUp" />
    <SearchInput id="searchInput" />
    <Dropbox label="select" :items="dropBoxItem" />
    <PageNavigationButton id="makeRoom" data="방만들기"/>
    <PageNavigationButton id="findRoom" data="방찾기"/>
</template>

<style scoped></style>
