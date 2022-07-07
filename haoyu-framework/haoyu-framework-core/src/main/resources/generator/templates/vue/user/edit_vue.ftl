<#assign name=table.name?uncap_first >
<#assign module=package.ModuleName >
<#assign hasDateField=false >
<template>
  <a-modal
          :title="this.parentObj.id ? '编辑' : '创建'"
          :visible="parentObj.isShow"
          @ok="ok"
          @cancel="cancel"
          :width="1000"
          destroyOnClose
    >
    <a-form-model ref="editFormRef" :model="editForm" :label-col="{span:5}" :wrapper-col="{span:12}" :rules="rules">
      <#assign dicts = [] />
      <#list table.fields as field>
      <a-form-model-item label="${field.comment!}" prop="${field.propertyName}">
        <#if (field.customMap['isDict'])??>
        <a-select v-model="editForm.${field.propertyName}" placeholder="请选择">
          <a-select-option v-for="dict in ${field.propertyName}DictList" :value="dict.dictValue">{{dict.dictName}}</a-select-option>
        </a-select>
        <#elseif (field.customMap['isText'])??>
        <a-textarea v-model="editForm.${field.propertyName}" placeholder="请输入" :rows="5" :cols="600"/>
        <#elseif (field.customMap['isEditor'])??>
        <Editor :modelEntity="editForm" modelFieldName="${field.propertyName}"></Editor>
        <#elseif 'DATE' == field.columnType>
          <#assign hasDateField=true>
        <a-date-picker @change="onChange${field.propertyName?cap_first}" v-model="editForm.${field.propertyName}" format="YYYY-MM-DD"/>
        <#elseif ['DOUBLE','INTEGER','LONG','FLOAT','BIG_DECIMAL']?seq_contains(field.columnType)>
        <a-input-number v-model="editForm.${field.propertyName}" placeholder="请输入" :min="0"/>
        <#else>
        <a-input v-model="editForm.${field.propertyName}" placeholder="请输入"/>
        </#if>
      </a-form-model-item>
      </#list>
    </a-form-model>
  </a-modal>
</template>

<script>
  import {mapGetters} from 'vuex';
  import $${name} from '@/api/${module}/${name}';
  import _ from 'lodash';
  import $dict from '@/api/dict';
  <#if hasDateField>
  import $moment from 'moment';
  </#if>

  export default {
    data () {
      return {
        initEditForm: {
        },
        editForm:{
        },
        reloadKey: 0,
        rules: {
          <#list table.fields as field>
            <#assign msg='必填项' />
            <#if (field.customMap['isDict'])??>
              <#assign msg='必选项' />
            </#if>
          ${field.propertyName}: [
            { required: true, message: '${msg}', trigger: 'blur' },
          ],
          </#list>
        },

      <#list table.fields as field>
        <#if (field.customMap['isDict'])??>
        ${field.propertyName}DictList:[],
        </#if>
      </#list>

      <#if hasDateField>
        <#list table.fields as field>
          <#if 'DATE' == field.columnType>
        ${field.propertyName} : null,
          </#if>
        </#list>
      </#if>
      }
    },
    props: {
      parentObj: {
        type: Object
      }
    },
    async created(){
      <#list table.fields as field>
      <#if (field.customMap['isDict'])??>
      this.${field.propertyName}DictList =  await $dict.getList('${field.customMap['dictCode']}');
        </#if>
      </#list>
    },
    methods: {
      async ok () {
        const that = this;
        that.$refs.editFormRef.validate(async function(valid){
          if (valid) {
            if (that.editForm.id) {
              await $${name}.update(that.editForm);
            } else {
              await $${name}.create(that.editForm);
            }
            that.parentObj.isShow = false;
            that.reset();
            that.$emit('getList');
            that.$message.success('提交成功');
          }
        });
      },
      cancel () {
        this.parentObj.isShow = false;
        this.reset();
      },
      reset() {
        this.editForm = _.cloneDeep(this.initEditForm);
      },
      <#if hasDateField>
        <#list table.fields as field>
          <#if 'DATE' == field.columnType>
      onChange${field.propertyName?cap_first}(date, dateString){
        this.${field.propertyName} = date;
        this.editForm.${field.propertyName} = dateString;
      }
          </#if>
        </#list>
      </#if>
    },
    watch: {
      parentObj: {
        async handler (newObj) {
          if (newObj.isShow) {
            if (newObj.id) {
              const data = await $${name}.get(newObj.id);
              this.editForm = data;

              <#if hasDateField>
                <#list table.fields as field>
                  <#if 'DATE' == field.columnType>
              this.${field.propertyName} = this.editForm.${field.propertyName} ? $moment(this.editForm.${field.propertyName}) : '';
                  </#if>
                </#list>
              </#if>

              ++this.reloadKey;
            } else {
              this.reset();
            }
          }
        },
        deep: true
      }
    },
    computed: {
      ...mapGetters([
        'authLoginUser', 'fileDomain'
      ]),
    }
  };
</script>
<style scoped lang="less">
</style>
