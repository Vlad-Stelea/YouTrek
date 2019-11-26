<template>
  <b-modal id="play" size="md" centered :title="playlist.name">
    <div id="filler-div"></div>
    <video
      v-for="video in videos"
      v-bind:key="video.name"
      :id="video.id + video.name"
      style="padding-bottom: 0px;"
      class="videoContainer"
      :class="{ 'playing' : playing == video.id }"
      @canplay="registerVideo(video.id)"
      @ended="playNext(video.id)"
    >
      <source v-bind:src="video.url" type="video/ogg" />/>
    </video>
  </b-modal>
</template>

<script>
export default {
  props: {
    playlist: Object,
    videos: Array
  },
  data: function () {
    return {
      doneLoading: false,
      currentVideo: 0,
      playing: 0
    }
  },
  mounted: function () {
    console.log(this.playlist)
  },
  methods: {
    registerVideo (index) {
      this.videos.forEach(video => {
        if (video.id === index) {
          video.element = event.target
        }
      })
      this.loadedCheck()
    },
    loadedCheck () {
      var isLoaded = true
      this.videos.forEach(video => {
        if (video.element === null) {
          isLoaded = false
          video.playing = false
        }
      })
      console.log(isLoaded)
      this.doneLoading = isLoaded
      if (isLoaded) this.play()
    },
    play () {
      var video = this.videos[this.currentVideo]
      this.playing = video.id
      video.element.play()
    },
    playNext (id) {
      console.log('next')
      var doneWith = 0
      for (let i = 0; i < this.videos.length; i++) {
        var video = this.videos[i]
        if (video.id === id) {
          doneWith = i
        }
      }
      this.currentVideo = (doneWith + 1) % this.videos.length
      this.play()
    }
  }
}
</script>

<style>
#filler-div {
  width: 100%;
  padding-top: 75%;
  background-color: #000;
}
.videoContainer {
  position: absolute;
  z-index: 100;
  top: 0px;
  left: 0px;
  width: 100%;
}
.modal-body {
  padding: 0px;
}

.videoContainer + .playing {
  opacity: 1 !important;
}

.videoContainer + :not(.playing) {
  opacity: 0 !important;
}
</style>
