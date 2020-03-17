<template>
  <div class="app-container">
    <el-table
      :data="list.filter(data => !search || data.title.toLowerCase().includes(search.toLowerCase()))"
      border
      fit
      highlight-current-row
      style="width: 100%"
    >
      <el-table-column
        align="center"
        label="ID"
        prop="id"
        width="80px"
      />
      <el-table-column
        align="center"
        label="标题"
        prop="title"
      />
      <el-table-column
        align="center"
        label="创建日期"
        prop="createDate"
        sortable
        width="160px"
      />
      <el-table-column
        align="center"
        label="更新日期"
        prop="updateDate"
        sortable
        width="160px"
      />
      <el-table-column
        align="center"
        label="点击数"
        prop="clickCount"
        sortable
        width="90px"
      />
      <el-table-column
        align="center"
        label="评论数"
        prop="commentCount"
        sortable
        width="90px"
      />
      <el-table-column
        align="center"
        label="喜欢数"
        prop="likeCount"
        sortable
        width="90px"
      />
      <el-table-column align="center" width="200px" class-name="small-padding fixed-width">
        <template slot="header">
          <el-input
            v-model="search"
            size="mini"
            placeholder="输入关键字搜索"
          />
        </template>
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
import { fetchBlogList, deleteBlog } from '@/api/blog'
import Pagination from '@/components/Pagination'

export default {
  components: { Pagination },
  data() {
    return {
      data: {},
      list: [],
      search: '',
      total: 0,
      listQuery: {
        page: 1,
        limit: 20
      }
    }
  },
  created() {
    this.fetchData()
  },
  methods: {
    fetchData() {
      fetchBlogList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
      })
    },
    handleEdit(index, row) {
      this.$router.push({
        path: '/blog/edit',
        query: { id: row.id }
      })
    },
    handleDelete(index, row) {
      this.$confirm(`此操作将永久删除《${row.title}》, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteBlog(row.id).then(response => {
          // 更新数据
          this.fetchData()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      })
    },
    calcIndex(index) {
      return this.size * (this.page - 1) + index + 1
    },
    handleCurrentChange(currPage) {
      this.page = currPage
      this.fetchData()
    }
  }
}
</script>

<style scoped>

</style>
