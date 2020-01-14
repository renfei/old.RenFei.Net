import {getRequest, postRequest, putRequest, deleteRequest, uploadFileRequest} from '@/libs/axios';

// 获取服务器端公钥
export const getPublicKey = (params) => {
    return getRequest('/api/security/secretkey', params)
}
// 发送客户端端公钥并获得AESKEY
export const setPublicKey = (uuid, params) => {
    return postRequest('/api/security/secretkey/' + uuid, params)
}
// 登陆
export const signin = (params) => {
    return postRequest('/api/auth/signin', params)
}
//获取菜单
export const getMenu = (params) => {
    return getRequest('/api/menu', params)
}
//获取分类列表
export const getCat = (params) => {
    return getRequest('/api/cat', params)
}
//获取标签云列表
export const getTags = (params) => {
    return getRequest('/api/tags', params)
}
//获取文章
export const getAllPosts = (params) => {
    return getRequest('/api/posts/all', params)
}
//获取文章
export const getPosts = (params) => {
    return getRequest('/api/posts', params)
}
//根基目标ID获取标签云列表
export const getTagsByTargetId = (params) => {
    return getRequest('/api/tags/bytargetid', params)
}
//添加文章
export const postPosts = (params) => {
    return postRequest('/api/posts', params)
}
//修改文章
export const putPosts = (params) => {
    return putRequest('/api/posts', params)
}
//删除文章
export const deletePosts = (params) => {
    return deleteRequest('/api/posts', params)
}