package com.lorarch.challenge.controller;


import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

@Controller
@RequestMapping("/motos")
public class MotoWebController {

    private final MotoService service;
    public MotoWebController(MotoService service){ this.service = service; }

    @GetMapping
    public String list(Model model) {
        model.addAttribute("motos", service.listarTodos()); // ou paginado se tiver
        return "motos/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("moto", new MotoDTO());
        model.addAttribute("statuses", StatusMoto.values());
        model.addAttribute("action", "/motos");
        return "motos/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("moto") MotoDTO dto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", StatusMoto.values());
            model.addAttribute("action", "/motos");
            return "motos/form";
        }
        service.criar(dto);
        return "redirect:/motos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        MotoDTO dto = service.buscarPorId(id);
        model.addAttribute("moto", dto);
        model.addAttribute("statuses", StatusMoto.values());
        model.addAttribute("action", "/motos/"+id);
        return "motos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id, @Valid @ModelAttribute("moto") MotoDTO dto, BindingResult br, Model model) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", StatusMoto.values());
            model.addAttribute("action", "/motos/"+id);
            return "motos/form";
        }
        service.atualizar(id, dto);
        return "redirect:/motos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id) {
        service.deletar(id);
        return "redirect:/motos";
    }

    @PostMapping("/{id}/manutencao")
    public String enviarParaManutencao(@PathVariable Long id) {
        service.enviarParaManutencao(id, "Encaminhada para manutenção via painel");
        return "redirect:/motos";
    }
}
