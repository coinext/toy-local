const withCSS = require('@zeit/next-css')
module.exports = withCSS({
  publicRuntimeConfig: {
    oauthEndpoint: 'http://52.79.128.170:8081',
    serverEndpoint: 'http://localhost:8080',
    clientId: 'toylocal-id',
    clientSecret: 'toylocal-secret',
  },
  webpack: function (config) {
    config.module.rules.push({
      test: /\.(eot|woff|woff2|ttf|svg|png|jpg|gif)$/,
      use: {
        loader: 'url-loader',
        options: {
          limit: 100000,
          name: '[name].[ext]'
        }
      }
    })
    return config
  },
})