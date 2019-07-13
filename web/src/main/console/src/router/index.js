import Vue from 'vue'
import VueRouter from 'vue-router'
import {routers, otherRouter} from './router.js';
import {Loading} from 'element-ui';
import Util from '../libs/util';
import {getStore, setStore} from '../libs/storage';

Vue.use(VueRouter)

// 路由配置
const RouterConfig = {
    routes: routers
};
let loadingInstance;

export const router = new VueRouter(RouterConfig);

router.beforeEach((to, from, next) => {
    Util.title(to.meta.title);
    loadingInstance = Loading.service({
        fullscreen: true,
        lock: true,
        text: 'Loading....'
    });
    if (to.name != 'signin') {
        let Authorization = getStore("Authorization");
        if (Authorization == undefined || Authorization == null || Authorization == '') {
            router.push('/auth/signin');
            return;
        }
    }
    next()
});

router.afterEach((to) => {
    loadingInstance.close();
});