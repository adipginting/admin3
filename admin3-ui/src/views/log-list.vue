<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-select v-model="query.typeNames" placeholder="Operation Type" class="handle-select mr10" style="width: 150px"
                   @change="handleSelectTypeInfoChange" filterable multiple clearable>
          <el-option
            v-for="item in typeInfo"
            :key="item.value"
            :label="item.label"
            :value="item.value"
          />
        </el-select>
        <el-button type="primary" :icon="Refresh" @click="handleRefresh">Refresh Logs</el-button>
        <el-button type="danger" :icon="Delete" @click="handleCleanLogs" v-action:log:clean>Clear Logs</el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
        <el-table-column prop="user.username" label="User" width="120"></el-table-column>
        <el-table-column prop="typeNameLabel" label="Operation Type" width="120">
          <template #default="{ row }">
            <span>{{ typeInfo?.find(t => t.value === row.typeName)?.label }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="content" label="Description"></el-table-column>
        <el-table-column prop="occurredOn" label="Operation Time"></el-table-column>
      </el-table>
      <div class="pagination">
        <el-pagination
          background
          layout="total, prev, pager, next"
          :current-page="query.pageIndex"
          :page-size="query.pageSize"
          :total="pageTotal"
          @current-change="handlePageChange"
        ></el-pagination>
      </div>
    </div>
  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {Delete, Refresh} from '@element-plus/icons-vue';
import {cleanLogs, getLogList} from "../api/log";
import {getEventTypes} from "../api/common";

interface TableItem {
  id: number;
  user: { id: number; username: string; };
  typeName: string;
  typeNameLabel?: string;
  content: string;
  occurredOn: string;
}

interface TypeInfo {
  label: string;
  value: string;
}

const query = reactive({
  typeNames: [],
  pageIndex: 1,
  pageSize: 10
});

const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
const typeInfo = ref<TypeInfo[]>();

// Get types
const getTypeInfo = () => {
  getEventTypes().then(res => {
    typeInfo.value = res.data;
  });
};
getTypeInfo();
// Get table data
const getData = () => {
  getLogList({
    page: query.pageIndex,
    size: query.pageSize,
    typeNames: query.typeNames.join() || undefined
  }).then(res => {
    tableData.value = res.data.list;
    pageTotal.value = res.data.total;
  });
};
getData();
// Search operation
const handleSelectTypeInfoChange = () => {
  query.pageIndex = 1;
  getData();
};
// Pagination
const handlePageChange = (val: number) => {
  query.pageIndex = val;
  getData();
};
// Refresh logs
const handleRefresh = () => {
  getData();
  ElMessage.success('Refreshed successfully');
}
// Clear logs
const handleCleanLogs = () => {
  // Confirm delete
  ElMessageBox.confirm('Are you sure you want to clear all logs?', 'Warning', {
    type: 'warning'
  }).then(() => {
    cleanLogs().then(_ => {
      ElMessage.success('Cleared successfully');
      getData();
    });
  })
    .catch(() => {
    });
};
</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.mr10 {
  margin-right: 10px;
}
</style>
