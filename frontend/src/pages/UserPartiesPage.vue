<script setup lang="ts">
import PartyCard from "@/components/PartyCard.vue";
import {storeToRefs} from "pinia";
import {onMounted, ref} from "vue";
import {useAuthStore} from "@/stores/authStore.ts";
import {useMoneySplitterHttpClient} from "@/http/data/useMoneySplitterHttpClient.ts";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.ts";
import type {PartyWithAggregates} from "@/models/PartyWithAggregates.ts";
import {useI18n} from "vue-i18n";
import CreatePartyModal from "@/windows/CreatePartyModal.vue";

const { t } = useI18n();
const userParties = ref<PartyWithAggregates[]>();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const userDataHttpClient = useUserDataHttpClient();
const moneySplitterHttpClient = useMoneySplitterHttpClient();
const isShowCreatePartyModal = ref(false);

function showCreatePartyModal() {
  isShowCreatePartyModal.value = true;
}

function onCloseCreatePartyModal() {
  isShowCreatePartyModal.value = false;
}

async function loadUserParties() {
  const partyIds: string[] = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  userParties.value = await moneySplitterHttpClient.getAllPartyWithAggregatesById(partyIds);
}

onMounted(async () => {
  if (user) {
    await loadUserParties();
  }
})
</script>

<template>
  <div class="container">
    <div class="card">
      <div class="header-container">
        <h2>📅 {{ t('headers.myParties') }}</h2>
        <button class="btn btn-main btn-create-party" @click="showCreatePartyModal">{{ t('buttons.createParty') }}</button>
      </div>

      <div class="parties-grid">
        <PartyCard
          v-for="party in userParties"
          :party="party"
          @reloadParties="loadUserParties"
        ></PartyCard>
      </div>
    </div>
  </div>

  <CreatePartyModal
    :is-opened="isShowCreatePartyModal"
    @close="onCloseCreatePartyModal"
  ></CreatePartyModal>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";

.parties-grid {
  display: grid;
  grid-template-columns: 1fr 1fr 1fr;
}

.header-container {
  display: flex;
  justify-content: space-between;
  border-bottom: 2px solid colors.$card-border-header;
  margin-bottom: 1rem;
  align-items: center;

  h2 {
    border: none;
    margin-bottom: 0;
  }
}

.btn-create-party {
  width: auto;
  padding: 0.85rem 1rem;
  margin: 1rem 0 1rem 1rem;
}
</style>