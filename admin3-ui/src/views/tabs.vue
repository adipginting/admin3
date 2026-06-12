<template>
  <div class="container">
    <el-tabs v-model="message">
      <el-tab-pane :label="`Unread Messages(${state.unread.length})`" name="first">
        <el-table :data="state.unread" :show-header="false" style="width: 100%">
          <el-table-column>
            <template #default="scope">
              <span class="message-title">{{ scope.row.title }}</span>
            </template>
          </el-table-column>
          <el-table-column prop="date" width="180"></el-table-column>
          <el-table-column width="120">
            <template #default="scope">
              <el-button size="small" @click="handleRead(scope.$index)">Mark as Read</el-button>
            </template>
          </el-table-column>
        </el-table>
        <div class="handle-row">
          <el-button type="primary">Mark All as Read</el-button>
        </div>
      </el-tab-pane>
      <el-tab-pane :label="`Read Messages(${state.read.length})`" name="second">
        <template v-if="message === 'second'">
          <el-table :data="state.read" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title">{{ scope.row.title }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="date" width="150"></el-table-column>
            <el-table-column width="120">
              <template #default="scope">
                <el-button type="danger" @click="handleDel(scope.$index)">Delete</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="danger">Delete All</el-button>
          </div>
        </template>
      </el-tab-pane>
      <el-tab-pane :label="`Recycle Bin(${state.recycle.length})`" name="third">
        <template v-if="message === 'third'">
          <el-table :data="state.recycle" :show-header="false" style="width: 100%">
            <el-table-column>
              <template #default="scope">
                <span class="message-title">{{ scope.row.title }}</span>
              </template>
            </el-table-column>
            <el-table-column prop="date" width="150"></el-table-column>
            <el-table-column width="120">
              <template #default="scope">
                <el-button @click="handleRestore(scope.$index)">Restore</el-button>
              </template>
            </el-table-column>
          </el-table>
          <div class="handle-row">
            <el-button type="danger">Empty Recycle Bin</el-button>
          </div>
        </template>
      </el-tab-pane>
    </el-tabs>
  </div>
</template>

<script setup lang="ts" name="tabs">
import {reactive, ref} from 'vue';

const message = ref('first');
const state = reactive({
  unread: [
    {
      date: '2018-04-19 20:00:00',
      title: '[System Notice] The system will be under maintenance from 2 AM to 5 AM tonight'
    },
    {
      date: '2018-04-19 21:00:00',
      title: 'Red envelope giveaway at 12 tonight, first come first served'
    }
  ],
  read: [
    {
      date: '2018-04-19 20:00:00',
      title: '[System Notice] The system will be under maintenance from 2 AM to 5 AM tonight'
    }
  ],
  recycle: [
    {
      date: '2018-04-19 20:00:00',
      title: '[System Notice] The system will be under maintenance from 2 AM to 5 AM tonight'
    }
  ]
});

const handleRead = (index: number) => {
  const item = state.unread.splice(index, 1);
  state.read = item.concat(state.read);
};
const handleDel = (index: number) => {
  const item = state.read.splice(index, 1);
  state.recycle = item.concat(state.recycle);
};
const handleRestore = (index: number) => {
  const item = state.recycle.splice(index, 1);
  state.read = item.concat(state.read);
};
</script>

<style>
.message-title {
  cursor: pointer;
}
.handle-row {
  margin-top: 30px;
}
</style>
