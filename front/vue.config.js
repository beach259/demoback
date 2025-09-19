const { defineConfig } = require('@vue/cli-service')
module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    proxy: {
      '/api': {
        target: 'http://localhost:9000', // 后端 Spring Boot 地址
        changeOrigin: true,              // 是否修改请求头中的 origin
        // 注释掉重写规则，保留 /api 前缀
        // rewrite: path => path.replace(/^\/api/, ''), 
        pathRewrite: {
          // '^/api': '' // 注释掉，不移除 /api 前缀
        }
      }
    }
  }
})
