package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@Service
@CacheConfig(cacheNames = "motos")
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;


    private String normPlaca(String p) {
        return p == null ? null : p.replaceAll("\\s+", "").toUpperCase();
    }

    private StatusMoto parseStatus(String s) {
        if (s == null) throw new IllegalArgumentException("Status obrigatório.");
        return StatusMoto.valueOf(s.trim().toUpperCase().replace(' ', '_'));
    }

    private Pageable ensureDescId(Pageable pageable) {
        Sort sort = pageable == null || pageable.getSort().isUnsorted()
                ? Sort.by(Sort.Direction.DESC, "id")
                : pageable.getSort();
        int page = pageable == null ? 0 : Math.max(pageable.getPageNumber(), 0);
        int size = pageable == null ? 10 : (pageable.getPageSize() > 0 ? pageable.getPageSize() : 10);
        return PageRequest.of(page, size, sort);
    }


    @Transactional
    @CachePut(key = "#result.id")
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(cacheNames = "motosPages", allEntries = true)
    })
    public Moto criarMoto(MotoDTO dto) {
        String placa = normPlaca(dto.getPlaca());
        motoRepository.findByPlaca(placa)
                .ifPresent(m -> { throw new IllegalArgumentException("Placa já cadastrada: " + dto.getPlaca()); });

        Moto m = new Moto();
        m.setPlaca(placa);
        m.setModelo(dto.getModelo() == null ? null : dto.getModelo().trim());
        m.setSetor(dto.getSetor() == null ? null : dto.getSetor().trim());
        m.setStatus(parseStatus(dto.getStatus()));
        return motoRepository.save(m);
    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(cacheNames = "motosPages", allEntries = true)
    })
    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = buscarPorId(id);

        String placa = normPlaca(dto.getPlaca());
        motoRepository.findByPlaca(placa)
                .filter(existente -> !Objects.equals(existente.getId(), id))
                .ifPresent(x -> { throw new IllegalArgumentException("Placa já cadastrada: " + dto.getPlaca()); });

        moto.setPlaca(placa);
        moto.setModelo(dto.getModelo() == null ? null : dto.getModelo().trim());
        moto.setSetor(dto.getSetor() == null ? null : dto.getSetor().trim());
        moto.setStatus(parseStatus(dto.getStatus()));
        return motoRepository.save(moto);
    }

    @Transactional
    @CacheEvict(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(cacheNames = "motosPages", allEntries = true)
    })
    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(cacheNames = "motosPages", allEntries = true)
    })
    public Moto enviarParaManutencao(Long id) {
        Moto moto = buscarPorId(id);
        if (moto.getStatus() == StatusMoto.EM_MANUTENCAO) return moto;

        if (!(moto.getStatus() == StatusMoto.DISPONIVEL
                || moto.getStatus() == StatusMoto.EM_USO
                || moto.getStatus() == StatusMoto.NOVA)) {
            throw new IllegalStateException("Só é possível enviar para manutenção motos NOVA/DISPONIVEL/EM_USO.");
        }
        moto.setStatus(StatusMoto.EM_MANUTENCAO);
        return motoRepository.save(moto);
    }

    @Transactional
    @CachePut(key = "#id")
    @Caching(evict = {
            @CacheEvict(key = "'all'"),
            @CacheEvict(cacheNames = "motosPages", allEntries = true)
    })
    public Moto concluirManutencao(Long id) {
        Moto moto = buscarPorId(id);
        if (moto.getStatus() != StatusMoto.EM_MANUTENCAO) {
            throw new IllegalStateException("A moto não está em manutenção.");
        }
        moto.setStatus(StatusMoto.DISPONIVEL);
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

    @Cacheable(cacheNames = "motosPages",
            key = "T(java.util.Objects).hash(#termoBusca == null ? '' : #termoBusca.trim().toLowerCase(), " +
                    "#pageable.pageNumber, #pageable.pageSize, #pageable.sort)")
    public Page<Moto> buscarTodas(String termoBusca, Pageable pageable) {
        Pageable p = ensureDescId(pageable);

        if (termoBusca == null || termoBusca.isBlank()) {
            return motoRepository.findAll(p);
        }
        String t = termoBusca.trim();
        return motoRepository.findByPlacaContainingIgnoreCaseOrSetorContainingIgnoreCase(t, t, p);
    }

    public Map<String, Long> getEstatisticaGeral() {
        List<Moto> todas = motoRepository.findAll();

        long total         = todas.size();
        long disponivel    = todas.stream().filter(m -> m.getStatus() == StatusMoto.DISPONIVEL).count();
        long indisponivel  = todas.stream().filter(m -> m.getStatus() == StatusMoto.INDISPONIVEL).count();
        long manutencao    = todas.stream().filter(m -> m.getStatus() == StatusMoto.EM_MANUTENCAO).count();
        long danificada    = todas.stream().filter(m -> m.getStatus() == StatusMoto.DANIFICADA).count();

        Map<String, Long> stats = new HashMap<>();
        stats.put("TOTAL", total);
        stats.put("DISPONIVEL", disponivel);
        stats.put("INDISPONIVEL", indisponivel);
        stats.put("EM_MANUTENCAO", manutencao);
        stats.put("DANIFICADA", danificada);
        return stats;
    }
}
