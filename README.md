# Intergration-Test
A repository for All Types of Implementation Tests

모든 유형의 구현 테스트를 위한 저장소

---

## 1. 실시간 반응형 통계 데이터 시각화

<details>
<summary>서버 구성</summary>

**프로젝트 구성**
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

## Backend Parser

각종 데이터의 변환 과정을 기록한 저장소

- Spring Boot 3.1.2
- Spring Data JPA
- Spring Batch
- Spring RestDocs
- MariaDB
- H2

---

## CSV
- [아주 간단한 CSV 파싱 테스트](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/CSV.md)
- Spring Batch를 이용해 주기적으로 자동 파싱 & DB 저장 (준비 중)
- FrontEnd를 위한 RestAPI 작성 후 Reactive하게 Time Graph 변화시키기 (준비 중)

---

## JSON
- JSON (준비 중)

---

## RTSP/RTMP -> HLS
- [RTSP -> HLS 변환 후 프론트엔드 Vue 서버에서 스트리밍 (아직 미완성)](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Converter/RTSP.md)
</details>

<br>

<details>
<summary>Frontend Details</summary>

- [Chart.js를 이용한 실시간 반응형 시각화](https://github.com/spacedustz/Intergration-Test/blob/main/Description/Vue/Vue.md)
</details>

---

## 다른 프로젝트 준비 중