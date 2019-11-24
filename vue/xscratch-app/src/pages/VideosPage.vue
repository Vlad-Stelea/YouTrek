<template>
  <div id="videopage">
    <h1>Library:</h1>
    <div class="topnav">
      <input type="text" placeholder="Search.." @keydown.enter="searchVideos($event.target.value)" />
    </div>
    <h1>Below are the library's videos:</h1>
    <div id="Overlay"></div>

    <div id="divVideo">
      <div v-for="video in videos" v-bind:key="video.name" class="vidContainer">
        <div class="titleContainer">
          <h3>{{video.name}}</h3>
        </div>
        <video controls=" " width="320" height="240">
          <source v-bind:src="video.url" type="video/ogg" />/>
        </video>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data: function () {
    return {
      videos: []
    }
  },
  created: function () {
    this.loadVideos()
  },
  methods: {
    async loadVideos () {
      this.videos = await api.getVideos()
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
    },
    async searchVideos (searchString) {
      this.videos = await api.searchVideos(searchString)
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
    }
  }
}
</script>

<style>
h1 {
  font-family: Arial, Helvetica, sans-serif;
  color: #f90;
}
div {
  color: #f90;
}
#divVideo {
  position: relative;
  overflow: hidden;
  display: inline-block;
}
.vidContainer {
  float: left;
  width: 320;
}
.titleContainer {
  text-align: center;
}

#videopage {
  padding-right: 40px;
}
</style>
