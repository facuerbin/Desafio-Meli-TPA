package com.example.mutantapi.repository;

import com.example.mutantapi.entities.Mutant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MutantRepository extends JpaRepository<Mutant, Long> {
    List <Mutant> findByDna (String dna);

    List <Mutant> findByIsMutant (boolean isMutant);
}
