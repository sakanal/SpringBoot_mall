import request from "~/utils/request";

export default {
  getRandomProductList() {
    return request({
      url: `/productInfo/getRandomProduct/20`,
      method:'post'
    })
  } ,
  getRandomRecommendProductList() {
    return request({
      url: `/recommendProduct/getRecommend/4`,
      method:'get'
    })
  }
}
