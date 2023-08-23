# Intergration-Test
A repository for All Types of Implementation Tests

모든 유형의 구현 테스트를 위한 저장소

---

## 1. 실시간 반응형 통계 데이터 시각화 (진행 중)

- 솔루션에서 특정 트리거가 발동되면 이벤트 데이터가 나온다. (Json, CSV, RTSP, 영상 데이터 등등)
- Spring Batch Job, Akka Actor 를 이용하여 주기적으로 MQTT를 이용해 백엔드로 주기적 전송/파싱 -> DB 저장
- Frontend로 넘길 Rest API 작성
- Frontend에서 Rest API로 데이터를 떙겨와 Time Graph에 데이터를 넘긴다.
- 감시자(Watcher)가 Rest API에서 데이터를 가져오는 함수를 감시하며 새로운 데이터가 Fetch 될 시 차트를 업데이터 한다
- 데이터를 넘기면서 Reactive하게 실시간으로 차트의 x,y축이 변동되고 바로 적용되어야 함

<br>

<details>
<summary>서버 구성</summary>

- Backend : Spring Boot 3.1.2
- Frontend : Vue 3

<br>

**Languages**
- Backend : Java
- Frontend : TypeScript

<br>

**사용 기술 스택**

_Backend_
- Spring Batch
- Spring Data JPA
- Maria DB
- QueryDSL
- MQTT, RTSP, HLS, FFmpeg
- Akka Actor (Scala)
- Kakao Map API

<br>

_Frontend_
- Vue 3 Composition API, BootStrap, Vite
- Axios, Chart.js, Vue-Chart-3, ESLint, hls.js
- Vuex
- Vue Router
- date-fns & @types/date-fns
</details>

<br>

### 상세 내용

<details>
<summary>Backend Details</summary>

- Spring Boot 3.1.2
- Spring Data JPA
- Spring Batch
- Spring RestDocs
- MariaDB
- H2

<br>

## CSV
- [아주 간단한 CSV 파싱 테스트](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/CSV.md)
- Spring Batch를 이용해 주기적으로 자동 파싱 & DB 저장 (준비 중)
- FrontEnd를 위한 RestAPI 작성 후 Reactive하게 Time Graph 변화시키기 (준비 중)

<br>

## JSON
- JSON (준비 중)

<br>

## RTSP/RTMP -> HLS
- [RTSP -> HLS 변환 후 프론트엔드 Vue 서버에서 스트리밍 (아직 미완성)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/RTSP.md)
</details>

---

<details>
<summary>Frontend Details</summary>

- [Vite 기반 Vue 3 Composition + TypeScript 프로젝트 세팅](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Vue/Setup.md)
- [Chart.js를 이용한 정적인 차트 구현](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Vue/Chart.md)
</details>

---

## 2. 다른 프로젝트 (1번 다 끝나면 추가 예정)