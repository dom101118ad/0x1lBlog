<template>
  <div class="site">
    <Nav :blogName="blogName"/>

    <div class="main">
      <div class="py-6">
        <div class="container mx-auto" style="max-width: 1450px;">
          <div class="flex flex-row flex-nowrap ">

            <div class="hidden md:block flex-none sticky-sidebar-wrapper" style="width: 18.75% !important;">
              <div class="sticky-sidebar">
                <Introduction/>
              </div>
            </div>
            <div class="flex-1 min-w-0 main-content" style="width: 62.5% !important;">
              <router-view/>
            </div>

            <div class="hidden md:block flex-none" style="width: 18.75% !important;">
              <Tags :tag-list="tags"/>
            </div>
          </div>
        </div>
      </div>
      <button
          @click="scrollToTop"
          class="scroll-top-btn"
      >
        <img src="/src/assets/img/plane-top.png"  alt="dfds">
      </button>
    </div>

    <Footer :siteInfo="siteInfo" :badges="badges" :newBlogList="newBlogList" :hitokoto="hitokoto"/>
  </div>
</template>

<script setup lang="ts">
import Nav from "@/components/index/Nav.vue";
import {computed, onMounted, ref, watch} from "vue";
import Footer from "@/components/index/Footer.vue";
import {getHitokoto, getSite, translateUrl} from "@/network/index.js";
import {useAppStore} from "@/store";
import {useRoute} from "vue-router";
import Introduction from "@/components/sidebar/Introduction.vue";
import {storeToRefs} from "pinia";
import {useScrollToTop} from '@/util/ScrollToTop.js'
import Tags from "@/components/sidebar/Tags.vue";
import {getTags} from '@/network/tags'
import type { ApiResponse} from "@/plugins/axios2";
import type {Tag} from "@/types/tagType";


const tags = ref<Tag[]>([])
const loading = ref(false)
const error = ref<string|null>(null)

const {scrollToTop} = useScrollToTop()

const route = useRoute()
const store = useAppStore()
const {siteInfo} = storeToRefs(store)

const badges = ref([])
const newBlogList = ref([])
const hitokoto = ref<Record<string, any>>({})
const blogName = computed(() => siteInfo.value?.blogName || 'Thinh0x1l\'')

// Functions
const getHitokotoData = async () => {
  try {
    const res = await getHitokoto()
    hitokoto.value = res
    const trans1: Record<string, any> = await translateUrl(hitokoto.value.hitokoto)
    const trans2: Record<string, any> = await translateUrl(hitokoto.value.from)
    hitokoto.value.hitokoto = trans1[0][0][0]
    hitokoto.value.from = trans2[0][0][0]
  } catch (error) {
    console.error('Lấy hitokoto thất bại:', error)
    hitokoto.value = {
      hitokoto: 'Hãy viết code bằng cả trái tim',
      from: 'Lập trình viên'
    }
  }
}

watch(() => route.fullPath, () =>{
  scrollToTop()
})

const site = async () => {
  try {
    const res:Record<string, any> = await getSite();
    if(res.code === 200){
      badges.value = res.data.badges
      newBlogList.value = res.data.newBlogList

      store.saveIntroduction(res.data.introduction)
      store.saveSiteInfo(res.data.siteInfo)
      watch(
          [() => route.meta?.title , () => store.webTitleSuffix],
          ([title, suffix]) => {
            document.title = `${title || ''} | ${suffix}`;
          },
          { immediate: true }
      )
    }
  } catch (e) {
    console.error(e)
  }
}
const fetchTags = async () => {
  loading.value = true
  error.value = null
  try {
    const response: ApiResponse<Tag[]> = await getTags()
    if(response.code === 200){
      tags.value = response.data ?? []
      console.log(tags.value)
    }
  }catch (err: any){
  }finally {
    loading.value = false
  }
}

onMounted(() => {
  site()
  getHitokotoData()
  fetchTags()
})
</script>

<style scoped>
/* Reset các thuộc tính có thể ảnh hưởng đến sticky */
.site, .main, .container, .flex {
  position: static !important;
  overflow: visible !important;
}

.site {
  display: flex;
  min-height: 100vh;
  flex-direction: column;
}

.main {
  margin-top: 40px;
  flex: 1;
  position: relative;
}
.main-content{
  padding-left: 1rem;
  padding-right: 1rem;
  padding-bottom: 1rem;
}
@media (max-width: 768px) {
  .main-content{
    padding-right: max((100vw - 421px)/25, 0px) !important;
    padding-left: max((100vw - 421px)/25, 0px) !important;
  }
}
.scroll-top-btn img{
  width: 56px;
  height: 56px;
}
.scroll-top-btn {
  background: none;
  position: fixed;
  bottom: 2.5rem;
  right: 2.5rem;
  border: none;
  cursor: pointer;
  z-index: 1000;
}

.scroll-top-btn:hover {
  transform: translateY(-3px);
  filter: brightness(1.1);
}
/* Sticky sidebar wrapper */
.sticky-sidebar-wrapper {
  align-self: flex-start;
  position: sticky;
  top: 68px;
  height: 100%;
}

/*.sticky-sidebar {
  position: sticky;n dev
  top: 10px;
  height: fit-content;
  overflow-y: auto;
}*/

</style>