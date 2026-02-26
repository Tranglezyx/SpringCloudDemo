<script setup lang="ts">
import { ref, onMounted } from 'vue'
import { ElMessage, ElMessageBox } from 'element-plus'
import type { FormInstance } from 'element-plus'
import { getUserPage, saveUser, updateUser, deleteUser, type SysUser } from '../api/user'

const loading = ref(false)
const tableData = ref<SysUser[]>([])
const total = ref(0)
const currentPage = ref(1)
const pageSize = ref(10)

const searchForm = ref({
  account: '',
  mobile: ''
})

const dialogVisible = ref(false)
const dialogTitle = ref('')
const formRef = ref<FormInstance>()
const formData = ref<SysUser>({
  account: '',
  mobile: '',
  password: '',
  nickName: ''
})

const rules = {
  account: [{ required: true, message: '请输入账号', trigger: 'blur' }],
  mobile: [{ required: true, message: '请输入手机号', trigger: 'blur' }],
  password: [{ required: true, message: '请输入密码', trigger: 'blur' }]
}

const loadData = async () => {
  loading.value = true
  try {
    const result = await getUserPage({
      pageNum: currentPage.value,
      pageSize: pageSize.value,
      account: searchForm.value.account || undefined,
      mobile: searchForm.value.mobile || undefined
    })
    tableData.value = (result as any).list
    total.value = (result as any).total
  } catch (error) {
    ElMessage.error('加载数据失败')
  } finally {
    loading.value = false
  }
}

const handleSearch = () => {
  currentPage.value = 1
  loadData()
}

const handleReset = () => {
  searchForm.value = {
    account: '',
    mobile: ''
  }
  handleSearch()
}

const handleAdd = () => {
  dialogTitle.value = '新增用户'
  formData.value = {
    account: '',
    mobile: '',
    password: '',
    nickName: ''
  }
  dialogVisible.value = true
}

const handleEdit = (row: SysUser) => {
  dialogTitle.value = '编辑用户'
  formData.value = { ...row }
  dialogVisible.value = true
}

const handleDelete = async (row: SysUser) => {
  try {
    await ElMessageBox.confirm('确认删除该用户吗?', '提示', {
      type: 'warning'
    })
    await deleteUser(row.id!)
    ElMessage.success('删除成功')
    loadData()
  } catch (error) {
    if (error !== 'cancel') {
      ElMessage.error('删除失败')
    }
  }
}

const handleSubmit = async () => {
  if (!formRef.value) return
  await formRef.value.validate(async (valid) => {
    if (valid) {
      try {
        if (formData.value.id) {
          await updateUser(formData.value)
          ElMessage.success('更新成功')
        } else {
          await saveUser(formData.value)
          ElMessage.success('保存成功')
        }
        dialogVisible.value = false
        loadData()
      } catch (error) {
        ElMessage.error('操作失败')
      }
    }
  })
}

const handleSizeChange = (size: number) => {
  pageSize.value = size
  loadData()
}

const handlePageChange = (page: number) => {
  currentPage.value = page
  loadData()
}

onMounted(() => {
  loadData()
})
</script>

<template>
  <div class="user-management">
    <el-card class="search-card">
      <el-form :inline="true" :model="searchForm">
        <el-form-item label="账号">
          <el-input v-model="searchForm.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="手机号">
          <el-input v-model="searchForm.mobile" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item>
          <el-button type="primary" @click="handleSearch">查询</el-button>
          <el-button @click="handleReset">重置</el-button>
        </el-form-item>
      </el-form>
    </el-card>

    <el-card class="table-card">
      <template #header>
        <el-button type="primary" @click="handleAdd">新增</el-button>
      </template>
      <el-table :data="tableData" v-loading="loading" border stripe>
        <el-table-column prop="id" label="ID" width="80" />
        <el-table-column prop="account" label="账号" />
        <el-table-column prop="mobile" label="手机号" />
        <el-table-column prop="nickName" label="昵称" />
        <el-table-column prop="createTime" label="创建时间" width="180" />
        <el-table-column label="操作" width="200">
          <template #default="{ row }">
            <el-button type="primary" size="small" @click="handleEdit(row)">编辑</el-button>
            <el-button type="danger" size="small" @click="handleDelete(row)">删除</el-button>
          </template>
        </el-table-column>
      </el-table>
      <el-pagination
        v-model:current-page="currentPage"
        v-model:page-size="pageSize"
        :page-sizes="[10, 20, 50, 100]"
        :total="total"
        layout="total, sizes, prev, pager, next, jumper"
        @size-change="handleSizeChange"
        @current-change="handlePageChange"
        class="pagination"
      />
    </el-card>

    <el-dialog v-model="dialogVisible" :title="dialogTitle" width="500px">
      <el-form ref="formRef" :model="formData" :rules="rules" label-width="80px">
        <el-form-item label="账号" prop="account">
          <el-input v-model="formData.account" placeholder="请输入账号" />
        </el-form-item>
        <el-form-item label="手机号" prop="mobile">
          <el-input v-model="formData.mobile" placeholder="请输入手机号" />
        </el-form-item>
        <el-form-item label="密码" prop="password">
          <el-input v-model="formData.password" type="password" placeholder="请输入密码" />
        </el-form-item>
        <el-form-item label="昵称">
          <el-input v-model="formData.nickName" placeholder="请输入昵称" />
        </el-form-item>
      </el-form>
      <template #footer>
        <el-button @click="dialogVisible = false">取消</el-button>
        <el-button type="primary" @click="handleSubmit">确定</el-button>
      </template>
    </el-dialog>
  </div>
</template>

<style scoped>
.user-management {
  padding: 20px;
}

.search-card {
  margin-bottom: 20px;
}

.table-card {
  margin-bottom: 20px;
}

.pagination {
  margin-top: 20px;
  display: flex;
  justify-content: flex-end;
}
</style>
