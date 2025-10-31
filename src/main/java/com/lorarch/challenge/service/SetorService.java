package com.lorarch.challenge.service;

import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Setor;
import com.lorarch.challenge.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@CacheConfig(cacheNames = "setores")
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    @Cacheable(key = "'all'")
    public List<Setor> listarTodos() {
        return setorRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"));
    }

    @Cacheable(key = "#id")
    public Setor buscarPorId(Long id) {
        return setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + id));
    }

    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Setor criar(Setor dados) {
        if (dados.getNome() != null) dados.setNome(dados.getNome().trim());
        return setorRepository.save(dados);
    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Setor atualizar(Long id, Setor dados) {
        Setor existente = buscarPorId(id);
        existente.setNome(dados.getNome());
        return setorRepository.save(existente);
    }

    @Transactional
    @CacheEvict(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public void deletar(Long id) {
        Setor existente = buscarPorId(id);
        setorRepository.delete(existente);
    }
}
