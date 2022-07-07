<#assign name=table.name?uncap_first >
<#assign module=package.ModuleName >
<#assign hasDateField=false >
<template>
  <a-modal
          title="查看"
          :visible="parentObj.isShow"
          :width="1000"
          destroyOnClose
          :footer="null"
          @cancel="cancel"
  >
    <a-form-model :label-col="{span:5}" :wrapper-col="{span:12}">
      <#assign dicts = [] />
      <#list table.fields as field>
        <a-form-model-item label="${field.comment!}" prop="${field.propertyName}">
        <#if (field.customMap['isDict'])??>
          {{ entity.${field.propertyName}Dict.dictName }}
        <#elseif (field.customMap['isText'])??>
          <span style="white-space: nowrap">{{ entity.${field.propertyName} }}</span>
        <#elseif (field.customMap['isEditor'])??>
          <span v-html="entity.${field.propertyName}"></span>
        <#elseif 'DATE' == field.columnType>
          {{ entity.${field.propertyName} | dateTimeFormat }}
        <#else>
          {{ entity.${field.propertyName} }}
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

  export default {
    data () {
      return {
        entity: {
          <#list table.fields as field>
            <#if (field.customMap['isDict'])??>
          ${field.propertyName}Dict:{},
            </#if>
          </#list>
        },
        reloadKey: 0,
      }
    },
    props: {
      parentObj: {
        type: Object
      }
    },
    async created(){
    },
    methods: {
      cancel () {
        this.parentObj.isShow = false;
      },
    },
    watch: {
      parentObj: {
        async handler (newObj) {
          if (newObj.isShow) {
            if (newObj.id) {
              const data = await $${name}.get(newObj.id);
              this.entity = data;

              ++this.reloadKey;
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
