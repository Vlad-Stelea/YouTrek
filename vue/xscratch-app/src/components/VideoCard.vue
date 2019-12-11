<template>
  <b-card class="vidContainer m-3">
    <video style="padding-bottom: 0px;" controls=" " width="320" height="240">
      <source v-bind:src="video.url" type="video/ogg" />/>
    </video>
    <b-card-footer style="height: 5rem;">
      <b-row id="footer" class="d-flex justify-content-between">
        <div id="dialogue" class="pl-3 pt-0 text-wrap font-weight-bold">{{video.dialogue}}</div>
        <div class="float-right d-flex flex-column pt-0 mb-1 pr-3">
          <b-button
            v-if="!isPlaylist"
            @click="remove"
            variant="outline-danger"
            class="border-0"
            title="Delete Video"
          >
            <font-awesome-icon icon="trash" />
          </b-button>
          <b-button
            v-else
            @click="remove"
            variant="outline-danger"
            class="border-0"
            title="Remove Video"
          >
            <font-awesome-icon icon="minus-circle" />
          </b-button>
          <b-button
            v-if="isAdmin && video.isAvailable"
            @click="video.isAvailable = setAvail(video.id, false)"
            variant="outline-success"
            class="border-0"
            title="Mark as private"
          >
            <font-awesome-icon icon="globe" />
          </b-button>
          <b-button
            v-if="isAdmin && !video.isAvailable"
            @click="video.isAvailable = setAvail(video.id, true)"
            variant="outline-secondary"
            class="border-0"
            title="Mark as public"
          >
            <font-awesome-icon icon="globe" />
          </b-button>
        </div>
      </b-row>
    </b-card-footer>
  </b-card>
</template>

<script>
import api from '@/api'

export default {
  props: {
    video: Object,
    isAdmin: Boolean,
    isPlaylist: Boolean
  },
  methods: {
    remove () {
      this.$emit('remove', this.video.id)
    },
    available () {
      this.video.isAvailable = !this.video.isAvailable
    },
    async setAvail (vidID, vidAvail) {
      const response = await api.setAvailability(vidID, vidAvail)
      console.log(response.isAvailable)
      if (!response.isAvailable) {
        this.loadVideos()
      }
      return response.isAvailable
    }
  }
}
</script>
<style>
.vidContainer {
  float: left;
  width: 320px;
}
#footer {
  width: 320px;
}

.card-footer,
.vidContainer {
  background: transparent !important;
  background-color: transparent !important;
}

#dialogue {
  width: 260px;
  font-size: 1.3rem;
}

#dialogue:hover {
  text-decoration: underline;
}
</style>
