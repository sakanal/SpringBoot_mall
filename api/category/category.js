import request from "~/utils/request";

export default {
  getOneAndTwoLevel(){
    return request({
      url: '/categoryInfo/getOneAndTwoLevel',
      method: 'get'
    })
  },
  getChildrenLevel(catId){
    return request({
      url: `/categoryInfo/getThreeLevel/${catId}`,
      method: 'get'
    })
  },
  getProductByCategoryId(current,size,catId) {
    return request({
      url: `/categoryInfo/getProductByCatId/${current}/${size}/${catId}`,
      method: 'get'
    })
  }
}
