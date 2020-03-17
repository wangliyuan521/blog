<template>
  <div class="app-container">
    <el-table
      :data="list"
      border
      fit
      highlight-current-row
      style="width: 100%;"
    >
      <el-table-column label="ID" prop="id" sortable="custom" align="center" width="80px">
        <template slot-scope="{row}">
          <span>{{ row.id }}</span>
        </template>
      </el-table-column>
      <el-table-column label="账号" prop="username" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.username }}</span>
        </template>
      </el-table-column>
      <el-table-column label="角色" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.role }}</span>
        </template>
      </el-table-column>
      <el-table-column label="手机号" width="110px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.phone }}</span>
        </template>
      </el-table-column>
      <el-table-column label="邮箱" width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.email }}</span>
        </template>
      </el-table-column>
      <el-table-column label="性别" width="80px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.sex }}</span>
        </template>
      </el-table-column>
      <el-table-column label="头像" width="80px" align="center">
        <template slot-scope="{row}">
          <img :src="row.avatar" class="image-cell">
        </template>
      </el-table-column>
      <el-table-column label="个人简介" min-width="100px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.preInfo }}</span>
        </template>
      </el-table-column>
      <el-table-column label="状态" width="80px" align="center">
        <template slot-scope="{row}">
          <el-tag v-if="row.status==0" type="success">
            正常
          </el-tag>
          <el-tag v-if="row.status==1" type="danger">
            已禁用
          </el-tag>
        </template>
      </el-table-column>
      <el-table-column label="注册时间" width="160px" align="center">
        <template slot-scope="{row}">
          <span>{{ row.createTime }}</span>
        </template>
      </el-table-column>
      <el-table-column label="操作" align="center" width="230px" class-name="small-padding fixed-width">
        <template slot-scope="{row,$index}">
          <el-button type="primary" size="mini" @click="handleUpdate(row)">
            更新
          </el-button>
          <el-button v-if="row.status!=0" size="mini" type="success" @click="handleModifyStatus(row,0)">
            启用
          </el-button>
          <el-button v-if="row.status!=1" size="mini" @click="handleModifyStatus(row,1)">
            禁用
          </el-button>
          <el-button size="mini" type="danger" @click="handleDelete(row,$index)">
            删除
          </el-button>
        </template>
      </el-table-column>
    </el-table>
    <pagination v-show="total>0" :total="total" :page.sync="listQuery.page" :limit.sync="listQuery.limit" @pagination="refreshTable" />

    <el-dialog :title="textMap[dialogStatus]" :visible.sync="dialogFormVisible">
      <el-form ref="dataForm" :rules="rules" :model="user" label-width="100px" style="width:60%">
        <el-form-item hidden label="ID" prop="id">
          <el-input v-model="user.id" />
        </el-form-item>
        <el-form-item label="账号" prop="username">
          <el-input v-model="user.username" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="user.password" />
        </el-form-item>
        <el-form-item label="角色" prop="role">
          <el-select v-model="user.role" placeholder="请选择用户角色" class="el-select">
            <el-option label="管理员" value="admin"></el-option>
            <el-option label="普通用户" value="user"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="手机" prop="phone">
          <el-input v-model="user.phone" />
        </el-form-item>
        <el-form-item label="邮箱" prop="email">
          <el-input v-model="user.email" />
        </el-form-item>
        <el-form-item label="性别" prop="sex">
          <el-select v-model="user.sex" placeholder="请选择用户性别" class="el-select">
            <el-option label="男" value="男"></el-option>
            <el-option label="女" value="女"></el-option>
          </el-select>
        </el-form-item>
        <el-form-item label="头像">
          <lb-upload v-model="user.avatar" height="100px" />
        </el-form-item>
        <el-form-item label="简介">
          <el-input v-model="user.preInfo" :autosize="{ minRows: 3, maxRows: 4}" type="textarea" placeholder="请填写个人简介。。。。" />
        </el-form-item>
      </el-form>
      <div slot="footer" class="dialog-footer">
        <el-button @click="dialogFormVisible = false">
          取消
        </el-button>
        <el-button type="primary" @click="dialogStatus==='create'?createData():updateData()">
          确认
        </el-button>
      </div>
    </el-dialog>
  </div>
</template>

<script>
import { fetchUserList, updateUser, deleteUser } from '@/api/user'
import Pagination from '@/components/Pagination'
import Upload from '@/components/Upload'
export default {
  name: 'ComplexTable',
  components: {
    lbUpload: Upload, Pagination
  },
  data() {
    return {
      tableKey: 0,
      list: null,
      total: 0,
      listLoading: true,
      listQuery: {
        page: 1,
        limit: 20
      },
      user: {
        id: undefined,
        username: '',
        password: '',
        role: '',
        phone: '',
        email: '',
        sex: '',
        avatar: '',
        preInfo: '',
        status: 0
      },
      dialogFormVisible: false,
      dialogStatus: '',
      textMap: {
        update: '更新',
        create: '添加'
      },
      rules: {
        username: [{ required: true, message: '账号必填项', trigger: 'change' }],
        password: [{ required: true, message: '密码必填项', trigger: 'change' }],
        role: [{ required: true, message: '角色必填项', trigger: 'change' }]
      }
    }
  },
  created() {
    this.refreshTable()
  },
  methods: {
    refreshTable() {
      fetchUserList(this.listQuery).then(response => {
        this.list = response.data.list
        this.total = response.data.total
      })
    },
    handleModifyStatus(row, status) {
      row.status = status
      row.updateTime = null
      updateUser(row).then(response => {
        // 刷新表格
        this.refreshTable()
        if (status === 1) {
          this.$message({
            message: '禁用成功',
            type: 'success',
            duration: 2000
          })
        } else {
          this.$message({
            message: '启用成功',
            type: 'success',
            duration: 2000
          })
        }
      })
    },
    handleUpdate(row) {
      this.user = Object.assign({}, row) // copy obj
      this.dialogStatus = 'update'
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    updateData() {
      this.$refs['dataForm'].validate((valid) => {
        if (valid) {
          const formData = Object.assign({}, this.user)
          updateUser(formData).then(() => {
            this.dialogFormVisible = false
            this.refreshTable()
            this.$notify({
              title: 'Success',
              message: '更新成功',
              type: 'success',
              duration: 2000
            })
          })
        }
      })
    },
    // 删除操作
    handleDelete(row, index) {
      this.$confirm(`此操作将永久删除该用户, 是否继续?`, '提示', {
        confirmButtonText: '确定',
        cancelButtonText: '取消',
        type: 'warning'
      }).then(() => {
        deleteUser(row.id).then(response => {
          // 刷新表格
          this.refreshTable()
          this.$message({
            title: 'Success',
            message: '删除成功!',
            type: 'success',
            duration: 2000
          })
        })
      })
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
  .el-select{
    width: 100%;
  }

</style>

