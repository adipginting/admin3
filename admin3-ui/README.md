# vue-manage-system

<a href="https://github.com/vuejs/vue">
    <img src="https://img.shields.io/badge/vue-3.1.2-brightgreen.svg" alt="vue">
  </a>
  <a href="https://github.com/vuejs/pinia">
    <img src="https://img.shields.io/badge/pinia-2.0.14-brightgreen.svg" alt="pinia">
  </a>
  <a href="https://github.com/lin-xin/vue-manage-system/blob/master/LICENSE">
    <img src="https://img.shields.io/github/license/mashape/apistatus.svg" alt="license">
  </a>
  <a href="https://github.com/lin-xin/vue-manage-system/releases">
    <img src="https://img.shields.io/github/release/lin-xin/vue-manage-system.svg" alt="GitHub release">
  </a>
  <a href="https://lin-xin.gitee.io/example/work/#/donate">
    <img src="https://img.shields.io/badge/%24-donate-ff69b4.svg" alt="donate">
  </a>

A backend management system solution based on Vue3 + pinia + Element Plus. [Online Demo](https://lin-xin.gitee.io/example/work/)

> For the Vue2 version, see [tag-V4.2.0](https://github.com/lin-xin/vue-manage-system/tree/V4.2.0)

[English document](https://github.com/lin-xin/manage-system/blob/master/README_EN.md)

## Sponsors

### Haowen

[<img src="https://static.bestqa.net/logo/bestqa_haowen.png" width="220" height="100">](https://www.bestqa.net/home/index.html)

Professional survey services, one-on-one customer service, customized on demand.

## Support the Author

Buy the author a coffee! (WeChat ID: linxin_20)

![WeChat Scan](https://lin-xin.gitee.io/images/weixin.jpg)

## Preface

This project is a multi-functional backend framework template, suitable for most backend management system development. Based on Vue3 + pinia + typescript, using the Element Plus component library for easy development. The implementation logic is simple, suitable for outsourcing projects, and fast delivery.

## Features

-   [x] Element Plus
-   [x] vite 3
-   [x] pinia
-   [x] typescript
-   [x] Login / Logout
-   [x] Dashboard
-   [x] Table
-   [x] Tab
-   [x] Form
-   [x] Charts :bar_chart:
-   [x] Rich text / Markdown editor
-   [x] Image drag / crop upload
-   [x] Permission management
-   [x] Three-level menu
-   [x] Custom icons


## Installation Steps
> Because vite3 is used, Node version needs to be 14.18+

```
git clone https://github.com/lin-xin/vue-manage-system.git      // Download the template to local
cd vue-manage-system    // Enter the template directory
npm install         // Install project dependencies, wait for installation to complete. If installation fails, you can use cnpm or yarn

// Run
npm run dev

// Execute the build command, place the generated dist folder on the server to access
npm run build
```

## Component Usage Instructions and Demo

### vue-schart

Chart component for vue.js wrapper of sChart.js. Visit: [vue-schart](https://github.com/lin-xin/vue-schart#/)

<p><a href="https://www.npmjs.com/package/vue-schart"><img src="https://img.shields.io/npm/dm/vue-schart.svg" alt="Downloads"></a></p>

```html
<template>
    <div>
        <schart class="wrapper" canvasId="myCanvas" :options="options"></schart>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';
import Schart from "vue-schart"; // Import Schart component
const options = ref({
    type: "bar",
    title: {
        text: "Sales chart by category in the last week",
    },
    labels: ["Mon", "Tue", "Wed", "Thu", "Fri"],
    datasets: [
        {
            label: "Home Appliances",
            data: [234, 278, 270, 190, 230],
        },
        {
            label: "Department Store",
            data: [164, 178, 190, 135, 160],
        },
        {
            label: "Food",
            data: [144, 198, 150, 235, 120],
        },
    ],
})
</script>
<style>
    .wrapper {
        width: 7rem;
        height: 5rem;
    }
</style>
```

## Project Screenshots

### Login

![Image text](https://github.com/lin-xin/manage-system/raw/master/screenshots/wms3.png)

### Home

![Image text](https://github.com/lin-xin/manage-system/raw/master/screenshots/wms1.png)

## License

[MIT](https://github.com/lin-xin/vue-manage-system/blob/master/LICENSE)
