<template>
  <div id="videopage">
    <h1>Library:</h1>
    <b-row>
      <b-col class="col-sm-8 col-md-5 col-lg-4 col-xl-3">
        <div class="topnav pb-4">
          <b-input-group prepend="Search" class="mt-3">
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
      </b-col>
    </b-row>
    <h4 class="py-3" v-if="activeSearch">Searching for "{{activeSearch}}"</h4>
    <Loading key="videos" :active="loading" />

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
              <b-button @click="deleteVidProcess(video.id)" variant="outline-danger">
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
import Loading from '@/components/Loading.vue'
import api from '@/api'

export default {
  components: {
    Loading
  },
  data: function () {
    return {
      loading: false,
      videos: [],
      search: '',
      activeSearch: ''
    }
  },
  mounted: function () {
    this.loadVideos()
  },
  methods: {
    async deleteVidProcess (idNum) {
      var idBody = {
        id: idNum
      }
      console.log(idBody)
      await api.deleteVideo(idBody)
        .catch(error => {
          this.errors = []
          console.log(error)
        })
      this.loadVideos()
      this.loading = false
    },
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

#videopage {
  padding-right: 40px;
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
  padding: 0rem !important;
  border: none;
}
.card {
  border: none;
}
.card-footer {
  margin-top: -5px;
}
</style>
