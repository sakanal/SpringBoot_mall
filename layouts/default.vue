<template>
  <div class="in-wrap">
    <!-- 公共头引入 -->
    <header id="header">
      <section class="container">
        <div class="h-r-nsl">
          <ul class="nav">
            <router-link to="/" tag="li" active-class="current" exact>
              <a>首页</a>
            </router-link>
            <router-link to="/product" tag="li" active-class="current">
              <a>商品</a>
            </router-link>
            <router-link to="/cart" tag="li" active-class="current">
              <a>购物车</a>
            </router-link>
            <router-link to="/order" tag="li" active-class="current">
              <a>订单中心</a>
            </router-link>
          </ul>
          <!-- / nav -->
          <ul class="h-r-login">
<!--            未登录-->
            <li v-if="!loginInfo.id" id="no-login">
              <a href="/login" title="登录">
                <em class="icon18 login-icon">&nbsp;</em>
                <span class="vam ml5">登录</span>
              </a>
              |
              <a href="/register" title="注册">
                <span class="vam ml5">注册</span>
              </a>
            </li>
<!--            已登录-->
            <li v-if="loginInfo.id" id="is-login-two" class="h-r-user">
              <a href="#" title>
<!--                <span class="vam disIb" id="userName">{{loginInfo.userName}}</span>-->
                <el-dropdown>
                  <span class="el-dropdown-link">
                    {{loginInfo.userName}}
                    <i class="el-icon-arrow-down el-icon--right"/>
                  </span>
                  <el-dropdown-menu slot="dropdown">
                    <el-dropdown-item>
                      <el-link href="/userInfo" :underline="false">个人中心</el-link>
                    </el-dropdown-item>
                    <el-dropdown-item divided>
                      <el-button type="text" @click="logout()">退出登录</el-button>
                    </el-dropdown-item>
                  </el-dropdown-menu>
                </el-dropdown>
              </a>
            </li>
            <!-- /未登录显示第1 li；登录后显示第2，3 li -->
          </ul>
        </div>
        <aside class="mw-nav-btn">
          <div class="mw-nav-icon"></div>
        </aside>
        <div class="clear"></div>
      </section>
    </header>

    <!-- /公共头引入 -->
    <nuxt/>

    <!-- 公共底引入 -->
    <footer id="footer">
      <section class="container">
        <div class>
          <h4 class="hLh30">
            <span class="fsize18 f-fM c-999">友情链接</span>
          </h4>
          <ul class="of flink-list">
            <li>
              <a href="http://www.baidu.com/" title="百度" target="_blank">百度</a>
            </li>
          </ul>
          <div class="clear"></div>
        </div>
        <div class="b-foot">
          <section class="fl col-7">
            <section class="mr20">
              <section class="b-f-link">
                <a href="#" title="关于我们" target="_blank">关于我们</a>|
                <a href="#" title="联系我们" target="_blank">联系我们</a>|
                <a href="#" title="帮助中心" target="_blank">帮助中心</a>|
                <span>服务热线：000-00000000(北京) 0000-0000000(深圳)</span>
                <span>Email：sakanal9527@gmail.com</span>
              </section>
              <section class="b-f-link mt10">
                <span>©三人共同开发</span>
              </section>
            </section>
          </section>
          <aside class="fl col-3 tac mt15">
            <section class="gf-tx">
              <span>
                <img src="@/assets/img/wx-icon.png" alt>
              </span>
            </section>
            <section class="gf-tx">
              <span>
                <img src="@/assets/img/wb-icon.png" alt>
              </span>
            </section>
          </aside>
          <div class="clear"></div>
        </div>
      </section>
    </footer>
    <!-- /公共底引入 -->
  </div>
</template>
<script>
import "~/assets/css/reset.css";
import "~/assets/css/theme.css";
import "~/assets/css/global.css";
import "~/assets/css/web.css";
import cookie from "js-cookie";

export default {
  data() {
    return {
      token: "",
      loginInfo: {
        id: '',
        userName: ''
      },
      productName: ''
    };
  },
  created() {
    this.showInfo();
  },
  methods: {
    logout() {
      //清空cookie值
      cookie.remove("token")
      cookie.remove("userInfo")
      //跳转页面到登录
      this.$router.go(0)
      // this.$router.push({ path: "/" });
    },
    //创建方法从cookie中获取信息
    showInfo() {
      //从cookie中获取信息
      const userStr = cookie.get("userInfo");
      //转字符串转换成json对象(js对象)
      if (userStr) {
        this.loginInfo = JSON.parse(userStr);
        console.log(this.loginInfo)
      }
    }
  },
};
</script>
