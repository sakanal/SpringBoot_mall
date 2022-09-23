import request from "~/utils/request";

export default {
  getProductById(productId) {
    return request({
      url: `/productInfo/info/${productId}`,
      method: 'get'
    })
  },
  searchProductByName(current, productQuery) {
    return request({
      url: `/productInfo/pageFind/${current}`,
      method: 'post',
      data: productQuery
    })
  }
}
