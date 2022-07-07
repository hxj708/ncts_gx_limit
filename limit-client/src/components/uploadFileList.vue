<template>
  <div>
    <div class="uploadbtn" v-if="isCanEdit">
      <a-button>
        <a-icon type="upload"/>
        上传
      </a-button>
      <input type="file" @change="uploadFile">
    </div>
    <!--<p class="uploadtip">支持扩展名：.rar .zip .doc .docx .pdf .jpg.</p>-->
    <ul class="uploadflie" :key="reloadKey">
      <li v-for='(item,index) in formObj[this.fileListName]' :key='index'>
        <span class="name">{{item.fileName}}</span>
        <div class="edit">
          <span v-if="item.type !== 'upload'">
            <a href="javascript:;" @click="previewFile(item)">预览</a>
            <a-divider type="vertical"/>
            <a @click="$downloadFile(item)">下载</a>
            <a-divider v-if="isCanEdit" type="vertical"/>
          </span>
          <a href="javascript:;" v-if="isCanEdit" @click="deleteFile(index)">删除</a>
        </div>
      </li>
    </ul>

    <previewFileComponent :parentObj="previewFileObj" v-if="previewFileObj.isShow"></previewFileComponent>
  </div>
</template>

<script>
  import $file from '@/api/file';

  export default {
    data() {
      return {
        reloadKey: 0,
        previewFileObj: {
          isShow:false,
          fileInfo:{}
        }
      };
    },
    props: {
      formObj: {type: Object},
      fileSizeLimit: {type: Number, default:500},  //MB
      fileTypeLimit: {type: Array},
      fileListName: {type: String},
      prefixDir: {type: String},
      isCanEdit:{type:Boolean,default:true},
    },
    methods: {
      async uploadFile(e) {
        const file = e.target.files[0];
        const fileSize = file.size;
        const arr = file.name.split('.');
        const fileType = arr[arr.length - 1];
        const formData = new window.FormData();

        if(this.fileTypeLimit){
          if (!this.fileTypeLimit.includes(fileType)) {
            this.$message.error('文件格式不符合要求');
            return;
          }
        }
        if(this.fileSizeLimit && this.fileSizeLimit > 0){
          if(fileSize / 1024 / 1024 > this.fileSizeLimit){
            this.$message.error('不能上传超过' + this.fileSizeLimit + 'Mb的文件');
            return;
          }
        }
        formData.append('file', file);
        const config = {
          headers: {
            'Content-Type': file.type
          },
          onUploadProgress: progressEvent => {
            var complete = (progressEvent.loaded / progressEvent.total * 100 | 0) + '%';
            this.progress = complete;
          }
        };
        const data = await $file.uploadTemp(formData, config);

        data.type = 'upload';
        data.prefixDir = this.prefixDir;
        this.formObj[this.fileListName].push(data);
        ++this.reloadKey;
      },
      deleteFile(i){
        this.formObj[this.fileListName].splice(i, 1);
        ++this.reloadKey;

      },
      previewFile(fileInfo){
        this.previewFileObj.fileInfo = fileInfo;
        this.previewFileObj.isShow = true;
      }
    },
  };
</script>
