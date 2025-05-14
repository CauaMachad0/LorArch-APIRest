package com.lorarch.challenge.repository;

import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MotoRepository extends JpaRepository<Moto, Long> {
    List<Moto> findByStatus(StatusMoto status);
}
