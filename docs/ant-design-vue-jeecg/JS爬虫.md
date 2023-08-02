[返回](../)

# JS爬虫

前端爬虫研究

**基于Cheerio的爬虫示例**

* [WebCheerioCrawler](https://github.com/yoko-murasame/ant-design-vue-jeecg/blob/yoko/src/components/yoko/utils/WebCheerioCrawler.js)
* cheerio https://github.com/cheeriojs/cheerio
* `yarn add cheerio@1.0.0-rc.3`

测试发现NextJS的页面中，虽然是后端渲染的，但是数据填充还是在前端JS脚本中，而Cheerio不会产生视觉呈现，应用CSS，加载外部资源，或者执行JavaScript。因此，如果需要爬取NextJS页面，需要使用无头浏览器，如Puppeteer。

```js
import WebCheerioCrawler from '@comp/yoko/utils/WebCheerioCrawler'
function testCrawler() {
  const urls = ['https://sqfb.zjsq.net.cn:8089/nuxtsyq/new/MarkInfo?zh=70508440&zm=%E6%B3%BD%E9%9B%85%E6%B0%B4%E5%BA%93&day=1']
  const chandler = async($, crawl) => {
    const text = $.html()
    console.log('chandler', text)
    return text
  }
  const callback = (data) => {
    console.log('callback', data)
  }
  new WebCheerioCrawler(urls, chandler, callback).start();
}
```


修改历史:
* 2023-08-02: 添加基于Cheerio的爬虫示例
