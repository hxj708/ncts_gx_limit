<#assign name=table.name?uncap_first >
<#assign module=package.ModuleName >
<template>
    <div>
        <input v-model="queryParam.name" />
        <button @click="search">搜索</button>
        <button @click="goCreate">创建</button>

        <div v-for="data in dataList">
            <#list table.fields as field>
                <#assign propertyName=field.propertyName />
                <#if (field.customMap['isDict'])??>
                    <#assign propertyName=field.propertyName + 'Dict.dictName' />
                </#if>
                <#if 'DATE' == field.columnType>
            {{ data.${propertyName} | dateTimeFormat}}
                <#elseif !(field.customMap['isEditor'])?? >
            {{ data.${propertyName} }}
                </#if>
            </#list>
            <button @click="goEdit(record.id)">编辑</button>
            <a-popconfirm title="此操作将永久删除该记录, 是否继续？" ok-text="继续" cancel-text="取消" @confirm="deleteEntity(record.id)">
                <button>删除</button>
            </a-popconfirm>
        </div>
        <pageComponent :queryParam="queryParam" :total="total" @getList="getList"></pageComponent>

        <editComponent :parentObj="editComponentObj" @getList="getList"></editComponent>
    </div>
</template>

<script>
  import $dict from '@/api/dict';
  import editComponent from '@/pages/${module}/edit_${name}.vue';
  import {mapGetters} from 'vuex';
  import $${name} from '@/api/${module}/${name}';

  export default {
    components:{
      editComponent
    },
    data() {
      return {
        queryParam: {
          name: '',
          current: 1,
          size: 10,
          orders: 'CREATE_TIME.DESC',
        },
        dataList: [],
        total: 0,
        editComponentObj: {
          id: '',
          isShow: false
        },
      };
    },
    async created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'authLoginUser', 'fileDomain'
      ]),
    },
    methods: {
      async getList() {
        const data = await $${name}.getPageList(this.queryParam);
        this.dataList = data.records;
        this.total = data.total;
      },
      goCreate() {
        this.editComponentObj.id = null;
        this.editComponentObj.isShow = true;
      },
      goEdit(id) {
        this.editComponentObj.id = id;
        this.editComponentObj.isShow = true;
      },
      async deleteEntity(id) {
        await $${name}.delete(id);
        this.$message.success('提交成功');
        await this.getList();
      },
      search() {
        this.queryParam.current = 1;
        this.getList();
      },
      reset() {
        this.queryParam.name = '';
      },
    }
  };
</script>
<style scoped lang="less">

</style>
