package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "motos")
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @CachePut(key = "#result.id")
    public Moto criarMoto(MotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setStatus(StatusMoto.valueOf(dto.getStatus().toUpperCase()));
        moto.setSetor(dto.getSetor());
        return motoRepository.save(moto);
    }

    @Cacheable(key = "'all'")
    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    @Cacheable(key = "#id")
    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com ID: " + id));
    }

    @CachePut(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'")
    })
    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = buscarPorId(id);
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setStatus(StatusMoto.valueOf(dto.getStatus().toUpperCase()));
        moto.setSetor(dto.getSetor());
        return motoRepository.save(moto);
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'")
    })
    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }
}
