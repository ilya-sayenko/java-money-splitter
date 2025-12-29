<script setup lang="ts">
import {useRoute} from "vue-router";
import {computed, onMounted} from "vue";
import {usePartyStore} from "@/stores/partyStore.ts";
import {storeToRefs} from "pinia";

const route = useRoute();
const partyId = computed(() => route.params.partyId as string);
const partyStore = usePartyStore();
const { spendings } = storeToRefs(partyStore);

onMounted(async () => {
  await partyStore.loadSpendingsByPartyId(partyId.value);
})
</script>

<template>
  <div class="card spendings-card">
    <h2>🧾 Расходы</h2>
    <div class="form-group">
      <label for="new-spending-name">За что платили?</label>
      <input type="text" id="new-spending-name" placeholder="Например, Аренда лыж" />
    </div>
    <div class="form-group">
      <label for="new-spending-amount">Сумма:</label>
      <input type="text" id="new-spending-amount" placeholder="5000" />
    </div>
    <div class="form-group">
      <label for="new-spending-amount">Кто платил?</label>
      <select name="participants" id="participants">
        <option value="UUID1">Вася</option>
        <option value="UUID1">Вася</option>
        <option value="UUID1">Вася</option>
        <option value="UUID1">Вася</option>
      </select>
    </div>

    <button class="btn btn-main">Добавить расход</button>

    <h3 class="spendings-list-title">Список расходов:</h3>
    <ul class="spendings-list">
      <li class="spending-item" v-for="spending in spendings">
        <div class="spending-item-info">
          <div class="spending-name">{{ spending.name }}</div>
          <div>Оплатил(а): {{ spending.payer.name }}</div>
          <div>Сумма: <span class="spending-amount">{{ spending.amount }}</span></div>
        </div>
        <div>
          <button class="btn-edit">✏️</button>
          <button>❌</button>
        </div>
      </li>

<!--      <li class="spending-item">-->
<!--        <div class="spending-item-info">-->
<!--          <div class="spending-name">Название траты</div>-->
<!--          <div>Оплатил(а): Маша</div>-->
<!--          <div>Сумма: <span class="spending-amount">Маша</span></div>-->
<!--        </div>-->
<!--        <div>-->
<!--          <button class="btn-edit">✏️</button>-->
<!--          <button>❌</button>-->
<!--        </div>-->
<!--      </li>-->

<!--      <li class="spending-item">-->
<!--        <span class="spending-name">Название траты</span>-->
<!--        <div>-->
<!--          <button class="btn-edit">✏️</button>-->
<!--          <button>❌</button>-->
<!--        </div>-->
<!--      </li>-->

<!--      <li class="spending-item">-->
<!--        <span class="spending-name">Название траты</span>-->
<!--        <div>-->
<!--          <button class="btn-edit">✏️</button>-->
<!--          <button>❌</button>-->
<!--        </div>-->
<!--      </li>-->

    </ul>
  </div>
</template>

<style scoped>

</style>