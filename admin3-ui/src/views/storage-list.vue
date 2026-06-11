<template>
  <div>
    <div class="container">
      <div class="handle-box">
        <el-button type="primary" :icon="Plus" @click="addVisible = true;Object.assign(form, new StorageImpl());"
                   v-action:storage:create>
          Add Object Storage
        </el-button>
      </div>
      <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
        <el-table-column prop="name" label="Name" width="120"></el-table-column>
        <el-table-column prop="storageId" label="ID" align="center"></el-table-column>
        <el-table-column prop="type" label="Type" width="120">
          <template #default="{ row }">
            <el-tag class="ml-2" type="success">{{ row.type }}</el-tag>
          </template>
        </el-table-column>
        <el-table-column prop="bucketName" label="Bucket Name"></el-table-column>
        <el-table-column prop="endpoint" label="Endpoint"></el-table-column>
        <el-table-column prop="address" label="Access URL"></el-table-column>
        <el-table-column prop="storagePath" label="Storage Path"></el-table-column>
        <el-table-column prop="isDefault" label="Default Storage">
          <template #default="{ row }">
            <el-tag effect="dark" link v-if="row.isDefault" v-action:storage:markAsDefault>Default</el-tag>
            <el-popconfirm title="Set as default?" v-else @confirm="handleMarkAsDefaultConfig(row)">
              <template #reference>
                <el-button link>Set as default</el-button>
              </template>
            </el-popconfirm>
          </template>
        </el-table-column>
        <el-table-column prop="createUser" label="Created By"></el-table-column>
        <el-table-column prop="createTime" label="Created Time"></el-table-column>
        <el-table-column label="Operations">
          <template #default="scope">
            <el-button-group>
              <el-button text :icon="Edit" @click="handleEdit(scope.row)" v-action:storage:update>
                Edit
              </el-button>
              <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-action:storage:delete>
                Delete
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- Add Dialog -->
    <el-dialog title="Add" v-model="addVisible" width="35%">
      <el-form label-width="100px">
        <el-form-item label="Name">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="Type">
          <el-radio-group v-model="form.type" size="small" @change="handleTypeChange">
            <el-radio-button label="OSS">Alibaba Cloud OSS</el-radio-button>
            <el-radio-button label="OBS">Huawei Cloud OBS</el-radio-button>
            <el-radio-button label="S3">Other S3 Protocol</el-radio-button>
            <el-radio-button label="LOCAL">Local</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-alert title="All inputs below support environment variables using the syntax {{VARIABLE_NAME}}" show-icon center :closable="false" style="margin-bottom: 18px"/>
        <el-form-item label="Endpoint" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.endpoint" placeholder="Object storage endpoint"/>
        </el-form-item>
        <el-form-item label="Bucket Name" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.bucketName" placeholder="Object storage bucket name"/>
        </el-form-item>
        <el-form-item label="AccessKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.accessKey"/>
        </el-form-item>
        <el-form-item label="SecretKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.secretKey"/>
        </el-form-item>
        <el-form-item label="Access URL">
          <el-input v-model="form.address" placeholder="Leave blank to use vendor address (except local storage)"/>
        </el-form-item>
        <el-form-item label="Storage Path">
          <el-input v-model="form.storagePath" placeholder="Leave blank to store in files directory by default"/>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="addVisible = false">Cancel</el-button>
					<el-button type="primary" @click="handleCreateConfig">OK</el-button>
				</span>
      </template>
    </el-dialog>

    <!-- Edit Dialog -->
    <el-dialog title="Edit" v-model="editVisible" width="35%">
      <el-form label-width="100px">
        <el-form-item label="Name">
          <el-input v-model="form.name"/>
        </el-form-item>
        <el-form-item label="Type">
          <el-radio-group v-model="form.type" size="small" @change="handleTypeChange">
            <el-radio-button label="OSS">Alibaba Cloud OSS</el-radio-button>
            <el-radio-button label="OBS">Huawei Cloud OBS</el-radio-button>
            <el-radio-button label="S3">Other S3 Protocol</el-radio-button>
            <el-radio-button label="LOCAL">Local</el-radio-button>
          </el-radio-group>
        </el-form-item>
        <el-alert title="All inputs below support environment variables using the syntax {{VARIABLE_NAME}}" show-icon center :closable="false" style="margin-bottom: 18px"/>
        <el-form-item label="Endpoint" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.endpoint" placeholder="Object storage endpoint"/>
        </el-form-item>
        <el-form-item label="Bucket Name" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.bucketName" placeholder="Object storage bucket name"/>
        </el-form-item>
        <el-form-item label="AccessKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.accessKey"/>
        </el-form-item>
        <el-form-item label="SecretKey" v-if="form.type!=='LOCAL'">
          <el-input v-model="form.secretKey"/>
        </el-form-item>
        <el-form-item label="Access URL">
          <el-input v-model="form.address"/>
        </el-form-item>
        <el-form-item label="Storage Path">
          <el-input v-model="form.storagePath" placeholder="Leave blank to store in files directory by default"/>
        </el-form-item>
      </el-form>
      <template #footer>
				<span class="dialog-footer">
					<el-button @click="editVisible = false">Cancel</el-button>
					<el-button type="primary" @click="handleUpdateConfig">OK</el-button>
				</span>
      </template>
    </el-dialog>

  </div>
</template>

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {Delete, Edit, Plus} from '@element-plus/icons-vue';
import {
  createStorageConfig,
  deleteStorageConfig,
  getStorageConfigList,
  markAsDefaultConfig,
  updateStorageConfig
} from "../api/storage";
import {ElMessage, ElMessageBox} from "element-plus";

interface Storage {
  id?: number | null;
  storageId?: string | null;
  name: string;
  type: string;
  endpoint?: string;
  accessKey?: string;
  secretKey?: string;
  bucketName?: string;
  isDefault?: boolean;
  address?: string;
  storagePath?: string;
  createUser?: string;
  createTime?: string;
}

class StorageImpl implements Storage {
  id = null;
  endpoint = 'oss-cn-shanghai.aliyuncs.com';
  name = '';
  type = 'OSS';
  storagePath = 'files';
  accessKey = '';
  secretKey = '';
  bucketName = '';
  isDefault = false;
  address = '';
  createUser = '';
  createTime = '';
}

const addVisible = ref<boolean>(false);
const editVisible = ref<boolean>(false);

let form = reactive<Storage>(new StorageImpl());

const tableData = ref<Storage[]>([]);

// Get table data
const getData = () => {
  getStorageConfigList()
    .then(res => {
      tableData.value = res.data;
    });
};
getData();

const handleEdit = (record: any) => {
  form.id = record.id;
  form.endpoint = record.endpoint;
  form.name = record.name;
  form.type = record.type;
  form.storagePath = record.storagePath;
  form.accessKey = record.accessKey;
  form.secretKey = record.secretKey;
  form.bucketName = record.bucketName;
  form.isDefault = record.isDefault;
  form.address = record.address;

  editVisible.value = true;
}

const handleDelete = (record: any) => {
  ElMessageBox.confirm('Are you sure you want to delete?', 'Warning', {
    type: 'warning'
  }).then(() => {
    deleteStorageConfig(record.id).then(res => {
      getData();
      ElMessage.success('Deleted successfully');
    });
  }).catch(() => {
  });

}


const handleTypeChange = () => {
  switch (form.type) {
    case 'OSS':
      Object.assign(form, {endpoint: 'oss-cn-shanghai.aliyuncs.com'});
      break;
    case 'OBS':
      Object.assign(form, {endpoint: 'obs.cn-north-4.myhuaweicloud.com'});
      break;
    default:
      Object.assign(form, {endpoint: ''})
  }
}

const handleCreateConfig = () => {
  createStorageConfig(form).then(res => {
    getData();
    ElMessage.success('Added successfully');
    addVisible.value = false;
  })
}

const handleUpdateConfig = () => {
  updateStorageConfig(form.id || -1, form).then(res => {
    getData();
    ElMessage.success('Updated successfully');
    editVisible.value = false;
  });
}

const handleMarkAsDefaultConfig = (record: any) => {
  markAsDefaultConfig(record.id).then(res => {
    getData();
  })
}


</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.mr10 {
  margin-right: 10px;
}
</style>
