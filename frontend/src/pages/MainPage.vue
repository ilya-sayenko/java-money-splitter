<script setup lang="ts">
import CreatePartyModal from "@/windows/CreatePartyModal.vue";
import {ref} from "vue";

const isShowCreatePartyModal = ref(false);

function showCreatePartyModal() {
  isShowCreatePartyModal.value = true;
}

function hideCreatePartyModal() {
  isShowCreatePartyModal.value = false;
}
</script>

<template>
  <div class="container">
    <CreatePartyModal
      :isOpened="isShowCreatePartyModal"
      @close="hideCreatePartyModal"
    ></CreatePartyModal>

    <button
      class="btn btn-main btn-create-party"
      @click="showCreatePartyModal"
    >Создать событие</button>
  </div>
</template>

<style scoped>

</style>