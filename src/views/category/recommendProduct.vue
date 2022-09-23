<template>
  <div class="app-container">
    <el-table :data="productInfo" stripe style="width: 100%">
      <el-table-column label="序号" width="80">
        <template slot-scope="scope">
          {{ (current-1)*10 + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品标题" width="180"/>
      <el-table-column prop="description" label="商品简介"/>
      <el-table-column prop="price" label="商品价格" width="90"/>
      <el-table-column prop="isRecommend" label="是否推荐" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.isRecommend === 0">否</span>
          <span v-else>推荐</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="90">
        <template slot-scope="scope">
          <el-button v-if="scope.row.isRecommend === 0" type="text" @click="setRecommend(scope.row.id)">推荐</el-button>
          <el-button v-else type="text" @click="removeRecommend(scope.row.id)">取消推荐</el-button>
        </template>
      </el-table-column>
    </el-table>
  </div>

</template>

<script>
import product from '@/api/product/product'
import recommendProduct from '@/api/product/recommendProduct'
export default {
  data() {
    return {
      catId: 0, // 但前页面所属的三级分类id
      productInfo: [
        {
          id: '1571512440636690434',
          catId: 225,
          name: '\t 【全力当天发货 顺丰包邮 抢先收藏加购】iPhone 14',
          description: '【全力当天发货 顺丰包邮 抢先收藏加购】iPhone 14全网通2022新品5G手机正品国行苹果14手机官方正品iphone \n品牌 Apple/苹果 Apple\n型号 iPhone 14 \n机身颜色 红色 午夜色 星光色 蓝色 紫色 \n操作系统 iOS \nCPU品牌 Apple/苹果',
          picture: '[{"url": "https://mall-sakanal.oss-cn-hangzhou.aliyuncs.com/2022/09/18/4a3dd980a2394de9ae8da91e5922cc70iPhone 14-1.jpg", "name": "iPhone 14-1.jpg"}, {"url": "https://mall-sakanal.oss-cn-hangzhou.aliyuncs.com/2022/09/18/0f6600702dd94853928c5d8a9bd69b3biPhone 14-2.png", "name": "iPhone 14-2.png"}, {"url": "https://mall-sakanal.oss-cn-hangzhou.aliyuncs.com/2022/09/18/6a3f5dfefd5440399fe8d2dee95b82ceiPhone 14-3.jpg", "name": "iPhone 14-3.jpg"}, {"url": "https://mall-sakanal.oss-cn-hangzhou.aliyuncs.com/2022/09/18/0790a0c1238343d3984835eed4f2f7e0iPhone 14-4.jpg", "name": "iPhone 14-4.jpg"}]',
          pictureList: null,
          price: 5999,
          userId: '1571383413791789057',
          number: null
        }
      ],
      current: 0,
      pages: -1
    }
  },
  created() {
    if (this.$route.params && this.$route.params.catId) {
      this.catId = this.$route.params.catId
      this.getProductByCatId(1, this.$route.params.catId)
    }
  },
  methods: {
    // <!-- 初始化 -->
    getProductByCatId(current = 1, catId) {
      this.current = current
      product.getProductByCatId(current, catId)
        .then(response => {
          this.productInfo = response.data.records
          this.pages = response.pages
        })
        .catch(error => {
          console.log(error)
          this.$router.go(-1)
        })
    },
    setRecommend(productId) {
      this.$confirm('是否设置该商品为推荐商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        recommendProduct.setRecommend(productId)
          .then(response => {
            this.getProductByCatId(1, this.catId)
          })
          .catch(error => console.log(error))
      })
    },
    removeRecommend(productId) {
      this.$confirm('是否推荐该商品?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        recommendProduct.removeRecommend(productId)
          .then(response => {
            this.getProductByCatId(1, this.catId)
          })
          .catch(error => console.log(error))
      })
    }
  }
}
</script>

<style scoped>

</style>
