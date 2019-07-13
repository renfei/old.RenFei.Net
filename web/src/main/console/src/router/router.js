import Main from '@/components/Main.vue';
// 不作为Main组件的子页面展示的页面单独写，如下
export const loginRouter = {
    path: '/auth/signin',
    name: 'signin',
    meta: {
        title: '登录'
    },
    component: () => import('@/components/signin')
};

// 作为Main组件的子页面展示但是不在左侧菜单显示的路由写在otherRouter里
export const otherRouter = {
    path: '/',
    name: 'otherRouter',
    redirect: '/Dashboard',
    component: Main,
    children: [
        { path: 'Dashboard', title: 'Dashboard', name: 'Dashboard', component: () => import('@/components/Dashboard.vue') },
        { path: 'Ue', title: 'Ue', name: 'Ue', component: () => import('@/components/ue.vue') }
]
};
// 所有上面定义的路由都要写在下面的routers里
export const routers = [
    loginRouter,
    otherRouter
];