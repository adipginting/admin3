import { createRouter, createWebHashHistory, RouteRecordRaw } from "vue-router";
import Home from "../views/home.vue";
import { useBasicStore } from "../store/basic";

const routes: RouteRecordRaw[] = [
  {
    path: "/",
    redirect: "/dashboard",
  },
  {
    path: "/",
    name: "Home",
    component: Home,
    children: [
      {
        path: "/dashboard",
        name: "dashboard",
        meta: {
          title: "Dashboard",
        },
        component: () =>
          import(/* webpackChunkName: "dashboard" */ "../views/dashboard.vue"),
      },
      {
        path: "/users",
        name: "users",
        meta: {
          title: "User List",
        },
        component: () =>
          import(/* webpackChunkName: "dashboard" */ "../views/user-list.vue"),
      },
      {
        path: "/roles",
        name: "roles",
        meta: {
          title: "Role Management",
        },
        component: () =>
          import(/* webpackChunkName: "dashboard" */ "../views/role-list.vue"),
      },
      {
        path: "/resources",
        name: "resources",
        meta: {
          title: "Permission Resources",
        },
        component: () =>
          import(
            /* webpackChunkName: "dashboard" */ "../views/resource-list.vue"
          ),
      },
      {
        path: "/logs",
        name: "logs",
        meta: {
          title: "System Logs",
        },
        component: () =>
          import(/* webpackChunkName: "dashboard" */ "../views/log-list.vue"),
      },
      {
        path: "/storage",
        name: "storage",
        meta: {
          title: "Object Storage",
        },
        component: () =>
          import(
            /* webpackChunkName: "dashboard" */ "../views/storage-list.vue"
          ),
      },
      {
        path: "/tabs",
        name: "tabs",
        meta: {
          title: "Tabs",
          permiss: "3",
        },
        component: () =>
          import(/* webpackChunkName: "tabs" */ "../views/tabs.vue"),
      },
      {
        path: "/user",
        name: "user",
        meta: {
          title: "Personal Center",
        },
        component: () =>
          import(/* webpackChunkName: "user" */ "../views/user.vue"),
      },
    ],
  },
  {
    path: "/login",
    name: "Login",
    meta: {
      title: "Login",
    },
    component: () =>
      import(/* webpackChunkName: "login" */ "../views/login.vue"),
  },
  {
    path: "/403",
    name: "403",
    meta: {
      title: "No Permission",
    },
    component: () => import(/* webpackChunkName: "403" */ "../views/403.vue"),
  },
];

const router = createRouter({
  history: createWebHashHistory(),
  routes,
});

router.beforeEach(async (to, from, next) => {
  document.title = `${to.meta.title} | Admin3 Management System`;
  const token = localStorage.getItem("token");
  const basicStore = useBasicStore();
  if (!token && to.path !== "/login") {
    next("/login");
  } /*else if (to.meta.permiss && !permiss.key.includes(to.meta.permiss)) {
        // If no permission, go to 403
        next('/403');
    }*/ else {
    if (token) {
      await basicStore.fetchUserinfo();
    }
    next();
  }
});
export default router;
