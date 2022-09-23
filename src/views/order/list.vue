<template>
  <div class="app-container">
    <div>
      <el-form :inline="true" :model="orderQuery" class="demo-form-inline">
        <el-form-item label="订单号">
          <el-input v-model="orderQuery.orderSn" placeholder="订单号"></el-input>
        </el-form-item>
        <el-form-item label="订单状态">
          <el-select v-model="orderQuery.status" placeholder="订单状态">
            <el-option label="未发货" :value="1"></el-option>
            <el-option label="已发货" :value="2"></el-option>
            <el-option label="已收货" :value="3"></el-option>
            <el-option label="订单异常" :value="4"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="searchOrder">查询</el-button>
          <el-button @click="reset">重置</el-button>
        </el-form-item>
      </el-form>
    </div>
    <div>
      <el-table v-loading="isLoading" :data="orderList" style="width: 100%">
        <el-table-column prop="orderSn" label="订单号"/>
        <el-table-column prop="status" label="订单状态" width="180">
          <template slot-scope="scope">
            <template v-if="scope.row.status === 1">未发货</template>
            <template v-else-if="scope.row.status === 2">已发货</template>
            <template v-else-if="scope.row.status === 3">已收货</template>
            <template v-else>订单错误</template>
            <template v-if="scope.row.status === 1">
              | <el-button
                size="mini"
                type="primary"
                @click="shipProduct(scope.row)">发货</el-button>
            </template>
          </template>
        </el-table-column>
        <el-table-column prop="totalAmount" label="总金额" width="160"/>
        <el-table-column prop="createTime" label="创建时间" width="180"/>
        <el-table-column label="操作">
          <template slot-scope="scope">
            <el-button
              size="mini"
              @click="viewOrder(scope.row)">查看</el-button>
          </template>
        </el-table-column>
      </el-table>
    </div>


    <el-pagination
      style="padding: 30px 0;text-align: center"
      layout="total, prev, pager, next, jumper"
      :page-count="pages"
      :total="total"
      @current-change="pageFind">
    </el-pagination>
  </div>
</template>

<script>
import order from '@/api/order/order'

export default {
  data() {
    return {
      isLoading: true,
      current: 0,
      total: 0,
      pages: 0,
      orderQuery: {
        userId: '',
        orderSn: ''
        // status: 0
      },
      orderList: []
    }
  },
  created() {
    this.pageFind()
  },
  methods: {
    // 初始化 分页获取订单信息
    pageFind(current = 1) {
      this.isLoading = true
      this.current = 1
      order.pageFind(current, this.orderQuery)
        .then(response => {
          if (response.success) {
            this.orderList = response.data.records
            this.isLoading = false
            this.pages = response.data.pages
            this.total = response.data.total
          } else {
            this.$message.error('该条件下暂无订单')
            this.isLoading = false
          }
        })
        .catch(error => {
          console.log(error)
          this.isLoading = false
        })
    },
    // 搜索订单
    searchOrder() {
      this.isLoading = true
      order.pageFind(1, this.orderQuery)
        .then(response => {
          if (response.success) {
            console.log(response.data)
            this.orderList = response.data.records
            this.isLoading = false
          } else {
            this.$message.error('该条件下暂无数据')
            this.isLoading = false
          }
        })
        .catch(error => {
          console.log(error)
          this.isLoading = false
        })
    },
    // 发货
    shipProduct(row) {
      const orderInfo = {
        id: row.id,
        status: 2
      }
      order.shipProduct(orderInfo)
        .then(response => {
          if (response.success) {
            this.$message.success('发货成功')
            this.pageFind()
          } else {
            this.$message.error('发货失败')
          }
        })
    },
    // 查看订单详细信息
    viewOrder(row) {
      this.$router.push({ path: `/order/orderInfo/${row.orderSn}` })
    },
    // 重置
    reset() {
      this.orderQuery = {}
      this.pageFind()
    }
  }
}
</script>

<style scoped>

</style>
