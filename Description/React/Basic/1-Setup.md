## React

React 핵심만 정리합니다.

[My Github Repository](https://github.com/spacedustz/Intergration-Test/)

<br>

**Solid Foundation**

- Components & JSX
- Props
- State & Events
- Outputting Content
- Styling
- Hooks
- Debugging

<br>

**Advanced Concepts**

- Refs & Portals
- Behind the Scenes
- HTTP Requests
- Side Effects
- Context API
- Authentication
- Advanced Hooks
- Redux
- Unit Testing
- Custom Hook
- Routing
- Next.js

---

## Project 생성 & 세팅

Browser-Based Setup(CodeSandbox&Similar) 방식은 제외하고 Local Setup 방식으로 프로젝트를 만들어 보겠습니다.

NodeJS를 설치하고 아래 두 방식 중 하나를 선택해 React App을 만듭니다.

<br>

**Create-React-App 방식**

``` bash
# 프로젝트 생성
npm i -g typescript
npx create-react-app [프로젝트 이름]

# 프로젝트 실행
npm start
```

<br>

**Vite 방식**

```bash
# 프로젝트 생성
npm i -g vite typescript
npm create vite@latest

# 프로젝트 실행
npm run dev
```

<br>

**타입스크립트 컴파일러 실행**

```bash
npx tsc [파일명].ts
```

<br>

**vite 기반 리액트 포트 변경**

- `package.json` 파일에서 `"dev": "vite"` 부분을 `"dev": "vite --port [원하는 포트]"` 로 변경

<br>

**tsconfig.json**

Linting 부분에`"allowSyntheticDefaultImports": true`를  사용하면, 기본적으로 default export가 없는 모듈에서도 default import를 허용합니다.

<br>

**추가된 패키지 (필요한 패키지가 생길 때마다 추가 중)**

- axios
- @types/node @types/react @types/react-dom @types/jest
- eslint
- chart.js & react-chartjs-2
- lodash @types/lodash
- chartjs-adapter-moment
- @reduxjs/toolkit react-redux @types/react-redux