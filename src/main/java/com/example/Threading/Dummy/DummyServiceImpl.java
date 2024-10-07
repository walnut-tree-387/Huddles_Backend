package com.example.Threading.Dummy;

import com.example.Threading.Dummy.Dto.DummyCreateDto;
import com.example.Threading.Dummy.Dto.DummyGetDto;
import com.example.Threading.Dummy.Dto.DummyUpdateDto;
import com.example.Threading.Helpers.SystemMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class DummyServiceImpl implements DummyService {
    private final DummyRepository dummyRepository;
    private static final Logger logger = LoggerFactory.getLogger(DummyServiceImpl.class);
    public DummyServiceImpl(DummyRepository dummyRepository) {
        this.dummyRepository = dummyRepository;
    }

    @Override
    public Dummy getById(UUID uuid) {
        Optional<Dummy> dummy = dummyRepository.findById(uuid);
        if(dummy.isEmpty()){
            throw new RuntimeException("Dummy not found with uuid " + uuid);
        }
        return dummy.get();
    }
    @Override
    public void save(DummyCreateDto dummyCreateDto) {
        Dummy dummy = SystemMapper.toEntity(dummyCreateDto, Dummy.class);
        dummy.setVersion(dummy.getVersion() + 1);
        dummyRepository.save(dummy);
    }

    @Override
    public List<DummyGetDto> getAll() {
        return SystemMapper.toDtoList(dummyRepository.findAll(), DummyGetDto.class);
    }

    @Override
    public DummyGetDto update(UUID uuid, DummyUpdateDto updateDto) {
        Dummy dummy = getById(uuid);
        dummy.setName(updateDto.getName());
        dummy.setVersion(dummy.getVersion() + 1);
        dummy = dummyRepository.save(dummy);
        return SystemMapper.toDto(dummy, DummyGetDto.class);
    }

    @Override
    public void delete(UUID uuid) {
        Dummy dummy = getById(uuid);
        dummyRepository.delete(dummy);
    }
}
