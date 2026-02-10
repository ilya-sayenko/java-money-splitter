<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted, ref} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";
import {SplitType} from "@/models/SplitType.ts";
import CreateSpendingModal from "@/windows/CreateSpendingModal.vue";
import {currencyFormat} from "@/utils/currencyFormat.ts";
import {useI18n} from "vue-i18n";

const { t } = useI18n();
const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { spendings } = storeToRefs(partyStore);
const isShowCreateSpendingModal = ref(false);

function showCreateSpendingModal() {
  isShowCreateSpendingModal.value = true;
}

function hideCreateSpendingModal() {
  isShowCreateSpendingModal.value = false;
}

const showSpendings = computed(() => {
  return spendings.value && spendings.value.length !== 0;
})

async function deleteSpendingById(id: string) {
  await partyStore.deleteSpendingById(id);
  await Promise.all([
    partyStore.loadSpendingsByPartyId(partyId.value),
    partyStore.loadTransactionsByPartyId(partyId.value)
  ]);
}

onMounted(async () => {
  await partyStore.loadSpendingsByPartyId(partyId.value);
})
</script>

<template>
  <div class="card card-translated spendings-card">
    <h2>🧾 {{ t('headers.spendings') }}</h2>
    <button class="btn btn-main" @click="showCreateSpendingModal">{{ t('buttons.addSpending') }}</button>

    <div v-if="showSpendings" class="spendings-list-wrap">
      <h3 class="spendings-list-title">{{ t('spendingsCard.list') }}</h3>
      <ul class="spendings-list">
        <li class="spending-item" v-for="spending in spendings" :key="spending.id">
          <div class="spending-main">
            <div class="spending-top">
              <div class="spending-title">
                <span
                  class="pill"
                  :class="spending.splitType === SplitType.EQUAL ? 'pill--good' : 'pill--warn'"
                >
                  {{ spending.splitType === SplitType.EQUAL ? 'Поровну' : 'Пропорции' }}
                </span>
                <span class="spending-name">{{ spending.name }}</span>
              </div>
              <div class="spending-amount">{{ currencyFormat(spending.amount) }}</div>
            </div>

            <div class="spending-meta">
              <span class="meta-item">
                <span class="meta-label">Плательщик</span>
                <span class="meta-value">{{ spending.payer.name }}</span>
              </span>
              <span class="meta-dot">•</span>
              <span class="meta-item">
                <span class="meta-label">Участники</span>
                <span class="meta-value">{{ spending.splitType === SplitType.EQUAL ? 'Все' : 'Выбраны' }}</span>
              </span>
            </div>

            <div v-if="spending.splitType !== SplitType.EQUAL" class="proportions">
              <div class="proportions-title">Доли:</div>
              <div class="chips">
                <div class="chip" v-for="proportion in spending.proportions" :key="proportion.participant.id">
                  <span class="chip-name">{{ proportion.participant.name }}</span>
                  <span class="chip-amount">{{ currencyFormat(proportion.amount) }}</span>
                </div>
              </div>
            </div>
          </div>

          <button class="icon-btn" title="Удалить расход" @click="deleteSpendingById(spending.id)">❌</button>
        </li>
      </ul>
    </div>

    <div v-else class="empty-state">
      <div class="empty-state__title">Пока нет расходов</div>
      <div class="empty-state__desc">Добавьте первый расход — и мы посчитаем, кто кому должен.</div>
      <button class="btn btn-main" @click="showCreateSpendingModal">Добавить расход</button>
    </div>
  </div>

  <CreateSpendingModal
    :isOpened="isShowCreateSpendingModal"
    @close="hideCreateSpendingModal"
  ></CreateSpendingModal>
</template>

<style lang="scss" scoped>
@use "@/assets/scss/_colors.scss";

.spendings-list-wrap {
  margin-top: 0.8rem;
}

.spendings-list {
  list-style: none;
  padding: 0;
  margin: 0;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
}

.spending-item {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 10px;
  padding: 12px;
  border-radius: 16px;
  border: 1px solid rgba(15, 23, 42, 0.08);
  background: linear-gradient(180deg, rgba(255, 255, 255, 0.9), rgba(255, 255, 255, 0.6));
  transition: transform 120ms ease, box-shadow 120ms ease, border-color 120ms ease;
}

.spending-item:hover {
  transform: translateY(-1px);
  box-shadow: 0 10px 28px rgba(15, 23, 42, 0.08);
  border-color: rgba(99, 102, 241, 0.22);
}

.spending-top {
  display: grid;
  grid-template-columns: 1fr auto;
  gap: 10px;
  align-items: start;
}

.spending-title {
  display: flex;
  align-items: center;
  gap: 8px;
  flex-wrap: wrap;
  min-width: 0;
}

.spending-name {
  color: colors.$text-dark;
  word-break: break-word;
}

.spending-amount {
  color: colors.$text-dark;
  white-space: nowrap;
}

.pill {
  font-size: 12px;
  padding: 4px 10px;
  border-radius: 999px;
  border: 1px solid rgba(15, 23, 42, 0.10);
  background: rgba(248, 250, 252, 0.85);
  color: rgba(15, 23, 42, 0.72);
  font-weight: 700;
}

.pill--good {
  border-color: rgba(34, 197, 94, 0.25);
  background: rgba(34, 197, 94, 0.10);
  color: rgba(22, 163, 74, 0.95);
}

.pill--warn {
  border-color: rgba(245, 158, 11, 0.30);
  background: rgba(245, 158, 11, 0.10);
  color: rgba(180, 83, 9, 0.95);
}

.spending-meta {
  margin-top: 8px;
  display: flex;
  align-items: center;
  flex-wrap: wrap;
  gap: 8px;
  font-size: 13px;
  color: rgba(15, 23, 42, 0.7);
}

.meta-item {
  display: inline-flex;
  align-items: baseline;
  gap: 6px;
}

.meta-label {
  color: rgba(15, 23, 42, 0.55);
  font-size: 12px;
}

.meta-value {
  color: rgba(15, 23, 42, 0.86);
  font-weight: 700;
}

.meta-dot {
  color: rgba(15, 23, 42, 0.35);
}

.proportions {
  margin-top: 10px;
  padding-top: 10px;
  border-top: 1px dashed rgba(15, 23, 42, 0.14);
}

.proportions-title {
  font-size: 12px;
  color: rgba(15, 23, 42, 0.6);
  margin-bottom: 8px;
  font-weight: 700;
}

.chips {
  display: flex;
  flex-wrap: wrap;
  gap: 8px;
}

.chip {
  display: inline-flex;
  gap: 8px;
  align-items: center;
  padding: 6px 10px;
  border-radius: 999px;
  border: 1px solid rgba(15, 23, 42, 0.10);
  background: colors.$list-item-background;
}

.chip-name {
  font-weight: 600;
  color: rgba(15, 23, 42, 0.85);
}

.chip-amount {
  font-weight: 700;
  color: rgba(15, 23, 42, 0.8);
}

.icon-btn {
  width: 36px;
  height: 36px;
  cursor: pointer;
}

.empty-state {
  margin-top: 12px;
  padding: 16px;
  border-radius: 16px;
  border: 1px dashed rgba(15, 23, 42, 0.18);
  background: rgba(248, 250, 252, 0.7);
  display: flex;
  flex-direction: column;
  gap: 8px;
}

.empty-state__title {
  font-weight: 900;
  color: rgba(15, 23, 42, 0.9);
}

.empty-state__desc {
  color: rgba(15, 23, 42, 0.65);
  font-size: 13px;
}

@media (max-width: 720px) {
  .spending-top {
    grid-template-columns: 1fr;
  }
  .spending-amount {
    margin-top: 2px;
  }
}
</style>