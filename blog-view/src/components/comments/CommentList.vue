<template>
  <div >
    <Comment :comment-stats="commentStats" :comments="comments"/>
    <Pagination :blog-id="blogId"
                :page="page"
                :detailPaginator="detailPaginator"
                @page-change="handlePageChange"
    />
  </div>
</template>

<script setup>
import Comment from "@/components/comments/Comment.vue";
import Pagination from "@/components/comments/Pagination.vue";
import { ref} from "vue";
import {getCommentListByQuery} from "@/network/comment";
const props = defineProps({
  page: {
    type: Number,
    required: true,
  },
  blogId: {
    type: Number,
    required: true,
  }
})

const totalPages = ref(0)
const comments = ref([])
const commentStats = ref({})
const detailPaginator = ref({})

const getCommentList = async (query) => {
  try{
    const res = await getCommentListByQuery(query)
    if(res.code === 200){
      totalPages.value = res.data.comments.totalPage
      comments.value = res.data.comments.list
      commentStats.value = res.data.commentStats
      detailPaginator.value = res.data.comments
    }
  }catch (error){
    console.error(error)
  }
}

const handlePageChange = (query) =>{
  getCommentList(query)
}

</script>
<style scoped>

</style>