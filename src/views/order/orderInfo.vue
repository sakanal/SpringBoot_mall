<template>
  <div class="app-container">
    <div>
      <el-table :data="[orderInfo]" style="width: 100%">
        <el-table-column label="订单信息">
          <el-table-column prop="orderSn" label="订单号" width="300"/>
          <el-table-column label="订单状态" prop="status" width="180">
            <template slot-scope="scope">
              <template v-if="scope.row.status === 1">已付款</template>
              <template v-else-if="scope.row.status === 2">已发货</template>
              <template v-else-if="scope.row.status === 3">已收货</template>
              <template v-else>订单错误</template>
            </template>
          </el-table-column>
          <el-table-column prop="totalAmount" label="总金额" width="180">
            <template slot-scope="scope">
              {{ Math.round(scope.row.totalAmount) }}
            </template>
          </el-table-column>
          <el-table-column prop="payAmount" label="付款" width="180">
            <template slot-scope="scope">
              {{ Math.round(scope.row.payAmount) }}
            </template>
          </el-table-column>
          <el-table-column prop="freightAmount" label="运费" width="180">
            <template slot-scope="scope">
              {{ Math.round(scope.row.freightAmount) }}
            </template>
          </el-table-column>
          <el-table-column prop="createTime" label="下单时间"/>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-table :data="[userInfo]" style="width: 100%">
        <el-table-column label="购买人信息">
          <el-table-column prop="userName" label="购买人姓名" width="300"/>
          <el-table-column prop="mobile" label="购买人电话" width="180"/>
          <el-table-column prop="email" label="购买人邮箱" width="180"/>
          <el-table-column prop="gender" label="购买人性别" width="180">
            <template slot-scope="scope">
              <template v-if="scope.row.gender === 0">男</template>
              <template v-else-if="scope.row.gender === 1">女</template>
              <template v-else>无</template>
            </template>
          </el-table-column>
          <el-table-column prop="isDelete" label="用户状态" width="180">
            <template slot-scope="scope">
              <template v-if="scope.row.isDelete === 0">激活</template>
              <template v-else-if="scope.row.isDelete === 1">注销</template>
              <template v-else>错误</template>
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-table :data="[addressInfo]" style="width: 100%">
        <el-table-column label="收货人信息">
          <el-table-column prop="name" label="收货人姓名" width="300"/>
          <el-table-column prop="phone" label="收货人电话" width="180"/>
          <el-table-column prop="address" label="收货地址"/>
        </el-table-column>
      </el-table>
    </div>
    <div>
      <el-table :data="productList" style="width: 100%">
        <el-table-column label="商品信息">
          <el-table-column prop="name" label="商品标题" width="300"/>
          <el-table-column prop="pictureList" label="商品图片" width="180">
            <template slot-scope="scope">
              <el-image :src="scope.row.pictureList[0].url" style="width: 100px; height: 100px"/>
            </template>
          </el-table-column>
          <el-table-column prop="price" label="商品单价"/>
          <el-table-column prop="number" label="商品数量"/>
          <el-table-column  label="商品总价">
            <template slot-scope="scope">
              {{ scope.row.price * scope.row.number }}
            </template>
          </el-table-column>
        </el-table-column>
      </el-table>
    </div>
  </div>
</template>

<script>
import order from '@/api/order/order'

export default {
  data() {
    return {
      orderSn: '',
      orderInfo: {},
      userInfo: {},
      addressInfo: {},
      productList: []
    }
  },
  created() {
    if (this.$route.params && this.$route.params.orderSn) {
      this.orderSn = this.$route.params.orderSn
      this.getOrderInfoByOrderSn()
    } else {
      this.$router.go(-1)
    }
  },
  methods: {
    // 初始化 获取订单的基本信息
    getOrderInfoByOrderSn() {
      order.getOrderInfoByOrderSn(this.orderSn)
        .then(response => {
          if (response.success) {
            this.orderInfo = response.data
            this.productList = this.orderInfo.productList
            this.getAddressInfoByAddressId(this.orderInfo.addressId)
            this.getUserInfoByUserId()
          } else {
            this.$message.error('订单数据错误')
          }
        })
        .catch(error => console.log(error))
    },
    // 初始化 获取用户信息
    getUserInfoByUserId() {
      order.getUserInfoByUserId(this.orderInfo.userId)
        .then(response => {
          if (response.success) {
            this.userInfo = response.data
          } else {
            this.$message.error('用户数据错误')
          }
        })
        .catch(error => console.log(error))
    },
    // 初始化 获取订单的收货地址
    getAddressInfoByAddressId(addressId) {
      order.getAddressInfoByAddressId(addressId)
        .then(response => {
          if (response.success) {
            this.addressInfo = response.data
          } else {
            this.$message.error('地址数据错误')
          }
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>

</style>
