<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">登录</a>
      <span>·</span>
      <a href="/register">注册</a>
    </div>
    <div>
      <el-form ref="userForm" :model="userInfo">
        <el-form-item prop="email" label="邮箱"
          :rules="[
            {required: true,message: '请输入邮箱',trigger: 'blur',},
            { type: 'email',message: '请输入正确格式的邮箱', trigger: 'blur' },
          ]">
          <div>
<!--            <i class="el-icon-phone" />-->
            <el-input type="text" placeholder="登录邮箱" v-model="userInfo.email"/>
          </div>
        </el-form-item>
        <el-form-item prop="password" label="密码"
          :rules="[{ required: true, message: '请输入密码', trigger: 'blur' }]">
          <div>
<!--            <i class="el-icon-lock" />-->
            <el-input type="password" placeholder="密码" v-model="userInfo.password"/>
          </div>
        </el-form-item>
        <div class="btn">
          <input type="button" class="sign-in-button" value="登录" @click="submitLogin('userForm')"/>
          <el-link href="http://localhost:9528" :underline="false">我是管理员</el-link>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";
import request from "@/utils/request";
import cookie from "js-cookie";
import login from "@/api/user/login";
export default {
  layout: 'sign',
  data() {
    return {
      userInfo: {
        //封装用于登录的用户对象
        email: "",
        password: ""
      },
      //用于获取接口传来的token中的对象
      loginInfo: {},
    };
  },
  methods: {
    submitLogin(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid){
          login.submitLogin(this.userInfo)
            .then((response) => {
              if (response.data.success) {
                cookie.set("token", response.data.data, {domain: "localhost",});
                login.getLoginInfo().then((response) => {
                  this.loginInfo = response.data.data;
                  cookie.set("userInfo", JSON.stringify(this.loginInfo), { domain: "localhost" });
                  this.$router.push({path:'/'})
                });
              }else {
                this.$message.error(response.data.message)
              }
            });
        }else {
          return false
        }
      })
    },
    checkPhone(rule, value, callback) {
      //debugger
      if (!/^1[34578]\d{9}$/.test(value)) {
        return callback(new Error("手机号码格式不正确"));
      }
      return callback();
    },
  }
}
</script>

<style scoped>

</style>
