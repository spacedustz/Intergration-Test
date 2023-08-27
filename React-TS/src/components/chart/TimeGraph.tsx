// import React, { useEffect, useState } from 'react';
// import { Line } from 'react-chartjs-2';
// import axios from 'axios';
// import * as _ from 'lodash';
// import { Chart, ChartData, ChartOptions, registerables } from 'chart.js';
//
// interface FrameData {
//     count: number;
//     frameId: number;
//     frameTime: number;
//     instanceId: string;
//     systemDate: number[];
//     systemTimestamp: number;
// }
//
// Chart.register(...registerables);
//
// const fetchFrame = async (): Promise<FrameData[]> => {
//     try {
//         const response = await axios.get<FrameData[]>('http://localhost:8080/csv');
//         return response.data;
//     } catch (error) {
//         console.error('프레임 데이터 가져오기 오류:', error);
//         return [];
//     }
// };
//
// const getMinutesFromSystemDate = (systemDate: number[]): string => {
//     const [, , , , minute, second] = systemDate;
//     return `${String(minute).padStart(2, '0')}:${String(second).padStart(2, '0')}`;
// };
//
// export const ChartComponent = () => {
//     const [frameData, setFrameData] = useState<FrameData[]>([]);
//
//     useEffect(() => {
//         setData();
//     }, []);
//
//     console.log('데이터 가져오기 성공', frameData);
//
//     const setData = async () => {
//         try {
//             const data = await fetchFrame();
//             setFrameData(data);
//         } catch (error) {
//             console.error('데이터 설정 오류:', error);
//         }
//     };
//
//     if (!frameData.length) return null;
//
//     const groupedByKey = _.groupBy(frameData, frame => getMinutesFromSystemDate(frame.systemDate));
//
//     const lineChartData: ChartData<'line'> = {
//         datasets: [
//             {
//                 label: '보안 이벤트',
//                 data: Object.keys(groupedByKey).map(timeKey => {
//                     const framesForThisTime = groupedByKey[timeKey];
//                     const maxCountForThisTime = Math.max(...framesForThisTime.map(frame => frame.count));
//                     const firstFrameId = framesForThisTime[0].frameId;
//                     const lastFrameId = framesForThisTime[framesForThisTime.length - 1].frameId;
//
//                     return {
//                         x: timeKey,
//                         y: maxCountForThisTime,
//                         frameId: `${firstFrameId}-${lastFrameId}`,
//                     };
//                 }),
//                 backgroundColor: ['lightblue'],
//                 pointRadius: 1.5,
//                 showLine: true,
//                 borderColor: 'gray',
//                 borderWidth: 1,
//             },
//         ],
//     };
//
//     const maxCountValues = lineChartData.datasets[0].data.map(dataPoint => dataPoint.y);
//
//     const lineChartOptions: ChartOptions<'line'> = {
//         responsive: true,
//         maintainAspectRatio: false,
//         aspectRatio: 1,
//         plugins: {
//             legend: { position: 'top' },
//             title: { display: true, text: 'Cvedia 이벤트' },
//             tooltip: {
//                 callbacks: {
//                     title: () => '프레임 ID',
//                     label: (context: any) => {
//                         const dataIndex: number = context.dataIndex;
//                         const datasetIndex: number = context.datasetIndex;
//
//                         if (typeof dataIndex !== 'undefined' && typeof datasetIndex !== 'undefined') {
//                             const frameIdRange = lineChartData.datasets[datasetIndex].data[dataIndex].frameId;
//                             if (frameIdRange) {
//                                 return `프레임 ID: ${frameIdRange}`;
//                             }
//                         }
//                         return '';
//                     },
//                 },
//             },
//         },
//         scales: {
//             x: {
//                 type: 'time',
//                 title: { display: true, text: '시간 (분:초)' },
//                 time: {
//                     unit: 'minute',
//                     displayFormats: { minute: 'mm:ss' },
//                     parser: 'mm:ss',
//                 },
//                 ticks: { source: 'auto' },
//             },
//             y: {
//                 title: { display: true, text: '개수' },
//                 min: Math.min(...maxCountValues),
//                 max: Math.max(...maxCountValues),
//                 ticks: { stepSize: 1, beginAtZero: true },
//             },
//         },
//     };
//
//     return (
//         <div>
//             <h2 align="center">라인 차트</h2>
//             <div style={{ overflow: 'auto', maxWidth: '1000px', maxHeight: '800px' }}>
//                 <Line data={lineChartData} options={lineChartOptions} />
//             </div>
//         </div>
//     );
// };