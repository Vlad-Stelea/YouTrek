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
      <VideoCard
        v-for="video in videos"
        v-bind:key="video.name"
        :video="video"
        @remove="deleteVidProcess"
        :isAdmin="false"
        :isPlaylist="false"
      />
    </div>
  </div>
</template>

<script>
import Loading from '@/components/Loading.vue'
import VideoCard from '@/components/VideoCard.vue'
import api from '@/api'

export default {
  components: {
    Loading,
    VideoCard
  },
  props: {
    reloadFlag: Number
  },
  watch: {
    reloadFlag: function (val) {
      this.loadVideos()
    }
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
      if (!confirm('Are you sure you want to delete this video?')) return
      this.loading = true
      this.videos = await api.deleteVideo(idNum)
        .catch(error => {
          this.errors = []
          console.log(error)
        })
      this.loading = false
    },
    async loadVideos () {
      this.loading = true
      this.videos = []
      this.videos = await api.getVideos()
      this.loading = false
    },
    async searchVideos () {
      this.activeSearch = this.search
      if (this.search === '') this.activeSearch = ''
      this.loading = true
      console.log(this.search)
      this.videos = await api.searchVideos(this.search)
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
