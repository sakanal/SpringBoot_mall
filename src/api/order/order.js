import request from '@/utils/request'

export default {
  pageFind(current, orderInfo) {
    return request({
      url: `/orderInfo/pageFind/${current}`,
      method: 'post',
      data: orderInfo
    })
  },
  shipProduct(orderInfo) {
    return request({
      url: '/orderInfo/update',
      method: 'put',
      data: orderInfo
    })
  },
  getOrderInfoByOrderSn(orderSn) {
    return request({
      url: `/orderInfo/info/${orderSn}`,
      method: 'get'
    })
  },
  getAddressInfoByAddressId(addressId) {
    return request({
      url: `/userAddress/info/${addressId}`,
      method: 'get'
    })
  },
  getUserInfoByUserId(userId) {
    return request({
      url: `/userInfo/info/${userId}`,
      method: 'get'
    })
  }
}
