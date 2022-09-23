import request from '@/utils/request'

export default {
  addRecommendProduct(productId) {
    return request({
      url: '/recommendProduct/save',
      method: 'post',
      data: productId
    })
  },
  setRecommend(productId) {
    return request({
      url: `/recommendProduct/setRecommend/${productId}`,
      method: 'put'
    })
  },
  removeRecommend(productId) {
    return request({
      url: `/recommendProduct/removeRecommend/${productId}`,
      method: 'put'
    })
  }
}
