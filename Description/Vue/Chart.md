# Components information

**차트 컴포넌트 목록**

- BarChart
- DoughnutChart
- LineChart
- PieChart
- PolarAreaChart
- RadarChart
- BubbleChart
- ScatterChart

<br>

**모든 컴포넌트 props**

|Prop|Type|Default|
|---|---|---|
|'chartData'|ChartJs.ChartData||
|'options'|ChartJs.ChartOptions||
|'plugins'|ChartJs.Plugin[]||
|'cssClasses'|string||
|'width'|number|400|
|'height'|number|400|
|'onChartRender'|(chartInstance: Chart) => void||
|'onChartUpdate'|(chartInstance: Chart) => void||
|'onChartDestroy'|() => void||
|'onLabelsUpdate'|() => void||

<br>

**참조로 접근할 수 있는 데이터**

|Prop|Type|
|---|---|
|'chartInstance'|Chart|
|'canvasRef'|HtmlCanvasElement|
|'renderChart'|() => void|

<br>

**모든 컴포넌트에서 발생시킬 수 있는 이벤트 Emits**

|Event|Payload|
|---|---|
|'chart:render'|chartInstance: Chart|
|'chart:update'|chartInstance: Chart|
|'chart:destroy'|-|
|'labels:update'|-|



---

## 정적인 차트 구현

**Vue-Chart-3 공식문서 보고 구현하기**

<br>

이 코드는 아직 정적인 코드이기 떄문에 감시자(Watcher)나 동적으로 그래프를 변하게 하지는 않습니다. (추가 예정)

위의 차트 목록에서 Doughnut, PolarArea, Bar 3개의 차트를 가져 와 봤습니다.

```ts
<template>  
  <div>  
    <h2 align="center">Doughnut Chart</h2>  
    <DoughnutChart ref="doughnutRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />  
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
import { DoughnutChart, BubbleChart, LineChart, RadarChart, PieChart, PolarAreaChart, BarChart, ScatterChart } from 'vue-chart-3';  
import {Chart, registerables} from "chart.js";  
import { shuffle } from 'lodash';  
  
Chart.register(...registerables);  
  
const data = ref<number[]>([30, 40, 60, 70, 5]);  
const doughnutRef = ref<InstanceType<typeof DoughnutChart> | null>(null);  
const polarAreaRef = ref<InstanceType<typeof PolarAreaChart> | null>(null);  
const barRef = ref<InstanceType<typeof BarChart> | null>(null);  
  
  
// Chart Data  
const testData = computed(() => ({  
  labels: ['Paris', 'Nîmes', 'Toulon', 'Perpignan', 'Autre'],  
  datasets: [  
    {  
      data: data.value,  
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
      text: 'Chart.js Doughnut Chart',  
    },  
  },  
});  
  
// Shuffle  
const shuffleData = () => {  
  data.value = shuffle(data.value);  
};  
  
// Render Events  
function handleChartRender(chart: any) {  
  console.log(chart);  
}  
  
// Life Cycle Hooks  
onMounted(() => {  
  console.log(doughnutRef.value?.chartInstance);  
  doughnutRef.value?.chartInstance.toBase64Image();  
})  
</script>
```

<br>

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/chart.png)

<br>

**템플릿 코드가 없는 이유**

코드를 보시면 템플릿 코드가 없고 `vue-chart-3`에 사전 정의된 컴포넌트를 확장해서 사용하는 방식이라 템플릿코드가 단순히 컴포넌트를 불러오는 방식입니다.

<br>

**Chart Data, Chart Option**

`vue-chart-3`에서 받아온 컴포넌트는 `차트 데이터`, `차트 옵션`을 바인딩할 수 있습니다.

그래서 options와 testData를 작성해서 차트 컴포넌트에 testData, options를 바인딩 해주었습니다.

- `차트 데이터` : 실제로 차트에 표현될 정보를 담은 객체
- `차트 옵션` : 데이터를 이용해 그려진 차트를 어떻게 보여줄 것인지에 대한 정보를 담은 객체

<br>

그리고 lodash의 shuffle()을 이용해 더미 데이터를 랜덤으로 돌려주는 버튼과 함수를 바인딩해서 버튼을 누르면 차트가 움직이게 됩니다.

<br>

Chart.js `chartInstance`및 를 `canvasRef`사용하여 구성 요소의 참조로 액세스할 수 있습니다.

`ref`. `chart-render`이벤트( 및 `chart-update`) 에도 전달됩니다.

---

## 시계열 데이터 Locale

---

## Rest API에서 동적으로 데이터 받아오고 차트 업데이트

Rest API를 이용해 백엔드에서 받아온 데이터에 감시자 (Watcher)를 붙여 그래프를 동적으로 변하게 만들어 보겠습니다.