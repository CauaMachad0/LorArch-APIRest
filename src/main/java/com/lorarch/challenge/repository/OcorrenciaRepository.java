package com.lorarch.challenge.repository;

import com.lorarch.challenge.model.Ocorrencia;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface OcorrenciaRepository extends JpaRepository<Ocorrencia, Long> {

    @Override
    @EntityGraph(attributePaths = {"moto", "setor"})
    List<Ocorrencia> findAll();

    @Override
    @EntityGraph(attributePaths = {"moto", "setor"})
    Optional<Ocorrencia> findById(Long id);
}
