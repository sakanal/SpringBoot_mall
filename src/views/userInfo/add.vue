<template>
  <div class="app-container" style="margin-top: 100px">
    <el-row :gutter="20">
      <el-col :span="12" :offset="6">
        <el-form ref="userInfo" :model="userInfo" :rules="userRules" label-position="right" label-width="80px">
          <el-form-item label="用户名" prop="userName">
            <el-input v-model="userInfo.userName" type="text" />
          </el-form-item>
          <el-form-item label="密码" prop="password">
            <el-input v-model="userInfo.password" type="password" />
          </el-form-item>
          <el-form-item label="确认密码" prop="checkPassword">
            <el-input v-model="userInfo.checkPassword" type="password" />
          </el-form-item>
          <el-form-item label="电话" prop="mobile">
            <el-input v-model="userInfo.mobile" />
          </el-form-item>
          <el-form-item label="邮箱" prop="email">
            <el-input v-model="userInfo.email" />
          </el-form-item>
          <el-form-item label="性别">
            <el-radio-group v-model="userInfo.gender">
              <el-radio :label="0">男</el-radio>
              <el-radio :label="1">女</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item label="角色">
            <el-radio-group v-model="userInfo.role">
              <el-radio :label="0">管理员</el-radio>
              <el-radio :label="1">商家</el-radio>
              <el-radio :label="2">用户</el-radio>
            </el-radio-group>
          </el-form-item>
          <el-form-item>
            <template v-if="isSave === true">
              <el-button type="primary" @click="addUser('userInfo')">添加</el-button>
              <el-button @click="flushForm('userInfo')">重置</el-button>
            </template>
            <template v-else>
              <el-button type="primary" @click="updateUserInfo('userInfo')">保存</el-button>
              <el-button @click="back">返回</el-button>
            </template>
          </el-form-item>
        </el-form>
      </el-col>
    </el-row>
  </div>
</template>

<script>
import userInfo from '@/api/userInfo/userInfo'
export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.userInfo.checkPassword !== '') {
          this.$refs.userInfo.validateField('checkPassword')
        }
        callback()
      }
    }
    const validateCheckPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.userInfo.password) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    const validateMobile = (rule, value, callback) => {
      const phoneReg = /^((1[0-9]{2})+\d{8})$/
      if (value === '') {
        callback(new Error('请输入手机号'))
      } else if (!phoneReg.test(value)) {
        callback(new Error('请输入正确的手机号'))
      } else {
        callback()
      }
    }
    return {
      isSave: true,
      userInfo: {
        userName: '',
        password: '',
        checkPassword: '',
        mobile: '',
        email: '',
        gender: 0,
        role: 2
      },
      userRules: {
        userName: [
          { required: true, message: '请输入用户名', trigger: 'blur' },
          { min: 1, max: 18, message: '长度在 1 到 18 个字符', trigger: 'blur' }
        ],
        password: [
          { required: true, message: '请输入密码', trigger: 'blur' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请二次输入密码', trigger: 'blur' },
          { validator: validateCheckPassword, trigger: 'blur' }
        ],
        mobile: [
          { validator: validateMobile, trigger: 'blur' }
        ],
        email: [
          { required: true, message: '请输入邮箱前缀', trigger: 'blur' },
          { type: 'email', message: '请输入有效邮箱', trigger: 'change' }
        ]
      }
    }
  },
  created() {
    if (this.$route.params && this.$route.params.id) {
      this.isSave = false
      this.userInfo.id = this.$route.params.id
      this.getUserInfoById()
    }
  },
  methods: {
    addUser(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userInfo.addUser(this.userInfo)
            .then(response => {
              this.$message.success('添加用户成功')
              this.$router.push({ path: '/userInfo/list' })
            })
        } else {
          this.$message.error('请完成信息的输入')
          return false
        }
      })
    },
    flushForm(formName) {
      this.$refs[formName].resetFields()
      this.userInfo = {}
      this.userInfo.gender = 0
      this.userInfo.role = 2
    },
    getUserInfoById() {
      userInfo.getUserInfoById(this.userInfo.id)
        .then(response => {
          console.log(response)
          this.userInfo = response.data
          this.userInfo.checkPassword = response.data.password
        })
        .catch(error => console.log(error))
    },
    updateUserInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          userInfo.updateUserInfo(this.userInfo)
            .then(response => {
              this.$message.success('修改用户信息成功')
              this.$router.push({ path: '/userInfo/list' })
            })
        } else {
          this.$message.error('请完成信息的输入')
          return false
        }
      })
    },
    back() {
      this.$router.push({ path: '/userInfo/list' })
    }
  }
}
</script>

<style scoped>

</style>
