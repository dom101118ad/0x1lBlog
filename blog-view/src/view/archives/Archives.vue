<template>
    <div class="text-base-c m-box">
      <div class="top-section border-round-top  text-center">
        <h2 style="font-weight: bold;">Lưu trữ Blog</h2>
        <p class="mt-2 mb-0 text-base-c">Tốt! Hiện tại có tổng cộng {{count}} bài viết</p>
      </div>

      <div class="content-section">
        <div class="timeline">

          <div :class="colorObj[index%5]" v-for="(value, key, index) in blogMap" :key="index">
            <div class="tl-header" >
              <span class="text-base-c p-tag p-tag-lg"> {{key}}</span>
            </div>
            <div class="tl-item" v-for="blog in value" :key="blog.id">
              <div class="tl-wrap">
                <span class="tl-date text-base-c"> {{ blog.day }}</span>
                <router-link  class="timeline-link" :to="'/blog/'+blog.id">
                  <span class="timeline-label tl-title text-base-c"> {{ blog.title}}</span>
                </router-link>
              </div>
            </div>
          </div>

          <div class="tl-header"  style="color: white; padding-left:  calc(2em + 2px);">
            <span class="p-tag p-tag-lg text-base-c bg-gray-900">
              <span style="color: white">Hello World!</span>
            </span>
          </div>
<!--            <div class="tl-dark">-->
<!--              <div class="tl-item">-->
<!--                <div class="tl-wrap">-->
<!--                  <span class="tl-date">N15</span>-->
<!--                  <a href="#" @click.prevent="toBlog({id: 0, title: 'Bài viết đầu tiên'})" class="timeline-link">-->
<!--                    <span class="timeline-label tl-title text-base-c ">Bài viết đầu tiên trên blog của tôi</span>-->
<!--                  </a>-->
<!--                </div>-->
<!--              </div>-->
<!--            </div>-->
        </div>
      </div>
    </div>
</template>
<style scoped>
.top-section{
  padding: 1em;
  background: #fff !important;
  border: 1px solid #d4d4d5;
}
.content-section{
  background: #fff !important;
  padding: 1em;
  border: 1px solid #d4d4d5;
  margin-top: -1px;
}
h2{
  font-family: Lato, 'Helvetica Neue', Arial, Helvetica, sans-serif;
  line-height: 1.28571429em;
  margin: 0 0 1rem 0 ;
  padding: 0;
}

.timeline{
  margin: 20px 0;
}

.tl-header{
  text-align: left;
  margin: 0;
  padding-left:  calc(3em + 2px);
  position: relative;
}

.tl-header .p-tag{
  display: inline-block;
  font-weight: 500;
  letter-spacing: 0.5px;
  padding: 0.5em 1em;
  position: relative;
  z-index: 10;
}

.tl-wrap{
  padding: 12px 0 12px 20px;
  margin-left: 6em;
  border-left: 4px solid;
  position: relative;
}

.timeline-label::before {
  content: '';
  position: absolute;
  right: 100%;
  top: 50%;
  transform: translateY(-50%);
  width: 0;
  height: 0;
  border-style: solid;
  border-width: 6px 8px 6px 0;
  border-color: transparent currentColor transparent transparent;
  z-index: 1;
}

.timeline-label:hover {
  transform: translateY(-2px);
  box-shadow: 0 5px 8px rgba(0, 0, 0, 0.19);
}

.tl-wrap:before {
  position: relative;
  top: 15px;
  float: left;
  width: 10px;
  height: 10px;
  margin-left: -27px;
  background: #fff;
  border-color: inherit;
  border-style: solid;
  border-width: 3px;
  border-radius: 50%;
  content: "";
  box-shadow: 0 0 0 4px #fff;
  z-index: 10;
}

.tl-wrap:hover:before {
  background: 0 0;
  border-color: #fff;
}

.timeline-link {
  text-decoration: none;
  display: inline-block;
}

.tl-date{
  position: relative;
  float: left;
  width: 4.5rem;
  text-align: right;
  margin-left: -7.5em;
  top: 12px;
}

.timeline-label{
  position: relative;
  margin-left: 8px;
  padding: 11px 15px;
  border-radius: 0.21571429rem;
  cursor: pointer;
  transition: all 0.2s ease;
  display: inline-block;
}

.tl-blue .tl-header .p-tag,
.tl-blue .timeline-label {
  background: #23b7e5 !important;
  color: #fff !important;
}

.tl-blue .tl-item .tl-wrap {
  border-color: #23b7e5;
}

.tl-blue .timeline-label::before {
  border-color: transparent #23b7e5 transparent transparent;
}


.tl-dark .tl-header .p-tag,
.tl-dark .timeline-label {
  background: #3a3f51 !important;
  color: #fff !important;
}

.tl-dark .tl-item .tl-wrap {
  border-color: #3a3f51;
}

.tl-dark .timeline-label::before {
  border-color: transparent #3a3f51 transparent transparent;
}


.tl-green .tl-header .p-tag,
.tl-green .timeline-label {
  background: #27c24c !important;
  color: #fff !important;
}

.tl-green .tl-item .tl-wrap {
  border-color: #27c24c;
}


.tl-green .timeline-label::before {
  border-color: transparent #27c24c transparent transparent;
}


.tl-purple .tl-header .p-tag,
.tl-purple .timeline-label {
  background: #7266ba !important;
  color: #fff !important;
}

.tl-purple .tl-item .tl-wrap {
  border-color: #7266ba;
}

.tl-purple .timeline-label::before {
  border-color: transparent #7266ba transparent transparent;
}


.tl-red .tl-header .p-tag,
.tl-red .timeline-label {
  background: #f05050 !important;
  color: #fff !important;
}

.tl-red .tl-item .tl-wrap {
  border-color: #f05050;
}

.tl-red .timeline-label::before {
  border-color: transparent #f05050 transparent transparent;
}


</style>





<script setup>
import {onMounted, ref} from 'vue'
import {getArchives} from "@/api/archive.js";

// const blogMap = ref({
//   "12/2023": [
//     { id: 1, day: "31d", title: "Tổng kết hành trình học tập năm 2023" },
//     { id: 2, day: "25d", title: "Giáng sinh vui vẻ! Kế hoạch nghỉ lễ của tôi" },
//     { id: 3, day: "20d", title: "Thực hành Vue 3 Composition API" },
//     { id: 4, day: "15d", title: "Kinh nghiệm sử dụng thư viện PrimeVue ghiệm sử dụng thư viện Pghiệm sử dụng thư viện Pghiệm sử dụng thư viện P" }
//   ],
//   "11/2023": [
//     { id: 5, day: "30d", title: "Mẹo sử dụng TypeScript nâng cao" },
//     { id: 6, day: "25d", title: "Tối ưu hiệu suất Frontend thực tế" },
//     { id: 7, day: "20d", title: "Nhập môn phát triển Backend với Node.js" },
//     { id: 8, day: "10d", title: "Thiết kế và tối ưu cơ sở dữ liệu" }
//   ],
//   "10/2023": [
//     { id: 9, day: "28d", title: "Thực hành tốt nhất cho thiết kế Responsive" },
//     { id: 10, day: "20d", title: "Phân tích sâu về CSS Grid Layout" },
//     { id: 11, day: "15d", title: "Tính năng mới trong JavaScript ES2023" },
//     { id: 12, day: "05d", title: "Hướng dẫn cấu hình Frontend Engineering" }
//   ],
//   "2023-11": [
//     { id: 13, day: "30d", title: "Cấu hình chi tiết Webpack 5" },
//     { id: 14, day: "25d", title: "So sánh hiệu suất Vite vs Webpack" },
//     { id: 15, day: "18d", title: "Unit Testing và Integration Testing" },
//     { id: 16, day: "10d", title: "Thiết lập pipeline CI/CD" }
//   ],
//   "08/2023": [
//     { id: 17, day: "N28", title: "Thực hành kiến trúc Micro Frontend" },
//     { id: 18, day: "20d", title: "Phân tích so sánh các thư viện quản lý state" },
//     { id: 19, day: "15d", title: "Giải pháp thích ứng cho phát triển di động" },
//     { id: 20, day: "05d", title: "Hướng dẫn phát triển ứng dụng PWA" }
//   ]
// })
const blogMap = ref({})
const count = ref(0)
const colorObj = ref({
  0: 'tl-blue',
  1: 'tl-dark',
  2: 'tl-green',
  3: 'tl-purple',
  4: 'tl-red',
})
const archiveList = async () => {
  try {
    const res = await getArchives()
    if(res.code === 200){
      count.value = res.data.count;
      blogMap.value = res.data.blogMap
    }else {

    }
  }catch (error){
    console.error(error)
  }
}
onMounted(() => {
  archiveList()
})


const toBlog = (blog) => {
  console.log('Điều hướng đến bài viết:', blog)

}
</script>

