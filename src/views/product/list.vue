<template>
  <div class="app-container">
    <el-form ref="productQuery" :inline="true" :model="productQuery" size="small" class="demo-form-inline">
      <el-form-item label="商品标题">
        <el-input v-model="productQuery.name" placeholder="请输入商品标题" />
      </el-form-item>
<!--      <el-form-item label="商品类型" prop="catId">-->
<!--        <el-cascader v-model="catId" :options="categoryList" :props="categoryProps" :show-all-levels="false" placeholder="请选择商品类型"/>-->
<!--      </el-form-item>-->
      <el-form-item label="商家名称">
        <el-select v-model="productQuery.userId" placeholder="请选择商家">
          <el-option
            v-for="merchant in merchantList"
            :key="merchant.id"
            :label="merchant.userName"
            :value="merchant.id"
            filterable
          />
        </el-select>
      </el-form-item>
      <el-form-item label="商品价格">
        <el-select v-model="productQuery.price" clearable placeholder="请选择商品价格">
          <el-option value="50" key="50" label="50元以下"/>
          <el-option value="100" key="100" label="100元以下"/>
          <el-option value="1000" key="1000" label="1000元以下"/>
          <el-option value="10000" key="10000" label="10000元以下"/>
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getPageProductList()">查询</el-button>
        <el-button @click="resetData()">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table :data="productList" style="width: 100%">
      <el-table-column label="序号" width="80">
        <template slot-scope="scope">
          {{ (current-1)*10 + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品标题" width="180"/>
      <el-table-column prop="description" label="商品描述"/>
      <el-table-column prop="price" label="商品价格" width="180"/>
      <el-table-column label="操作" width="180">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="updateProduct(scope.row.id)">编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteProduct(scope.row.id)">删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      layout="prev, pager, next"
      :total="pages">
    </el-pagination>
  </div>
</template>

<script>
import category from '@/api/category/category'
import product from '@/api/product/product'
import userInfo from '@/api/userInfo/userInfo'

export default {
  data() {
    return {
      productList: [],
      productQuery: {},
      // catId: 0,
      categoryList: [],
      categoryProps: {
        label: 'name',
        value: 'catId'
      },
      merchantList: {},
      current: 1,
      pages: -1
    }
  },
  watch: {
    // catId(value) {
    //   this.productQuery.catId = value[2]
    // }
  },
  created() {
    this.getPageProductList()
    this.getAllCategory()
    this.getMerchantList()
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
    getPageProductList(current = 1) {
      console.log(this.productQuery.userId)
      this.current = current
      product.getPageProductList(current, this.productQuery)
        .then(response => {
          this.productList = response.data.records
          this.pages = response.data.pages
        })
        .catch(error => console.log(error))
    },
    updateProduct(productId) {
      this.$router.push({ path: `/product/update/${productId}` })
    },
    deleteProduct(productId) {
      this.$confirm('此操作将永久删除该商品, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        product.removeProductById([productId])
          .then(response => {
            this.$message.success('删除商品成功')
            this.getPageProductList()
          })
          .catch(error => console.log(error))
      })
    },
    resetData() {
      this.productQuery = {}
      this.getPageProductList()
    }
  }
}
</script>

<style scoped>

</style>
