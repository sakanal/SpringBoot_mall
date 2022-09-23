<template>
  <el-container style="min-height: 500px">
    <el-main>

                <aside class="h-r-search-main">
                  <form action="#" method="post">
                    <label class="h-r-s-box">
                      <input type="text" placeholder="搜索商品" name="searchProductName" v-model="searchProductName">
                      <button type="submit" class="s-btn" @click.prevent="searchProduct()">
                        <em class="icon18">&nbsp;</em>
                      </button>
                    </label>
                  </form>
                </aside>
      <!-- 商品类别 开始 -->
      <el-row :gutter="24">
        <el-col :span="20" :offset="2">
          <hr>
          <el-link  :underline="false" @click="getRandomProductList">
            <span style="margin-right: 20px">全部</span>
          </el-link>
          <template v-for="oneCategory in oneAndTwoCategoryInfoList">
            <el-link  :underline="false" :key="oneCategory.id" @click="getTwoLevel(oneCategory.catId)">
              <span style="margin-right: 20px">{{oneCategory.name}}</span>
            </el-link>
          </template>
          <hr>
          <template v-if="twoCategoryInfoList.length>0">
            <el-link  :underline="false" @click="getProductByCategoryId(1,20,twoCategoryInfoList[0].parentCid)">
              <span style="margin-right: 20px">全部</span>
            </el-link>
            <template v-for="twoCategory in twoCategoryInfoList">
              <el-link  :underline="false" :key="twoCategory.id" @click="getChildrenLevel(2,twoCategory.catId)">
                <span style="margin-right: 20px">{{twoCategory.name}}</span>
              </el-link>
            </template>
            <hr>
          </template>
          <template v-if="threeCategoryInfoList.length>0">
            <el-link  :underline="false" @click="getProductByCategoryId(1,20,threeCategoryInfoList[0].parentCid)">
              <span style="margin-right: 20px">全部</span>
            </el-link>
            <template v-for="threeCategory in threeCategoryInfoList">
              <el-link  :underline="false" :key="threeCategory.id" @click="getProductByCategoryId(1,20,threeCategory.catId)">
                <span style="margin-right: 20px">{{threeCategory.name}}</span>
              </el-link>
            </template>
            <hr>
          </template>
        </el-col>
      </el-row>
      <!-- 商品类别 结束 -->
      <!-- 商品列表 开始 -->
      <div>
        <section class="container">
          <header class="comm-title">

          </header>
          <div>
            <article class="comm-course-list">
              <ul class="of" id="randomProduct">
                <li v-for="product in productInfoList" :key="product.id">
                  <div class="cc-l-wrap">
                    <section class="course-img">
                      <!--                    TODO-->
                      <template v-if="product.pictureList === null">
                        <img :src="defaultUtl" class="img-responsive" :alt="product.name">
                      </template>
                      <template v-else>
                        <img :src="product.pictureList[0].url" class="img-responsive" :alt="product.name">
                      </template>
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
                    </section>
                  </div>
                </li>
              </ul>
              <div class="clear"></div>
            </article>
          </div>
        </section>

        <template v-if="isSearch">
          <el-pagination
            style="padding: 30px 0;text-align: center"
            layout="total, prev, pager, next, jumper"
            :page-count="pages"
            :total="total"
            @current-change="searchProduct"
          />
        </template>
      </div>
      <!-- 商品列表 结束 -->
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
                      <template v-if="product.pictureList === null">
                        <img :src="defaultUtl" class="img-responsive" :alt="product.name">
                      </template>
                      <template v-else>
                        <img :src="product.pictureList[0].url" class="img-responsive" :alt="product.name">
                      </template>
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
    </el-main>
  </el-container>
</template>

<script>
import product from "~/api/product/product";
import category from "~/api/category/category";
import index from "~/api";
export default {
  data() {
    return {
      oneAndTwoCategoryInfoList: [],
      twoCategoryInfoList: [],
      threeCategoryInfoList: [],
      productInfoList: [],
      recommendProductList: {},
      isSearch: false,
      current: 0,
      pages: 0,
      total: 0,
      defaultUtl: 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png',
      searchProductName: '',
      selectCatId: undefined
    }
  },
  created() {
    this.getOneAndTwoLevel()
    this.getRandomProductList()
    this.getRandomRecommendProductList()
  },
  methods: {
    // 初始化 获取一二级类别
    getOneAndTwoLevel() {
      category.getOneAndTwoLevel()
        .then(response => {
          this.oneAndTwoCategoryInfoList = response.data.data
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
    // 初始化 获取随机商品
    getRandomProductList() {
      this.searchProductName = ''
      this.isSearch = false
      this.selectCatId = undefined
      index.getRandomProductList()
        .then(response => {
          this.productInfoList = response.data.data
        })
    },
    // 根据一级类别id检索获取二级类别 获取具体商品
    getTwoLevel(oneLevelId) {
      this.searchProductName = ''
      this.selectCatId = oneLevelId
      this.twoCategoryInfoList = []
      this.threeCategoryInfoList = []
      for (let i = 0; i < this.oneAndTwoCategoryInfoList.length; i++) {
        if (oneLevelId === this.oneAndTwoCategoryInfoList[i].catId){
          this.twoCategoryInfoList = this.oneAndTwoCategoryInfoList[i].children
          break
        }
      }
      this.getProductByCategoryId(1,20,oneLevelId)
    },
    // 根据二级类别id 获取三级类别 获取具体商品
    getChildrenLevel(level,catId) {
      this.searchProductName = ''
      this.selectCatId = catId
      if (level === 2){
        category.getChildrenLevel(catId)
          .then(response => {
            this.threeCategoryInfoList = response.data.data.children
          })
          .catch(error => console.log(error))
      }
      this.getProductByCategoryId(1,20,catId)
    },
    // 根据类别id 获取具体商品
    getProductByCategoryId(current=1, size=20, catId) {
      this.selectCatId = catId
      this.current = current
      this.isSearch = true
      category.getProductByCategoryId(current,size,catId)
        .then(response => {
          if (response.data.success){
            this.productInfoList = response.data.data.records
            this.pages = response.data.data.pages
            this.total = response.data.data.total
          }else {
            this.$message.error(response.data.message)
          }
        })
        .catch(error => console.log(error))
    },
    searchProduct(current = 1) {
      this.current = current
      this.isSearch = true
      const productQuery = {
        name: this.searchProductName,
        catId: this.selectCatId
      }
      product.searchProductByName(current,productQuery)
        .then(response => {
          console.log(response)
          if (response.data.success){
            console.log(response)
            this.productInfoList = response.data.data.records
            this.pages = response.data.data.pages
            this.total = response.data.data.total
          }else {
            this.$message.error(response.data.message)
          }
        })
        .catch(error => console.log(error))
    }
  }
}
</script>

<style scoped>
.h-r-s-box .s-btn{
  background:0 0;
  border:none;
  position:absolute;
  right:610px;
  top:0;
  width:30px;
  height:30px;
  cursor:pointer
}
.h-r-search-main{
  margin:10px 0 0 700px
}
</style>
