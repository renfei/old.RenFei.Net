<template>
    <div style="height: 100%;">
        <el-container>
            <el-header id="header" height="60px" style="padding: 0;">
                <el-menu
                        default-active="1"
                        class="el-menu-demo"
                        mode="horizontal"
                        background-color="#252a2f"
                        text-color="#fff"
                        active-text-color="#1989fa">
                    <el-menu-item index="1" style="width: 249px;">
                        <img width="40" height="40" src="//cdn.renfei.net/logo/RF.svg"/>
                        <span>RenFei.Net Dashboard</span>
                    </el-menu-item>
                    <el-menu-item index="2">
                        Dashboard
                    </el-menu-item>
                </el-menu>
            </el-header>
            <el-container height="100%">
                <el-aside width="250px" height="100%" id="leftMenu">
                    <el-menu>
                        <NavMenu :navlist="menu"></NavMenu>
                    </el-menu>
                </el-aside>
                <el-main height="100%">
                    <router-view/>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<script type="text/ecmascript-6">
    import {getMenu} from '../api/index'
    import NavMenu from './NavMenu.vue'
    export default {
        data(){
            return {
                menu: []
            }
        },
        mounted(){
            this.getMenu();
        },
        methods: {
            getMenu(){
                getMenu().then(res => {
                    this.menu = res.data;
                });
            },
            handleRoute (menu) {
                // 通过菜单URL跳转至指定路由
                this.$router.push(menu.menuLink)
            }
        },
        components: {
            'NavMenu': NavMenu
        }
    }
</script>