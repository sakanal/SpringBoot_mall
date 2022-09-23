import request from '@/utils/request'

export default {
  // 根据商品类型id获取有关
  getProductByCatId(current, catId) {
    return request({
      url: `/productInfo/getByCategoryId/${current}/${catId}`,
      method: 'get'
    })
  },
  getProductInfoById(productId) {
    return request({
      url: `/productInfo/info/${productId}`,
      method: 'get'
    })
  },
  // 添加商品
  addProduct(productInfo) {
    return request({
      url: '/productInfo/save',
      method: 'post',
      data: productInfo
    })
  },
  // 条件查询商品，分页
  getPageProductList(current, productQuery) {
    return request({
      url: `/productInfo/pageFind/${current}`,
      method: 'post',
      data: productQuery
    })
  },
  // 修改商品
  updateProduct(productInfo) {
    return request({
      url: '/productInfo/update',
      method: 'put',
      data: productInfo
    })
  },
  // 删除商品
  removeProductById(productIds) {
    return request({
      url: '/productInfo/delete',
      method: 'delete',
      data: productIds
    })
  }
}
