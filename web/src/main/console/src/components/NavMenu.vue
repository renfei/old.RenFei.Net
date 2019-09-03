<template>
    <div>
        <!-- 根据实际情况做修改 -->

        <el-submenu v-for="(item,index) in navlist" :key="index" v-if="item.submenu!=null"
                    :index="item.menuName">
            <template slot="title">
                <i class="el-icon-location"></i>
                <span>{{item.menuName}}</span>
            </template>
            <NavMenu :navlist="item.submenu"></NavMenu>
        </el-submenu>

        <el-menu-item v-for="(item,index) in navlist" :key="index" v-if="item.submenu==null"
                      :index="item.menuLink"><i class="el-icon-location"></i>
            <span>{{item.menuName}}</span></el-menu-item>


    </div>
</template>

<script>
    export default {
        name: "NavMenu",
        props: ["navlist"],
        data() {
            return {};
        },
        methods: {}
    };
</script>