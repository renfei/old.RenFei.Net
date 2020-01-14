<template>
    <div>
        <el-container>
            <el-header>
                <el-button @click="newpost" type="primary" icon="el-icon-edit">创作文章</el-button>
            </el-header>
            <el-main>
                <el-table
                        :data="tableData"
                        border
                        max-height="600"
                        style="width: 100%">
                    <el-table-column
                            label="标题"
                            prop="title"
                            width="400">
                    </el-table-column>
                    <el-table-column
                            label="分类"
                            prop="categoryId"
                            width="180">
                    </el-table-column>
                    <el-table-column
                            prop="views"
                            width="80"
                            label="浏览量">
                    </el-table-column>
                    <el-table-column
                            width="60"
                            label="原创">
                        <template slot-scope="scope">
                            <p>{{ scope.row.isOriginal?'原创':'非原创' }}</p>
                        </template>
                    </el-table-column>
                    <el-table-column
                            prop="releaseTime"
                            width="180"
                            label="发布时间">
                    </el-table-column>
                    <el-table-column
                            prop="addTime"
                            width="180"
                            label="添加时间">
                    </el-table-column>
                    <el-table-column fixed="right" width="200"
                                     label="操作">
                        <template slot-scope="scope">
                            <el-button
                                    size="mini"
                                    icon="el-icon-edit"
                                    @click="handleEdit(scope.$index, scope.row)">编辑
                            </el-button>
                            <el-button
                                    size="mini"
                                    icon="el-icon-delete"
                                    type="danger"
                                    @click="handleDelete(scope.$index, scope.row)">删除
                            </el-button>
                        </template>
                    </el-table-column>
                </el-table>
                <div class="block">
                    <el-pagination
                            @size-change="handleSizeChange"
                            @current-change="handleCurrentChange"
                            :current-page="pagination.page"
                            :page-sizes="[10, 20, 30, 50, 100, 200, 300, 500]"
                            :page-size="pagination.rows"
                            layout="total, sizes, prev, pager, next, jumper"
                            :total="pagination.total">
                    </el-pagination>
                </div>
            </el-main>
        </el-container>
        <el-dialog
                title="提示"
                :visible.sync="dialogVisible"
                width="30%">
            <span>将要进行删除《{{deletetitle}}》操作，确定删除吗？</span>
            <span slot="footer" class="dialog-footer">
    <el-button @click="dialogVisible = false">取 消</el-button>
    <el-button type="primary" @click="deletepost">确 定</el-button>
  </span>
        </el-dialog>
    </div>
</template>
<script type="text/ecmascript-6">
    import {getAllPosts, deletePosts, getTags} from '../api/index';
    import {Notification} from 'element-ui';
    export default {
        data() {
            return {
                allTags: [],
                dialogVisible: false,
                deleteid: null,
                deletetitle: null,
                pagination: {
                    rows: 10,
                    page: 1,
                    total: 0
                },
                tableData: []
            }
        },
        mounted(){
            this.getAllTags();
            this.getAllPosts();
        },
        methods: {
            getAllTags(){
                getTags().then(res => {
                    this.allTags = res.data;
                });
            },
            newpost(){
                this.$router.push({path: '/Posts/Edit/0/'});
            },
            deletepost(){
                this.dialogVisible = false;
                deletePosts({
                    id: this.deleteid
                }).then(res => {
                    if (res.success) {
                        Notification.success({
                            title: 'Success!',
                            message: "删除成功！"
                        });
                        this.getAllPosts();
                    } else {
                        Notification.error({
                            title: 'Error!',
                            message: res.message
                        });
                    }
                });
            },
            getAllPosts(){
                getAllPosts({
                    pages: this.pagination.page,
                    rows: this.pagination.rows
                }).then(res => {
                    if (res.success) {
                        this.pagination.total = res.data.count;
                        this.tableData = res.data.postsList;
                        this.format();
                    } else {
                        Notification.error({
                            title: 'Error!',
                            message: res.message
                        });
                    }
                });
            },
            dateFormat(time) {
                var date = new Date(time);
                var year = date.getFullYear();
                /* 在日期格式中，月份是从0开始的，因此要加0
                 * 使用三元表达式在小于10的前面加0，以达到格式统一  如 09:11:05
                 * */
                var month = date.getMonth() + 1 < 10 ? "0" + (date.getMonth() + 1) : date.getMonth() + 1;
                var day = date.getDate() < 10 ? "0" + date.getDate() : date.getDate();
                var hours = date.getHours() < 10 ? "0" + date.getHours() : date.getHours();
                var minutes = date.getMinutes() < 10 ? "0" + date.getMinutes() : date.getMinutes();
                var seconds = date.getSeconds() < 10 ? "0" + date.getSeconds() : date.getSeconds();
                // 拼接
                return year + "-" + month + "-" + day + " " + hours + ":" + minutes + ":" + seconds;
            },
            handleEdit(index, row) {
                this.$router.push({path: '/Posts/Edit/' + row.id + "/"});
            },
            handleDelete(index, row) {
                this.dialogVisible = true;
                this.deleteid = row.id;
                this.deletetitle = row.title;
            },
            handleSizeChange(val) {
                this.pagination.rows = val;
                this.getAllPosts();
            },
            handleCurrentChange(val) {
                this.pagination.page = val;
                this.getAllPosts();
            },
            format(){
                for (var i = 0; i < this.tableData.length; i++) {
                    this.tableData[i].addTime = this.dateFormat(this.tableData[i].addTime);
                    this.tableData[i].releaseTime = this.dateFormat(this.tableData[i].releaseTime);
                    for (var j = 0; j < this.allTags.length; j++) {
                        if (this.tableData[i].categoryId == this.allTags[j].key) {
                            this.tableData[i].categoryId = this.allTags[j].label;
                        }
                    }
                }
            }
        }
    }
</script>