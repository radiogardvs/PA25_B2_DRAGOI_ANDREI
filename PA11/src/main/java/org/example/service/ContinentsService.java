package org.example.service;
import java.util.logging.Logger;
import org.example.dao.Continents;
import org.example.dto.ContinentsDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.example.repository.ContinentsRepository;
import org.example.mapper.ContinentsMapper;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class ContinentsService {
    private static final Logger logger = Logger.getLogger(ContinentsService.class.getName());

    private final ContinentsRepository ContinentsRepository;

    @Autowired
    public ContinentsService(ContinentsRepository ContinentsRepository) {
        this.ContinentsRepository = ContinentsRepository;
    }

    public List<ContinentsDTO> findAll() {
        return ContinentsRepository.findAll()
                .stream()
                .map(ContinentsMapper::toDTO)
                .collect(Collectors.toList());
    }

    public ContinentsDTO findById(Long id) {
        Continents entity = ContinentsRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Continent with id " + id + " not found";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        return ContinentsMapper.toDTO(entity);
    }
    @Transactional
    public ContinentsDTO create(ContinentsDTO dto) {
        Continents entity = ContinentsMapper.toEntity(dto);
        entity.setName(dto.getName());
        return ContinentsMapper.toDTO(ContinentsRepository.save(entity));
    }
    @Transactional
    public ContinentsDTO update(Long id, ContinentsDTO dto) {
        Continents existing = ContinentsRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Continent with id " + id + " not found for update";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        existing.setName(dto.getName());
        return ContinentsMapper.toDTO(ContinentsRepository.save(existing));
    }
    @Transactional
    public ContinentsDTO updatePartial(Long id, Map<String, Object> updates) {
        Continents existing = ContinentsRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Continents with id " + id + " not found"));

        updates.forEach((key, value) -> {
            switch (key) {
                case "name" -> existing.setName(value.toString());
            }
        });

        return ContinentsMapper.toDTO(ContinentsRepository.save(existing));
    }
    @Transactional
    public void delete(Long id) {
        Continents existing = ContinentsRepository.findById(id)
                .orElseThrow(() -> {
                    String errorMsg = "Continent with id " + id + " not found for deletion";
                    logger.severe(errorMsg);
                    return new RuntimeException(errorMsg);
                });
        ContinentsRepository.delete(existing);
    }
}
