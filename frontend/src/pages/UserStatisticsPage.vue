<script setup lang="ts">
import { Bar } from 'vue-chartjs'
import {
  Chart as ChartJS,
  Title,
  Tooltip,
  Legend,
  BarElement,
  CategoryScale,
  LinearScale,
  Colors,
  type ChartData, type ChartOptions
} from 'chart.js'
import {computed, onMounted, reactive, ref, watch} from "vue";
import {useMoneySplitterHttpClient} from "@/http/data/useMoneySplitterHttpClient.ts";
import {useUserDataHttpClient} from "@/http/user/useUserDataHttpClient.ts";
import {useAuthStore} from "@/stores/authStore.ts";
import {storeToRefs} from "pinia";
import type {Spending} from "@/models/Spending.ts";

ChartJS.register(Title, Tooltip, Legend, BarElement, CategoryScale, LinearScale, Colors)

const userDataHttpClient = useUserDataHttpClient();
const moneySplitterHttpClient = useMoneySplitterHttpClient();
const authStore = useAuthStore();
const { user } = storeToRefs(authStore);
const spendings = ref<Spending[]>();
const spendingsData = reactive(new Map<string, number>());

const chartData = computed<ChartData<'bar'>>(() => ({
  labels: [...spendingsData.keys()],
  datasets: [
    {
      label: 'Сумма всех расходов',
      data: [...spendingsData.values()],
    },
  ],
}));

const chartOptions = ref<ChartOptions<'bar'>>({
  responsive: true
});

onMounted(async () => {
  const partyIds = await userDataHttpClient.getPartyIdsByUserId(user.value!.id);
  spendings.value = await moneySplitterHttpClient.getAllSpendingsByPartyId(partyIds);
})

watch(spendings, (newSpendings) => {
  newSpendings?.forEach((spending) => {
    spendingsData.set(spending.payer.name, (spendingsData.get(spending.payer.name) || 0) + spending.amount);
  });
})
</script>

<template>
  <div class="container">
    <div class="card">
      <h2>Статистика участников по расходам</h2>
      <Bar
        id="my-chart-id"
        :options="chartOptions"
        :data="chartData"
      />
    </div>
  </div>
</template>

<style scoped>

</style>