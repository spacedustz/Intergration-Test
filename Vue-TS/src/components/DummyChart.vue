<template>
  <div>
    <h2 align="center">Scatter Chart</h2>
    <ScatterChart ref="scatterRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />
    <button @click="shuffleData">Shuffle</button>
  </div>

  <div>
    <h2 align="center">PolarArea Chart</h2>
    <PolarAreaChart ref="polarAreaRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />
    <button @click="shuffleData">Shuffle</button>
  </div>

  <div>
    <h2 align="center">Line Chart</h2>
    <BarChart ref="barRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />
    <button @click="shuffleData">Shuffle</button>
  </div>
</template>

<script lang="ts" setup>
import {ref, computed, onMounted} from 'vue';
import { PolarAreaChart, BarChart, ScatterChart } from 'vue-chart-3';
import {Chart, registerables} from "chart.js";
import { shuffle } from 'lodash';
import { fetchFrame } from "@/stores/api";

Chart.register(...registerables);

const data = ref<number[]>([30, 40, 60, 70, 5]);
const scatterRef = ref<InstanceType<typeof ScatterChart> | null>(null);
const polarAreaRef = ref<InstanceType<typeof PolarAreaChart> | null>(null);
const barRef = ref<InstanceType<typeof BarChart> | null>(null);

interface FrameData {
  count: number;
  frameId: number;
  frameTime: number;
  instanceId: string;
  systemDate: string;
  systemTimestamp: number;
}

const frameData = ref<FrameData[]>([]);


// Chart Data
const testData = computed(() => ({
  labels: ['Paris', 'Nîmes', 'Toulon', 'Perpignan', 'Autre'],
  datasets: [
    {
      data: frameData.value.map(frame => ({ x : new Date(frame.systemDate), y : frame.count} )),
      backgroundColor: ['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED'],
    },
  ],
}));

// Chart Options
const options = ref({
  responsive: true,
  plugins: {
    legend: {
      position: 'top',
    },

    title: {
      display: true,
      text: 'Cvedia Events',
    },
  },

  scales: {
    x: {
      type: 'time',
      title: {
        display: true,
        text: 'System Date'
      }
    },
    y: {
      title: {
        display: true,
        text: 'Count'
      }
    }
  }
});

// Shuffle
const shuffleData = () => {
  data.value = shuffle(data.value);
};

// Rest API에서 데이터 받아오기
const setData = async () => {
  try {
    frameData.value = await fetchFrame();
  } catch (error) {
    console.error('데이터를 가져오는 중 오류 발생:', error);
  }
};

// Render Events
function handleChartRender(chart: any) {
  console.log(chart);
}

// Life Cycle Hooks
onMounted(() => {
  console.log(scatterRef.value?.chartInstance);
  scatterRef.value?.chartInstance.toBase64Image();
  setData();
})
</script>