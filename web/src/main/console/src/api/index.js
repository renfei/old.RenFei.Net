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