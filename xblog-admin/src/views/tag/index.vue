<template>
  <div class="app-container">
    <el-button-group class="el-button-group">
      <el-button type="success" icon="el-icon-plus" @click="handleAdd" class="inner_btn">添加标签</el-button>
    </el-button-group>
    <el-table
      :data="list"
      style="width: 100%"
      border
      fit
      highlight-current-row
      @current-change="handleCurrentRowChange"
    >
      <el-table-column
        align="center"
        label="ID"
        width="100px"
        prop="id"
      />
      <el-table-column
        align="center"
        label="名称"
        prop="name"
      />
      <el-table-column
        align="center"
        label="操作"
        width="300px"
      >
        <template slot-scope="scope">
          <el-button size="mini" type="primary" @click="handleEdit(scope.$index, scope.row)">
            编辑
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(scope.$index, scope.row)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </div>
</template>
<script>
import { fetchTagList, deleteTag, addTag, updateTag } from '@/api/tag'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  data() {
    return {
      data: {},
      list: [
        {
          id: null,
          name: ''
        }
      ],
      total: 0,
      listQuery: {
        page: 1,
        limit: 10
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      fetchTagList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
      })
    },
    calcIndex(index) {
      return this.size * (this.page - 1) + index + 1
    },
    handleAdd() {
      this.$prompt('请输入标签名称', '新建', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        addTag({ name: value }).then(response => {
          this.$message({
            type: 'success',
            message: '添加成功！'
          })
          this.fetchData()
        })
      })
    },
    handleEdit(index, row) {
      if (this.currRow === null) {
        this.currRow = row
      }
      this.$prompt('请输入标签名称', '修改', {
        confirmButtonText: '确定',
        cancelButtonText: '取消'
      }).then(({ value }) => {
        updateTag({ id: this.currRow.id, name: value }).then(response => {
          this.$message({
            type: 'success',
            message: '修改成功！'
          })
          this.fetchData()
        })
      })
    },
    handleDelete() {
      if (this.currRow === null) {
        this.currRow = row
      }
      this.$confirm(`此操作将永久删除标签“ ${this.currRow.name} ”，是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteTag(this.currRow.id).then(response => {
          // 更新数据
          this.fetchData()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
          this.currRow = null
        })
      })
    },
    handleCurrentPageChange(currPage) {
      this.page = currPage
      this.fetchData()
    },
    handleCurrentRowChange(currRow) {
      this.currRow = currRow
    }
  }
}
</script>

<style lang="scss" scoped>
  .inner_btn{
    margin-bottom: 10px;
  }
</style>
