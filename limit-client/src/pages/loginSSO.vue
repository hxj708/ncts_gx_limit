<template>
  <div>

  </div>
</template>

<script>
  import CryptoJS from 'crypto-js';
  import {setAuthToken, setAuthResourceList} from '@/utils/auth';

  export default {
    data() {
      return {
      };
    },
    created(){
      let auth = this.$route.query.auth;
      let func = this.$route.query.func;
      let data = this.$route.query.data;
      auth = CryptoJS.enc.Base64.parse(auth);
      auth = JSON.parse(auth.toString(CryptoJS.enc.Utf8));
      setAuthToken(auth);
      setAuthResourceList();
      // this.$router.push('/index');
      window.location.href = this.$store.getters.bmsClientDomain + '#/index'
    }
  };
</script>
