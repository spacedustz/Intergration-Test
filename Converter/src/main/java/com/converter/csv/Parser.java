package com.converter.csv;

import com.converter.error.CommonException;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

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
                log.info("========== 데이터 저장 성공 ==========");
            } catch (Exception e) {
                log.error("Entity List 저장 실패");
                throw new CommonException("DATA-002", HttpStatus.BAD_REQUEST);
            }

        } catch (IOException e) {
            log.error("===== 데이터 파싱 실패 =====");
            throw new CommonException("DATA-001", HttpStatus.BAD_REQUEST);
        }
    }
}
