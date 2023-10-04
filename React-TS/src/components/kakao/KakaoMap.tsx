import React, {useEffect} from "react";
import styled from "styled-components";

declare global {
    interface Window {
        kakao: any;
    }
}

// Kakao Map 크기
const Map = styled.div`
width: 500px;
height: 300px;
`;

const KakaoMap: React.FC = () => {
    useEffect(() => {
        const container = document.getElementById('map');
        const options = {
            center: new window.kakao.maps.LatLng(33.450701, 126.50667),
            level: 5
        };

        const map = new window.kakao.maps.Map(container, options);

        return () => {};
    }, []);

    return (
        <Map id="map" />
    )
}

export default KakaoMap;