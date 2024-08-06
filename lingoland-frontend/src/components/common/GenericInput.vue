<script setup>
import { defineModel, defineProps, defineEmits } from "vue";
/*
 id: 컴포넌트 id
 type: input 컴포넌트 type 지정
 data: input 컴포넌트 위에 지정할 이름
 variant: input field 모양을 지정
*/
defineEmits(["blurEvent"]);

const model = defineModel();

const props = defineProps({
    id: String,
    type: String,
    data: String,
    variant: String,
    // value:String,
    hint: String,
});
// readonly: {
//   type: Boolean,
//   default: false
// }
</script>
<template>

    <div>
        {{ data }}
        <v-text-field
            clearable
            v-model="model"
            :type="type"
            :id="id"
            variant="outlined"
            :placeholder="hint"
            @blur="$emit('blurEvent')"
           
            ></v-text-field>
            <!-- :readonly="readonly" -->
            <!-- :value="value" -->
    </div>
</template>

<style scoped></style>
