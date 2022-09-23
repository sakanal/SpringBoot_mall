<template>
  <el-container style="min-height: 500px">
    <el-main>
      <el-row :gutter="24">
        <el-col :span="20" :offset="2">
          <el-table ref="orderList" :data="orderList" style="width: 100%" @expand-change="listExpandHandle">
            <el-table-column type="expand">
              <template slot-scope="props">
                <el-table :data="props.row.productList.productList" tooltip-effect="dark" style="width: 100%">
                  <el-table-column prop="name" label="商品标题"/>
                  <el-table-column label="商品图片" width="150">
                    <template slot-scope="scope">
                      <el-image :src="scope.row.pictureList[0].url" style="width: 100px; height: 100px"/>
                    </template>
                  </el-table-column>
                  <el-table-column prop="price" label="单价" width="100"/>
                  <el-table-column prop="number" label="数量" width="60"/>
                  <el-table-column label="总价" width="120">
                    <template slot-scope="scope">
                      {{scope.row.price * scope.row.number}}
                    </template>
                  </el-table-column>
                </el-table>
              </template>
            </el-table-column>
            <el-table-column label="订单号" prop="orderSn"/>
            <el-table-column label="创建时间" prop="createTime" width="200"/>
            <el-table-column label="订单状态" prop="status" width="220">
              <template slot-scope="scope">
                <template v-if="scope.row.status === 1">已付款</template>
                <template v-else-if="scope.row.status === 2">已发货</template>
                <template v-else-if="scope.row.status === 3">已收货</template>
                <template v-else>订单错误</template>
                <template v-if="scope.row.status !== 3">
                  <el-button
                    size="mini"
                    type="primary"
                    @click="checkConfirmProduct(scope.row)">确认收货</el-button>
                </template>
              </template>
            </el-table-column>
            <el-table-column label="订单总额" prop="totalAmount" width="120px">
              <template slot-scope="scope">
                ￥ {{Math.round(scope.row.totalAmount)}}
              </template>
            </el-table-column>
            <el-table-column label="运费金额" prop="freightAmount" width="120px">
              <template slot-scope="scope">
                ￥ {{Math.round(scope.row.freightAmount)}}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="120px">
              <template slot-scope="scope">
                <el-button
                  size="mini"
                  type="danger"
                  @click="removeOrder(scope.row.orderSn)">删除订单</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-main>

  </el-container>
</template>

<script>
import cookie from "js-cookie";
import order from "@/api/order/order";

export default {
  data() {
    return {
      isSelect: false,
      isLogin: false,
      userInfo: {},
      orderList: []
    }
  },
  created() {
    this.getUserInfo()
    this.getOrderListByUserId()
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
    // 初始化 根据用户id获取用户的所有订单基础信息，和获取订单内的每个商品信息
    getOrderListByUserId() {
      order.getOrderListByUserId(this.userInfo.id)
        .then(response => {
          this.orderList = response.data.data
          for (let i = 0; i < this.orderList.length; i++) {
            const orderSn = this.orderList[i].orderSn
            order.getOrderInfoByOrderSn(orderSn)
              .then(response => {
                this.orderList[i].productList = response.data.data
              })
              .catch(error => console.log(error))
          }
        })
        .catch(error => console.log(error))
    },
    // 实现手风琴效果
    listExpandHandle(row,expandRows){
      if(expandRows.length === 2) {
        this.$refs.orderList.toggleRowExpansion(expandRows[0], false)
        console.log(expandRows[1])
        this.isSelect = true
      }
    },
    // 根据订单id删除订单
    removeOrder(orderSn){
      this.$confirm('将永久删除该订单, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        order.removeOrderInfoByOrderId([orderSn])
          .then(response => {
            if (response.data.success){
              this.$message.success('删除成功')
              this.getOrderListByUserId()
            }else {
              this.$message.error('删除失败')
            }
          })
          .catch(error => console.log(error))
      })
    },
    // 确认收货
    checkConfirmProduct(row){
      const orderInfo = {
        id: row.id,
        status: 3
      }
      if (row.status === 1){
        this.$confirm('该商品尚未发货，是否确认收货？','提示',{
          confirmButtonText: '确认',
          cancelButtonText: '取消',
          type: 'warning'
        }).then(() => {
          this.confirmProduct(orderInfo)
        })
      }else {
        this.confirmProduct(orderInfo)
      }
    },
    confirmProduct(orderInfo){
      order.confirmProduct(orderInfo)
        .then(response => {
          this.$message.success('收货成功')
          this.getOrderListByUserId()
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>

</style>
