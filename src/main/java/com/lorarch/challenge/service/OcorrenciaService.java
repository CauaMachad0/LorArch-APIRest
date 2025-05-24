package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.repository.OcorrenciaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@CacheConfig(cacheNames = "ocorrencias")
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private MotoService motoService;

    @CachePut(key = "#result.id")
    @Caching(evict = {
            @CacheEvict(key = "'all'")
    })
    public Ocorrencia criarOcorrencia(OcorrenciaDTO dto) {
        Moto moto = motoService.buscarPorId(dto.getMotoId());

        Ocorrencia ocorrencia = new Ocorrencia();
        ocorrencia.setTipo(dto.getTipo());
        ocorrencia.setDescricao(dto.getDescricao());
        ocorrencia.setData(dto.getData());
        ocorrencia.setMoto(moto);

        return ocorrenciaRepository.save(ocorrencia);
    }

    @Cacheable(key = "'all'")
    public List<Ocorrencia> listarTodas() {
        return ocorrenciaRepository.findAll();
    }

    @Cacheable(key = "#id")
    public Ocorrencia buscarPorId(Long id) {
        return ocorrenciaRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Ocorrência não encontrada com ID: " + id));
    }

    @CacheEvict(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'")
    })
    public void deletar(Long id) {
        Ocorrencia ocorrencia = buscarPorId(id);
        ocorrenciaRepository.delete(ocorrencia);
    }
}
