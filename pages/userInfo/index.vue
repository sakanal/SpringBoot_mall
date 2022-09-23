<template>
  <el-container style="min-height: 500px">
    <el-main style="min-height: 500px">
      <el-row :gutter="20">
        <el-col :span="18" :offset="2">
          <el-tabs :tab-position="'left'">
            <el-tab-pane label="个人信息">
              <el-row :gutter="20">
                <el-col :span="15" :offset="5">
                  <el-form ref="userInfoForm" :model="userInfo" label-width="80px">
                    <el-form-item label="昵称">
                      <el-input v-model="userInfo.userName"></el-input>
                    </el-form-item>
                    <el-form-item label="电话">
                      <el-input v-model="userInfo.mobile"></el-input>
                    </el-form-item>
                    <el-form-item label="邮箱">
                      <el-input v-model="userInfo.email"></el-input>
                    </el-form-item>
                    <el-form-item label="密码">
                      <el-button @click="openPasswordForm">点击修改</el-button>
                    </el-form-item>
                    <el-form-item label="性别">
                      <el-radio-group v-model="userInfo.gender">
                        <el-radio :label="0">男</el-radio>
                        <el-radio :label="1">女</el-radio>
                      </el-radio-group>
                    </el-form-item>
                    <el-form-item>
                      <el-button type="primary" @click="updateUserInfo">保存</el-button>
                      <el-button @click="reset">重置</el-button>
                    </el-form-item>
                  </el-form>
                </el-col>
              </el-row>
            </el-tab-pane>
            <el-tab-pane label="收货地址">
              <el-row :gutter="20">
                <el-col :span="2" :offset="1">
                  <el-button type="primary" @click="openAddressForm(null)">新增</el-button>
                </el-col>
                <el-col :span="18" :offset="1">
                  <el-table :data="userAddressList" stripe style="width: 100%">
                    <el-table-column prop="name" label="收货人姓名" width="100"/>
                    <el-table-column prop="phone" label="收货人电话" width="140"/>
                    <el-table-column prop="address" label="详细地址"/>
                    <el-table-column label="操作">
                      <template slot-scope="scope">
                        <el-button size="mini"
                          @click="openAddressForm(scope.row.id)">编辑</el-button>
                        <el-button size="mini" type="danger"
                          @click="removeUserAddressById(scope.row.id)">删除</el-button>
                      </template>
                    </el-table-column>
                  </el-table>
                </el-col>
              </el-row>
            </el-tab-pane>
          </el-tabs>
        </el-col>
      </el-row>
    </el-main>
    <!--  修改密码模态框 开始  -->
    <el-dialog title="修改密码" :visible.sync="updatePasswordFormVisible">
      <el-form ref="updatePasswordForm" :model="updatePasswordForm" :rules="passwordRules">
        <el-form-item label="旧密码" label-width="120px" prop="password">
          <el-input type="password" v-model="updatePasswordForm.password" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="新密码" label-width="120px" prop="newPassword">
          <el-input type="password" v-model="updatePasswordForm.newPassword" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="确认密码" label-width="120px" prop="checkPassword">
          <el-input type="password" v-model="updatePasswordForm.checkPassword" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="updatePasswordFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="updateUserPassword('updatePasswordForm')">确 定</el-button>
      </div>
    </el-dialog>
    <!--  修改密码模态框 结束  -->
    <!--  修改收货地址模态框 开始  -->
    <el-dialog title="收货地址" :visible.sync="addressFormVisible">
      <el-form ref="addressForm" :model="addressForm" :rules="addressRules">
        <el-form-item label="收货人姓名" label-width="120px" prop="name">
          <el-input v-model="addressForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="收货人电话" label-width="120px" prop="phone">
          <el-input v-model="addressForm.phone" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="详细地址" label-width="120px" prop="address">
          <el-input v-model="addressForm.address" autocomplete="off"></el-input>
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="addressFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addOrUpdateAddress('addressForm')">确 定</el-button>
      </div>
    </el-dialog>
    <!--  修改收货地址模态框 结束  -->
  </el-container>

</template>

<script>
import cookie from "js-cookie";
import userInfo from "@/api/userInfo/userInfo";
import login from "@/api/user/login";

export default {
  data() {
    const validatePassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请输入密码'))
      } else {
        if (this.updatePasswordForm.checkPassword !== '') {
          this.$refs.updatePasswordForm.validateField('checkPassword')
        }
        callback()
      }
    }
    const validateCheckPassword = (rule, value, callback) => {
      if (value === '') {
        callback(new Error('请再次输入密码'))
      } else if (value !== this.updatePasswordForm.newPassword) {
        callback(new Error('两次输入密码不一致!'))
      } else {
        callback()
      }
    }
    return{
      isLogin: false,
      userInfo: {},
      updatePasswordFormVisible: false,
      updatePasswordForm: {
        userId: '',
        password: '',
        newPassword: ''
      },
      passwordRules: {
        password: [
          { required: true, message: '请输入旧密码', trigger: 'blur' }
        ],
        newPassword: [
          { required: true, message: '请输入新密码', trigger: 'blur' },
          { min: 6, max: 18, message: '长度在 6 到 18 个字符', trigger: 'blur' },
          { validator: validatePassword, trigger: 'blur' }
        ],
        checkPassword: [
          { required: true, message: '请二次输入密码', trigger: 'blur' },
          { validator: validateCheckPassword, trigger: 'blur' }
        ]
      },
      userAddressList: [],
      addressFormVisible: false,
      addressForm:{
        userId: '',
        name: '',
        phone: '',
        address: ''
      },
      addressRules: {
        name: [
          { required: true, message: '请输入收货人姓名', trigger: 'blur' }
        ],
        phone: [
          { required: true, message: '请输入收货人电话', trigger: 'blur' }
        ],
        address: [
          { required: true, message: '请输入收货人详细地址', trigger: 'blur' }
        ]
      },
    }
  },
  created() {
    this.getUserInfo()
    this.getUserAddressListByUserId()
  },
  methods: {
    // 获取cookie中储存的用户信息
    getUserInfo() {
      const userInfo = cookie.get('userInfo')
      if (userInfo){
        this.userInfo = JSON.parse(userInfo);
        this.isLogin = true
      }else {
        this.$message.error('请先登录')
        this.$router.push({path: '/login'})
      }
    },
    // 解析token
    reset(){
      login.getLoginInfo().then((response) => {
        this.userInfo = response.data.data;
      });
    },
    // 修改用户基本信息
    updateUserInfo(){
      userInfo.updateUserInfo(this.userInfo)
        .then(response => {
          this.$message.success('保存成功')
          login.submitLogin(this.userInfo)
            .then(response => {
              console.log(response)
              if (response.data.success) {
              cookie.set("token", response.data.data, {domain: "localhost",});
              login.getLoginInfo().then((response) => {
              this.loginInfo = response.data.data;
              cookie.set("userInfo", JSON.stringify(this.loginInfo), { domain: "localhost" });
              this.$router.go(0)
            });
          }
            })
            .catch(error => console.log(error))
        })
        .catch(error => console.log(error))
    },
    // 开启修改密码模态框
    openPasswordForm(){
      this.updatePasswordForm={}
      this.updatePasswordFormVisible=true
    },
    // 修改用户密码
    updateUserPassword(fromName){
      this.$refs[fromName].validate((valid)=>{
        if (valid){
          this.updatePasswordForm.userId = this.userInfo.id
          userInfo.updatePassword(this.updatePasswordForm)
            .then(response => {
              if (response.data.success){
                this.$message.success('修改成功')
                cookie.remove("token")
                cookie.remove("userInfo")
                this.$router.go(0)
              }else {
                this.$message.error(response.data.message)
              }
            })
            .catch(error => console.log(error))
        }else {
          this.$message.error('请完成信息的输入')
        }
      })
    },
    getUserAddressListByUserId() {
      userInfo.getUserAddressListByUserId(this.userInfo.id)
        .then(response => {
          this.userAddressList = response.data.data
        })
        .catch(error => console.log(error))
    },
    // 开启收货地址模态框
    openAddressForm(userAddressId){
      this.addressForm = {}
      if (!userAddressId){
        // 新增地址
      }else {
        // 修改地址
        userInfo.getUserAddressById(userAddressId)
          .then(response => {
            this.addressForm = response.data.data
          })
          .catch(error => console.log(error))
      }
      this.addressFormVisible = true
    },
    addOrUpdateAddress(formName){
      this.$refs[formName].validate((valid)=>{
        if (valid){
          if (this.addressForm.userId===undefined){
            this.addressForm.userId = this.userInfo.id
            userInfo.addUserAddress(this.addressForm)
              .then(response => {
                if (response.data.success){
                  this.$message.success('新增收货地址成功')
                  this.getUserAddressListByUserId()
                  this.addressFormVisible = false
                }else {
                  this.$message.error('新增收货地址失败')
                }
              })
              .catch(error => console.log(error))
          }else {
            //TODO
            userInfo.updateUserAddress(this.addressForm)
              .then(response => {
                if (response.data.success){
                  this.$message.success('修改收货地址成功')
                  this.getUserAddressListByUserId()
                  this.addressFormVisible = false
                }else {
                  this.$message.error('修改收货地址失败')
                }
              })
              .catch(error => console.log(error))
          }
        }else {
          this.$message.error('请完成信息的输入')
        }
      })
    },
    removeUserAddressById(ids){
      this.$confirm('是否删除该收货地址?','提示',{
        confirmButtonText: '确认',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        userInfo.removeUserAddressById([ids])
          .then(response => {
            if (response.data.success){
              this.$message.success('删除成功')
              this.getUserAddressListByUserId()
            }else {
              this.$message.error('删除失败')
            }
          })
          .catch(error => console.log(error))
      })
    }
  }
}
</script>

<style scoped>

</style>
