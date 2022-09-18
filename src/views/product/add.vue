<template>
  <div class="app-container">
    <el-form ref="productInfo" :model="productInfo" :rules="productInfoRules" label-width="80px">
      <el-form-item label="商品名称" prop="name">
        <el-input v-model="productInfo.name" placeholder="请输入商品名称" />
      </el-form-item>
      <el-form-item label="商品类型" prop="catId">
        <el-cascader v-model="catId" :options="categoryList" :props="categoryProps" :show-all-levels="false" placeholder="请选择商品类型" />
      </el-form-item>
      <el-form-item label="商品描述">
        <el-input v-model="productInfo.description" type="textarea" placeholder="请输入商品描述" maxlength="200" show-word-limit />
      </el-form-item>
      <el-form-item label="商品图片">
        <el-upload
          class="upload-demo"
          action="http://localhost:8080/oss/product/"
          :before-remove="beforeRemovePicture"
          :on-remove="removePicture"
          :before-upload="compressImage"
          :on-preview="clickPicture"
          :on-success="uploadSuccess"
          :on-exceed="overPictureSize"
          multiple
          :limit="5"
          name="multipartFile"
          list-type="picture"
          :file-list="fileList"
        >
          <el-button size="small" type="primary">点击上传</el-button>
          <div slot="tip" class="el-upload__tip">只能上传jpg/png文件，最多上传5张，且每张不超过20MB</div>
        </el-upload>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-input-number v-model="productInfo.price" :min="0" label="描述文字" />
      </el-form-item>
      <el-form-item label="商家">
        <el-select v-model="productInfo.userId" placeholder="请选择">
          <el-option
            v-for="merchant in merchantList"
            :key="merchant.id"
            :label="merchant.userName"
            :value="merchant.id"
            filterable
          />
        </el-select>
      </el-form-item>
      <el-form-item>
        <template v-if="isAdd === true">
          <el-button type="primary" @click="addProductInfo('productInfo')">立即创建</el-button>
        </template>
        <template v-else>
          <el-button type="primary" @click="updateProductInfo('productInfo')">保存</el-button>
        </template>
        <el-button>重置</el-button>
      </el-form-item>
    </el-form>
  </div>
</template>

<script>
import category from '@/api/category/category'
import product from '@/api/product/product'
import userInfo from '@/api/userInfo/userInfo'

import * as imageConversion from 'image-conversion'
export default {
  data() {
    const validateCatId = (rule, value, callback) => {
      if (value === 0) {
        callback(new Error('请选择商品类型'))
      } else {
        callback()
      }
    }
    return {
      productInfo: {
        pictureList: [], // 商品图片
        price: 0 // 商品价格
      },
      isAdd: true,
      isInit: true,
      isUpload: false,
      catId: 0,
      productInfoRules: {
        name: [
          { required: true, message: '请输入商品名称', trigger: 'blur' },
          { min: 1, max: 50, message: '长度在 1 到 50 个字符', trigger: 'blur' }
        ],
        catId: [
          // { required: true, message: '请选择商品类型', trigger: 'blur' },
          // { validator: validateCatId, trigger: 'blur' }
        ]
      },
      merchantList: {},
      categoryList: [],
      categoryProps: {
        label: 'name',
        value: 'catId'
      },
      fileList: []

    }
  },
  watch: {
    catId(value) {
      this.productInfo.catId = value[2]
      this.$refs.productInfo.validateField('catId')
    }
  },
  created() {
    this.getAllCategory()
    this.getMerchantList()
    if (this.$route.params && this.$route.params.productId) {
      this.productInfo.id = this.$route.params.productId
      this.getProductInfoById(this.$route.params.productId)
      // this.catId = [1, 22, 165]
      this.isInit = false
      this.isAdd = false
    }
  },
  methods: {
    // <!-- 初始化 -->
    getAllCategory() {
      category.getAllCategory()
        .then(response => {
          this.categoryList = this.getTreeData(response.data)
        })
        .catch(error => console.log(error))
    },
    getTreeData(data) {
      // 循环遍历json数据
      for (var i = 0; i < data.length; i++) {
        if (data[i].children.length < 1) {
          // children若为空数组，则将children设为undefined
          data[i].children = undefined
        } else {
          // children若不为空数组，则继续 递归调用 本方法
          this.getTreeData(data[i].children)
        }
      }
      return data
    },
    getMerchantList() {
      userInfo.getMerchantList()
        .then(response => {
          this.merchantList = response.data
        })
        .catch(error => console.log(error))
    },
    getProductInfoById(productId) {
      product.getProductInfoById(productId)
        .then(response => {
          console.log(response)
          this.productInfo = response.data
          this.fileList = this.productInfo.pictureList
          category.getCategoryPath(this.productInfo.catId)
            .then(response => {
              console.log(response)
              this.catId = response.data
            })
            .catch(error => console.log(error))
        })
        .catch(error => console.log(error))
    },
    // <!-- 上传图片 -->
    // 删除图片之前的提示框
    beforeRemovePicture(file, fileList) {
      return this.$confirm(`确定移除 ${file.name} ？`)
    },
    // 删除图片
    removePicture(file, fileList) {
      this.fileList = fileList
    },
    // 压缩图片
    compressImage(file) {
      // console.log(file)
      const isJpgOrPng = file.type === 'image/jpeg' || file.type === 'image/png'
      const isLt2M = file.size / 1024 / 1024 < 20
      if (!isJpgOrPng) {
        this.$message.error('上传图片只能是 JPG 或 PNG 格式!')
        return false
      }
      if (!isLt2M) {
        this.$message.error('上传图片大小不能超过 20MB!')
        return false
      }
      return new Promise((resolve) => {
        // 压缩到100KB,这里的100就是要压缩的大小,可自定义
        imageConversion.compressAccurately(file, 100).then(res => {
          // console.log(res)
          resolve(res)
        })
        // compressAccurately有多个参数时传入对象
        // imageConversion.compressAccurately(file, {
        // size: 1024, //图片大小压缩到1024kb
        // width:1280 //宽度压缩到1280
        // }).then(res => {
        // resolve(res)
        // })
      })
    },
    // 成功上传图片后的回调
    uploadSuccess(response, file, fileList) {
      this.fileList = fileList
      this.isUpload = true
    },
    // 点击图片
    clickPicture(file) {
      console.log(file)
    },
    // 超出限制后的回调函数
    overPictureSize(files, fileList) {
      this.$message.warning(`当前限制选择 5 个文件，本次选择了 ${files.length} 个文件，共选择了 ${files.length + fileList.length} 个文件`)
    },
    getPictureList() {
      this.productInfo.pictureList = []
      console.log(this.fileList)
      for (let i = 0; i < this.fileList.length; i++) {
        const newFile = {
          name: this.fileList[i].name,
          url: this.fileList[i].response.message
        }
        this.productInfo.pictureList.push(newFile)
      }
    },
    getUpdatePictureList() {
      this.productInfo.pictureList = []
      for (let i = 0; i < this.fileList.length; i++) {
        if (this.fileList[i].response !== undefined) {
          const newFile = {
            name: this.fileList[i].name,
            url: this.fileList[i].response.message
          }
          this.productInfo.pictureList.push(newFile)
        } else {
          this.productInfo.pictureList.push(this.fileList[i])
        }
      }
      console.log(this.productInfo.pictureList)
    },
    addProductInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getPictureList()
          product.addProduct(this.productInfo)
            .then(response => {
              this.$router.push({ path: '/product/list' })
            })
            .catch(error => console.log(error))
        } else {
          this.$message.error('请完成信息的输入')
          return false
        }
      })
    },
    updateProductInfo(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          this.getUpdatePictureList()
          product.updateProduct(this.productInfo)
            .then(response => {
              this.$router.push({ path: '/product/list' })
            })
            .catch(error => console.log(error))
        } else {
          this.$message.error('请完成信息的输入')
          return false
        }
      })
    }
  }
}
</script>

<style scoped>

</style>
