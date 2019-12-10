<template>
  <b-modal
    id="upload"
    size="md"
    header-bg-variant="dark"
    header-border-variant="dark"
    body-bg-variant="dark"
    footer-bg-variant="dark"
    footer-border-variant="dark"
    centered
    title="Upload Video"
    @hidden="reset"
  >
    <div v-if="submitting">
      <h2>Submitting</h2>
    </div>
    <div v-else>
      <form>
        <b-form-group label="Video Title:" label-cols-sm="3" label-for="input">
          <b-form-input v-model="upload.title" :state="titleState" placeholder="Enter video title" />
          <b-form-invalid-feedback :state="titleState">You must give this video a title</b-form-invalid-feedback>
        </b-form-group>
        <b-form-group label="Video Dialogue:" label-cols-sm="3" label-for="input">
          <b-form-input
            v-model="upload.dialogue"
            :state="dialogueState"
            placeholder="Enter video dialogue"
          />
          <b-form-invalid-feedback :state="dialogueState">
            Every video has to have dialogue.... even if it's just
            <code>SCREAMING</code>
          </b-form-invalid-feedback>
        </b-form-group>
        <b-form-group label="Video Characters:" label-cols-sm="3" label-for="input">
          <b-form-input
            v-model="upload.characters"
            :state="characterState"
            placeholder="Enter video characters"
          />
          <b-form-invalid-feedback :state="titleState">You must give this video characters</b-form-invalid-feedback>
        </b-form-group>
      </form>
      <b-form-group label="Video File" label-cols-sm="3" label-for="input">
        <label for="file">Choose file to upload</label>
        <input type="file" @change="processFile($event)" name="avatar" accept=".ogg" />
      </b-form-group>
    </div>
    <template #modal-footer>
      <div>
        <b-btn type="reset" @click="reset" variant="outline-danger">Reset</b-btn>
        <b-btn type="submit" @click="submit" variant="success">Upload Video</b-btn>
      </div>
    </template>
  </b-modal>
</template>

<script>
import api from '@/api'
var globalEncodedVideo = ''
export default {
  data: function () {
    return {
      upload: {
        title: '',
        dialogue: '',
        characters: '',
        encodedVideo: ''
      },
      failedValidation: false,
      submitting: false
    }
  },
  computed: {
    titleState () {
      if (this.failedValidation) {
        return this.upload.title !== ''
      }
      return this.upload.title === '' ? null : true
    },
    dialogueState () {
      if (this.failedValidation) {
        return this.upload.dialogue !== ''
      }
      return this.upload.dialogue === '' ? null : true
    },
    characterState () {
      if (this.failedValidation) {
        return this.upload.characters !== ''
      }
      return this.upload.characters === '' ? null : true
    }
  },

  methods: {
    processFile (event) {
      var data = event.target.files[0]
      var reader = new FileReader()
      reader.onload = function (readerEvt) {
        var binaryString = readerEvt.target.result.substr(22)
        globalEncodedVideo = binaryString
        console.log(globalEncodedVideo)
      }
      reader.readAsDataURL(data)
    },
    async submit (event) {
      this.upload.encodedVideo = globalEncodedVideo
      if (this.upload.title === '' || this.upload.dialogue === '' || this.upload.characters === '' || this.upload.encodedVideo === '') {
        this.failedValidation = true
        return
      }
      this.submitting = true
      var videoBody = {
        name: this.upload.title,
        dialogue: this.upload.dialogue,
        characters: this.upload.characters.split(','),
        video: this.upload.encodedVideo
      }
      console.log(videoBody)
      await api.createVideo(videoBody)
      await api.getVideos()
      this.submitting = false
      this.$emit('reloadVideos')
      this.$bvModal.hide('upload')
    },
    reset () {
      this.upload = {
        title: '',
        dialogue: '',
        characters: '',
        video: ''
      }
      this.failedValidation = false
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
</style>
