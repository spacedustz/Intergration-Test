# Intergration-Test
😯

A repository for All Types of Implementation Tests

모든 유형의 구현 테스트를 위한 저장소

<br>

**이 저장소에서 사용할 기술 스택**
<details>
<summary>펼치기</summary>

<br>

**Languages**
- Backend : Java
- Frontend : TypeScript

<br>

**Backend**
- Spring Batch
- Spring Data JPA
- Maria DB & Redis
- QueryDSL
- MQTT, RTSP, HLS, FFmpeg
- EMQ X Broker (MQTT Broker)
- Akka Actor (Scala)
- Kakao Map API

<br>

**Frontend**
- **Vue.js** : Vue 3 Composition API, BootStrap, Vite, Vue Router, Vuex
- **React.js** : zustand(상태 관리), sytled-component(컴포넌트 스타일링), react-query
- **Next.js** : 
- **Libraries** : Axios, Chart.js, Vue-Chart-3, ESLint, hls.js, date-fns & @types/date-fns, styled-component, types@styled-component (추가 중)
</details>


---

## 1. 실시간 반응형 통계 데이터 시각화 (진행 중 -> 중단)
**중단이유** : Vue에서 React.js & Next.js로 변경


**📂Directory📂** -> Converter, Vue-TS

<br>

- Backend : Spring Boot 3.1.2
- Frontend : Vue 3


<br>

**요구사항**

- 딥러닝 엔진에서 특정 트리거가 발동되면 이벤트 데이터가 나온다. (Json, CSV, RTSP, 영상 데이터 등등)
- Spring Batch Job, Akka Actor 를 이용하여 주기적으로 MQTT를 이용해 백엔드로 주기적 전송/파싱 -> DB 저장
- Frontend로 넘길 Rest API 작성
- Frontend에서 Rest API로 데이터를 떙겨와 Time Graph에 데이터를 넘긴다.
- 감시자(Watcher)가 Rest API에서 데이터를 가져오는 함수를 감시하며 새로운 데이터가 Fetch 될 시 차트의 데이터 업데이트
- 데이터를 넘기면서 Reactive하게 실시간으로 차트의 x,y축이 변동되고 바로 적용되어야 함

<br>

### 상세 내용

**Backend**
- [아주 간단한 CSV 파싱 테스트](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/CSV.md)
- [RTSP -> HLS 변환 후 프론트엔드 Vue 서버에서 스트리밍 (아직 미완성)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/RTSP.md)
- [JSON Parser](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/Json.md)
- Spring Batch를 이용해 주기적으로 자동 파싱 & DB 저장 (준비 중)
- FrontEnd를 위한 RestAPI 작성 후 Reactive하게 Time Graph 변화시키기 (준비 중)

<br>

**Frontend**
- [Vite 기반 Vue 3 Composition + TypeScript 프로젝트 세팅](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Vue/Setup.md)
- [Chart.js: Scatter Chart 구현](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Vue/Chart.md)

---

## 2. React & TypeScript 학습 (진행 중)
**📂Directory📂** -> React-TS, TypeScript

**React Basic**
- [React 핵심 총 정리 Summary (진행 중)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/Summary.md)
- [1. Project Setting](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/1-Setup.md)
- [2. 함수형 컴포넌트 & Props](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/2-Props.md)
- [3. Ref & State & Event](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/3-Ref-State-Event.md)
- [4. Effect & Memo](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/4-Effect-Memo.md)
- [5. Callback](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/5-Callback.md)
- [6. Reducer](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/6-Reducer.md)
- [7. Custom Hooks](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/7-Custom-Hook.md)
- [8. Context API](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Basic/8-Context.md)

<br>

**Component Styling**
- [Styled-Component 사용법](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Component-Styling/Summary.md)

<br>

**상태 관리**
- [Zustand 사용법](https://github.com/spacedustz/Intergration-Test/blob/main/Description/React/Zustand/Summary.md)

<br>

**TypeScript**
- [TypeScript 환경 세팅](https://github.com/spacedustz/Intergration-Test/blob/main/Description/TypeScript/Setup.md)
- [TypeScript 총정리 Summary (업데이트 중)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/TypeScript/Summary.md)
- [Object 클래스 (모르는거 생길때마다 추가)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/TypeScript/Object.md)
- [Array에서 사용하는 함수들](https://github.com/spacedustz/Intergration-Test/blob/main/Description/TypeScript/Array.md)

---

## 3. 다른 프로젝트 (준비 중)
**📂Directory📂** -> None