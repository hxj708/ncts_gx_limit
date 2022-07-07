<#assign name=table.name?uncap_first >
<#assign module=package.ModuleName >
<template>
    <div>
        <div class="m-iptsearch-block">
            <a-form-model :model="queryParam" :label-col="{span:8 }" :wrapper-col="{ span:15 }">
                <a-row>
                    <a-col :lg="17" :xl="19" :xxl="20">
                        <a-col :xl="12" :xxl="8">
                            <a-form-model-item label="标题：">
                                <a-input title="" v-model="queryParam.name" allowClear></a-input>
                            </a-form-model-item>
                        </a-col>
                    </a-col>
                    <a-col :lg="7" :xl="5" :xxl="4">
                        <a-form-model-item :wrapper-col="{ span:24}">
                            <div class="search-editbtn">
                                <a-button type="primary" @click="search">
                                    <a-icon type="search"/>
                                    搜索
                                </a-button>
                                <a-button @click="reset">重置</a-button>
                            </div>
                        </a-form-model-item>
                    </a-col>
                </a-row>
            </a-form-model>
        </div>

        <div class="m-table-block">
            <div class="btn-group">
                <a-button @click="goCreate">创建</a-button>
                <a-popconfirm title="此操作将永久删除记录, 是否继续?？" ok-text="继续" cancel-text="取消" @confirm="deleteEntity">
                    <a-button>批量删除</a-button>
                </a-popconfirm>
            </div>

            <a-table :data-source="dataList" rowKey="id" :pagination="false" align="center" :row-selection="rowSelection">
                <a-table-column title="序号" data-index="no">
                    <template slot-scope="text, record, index">{{(queryParam.current - 1) * queryParam.size + index  + 1}}</template>
                </a-table-column>
                <#list table.fields as field>
                    <#assign propertyName=field.propertyName />
                    <#if (field.customMap['isDict'])??>
                        <#assign propertyName=field.propertyName + 'Dict.dictName' />
                    </#if>

                    <#if 'DATE' == field.columnType>
                <a-table-column title="${field.comment!}">
                    <template slot-scope="text, record, index">
                        {{record.${propertyName} | dateTimeFormat}}
                    </template>
                </a-table-column>
                    <#elseif !(field.customMap['isEditor'])?? >
                <a-table-column title="${field.comment!}" data-index="${propertyName}"/>
                    </#if>
                </#list>

                <a-table-column title="操作" data-index="action" align="center">
                    <template slot-scope="text, record">
                        <a @click="goEdit(record.id)">编辑</a>
                        <a-divider type="vertical"/>
                        <a-popconfirm title="此操作将永久删除该记录, 是否继续？" ok-text="继续" cancel-text="取消" @confirm="deleteEntity(record.id)">
                            <a>删除</a>
                        </a-popconfirm>
                        <a-divider type="vertical"/>
                        <a @click="goView(record.id)">查看</a>
                    </template>
                </a-table-column>
            </a-table>
            <pageComponent :queryParam="queryParam" :total="total" @getList="getList"></pageComponent>
        </div>

        <editComponent :parentObj="editComponentObj" @getList="getList"></editComponent>
        <viewComponent :parentObj="viewComponentObj"></viewComponent>
    </div>
</template>

<script>
  import $dict from '@/api/dict';
  import editComponent from '@/pages/admin/${module}/edit_${name}.vue';
  import viewComponent from '@/pages/admin/${module}/view_${name}.vue';
  import {mapGetters} from 'vuex';
  import $${name} from '@/api/${module}/${name}';

  export default {
    components:{
      editComponent, viewComponent
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
        viewComponentObj: {
          id: '',
          isShow: false
        },
        selectedRowKeys:[],
        selectedRows: [],
      };
    },
    async created() {
      this.getList();
    },
    computed: {
      ...mapGetters([
        'authLoginUser', 'fileDomain'
      ]),
      rowSelection() {
        const that = this;
        const {selectedRowKeys} = this;
        const {selectedRows} = this
        return {
          selectedRowKeys,
          selectedRows,
          onChange: function(selectedRowKeys,selectedRows) {
            that.selectedRowKeys = selectedRowKeys;
            that.selectedRows = selectedRows;
          },
          onSelection: this.onSelection,
        };
      },
    },
    methods: {
      async getList() {
        const data = await $${name}.getPageList(this.queryParam);
        this.dataList = data.records;
        this.total = data.total;
        this.rowSelection.onChange([]);
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
        if (id) {
          await $${name}.delete(id);
        } else {
          if (this.selectedRowKeys.length === 0) return;
          await $${name}.deleteBatch(this.selectedRowKeys);
        }
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
      goView(id){
        this.viewComponentObj.id = id;
        this.viewComponentObj.isShow = true;
      }
    }
  };
</script>
<style scoped lang="less">

</style>
