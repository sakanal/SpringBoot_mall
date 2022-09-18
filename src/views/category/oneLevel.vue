<template>
  <div class="app-container">
    <el-button type="primary" @click="openAddOneLevelDialog">添加主分类</el-button>
    <el-tree :data="categoryList" :props="defaultProps" :expand-on-click-node="false" accordion style="margin-top: 25px">
      <span class="custom-tree-node" slot-scope="{ node, data }">
        <span>{{ node.label }}</span>
        <template v-if="data.parentCid === 0">
          <span>
            <el-button type="text" size="mini" @click="openAddTwoLevelDialog(data.catId)">添加副分类</el-button>
            <el-button type="text" size="mini" @click="openEditLevelDialog(data.catId)">编辑</el-button>
            <el-button type="text" size="mini" @click="removeLevel(data.catId)">删除</el-button>
          </span>
        </template>
        <template v-else>
          <span>
<!--            TODO 查看副分类-->
            <el-button type="text" size="mini" @click="openEditLevelDialog(data.catId)">编辑</el-button>
            <el-button type="text" size="mini" @click="removeLevel(data.catId)">删除</el-button>
            <el-button type="text" size="mini" @click="check(data.catId)">查看详细</el-button>
          </span>
        </template>
      </span>
    </el-tree>

    <el-dialog title="添加分类" :visible.sync="levelDialogFormVisible">
      <el-form ref="levelForm" :model="levelForm" :rules="rules">
        <el-form-item label="分类名称" label-width="120px" prop="name">
          <el-input v-model="levelForm.name" autocomplete="off"></el-input>
        </el-form-item>
        <el-form-item label="是否显示" label-width="120px">
          <el-switch v-model="status" active-text="显示" inactive-text="不显示">
          </el-switch>
        </el-form-item>
        <el-form-item label="排序" label-width="120px">
          <el-input-number v-model="levelForm.sort" :min="0" :max="10" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="levelDialogFormVisible = false">取 消</el-button>
        <el-button type="primary" @click="addLevel('levelForm')">确 定</el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import category from '@/api/category/category'

export default {
  data() {
    return {
      categoryList: [],
      defaultProps: {
        children: 'children',
        label: 'name'
      },
      levelDialogFormVisible: false,
      status: false,
      levelForm: {
        name: '', // 名称
        parentCid: 0,
        catLevel: 1,
        showStatus: 0, // 是否显示 0-不显示，1-显示
        sort: 0 // 排序
      },
      parentCid: 0,
      rules: {
        name: [
          { required: true, message: '请输入类别标题', trigger: 'blur' },
          { min: 1, max: 25, message: '长度在 1 到 25 个字符', trigger: 'blur' }
        ]
      }
    }
  },
  watch: {
    status(value) {
      if (value === true) {
        this.levelForm.showStatus = 1
      } else {
        this.levelForm.showStatus = 0
      }
    }
  },
  created() {
    this.getOneAndTwoLevel()
  },
  methods: {
    // 初始化，获取所有的一二级分类
    getOneAndTwoLevel() {
      category.getOneAndTwoLevel()
        .then(response => {
          this.categoryList = response.data
        })
        .catch(error => console.log(error))
    },
    // 开启一级分类添加模态框，一二级共用，会重置数据
    openAddOneLevelDialog() {
      this.status = false
      this.levelForm = {}
      this.levelForm.id = null
      this.levelForm.showStatus = 0
      this.levelForm.parentCid = 0
      this.levelForm.catLevel = 1
      this.levelForm.sort = 0
      this.levelDialogFormVisible = true
    },
    // 开启二级分类添加模态框，一二级共用，会重置数据，并将一级分类的id赋值为二级分类的父id
    openAddTwoLevelDialog(catId) {
      this.status = false
      this.levelForm = {}
      this.levelForm.id = null
      this.levelForm.showStatus = 0
      this.levelForm.parentCid = catId
      this.levelForm.catLevel = 2
      this.levelForm.sort = 0
      this.levelDialogFormVisible = true
    },
    // 添加分类，数据校验，如果id为空，代表是添加，否则是修改（id，只能从后端拿到）
    addLevel(formName) {
      this.$refs[formName].validate((valid) => {
        if (valid) {
          if (this.levelForm.id === null) {
            category.addCategory(this.levelForm)
              .then(response => {
                this.$message.success('添加分类成功')
                this.levelDialogFormVisible = false
                this.getOneAndTwoLevel()
              })
              .catch(error => console.log(error))
          } else {
            category.updateCategory(this.levelForm)
              .then(response => {
                this.$message.success('修改成功')
                this.levelDialogFormVisible = false
                this.getOneAndTwoLevel()
              })
              .catch(error => console.log(error))
          }
        } else {
          console.log('error submit!!')
          return false
        }
      })
      // this.levelDialogFormVisible = false
    },
    // 开启分类修改模块框，根据id获取原始数据
    openEditLevelDialog(catId) {
      category.getCategoryById(catId)
        .then(response => {
          this.levelDialogFormVisible = true
          this.levelForm = response.data
          const showStatus = this.levelForm.showStatus
          if (showStatus === 1) {
            this.status = true
          } else if (showStatus === 0) {
            this.status = false
          }
        })
        .catch(error => console.log(error))
    },
    // 删除分类
    removeLevel(catId) {
      this.$confirm('此操作将永久删除该分类, 是否继续?', '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        const ids = [catId]
        category.removeCategoryById(ids)
          .then(response => {
            this.$message.success('该分类删除成功')
            this.getOneAndTwoLevel()
          })
          .catch(error => console.log(error))
      })
    },
    // 查看二级分类下的三级分类，跳转页面
    check(catId) {
      this.$router.push({ path: `/category/twoLevel/${catId}` })
    }
  }
}
</script>

<style scoped>
.custom-tree-node {
  flex: 1;
  display: flex;
  align-items: center;
  justify-content: space-between;
  font-size: 14px;
  padding-right: 8px;
}
</style>
