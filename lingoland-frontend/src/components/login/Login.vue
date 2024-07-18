<script setup>
import GenericInput from '../common/GenericInput.vue';
import NameTag from '../common/NameTag.vue';
import SubmitButton from '../common/SubmitButton.vue';


</script>

<template>
    <v-container>
        <NameTag id="login" type="text" data="로그인"></NameTag>
        <GenericInput id="id" type="id" data="아이디"></GenericInput>
        <GenericInput id="password" type="password" data="비밀번호"></GenericInput>
        <v-switch label="아이디저장" color="orange"></v-switch>
        <SubmitButton data="회원가입"></SubmitButton>
        <a href="">아이디찾기</a> | 
        <a href="">비밀번호찾기</a>
    </v-container>
</template>

<style scoped>

</style>