[返回](../)

# v-viewer图片预览组件

v-viewer图片预览

* https://www.npmjs.com/package/v-viewer
* [中文文档](https://mirari.cc/2017/08/27/Vue图片浏览组件v-viewer，支持旋转、缩放、翻转等操作/)
* https://github.com/fengyuanchen/viewerjs

安装：
```js
// npm install v-viewer
// yarn add v-Viewer

/* v-viewer 图片预览 */
import Viewer from 'v-viewer'
import 'viewerjs/dist/viewer.css'
Vue.use(Viewer)
Viewer.setDefaults({
  Options: {
    inline: true, // 启用inline模式
    button: true, // 右上角关闭按钮
    navbar: true, // 缩略图导航
    title: true, // 显示当前图片的标题
    toolbar: true, // 显示工具栏
    tooltip: true, // 显示缩放百分比
    movable: true, // 是否允许移动
    zoomable: true, // 是否可缩放
    rotatable: true, // 是否可旋转
    scalable: true, // 是否可翻转
    transition: true, // 是否使用css3过渡
    fullscreen: true, // 是否全屏
    keyboard: true, // 是否支持键盘
    url: 'data-source' // 设置大图片的url
  }
})
/* v-viewer 图片预览 */
```

通过接口调用，并设置默认缩放：
```js
toViewer = (url) => {
  const urls = url.split(',')
  const target = this.$viewerApi({
    images: urls,
    options: {
      viewed: function () {
        console.log('viewed', this)
        // 等比缩放
        // target.zoomTo(1.7)
        // 直接放大
        target.scale(1.7, 1.7)
      }
    }
  })
}
```

修改历史:
* 2023-07-20: 新增v-viewer图片预览组件
