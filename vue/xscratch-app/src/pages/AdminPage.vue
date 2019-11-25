<template>
  <div id="adminpage">
    <h1>Third Party Libraries:</h1>
    <div class="topnav">
      <input type="text" placeholder="Add Library URL" />
      <button id="searchButton" type="submit">
        <font-awesome-icon icon="plus" />
      </button>
    </div>

    <hr />

    <h1>Local Video Library:</h1>
    <div class="topnav">
      <input type="text" placeholder="Search.." v-model="search" @keydown.enter="searchVideos()" />
      <button id="searchButton" type="submit" @click="searchVideos()">
        <i class="fa fa-search"></i>
        <font-awesome-icon icon="coffee" />
      </button>
    </div>

    <div id="divVideo">
      <div v-for="video in videos" v-bind:key="video.name" class="vidContainer">
        <div class="titleContainer">
          <h3>{{video.name}}</h3>
        </div>
        <video controls=" " width="320" height="240">
          <source v-bind:src="video.url" type="video/ogg" />/>
        </video>
        <div class="buttonContainer">
          <button buttontype="button">Mark Remote</button>
          <button buttontype="button">Mark Local Only</button>
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
      videos: [],
      search: '',
      tlds: []
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
    async searchVideos () {
      console.log(this.search)
      this.videos = await api.searchVideos(this.search)
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

#adminpage {
  padding-right: 40px;
}

.buttonContainer {
  text-align: center;
}
</style>
