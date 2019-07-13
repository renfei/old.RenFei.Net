import {JSEncrypt} from 'jsencrypt'
import NodeRSA from 'node-rsa'
import CryptoJS from 'crypto-js'
import {setStore, getStore, removeStore} from './storage'
import {getPublicKey, setPublicKey} from '../api/index'
import {Notification} from 'element-ui';

let encryption = function (data) {
    var aes = getStore('aesKey');
    if (aes = undefined || aes == null || aes == '') {
        //AES为空需要从服务器获取
        var clientPublicKey = getStore('clientPublicKey');
        if (clientPublicKey == undefined || clientPublicKey == null || clientPublicKey == '') {
            //客户端公钥是空，需要生成
            generateKey();
        }
        clientPublicKey = getStore('clientPublicKey');
        //将客户端公钥加密上报给服务器
        var serverPublicKey = getStore('serverPublicKey');
        var uuid = getStore('uuid');
        if (serverPublicKey == undefined || serverPublicKey == null || serverPublicKey == '') {
            //服务器公钥也是空的，需要获取
            getPublicKey({}).then(res => {
                if (!res.success) {
                    Notification.error({
                        title: '错误',
                        message: res.message
                    });
                    return null;
                }
                setStore('uuid', res.data.uuid);
                setStore('serverPublicKey', res.data.publickey);
                uuid = getStore('uuid');
                serverPublicKey = getStore('serverPublicKey');
                var encrypted = encrypt(serverPublicKey, clientPublicKey);
                setPublicKey(uuid, {publickey: encrypted}).then(res => {
                    if (!res.success) {
                        Notification.error({
                            title: '错误',
                            message: res.message
                        });
                        return null;
                    }
                    setStore('aesKey', decrypt(getStore('clientPrivateKey'), res.data.aes));
                    aes = getStore('aesKey');
                    return aesencrypt(data, aes);
                });
            });
        } else {
            var encrypted = encrypt(serverPublicKey, clientPublicKey);
            setPublicKey(uuid, {publickey: encrypted}).then(res => {
                if (!res.success) {
                    Notification.error({
                        title: '错误',
                        message: res.message
                    });
                    return null;
                }
                setStore('aesKey', decrypt(getStore('clientPrivateKey'), res.data.aes));
                return aesencrypt(data, getStore('aesKey'));
            });
        }
    } else {
        return aesencrypt(data, getStore('aesKey'));
    }
};
let decryption = function (data) {
    var aes = getStore('aesKey');
    return aesdecrypt(data, aes);
};
function encrypt(key, data) {
    var encrypt = new JSEncrypt();
    encrypt.setPublicKey(key);
    return encrypt.encrypt(data);
}
function decrypt(key, data) {
    var decrypt = new JSEncrypt();
    decrypt.setPrivateKey(key);
    return decrypt.decrypt(data);
}
function generateKey() {
    const key = new NodeRSA({b: 1024});
    key.setOptions({encryptionScheme: 'pkcs1'});//指定加密格式
    let publicDer = key.exportKey("pkcs8-public-pem");  //公钥
    let privateDer = key.exportKey("pkcs8-private-pem");//私钥
    publicDer = publicDer.replace("-----BEGIN PUBLIC KEY-----\n", "").replace("\n-----END PUBLIC KEY-----", "");
    setStore('clientPublicKey', publicDer);
    setStore('clientPrivateKey', privateDer);
}
//加密
function aesencrypt(word, keyStr) {
    let key = CryptoJS.enc.Utf8.parse(keyStr);
    let iv = CryptoJS.enc.Utf8.parse(keyStr);
    let srcs = CryptoJS.enc.Utf8.parse(word);
    var encrypted = CryptoJS.AES.encrypt(srcs, key, {
        iv: iv,
        mode: CryptoJS.mode.CBC,
        padding: CryptoJS.pad.ZeroPadding
    });
    return CryptoJS.enc.Base64.stringify(encrypted.ciphertext);

    // var key = CryptoJS.enc.Utf8.parse(keyStr);//Latin1 w8m31+Yy/Nw6thPsMpO5fg==
    // var srcs = CryptoJS.enc.Utf8.parse(word);
    // var encrypted = CryptoJS.AES.encrypt(srcs, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    // return encrypted.toString();
}
//解密
function aesdecrypt(word, keyStr) {
    var key = CryptoJS.enc.Utf8.parse(keyStr);//Latin1 w8m31+Yy/Nw6thPsMpO5fg==
    var decrypt = CryptoJS.AES.decrypt(word, key, {mode: CryptoJS.mode.ECB, padding: CryptoJS.pad.Pkcs7});
    return CryptoJS.enc.Utf8.stringify(decrypt).toString();
}
export default {encryption, decryption};
