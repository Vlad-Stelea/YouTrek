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
    active: Boolean,
    sequence: String
  },
  data: function () {
    return {
      time: 0,
      counter: null,
      indicator: '',
      message: 'Loading',
      tea: false,
      input: false,
      teapotRFC: false
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
        this.tea = false
        this.input = false
        this.teapotRFC = false
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
      if (this.sequence === 'murder') {
        if (t < 6) return 'Loading'
        if (t < 7.5) return '404. Resource not found.'
        if (t < 11) return '404. Resource not found. Kidding. Not sure why this is taking so long'
        if (t < 14) return 'I think AWS murdered my backend'
        if (t < 15) return 'Guys?'
        if (t < 17) return 'Anyone there?'
        if (t < 19) return 'Well then...'
        return 'RIP my backend. I\'m coming for you Jeff. You won\'t get away with this'
      } else if (this.sequence === 'tea') {
        if (t < 6) return 'Loading'
        if (t < 8) return 'Still loading'
        if (t < 11) return 'I think AWS murdered my backend'
        if (t < 12) return 'Guys?'
        if (t < 14) {
          this.teapotRFC = true
          return 'Guys?'
        }
        if (t < 15) {
          this.teapotRFC = false
          return 'Oh.'
        }
        if (t < 18) return 'Oh. It seems I only have tea right now'
        this.tea = true
        return 'If you want tea, click the button below. Otherwise, please wait for me to find your videos'
      } else if (this.sequence === 'playlists') {
        if (t < 6) return 'Loading'
        if (t < 12 && t % 2 < 1) return 'lOaDiNg'
        if (t < 12 && t % 2 >= 1) return 'LoAdInG'
        return 'Still Loading'
      } else if (this.sequence === 'playlist') {
        if (t < 6) return 'Loading'
        if (t < 9) return 'Yes. I know. You waited for the last one'
        if (t < 11) return 'I can only do one at a time'
        if (t < 14) return 'If you really want this to be fast, you should pay me'
        if (t < 16) return 'You know what? That\'s a great idea.'
        this.input = true
        return 'You don\'t get anything until you pay. Give me your credit card number'
      }
      return 'Loading'
    }
  }
}
</script>

<style>
</style>
