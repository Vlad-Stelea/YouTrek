<template>
  <b-card class="vidContainer m-3">
    <video style="padding-bottom: 0px;" controls=" " width="320" height="240">
      <source v-bind:src="video.url" type="video/ogg" />/>
    </video>
    <b-card-footer style="height: 7rem;">
      <b-row id="footer" class="d-flex justify-content-between">
        <div class="d-flex flex-column">
          <div id="dialogue" class="pl-3 pt-0 text-wrap font-weight-bold">{{video.dialogue}}</div>
          <div v-if="!isPlaylist" class="ml-3 pt-1">
            <b-badge
              class="mx-1 pt-2"
              pill
              id="character-badge"
              variant="dark"
              v-for="character in video.characters"
              :key="character"
            >{{character}}</b-badge>
          </div>
        </div>
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
            v-if="isAdmin && !video.isRemote && video.isAvailable"
            @click="setAvail(video.id, false)"
            variant="outline-success"
            class="border-0"
            title="Mark as private"
          >
            <font-awesome-icon icon="globe" />
          </b-button>
          <b-button
            v-if="isAdmin && !video.isRemote && !video.isAvailable"
            @click="setAvail(video.id, true)"
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
      await api.setAvailability(vidID, vidAvail)
      this.available()
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

#character-badge {
  font-weight: 200;
  font-size: 1.1rem;
}
</style>
