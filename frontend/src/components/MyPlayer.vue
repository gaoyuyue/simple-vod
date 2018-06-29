<template>
  <el-container class="app">
    <el-header height="5rem" class="header">
      <h1 style="color: #fff">simple-vod</h1>
      <a href="https://github.com/gaoyuyue/simple-vod" style="float: right">
        <span style="color: honeydew">github</span>
      </a>

    </el-header>
    <el-container class="content">
      <div class="main">
        <video-player  class="vjs-custom-skin"
                       ref="videoPlayer"
                       :options="playerOptions"
                       :playsinline="true"
        >
        </video-player>
        <div style="width: 50px; height: 100%; display: flex; align-items: center; justify-content: center">
          <div class="fold-button" @click="floded = !floded">
            <i class="el-icon-arrow-left" style="color: honeydew; font-size: large"></i>
          </div>
        </div>
      </div>
      <div class="menu" v-if="!floded" style="width: 30rem">
        <el-container style="height: 100%">
          <el-header height="5rem">
            <h2 style="color: bisque">目录</h2>
          </el-header>
          <div style="background-color: honeydew; height: 1px; width: 100%"></div>
          <el-main>
            <a v-for="(item,index) in items" href="#" @click="setSrc(item.src,index)">
              <div style="margin: 1rem 0; width: 100%; background-color: #333">
                <span :style="item.isActive?{color: 'bisque'}:{color: 'honeydew'}">{{item.title}}</span>
                <span style="font-size: small; float: right; color: honeydew">{{item.date}}</span>
              </div>
            </a>
          </el-main>
        </el-container>
      </div>
    </el-container>
  </el-container>
</template>

<script>
  import 'video.js/dist/video-js.css'
  import 'vue-video-player/src/custom-theme.css'
  import { videoPlayer } from 'vue-video-player'

  export default {
    name: 'MyPlayer',
    components: {
      videoPlayer
    },
    data() {
      return {
        floded:false,
        items: [
          {title:"oceans", date:"2018-1-1 19:47", isActive: true, src:"http://vjs.zencdn.net/v/oceans.mp4"},
          {title:"小黄人", date:"2018-1-1 19:47", isActive: false, src:"http://yun.it7090.com/video/XHLaunchAd/video03.mp4"},
          {title:"广告", date:"2018-1-1 19:47", isActive: false, src:"http://yun.it7090.com/video/XHLaunchAd/video02.mp4"},
        ],
        playerOptions: {
          autoplay: true,
          muted: true,
          language: 'en',
          playbackRates: [0.7, 1.0, 1.5, 2.0],
          sources: [{
            type: "video/mp4",
            // mp4
            src: "http://vjs.zencdn.net/v/oceans.mp4",
          }],
          // poster: "https://surmon-china.github.io/vue-quill-editor/static/images/surmon-1.jpg",
        }
      }
    },
    computed: {
      player() {
        return this.$refs.videoPlayer.player
      }
    },
    methods: {
      setSrc(src,index) {
        this.changActive(index);
        this.player.src(src);
      },
      changActive(index) {
        this.items.forEach((e,i) => {
          if (i == index) {
            e.isActive = true;
          } else {
            e.isActive = false;
          }
        });
      }
    }
  }
</script>

<style>
  .app {
    position: absolute;
    top: 0;
    left: 0;
    bottom: 0;
    width: 100%;
  }
  .header {
    background-color: #333;
    display: flex;
    justify-content: space-between;
    align-items: center;
  }
  .content {
    background-color: darkgray;
  }
  .main {
    width: 100%;
    margin: 1rem 0;
    padding: 0;
    display: flex;
  }
  .menu {
    margin: 1rem 1rem 1rem 0;
    background-color: #333;
  }
  .vjs-custom-skin {
    margin: 0 0 0 1rem;
    width: 100%;
    height: 100%;
  }
  .vjs-custom-skin > .video-js {
    width: 100%;
    height: 100%;
    font-family: "PingFang SC","Helvetica Neue","Hiragino Sans GB","Segoe UI","Microsoft YaHei","微软雅黑",sans-serif;
  }
  .fold-button {
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: #444;
    width: 100%;
    height: 120px;
    border-radius: 2px;
    transform: perspective(.5em) rotateY(-5deg);
    transform-origin: right;
  }
</style>

