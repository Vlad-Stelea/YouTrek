<template>
  <div id="loading-indicator">
    <div v-if="active" style="font-size: 1.5rem;">{{message}}{{indicator}}</div>
    <code v-if="teapotRFC">418 I'm a teapot</code>
    <b-button
      v-if="tea"
      class="mt-3"
      variant="outline-success"
      href="https:\\drinktea.com"
    >Tea Available Here</b-button>
    <b-input v-if="input" class="mt-3 w-25"></b-input>
  </div>
</template>

<script>
export default {
  props: {
    active: Boolean
  },
  data: function () {
    return {
      time: 0,
      counter: null,
      indicator: '',
      message: 'Loading'
    }
  },
  watch: {
    active: function () {
      console.log(this.active)
      if (this.active) {
        this.counter = setInterval(() => {
          this.timeStep()
        }, 500)
      } else {
        clearInterval(this.counter)
        this.time = 0
        this.indicator = ''
        this.message = 'Loading'
      }
    }
  },
  methods: {
    timeStep () {
      this.time = this.time + 1
      this.indicator = this.indicator + '.'
      if (this.indicator === '....' || this.time > 12) this.indicator = ''
      this.message = this.getMessage(this.time / 2)
    },
    getMessage (t) {
      if (t < 6) return 'Loading'
      return 'One moment'
    }
  }
}
</script>

<style>
</style>
