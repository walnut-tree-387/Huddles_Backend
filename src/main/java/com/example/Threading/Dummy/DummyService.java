package com.example.Threading.Dummy;

import com.example.Threading.Dummy.Dto.DummyCreateDto;
import com.example.Threading.Dummy.Dto.DummyGetDto;
import com.example.Threading.Dummy.Dto.DummyUpdateDto;

import java.util.List;
import java.util.UUID;

public interface DummyService {
    Dummy getById(UUID uuid);
    void save(DummyCreateDto dummy);
    List<DummyGetDto> getAll();
    DummyGetDto update(UUID uuid, DummyUpdateDto dummy);
    void delete(UUID uuid);
}
