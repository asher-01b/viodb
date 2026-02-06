import { createApp } from "vue";

import ElementPlus from "element-plus"
// is necessary, otherwise the components will be transparent or messy
import 'element-plus/dist/index.css'
// to use icons from element-plus
import * as ElementPlusIconsVue from '@element-plus/icons-vue'

import App from "./App.vue";

const app = createApp(App)
app.use(ElementPlus)

// register all icons
for (const [key, component] of Object.entries(ElementPlusIconsVue)) {
  app.component(key, component)
}

app.mount("#app")