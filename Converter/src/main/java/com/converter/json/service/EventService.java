package com.converter.json.service;

import com.converter.json.dto.EventDTO;
import com.converter.json.entity.Bbox;
import com.converter.json.entity.Event;
import com.converter.json.entity.Extra;
import com.converter.json.entity.Vertice;
import com.converter.json.repository.BboxRepository;
import com.converter.json.repository.EventRepository;
import com.converter.json.repository.ExtraRepository;
import com.converter.json.repository.VerticeRepository;
import com.converter.rtsp.RtspConverter;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
@RequiredArgsConstructor
public class EventService {

    private final EventRepository eventRepository;
    private final ExtraRepository extraRepository;
    private final BboxRepository bboxRepository;
    private final VerticeRepository verticeRepository;
    private final RtspConverter converter;

    public List<EventDTO.Response> parseJson() throws Exception {
        List<Event> eventList = new ArrayList<>();
        List<JsonNode> rootNode = new ArrayList<>();
        ObjectMapper objectMapper = new ObjectMapper();

        // Json 파일 받아서 객체 배열로 분리
//        String jsonContent = new String(Files.readAllBytes(Paths.get("C:\\Users\\root\\Desktop\\Tools\\Cvedia\\files\\output\\eventsExport.json")));
        ClassPathResource resource = new ClassPathResource("video/test.json");

        String jsonContent = Files.readString(Paths.get(resource.getURI()));
        String[] splitJson = jsonContent.split("\n");

        for (String json : splitJson) {
            JsonNode jsonObject = objectMapper.readTree(json);
            rootNode.add(jsonObject);
        }

        // 분리된 Json 객체마다 반복
        for (JsonNode node : rootNode) {

            // Event의 FrameId 삽입
            Event event = new Event();
            event.setFrameId(node.get("frame_id").asDouble());

            JsonNode eventsArray = node.get("events");

            if (eventsArray != null && eventsArray.isArray()) {
                for (JsonNode eventNode : eventsArray) {
                    Extra extra = new Extra();
                    JsonNode extraNode = eventNode.get("extra");

                    // Bbox 매핑
                    JsonNode bbox = extraNode.get("bbox");
                    if (bbox != null) {
                        Bbox bboxEntity = Bbox.createOf(
                                bbox.get("height").asDouble(),
                                bbox.get("width").asDouble(),
                                bbox.get("x").asDouble(),
                                bbox.get("y").asDouble()
                        );
                        bboxRepository.save(bboxEntity);
                        extra.setBbox(bboxEntity);
                    }

                    // Vertices 매핑
                    JsonNode vertices = extraNode.get("tripwire").get("vertices");
                    List<Vertice> verticeList = new ArrayList<>();

                    if (vertices != null && vertices.isArray() && vertices.size() > 0) {

                        for (JsonNode vertice : vertices) {
                            if (vertice != null) {
                                Vertice verticeEntity = Vertice.createOf(
                                        vertice.get("x").asDouble(),
                                        vertice.get("y").asDouble()
                                );
                                verticeRepository.save(verticeEntity);
                                verticeList.add(verticeEntity);
                            }
                        }
                        extra.setVertices(verticeList);
                    }
                    extraRepository.save(extra);
                    event.setExtra(extra);
                }
            }
            eventRepository.save(event);
            eventList.add(event);
        }
        System.out.println("Event List : " + eventList);

        return eventList.stream().map(EventDTO.Response::fromEntity).collect(Collectors.toList());
    }

    public String convertRtsp() throws IOException {
        return converter.convertRtspToHls();
    }

    public String convertRtsp2() {
        return String.valueOf(converter.convertOnLocal());
    }
}

