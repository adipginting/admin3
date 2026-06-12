<template>
  <div>
    <div class="container">
      <el-table
          :data="tableData"
          style="width: 100%; margin-bottom: 20px"
          row-key="id"
          border
          default-expand-all
      >
        <el-table-column prop="name" label="Name" sortable/>
        <el-table-column prop="permission" label="Permission" sortable/>
        <el-table-column prop="gender" label="Type" sortable>
          <template #default="{ row }">
            <span>{{ row.type === 'MENU' ? 'Menu' : 'Button' }}</span>
          </template>
        </el-table-column>
        <el-table-column prop="url" label="URL" sortable/>
        <el-table-column prop="icon" align="center" label="Icon" sortable>
          <template #default="{ row }">
            <el-icon>
              <component :is="row.icon"></component>
            </el-icon>
          </template>
        </el-table-column>
        <el-table-column label="Operations" width="400" fixed="right">
          <template #default="scope">
            <el-button-group>
              <el-button text :icon="Edit" @click="handleEdit(scope.row)" v-action:resource:update>
                Edit
              </el-button>
              <el-button text :icon="Edit" @click="handleAdd(scope.row)" v-action:resource:create>
                Add Sibling
              </el-button>
              <el-button text :icon="Edit" @click="handleAdd(scope.row,true)" v-action:resource:create>
                Add Child
              </el-button>
              <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-action:resource:delete>
                Delete
              </el-button>
            </el-button-group>
          </template>
        </el-table-column>
      </el-table>
    </div>

    <!-- Add Dialog -->
    <el-dialog title="Add" v-model="addVisible" width="35%">
      <el-form label-width="70px">
        <el-form label-width="70px">
          <el-form-item label="Parent Node">
            <el-input v-model="form.parentName" disabled></el-input>
          </el-form-item>
          <el-form-item label="Name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="Permission">
            <el-input v-model="form.permission"></el-input>
          </el-form-item>
          <el-form-item label="Type" prop="region">
            <el-select v-model="form.type" placeholder="Please select">
              <el-option key="MENU" label="Menu" value="MENU"></el-option>
              <el-option key="BUTTON" label="Button" value="BUTTON"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="URL" v-if="form.type==='MENU'">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <el-form-item label="Icon" v-if="form.type==='MENU'">
            <el-input v-model="form.icon" readonly>
              <template #prepend>
                <el-button :icon="form.icon"></el-button>
              </template>
            </el-input>
            <div>
              <AppIcon v-model="form.icon" :selected-icon="form.icon" @change="handleAppIconChange"/>
            </div>
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveAdd">OK</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- Edit Dialog -->
    <el-dialog title="Edit" v-model="editVisible" width="40%">
      <el-form label-width="70px">
        <el-form label-width="70px">
          <el-form-item label="Parent Node">
            <el-input v-model="form.parentName" disabled></el-input>
          </el-form-item>
          <el-form-item label="Name">
            <el-input v-model="form.name"></el-input>
          </el-form-item>
          <el-form-item label="Permission">
            <el-input v-model="form.permission"></el-input>
          </el-form-item>
          <el-form-item label="Type" prop="region">
            <el-select v-model="form.type" placeholder="Please select">
              <el-option key="MENU" label="Menu" value="MENU"></el-option>
              <el-option key="BUTTON" label="Button" value="BUTTON"></el-option>
            </el-select>
          </el-form-item>
          <el-form-item label="URL" v-if="form.type==='MENU'">
            <el-input v-model="form.url"></el-input>
          </el-form-item>
          <el-form-item label="Icon" v-if="form.type==='MENU'">
            <el-input v-model="form.icon" readonly>
              <template #prepend>
                <el-button :icon="form.icon"></el-button>
              </template>
            </el-input>
            <div>
              <AppIcon v-model="form.icon" :model-value="form.icon" :selected-icon="form.icon"
                       @change="handleAppIconChange"/>
            </div>
          </el-form-item>
        </el-form>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="editVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveEdit">OK</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>
<script lang="ts" setup>
import {reactive, ref} from "vue";
import {Delete, Edit} from '@element-plus/icons-vue';
import {createResource, deleteResource, getResourceTree as reqResourceTree, updateResource} from "../api/resource";
import {ElMessage, ElMessageBox} from "element-plus";
import {FIRST_ICON} from "../components/AppIcon.vue";

const tableData = ref<Resource[]>([]);
const addVisible = ref<boolean>(false);
const editVisible = ref<boolean>(false);

let id: number = -1;

class Resource {
  name = '';
  type = 'MENU';
  url? = '';
  icon? = FIRST_ICON;
  permission = '';
  parentId = 1;
  parentName = '';
}

const handleAppIconChange = (icon: string) => {
  form.icon = icon
}

let form = reactive(new Resource());

const handleEdit = (record: any) => {
  id = record.id;
  form.name = record.name;
  form.type = record.type;
  form.url = record.url;
  form.icon = record.icon;
  form.permission = record.permission;
  form.parentId = record.parentId;
  form.parentName = record.parentName;
  editVisible.value = true;
}

const handleAdd = (record: any, isChildNode = false) => {
  Object.assign(form, new Resource());
  if (isChildNode) {
    form.parentId = record.id;
    form.parentName = record.name;
  } else {
    form.parentId = record.parentId;
    form.parentName = record.parentName;
  }
  addVisible.value = true;
}

const handleDelete = (record: any) => {
  // Confirm delete
  ElMessageBox.confirm('Are you sure you want to delete?', 'Warning', {
    type: 'warning'
  }).then(() => {
    deleteResource(record.id).then(res => {
      getResourceTree();
      ElMessage.success('Deleted successfully');
    });
  })
      .catch(() => {
      });
}

const saveAdd = () => {
  const isMenu = form.type == 'MENU';
  createResource({...form, url: isMenu ? form.url : undefined, icon: isMenu ? form.icon : undefined}).then(res => {
    getResourceTree();
    ElMessage.success(`Saved successfully`);
    addVisible.value = false;
  });
}

const saveEdit = () => {
  const isMenu = form.type == 'MENU';
  updateResource(id, {...form, url: isMenu ? form.url : undefined, icon: isMenu ? form.icon : undefined}).then(res => {
    getResourceTree();
    ElMessage.success(`Saved successfully`);
    editVisible.value = false;
  });
}

const getResourceTree = () => {
  reqResourceTree().then(res => {
    tableData.value = res.data;
  });
}
getResourceTree();

</script>
<style scoped>
.red {
  color: #F56C6C;
}
</style>
