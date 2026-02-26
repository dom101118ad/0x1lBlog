<template>
  <div class="comment-form-container">

    <Form :ref="mainFormRef"  v-slot="$form"  :resolver="resolver" class="form-container" @submit="onsubmitF" >
      <div class="textarea-wrapper">
           <Textarea
               :auto-resize="true"
               :rows="1"
               class="custom-textarea"
               :placeholder="nicknameReply"
               :class="{'textarea-custom' : props.replyComment.threadRoot}"
               v-model="form.content"
               name="content"
           ></Textarea>
       </div>
        <Message v-if="$form.content?.invalid " severity="error" size="small" variant="simple">
          {{ $form.content.error?.message}}</Message>
      <div  >
        <div  class="flex justify-content-center flex-wrap">
          <div>
            <font-awesome-icon icon="face-laugh-squint"  />
          </div>
          <div class="flex justify-content-center align-items-center flex-wrap">
            <div  class="input-wrapper">
            <FormField v-slot="$field" name="nickname" initial-value="">
              <font-awesome-icon icon="user"  />

              <InputText  v-model="form.nickname"  type="text"
                          :placeholder="'Nickname (bắt buộc)'"/>
              <Message v-if="$form.nickname?.invalid " severity="error" size="small" variant="simple">
                {{ $form.nickname.error?.message  }}</Message>
            </FormField>
          </div>
            <div class="input-wrapper">
              <FormField v-slot="$field" name="email" initial-value="">
                <font-awesome-icon icon="envelope"/>
                <InputText id="on_email" v-model="form.email" name="email"
                           :placeholder="'Email (bắt buộc)'"  @click="toggleEmail"/>
                <Popover ref="opEmail">
                  <div class="flex flex-col gap-4">
                    <h3>Được dùng để nhận email hồi đáp</h3>
                  </div>
                </Popover>
                <Message v-if="$form.email?.invalid" severity="error" size="small" variant="simple">
                  {{$form.email.error?.message}}
                </Message>
              </FormField>
            </div>
            <div class="input-wrapper">
              <font-awesome-icon icon="location-dot"/>
              <InputText id="on_website" name="website" placeholder="https:// (tùy chọn)"
                         @click="toggleWebsite" />
              <Popover ref="opWebsite">
                <div class="flex flex-col gap-4">
                  <h3>Tôi có thể xem xung quanh được không? 😊</h3>
                </div>
              </Popover>
            </div></div>
          <div>
            <span>Thông báo</span>
            <ToggleSwitch v-model="form.notice"/>
            <button type="submit">
              <font-awesome-icon icon="location-arrow" class="mr-2" size="2xl" style="color: #00a6ff; "/>
            </button>
          </div>
        </div>
      </div>
    </Form>
  </div>
</template>
<script setup>
import {computed, ref, watch} from "vue";
import { zodResolver } from '@primevue/forms/resolvers/zod';
import { z } from 'zod';
import {useToast} from "@/plugins/primevueConfig/primePluginVue.js";
const toast = useToast()
const props = defineProps({
  replyComment: {
    type: Object,
    required: true,
  },
})

const opEmail = ref();
const opWebsite = ref();
const toggleEmail = (event) => {
  opEmail.value.toggle(event);
}
const toggleWebsite = (event) => {
  opWebsite.value.toggle(event);
}

const emit = defineEmits(['getPayload'])

const mainFormRef = ref(null)
const form = ref({
  content: '',
  nickname: '',
  email: '',
  website: '',
  notice: true
})
const nicknameReply = ref('')
watch(() => props.replyComment?.parentNickname , (newName,oldValue) =>{
  if(newName)
    nicknameReply.value ='@'+props.replyComment.parentNickname+ ' '
  else
    nicknameReply.value ='Viết bình luận'
}, {deep:true, immediate:true})

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

const onsubmitF= (e) =>{
  console.log(e)
  console.log(form.value)
  const fields = e.states
  if(!e.valid){
    if(fields.content.invalid)
      toast.error(fields.content.errors[0].message)
    if(fields.email.invalid)
      toast.error(fields.email.errors[0].message)
    if(fields.nickname.invalid)
      toast.error(fields.nickname.errors[0].message)
  }
}
</script>
<style scoped>

.comment-form-container{
  background: #eaeaea;
  border: none;
  border-radius: 25px;
  margin-top: -1px;
  font-size: 1em;
  line-height: 1.4285em;
  min-height: 1em;
  position: relative;
  width: 100%;
}
.form-container{
  position: relative;
  display: flex;
  flex-direction: column;
  align-items: center;
  width: 100%;
}
.textarea-wrapper {
  width: 98% ;
  justify-content: center;
  display: flex;
  position: relative;
}
.dividing{
  margin: 0;
  border-bottom: #b3b3b3 1px solid;
  width: 98%;
}
:deep(.p-textarea){
  width: 100% ;
  border: none;
}
:deep(.p-inputtext),
:deep(.p-textarea){
  background: transparent;
  border-radius: 0!important;
  box-shadow: none;
}
.icon-button{
  position: absolute;
  right: 15px;
  bottom: 6px;
  cursor: pointer;
}
.input-wrapper{
  border-bottom: #505050 1px solid;
  margin-left: 1rem;
  width: 33%;
}
:deep(.p-inputtext){
  border: none;
  width: 80%;
}
.textarea-custom::placeholder {
  color:  #00a7e0;
  opacity: 1;
}

</style>