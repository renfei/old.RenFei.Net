import axios from 'axios';
import { getStore, setStore } from './storage';
import { router } from '../router/index';
import { Notification } from 'element-ui';
// 统一请求路径前缀
let base = 'http://localhost:8091';
// 超时设定
axios.defaults.timeout = 15000;

axios.interceptors.request.use(config => {
    return config;
}, err => {
    Notification.error({
        title: '错误',
        message: '请求超时'
    });
    return Promise.resolve(err);
});

// http response 拦截器
axios.interceptors.response.use(response => {
    const data = response.data;

    // 根据返回的code值来做不同的处理(和后端约定)
    switch (data.code) {
        case 401:
            // 未登录 清除已登录状态
            setStore('Authorization', '');
            setStore('clientPublicKey', '');
            setStore('clientPrivateKey', '');
            setStore('serverPublicKey', '');
            setStore('aesKey', '');
            router.push('/auth/signin');
            break;
        case 403:
            // 没有权限
            if (data.message !== null) {
                Notification.error({
                    title: '错误',
                    message: data.message
                });
            } else {
                Notification.error({
                    title: '错误',
                    message: '没有权限'
                });
            }
            break;
        case 500:
            // 错误
            if (data.message !== null) {
                Notification.error({
                    title: '错误',
                    message: data.message
                });
            } else {
                Notification.error({
                    title: '错误',
                    message: '未知错误'
                });
            }
            break;
        default:
            return data;
    }

    return data;
}, (err) => {
    // 返回状态码不为200时候的错误处理
    Notification.error({
        title: '错误',
        message: err.toString()
    });
    return Promise.resolve(err);
});

export const getRequest = (url, params) => {
    let Authorization = getStore('Authorization');
    let uuid = getStore('uuid');
    return axios({
        method: 'get',
        url: `${base}${url}`,
        params: params,
        headers: {
            'Authorization': Authorization,
            'uuid': uuid
        }
    });
};

export const postRequest = (url, params) => {
    let Authorization = getStore("Authorization");
    let uuid = getStore('uuid');
    return axios({
        method: 'post',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': Authorization,
            'uuid': uuid
        }
    });
};

export const putRequest = (url, params) => {
    let Authorization = getStore("Authorization");
    let uuid = getStore('uuid');
    return axios({
        method: 'put',
        url: `${base}${url}`,
        data: params,
        transformRequest: [function (data) {
            let ret = '';
            for (let it in data) {
                ret += encodeURIComponent(it) + '=' + encodeURIComponent(data[it]) + '&';
            }
            return ret;
        }],
        headers: {
            'Content-Type': 'application/x-www-form-urlencoded',
            'Authorization': Authorization,
            'uuid': uuid
        }
    });
};

export const deleteRequest = (url, params) => {
    let Authorization = getStore('Authorization');
    let uuid = getStore('uuid');
    return axios({
        method: 'delete',
        url: `${base}${url}`,
        params: params,
        headers: {
            'Authorization': Authorization,
            'uuid': uuid
        }
    });
};

export const uploadFileRequest = (url, params) => {
    let Authorization = getStore('Authorization');
    let uuid = getStore('uuid');
    return axios({
        method: 'post',
        url: `${base}${url}`,
        params: params,
        headers: {
            'Authorization': Authorization,
            'uuid': uuid
        }
    });
};
