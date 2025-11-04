package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.service.MotoService;
import com.lorarch.challenge.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;


@Controller
@RequestMapping("/motos")
public class MotoWebController {

    @Autowired
    private MotoService motoService;

    @Autowired
    private SetorService setorService;

    @GetMapping("/listar")
    public String listarMotos(Model model) {
        model.addAttribute("motos", motoService.listarTodas());
        return "motos/list";
    }

    @GetMapping("/nova")
    public String exibirFormulario(MotoDTO motoDTO, Model model) {
        model.addAttribute("setores", setorService.listarTodos());
        return "motos/form";
    }

    @PostMapping("/cadastrar")
    public String cadastrarMoto(@Valid MotoDTO motoDTO, BindingResult result, RedirectAttributes attributes, Model model) {
        if (result.hasErrors()) {
            model.addAttribute("setores", setorService.listarTodos());
            return "motos/form";
        }

        try {
            motoService.criarMoto(motoDTO);
            attributes.addFlashAttribute("success", "Motocicleta cadastrada com sucesso!");
            return "redirect:/motos/listar";
        } catch (IllegalArgumentException e) {
            result.rejectValue("placa", "error.motoDTO", e.getMessage());
            model.addAttribute("setores", setorService.listarTodos());
            return "motos/form";
        }
    }

    @GetMapping("/editar/{id}")
    public String editarMoto(@PathVariable Long id, Model model) {
        MotoDTO dto = new MotoDTO(motoService.buscarPorId(id));
        model.addAttribute("motoDTO", dto);
        model.addAttribute("setores", setorService.listarTodos());
        return "motos/form";
    }

    @PostMapping("/deletar/{id}")
    public String deletarMoto(@PathVariable Long id, RedirectAttributes attributes) {
        motoService.deletar(id);
        attributes.addFlashAttribute("success", "Motocicleta deletada com sucesso!");
        return "redirect:/motos/listar";
    }
}
