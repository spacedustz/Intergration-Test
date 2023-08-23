package com.converter.csv;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class FrameService {
    private final FrameRepository frameRepository;

    public List<FrameDTO.Response> getFrames() {
        return frameRepository.findAll().stream().map(FrameDTO.Response::fromEntity).toList();
    }
}
