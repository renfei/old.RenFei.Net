<template>
    <div id="signin">
        <el-row :gutter="10">
            <el-col :xs="0" :sm="10" :md="11" :lg="12" :xl="13" style="height: 10px;"></el-col>
            <el-col :xs="24" :sm="9" :md="8" :lg="6" :xl="5">
                <el-form ref="form" :model="form" label-width="80px" style="background-color: #ffffff;">
                    <div style="padding: 10px;">
                        <h3>Sign In</h3>
                        <el-form-item label="Account">
                            <el-input v-model="form.account" clearable></el-input>
                        </el-form-item>
                        <el-form-item label="Password">
                            <el-input v-model="form.password" show-password clearable></el-input>
                        </el-form-item>
                        <el-form-item v-if="form.showopt" label="OPT">
                            <el-input v-model="form.opt" clearable></el-input>
                        </el-form-item>
                        <el-form-item>
                            <el-checkbox-group v-model="form.remember">
                                <el-checkbox name="type">
                                    Keep me signed in
                                    <el-tooltip placement="top">
                                        <div slot="content">Keep me signed in<br/>Protect your account. Uncheck if using
                                            public/shared device.
                                        </div>
                                        <span class="fa fa-info-circle"></span>
                                    </el-tooltip>
                                </el-checkbox>
                            </el-checkbox-group>
                        </el-form-item>
                        <el-button type="primary" @click="onSubmit" style="width: 100%;">Sign In</el-button>
                    </div>
                </el-form>
            </el-col>
            <el-col :xs="0" :sm="5" :md="5" :lg="6" :xl="6" style="height: 10px;"></el-col>
        </el-row>
    </div>
</template>
<style>
    @media only screen and (min-width: 768px) {
        .el-row {
            padding-top: 50px;
        }

        #signin {
            height: 100%;
            background-image: url('/static/img/door.jpg');
            background-repeat: no-repeat;
            background-size: cover;
        }
    }

    @media only screen and (min-width: 9920px) {
        .el-row {
            padding-top: 100px;
        }
    }

    @media only screen and (min-width: 1200px) {
        .el-row {
            padding-top: 150px;
        }
    }

    @media only screen and (min-width: 1920px) {
        .el-row {
            padding-top: 200px;
        }
    }
</style>
<script type="text/ecmascript-6">
    import {signin} from '../api/index'
    import {setStore, getStore, removeStore} from '../libs/storage'

    export default {
        data(){
            return {
                form: {
                    account: '',
                    password: '',
                    opt: '',
                    showopt: false,
                    remember: false
                },
                sub: {
                    account: '',
                    password: '',
                    opt: '',
                    remember: 0
                }
            }
        },
        methods: {
            onSubmit() {
                console.log(getStore('aesKey'));
                this.sub.account = this.encryption.encryption(this.form.account);
                this.sub.password = this.encryption.encryption(this.form.password);
                this.sub.opt = this.form.opt;
                this.sub.remember = this.form.remember ? 1 : 0;
                signin(this.sub).then(res => {
                    if(res.success){
                        setStore("Authorization",res.data.Authorization);
                        this.router.push('/');
                    }else {
                        this.Notification.info(res.message);
                    }
                });
            }
        }
    }
</script>