<template>
    <div style="height: 100%;">
        <el-container>
            <el-header id="header" height="50px">Header</el-header>
            <el-container height="100%">
                <el-aside width="280px" height="100%" id="leftMenu">
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