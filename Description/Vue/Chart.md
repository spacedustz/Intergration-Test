## Vue - Chart.js를 이용한 반응형 데이터 시각화

[My Github Repository](https://github.com/spacedustz/Intergration-Test)

<br>

사내 프로젝트가 시작 되기 전 프론트엔드도 연습할 겸 간단하게 더미데이터를 만들어 테스트 해보려고 만든 저장소입니다.

프론트엔드를 다루는게 처음이라 혹시 틀린점이나 프론트엔드 고수형님 계시면 피드백 부탁드립니다..😭

<br>

**요구 사항**

- 솔루션에서 특정 트리거가 발동되면 이벤트 데이터가 나온다. (Json, CSV, RTSP, 영상 데이터 등등)
- Spring Batch Job, Akka Actor 를 이용하여 주기적으로 MQTT를 이용해 백엔드로 주기적 전송/파싱 -> DB 저장
- Frontend로 넘길 Rest API 작성
- Frontend에서 Rest API로 데이터를 떙겨와 Time Graph에 데이터를 넘긴다.
- 감시자(Watcher)가 Rest API에서 데이터를 가져오는 함수를 감시하며 새로운 데이터가 Fetch 될 시 차트의 데이터 업데이트
- 데이터를 넘기면서 Reactive하게 실시간으로 차트의 x,y축이 변동되고 바로 적용되어야 함

---

## Server Spec

**서버 구성**

- Backend : Spring Boot 3.1.2
- Frontend : Vue 3



**Languages**

- Backend : Java
- Frontend : TypeScript



**사용 기술 스택**

_Backend_

- Spring Batch
- Spring Data JPA
- Maria DB
- QueryDSL
- MQTT, RTSP, HLS, FFmpeg
- Akka Actor (Scala)
- Kakao Map API



_Frontend_

- Vue 3 Composition API, BootStrap, Vite
- Axios, Chart.js, Vue-Chart-3, ESLint, hls.js
- Vuex
- Vue Router
- date-fns & @types/date-fns

---

## 간단한 Backend Parsing Logic 구현

간단한 더미 데이터(CSV) 파싱을 위한 Parser를 작성하였습니다.

DTO, Service, Repository, Entity 들도 작성했으나 글에선 건너뜁니다.

```java
@Component
@RequiredArgsConstructor
public class Parser {
    private final FrameRepository frameRepository;
    private final Logger log = LoggerFactory.getLogger(Parser.class);

    /**
     * 변환, 리스트 저장 실패 시 트랜잭션 롤백  
     */
    @PostConstruct
    @Transactional
    public void initData() {
        // 임시로 로컬에서 CSV를 읽어옴  
        Resource resource = new ClassPathResource("sample/test.csv");

        try {
            List<String> lines = Files.readAllLines(Paths.get(resource.getFile().getPath()), StandardCharsets.UTF_8);
            List<Frame> list = new ArrayList<>();

            // CSV의 첫 행은 헤더이기 때문에 0번쨰 인덱스 스킵  
            for (int i=1; i<lines.size(); i++) {
                String[] split = lines.get(i).split(",");

                // CSV 파일의 값중 String이 아닌 값들의 타입 변환 준비  
                int count;
                float frameTime;
                long systemTimestamp;
                LocalDateTime systemDate;
                DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("EEE MMM dd HH:mm:ss yyyy", Locale.ENGLISH);
                String dateString = split[4];

                try {
                    // Count 변환  
                    count = Integer.parseInt(split[0]);

                    // Frame Time 변환  
                    Float frameValue = Float.parseFloat(split[2]);
                    frameTime = Float.parseFloat((String.format("%.4f", frameValue))); // 소수점 4자리 까지만  

                    // System TimeStamp 변환  
                    systemTimestamp = Long.parseLong(split[5]);

                    // System Date 날짜 변환  
                    systemDate = LocalDateTime.parse(dateString, dateFormat);
                } catch (Exception e) {
                    log.error("CSV 데이터 변환 실패");
                    throw new CommonException("DATA-003", HttpStatus.BAD_REQUEST);
                }

                // Entity 생성  
                Frame frame = Frame.createOf(
                        count,
                        frameTime,
                        split[3],
                        systemDate,
                        systemTimestamp
                );

                list.add(frame);
            }

            // 리스트에 Entity 추가  
            try {
                frameRepository.saveAll(list);
            } catch (Exception e) {
                log.error("Entity List 저장 실패");
                throw new CommonException("DATA-002", HttpStatus.BAD_REQUEST);
            }

        } catch (IOException e) {
            log.error("데이터 파싱 실패");
            throw new CommonException("DATA-001", HttpStatus.BAD_REQUEST);
        }
    }
}
```

---

## Vue Chart로 정적인 샘플 차트 구현

**Vue-Chart-3 공식문서 보고 구현하기**

<br>

이 코드는 아직 정적인 코드이기 떄문에 감시자(Watcher)나 동적으로 그래프를 변하게 하지는 않습니다. (추가 예정)

위의 차트 목록에서 Doughnut, PolarArea, Bar 3개의 차트를 가져 와 봤습니다.

**Template**

```html
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
```

<br>

**TimeGraph.vue**

```ts
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

## Scatter Chart 구현

**여기서 부터 차트를 Scatter Chart로 변경하고, 위의 샘플 차트를 모두 지우고 새로 만들겠습니다.**

날짜 관련 데이터를 출력하려면 라이브러리를 설치해야 합니다.

```
npm install moment chartjs-adapter-moment
```

<br>
그리고, main.ts 파일에 import 해주면 Chart.js가 자동으로 사용하게 됩니다.

```ts
import 'chartjs-adapter-moment';
```

<br>

아래는 구현한 차트 컴포넌트의 전체 코드입니다.

<br>

**Template**

v-if를 통해 차트가 렌더링 되기 전 데이터가 들어오지 않는다면 차트를 렌더링 하지 않게 설정합니다.

`onBeforeMount` Lifecycle Hook을 사용해도 되지만 간단하게 v-if를 사용하였습니다.

```html
<!-- Chart Instance 접근 방법 = scatterRef.value?.chartInstance.toBase64Image(); -->
<template>
    <div>
        <h2 align="center">Scatter Chart</h2>
        <ScatterChart v-if="frameData && frameData.length" ref="scatterRef" :chartData="testData" :options="options" @chart:render="handleChartRender" />
    </div>
</template>  
```

<br>

**TimeGraph.vue**

- 차트의 X축에는 데이터 중 `systemDate`, Y축은 `count` 필드로 설정할겁니다.
- ChartOption에 locale에 그래프상 X축과 Y축의 포맷, 출력 형식등을 지정해줍니다.
- 비동기 함수인 setData()를 `onMounted()`의 콜백함수로 넣고 데이터가 전부 로드될떄까지 기다릴 수 있도록, then()을 사용해서 데이터가 전부 로드 된 후 함수를 비동기로 실행하도록 해주었습니다.
- 그리고 감시자(Watcher)를 사용 해 Backend에서 들어오는 데이터가 업데이트 된다면 차트를 업데이트 하도록 포인팅 해주었습니다.

<br>

```ts
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
```

<br>

![img](https://raw.githubusercontent.com/spacedustz/Obsidian-Image-Server/main/img2/scatter.png)

<br>

> 😲 **만약 x축에 중복 데이터가 있는 경우 x축 그룹화, y축 합산**

ChartData의 데이터에서 백엔드에서 들어온 데이터를 돌면서 `systemDate`만 `moment` 라이브러리를 써서 변환해주었습니다.

```ts
data: frameData.value.map(frame => ({ x : moment(frame.systemDate, 'EEE MMM dd HH:mm:ss yyyy').minutes(), y : frame.count }))
```

<br>

하지만 위의 코드로 ChartData를 작성할 시, 동일한 "분"에 여러 데이터 포인트가 있다면 중복 문제가 생길 수 있습니다.

동일한 "분"의 여러 데이터 포인트를 합산하려면aus '분' 값을 직접 사용해야 합니다.

제 코드에서는 중복값과 Count의 값을 합산하지는 않을 것이지만 그룹화 하는 코드만 적어보겠습니다.

```ts
const groupedByMinutes = computed(() =>
    groupBy(frameData.value, (data) => moment(data.systemDate, 'EEE MMM dd HH:mm:ss yyyy').minutes())
);

const aggregatedData = computed(() => {
    return Object.entries(groupedByMinutes.value).map(([minute, dataGroup]) => {
        return {
            x: parseInt(minute),
            y: sumBy(dataGroup, 'count')
        };
    });
});

// Chart Data  
const testData = computed(() => ({
    labels: ['Paris', 'Nîmes', 'Toulon', 'Perpignan', 'Autre'],
    datasets: [
        {
            data: aggregatedData.value,
            backgroundColor: ['#77CEFF', '#0079AF', '#123E6B', '#97B0C4', '#A5C8ED'],
        },
    ],
}));
```

<br>

내용 추가 중..

---

## Spring Batch를 이용한 주기적인 이벤트 데이터 파싱

(준비중)

---

## Akka Actor

툴킷으로 JVM상의 동시성과 분산 어플리케이션을 단순화 하는 용도

<br>

**Actor Model**

Actor model은 아래와 같은 개념을 가진다.

- 첫째, 다른 액터에 한정된 개수의 메시지를 보낼 수 있다. (Send a finite number of messages to other actors.)
- 둘째, 유한한 개수의 액터를 만들어낼 수 있다. (Create a finite number of new actors.)
- 셋째, 다른 액터가 받을 메시지에 수반될 행동을 지정할 수 있다. (Designate the behavior to be used for the next message it receives.)
- 마지막으로, 이러한 모든 일이 동시적으로 일어난다.

Actor는 서로간에 공유하는 자원이 없고 서로간의 상태를 건드릴 수도 없다. 오직 message만을 이용해서 정보를 전달할 수 있다.

Actor model은 1973년 칼 휴이트가 제안한 수학적 모델을 기초로 삼고 있다. 이러한 오래된 개념이 다시 관심을 받게 된 이유는 multi processing에 적합한 개념이기 때문이다. multi-core 환경을 효율적으로 활용하기 위해 여러 개의 thread를 사용하여 구현하는 것이 중요해졌다. 하지만 thread 간에 가지는 공유된 자원으로 인해 race condition, deadlock, blocking call 등의 문제가 발생하기 쉽다. 이를 회피하기 위한 방법의 하나로 actor model을 구현한 Akka가 등장했다.

<br>
**Akka의 특징**

1. 처리율(throughput): 암달의 법칙에서 설명하는 순차적 부분이 차지하는 면적을 최소한으로 줄이면서 프로그램의 전체적인 처리율은 그와 반비례해서 급등한다.
2. Scale Out: Scale out을 구성파일의 내용을 약간 수정함으로써 자동으로 보장해준다.
3. 모듈화(modularity): Akka를 이용하면 클래스나 객체를 중심으로 설계를 하던 방식이 "Actor"를 중심으로 바뀐다. Actor는 서로 완벽하게 독립적이며, 메세지를 주고 받는 방식으로만 통신하므로 코드의 응집성(coherence), loosely coupled, 캡슐화(encapsulation)가 보장된다.