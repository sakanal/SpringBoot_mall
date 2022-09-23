import request from "@/utils/request";

export default {
  getUserInfoByUserId(userId){
    return request({
      url: `/userInfo/info/${userId}`,
      method: 'get'
    })
  },
  updateUserInfo(userInfo){
    return request({
      url: '/userInfo/update',
      method: 'put',
      data: userInfo
    })
  },
  updatePassword(updatePasswordForm){
    return request({
      url: '/userInfo/updatePassword',
      method: 'put',
      data: updatePasswordForm
    })
  },
  addUserAddress(userAddress){
    return request({
      url: '/userAddress/save',
      method: 'post',
      data: userAddress
    })
  },
  updateUserAddress(userAddress){
    return request({
      url: '/userAddress/update',
      method: 'put',
      data: userAddress
    })
  },
  getUserAddressListByUserId(userId){
    return request({
      url: `/userAddress/list/${userId}`,
      method: 'get'
    })
  },
  getUserAddressById(userAddressId){
    return request({
      url: `/userAddress/info/${userAddressId}`,
      method: 'get'
    })
  },
  removeUserAddressById(ids) {
    return request({
      url: '/userAddress/delete',
      method: 'delete',
      data: ids
    })
  }

}
