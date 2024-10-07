package com.example.Threading.Dummy;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface DummyRepository extends JpaRepository<Dummy, UUID> {
}
