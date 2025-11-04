package com.lorarch.challenge.service;


import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;


@Service
@CacheConfig(cacheNames = "motos")
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;
    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Moto criarMoto(MotoDTO dto) {
        motoRepository.findByPlaca(dto.getPlaca())
                .ifPresent(m -> { throw new IllegalArgumentException("Placa já cadastrada: " + dto.getPlaca()); });

        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setStatus(parseStatus(dto.getStatus()));
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

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = buscarPorId(id);
        motoRepository.findByPlaca(dto.getPlaca())
                .filter(existente -> !existente.getId().equals(id))
                .ifPresent(x -> { throw new IllegalArgumentException("Placa já cadastrada: " + dto.getPlaca()); });

        moto.setPlaca(dto.getPlaca());
        moto.setModelo(dto.getModelo());
        moto.setStatus(parseStatus(dto.getStatus()));
        moto.setSetor(dto.getSetor());
        return motoRepository.save(moto);

    }

    @Transactional
    @CacheEvict(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Moto enviarParaManutencao(Long id) {
        Moto moto = buscarPorId(id);
        if (moto.getStatus() == StatusMoto.EM_MANUTENCAO) return moto;
        if (!(moto.getStatus() == StatusMoto.DISPONIVEL || moto.getStatus() == StatusMoto.EM_USO || moto.getStatus() == StatusMoto.NOVA))
            throw new IllegalStateException("Só é possível enviar para manutenção motos NOVA/DISPONIVEL/EM_USO.");
        moto.setStatus(StatusMoto.EM_MANUTENCAO);
        return motoRepository.save(moto);

    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = { @CacheEvict(key = "'all'") })
    public Moto concluirManutencao(Long id) {
        Moto moto = buscarPorId(id);
        if (moto.getStatus() != StatusMoto.EM_MANUTENCAO)
            throw new IllegalStateException("A moto não está em manutenção.");
        moto.setStatus(StatusMoto.DISPONIVEL);
        return motoRepository.save(moto);

    }

    private StatusMoto parseStatus(String s) {
        if (s == null) throw new IllegalArgumentException("Status obrigatório.");
        return StatusMoto.valueOf(s.trim().toUpperCase());

    }

}