<template>
  <div>
    <div class="m-table-block">
      <a-table :data-source="dataList" rowKey="id" :pagination="false" align="center">

        <a-table-column title="关键字" data-index="name" width="100px"/>

        <a-table-column title="出现次数" data-index="num" width="100px"/>

        <a-table-column title="评论数" data-index="avg" width="100px"/>

        <a-table-column title="" data-index="" />

      </a-table>
    </div>

  </div>
</template>

<script>
  import $dict from '@/api/dict';
  import {mapGetters} from 'vuex';
  import $sheet from '@/api/sheet/sheet';

  export default {
    components:{
    },
    data() {
      return {
        queryParam: {
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
    },
    methods: {
      async getList() {
        const data = await $sheet.test(this.queryParam);
        this.dataList = data;
      },
    }
  };
</script>
<style scoped lang="less">

</style>
