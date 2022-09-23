import request from '@/utils/request'

export default {
  //根据邮箱发送短信
  getMobile(email){
    return request({
      url: `/simpleUser/getMsCode`,
      method: 'post',
      data: {email: email}
    })
  },
  //用户注册
  register(userInfoForm){
    return request({
      url: `/simpleUser/register`,
      method: 'post',
      data: userInfoForm
    })
  }
}
