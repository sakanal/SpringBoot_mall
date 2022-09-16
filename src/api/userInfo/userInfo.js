import request from '@/utils/request'

export default {
  addUser(userInfo) {
    return request({
      url: `/userInfo/addUser`,
      method: 'post',
      data: userInfo
    })
  },
  pageUserInfoFind(current, userInfoQuery) {
    return request({
      url: `/userInfo/pageFind/${current}`,
      method: 'post',
      data: userInfoQuery
    })
  },
  removeUserById(ids) {
    return request({
      url: '/userInfo/delete',
      method: 'delete',
      data: ids
    })
  },
  getUserInfoById(userId) {
    return request({
      url: `/userInfo/info/${userId}`,
      method: 'get'
    })
  },
  updateUserInfo(userInfo) {
    return request({
      url: '/userInfo/update',
      method: 'put',
      data: userInfo
    })
  }
}
