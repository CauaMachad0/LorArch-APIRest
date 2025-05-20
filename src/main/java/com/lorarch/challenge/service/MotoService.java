package com.lorarch.challenge.service;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public Moto criarMoto(MotoDTO dto) {
        Moto moto = new Moto();
        moto.setPlaca(dto.getPlaca());
        moto.setStatus(convertToStatusEnum(dto.getStatus()));
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

    public Moto atualizar(Long id, MotoDTO dto) {
        Moto moto = buscarPorId(id);
        moto.setStatus(StatusMoto.valueOf(dto.getStatus().toUpperCase()));
        moto.setSetor(dto.getSetor());
        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }

    private StatusMoto convertToStatusEnum(String status) {
        try {
            return StatusMoto.valueOf(status.toUpperCase());
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Status inválido: " + status);
        }
    }
}
