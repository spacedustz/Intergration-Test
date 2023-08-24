<!-- Chart Instance 접근 방법 = scatterRef.value?.chartInstance.toBase64Image(); -->
<template>
  <div>
    <h2 align="center">Scatter Chart</h2>
    <div style="overflow: auto; max-width: 1000px; max-height: 800px;">
      <ScatterChart
          v-if="frameData && frameData.length"
          ref="scatterRef" :chartData="chartData"
          :options="chartOptions"
          @chart:render="handleChartRender" />
    </div>
  </div>
</template>

<script lang="ts" setup>
import { ref, computed, onMounted, watch } from 'vue';
import { ScatterChart } from 'vue-chart-3';
import { Chart, registerables } from "chart.js";
import { fetchFrame } from "@/stores/api";
import { groupBy } from 'lodash';

Chart.register(...registerables);

/* ===== Reactive 변수 ===== */
interface FrameData {count: number;frameId: number;frameTime: number;instanceId: string;systemDate: number[];systemTimestamp: number;}
const scatterRef = ref<InstanceType<typeof ScatterChart> | null>(null);
const frameData = ref<FrameData[]>([]);

/* ===== Life Cycle Hooks ===== */
onMounted(() => {
  setData();
});

/* ===== Render Events ===== */
function handleChartRender(chart: any) {
  console.log(chart);
}

/* ===== Rest API에서 데이터 받아오기 ===== */
const setData = async () => {
  console.log("===== Data Fetch 완료 =====")

  try {
    frameData.value = await fetchFrame();
    console.log("Original frameData length:", frameData.value.length);
    console.log("데이터 원본 검증: ", frameData.value);
    console.log("시간 데이터 배열 확인: ", frameData.value.map(frame => getMinutesFromSystemDate(frame.systemDate)));
    console.log("최소 카운트: ", minCount.value);
    console.log("최대 카운트: ", maxCount.value);
  } catch (error) {
    console.error('데이터를 가져오는 중 오류 발생:', error);
  }
};

/* ===== Fetch된 데이터를 동일한 값의 systemDate를 기준으로 Grouping ===== */
const groupedByKey = computed(() => groupBy(frameData.value, frame => frame.systemDate));
console.log('길이 ㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇㅇ', groupedByKey.value.length)

/* ===== 각각의 그룹화된 그룹에서 최대 count 값을 반환하는 배열을 생성 ===== */
const maxCounts = computed(() => {
  return Object.values(groupedByKey.value).map(groupedFrames => {
    return groupedFrames.reduce((max, currentFrame) => {
      return currentFrame.count > max.count ? currentFrame : max;
    }).count;
  });
});

/* ===== Computed ===== */
const maxCount = computed(() => Math.max(...maxCounts.value));
const minCount = computed(() => Math.min(...maxCounts.value));

/* ===== 시간과 분을 문자열 형태로 반환 ===== */
const getMinutesFromSystemDate = (systemDate: number[]): string => {
  const [, , , , minute, second] = systemDate;
  return `${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`;
};

/* ===== Chart Data =====*/
const chartData = computed(() => ({
  datasets: [
    {
      label: "Security Event",
      data: frameData.value.map((frame, index) => ({
        x: getMinutesFromSystemDate(frame.systemDate),
        y: maxCounts.value[index], // 이 부분은 maxCounts에서 y 값을 가져옴
        frameId: frame.frameId
      })),
      backgroundColor: ['lightblue', 'green', 'red', 'yellow', 'black'],
    },
  ],
}));

/* ===== Chart Options ===== */
const chartOptions = ref({
  responsive: true,
  maintainAspectRatio: false, // 차트의 비율을 고정하지 않음
  aspectRatio: 1, // 비율을 1:1로 설정
  plugins: {
    tooltips: {
      callbacks: {
        title: function() {
          return 'Frame ID';
        },
        label: function(tooltipItem, data) {
          const dataIndex = tooltipItem.index;
          if (typeof dataIndex !== 'undefined') {
            const frameId = data.datasets[tooltipItem.datasetIndex].data[dataIndex].frameId;
            return `Frame ID: ${frameId}`;
          }
          return '';
        }
      }
    },
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
      type: 'time',
      title: {
        display: true,
        text: 'Time (Minute:Seceond)'
      },
      time: {
        unit: 'minute',
        displayFormats: {
          minute: 'mm:ss'
        },
        parser: 'mm:ss'
      },
      ticks: {
        source: 'auto'
      }
    },
    // y축 Count 포맷 설정
    y: {
      title: {
        display: true,
        text: 'Count'
      },
      min: minCount,
      max: maxCount,
      ticks: {
        stepSize: 1,
        beginAtZero: true
      }
    }
  }
});

/* ===== Watcher ===== */
watch(frameData, (newData) => {
  console.log('frameData 변경 감지: ', newData);
  if (newData.length > 0) {
    scatterRef.value?.update();
  }
});
</script>