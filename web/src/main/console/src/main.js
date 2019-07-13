// The Vue build version to load with the `import` command
// (runtime-only or standalone) has been set in webpack.base.conf with an alias.
import Vue from 'vue'
import ElementUI from 'element-ui';
import { Notification } from 'element-ui';
import 'element-ui/lib/theme-chalk/index.css';
import App from './App'
import {router} from './router/index'
import  VueQuillEditor from 'vue-quill-editor'
import 'quill/dist/quill.core.css'
import 'quill/dist/quill.snow.css'
import 'quill/dist/quill.bubble.css'
import 'font-awesome/css/font-awesome.min.css'
import {getRequest, postRequest, putRequest, deleteRequest, uploadFileRequest} from '@/libs/axios'
import {setStore, getStore, removeStore} from '@/libs/storage'
import encryption from './libs/encryption'

Vue.config.productionTip = false
Vue.use(ElementUI)
Vue.use(VueQuillEditor)
// 挂载全局使用的方法
Vue.prototype.router = router;
Vue.prototype.getRequest = getRequest;
Vue.prototype.postRequest = postRequest;
Vue.prototype.putRequest = putRequest;
Vue.prototype.deleteRequest = deleteRequest;
Vue.prototype.uploadFileRequest = uploadFileRequest;
Vue.prototype.setStore = setStore;
Vue.prototype.getStore = getStore;
Vue.prototype.removeStore = removeStore;
Vue.prototype.encryption = encryption;
Vue.prototype.Notification = Notification;

/* eslint-disable no-new */
new Vue({
    el: '#app',
    router,
    components: {App},
    template: '<App/>',
    created: function () {
        console.log("created:" + this.encryption.encryption("1"));
    }
})
