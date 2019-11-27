<template>
  <b-modal
    id="play"
    size="md"
    header-bg-variant="dark"
    header-border-variant="dark"
    footer-bg-variant="dark"
    footer-border-variant="dark"
    body-bg-variant="dark"
    centered
    :title="playlist.name"
  >
    <div id="filler-div"></div>
    <video
      v-for="video in videos"
      v-bind:key="video.name"
      :id="video.id + video.name"
      style="padding-bottom: 0px;"
      class="videoContainer"
      :class="{ 'playing' : playingID == video.id }"
      @canplay="registerVideo(video.id)"
      @ended="playNext(video.id)"
    >
      <source v-bind:src="video.url" type="video/ogg" />/>
    </video>
    <template v-slot:modal-footer>
      <b-row align-h="end">
        <b-col cols="auto">
          <b-button variant="outline-success" @click="backward">
            <font-awesome-icon icon="fast-backward" size="2x" />
          </b-button>
          <b-button v-if="playing" variant="outline-success" @click="pause">
            <font-awesome-icon icon="pause-circle" size="2x" />
          </b-button>
          <b-button v-else variant="outline-success" @click="play">
            <font-awesome-icon icon="play-circle" size="2x" />
          </b-button>
          <b-button variant="outline-success" @click="forward">
            <font-awesome-icon icon="fast-forward" size="2x" />
          </b-button>
        </b-col>
        <b-col cols="auto">
          <b-button v-if="loop" variant="success" @click="toggleLoop">
            <font-awesome-icon icon="redo-alt" size="2x" />
          </b-button>
          <b-button v-else variant="outline-success" @click="toggleLoop">
            <font-awesome-icon icon="redo-alt" size="2x" />
          </b-button>
        </b-col>
      </b-row>
    </template>
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
      playingID: 0,
      playing: false,
      loop: false
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
    },
    play () {
      this.playing = true
      var video = this.videos[this.currentVideo]
      this.playingID = video.id
      video.element.play()
    },
    pause () {
      this.playing = false
      var video = this.videos[this.currentVideo]
      this.playingID = video.id
      video.element.pause()
    },
    forward () {
      this.videos[this.currentVideo].element.pause() // pause the current video
      this.videos[this.currentVideo].element.currentTime = 0 // reset the time stamp before it is played again
      this.currentVideo = (this.currentVideo + 1) % this.videos.length
      var video = this.videos[this.currentVideo]
      this.playingID = video.id
      if (this.playing) this.play()
    },
    backward () {
      this.videos[this.currentVideo].element.pause() // pause the current video
      this.videos[this.currentVideo].element.currentTime = 0 // reset the time stamp before it is played again
      this.currentVideo = this.currentVideo === 0 ? this.videos.length - 1 : (this.currentVideo - 1)
      var video = this.videos[this.currentVideo]
      this.playingID = video.id
      if (this.playing) this.play()
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
      if (this.loop) {
        this.currentVideo = (doneWith + 1) % this.videos.length
        this.play()
      } else {
        if (this.currentVideo === this.videos.length - 1) {
          // if we are on the last video, pause and wrap around to play again
          this.pause()
          // reset time of the first video so that thumbnail matches
          this.videos[0].element.currentTime = 0
          this.forward()
        } else {
          // otherwise, keep playing
          this.currentVideo = doneWith + 1
          this.play()
        }
      }
    },
    toggleLoop () {
      this.loop = !this.loop
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
  z-index: 10 !important;
  top: 0px;
  left: 0px;
  width: 100%;
}
.modal-body {
  padding: 0px;
}
.modal-footer {
  justify-content: center;
}
.modal-content {
  background: none;
}

.videoContainer + .playing {
  opacity: 1 !important;
}

.videoContainer + :not(.playing) {
  opacity: 0 !important;
}
</style>