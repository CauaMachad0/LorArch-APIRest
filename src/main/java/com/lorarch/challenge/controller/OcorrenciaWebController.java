package com.lorarch.challenge.controller;

import com.lorarch.challenge.dto.OcorrenciaDTO;
import com.lorarch.challenge.model.Ocorrencia;
import com.lorarch.challenge.model.TipoMovimento;
import com.lorarch.challenge.service.MotoService;
import com.lorarch.challenge.service.OcorrenciaService;
import com.lorarch.challenge.service.SetorService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/ocorrencias")
public class OcorrenciaWebController {

    @Autowired private OcorrenciaService ocorrenciaService;
    @Autowired private MotoService motoService;
    @Autowired private SetorService setorService;

    @GetMapping
    public String list(Model model) {
        model.addAttribute("ocorrencias", ocorrenciaService.listarTodas());
        return "ocorrencias/list";
    }

    @GetMapping("/novo")
    public String novo(Model model) {
        model.addAttribute("form", new OcorrenciaDTO());
        model.addAttribute("tipos", List.of(TipoMovimento.values()));   // enum
        model.addAttribute("motos", motoService.listarTodas());
        model.addAttribute("setores", setorService.listarTodos());
        model.addAttribute("action", "/ocorrencias");
        return "ocorrencias/form";
    }

    @PostMapping
    public String criar(@Valid @ModelAttribute("form") OcorrenciaDTO dto,
                        BindingResult br, Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("tipos", List.of(TipoMovimento.values())); // enum
            model.addAttribute("motos", motoService.listarTodas());
            model.addAttribute("setores", setorService.listarTodos());
            model.addAttribute("action", "/ocorrencias");
            return "ocorrencias/form";
        }
        ocorrenciaService.criar(dto);
        ra.addFlashAttribute("success", "Ocorrência criada com sucesso!");
        return "redirect:/ocorrencias";
    }

    @GetMapping("/{id}/editar")
    public String editar(@PathVariable Long id, Model model) {
        Ocorrencia o = ocorrenciaService.buscarPorId(id);

        OcorrenciaDTO dto = new OcorrenciaDTO();
        dto.setId(o.getId());
        dto.setTipo(o.getTipo()); // enum direto (SEM .name())
        dto.setDescricao(o.getDescricao());
        dto.setData(o.getData());
        dto.setCusto(o.getCusto());
        dto.setMotoId(o.getMoto().getId());
        dto.setSetorId(o.getSetor().getId());

        model.addAttribute("form", dto);
        model.addAttribute("tipos", List.of(TipoMovimento.values())); // enum
        model.addAttribute("motos", motoService.listarTodas());
        model.addAttribute("setores", setorService.listarTodos());
        model.addAttribute("action", "/ocorrencias/" + id);
        return "ocorrencias/form";
    }

    @PostMapping("/{id}")
    public String atualizar(@PathVariable Long id,
                            @Valid @ModelAttribute("form") OcorrenciaDTO dto,
                            BindingResult br, Model model, RedirectAttributes ra) {
        if (br.hasErrors()) {
            model.addAttribute("tipos", List.of(TipoMovimento.values())); // enum
            model.addAttribute("motos", motoService.listarTodas());
            model.addAttribute("setores", setorService.listarTodos());
            model.addAttribute("action", "/ocorrencias/" + id);
            return "ocorrencias/form";
        }
        ocorrenciaService.atualizar(id, dto);
        ra.addFlashAttribute("success", "Ocorrência atualizada com sucesso!");
        return "redirect:/ocorrencias";
    }

    @PostMapping("/{id}/excluir")
    public String excluir(@PathVariable Long id, RedirectAttributes ra) {
        ocorrenciaService.deletar(id);
        ra.addFlashAttribute("success", "Ocorrência excluída.");
        return "redirect:/ocorrencias";
    }

    @GetMapping("/list") public String listAlias(Model model) { return list(model); }
    @GetMapping("/nova") public String novaAlias(Model model) { return novo(model); }
}
