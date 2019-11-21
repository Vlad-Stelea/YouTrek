<template>
  <div id="app">
    <div id="header">
      <h1>youTrek</h1>
      <button id="uploadButton">+ Upload Video</button>
    </div>
    <div id="sidebar">test</div>
    <div id="content">
      <router-view />
    </div>
  </div>
</template>

<script>
export default {
  name: 'App'
}
</script>

<style>
#app {
  font-family: "Avenir", Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  background-color: #232121;
  margin: 0px;
  padding: 0px;
  overflow: hidden;
}

#header {
  position: fixed;
  display: flex;
  width: 100%;
  height: 60px;
  background-color: #000000;
  align-items: center;
  padding-left: 2rem;
  padding-right: 2rem;
  align-items: center;
  justify-content: space-between;
  box-sizing: border-box;
}

#header h1 {
  color: #0cad0b;
  text-align: center;
  font-weight: 300;
  font-size: 2.2rem;
  /* padding: 14px 16px; */
}

#header #uploadButton {
  color: #f2f2f2;
  background-color: #0cad0b;
  font-weight: 300;
  font-size: 2rem;
  border: none;
  border-radius: 5px;
  padding: 5px 15px;
}

#sidebar {
  height: 100%; /* Full-height: remove this if you want "auto" height */
  width: 160px; /* Set the width of the sidebar */
  position: fixed; /* Fixed Sidebar (stay in place on scroll) */
  z-index: 1; /* Stay on top */
  top: 0; /* Stay at the top */
  left: 0;
  background-color: #312f2c; /* Black */
  overflow-x: hidden; /* Disable horizontal scroll */
  margin-top: 60px;
}

#content {
  z-index: -1;
  position: absolute;
  top: 60px;
  left: 160px;
  height: 100vh;
  width: 100%;
  background-color: #232121;
  overflow: scroll;
}
</style>
