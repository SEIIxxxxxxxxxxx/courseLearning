<template>
  <v-app>
    <div>
      <router-view v-if="isRouterAlive"></router-view>
    </div>
  </v-app>
</template>

<script lang="ts">
import Vue from "vue";

export default Vue.extend({
  computed: undefined,
  watch: undefined,
  name: "App",
  provide() {
    return {
      reload: this.reload
    };
  },
  data() {
    return {
      isRouterAlive: true
    };
  },
  methods: {
    reload() {
      this.isRouterAlive = false;
      this.$nextTick(function() {
        this.isRouterAlive = true;
      });
    }
  },
  components: {}
});
</script>

<style lang="scss">
a {
  text-decoration: none;
}
</style>
