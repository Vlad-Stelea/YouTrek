<template>
  <div id="videopage" v-on:playlist-change="loadPlaylist">
    <h1>{{playlist.name}}</h1>
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
      playlist: {},
      videos: []
    }
  },
  mounted: function () {
    this.loadPlaylist()
  },
  watch: {
    '$route.params.playlistID': function (id) {
      this.loadPlaylist()
    }
  },
  methods: {
    async loadPlaylist () {
      this.playlist = await api.getPlaylist(this.$route.params.playlistID)
      this.videos = this.playlist.videos.videos
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
