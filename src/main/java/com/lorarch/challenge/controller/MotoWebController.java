package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.MotoDTO;
import com.lorarch.challenge.model.Moto;
import com.lorarch.challenge.model.StatusMoto;
import com.lorarch.challenge.service.MotoService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequestMapping("/motos")
public class MotoWebController {

    @Autowired
    private MotoService service;

    @GetMapping
    public String list(@RequestParam(value = "q", required = false) String termoBusca,
                       Pageable pageable,
                       Model model) {
        Page<Moto> motosPage = service.buscarTodas(termoBusca, pageable);
        model.addAttribute("motos", motosPage.getContent());
        model.addAttribute("page", motosPage);

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
    public String criar(@Valid @ModelAttribute("moto") MotoDTO dto,
                        BindingResult br, Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("statuses", StatusMoto.values());
            model.addAttribute("action", "/motos");
            return "motos/form";
        }
        service.criarMoto(dto);
        ra.addFlashAttribute("success", "Moto criada com sucesso!");
        return "redirect:/motos";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        var moto = service.buscarPorId(id);
        var dto  = new MotoDTO(moto.getPlaca(), moto.getModelo(),
                moto.getStatus().name(), moto.getSetor());

        model.addAttribute("moto", dto);
        model.addAttribute("statuses", StatusMoto.values());
        model.addAttribute("action", "/motos/" + id);
        return "motos/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("moto") MotoDTO dto,
                            BindingResult br, Model model, RedirectAttributes ra) {
        service.atualizar(id, dto);
        ra.addFlashAttribute("success", "Moto atualizada com sucesso!");
        return "redirect:/motos";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        service.deletar(id);
        ra.addFlashAttribute("success", "Moto excluída.");
        return "redirect:/motos";
    }

    @PostMapping("/{id}/enviar-manutencao")
    public String enviarParaManutencao(@PathVariable Long id, RedirectAttributes ra) {
        service.enviarParaManutencao(id);
        ra.addFlashAttribute("success", "Encaminhada para manutenção.");
        return "redirect:/motos";
    }

    @PostMapping("/{id}/concluir-manutencao")
    public String concluirManutencao(@PathVariable Long id, RedirectAttributes ra) {
        service.concluirManutencao(id);
        ra.addFlashAttribute("success", "Manutenção concluída.");
        return "redirect:/motos";
    }
}
