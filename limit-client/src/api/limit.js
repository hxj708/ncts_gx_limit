import $http from '@/utils/request';
import { dealRes } from '@/api/common';

let $simple_test = {
    async get (id) {
        const { data: res } = await $http.get(`/api/v1/test/simpleTest/${id}`);
        return dealRes(res);
    },
    async getPageList (param) {
        const { data: res } = await $http.get('/api/v1/test/simpleTest/page', {params: param});
        return dealRes(res);
    },
    async getList (param) {
        const { data: res } = await $http.get('/api/v1/test/simpleTest', {params: param});
        return dealRes(res);
    },
    async getListCache (param) {
        const { data: res } = await $http.get('/api/v1/test/simpleTest/listCache', {params: param});
        return dealRes(res);
    },
    async getOne (param) {
        const { data: res } = await $http.get('/api/v1/test/simpleTest/getOne', {params: param});
        return dealRes(res);
    },
    async create(param){
        const { data: res } = await $http.post('/api/v1/test/simpleTest', param);
        return dealRes(res);
    },
    async update(param){
        const { data: res } = await $http.post(`/api/v1/test/simpleTest/${param.id}/update`, param);
        return dealRes(res);
    },
    async delete(id){
        const { data: res } = await $http.post(`/api/v1/test/simpleTest/${id}/delete`);
        return dealRes(res);
    },
    async deleteBatch(ids){
        const { data: res } = await $http.post(`/api/v1/test/simpleTest/deleteBatch`, {ids:ids});
        return dealRes(res);
    },
    async updateBatch(param){
        const { data: res } = await $http.post('/api/v1/test/simpleTest/updateBatch', param);
        return dealRes(res);
    },
    async import(param){
        const { data: res } = await $http.post('/api/v1/test/simpleTest/import', param);
        return dealRes(res);
    },
    async export(param){
        const {data: res} = await $http.post('/api/v1/test/simpleTest/export', {params:param}, {responseType: 'blob'});
        let blob = new Blob([res]);
        let link = document.createElement('a');
        link.href = URL.createObjectURL(blob);
        link.download = "导出.xlsx";
        link.click();
        link.remove();
    },
};

export default $simple_test;

