<template>
  <div>
    <div class="container">
      <el-row>
        <el-col :xl="6" :lg="6" style="border-right: 1px solid #dcdfe6;">
          <div style="padding-right: 10px">
            <div style="margin-bottom: 24px; font-weight: 700">Organization</div>
            <el-divider></el-divider>
            <el-tree
              :data="treeData"
              node-key="id"
              :current-node-key="selectedNode?.id"
              :highlight-current="true"
              :expand-on-click-node="false"
              :default-expanded-keys="defaultExpandedKeys"
              @node-click="handleNodeSelected"
              @node-expand="handleNodeExpand"
              ref="treeRef"
            >
              <template #default="{ node, data }">
              <span class="custom-tree-node">
                <el-icon v-if="data.type === OrgTypeEum.DEPART"><office-building/></el-icon>
                <el-icon v-else><SuitcaseLine/></el-icon>
                <span style="margin: 0 4px">{{ data.name }}</span>
                <el-dropdown trigger="hover">
                  <el-icon @click.stop><setting/></el-icon>
                  <template #dropdown>
                    <el-dropdown-menu>
                      <span v-action:organization:create>
                        <el-dropdown-item
                          @click.stop="handleAddOrgNode(data)">Add</el-dropdown-item>
                      </span>
                      <span v-if="data.id!==1" v-action:organization:create>
                        <el-dropdown-item
                          @click.stop="handleUpdateOrgNode(data,node?.parent?.data)"
                        >
                        Edit
                      </el-dropdown-item>
                      </span>
                      <span v-if="data.id!=1" v-action:organization:delete>
                        <el-dropdown-item @click.stop="saveDeleteOrgNode(data)">
                        <span style="color: #f56c6c">Delete</span>
                      </el-dropdown-item>
                      </span>
                    </el-dropdown-menu>
                  </template>
                </el-dropdown>
              </span>
              </template>
            </el-tree>
          </div>
        </el-col>
        <el-col :xl="18" :lg="18" style="border-right: 1px solid #dcdfe6">
          <div style="padding-left: 10px">
            <div class="handle-box">
              <el-input v-model="query.username" placeholder="Username" class="handle-input mr10" clearable></el-input>
              <el-select v-model="query.state" placeholder="Status" class="handle-select mr10" clearable>
                <el-option key="1" label="Normal" value="NORMAL"></el-option>
                <el-option key="2" label="Locked" value="LOCKED"></el-option>
              </el-select>
              <el-date-picker
                v-model="query.lastLoginTimeStart"
                type="datetime"
                placeholder="Last login time start"
                class="handle-select mr10"
                clearable
                value-format="YYYY-MM-DD HH:mm:ss"
              ></el-date-picker>
              <el-date-picker
                v-model="query.lastLoginTimeEnd"
                type="datetime"
                placeholder="Last login time end"
                class="handle-select mr10"
                clearable
                value-format="YYYY-MM-DD HH:mm:ss"
              ></el-date-picker>
              <el-button type="primary" :icon="Search" @click="handleSearch">Search</el-button>
              <el-button type="primary" :icon="Plus" @click="addVisible = true;Object.assign(form, new User())"
                         v-action:user:create>Add
              </el-button>
            </div>
            <el-table :data="tableData" border class="table" ref="multipleTable" header-cell-class-name="table-header">
              <el-table-column prop="id" label="ID" width="55" align="center"></el-table-column>
              <el-table-column prop="username" label="Username"></el-table-column>
              <el-table-column prop="gender" label="Gender">
                <template #default="{ row }">
                  <span>{{ row.gender === 'MALE' ? 'Male' : 'Female' }}</span>
                </template>
              </el-table-column>
              <el-table-column label="Avatar" align="center">
                <template #default="scope">
                  <el-image
                    class="table-td-thumb"
                    :src="scope.row.avatar"
                    :z-index="10"
                    :preview-src-list="[scope.row.avatar]"
                    preview-teleported
                  >
                  </el-image>
                </template>
              </el-table-column>
              <el-table-column label="Status" align="center">
                <template #default="scope">
                  <el-tag
                    :type="scope.row.state === 'NORMAL' ? 'success' : scope.row.state === 'LOCKED' ? 'danger' : ''"
                  >
                    {{ scope.row.state === 'NORMAL' ? 'Normal' : scope.row.state === 'LOCKED' ? 'Locked' : '' }}
                  </el-tag>
                </template>
              </el-table-column>

              <el-table-column prop="createdTime" label="Registration Time"></el-table-column>
              <el-table-column prop="lastLoginTime" label="Last Login Time"></el-table-column>
              <el-table-column prop="orgFullName" label="Organization"></el-table-column>
              <el-table-column label="Operations" width="300" fixed="right">
                <template #default="scope">
                  <el-popconfirm title="Disable this user?" @confirm="handleDisable(scope.row)">
                    <template #reference>
                      <el-button v-if="scope.row.state==='NORMAL'" text :icon="Lock" v-action:user:update>
                        Disable
                      </el-button>
                    </template>
                  </el-popconfirm>
                  <el-popconfirm title="Enable this user?" @confirm="handleEnable(scope.row)">
                    <template #reference>
                      <el-button v-if="scope.row.state==='LOCKED'" text :icon="Unlock" v-action:user:update>
                        Enable
                      </el-button>
                    </template>
                  </el-popconfirm>
                  <el-button text :icon="Edit" @click="handleEdit(scope.$index, scope.row)" v-action:user:update>
                    Edit
                  </el-button>
                  <el-button text :icon="Delete" class="red" @click="handleDelete(scope.row)" v-action:user:delete>
                    Delete
                  </el-button>
                </template>
              </el-table-column>
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
        </el-col>
      </el-row>
    </div>

    <!-- Add Organization -->
    <el-dialog title="Add" v-model="addOrgDialogVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="Parent Node">
          <el-input v-model="orgForm.parentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name">
          <el-input v-model="orgForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Type" prop="type">
          <el-radio-group v-model="orgForm.type">
            <el-radio :label="OrgTypeEum.DEPART">Department</el-radio>
            <el-radio :label="OrgTypeEum.JOB">Job</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addOrgDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveAddOrgNode">OK</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- Edit Organization -->
    <el-dialog title="Edit" v-model="updateOrgDialogVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="Parent Node">
          <el-input v-model="orgForm.parentName" disabled></el-input>
        </el-form-item>
        <el-form-item label="Name">
          <el-input v-model="orgForm.name"></el-input>
        </el-form-item>
        <el-form-item label="Type" prop="type">
          <el-radio-group v-model="orgForm.type" disabled>
            <el-radio :label="OrgTypeEum.DEPART">Department</el-radio>
            <el-radio :label="OrgTypeEum.JOB">Job</el-radio>
          </el-radio-group>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="updateOrgDialogVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveUpdateOrgNode">OK</el-button>
        </span>
      </template>
    </el-dialog>

    <!-- Add User Dialog -->
    <el-dialog title="Add" v-model="addVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="Organization">
          <el-input v-model="selectedNode.name" disabled></el-input>
        </el-form-item>
        <el-input v-model="form.avatar" hidden="hidden" v-show="false"></el-input>
        <el-form-item label="Username">
          <el-input v-model="form.username"></el-input>
        </el-form-item>
        <el-form-item label="Gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="MALE">Male</el-radio>
            <el-radio label="FEMALE">Female</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Avatar">
          <AvatarUpload :img-src="form.avatar" @on-select="onAvatarSelect"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="addVisible = false">Cancel</el-button>
          <el-button type="primary" @click="saveAdd">OK</el-button>
        </span>
      </template>
    </el-dialog>
    <!-- Edit User Dialog -->
    <el-dialog title="Edit" v-model="editVisible" width="30%">
      <el-form label-width="70px">
        <el-form-item label="Organization">
          <el-input v-model="selectedNode.name" disabled></el-input>
        </el-form-item>
        <el-input v-model="form.avatar" hidden="hidden" v-show="false"></el-input>
        <el-form-item label="Username">
          <el-input v-model="form.username" disabled></el-input>
        </el-form-item>
        <el-form-item label="Gender">
          <el-radio-group v-model="form.gender">
            <el-radio label="MALE">Male</el-radio>
            <el-radio label="FEMALE">Female</el-radio>
          </el-radio-group>
        </el-form-item>
        <el-form-item label="Avatar">
          <AvatarUpload :img-src="form.avatar" @on-select="onAvatarSelect"/>
        </el-form-item>
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

<script setup lang="ts">
import {reactive, ref} from 'vue';
import {ElMessage, ElMessageBox} from 'element-plus';
import {Delete, Edit, Lock, Plus, Search, Unlock} from '@element-plus/icons-vue';
import {createUser, deleteUser, disableUser, enableUser, updateUser} from "../api/user";
import {
  createOrganization,
  deleteOrganization,
  getOrganizationTree,
  getOrganizationUserList,
  updateOrganization
} from "../api/organization";
import cloneDeep from 'lodash/cloneDeep';
import AvatarUpload from "../components/AvatarUpload.vue";

interface OrgTreeNode {
  name: string
  id: number
  children?: OrgTreeNode[]
  type: OrgTypeEum
  isLeaf?: boolean
}

interface OrgSelectableTreeNode extends Partial<Omit<OrgTreeNode, 'children'>> {
  label: string
  value: number
  children?: OrgSelectableTreeNode[] | OrgTreeNode[]
}

enum OrgTypeEum {
  DEPART = 'DEPARTMENT',
  JOB = 'JOB',
}

const rootNode = {name: 'Root Node', id: 1, type: OrgTypeEum.DEPART, children: []};

const treeData = ref<OrgTreeNode[]>([rootNode]);
const defaultExpandedKeys = ref<number[]>([1]);
const selectedNode = ref<OrgTreeNode>(rootNode);

const handleNodeSelected = (node: OrgTreeNode) => {
  selectedNode.value = node;
  getUserData();
}

const handleNodeExpand = (d: any) => {
  // console.log(d, 'expand')
}
const reqAllNodes = async () => {
  const res = await getOrganizationTree({
    parentId: 1
  })
  treeData.value[0].children = res.data;

  const cloneTreeData = cloneDeep(treeData.value);
  convertOrgTree(cloneTreeData);
}
reqAllNodes();


class Org {
  id = 0;
  name = '';
  type = OrgTypeEum.DEPART;
  parentId = 1;
  parentName = '';
}

let orgForm = reactive(new Org());

// When adding to table
const addOrgDialogVisible = ref(false);

const updateOrgDialogVisible = ref(false);

const handleAddOrgNode = (data: OrgTreeNode) => {
  Object.assign(orgForm, new Org());
  orgForm.parentName = data.name;
  orgForm.parentId = data.id;
  addOrgDialogVisible.value = true;
}

const handleUpdateOrgNode = (data: OrgTreeNode, parent: OrgTreeNode) => {
  orgForm.id = data.id;
  orgForm.name = data.name;
  orgForm.type = data.type;
  orgForm.parentId = parent.id;
  orgForm.parentName = parent.name;
  updateOrgDialogVisible.value = true;
}

const saveAddOrgNode = async () => {
  await createOrganization(orgForm);
  addOrgDialogVisible.value = false;
  await reqAllNodes();
}

/**
 * @desc Convert array object key-value pairs el-tree => el-tree-select
 */
const convertOrgTree = (orgTreeNode: Partial<(OrgTreeNode & { value: number, label: string })>[]) => {
  if (!orgTreeNode.length) {
    return;
  }

  for (let org of orgTreeNode) {
    org.value = org.id as number;
    org.label = org.name as string;

    if (org.children?.length) {
      convertOrgTree(org.children);
    }
  }
}

const saveUpdateOrgNode = async () => {
  await updateOrganization(orgForm.id, orgForm);
  updateOrgDialogVisible.value = false;
  await reqAllNodes();
}

const saveDeleteOrgNode = async (data: OrgTreeNode) => {
  // Confirm delete
  ElMessageBox.confirm('Are you sure you want to delete?', 'Warning', {
    type: 'warning'
  }).then(() => {
    deleteOrganization(data.id).then(res => {
      ElMessage.success('Deleted successfully');
      reqAllNodes();
    });
  }).catch(() => {
  });

}

interface TableItem {
  id: number;
  username: string;
  gender: string;
  avatar: string;
  state: string;
  createdTime: string;
  lastLoginTime: string;
  orgFullName: string;
}

const query = reactive({
  username: '',
  state: '',
  lastLoginTimeStart: '',
  lastLoginTimeEnd: '',
  pageIndex: 1,
  pageSize: 10
});
const tableData = ref<TableItem[]>([]);
const pageTotal = ref(0);
// Get table data
const getUserData = () => {
  getOrganizationUserList(selectedNode.value.id, {
    page: query.pageIndex,
    size: query.pageSize,
    username: query.username || undefined,
    state: query.state || undefined,
    lastLoginTimeStart: query.lastLoginTimeStart || undefined,
    lastLoginTimeEnd: query.lastLoginTimeEnd || undefined,
  }).then(res => {
    tableData.value = res.data.list;
    pageTotal.value = res.data.total;
  });
};
getUserData();

// Search operation
const handleSearch = () => {
  query.pageIndex = 1;
  getUserData();
};
// Pagination
const handlePageChange = (val: number) => {
  query.pageIndex = val;
  getUserData();
};

// Delete operation
const handleDelete = (row: any) => {
  // Confirm delete
  ElMessageBox.confirm('Are you sure you want to delete?', 'Warning', {
    type: 'warning'
  }).then(() => {
    deleteUser(row.id).then(res => {
      getUserData();
    });
    ElMessage.success('Deleted successfully');
  })
    .catch(() => {
    });
};

// When adding to table
const addVisible = ref(false);

// Edit popup and save
const editVisible = ref(false);

class User {
  username = '';
  gender = 'MALE';
  avatar = '';
  organizationId = 1;
}

let id: number = 0;
let form = reactive(new User());

const saveAdd = () => {
  form.organizationId = selectedNode.value.id;
  createUser(form).then(res => {
    getUserData();
    ElMessage.success(`Added successfully`);
    addVisible.value = false;
  });
};
const handleEdit = (index: number, row: any) => {
  id = row.id;
  form.username = row.username;
  form.gender = row.gender;
  form.avatar = row.avatar;
  editVisible.value = true;
};
const saveEdit = () => {
  editVisible.value = false;
  form.organizationId = selectedNode.value.id;
  console.log(form);
  updateUser(id, form).then(res => {
    getUserData();
    ElMessage.success(`Updated successfully`);
  });
};
const handleDisable = (row: any) => {
  disableUser(row.id).then(res => {
    getUserData();
    ElMessage.success(`Disabled successfully`);
  });
}
const handleEnable = (row: any) => {
  enableUser(row.id).then(res => {
    getUserData();
    ElMessage.success(`Enabled successfully`);
  });
}
const onAvatarSelect = (imgUrl: string) => {
  form.avatar = imgUrl;
}

</script>

<style scoped>
.handle-box {
  margin-bottom: 20px;
}

.handle-select {
  width: 120px;
}

.handle-input {
  width: 300px;
}

.table {
  width: 100%;
  font-size: 14px;
}

.red {
  color: #F56C6C;
}

.mr10 {
  margin-right: 10px;
}

.table-td-thumb {
  display: block;
  margin: auto;
  width: 40px;
  height: 40px;
}
</style>
