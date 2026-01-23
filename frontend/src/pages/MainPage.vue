<script setup lang="ts">
import CreatePartyModal from "@/windows/CreatePartyModal.vue";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import UserParties from "@/components/UserParties.vue";
import {storeToRefs} from "pinia";

const authStore = useAuthStore();
const { isLoggedIn } = storeToRefs(authStore);

const isShowCreatePartyModal = ref(false);

function showCreatePartyModal() {
  isShowCreatePartyModal.value = true;
}

function hideCreatePartyModal() {
  isShowCreatePartyModal.value = false;
}

onMounted(async () => {
  if (isLoggedIn) {
    await authStore.loadProfile();
  }
})
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

    <UserParties v-show="isLoggedIn"></UserParties>
  </div>
</template>

<style scoped>

</style>