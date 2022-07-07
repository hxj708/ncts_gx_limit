<#assign name=table.name?uncap_first >
<#assign url =  '/api/v1' />
<#if package.ModuleName??>
    <#assign url += '/${package.ModuleName}' />
</#if>
<#if controllerMappingHyphenStyle??>
    <#assign url += '/${controllerMappingHyphen}' />
<#else>
    <#assign url += '/${table.entityPath}' />
</#if>
<#assign dola='$'  />
import $http from '@/utils/request';
import { dealRes } from '@/api/common';

let $${name} = {
    async get (id) {
        const { data: res } = await $http.get(`${url}/${dola}{id}`);
        return dealRes(res);
    },
    async getPageList (param) {
        const { data: res } = await $http.get('${url}/page', {params: param});
        return dealRes(res);
    },
    async getList (param) {
        const { data: res } = await $http.get('${url}', {params: param});
        return dealRes(res);
    },
    async getListCache (param) {
        const { data: res } = await $http.get('${url}/listCache', {params: param});
        return dealRes(res);
    },
    async getOne (param) {
        const { data: res } = await $http.get('${url}/getOne', {params: param});
        return dealRes(res);
    },
    async create(param){
        const { data: res } = await $http.post('${url}', param);
        return dealRes(res);
    },
    async update(param){
        const { data: res } = await $http.put(`${url}/${dola}{param.id}`, param);
        return dealRes(res);
    },
    async delete(id){
        const { data: res } = await $http.delete(`${url}/${dola}{id}`);
        return dealRes(res);
    },
    async deleteBatch(ids){
        const { data: res } = await $http.delete(`${url}/${dola}{ids.join(',')}`);
        return dealRes(res);
    },
    async updateBatch(param){
        const { data: res } = await $http.put('${url}/batch', param);
        return dealRes(res);
    },
};

export default $${name};

