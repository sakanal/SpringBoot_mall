<template>
  <div class="app-container">
    <el-form ref="productQuery" :inline="true" :model="productQuery" size="small" class="demo-form-inline">
      <el-form-item label="商品标题">
        <el-input v-model="productQuery.name" placeholder="请输入商品标题" />
      </el-form-item>
      <el-form-item label="商品类型" prop="catId">
        <el-cascader
          v-model="productQuery.catId"
          clearable
          :options="categoryList"
          :props="categoryProps"
          :show-all-levels="false"
          placeholder="请选择商品类型"
        />
      </el-form-item>
      <el-form-item label="商品价格">
        <el-select v-model="productQuery.price" clearable placeholder="请选择商品价格">
          <el-option key="50" value="50" label="50元以下" />
          <el-option key="100" value="100" label="100元以下" />
          <el-option key="1000" value="1000" label="1000元以下" />
          <el-option key="10000" value="10000" label="10000元以下" />
        </el-select>
      </el-form-item>
      <el-form-item label="推荐商品">
        <el-select v-model="productQuery.isRecommend" clearable placeholder="是否推荐">
          <el-option key="1" value="1" label="是" />
          <el-option key="0" value="0" label="否" />
        </el-select>
      </el-form-item>
      <el-form-item>
        <el-button type="primary" @click="getPageProductList()">查询</el-button>
        <el-button @click="resetData()">重置</el-button>
      </el-form-item>
    </el-form>

    <el-table v-loading="isLoading" :data="productList" style="width: 100%">
      <el-table-column label="序号" width="80">
        <template slot-scope="scope">
          {{ (current-1)*10 + scope.$index + 1 }}
        </template>
      </el-table-column>
      <el-table-column prop="name" label="商品标题" width="180" />
      <el-table-column prop="description" label="商品描述" />
      <el-table-column prop="price" label="商品价格" width="90" />
      <el-table-column prop="isRecommend" label="是否推荐" width="80">
        <template slot-scope="scope">
          <span v-if="scope.row.isRecommend === 0">否</span>
          <span v-else>推荐</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" width="160">
        <template slot-scope="scope">
          <el-button
            size="mini"
            @click="updateProduct(scope.row.id)"
          >编辑</el-button>
          <el-button
            size="mini"
            type="danger"
            @click="deleteProduct(scope.row.id)"
          >删除</el-button>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      style="padding: 30px 0;text-align: center"
      layout="total, prev, pager, next, jumper"
      :page-count="pages"
      :total="total"
      @current-change="getPageProductList"
    />
  </div>
</template>

<script>
import category from '@/api/category/category'
import product from '@/api/product/product'
import userInfo from '@/api/userInfo/userInfo'

export default {
  data() {
    return {
      isLoading: true,
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
      pages: 0,
      total: 0
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
      this.isLoading = true
      this.current = current
      if (this.productQuery.catId !== undefined) {
        if (Array.isArray(this.productQuery.catId)) {
          this.productQuery.catId = this.productQuery.catId[2]
        }
      }
      product.getPageProductList(current, this.productQuery)
        .then(response => {
          console.log(response)
          this.productList = response.data.records
          this.pages = response.data.pages
          this.total = response.data.total
          this.isLoading = false
        })
        .catch(error => {
          console.log(error)
          this.isLoading = false
        })
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
      this.current = 1
      this.getPageProductList()
    }
  }
}
</script>

<style scoped>

</style>
