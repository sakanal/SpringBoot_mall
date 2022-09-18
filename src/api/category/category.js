import request from '@/utils/request'

export default {
  getAllCategory() {
    return request({
      url: '/categoryInfo/list/tree',
      method: 'get'
    })
  },
  getOneAndTwoLevel() {
    return request({
      url: '/categoryInfo/getOneAndTwoLevel',
      method: 'get'
    })
  },
  addCategory(category) {
    return request({
      url: '/categoryInfo/save',
      method: 'post',
      data: category
    })
  },
  getCategoryById(catId) {
    return request({
      url: `/categoryInfo/info/${catId}`,
      method: 'get'
    })
  },
  getFatherAndChildrenLevel(catId) {
    return request({
      url: `/categoryInfo/getThreeLevel/${catId}`,
      method: 'get'
    })
  },
  getCategoryPath(catId) {
    return request({
      url: `/categoryInfo/getPath/${catId}`,
      method: 'get'
    })
  },
  updateCategory(category) {
    return request({
      url: '/categoryInfo/update',
      method: 'put',
      data: category
    })
  },
  removeCategoryById(ids) {
    return request({
      url: '/categoryInfo/delete',
      method: 'delete',
      data: ids
    })
  }
}
