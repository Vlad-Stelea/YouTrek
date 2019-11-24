<template>
  <div id="app">
    <div id="header">
      <h1>youTrek</h1>
      <button id="uploadButton">+ Upload Video</button>
    </div>
    <div id="sidebar">
      <div class="sidebar-item">My Videos</div>
      <div style="height: 60px;"></div>
      <div class="sidebar-item" v-for="p in playlists" v-bind:key="p.id">{{p.name}}</div>
    </div>
    <div id="content">
      <router-view />
    </div>
  </div>
</template>

<script>
import api from '@/api'

export default {
  name: 'App',
  data: function () {
    return {
      playlists: []
    }
  },
  created: function () {
    this.loadPlaylists()
  },
  methods: {
    async loadPlaylists () {
      this.playlists = await api.getPlaylists()
      console.log(this.playlists)
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
  color: #f90;
  text-align: center;
  font-weight: 400;
  font-size: 2.5rem;
  /* padding: 14px 16px; */
}

#header #uploadButton {
  color: #f2f2f2;
  background-color: #f90;
  font-weight: 300;
  font-size: 2rem;
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
  width: 100vw;
  padding-left: 20px;
  background-color: #232121;
  overflow: scroll;
}

.sidebar-item {
  font-size: 1.5rem;
  text-align: center;
  margin-top: 1rem;
  padding-top: 0.3rem;
  padding-bottom: 0.3rem;
  background-color: #312f2c;
  color: #f2f2f2;
}

.sidebar-item:hover {
  background-color: #f90;
}
</style>
