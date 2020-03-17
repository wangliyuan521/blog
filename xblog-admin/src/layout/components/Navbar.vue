<template>
  <div class="navbar">
    <hamburger :is-active="sidebar.opened" class="hamburger-container" @toggleClick="toggleSideBar" />
    <breadcrumb class="breadcrumb-container" />
    <div class="right-menu">
      <el-dropdown class="avatar-container" trigger="click">
        <div class="avatar-wrapper">
          <img :src="avatar+'?imageView2/1/w/80/h/80'" class="user-avatar">
          <i class="el-icon-caret-bottom" />
        </div>
        <el-dropdown-menu slot="dropdown" class="user-dropdown">
          <el-dropdown-item>
            <span style="display:block;" @click="handleUpdate">更新个人信息</span>
          </el-dropdown-item>
          <el-dropdown-item divided>
            <span style="display:block;" @click="logout">退出登录</span>
          </el-dropdown-item>
        </el-dropdown-menu>
      </el-dropdown>

      <el-dialog :visible.sync="dialogFormVisible">
        <el-form ref="dataForm" :model="userInfo" label-width="80px" class="el-form">
          <el-form-item label="账号">
            <el-input v-model="userInfo.username" class="inner-item" style="width: 50%;"/>
          </el-form-item>
          <el-form-item label="密码">
            <el-input v-model="userInfo.password" class="inner-item" style="width: 50%;"/>
          </el-form-item>
          <el-form-item label="手机">
            <el-input v-model="userInfo.password" class="inner-item" style="width: 50%;"/>
          </el-form-item>
          <el-form-item label="邮箱">
            <el-input v-model="userInfo.email" class="inner-item" style="width: 50%;"/>
          </el-form-item>
          <el-form-item label="头像">
            <lb-upload v-model="userInfo.avatar" height="180px" />
          </el-form-item>
          <el-form-item label="简介">
            <el-input v-model="userInfo.profile" class="inner-item" type="textarea" style="width: 90%;" :rows="7" />
          </el-form-item>
          <el-form-item style="text-align: center">
            <el-button type="primary" @click="onSubmit">提交</el-button>
          </el-form-item>
        </el-form>
      </el-dialog>
    </div>
  </div>
</template>

<script>
import { mapGetters } from 'vuex'
import Breadcrumb from '@/components/Breadcrumb'
import Hamburger from '@/components/Hamburger'
import Upload from '@/components/Upload'
import { getToken } from '@/utils/auth'
import { getInfo, updateAdmin } from '@/api/admin'

export default {
  components: {
    lbUpload: Upload,
    Breadcrumb,
    Hamburger
  },
  data() {
    return {
      userInfo: {
        nickname: '',
        username: '',
        password: '',
        avatar: '',
        email: '',
        profile: ''
      },
      dialogFormVisible: false
    }
  },
  computed: {
    ...mapGetters([
      'sidebar',
      'avatar'
    ])
  },
  created() {
    getInfo(getToken).then(response => {
      this.userInfo = response.data
    })
  },
  methods: {
    handleUpdate() {
      this.dialogFormVisible = true
      this.$nextTick(() => {
        this.$refs['dataForm'].clearValidate()
      })
    },
    onSubmit() {
      updateAdmin(this.userInfo).then(response => {
        // 更新state信息
        this.$store.dispatch('admin/getInfo')
        this.dialogFormVisible = false
        this.$message({
          type: 'success',
          message: '提交成功！'
        })
      })
    },
    toggleSideBar() {
      this.$store.dispatch('app/toggleSideBar')
    },
    async logout() {
      await this.$store.dispatch('admin/logout')
      this.$router.push(`/login?redirect=${this.$route.fullPath}`)
    }
  }
}
</script>

<style lang="scss" scoped>
.navbar {
  height: 50px;
  overflow: hidden;
  position: relative;
  background: #fff;
  box-shadow: 0 1px 4px rgba(0,21,41,.08);

  .hamburger-container {
    line-height: 46px;
    height: 100%;
    float: left;
    cursor: pointer;
    transition: background .3s;
    -webkit-tap-highlight-color:transparent;

    &:hover {
      background: rgba(0, 0, 0, .025)
    }
  }

  .breadcrumb-container {
    float: left;
  }

  .right-menu {
    float: right;
    height: 100%;
    line-height: 50px;

    &:focus {
      outline: none;
    }

    .right-menu-item {
      display: inline-block;
      padding: 0 8px;
      height: 100%;
      font-size: 18px;
      color: #5a5e66;
      vertical-align: text-bottom;

      &.hover-effect {
        cursor: pointer;
        transition: background .3s;

        &:hover {
          background: rgba(0, 0, 0, .025)
        }
      }
    }

    .avatar-container {
      margin-right: 30px;

      .avatar-wrapper {
        margin-top: 5px;
        position: relative;

        .user-avatar {
          cursor: pointer;
          width: 40px;
          height: 40px;
          border-radius: 40px;
        }

        .el-icon-caret-bottom {
          cursor: pointer;
          position: absolute;
          right: -20px;
          top: 25px;
          font-size: 12px;
        }
      }
    }
  }
}
</style>
