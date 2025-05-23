package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.repository.MotoRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    @Transactional
    public Moto criarMoto(MotoDTO dto) {
        validarCampos(dto);

        if (motoRepository.findByPlaca(dto.getPlaca()).isPresent()) {
            throw new DataIntegrityViolationException("Já existe uma moto com a placa: " + dto.getPlaca());
        }

        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setStatus(converterStatus(dto.getStatus()));
        moto.setSetor(dto.getSetor());

        return motoRepository.save(moto);
    }

    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Moto não encontrada com ID: " + id));
    }

    @Transactional
    public Moto atualizar(Long id, MotoDTO dto) {
        validarCampos(dto);

        Moto moto = buscarPorId(id);
        moto.setStatus(converterStatus(dto.getStatus()));
        moto.setSetor(dto.getSetor());

        return motoRepository.save(moto);
    }

    @Transactional
    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }

    private StatusMoto converterStatus(String status) {
        try {
            return StatusMoto.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            throw new IllegalArgumentException("Status inválido: " + status + ". Os valores válidos são: " + List.of(StatusMoto.values()));
        }
    }

    private void validarCampos(MotoDTO dto) {
        if (dto.getPlaca() == null || dto.getPlaca().isBlank()) {
            throw new IllegalArgumentException("A placa não pode ser vazia.");
        }
        if (dto.getStatus() == null || dto.getStatus().isBlank()) {
            throw new IllegalArgumentException("O status não pode ser vazio.");
        }
        if (dto.getSetor() == null || dto.getSetor().isBlank()) {
            throw new IllegalArgumentException("O setor não pode ser vazio.");
        }
    }
}