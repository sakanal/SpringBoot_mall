import request from "@/utils/request";

export default {
  createOrder(orderInfo){
    return request({
      url: '/orderInfo/save',
      method: 'post',
      data: orderInfo
    })
  },
  getOrderListByUserId(userId){
    return request({
      url: `/orderInfo/listOrder/${userId}`,
      method: 'post'
    })
  },
  getOrderInfoByOrderSn(orderSn){
    return request({
      url: `/orderInfo/info/${orderSn}`,
      method: 'get'
    })
  },
  removeOrderInfoByOrderId(orderSns){
    return request({
      url: '/orderInfo/delete',
      method: 'delete',
      data: orderSns
    })
  },
  confirmProduct(orderInfo){
    return request({
      url: '/orderInfo/update',
      method: 'put',
      data: orderInfo
    })
  }
}
