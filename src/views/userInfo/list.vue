<template>
  <div class="app-container">
    <el-form ref="userQuery" :inline="true" :model="userQuery" size="small" class="demo-form-inline">
      <el-form-item>
        <el-input v-model="userQuery.userName" placeholder="姓名" />
      </el-form-item>
      <el-form-item>
        <el-select v-model="userQuery.role" clearable placeholder="角色">
          <el-option :value="0" label="管理员" />
          <el-option :value="1" label="商家" />
          <el-option :value="2" label="普通用户" />
        </el-select>
      </el-form-item>
      <el-form-item label="添加时间">
        <el-date-picker v-model="userQuery.startTime" type="datetime" placeholder="选择开始时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>
      </el-form-item>
      <el-form-item>
        <el-date-picker v-model="userQuery.endTime" type="datetime" placeholder="选择结束时间" value-format="yyyy-MM-dd HH:mm:ss" default-time="00:00:00"/>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getUserInfoListPage()">查询</el-button>
        <el-button @click="resetData('userQuery')">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="isLoading" :data="userInfoList" style="width: 100%">
      <el-table-column label="序号" width="60">
        <template slot-scope="scope">
          {{ (current-1)*10 + scope.$index + 1 }}
        </template>
      </el-table-column>

      <el-table-column prop="userName" label="用户名" width="120" />

      <el-table-column prop="role" label="角色" width="120">
        <template slot-scope="scope">
          <template v-if="scope.row.role === 0">管理员</template>
          <template v-if="scope.row.role === 1">商家</template>
          <template v-if="scope.row.role === 2">普通用户</template>
        </template>
      </el-table-column>

      <el-table-column prop="email" label="邮箱" />

      <el-table-column prop="createTime" label="添加时间" width="180" />

      <el-table-column label="操作" width="200" align="center">
        <template slot-scope="scope">
          <router-link :to="'/userInfo/edit/'+scope.row.id">
            <el-button type="primary" size="mini">修改</el-button>
          </router-link>
          <el-button size="mini" type="danger" @click="removeUserById(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      style="padding: 30px 0;text-align: center"
      layout="total, prev, pager, next, jumper"
      :current-page="current"
      :page-size="10"
      :total="total"
      @current-change="getUserInfoListPage"/>
  </div>
</template>

<script>
import userInfo from '@/api/userInfo/userInfo'

export default {
  data() {
    return {
      isLoading: true,
      current: 1,
      total: -1,
      userInfoList: [],
      userQuery: {}
    }
  },
  created() {
    this.getUserInfoListPage()
    this.isLoading = false
  },
  methods: {
    getUserInfoListPage(current = 1) {
      this.isLoading = true
      this.current = current
      userInfo.pageUserInfoFind(this.current, this.userQuery)
        .then(response => {
          console.log(response)
          this.userInfoList = response.data.records
          this.total = response.data.total
          this.isLoading = false
        })
        .catch(error => {
          console.log(error)
          this.isLoading = false
        })
    },
    resetData(formName) {
      this.userQuery = {}
      this.getUserInfoListPage()
    },
    removeUserById(userId) {
      this.$confirm('此操作将永久删除该用户信息, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [userId]
        userInfo.removeUserById(ids)
          .then(response => {
            this.$message.success('该用户删除成功')
            this.getUserInfoListPage()
          })
          .catch(error => console.log(error))
      })
    }
  }
}
</script>

<style scoped>

</style>
