package com.example.Threading.Dummy.Dto;

import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@EqualsAndHashCode(callSuper = true)
@Data
public class DummyGetDto extends DummyBaseDto{
    private UUID uuid;
}
