<template>
  <div id="adminpage">
    <h1>Third Party Libraries:</h1>
    <div class="topnav">
      <b-input-group prepend="Add URL" class="mt-3 w-25">
        <b-form-input id="search-bar"></b-form-input>
        <b-input-group-append>
          <b-button variant="success">
            <font-awesome-icon icon="plus" />
          </b-button>
        </b-input-group-append>
      </b-input-group>
    </div>

    <hr class="bg-success" />

    <h1>Library:</h1>
    <div class="topnav pb-4">
      <b-input-group prepend="Search" class="mt-3 w-25">
        <b-form-input id="search-bar" v-model="search" @keydown.enter="searchVideos()"></b-form-input>
        <b-input-group-append>
          <b-button
            v-if="search != ''"
            @mouseup="clearSearch()"
            variant="outline-danger"
            id="clear-button"
          >
            <font-awesome-icon icon="times" />
          </b-button>
          <b-button @click="searchVideos()" variant="success">
            <font-awesome-icon icon="coffee" />
          </b-button>
        </b-input-group-append>
      </b-input-group>
    </div>
    <h4 class="py-3" v-if="activeSearch">Searching for "{{activeSearch}}"</h4>
    <div v-if="loading">Loading...</div>

    <div id="divVideo">
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
              <b-button
                v-if="video.isAvailable"
                variant="success"
                @click="video.isAvailable = false"
              >
                <font-awesome-icon icon="globe" />
              </b-button>
              <b-button v-else @click="video.isAvailable = true" variant="outline-secondary">
                <font-awesome-icon icon="globe" />
              </b-button>
              <b-button variant="outline-danger">
                <font-awesome-icon icon="trash" />
              </b-button>
            </b-col>
          </b-row>
        </b-card-header>
        <video style="padding-bottom: 0px;" controls=" " width="320" height="240">
          <source v-bind:src="video.url" type="video/ogg" />/>
        </video>
      </b-card>
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
      tlds: [],
      activeSearch: '',
      loading: ''
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
      this.activeSearch = this.search
      if (this.search === '') this.activeSearch = ''
      this.loading = true
      console.log(this.search)
      this.videos = await api.searchVideos(this.search)
      this.videos.forEach(el => {
        el.url = 'https://xscratch-videos.s3.us-east-2.amazonaws.com' + el.url
      })
      this.loading = false
    },
    async clearSearch () {
      this.search = ''
      this.searchVideos()
      this.activeSearch = ''
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
#clear-button,
#clear-button:hover,
#clear-button:focus {
  border: none;
  border-top: 1px solid;
  border-bottom: 1px solid;
  background-color: #fff;
  border-color: #ced4da;
}

#clear-button:hover,
#clear-button:focus {
  color: #f00;
  outline: none !important;
  box-shadow: none !important;
}
#search-bar,
#search-bar:focus {
  outline: none !important;
  box-shadow: none !important;
  border-right: none;
  border-color: #ced4da;
}
.card-header {
  font-size: 2rem;
  align-content: baseline;
  padding-top: 2px;
  padding-bottom: 2px;
}
.card-body {
  padding: 0px;
  border: none;
}
.card {
  border: none;
}
.card-footer {
  margin-top: -5px;
}
</style>