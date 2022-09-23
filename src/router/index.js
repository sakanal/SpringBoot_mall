import Vue from 'vue'
import Router from 'vue-router'

Vue.use(Router)

/* Layout */
import Layout from '@/layout'

/**
 * Note: sub-menu only appear when route children.length >= 1
 * Detail see: https://panjiachen.github.io/vue-element-admin-site/guide/essentials/router-and-nav.html
 *
 * hidden: true                   if set true, item will not show in the sidebar(default is false)
 * alwaysShow: true               if set true, will always show the root menu
 *                                if not set alwaysShow, when item has more than one children route,
 *                                it will becomes nested mode, otherwise not show the root menu
 * redirect: noRedirect           if set noRedirect will no redirect in the breadcrumb
 * name:'router-name'             the name is used by <keep-alive> (must set!!!)
 * meta : {
    roles: ['admin','editor']    control the page roles (you can set multiple roles)
    title: 'title'               the name show in sidebar and breadcrumb (recommend set)
    icon: 'svg-name'/'el-icon-x' the icon show in the sidebar
    breadcrumb: false            if set false, the item will hidden in breadcrumb(default is true)
    activeMenu: '/example/list'  if set path, the sidebar will highlight the path you set
  }
 */

/**
 * constantRoutes
 * a base page that does not have permission requirements
 * all roles can be accessed
 */
export const constantRoutes = [
  {
    path: '/login',
    component: () => import('@/views/login/index'),
    hidden: true
  },

  {
    path: '/404',
    component: () => import('@/views/404'),
    hidden: true
  },

  {
    path: '/',
    component: Layout,
    redirect: '/dashboard',
    children: [{
      path: 'dashboard',
      name: '首页',
      component: () => import('@/views/dashboard/index'),
      meta: { title: '首页', icon: 'dashboard' }
    }]
  },

  {
    path: '/userInfo',
    component: Layout,
    redirect: '/userInfo/list',
    name: '用户管理',
    meta: { title: '用户管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '用户列表',
        component: () => import('@/views/userInfo/list'),
        meta: { title: '用户列表', icon: 'table' }
      },
      {
        path: 'add',
        name: '添加用户',
        component: () => import('@/views/userInfo/add'),
        meta: { title: '添加用户', icon: 'tree' }
      },
      {
        path: 'edit/:id',
        name: '修改用户',
        component: () => import('@/views/userInfo/add'),
        meta: { title: '修改用户', icon: 'tree' },
        hidden: true
      }
    ]
  },
  {
    path: '/category',
    component: Layout,
    redirect: '/category/oneLevel',
    name: '类别管理',
    meta: { title: '类别管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'oneLevel',
        name: '类别管理',
        component: () => import('@/views/category/oneLevel'),
        meta: { title: '类别管理', icon: 'table' }
      },
      {
        path: 'twoLevel/:catId',
        name: '二级分类',
        component: () => import('@/views/category/twoLevel'),
        meta: { title: '二级分类', icon: 'table' },
        hidden: true
      },
      {
        path: 'recommendProduct/:catId',
        name: '商品列表',
        component: () => import('@/views/category/recommendProduct'),
        meta: { title: '商品列表', icon: 'tree' },
        hidden: true
      }
    ]
  },
  {
    path: '/product',
    component: Layout,
    redirect: '/product/list',
    name: '商品管理',
    meta: { title: '商品管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '商品列表',
        component: () => import('@/views/product/list'),
        meta: { title: '商品列表', icon: 'tree' }
      },
      {
        path: 'add',
        name: '添加商品',
        component: () => import('@/views/product/add'),
        meta: { title: '添加商品', icon: 'tree' }
      },
      {
        path: 'update/:productId',
        name: '修改商品',
        component: () => import('@/views/product/add'),
        meta: { title: '修改商品', icon: 'tree' },
        hidden: true
      },
      {
        path: 'productInfo/:productId',
        name: '商品详细',
        component: () => import('@/views/product/productInfo'),
        meta: { title: '商品详细', icon: 'tree' },
        hidden: true
      }
    ]
  },
  {
    path: '/order',
    component: Layout,
    redirect: '/order/list',
    name: '订单管理',
    meta: { title: '订单管理', icon: 'el-icon-s-help' },
    children: [
      {
        path: 'list',
        name: '订单管理',
        component: () => import('@/views/order/list'),
        meta: { title: '订单管理', icon: 'table' }
      },
      {
        path: 'orderInfo/:orderSn',
        name: '订单详细',
        component: () => import('@/views/order/orderInfo'),
        meta: { title: '订单详细', icon: 'tree' },
        hidden: true
      }
    ]
  },
  {
    path: 'external-link',
    component: Layout,
    children: [
      {
        path: 'http://localhost:3000/',
        meta: { title: '返回首页', icon: 'link' }
      }
    ]
  },

  // 404 page must be placed at the end !!!
  { path: '*', redirect: '/404', hidden: true }
]

const createRouter = () => new Router({
  // mode: 'history', // require service support
  scrollBehavior: () => ({ y: 0 }),
  routes: constantRoutes
})

const router = createRouter()

// Detail see: https://github.com/vuejs/vue-router/issues/1234#issuecomment-357941465
export function resetRouter() {
  const newRouter = createRouter()
  router.matcher = newRouter.matcher // reset router
}

export default router
