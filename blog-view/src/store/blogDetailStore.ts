import {defineStore} from "pinia";
import {ref} from "vue";

export const useBlogDetailStore = defineStore('blogDetail',()=>{
    const isBlogRenderCompleted = ref<boolean>(false)

    return{
        isBlogRenderCompleted
    }
})