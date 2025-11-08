package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.model.Setor;
import com.lorarch.challenge.repository.MotoRepository;
import com.lorarch.challenge.repository.OcorrenciaRepository;
import com.lorarch.challenge.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "ocorrencias")
public class OcorrenciaService {

    @Autowired private OcorrenciaRepository ocorrenciaRepository;
    @Autowired private MotoRepository motoRepository;
    @Autowired private SetorRepository setorRepository;

    public Page<Ocorrencia> listarPaginado(Pageable pageable) {
        return ocorrenciaRepository.findAll(pageable);
    }

    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Ocorrencia criar(OcorrenciaDTO dto) {
        Moto moto = motoRepository.findById(dto.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com ID: " + dto.getMotoId()));

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + dto.getSetorId()));

        Ocorrencia o = new Ocorrencia();
        o.setMoto(moto);
        o.setSetor(setor);
        o.setTipo(dto.getTipo());
        o.setTipoMovimentoExtra(dto.getTipo());
        o.setDescricao(dto.getDescricao());
        o.setData(dto.getData());
        o.setDataMovimento(dto.getData());
        o.setCusto(dto.getCusto());

        return ocorrenciaRepository.save(o);
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

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Ocorrencia atualizar(Long id, OcorrenciaDTO dto) {
        Ocorrencia o = buscarPorId(id);

        if (!o.getMoto().getId().equals(dto.getMotoId())) {
            Moto moto = motoRepository.findById(dto.getMotoId())
                    .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com ID: " + dto.getMotoId()));
            o.setMoto(moto);
        }

        if (!o.getSetor().getId().equals(dto.getSetorId())) {
            Setor setor = setorRepository.findById(dto.getSetorId())
                    .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + dto.getSetorId()));
            o.setSetor(setor);
        }

        o.setTipo(dto.getTipo());
        o.setTipoMovimentoExtra(dto.getTipo());
        o.setDescricao(dto.getDescricao());
        o.setData(dto.getData());
        o.setDataMovimento(dto.getData());
        o.setCusto(dto.getCusto());

        return ocorrenciaRepository.save(o);
    }

    @Transactional
    @CacheEvict(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public void deletar(Long id) {
        Ocorrencia o = buscarPorId(id);
        ocorrenciaRepository.delete(o);
    }
}
