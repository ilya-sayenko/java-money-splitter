<script setup lang="ts">
import {storeToRefs} from "pinia";
import {usePartyStore} from "@/stores/partyStore.ts";
import {dateFormat} from "@/utils/dateFormat.ts";
import {useI18n} from "vue-i18n";
import {ref} from "vue";
import UpdatePartyModal from "@/windows/UpdatePartyModal.vue";
import DeletePartyModal from "@/windows/DeletePartyModal.vue";
import {useRouter} from "vue-router";
import {useAuthStore} from "@/stores/authStore.ts";

const { t } = useI18n();
const partyStore = usePartyStore();
const { party } = storeToRefs(partyStore);
const authStore = useAuthStore();
const { isLoggedIn } = storeToRefs(authStore);
const router = useRouter();
const isShowDeletePartyModal = ref(false);
const isShowUpdatePartyModal = ref(false);

function showDeletePartyModal() {
  isShowDeletePartyModal.value = true;
}

function onCloseDeletePartyModal(result: boolean) {
  isShowDeletePartyModal.value = false;

  if (!result) {
    return;
  }

  if (isLoggedIn) {
    router.push( { name: 'ProfileParties' } );
  } else {
    router.push({ name: 'Main' });
  }
}

function showUpdatePartyModal() {
  isShowUpdatePartyModal.value = true;
}

function onCloseUpdatePartyModal() {
  isShowUpdatePartyModal.value = false;
  if (party.value) {
    partyStore.loadPartyById(party.value.id);
  }
}
</script>

<template>
  <div class="card party-header-card" v-if="party">
    <div class="header-container">
      <h2>{{ party.name }}</h2>
      <div class="btn-edit-delete">
        <button :title="t('titles.editParty')" @click="showUpdatePartyModal">✏️</button>
        <button :title="t('titles.deleteParty')" @click="showDeletePartyModal">❌</button>
      </div>
    </div>
    <p>{{ dateFormat(party.createDate) }}</p>
    <p>{{ party.description }}</p>

    <DeletePartyModal
      :is-opened="isShowDeletePartyModal"
      :partyId="party.id"
      @close="onCloseDeletePartyModal"
    ></DeletePartyModal>

    <UpdatePartyModal
      :is-opened="isShowUpdatePartyModal"
      :party="party"
      @close="onCloseUpdatePartyModal"
    ></UpdatePartyModal>
  </div>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";

.party-header-card {
  margin-left: 0;
  cursor: default;
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: start;
  border-bottom: 2px solid colors.$card-border-header;
  margin-bottom: 1rem;
  padding-bottom: 1rem;

  h2 {
    margin-bottom: 0;
    padding-bottom: 0;
    border: none;
  }
}

p {
  color: colors.$text-midtone;
}
</style>