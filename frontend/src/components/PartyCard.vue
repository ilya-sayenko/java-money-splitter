<script setup lang="ts">
import {dateFormat} from "@/utils/dateFormat.ts";
import type {PartyWithAggregates} from "@/models/PartyWithAggregates.ts";
import {useI18n} from "vue-i18n";
import {useRouter} from "vue-router";
import DeletePartyModal from "@/windows/DeletePartyModal.vue";
import {ref} from "vue";
import UpdatePartyModal from "@/windows/UpdatePartyModal.vue";

defineProps<{
  party: PartyWithAggregates
}>();

const emits = defineEmits<{
  (e: 'reloadParties'): void,
}>();

const { t, n } = useI18n();
const router = useRouter();
const isShowDeletePartyModal = ref(false);
const isShowUpdatePartyModal = ref(false);

function showDeletePartyModal() {
  isShowDeletePartyModal.value = true;
}

function hideDeletePartyModal() {
  isShowDeletePartyModal.value = false;
}

function showUpdatePartyModal() {
  isShowUpdatePartyModal.value = true;
}

function hideUpdatePartyModal() {
  isShowUpdatePartyModal.value = false;
}

function routeToPartyPage(partyId: string) {
  router.push(`/parties/${partyId}`);
}
</script>

<template>
  <div class="card card-translated party-card">
    <div class="header-container">
      <h2
        class="party-card-name"
        @click="routeToPartyPage(party.id)"
      >{{ party.name }}</h2>
      <div class="btn-edit-delete">
        <button @click="showUpdatePartyModal">✏️</button>
        <button @click="showDeletePartyModal">❌</button>
      </div>
    </div>

    <p>{{ dateFormat(party.createDate) }}</p>
    <p>{{ party.description }}</p>
    <div class="party-card-meta">
      <span>{{ party.participantsCount }} {{ t('labels.participantsAlt') }}</span>
      <span>{{ party.spendingsCount }} {{ t('labels.spendingsAlt') }}</span>
    </div>
    <div class="party-card-amount">
      {{ t('labels.total') }}: {{ n(party.totalAmount, 'currency') }}
    </div>
  </div>

  <DeletePartyModal
    :is-opened="isShowDeletePartyModal"
    :partyId="party.id"
    @close="hideDeletePartyModal"
    @reloadParties="emits('reloadParties')"
  ></DeletePartyModal>

  <UpdatePartyModal
    :is-opened="isShowUpdatePartyModal"
    :party="party"
    @close="hideUpdatePartyModal"
    @reloadParties="emits('reloadParties')"
  ></UpdatePartyModal>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/colors.scss";
.party-card {

  p, span {
    color: colors.$text-midtone;
  }

  h2 {
    cursor: pointer;
    border: none;
    margin-bottom: 0.5rem;
    padding-bottom: 0;
  }

  &:nth-child(3n+1) {
    margin-left: 0;
  }

  &:nth-child(3n+3) {
    margin-right: 0;
  }
}

.header-container {
  display: flex;
  justify-content: space-between;
  align-items: start;
}

.party-card-name {
  font-size: 1.3rem;
  font-weight: 600;
  color: #2d3748;
}

.party-card-meta {
  margin-top: 0.5rem;
  display: flex;
  justify-content: space-between;
}

.party-card-amount {
  margin-top: 1rem;
  padding-top: 1rem;
  border-top: 2px solid colors.$card-border-header;
  font-weight: 600;
  font-size: 1.1rem;
  color: colors.$text-summary-light;
}
</style>