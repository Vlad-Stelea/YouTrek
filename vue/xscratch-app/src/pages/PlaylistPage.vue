<template>
  <div :id="playlist.name + playlist.id" v-on:playlist-change="loadPlaylist">
    <h1>
      {{playlist.name}}
      <b-button v-b-modal.play v-if="!loading" class="ml-4" variant="success">
        <font-awesome-icon icon="play-circle" />
      </b-button>
      <b-button
        v-if="!loading"
        @click="deletePlaylistProcess(playlist.id)"
        variant="outline-danger"
      >
        <font-awesome-icon icon="trash" />
      </b-button>
    </h1>

    <Loading :key="playlist.name" :active="loading" />

    <div v-if="!loading && videos.length == 0">No videos in this playlist</div>

    <div id="divVideo">
      <!-- List of videos -->
      <b-card
        v-for="video in videos"
        v-bind:key="video.name"
        class="vidContainer m-2"
        v-bind:footer="video.dialogue"
        bg-variant="dark"
      >
        <b-card-header>
          <b-row align-h="between">
            <b-col cols="auto" class="pt-1">{{video.name}}</b-col>
            <b-col cols="auto" class="mb-1 pr-3">
              <b-button variant="outline-danger" @click="removeVideo(video.id)">
                <font-awesome-icon icon="minus-circle" />
              </b-button>
            </b-col>
          </b-row>
        </b-card-header>
        <video style="padding-bottom: 0px;" controls=" " width="320" height="240">
          <source v-bind:src="video.url" type="video/ogg" />/>
        </video>
      </b-card>
      <!-- Add Video Card -->
      <b-card
        class="vidContainer addVideoCard m-2"
        bg-variant="dark"
        footer="Append a video to this playlist"
        v-b-modal.append
      >
        <b-card-header>
          <b-row align-h="between">
            <b-col cols="auto" class="pt-1">Add Video</b-col>
          </b-row>
        </b-card-header>
        <div class="addVideoSlot">
          <font-awesome-icon icon="plus-circle" size="6x" />
        </div>
      </b-card>
    </div>
    <PlayPlaylist :playlist="playlist" :videos="videos" id="play" />
    <AppendVideo :playlist="playlist" id="append" @reload="loadPlaylist" />
  </div>
</template>

<script>
import PlayPlaylist from '@/components/PlayPlaylist'
import AppendVideo from '@/components/AppendVideo'
import Loading from '@/components/Loading'
import api from '@/api'

export default {
  data: function () {
    return {
      loading: true,
      playlist: {},
      videos: []
    }
  },
  components: {
    PlayPlaylist,
    Loading,
    AppendVideo
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
    async deletePlaylistProcess (idNum) {
      var idBody = {
        id: idNum
      }
      await api.deletePlaylist(idBody)
        .catch(error => {
          this.errors = []
          console.log(error)
        })
      this.$router.replace({name: 'videos'})
      this.$emit('reloadPlaylists')
      this.loading = false
    },
    async loadPlaylist () {
      this.loading = true
      this.playlist = await api.getPlaylist(this.$route.params.playlistID)
      this.videos = this.playlist.videos.videos
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
      this.loading = false
    },
    async removeVideo (videoID) {
      this.loading = true
      await api.removeVideo(this.playlist.id, videoID)
      this.loadPlaylist()
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
.addVideoSlot {
  width: 320px !important;
  height: 240px !important;
  display: flex;
  align-items: center;
  justify-content: center;
  padding-bottom: 0px;
  stroke: #0cad0b;
  stroke-width: 1rem;
  color: transparent;
}

.addVideoCard .card-footer {
  margin-top: 0px !important;
}

/* .addVideoSlot.fa-6x {
  margin-left: auto;
} */

#videopage {
  padding-right: 40px;
}
.card-header {
  font-size: 2rem;
  align-content: baseline;
  padding-top: 2px;
  padding-bottom: 2px;
}
.card-body {
  padding: 0px !important;
  border: none;
}
.card {
  border: none;
}
.card-footer {
  margin-top: -5px;
}
</style>
