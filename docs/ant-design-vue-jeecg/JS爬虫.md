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

**puppeteer使用初探**

依赖：
```shell
yarn add puppeteer
#yarn add puppeteer-core
#支持webpack中的node环境
yarn add node-polyfill-webpack-plugin
```

修改`vue.config.js`：
```js
module.exports = {
  configureWebpack: {
    plugins: [
      new NodePolyfillPlugin()
    ]
  }
}
```

添加`puppeteer.config.cjs`：
```js
const {join} = require('path');

/**
 * @type {import("puppeteer").Configuration}
 */
module.exports = {
  cacheDirectory: join(__dirname, '.cache', 'puppeteer'),
};

```

测试：
```vue
<template>
  <div>
    <a-button type="primary" @click="testPuppeteer">测试Puppeteer</a-button>
  </div>
</template>
<script>
// import puppeteer from 'puppeteer'
const puppeteer = require('puppeteer')
export default {
  methods: {
    async testCrawler() {
      const urls = ['https://sqfb.zjsq.net.cn:8089/nuxtsyq/new/MarkInfo?zh=70508440&zm=%E6%B3%BD%E9%9B%85%E6%B0%B4%E5%BA%93&day=1']

      // Launch the browser and open a new blank page
      const browser = await puppeteer.launch();
      const page = await browser.newPage();

      // Navigate the page to a URL
      await page.goto(urls[0]);

      // Set screen size
      await page.setViewport({ width: 1080, height: 1024 });

      // Type into search box
      await page.type('.search-box__input', 'automate beyond recorder');

      // Wait and click on first result
      const searchResultSelector = '.search-box__link';
      await page.waitForSelector(searchResultSelector);
      await page.click(searchResultSelector);

      // Locate the full title with a unique string
      const textSelector = await page.waitForSelector(
          '#pane-ssgc > div.chartTable > div:nth-child(3) > div > div:nth-child(1) > span'
      );
      if (!textSelector) {
        const fullTitle = await textSelector.evaluate(el => el.textContent.trim());
        console.log('The title of this blog post is "%s".', fullTitle);
      }

      await browser.close();
    },
  }
}
</script>
```

修改历史:
* 2023-08-02: 添加基于Cheerio的爬虫示例
* 2021-08-07: puppeteer使用初探
