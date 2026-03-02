<template>
  <div>
    <h3>Welcome</h3>
    <el-input type="textarea" v-model="text"></el-input>
    <el-button @click="generate1">Genne1</el-button>
    <el-button @click="generate2">Genne2</el-button>
    <div id="container" ></div>
  </div>


</template>

<script setup>

import { ref, computed } from 'vue'
import {generateHtml,generateText} from "@/api/markdown.js";

const text = ref('')






const generate1 = async () => {
  const container = document.getElementById('container')
  console.log(text.value)
  const res = await generateHtml(text.value)
  if(res.code ===200){
    console.log(res.data)
    container.innerHTML = res.data;
  }
}
const generate2 = async () => {
  const container = document.getElementById('container')

  const res = await generateText(text.value)
  if(res.code ===200){
    console.log(res.data)
    container.innerText = res.data;

  }
}
</script>

<style scoped></style>