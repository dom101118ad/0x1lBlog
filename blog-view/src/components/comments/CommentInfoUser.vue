<template>
  <div class="comment-form-container">
    <div  >
      <div  class="flex justify-content-evenly flex-wrap align-items-center">
        <div  class="input-wrapper">
            <font-awesome-icon icon="user"  />
            <InputText ref="inputNickname"
                  type="text" v-model="commentForm.nickname"
                        :placeholder="'Nickname (bắt buộc)'"/>
            <Message v-if="formValidateMessage.nickname" severity="error" size="small" variant="simple">
              {{ formValidateMessage.nickname  }}</Message>
        </div>
        <div class="input-wrapper">
            <font-awesome-icon icon="envelope"/>
            <InputText id="on_email" name="email" ref="inputEmail" v-model="commentForm.email"
                       :placeholder="'Email (bắt buộc)'"  @click="toggleEmail"/>
            <Popover ref="opEmail">
              <div class="flex flex-col gap-4">
                <h3>Được dùng để nhận email hồi đáp</h3>
              </div>
            </Popover>
            <Message v-if="formValidateMessage.email" severity="error" size="small" variant="simple">
              {{formValidateMessage.email}}
            </Message>
        </div>
        <div class="input-wrapper">
          <font-awesome-icon icon="location-dot"/>
          <InputText id="on_website" name="website" placeholder="https:// (tùy chọn)" v-model="commentForm.website"
                     @click="toggleWebsite" />
          <Popover ref="opWebsite">
            <div class="flex flex-col gap-4">
              <h3>Tôi có thể xem xung quanh được không? 😊</h3>
            </div>
          </Popover>
        </div>
      </div>
    </div>

  </div>
</template>
<script setup lang="ts">
import {useCommentStore} from "@/store/commentStore";
import {storeToRefs} from "pinia";
import {type Ref, ref, watch} from "vue";
import {zodResolver} from "@primevue/forms/resolvers/zod";
import {z} from "zod";

const commentStore = useCommentStore()
const { commentForm} = storeToRefs(commentStore)

//Pop
const opEmail = ref();
const opWebsite = ref();
const toggleEmail = (event: any) => {
  opEmail.value.toggle(event);
}
const toggleWebsite = (event: any) => {
  opWebsite.value.toggle(event);
}

// validate
const props = defineProps<{
  formValidateMessage: Record<string, string>
}>()
const inputEmail = ref<any>(null)
const inputNickname = ref<any>(null)
type InfoFields = "nickname" | "email";
const scrollToField = (field: InfoFields) => {
  const fieldMap: Record<InfoFields, any> ={
    nickname: inputNickname,
    email: inputEmail
  }
  const targetRef = fieldMap[field]
  const el: HTMLElement | undefined =
      targetRef.value?.$el ?? targetRef.value;
  el?.scrollIntoView({
    behavior:"smooth",
    block:"center",
  });
}
defineExpose({
  scrollToField
})

const commentSchema = z.object({
  content: z.string()
      // .min(1, { message: 'Phải nhập nội dung bình luận' })
      .max(255, { message: 'Nội dung bình luận quá dài!' }),
  nickname: z.string()
      .min(1, { message: 'Không được để trống Nickname' })
      .max(50, { message: 'Nickname quá dài!' }),
  email: z.string()
      .max(100, { message: 'Email quá dài!' })
      .regex(
          /^(([^<>()[\]\\.,;:\s@"]+(\.[^<>()[\]\\.,;:\s@"]+)*)|(".+"))@((\[[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}\.[0-9]{1,3}])|(([a-zA-Z\-0-9]+\.)+[a-zA-Z]{2,}))$/,
          { message: 'Email không hợp lệ' }
      )
});

const resolver = ref(zodResolver(commentSchema));
</script>
<style scoped>
.comment-form-container{
  background: #c6f4ef;
  border: none;
  margin-top: -1px;
  margin-bottom: 2px;
  font-size: 1em;
  line-height: 1.4285em;
  min-height: 1em;
  position: relative;

}
.form-container{
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;

}


:deep(.p-inputtext),
:deep(.p-textarea){
  background: transparent;
  border-radius: 0!important;
  box-shadow: none;
}

.input-wrapper{
  border-bottom: #505050 1px solid;
  margin-left: 1rem;
  position: relative;

}
:deep(.p-inputtext){
  border: none;
  width: 90%;
}

</style>