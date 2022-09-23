<template>
  <div style="background-color: white">

    <!-- 幻灯片 开始 -->
    <div v-swiper:mySwiper="swiperOption">
      <div class="swiper-wrapper">
        <div class="swiper-slide" style="background: #040B1B;">
          <a target="_blank" href="/">
<!--            <img src="@/assets/photo/banner/94349871_p1_任尔东西南北风.png" alt="首页banner">-->
            <img src="https://m.360buyimg.com/babel/jfs/t1/194078/10/28937/143578/6311e96dE0983f3fd/83a36776494a81b3.jpg.webp" alt="首页banner">
          </a>
        </div>
        <div class="swiper-slide" style="background: #040B1B;">
          <a target="_blank" href="/">
<!--            <img src="@/assets/photo/banner/98840324_p1_现世的霓虹.png" alt="首页banner">-->
            <img src="https://m.360buyimg.com/babel/jfs/t1/59386/18/19816/187664/632c40e2E58a5ccb1/08ee49197c8a2fe8.jpg.webp" alt="首页banner">
          </a>
        </div>
        <div class="swiper-slide" style="background: #040B1B;">
          <a target="_blank" href="/">
<!--            <img src="@/assets/photo/banner/100202805_p1_兰·七夕.png" alt="首页banner">-->
            <img src="https://imgcps.jd.com/ling4/100039356516/5Lqs6YCJ5aW96LSn/5L2g5YC85b6X5oul5pyJ/p-5bd8253082acdd181d02fa40/cca4da51/cr/s/q.jpg" alt="首页banner">
          </a>
        </div>
      </div>
      <div class="swiper-pagination swiper-pagination-white"></div>
      <div class="swiper-button-prev swiper-button-white" slot="button-prev"></div>
      <div class="swiper-button-next swiper-button-white" slot="button-next"></div>
    </div>
    <!-- 幻灯片 结束 -->

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
          <section class="tac pt20">
            <a :href="/product/" title="全部商品" class="comm-btn c-btn-2">全部商品</a>
          </section>
        </div>
      </section>
    </div>
    <!-- 推荐商品 结束 -->
    <!-- 随机商品 开始 -->
    <div>
      <section class="container">
        <header class="comm-title">
          <h2 class="tac">
            <span class="c-333">热门商品</span>
          </h2>
        </header>
        <div>
          <article class="comm-course-list">
            <ul class="of" id="randomProduct">
              <li v-for="product in productList" :key="product.id">
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
    <!-- 随机商品 结束 -->

  </div>
</template>

<script>
import AppLogo from '~/components/AppLogo.vue'
import index from '@/api/index'
export default {
  layout: 'default',
  components: {
    AppLogo
  },
  data() {
    return {
      swiperOption: {
        //配置分页
        pagination: {
          el: '.swiper-pagination'//分页的dom节点
        },
        //配置导航
        navigation: {
          nextEl: '.swiper-button-next',//下一页dom节点
          prevEl: '.swiper-button-prev'//前一页dom节点
        },
        autoplay: {
          delay: 2000
        },
        speed: 800
      },
      defaultUtl: 'https://guli-edu-sakanal.oss-cn-hangzhou.aliyuncs.com/avatar/string/2022/09/13/ef45e4e3e2764a77a3925aa4ce1f5e31multipartFile.png',
      productList: {},
      recommendProductList: {}
    }
  },
  created() {
    this.getRandomProductList()
    this.getRandomRecommendProductList()
  },
  methods: {
    getRandomProductList() {
      index.getRandomProductList()
        .then(response => {
          this.productList = response.data.data
        })
    },
    getRandomRecommendProductList() {
      index.getRandomRecommendProductList()
        .then(response => {
          this.recommendProductList = response.data.data
        })
    }
  }
}
</script>

<style>
</style>

