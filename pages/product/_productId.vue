<template>

  <el-container>
    <el-header></el-header>
    <el-main>
      <el-row :gutter="20">
        <el-col :span="6" :offset="3">
          <div class="grid-content bg-purple">
            <div class="block">
              <el-carousel>
                <template v-if="productInfo.pictureList === null">
                  <el-carousel-item>
                    <img :src="defaultUtl" class="img-responsive" :alt="productInfo.name">
                  </el-carousel-item>
                </template>
                <template v-else>
                  <el-carousel-item v-for="picture in productInfo.pictureList" :key="picture.name">
                    <el-image :src="picture.url" fit="cover"/>
                  </el-carousel-item>
                </template>
              </el-carousel>
            </div>
          </div>
        </el-col>
        <el-col :span="12">
          <div class="grid-content bg-purple" style="white-space:pre-wrap;">
            <el-descriptions title="商品信息" :colon="false" :column="1">
              <el-descriptions-item>
                <span style="font-size: 20px">{{productInfo.name}}</span>
              </el-descriptions-item>
              <el-descriptions-item>{{productInfo.description}}</el-descriptions-item>
              <el-descriptions-item><span style="font-size: 20px;color: tomato">￥ {{productInfo.price}}</span></el-descriptions-item>
            </el-descriptions>
          </div>
          <div>
            <el-input-number v-model="cartInfo.number" controls-position="right" :min="1"/>
            <el-button type="warning" @click="addCart">加入购物车</el-button>
            <el-button type="danger" @click="openBuyDialog">立即购买</el-button>
          </div>
        </el-col>
      </el-row>
      <el-dialog title="立即购买" center :visible.sync="buyDialogTableVisible">
        <el-table :data="orderProduct">
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
          <el-button type="danger" @click="nowBuy">下 单</el-button>
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

    </el-main>

    <!-- 推荐商品 开始 -->
    <div>
      <section class="container">
        <header class="comm-title">
          <h2 class="tac">
            <span class="c-333">猜你喜欢</span>
          </h2>
        </header>
        <div>
          <article class="comm-course-list">
            <ul class="of" id="randomRecommendProduct">
              <li v-for="product in recommendProductList" :key="product.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <!--                    TODO-->
                    <img :src="product.pictureList[0].url" class="img-responsive" :alt="product.name">
                    <div class="cc-mask">
                      <a :href="'/product/'+product.id" title="查看详细" class="comm-btn c-btn-1">查看详细</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/product/'+product.id" :title="product.name" class="course-title fsize18 c-333">{{product.name}}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                      <span class="fr jgTag bg-green" v-if="Number(product.price) === 0">
                        <i class="c-fff fsize12 f-fA">免费</i>
                      </span>
                    <span class="fr jgTag bg-green" v-else>
                        <i class="c-fff fsize12 f-fA">￥{{product.price}}</i>
                      </span>
                    <!--                    <span class="fl jgAttr c-ccc f-fA">-->
                    <!--                        <i class="c-999 f-fA">{{course.count}}人学习</i>-->
                    <!--                        |-->
                    <!--                        <i class="c-999 f-fA">9634评论</i>-->
                    <!--                      </span>-->
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"></div>
          </article>
        </div>
      </section>
    </div>
    <!-- 推荐商品 结束 -->
  </el-container>
</template>

<script>
import product from "~/api/product/product";
import index from "~/api";
import cart from "@/api/cart/cart";
import order from "@/api/order/order"
import cookie from "js-cookie";
import userInfo from "@/api/userInfo/userInfo";

export default {
  data() {
   return {
     defaultUtl: 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png',
     isLogin: false,
     userInfo: {},
     productInfo: {},
     userAddressList: {},
     isCheckUserAddress: false,
     userAddress: {},
     cartInfo: {
       userId: '',
       productId: '',
       number: 1
     },
     recommendProductList: {},
     buyDialogTableVisible: false,
     userAddressDialogTableVisible: false,
     orderProduct: [
       {
         userId: '',
         productId: '',
         productNumber: 1,
         unitPrice: 0,
         totalPrice: 0,
         addressId: ''
       }
     ]
   }
  },
  created() {
    this.getUserInfo()
    if (this.$route.params && this.$route.params.productId){
      this.productId = this.$route.params.productId
      this.cartInfo.productId = this.productId
      this.getUserAddressListByUserId()
      this.getProductById(this.productId)
      this.getRandomRecommendProductList()
    } else {
      this.$message.error('暂无数据')
      this.$router.go(-1)
    }
  },
  methods: {
    // 初始化 获取cookie中储存的用户信息
    getUserInfo() {
      const userInfo = cookie.get('userInfo')
      if (userInfo){
        this.userInfo = JSON.parse(userInfo);
        this.isLogin = true
        this.cartInfo.userId = this.userInfo.id
      }
    },
    // 初始化 获取根据商品id获取商品
    getProductById(productId) {
      product.getProductById(productId)
        .then(response => {
          this.productInfo = response.data.data
        })
        .catch(error => console.log(error))
    },
    // 初始化 根据用户id获取所有收货地址
    getUserAddressListByUserId() {
      userInfo.getUserAddressListByUserId(this.userInfo.id)
        .then(response => {
          this.userAddressList = response.data.data
          console.log(this.userAddressList)
        })
        .catch(error => console.log(error))
    },
    // 初始化 获取随机推荐商品
    getRandomRecommendProductList() {
      index.getRandomRecommendProductList()
        .then(response => {
          this.recommendProductList = response.data.data
        })
    },
    // 添加购物车
    addCart() {
      if (this.isLogin){
        cart.haveProductByUserAndProductId(this.userInfo.id,this.productId)
          .then(response => {
            if (response.data.success){
              // 购物车中无该商品 add
              cart.addCart(this.cartInfo)
                .then(response => {
                  this.$message.success('加购成功')
                })
                .catch(error => console.log(error))
            }else {
              // 购物车中有该商品 update
              cart.updateCart(this.cartInfo)
                .then(response => {
                  this.$message.success('加购成功')
                })
                .catch(error => console.log(error))
            }
          })
          .catch(error => console.log(error))
      }else {
        this.$message.error('请先登录')
        this.$router.push({path:'/login'})
      }
    },
    // 开启购买商品模态框，为参数赋值
    openBuyDialog(){
      this.orderProduct=[]
      this.orderProduct = [this.productInfo]
      this.orderProduct[0].userId = this.userInfo.id
      this.orderProduct[0].productNumber = this.cartInfo.number
      this.orderProduct[0].productId = this.productInfo.id
      this.orderProduct[0].unitPrice = this.productInfo.price
      this.orderProduct[0].totalPrice = this.productInfo.price * this.cartInfo.number
      this.buyDialogTableVisible = true
    },
    // 根据收货地址id，获取详细的收货地址信息，并赋值
    checkUserAddress(userAddressId){
      this.userAddressDialogTableVisible = false
      userInfo.getUserAddressById(userAddressId)
        .then(response => {
          if (response.data.success){
            this.userAddress = [response.data.data]
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
    nowBuy() {
      if (this.isLogin){
        if (this.isCheckUserAddress){
          this.orderProduct[0].addressId = this.userAddress[0].id
          console.log(this.orderProduct)
          console.log(this.userAddress)
          order.createOrder(this.orderProduct)
            .then(response => {
              if(response.data.success){
                this.$message.success('下单成功')
                this.$router.push({path: '/order'})
              }else {
                this.$message.error(response.data.message)
              }
            })
            .catch(error => console.log(error))
        }else {
          this.$message.error('请先选择收货地址')
        }
      }else {
        this.$message.error('请先登录')
        this.$router.push({path:'/login'})
      }
    }
  }
}
</script>

<style scoped>

</style>
