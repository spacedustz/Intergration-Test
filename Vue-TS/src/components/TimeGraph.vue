<!-- Chart Instance 접근 방법 = scatterRef.value?.chartInstance.toBase64Image(); -->

<template>
  <div>
    <h2 align="center">Scatter Chart</h2>
    <ScatterChart v-if="frameData && frameData.length" ref="scatterRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />
    <button @click="shuffleData">Shuffle</button>
  </div>
</template>

<script lang="ts" setup>
import {ref, computed, onMounted, watch } from 'vue';
import { ScatterChart } from 'vue-chart-3';
import { Chart, registerables } from "chart.js";
import { shuffle, groupBy, sumBy } from 'lodash';
import { fetchFrame } from "@/stores/api";
import moment from "moment";

Chart.register(...registerables);

interface FrameData {
  count: number;
  frameId: number;
  frameTime: number;
  instanceId: string;
  systemDate: string;
  systemTimestamp: number;
}

const data = ref<number[]>([30, 40, 60, 70, 5]);
const scatterRef = ref<InstanceType<typeof ScatterChart> | null>(null);
const frameData = ref<FrameData[]>([]);

// Chart Data
const testData = computed(() => ({
  labels: ['Paris', 'Nîmes', 'Toulon', 'Perpignan', 'Autre'],
  datasets: [
    {
      data: frameData.value.map(frame => ({ x : moment(frame.systemDate, 'EEE MMM dd HH:mm:ss yyyy').format('MM/DD/YYYY HH:mm'), y : frame.count })),
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

  // Time Scales
  scales: {
    // x축 System Date 시간 포맷 설정
    x: {
      type: 'linear',
      min: 0,
      max: 59, // 분의 최대 값
      title: {
        display: true,
        text: 'Minutes'
      }
    },
    // y축 Count 포맷 설정
    y: {
      title: {
        display: true,
        text: 'Count'
      },
      ticks: {
        stepSize: 1,
        beginAtZero: true
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
  setData().then(() => {
    scatterRef.value?.chartInstance.update();
  });
});

// Watcher
watch(frameData, (newData) => {
  if (newData.length > 0) {
    scatterRef.value?.update();
  }
});
</script>