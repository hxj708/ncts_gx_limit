const webpack = require('webpack')
module.exports = {
  publicPath: '/bms/',
  productionSourceMap: false,
  devServer: {
    open: false,
    host: '127.0.0.1',
    port: 82,
    // https: false,
    // hotOnly: false,
    // proxy: 'http://127.0.0.1:1001' // 配置跨域处理,只有一个代理
    proxy: { // 配置跨域
      '/api': {
        //要访问的跨域的api的域名
        target: 'http://127.0.0.1:8081',
        ws: true,
        changOrigin: true,
      },
    }
    // before: app => { }
  },
  css: {
    loaderOptions: {
      less: {
        lessOptions: {
          // modifyVars: {
          //   'primary-color': '#1890ff',
          //   'link-color': '#1890ff',
          //   // 'heading-color':'',
          //   'border-radius-base': '2px',
          // },
          // javascriptEnabled: true,
          modifyVars: {
            // 'primary-color': '#1890ff',//黄埔主题色
            // 'link-color': '#1890ff',//黄埔链接颜色

            // 'primary-color': '#76a863',//江门主题色
            // 'link-color': '#76a863',//江门链接颜色
            // 'layout-header-background': '#76a863',//江门头部颜色
            // 'menu-dark-item-active-bg':'#76a863 ',//江门头部选中颜色
            // 'layout-footer-background':'#76a863',//江门底部颜色

            'primary-color': '#5292c6',//潮南区主题色
            'link-color': '#5292c6',//潮南区链接颜色
            'layout-header-background': '#5292c6',//潮南区头部颜色
            'menu-dark-item-active-bg':'#387bb2 ',//潮南区头部选中颜色
            'layout-footer-background':'#5292c6',//潮南区底部颜色

            //  'primary-color': '#fd5e39',//江门主题色
            //  'link-color': '#fd5e39',//江门链接颜色
            //  'layout-header-background': '#1890ff',//江门头部颜色
            //  'menu-dark-item-active-bg':'#a2190f ',//江门头部选中颜色
            //  'layout-footer-background':'#c1190b',//江门底部颜色
            // 'border-radius-base': '2px',
          },
          javascriptEnabled: true,
        },
      },
    },
  },

  chainWebpack: config => {
    // 发布模式
    // config.when(process.env.NODE_ENV === 'production', config => {
    //   config
    //     .entry('app')
    //     .clear()
    //     .add('./src/main-prod.js')
    //
    //   // config.set('externals', {
    //   //   vue: 'Vue',
    //   //   'vue-router': 'VueRouter',
    //   //   axios: 'axios',
    //   //   lodash: '_',
    //   //   echarts: 'echarts',
    //   //   nprogress: 'NProgress',
    //   //   'vue-quill-editor': 'VueQuillEditor'
    //   // })
    //
    //   config.plugin('html').tap(args => {
    //     args[0].isProd = true
    //     return args
    //   })
    // })

    config.plugin('provide').use(webpack.ProvidePlugin, [{
      'window.Quill': 'quill/dist/quill.js',
      'Quill': 'quill/dist/quill.js'
    }]);

    // 开发模式
    // config.when(process.env.NODE_ENV === 'development', config => {
    //   config
    //     .entry('app')
    //     .clear()
    //     .add('./src/main.js')
    //
    //   config.plugin('html').tap(args => {
    //     args[0].isProd = false
    //     return args
    //   })
    // })
  }
};

configureWebpack: config => {
  if (process.env.NODE_ENV === 'production') { //生产环境生效 取消console
    config.optimization.minimizer[0].options.terserOptions.compress.warnings = false;
    config.optimization.minimizer[0].options.terserOptions.compress.drop_console = true;
    config.optimization.minimizer[0].options.terserOptions.compress.drop_debugger = true;
    config.optimization.minimizer[0].options.terserOptions.compress.pure_funcs = ['console.log']
  }
}

/*const UglifyJsPlugin = require('uglifyjs-webpack-plugin');
const CompressionWebpackPlugin = require('compression-webpack-plugin');
const productionGzipExtensions = ['js', 'css'];
const env = process.env.NODE_ENV;
console.log(env);
configureWebpack: (config) => {
  if (env !== 'development' || env !== 'test') {
    config.plugins.push(new CompressionWebpackPlugin({
      algorithm: 'gzip',
      test: new RegExp(`\\.(${productionGzipExtensions.join('|')})$`),
      threshold: 10240,
      minRatio: 0.8
    }));
    config.plugins.push(
      new UglifyJsPlugin({
        uglifyOptions: {
          compress: {
            warnings: false,
            drop_debugger: true, // conso le
            drop_console: true,
            pure_funcs: ['console.log'] // 移除console
          }
        },
        sourceMap: false,
        parallel: true
      })
    );
  }
};*/
