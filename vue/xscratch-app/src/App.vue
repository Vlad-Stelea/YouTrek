<template>
  <div id="app">
    <div id="header">
      <router-link :to="{ name: 'videos'}">
        <h1>youTrek</h1>
      </router-link>
      <b-button id="uploadButton" v-b-modal.upload>+ Upload Video</b-button>
    </div>
    <div id="sidebar">
      <router-link :to="{ name: 'videos'}">
        <div class="sidebar-item" :class="{ 'active' : 'videos' == $route.name }">My Videos</div>
      </router-link>
      <router-link :to="{ name: 'admin'}">
        <div class="sidebar-item" :class="{ 'active' : 'admin' == $route.name }">Admin Page</div>
      </router-link>
      <div style="height: 60px;"></div>
      <div v-if="addingPlaylist" id="newPlaylistForm">
        <b-input-group class="px-1">
          <b-form-input
            id="add-playlist-input"
            ref="addPlaylistInput"
            v-model="addPlaylistValue"
            @keydown.enter="addPlaylist()"
            @blur="cancelAddPlaylist"
          ></b-form-input>
        </b-input-group>
      </div>
      <div v-else class="sidebar-item" id="newPlaylist" @click="newPlaylistForm">+ New Playlist</div>
      <Loading class="sidebar-item" key="playlists" :active="loadingPlaylists" />
      <router-link
        :to="{ name: 'playlist', params: { playlistID: p.id }}"
        v-for="p in playlists"
        v-bind:key="p.id"
      >
        <div class="sidebar-item" :class="{ 'active' : p.id == $route.params.playlistID}">{{p.name}}</div>
      </router-link>
    </div>
    <div id="content">
      <router-view @reloadPlaylists="loadPlaylists" :reloadFlag="reloadFlag" class="pt-3" />
    </div>
    <UploadVideo @reloadVideos="reloadVideos" />
  </div>
</template>

<script>
import UploadVideo from '@/components/UploadVideo'
import Loading from '@/components/Loading'
import api from '@/api'

export default {
  name: 'App',
  components: {
    UploadVideo,
    Loading
  },
  data: function () {
    return {
      playlists: [],
      loadingPlaylists: false,
      addingPlaylist: false,
      addPlaylistValue: '',
      reloadFlag: 0
    }
  },
  mounted: function () {
    this.loadPlaylists()
  },
  methods: {
    reloadVideos () {
      this.reloadFlag += 1
    },
    async loadPlaylists () {
      this.loadingPlaylists = true
      this.playlists = await api.getPlaylists()
      this.loadingPlaylists = false
    },
    async newPlaylistForm () {
      this.addingPlaylist = true
      this.$nextTick(() => this.$refs.addPlaylistInput.focus())
    },
    async addPlaylist () {
      this.addingPlaylist = false
      this.loadingPlaylists = true
      this.playlists = []
      api.createPlaylist(this.addPlaylistValue).then(() => {
        this.loadPlaylists()
      })
    },
    cancelAddPlaylist () {
      this.addingPlaylist = false
      this.addPlaylistValue = ''
    }
  }
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #232121;
  margin: 0px;
  padding: 0px;
  overflow: hidden;
}

#header {
  z-index: 3;
  position: fixed;
  display: flex;
  width: 100%;
  height: 60px;
  background-color: #000000;
  align-items: center;
  padding-left: 2rem;
  padding-right: 2rem;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
}

#header h1 {
  color: #0cad0b;
  text-align: center;
  font-weight: 400;
  font-size: 2.5rem;
  /* padding: 14px 16px; */
}

#header #uploadButton {
  color: #f2f2f2;
  background-color: #0cad0b;
  font-weight: 300;
  font-size: 1.5rem;
  border: none;
  border-radius: 5px;
  padding: 5px 15px;
}

#sidebar {
  height: 100%; /* Full-height: remove this if you want "auto" height */
  width: 160px; /* Set the width of the sidebar */
  position: fixed; /* Fixed Sidebar (stay in place on scroll) */
  z-index: 1; /* Stay on top */
  top: 0; /* Stay at the top */
  left: 0;
  background-color: #312f2c; /* Black */
  overflow-x: hidden; /* Disable horizontal scroll */
  margin-top: 60px;
  color: #f2f2f2;
}

#content {
  z-index: 0;
  position: absolute;
  top: 60px;
  left: 160px;
  height: 100vh;
  padding-bottom: 60px;
  width: calc(100vw - 160px);
  padding-left: 20px;
  padding-right: 20px;
  background-color: #232121;
  overflow: scroll;
}

.sidebar-item {
  font-size: 1.5rem !important;
  text-align: center;
  margin-top: 1rem;
  padding-top: 0.3rem;
  padding-bottom: 0.3rem;
  background-color: #312f2c;
  color: #f2f2f2;
  text-decoration: none;
}

#add-playlist-input {
  font-size: 1.5rem !important;
  text-align: center;
  margin-top: 1rem;
  margin-left: 0.2rem;
  margin-right: 0.2rem;
  padding-top: 0.3rem;
  padding-bottom: 0.3rem;
  background-color: #312f2c;
  color: #f2f2f2;
  text-decoration: none;
  outline: none !important;
  box-shadow: none !important;
  border: none;
  border-bottom: 1px solid #0cad0b;
  border-radius: 0px;
}

router-link {
  text-decoration: none;
}

#newPlaylist {
  color: #0cad0b;
}

#newPlaylist:hover {
  color: #f2f2f2;
  text-decoration: underline;
}

a {
  text-decoration: none;
}

.sidebar-item:not(.active):hover {
  background-color: #0cad0b77;
}

.active {
  background-color: #0cad0b;
}
</style>
