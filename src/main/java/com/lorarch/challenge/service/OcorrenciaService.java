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
import java.util.Set;

@Service
@CacheConfig(cacheNames = "ocorrencias")
public class OcorrenciaService {

    @Autowired
    private OcorrenciaRepository ocorrenciaRepository;

    @Autowired
    private MotoRepository motoRepository;

    @Autowired
    private SetorRepository setorRepository;

    private static final Set<String> TIPOS_VALIDOS = Set.of("Entrada", "Saida", "Manutencao", "Diagnostico");

    public Page<Ocorrencia> listarPaginado(Pageable pageable) {
        return ocorrenciaRepository.findAll(pageable);
    }

    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Ocorrencia criar(OcorrenciaDTO dto) {
        validarTipo(dto.getTipo());

        Moto moto = motoRepository.findById(dto.getMotoId())
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com ID: " + dto.getMotoId()));

        Setor setor = setorRepository.findById(dto.getSetorId())
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + dto.getSetorId()));

        Ocorrencia o = new Ocorrencia();
        o.setMoto(moto);
        o.setSetor(setor);
        o.setTipo(dto.getTipo());
        o.setDescricao(dto.getDescricao());
        o.setData(dto.getData());
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
        validarTipo(dto.getTipo());

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
        o.setDescricao(dto.getDescricao());
        o.setData(dto.getData());
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

    private void validarTipo(String tipo) {
        if (tipo == null || !TIPOS_VALIDOS.contains(tipo.trim())) {
            throw new IllegalArgumentException("Tipo inválido. Use: " + String.join(", ", TIPOS_VALIDOS));
        }
    }
}
