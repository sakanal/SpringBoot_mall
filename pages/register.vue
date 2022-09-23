<template>
  <div class="main">
    <div class="title">
      <a href="/login">登录</a>
      <span>·</span>
      <a  class="active" href="/register">注册</a>
    </div>
    <div>
      <el-form ref="userInfoForm" :model="userInfoForm" :rules="userRules">
        <el-form-item prop="userName" label="昵称">
          <el-input v-model="userInfoForm.userName" placeholder="请输入昵称"/>
        </el-form-item>
        <el-form-item prop="email" label="邮箱">
          <div>
            <el-input type="text" placeholder="注册邮箱" v-model="userInfoForm.email"/>
          </div>
        </el-form-item>
        <el-form-item prop="code">
          <el-input placeholder="请输入验证码" v-model="userInfoForm.code">
            <template slot="append" v-if="!isSend">
              <el-link :underline="false" @click="getMobile('userInfoForm')">获取验证码</el-link>
            </template>
            <template slot="append" v-else>
              <el-link :underline="false">{{second}}秒后重新获取</el-link>
            </template>
          </el-input>
        </el-form-item>
        <el-form-item prop="password" label="密码">
          <el-input type="password" placeholder="密码" v-model="userInfoForm.password"/>
        </el-form-item>
        <el-form-item prop="checkPassword" label="确认密码">
          <el-input type="password" placeholder="请二次输入密码" v-model="userInfoForm.checkPassword"/>
        </el-form-item>
        <div class="btn">
          <input type="button" class="sign-in-button" value="注册" @click="submitRegister('userInfoForm')"/>
        </div>
      </el-form>
    </div>
  </div>
</template>

<script>
import "~/assets/css/sign.css";
import "~/assets/css/iconfont.css";
import register from "@/api/user/register";
export default {
  layout: 'sign',
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.userInfoForm.checkPassword !== '') {
          this.$refs.userInfoForm.validateField('checkPassword')
        }
        callback()
      }
    }
    const validateCheckPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.userInfoForm.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return {
      userInfoForm: {
        userName: '',
        email: '',
        code: '',
        password: '',
        checkPassword: ''
      },
      userRules: {
        userName: [
          {required: true,message: '请输入昵称',trigger: 'blur'},
          { min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur' }
        ],
        email: [
          {required: true,message: '请输入邮箱',trigger: 'blur'},
          { type: 'email',message: '请输入正确格式的邮箱', trigger: 'blur' }
        ],
        code: [
          {required: true,message: '请输入验证码',trigger: 'blur'},
          {min: 6, max: 6, message:'请输入正确的六位验证码', trigger: 'blur'}
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请二次输入密码', trigger: 'blur' },
          { validator: validateCheckPassword, trigger: 'blur' }
        ]
      },
      isSend: false,
      second: 60
    }
  },
  methods: {
    getMobile(formName) {
      this.$refs.userInfoForm.validateField("email", (emailError) => {
        if (emailError === "") {
          register.getMobile(this.userInfoForm.email)
            .then((response) => {
              if (response.data.success){
                this.$message.success('验证码发送成功')
                this.isSend=true
                this.timeDown();
              }else {
                this.$message.error(response.data.message)
              }
            });
        }
      });
    },
    //倒计时
    timeDown() {
      let result = setInterval(() => {
        --this.second;
        this.codeTest = this.second;
        if (this.second < 1) {
          clearInterval(result);
          this.isSend = false;
          //this.disabled = false;
          this.second = 60;
        }
      }, 1000);
    },
    submitRegister(formName){
      this.$refs[formName].validate((valid) => {
        if(valid){
          register.register(this.userInfoForm)
            .then(response => {
              if (response.data.success){
                this.$message.success('注册成功')
                this.$router.push({path:'/login'})
              }else {
                this.$message.error(response.data.message)
              }
            })
        }else {
          this.$message.error('请完成注册信息的输入')
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
