<template>
  <div class="app-container">
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%"
      :cell-style="{padding:'4px'}"
    >
      <el-table-column
        align="center"
        label="ID"
        prop="id"
        width="80px"
      />
      <el-table-column
        align="center"
        width="80px"
        label="头像"
        prop="readerAvatar"
      >
        <template slot-scope="scope">
          <img :src="scope.row.readerAvatar" class="image-cell">
        </template>
      </el-table-column>
      <el-table-column
        align="center"
        label="用户名"
        width="80px"
        prop="readerName"
      />
      <el-table-column
        align="center"
        label="博客标题"
        :formatter="blogTitleFormatter"
      />
      <el-table-column
        align="center"
        label="内容"
        prop="content"
      />
      <el-table-column
        align="center"
        label="日期"
        width="160px"
        prop="commentDate"
        sortable
      />
      <el-table-column
        align="center"
        label="IP"
        width="140px"
        prop="ip"
      />
      <el-table-column
        align="center"
        label="操作"
        width="100px"
      >
        <template slot-scope="scope">
          <el-button
            size="mini"
            type="danger"
            @click="handleDelete(scope.$index, scope.row)"
          >删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="fetchData" />
  </div>
</template>

<script>
import { fetchCommentList, deleteComment } from '@/api/comment'
import Pagination from '@/components/Pagination'
export default {
  components: { Pagination },
  data() {
    return {
      data: {},
      list: [
        {
          id: null,
          blogId: null,
          blogTitle: '',
          commentDate: '',
          readerName: '',
          readerAvatar: '',
          ip: '',
          content: '',
          receiverName: '',
          receiverAvatar: '',
          receiverContent: '',
          receiverDate: ''
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
      fetchCommentList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
      })
    },
    calcIndex(index) {
      return this.size * (this.page - 1) + index + 1
    },
    handleDelete(index, row) {
      this.$confirm(`此操作将永久删除该评论, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteComment(row.id).then(response => {
          // 更新数据
          this.fetchData()
          this.$message({
            type: 'success',
            message: '删除成功!'
          })
        })
      })
    },
    handleCurrentPageChange(currPage) {
      this.page = currPage
      this.fetchData()
    },
    blogTitleFormatter(row) {
      return `《${row.blogTitle}》`
    }
  }
}
</script>

<style lang="scss" scoped>
  .image-cell {
    width: 40px;
    height: 40px;
    display: block;
    margin: 0 auto;
  }

</style>
