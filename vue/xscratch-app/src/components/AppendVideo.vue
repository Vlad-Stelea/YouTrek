<template>
  <b-modal
    id="append"
    size="md"
    header-bg-variant="dark"
    header-border-variant="dark"
    body-bg-variant="dark"
    footer-bg-variant="dark"
    footer-border-variant="dark"
    centered
    striped
    scrollable
    hide-footer
    @hidden="killAll"
    @show="loadVideos"
    :title="'Add Video to: ' + playlist.name"
  >
    <div>
      <!-- Search Bar -->
      <b-input-group prepend="Character Filter" class="mb-3">
        <b-form-input id="search-bar" v-model="charSearch"></b-form-input>
        <b-input-group-append>
          <b-button
            v-if="charSearch != ''"
            @mouseup="clearCharacter()"
            variant="outline-danger"
            id="clear-button"
          >
            <font-awesome-icon icon="times" />
          </b-button>
        </b-input-group-append>
      </b-input-group>
      <b-input-group prepend="Dialogue Filter" class="mb-3">
        <b-form-input id="search-bar" v-model="textSearch"></b-form-input>
        <b-input-group-append>
          <b-button
            v-if="textSearch != ''"
            @mouseup="clearDialogue()"
            variant="outline-danger"
            id="clear-button"
          >
            <font-awesome-icon icon="times" />
          </b-button>
        </b-input-group-append>
      </b-input-group>
    </div>

    <!-- Video List -->
    <div>
      <b-table
        ref="videotable"
        hover
        dark
        borderless
        :filter="{ 'text': this.textSearch, 'char': this.charSearch }"
        :filter-function="filter"
        :items="videos"
        :fields="fields"
      >
        <template v-slot:cell(actions)="row">
          <b-button
            v-if="row.item.state === 'ready'"
            size="sm"
            variant="success"
            @click="appendVid(row.item)"
            class="mr-1"
          >
            <font-awesome-icon size="lg" icon="plus-circle" />
          </b-button>
          <b-button
            v-if="row.item.state === 'loading'"
            size="sm"
            variant="warning"
            @click="appendVid(row.item)"
            class="mr-1"
          >
            <font-awesome-icon size="lg" icon="spinner" spin />
          </b-button>
          <b-button
            v-if="row.item.state === 'added'"
            size="sm"
            variant="light"
            @click="appendVid(row.item)"
            class="mr-1"
          >
            <font-awesome-icon size="lg" icon="check-circle" />
          </b-button>
        </template>
      </b-table>
    </div>
    <template #modal-footer>
      <div>
        <b-btn type="submit" @click="submit" variant="success">Append Video</b-btn>
      </div>
    </template>
  </b-modal>
</template>

<script>
import api from '@/api'

export default {
  props: {
    playlist: Object
  },
  data: function () {
    return {
      search: '',
      videos: [],
      loading: false,
      charSearch: '',
      textSearch: '',
      fields: [
        { key: 'actions', label: 'Add' },
        { key: 'dialogue', label: 'Dialogue', sortable: true, sortDirection: 'desc' },
        {
          key: 'characters',
          label: 'Characters',
          formatter: (value, key, item) => {
            var c = ''
            value.forEach(el => {
              c += ', ' + el
            })
            return c.substring(2)
          },
          sortable: true,
          sortByFormatted: true,
          filterByFormatted: true
        }
      ]
    }
  },
  methods: {
    filter (item, filterObj) {
      const textSearch = filterObj.text.toLowerCase()
      const charSearch = filterObj.char.toLowerCase()
      var charFlag = (charSearch === '')
      item.characters.forEach(char => {
        let character = char.toLowerCase()
        if (character.includes(charSearch)) charFlag = true
      })
      var textFlag = item.dialogue.toLowerCase().includes(textSearch) || textSearch === ''
      return textFlag && charFlag
    },
    async loadVideos () {
      this.loading = true
      this.videos = await api.getVideos()
      this.videos.forEach(el => {
        el.state = 'ready'
      })
      this.loading = false
    },
    clearCharacter () {
      this.charSearch = ''
    },
    clearDialogue () {
      this.textSearch = ''
    },
    async appendVid (item, index) {
      if (item.state === 'added') return
      this.videos.forEach(el => {
        if (el.id === item.id) el.state = 'loading'
      })
      this.$refs.videotable.refresh()
      await api.appendVideo(this.playlist.id, item)
      this.videos.forEach(el => {
        if (el.id === item.id) el.state = 'added'
      })
      this.$refs.videotable.refresh()
      this.$emit('reload')
    },
    killAll () {
      this.videos = []
    }
  }
}
</script>

<style>
.modal-content {
  background-color: transparent;
}

button.close {
  text-shadow: none;
  color: #fff;
  font-size: 1.5rem;
}

.modal-header.close {
  padding-top: 0.5rem;
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
</style>
