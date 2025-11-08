package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.SetorDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Setor;
import com.lorarch.challenge.repository.SetorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@CacheConfig(cacheNames = "setores")
public class SetorService {

    @Autowired
    private SetorRepository setorRepository;

    /** Métodos de Conversão (Mapper Simples) **/

    private SetorDTO toDTO(Setor setor) {
        if (setor == null) return null;
        return new SetorDTO(setor.getId(), setor.getNome());
    }

    private Setor toEntity(SetorDTO dto) {
        if (dto == null) return null;
        Setor setor = new Setor();

        setor.setNome(dto.getNome().trim());
        return setor;
    }


    @Cacheable(key = "'all'")
    public List<SetorDTO> listarTodos() {
        return setorRepository.findAll(Sort.by(Sort.Direction.ASC, "nome"))
                .stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }


    @Cacheable(key = "#id")
    public SetorDTO buscarPorId(Long id) {
        Setor setor = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + id));
        return toDTO(setor);
    }


    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public SetorDTO criar(SetorDTO dto) {
        Setor novoSetor = toEntity(dto);
        Setor salvo = setorRepository.save(novoSetor);
        return toDTO(salvo);
    }

    @Transactional
    @CachePut(key = "#id") // A chave de cache é o ID do Setor
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public SetorDTO atualizar(Long id, SetorDTO dto) {

        Setor existente = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + id));

        existente.setNome(dto.getNome().trim());

        Setor atualizado = setorRepository.save(existente);
        return toDTO(atualizado);
    }


    @Transactional
    @CacheEvict(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public void deletar(Long id) {
        Setor existente = setorRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Setor não encontrado com ID: " + id));
        setorRepository.delete(existente);
    }
}