package com.lorarch.challenge.service;

import com.lorarch.challenge.exception.ResourceNotFoundException;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.repository.MotoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MotoService {

    @Autowired
    private MotoRepository motoRepository;

    public List<Moto> listarTodas() {
        return motoRepository.findAll();
    }

    public Moto buscarPorId(Long id) {
        return motoRepository.findById(id)
                .orElseThrow(() -> new com.lorarch.challenge.service.ResourceNotFoundException("Moto não encontrada com ID: " + id));
    }

    public Moto salvar(Moto moto) {
        return motoRepository.save(moto);
    }

    public Moto atualizar(Long id, Moto motoAtualizada) {
        Moto moto = buscarPorId(id);
        moto.setStatus(motoAtualizada.getStatus());
        moto.setDescricao(motoAtualizada.getDescricao());
        return motoRepository.save(moto);
    }

    public void deletar(Long id) {
        Moto moto = buscarPorId(id);
        motoRepository.delete(moto);
    }
}
