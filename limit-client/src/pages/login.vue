<template>
  <div class="login_container">
    <div class="login_box">
      <!-- 头像区 -->
      <div class="avatar_box">
        <img src="@/assets/images/logo.png" alt="avatar"/>
      </div>
      <!-- 登录表单 -->
      <div class="login_form_div">
        <a-form-model ref="loginFormRef" :model="loginForm" :rules="loginFormRules" :label-col="labelCol" :wrapper-col="wrapperCol">
          <a-form-model-item has-feedback label="账号" prop="userName">
            <a-input v-model="loginForm.userName" autocomplete="off"/>
          </a-form-model-item>
          <a-form-model-item has-feedback label="密码" prop="password">
            <a-input v-model="loginForm.password" type="password" autocomplete="off"/>
          </a-form-model-item>
          <a-form-model-item :wrapper-col="{ span: 14, offset: 4 }">
            <a-button type="primary" @click="login">
              登录
            </a-button>
            <a-button style="margin-left: 10px" @click="resetLoginForm">
              重置
            </a-button>
          </a-form-model-item>
          <span id="loginMessage">{{ loginMessage }}</span>
        </a-form-model>
      </div>
    </div>
  </div>
</template>

<script>
  import '@/assets/css/login.less';
  import $login from '@/api/login';
  import {AuthResourceList, getAuthResourceCode, setAuthToken, setAuthResourceList} from "../utils/auth";
  import CryptoJS from "crypto-js";
  import $resource from "@/api/auth/resource";

  export default {
    data() {
      return {
        labelCol: {span: 4},
        wrapperCol: {span: 18},
        loginMessage:'',
        loginForm: {
        },
        // 表单验证
        loginFormRules: {
          userName: [
            {
              required: true,
              message: '请输入用户名',
              trigger: 'blur'
            },
            /*{
              min: 2,
              max: 10,
              message: '长度在 2 到 10 个字符',
              trigger: 'blur'
            }*/
          ],
          password: [
            {
              required: true,
              message: '请输入用户密码',
              trigger: 'blur'
            },
            /*{
              min: 6,
              max: 18,
              message: '长度在 6 到 18 个字符',
              trigger: 'blur'
            }*/
          ]
        }
      };
    },
    created(){
      // console.info(process.env.BASE_URL);
    },
    methods: {
      // 表单重置按钮
      resetLoginForm() {
        this.$refs.loginFormRef.resetFields();
      },
      login() {
        // 表单预验证
        // valid：bool类型
        this.$refs.loginFormRef.validate(async valid => {
          // console.log(valid)
          if (!valid) return false;
          const res = await $login.login(this.loginForm);
          console.log(res);
          if (res.code !== 200) {
            this.loginMessage=res.msg;
            return this.$message.error(res.msg);
          }
          this.loginMessage=res.msg;
          this.$message.success(res.msg);
          // 1、将登陆成功之后的token, 保存到客户端的sessionStorage中; localStorage中是持久化的保存
          //   1.1 项目中出现了登录之外的其他API接口，必须在登陆之后才能访问
          //   1.2 token 只应在当前网站打开期间生效，所以将token保存在sessionStorage中
          setAuthToken(res.data);
          await setAuthResourceList();
          // 2、通过编程式导航跳转到后台主页, 路由地址为：/home
          this.$router.push('/index');
          // var wordArray = CryptoJS.enc.Utf8.parse(JSON.stringify(res.data));
          // var base64 = CryptoJS.enc.Base64.stringify(wordArray);
          // window.location.href = 'http://127.0.0.1:81/#/loginSSO?data='+base64
        });
      }
    }
  };
</script>
