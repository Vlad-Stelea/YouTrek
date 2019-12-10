<template>
  <div :id="playlist.name + playlist.id" v-on:playlist-change="loadPlaylist">
    <h1>
      {{playlist.name}}
      <b-button
        v-b-modal.play
        v-if="!loading"
        class="ml-4 border-0"
        title="Play Playlist"
        variant="success"
      >
        <font-awesome-icon icon="play-circle" size="2x" />
      </b-button>
      <b-button
        v-b-modal.append
        v-if="!loading"
        class="mx-3 border-0"
        title="Append Video"
        variant="outline-success"
      >
        <font-awesome-icon icon="plus-circle" size="2x" />
      </b-button>
      <b-button
        v-if="!loading"
        @click="deletePlaylistProcess(playlist.id)"
        class="border-0"
        variant="outline-danger"
        title="Delete Playlist"
      >
        <font-awesome-icon icon="trash" size="2x" />
      </b-button>
    </h1>

    <Loading :key="playlist.name" :active="loading" />

    <div v-if="!loading && videos.length == 0">No videos in this playlist</div>

    <div id="divVideo">
      <!-- List of videos -->
      <VideoCard
        v-for="video in videos"
        v-bind:key="video.name"
        :video="video"
        @remove="removeVideo"
        :isAdmin="false"
        :isPlaylist="true"
      />
    </div>
    <PlayPlaylist :playlist="playlist" :videos="videos" id="play" />
    <AppendVideo :playlist="playlist" id="append" @reload="loadPlaylist" />
  </div>
</template>

<script>
import PlayPlaylist from '@/components/PlayPlaylist'
import AppendVideo from '@/components/AppendVideo'
import Loading from '@/components/Loading'
import VideoCard from '@/components/VideoCard'
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
    AppendVideo,
    VideoCard
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
      if (!confirm('Are you sure you want to delete this playlist?')) return
      this.loading = true
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
      this.loading = false
    },
    async removeVideo (videoID) {
      if (!confirm('Are you sure you want to remove this video from the playlist?')) return
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
