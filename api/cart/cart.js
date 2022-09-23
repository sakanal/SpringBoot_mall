import request from "@/utils/request";

export default {
  haveProductByUserAndProductId(userId,productId){
    return request({
      url: `/cartInfo/count/${userId}/${productId}`,
      method: 'get'
    })
  },
  addCart(cartInfo){
    return request({
      url: '/cartInfo/save',
      method: 'post',
      data: cartInfo
    })
  },
  updateCart(cartInfo){
    return request({
      url: '/cartInfo/update',
      method: 'put',
      data: cartInfo
    })
  },
  updateInCart(cartInfo){
    return request({
      url: '/cartInfo/updateInCart',
      method: 'put',
      data: cartInfo
    })
  },
  getCartInfoByUserId(userId){
    return request({
      url:  `/cartInfo/getCateInfoByUserId/${userId}`,
      method:'get'
    })
  },
  removeProductByProductId(cartInfo){
    return request({
      url: '/cartInfo/delete',
      method: 'delete',
      data: cartInfo
    })
  }

}
