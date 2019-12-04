<template>
  <div id="loading-indicator">
    <div v-if="active" style="font-size: 1.5rem;">{{message}}{{indicator}}</div>
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
      if (this.indicator === '....') this.indicator = ''
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
