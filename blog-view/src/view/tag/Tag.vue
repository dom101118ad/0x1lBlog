<template>
  <div>
    <BlogList :getBlogList="fetchBlogsByTagId" :blogList="blogList" :totalPage="totalPage"/>
  </div>
</template>

<script setup lang="ts">
import BlogList from "@/components/blogList/BlogList.vue";
import { getBlogListByTagId} from "@/api/tags";
import {computed, nextTick, onMounted, ref, watch} from "vue";
import {useRoute} from "vue-router";
import type {ApiResponse} from "@/plugins/axios2";
import type {BlogInfo} from "@/types/blogType";
import type {TagIdGetBlogsResponse} from "@/types/tagType";
const route = useRoute()

const blogList = ref<BlogInfo[]>([])
const totalPage = ref<number>(0)
const tagId = computed<number>(() => parseInt(<string>route.params.id))

const fetchBlogsByTagId = async (pageNum: number) => {
  try {
    const response: ApiResponse<TagIdGetBlogsResponse> =
        await getBlogListByTagId(tagId.value,pageNum, 2)
    if (response.code === 200){
      blogList.value = response.data.blogInfos.list
      totalPage.value = response.data.blogInfos.pages
    }
  }catch (err) {
  }
}

watch(() => route.fullPath, () => {
      fetchBlogsByTagId(1)
    },
    {immediate: true}
)

</script>