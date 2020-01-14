<template>
    <div>
        <el-container>
            <el-header>
                <el-input v-model="posts.title" placeholder="文章标题"></el-input>
            </el-header>
            <el-container>
                <el-aside width="250px">
                    <el-form label-position="right" label-width="60px">
                        <el-form-item label="ID">
                            <el-input placeholder="原文ID" v-model="posts.id" class="input-with-select">
                                <el-button slot="append" @click="getPost" icon="el-icon-search"></el-button>
                            </el-input>
                        </el-form-item>
                        <el-form-item label="分类">
                            <el-select v-model="posts.categoryId" placeholder="文章分类">
                                <el-option
                                        v-for="item in cat"
                                        :key="item.id"
                                        :label="item.zhName"
                                        :value="item.id">
                                </el-option>
                            </el-select>
                        </el-form-item>
                        <el-form-item label="时间">
                            <el-date-picker
                                    style="width:190px;"
                                    v-model="posts.releaseTime"
                                    type="datetime"
                                    placeholder="选择发布时间">
                            </el-date-picker>
                        </el-form-item>
                        <el-form-item label="图像">
                            <el-upload
                                    :headers="headers"
                                    class="avatar-uploader"
                                    action="/api/upload/image"
                                    :show-file-list="false"
                                    :on-success="handleAvatarSuccess"
                                    :before-upload="beforeAvatarUpload">
                                <img v-if="posts.featuredImage" :src="posts.featuredImage" class="avatar">
                                <i v-else class="el-icon-plus avatar-uploader-icon"></i>
                            </el-upload>
                        </el-form-item>
                        <el-form-item label="原创">
                            <el-switch
                                    v-model="posts.isOriginal"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>
                        <el-form-item label="原链">
                            <el-input v-model="posts.sourceUrl" placeholder="原文链接"></el-input>
                        </el-form-item>
                        <el-form-item label="作者">
                            <el-input v-model="posts.sourceName" placeholder="文章作者"></el-input>
                        </el-form-item>
                        <el-form-item label="评论">
                            <el-switch
                                    v-model="posts.isComment"
                                    active-color="#13ce66"
                                    inactive-color="#ff4949">
                            </el-switch>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="reset">重置</el-button>
                        </el-form-item>
                        <el-form-item>
                            <el-button @click="back"type="info">返回</el-button>
                            <el-button @click="submit" type="primary">提交</el-button>
                        </el-form-item>
                    </el-form>
                </el-aside>
                <el-main style="padding-top: 0;">
                    <keep-alive>
                        <vue-neditor-wrap v-model="posts.content" :config="myConfig"
                                          :destroy="false"></vue-neditor-wrap>
                    </keep-alive>
                    <hr>
                    <el-container>
                        <el-aside width="500px">
                            <el-transfer v-model="tags" :data="allTags" :titles="titles"></el-transfer>
                        </el-aside>
                        <el-main>
                            <el-form label-position="right" label-width="70px">
                                <el-form-item label="文章简介">
                                    <el-input
                                            type="textarea"
                                            autosize
                                            placeholder="请输入文章简介"
                                            :autosize="{ minRows: 9}"
                                            v-model="posts.describes">
                                    </el-input>
                                </el-form-item>
                                <el-form-item label="关键词">
                                    <el-input v-model="posts.keyword" placeholder="关键词"></el-input>
                                </el-form-item>
                            </el-form>
                        </el-main>
                    </el-container>
                </el-main>
            </el-container>
        </el-container>
    </div>
</template>
<style>
    .avatar-uploader .el-upload {
        border: 1px dashed #d9d9d9;
        border-radius: 6px;
        cursor: pointer;
        position: relative;
        overflow: hidden;
    }

    .avatar-uploader .el-upload:hover {
        border-color: #409EFF;
    }

    .avatar-uploader-icon {
        font-size: 28px;
        color: #8c939d;
        width: 188px;
        height: 188px;
        line-height: 178px;
        text-align: center;
    }

    .avatar {
        width: 178px;
        height: 178px;
        display: block;
    }
</style>
<script>
    import {Notification} from 'element-ui';
    import VueNeditorWrap from 'vue-neditor-wrap';
    import {getCat, getTags, getPosts, getTagsByTargetId,postPosts,putPosts} from '../api/index';
    export default {
        data(){
            return {
                tags: [],
                allTags: [],
                titles: [
                    '待选标签', '文章标签'
                ],
                myConfig: {
                    // 如果需要上传功能,找后端小伙伴要服务器接口地址
                    serverUrl: '/api/upload/ueditor',
                    // 你的UEditor资源存放的路径,相对于打包后的index.html
                    UEDITOR_HOME_URL: '/static/neditor/',
                    // 编辑器自动被内容撑高
                    autoHeightEnabled: true,
                    // 初始容器高度
                    initialFrameHeight: 240,
                    // 初始容器宽度
                    initialFrameWidth: '100%',
                    // 关闭自动保存
                    enableAutoSave: false
                },
                cat: [{
                    id: 0,
                    typeId: 0,
                    enName: "",
                    zhName: "",
                    featuredImage: "",
                    typeName: "",
                    uriPath: ""
                }],
                posts: {
                    id: null,
                    categoryId: null,
                    isOriginal: true,
                    releaseTime: "",
                    isComment: true,
                    featuredImage: "",
                    title: "",
                    content: "",
                    sourceUrl: "",
                    sourceName: "",
                    describes: "",
                    keyword: ""
                },
                headers: {
                    'Authorization': window.localStorage.getItem("Authorization"),
                    'uuid': window.localStorage.getItem("uuid")
                }
            }
        },
        mounted(){
            this.getCat();
            this.getTags();
            this.getid();
        }, components: {
            VueNeditorWrap
        }, methods: {
            getid(){
                var id = this.$route.params.id;
                if (id != undefined && id != null && id != "" && id != 0) {
                    this.posts.id = id;
                    this.getPost();
                }else {
                    this.reset();
                }
            },
            back(){
                this.$router.push({path:'/Posts'});
            },
            getPost(){
                var pid = this.posts.id;
                getPosts({"id": pid}).then(res => {
                    if (res.data != undefined) {
                        this.posts = res.data;
                        getTagsByTargetId({"postid":this.posts.id}).then(res=>{
                            if(res.data!=undefined){
                                for(var i=0;i<res.data.length;i++){
                                    this.tags.push(res.data[i].key);
                                }
                            }
                        });
                    } else {
                        Notification.error({
                            title: 'Error',
                            message: "未找到该文章！"
                        });
                    }
                });
            },
            getCat(){

                //文章的Type是1
                getCat({type: 1}).then(res => {
                    this.cat = res.data;
                });
            },
            getTags(){
                getTags().then(res => {
                    this.allTags = res.data;
                });
            },
            submit(){
                if(this.posts.id!=undefined&&this.posts.id!=null){
                    putPosts({
                        posts:JSON.stringify(this.posts),
                        tags:this.tags
                    }).then(res=>{
                        if(res.success){
                            Notification.success({
                                title: 'Success',
                                message: "提交成功！"
                            });
                        }else {
                            Notification.error({
                                title: 'Error!',
                                message: res.message
                            });
                        }
                    });
                }else {
                    postPosts({
                        posts:JSON.stringify(this.posts),
                        tags:this.tags
                    }).then(res=>{
                        if(res.success){
                            Notification.success({
                                title: 'Success',
                                message: "提交成功！"
                            });
                            this.posts = res.data;
                        }else {
                            Notification.error({
                                title: 'Error!',
                                message: res.message
                            });
                        }
                    });
                }
            },
            reset(){
                this.posts = {
                    id: null,
                    categoryId: null,
                    isOriginal: true,
                    releaseTime: "",
                    isComment: true,
                    featuredImage: "",
                    title: "",
                    content: "",
                    sourceUrl: "",
                    sourceName: "",
                    describes: "",
                    keyword: ""
                };
                this.tags=[];
            },
            handleAvatarSuccess(res, file) {
                if (res.success) {
//                    this.imageUrl = URL.createObjectURL(file.raw);
                    this.posts.featuredImage = res.data;
                } else {

                }
            },
            beforeAvatarUpload(file) {
                const isJPG = file.type === 'image/jpeg';
                const isLt2M = file.size / 1024 / 1024 < 2;

                if (!isJPG) {
                    this.$message.error('上传头像图片只能是 JPG 格式!');
                }
                if (!isLt2M) {
                    this.$message.error('上传头像图片大小不能超过 2MB!');
                }
                return isJPG && isLt2M;
            }
        }
    }
</script>
