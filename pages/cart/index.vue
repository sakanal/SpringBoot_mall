<template>
  <el-container style="min-height: 500px">
    <el-main>

      <el-row :gutter="24">
        <el-col :span="20" :offset="2">
          <el-button type="danger" @click="openBuyDialogTable">购 买</el-button>
          <el-table ref="multipleTable" :data="productList" tooltip-effect="dark" style="width: 100%" @selection-change="handleSelectionChange">
            <el-table-column type="selection" width="55"/>
            <el-table-column prop="name" label="商品标题"/>
            <el-table-column prop="pictureList" label="商品图片" width="150">
              <template slot-scope="scope">
                <el-image :src="scope.row.pictureList[0].url" style="width: 100px; height: 100px"/>
              </template>
            </el-table-column>
            <el-table-column prop="price" label="单价" width="150"/>
            <el-table-column prop="number" label="数量" width="150">
              <template slot-scope="scope">
                <el-input-number style="width: 100px" controls-position="right" size="small" v-model="scope.row.number" @change="changeProductNumber(scope.row.id,scope.row.number)" :min="1" />
              </template>
            </el-table-column>
            <el-table-column label="总价" width="150">
              <template slot-scope="scope">
                {{scope.row.price * scope.row.number}}
              </template>
            </el-table-column>
            <el-table-column label="操作" width="150">
              <template slot-scope="scope">
                <el-button
                  size="mini" @click="viewProduct(scope.row.id)">查看</el-button>
                <el-button
                  size="mini"
                  type="danger" @click="removeProduct(scope.row)">删除</el-button>
              </template>
            </el-table-column>
          </el-table>
        </el-col>
      </el-row>
    </el-main>

    <el-dialog title="立即购买" center :visible.sync="buyDialogTableVisible">
      <el-table :data="multipleSelection">
        <el-table-column  property="name" label="商品名称" width="150" :show-overflow-tooltip="true"/>
        <el-table-column property="productNumber" label="商品数量"/>
        <el-table-column property="unitPrice" label="商品单价"/>
        <el-table-column property="totalPrice" label="商品总价"/>
      </el-table>
      <el-button type="text" @click="userAddressDialogTableVisible = true">选择地址</el-button>
      <template v-if="isCheckUserAddress">
        <el-table :data="userAddress">
          <el-table-column property="name" label="收货人姓名" width="120"/>
          <el-table-column property="phone" label="收货人电话" width="120"/>
          <el-table-column property="address" label="收货地址"/>
          <el-table-column width="120">
            <template slot-scope="scope">
              <el-button
                size="mini"
                type="danger"
                @click="removeUserAddress(scope.row.id)">删除</el-button>
            </template>
          </el-table-column>
        </el-table>
      </template>
      <div slot="footer" class="dialog-footer">
        <el-button @click="buyDialogTableVisible = false">取 消</el-button>
        <el-button type="danger" @click="multipleBuy">下 单</el-button>
      </div>

      <el-dialog title="收货地址" :visible.sync="userAddressDialogTableVisible" append-to-body>
        <el-table :data="userAddressList">
          <el-table-column property="name" label="收货人姓名" width="120"/>
          <el-table-column property="phone" label="收货人电话" width="120"/>
          <el-table-column property="address" label="收货地址"/>
          <el-table-column width="120">
            <template slot-scope="scope">
              <el-button
                size="mini"
                @click="checkUserAddress(scope.row.id)">选择</el-button>
            </template>
          </el-table-column>
        </el-table>
      </el-dialog>
    </el-dialog>
  </el-container>
</template>

<script>
import cookie from "js-cookie";
import cart from "@/api/cart/cart";
import userInfo from "@/api/userInfo/userInfo";
import order from "@/api/order/order";

export default {
  data() {
    return{
      isSelect: false,
      isLogin: false,
      userInfo: {},
      productList: [],
      multipleSelection: [],
      userAddressList: {},
      isCheckUserAddress: false,
      userAddress: {},
      buyDialogTableVisible: false,
      userAddressDialogTableVisible: false,
    }
  },
  created() {
    this.getUserInfo()
    this.getUserAddressListByUserId()
    this.getCartInfoByUserId(this.userInfo.id)
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
    // 初始化 根据用户id获取购物车内的所有商品
    getCartInfoByUserId(userId) {
      cart.getCartInfoByUserId(userId)
        .then(response => {
          this.productList = response.data.data
        })
        .catch(error => console.log(error))
    },
    // 初始化 根据用户id获取所有收货地址
    getUserAddressListByUserId() {
      userInfo.getUserAddressListByUserId(this.userInfo.id)
        .then(response => {
          this.userAddressList = response.data.data
        })
        .catch(error => console.log(error))
    },
    // 修改购物车内的商品数量
    changeProductNumber(productId,number){
      const cartInfo = {
        productId: productId,
        number: number,
        userId: this.userInfo.id
      }
      cart.updateInCart(cartInfo)
        .then(response => console.log(response))
        .catch(error => console.log(error))
    },
    // 查看商品详细页面
    viewProduct(productId){
      this.$router.push({path: `/product/${productId}`})
    },
    // 删除购物车内的商品
    removeProduct(cartInfo){
      cartInfo.productId = cartInfo.id
      cartInfo.userId = this.userInfo.id
      this.$confirm('此操作将从购物车中删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        cart.removeProductByProductId(cartInfo)
          .then(response => {
            if (response.data.success){
              this.$message.success('删除成功')
              this.getCartInfoByUserId(this.userInfo.id)
            }else {
              this.$message.error('删除失败')
            }
          })
          .catch(error => console.log(error))
      })
    },
    // 选择要购买的商品，允许多选
    handleSelectionChange(val) {
      this.multipleSelection = val;
      this.isSelect = this.multipleSelection.length > 0;
    },
    // 根据收货地址id，获取详细的收货地址信息，并赋值
    checkUserAddress(userAddressId){
      this.userAddressDialogTableVisible = false
      userInfo.getUserAddressById(userAddressId)
        .then(response => {
          if (response.data.success){
            this.userAddress = [response.data.data]
            this.multipleSelection[0].addressId = this.userAddress[0].id
            this.isCheckUserAddress = true
          }else {
            this.$message.error(response.data.message)
          }
        })
        .catch(error => console.log(error))
    },
    // 取消选择的收货地址
    removeUserAddress(userAddressId){
      this.userAddress = {}
      this.isCheckUserAddress = false
    },
    // 下单
    openBuyDialogTable() {
      if (this.isSelect){
        for (let i = 0; i < this.multipleSelection.length; i++) {
          this.multipleSelection[i].userId = this.userInfo.id
          this.multipleSelection[i].productId = this.multipleSelection[i].id
          this.multipleSelection[i].productNumber = this.multipleSelection[i].number
          this.multipleSelection[i].unitPrice = this.multipleSelection[i].price
          this.multipleSelection[i].totalPrice = this.multipleSelection[i].price * this.multipleSelection[i].number
        }
        this.buyDialogTableVisible = true
      }else {
        this.$message.error('请选择需要购买的商品')
      }
    },
    multipleBuy(){
      if (this.isCheckUserAddress){
        order.createOrder(this.multipleSelection)
          .then(response => {
            if (response.data.success){
              this.$message.success('购买成功')
              this.$router.push({path: '/order'})
            }else {
              this.$message.error('购买失败')
            }
          })
      }else {
       this.$message.error('请选择收货地址')
      }
    }
  }
}
</script>

<style scoped>

</style>
