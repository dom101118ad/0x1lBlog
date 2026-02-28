<template>
  <Paginator
    :rows="commentQuery.pageSize"
    :total-records="totalPages*commentQuery.pageSize"
    @page="handleCurrentChange"
    :pageLinkSize="7"
    :alwaysShow="false"
  />
</template>

<script setup lang="ts">
import {useCommentStore} from "@/store/commentStore";
import type {PageState} from "primevue";
import {storeToRefs} from "pinia";
import {onMounted} from "vue";

const commentStore = useCommentStore()
const {commentQuery, totalPages} = storeToRefs(commentStore)

const handleCurrentChange = (pageState: PageState) => {
  commentStore.setCommentQueryPageNum(pageState.page+1)
  commentStore.resetFormComment()
  commentStore.getCommentList()
}
</script>

<style scoped>
:deep(.p-paginator-page),
:deep(.p-paginator-next),
:deep(.p-paginator-first),
:deep(.p-paginator-last),
:deep(.p-paginator-prev) {
  font-weight: bolder;
  border-radius: 0;
}
:deep(.p-paginator-page.p-paginator-page-selected){
  color: #005cd5;
  background: #c1efff;
  pointer-events: none ;
  cursor: default ;
}
:deep(.p-paginator-page:not(.p-disabled):not(.p-paginator-page-selected):hover),
:deep(.p-paginator-next:not(.p-disabled):hover),
:deep(.p-paginator-first:not(.p-disabled):hover),
:deep(.p-paginator-last:not(.p-disabled):hover),
:deep(.p-paginator-prev:not(.p-disabled):hover) {
  background: rgba(200, 240, 255, 0.35);
}


</style>

