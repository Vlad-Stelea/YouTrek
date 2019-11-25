<template>
  <div id="videopage">
    <h1>Library:</h1>
    <div class="topnav">
      <input type="text" placeholder="Search.." v-model="search" @keydown.enter="searchVideos()" />
      <button id="searchButton" type="submit" @click="searchVideos()">
        <i class="fa fa-coffee"></i>
        <font-awesome-icon icon="coffee" />
      </button>
    </div>
    <h1>Below are the library's videos:</h1>
    <div v-if="loading">Loading...</div>

    <div id="divVideo">
      <div v-for="video in videos" v-bind:key="video.name" class="vidContainer">
        <div class="titleContainer">
          <h2>{{video.name}}</h2>
        </div>
        <video controls=" " width="320" height="240">
          <source v-bind:src="video.url" type="video/ogg" />/>
        </video>
        <div class="titleContainer">
          <h4>{{video.dialogue}}</h4>
        </div>
      </div>
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  data: function () {
    return {
      loading: true,
      videos: [],
      search: ''
    }
  },
  created: function () {
    this.loadVideos()
  },
  methods: {
    async loadVideos () {
      this.loading = true
      this.videos = await api.getVideos()
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
      this.loading = false
    },
    async searchVideos () {
      this.loading = true
      console.log(this.search)
      this.videos = await api.searchVideos(this.search)
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
      this.loading = false
    }
  }
}
</script>

<style>
h1 {
  font-family: Arial, Helvetica, sans-serif;
  color: #0cad0b;
}
div {
  color: #0cad0b;
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
