package com.curso.boot.web.controller;

import java.util.List;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.boot.domain.Cargo;
import com.curso.boot.domain.Departamento;
import com.curso.boot.service.CargoService;
import com.curso.boot.service.DepartamentoService;
import com.curso.boot.util.PaginacaoUtil;

@Controller
@RequestMapping("cargos")
public class CargoController {

	@Autowired
	CargoService cargoService;

	@Autowired
	DepartamentoService departamentoService;

	@GetMapping("/cadastrar")
	public String cadastrar(Cargo cargo) {
		return "/cargo/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model, @RequestParam("page") Optional<Integer> page,
			@RequestParam("dir") Optional<String> direcao) {

		int paginaAtual = page.orElse(1);
		String ordem = direcao.orElse("asc");

		PaginacaoUtil<Cargo> pageCargo = cargoService.buscarPorPagina(paginaAtual, ordem);
		model.addAttribute("pageCargo", pageCargo);

		return "/cargo/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cargo/cadastro";
		}

		cargoService.salvar(cargo);
		attr.addFlashAttribute("sucesso", "Cargo inserido com sucesso");

		return "redirect:/cargos/cadastrar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("cargo", cargoService.buscarPorId(id));
		return "cargo/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Cargo cargo, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/cargo/cadastro";
		}
		cargoService.editar(cargo);
		attr.addFlashAttribute("sucesso", "Cargo atualizado com sucesso!");
		return "redirect:/cargos/cadastrar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (cargoService.cargoTemFuncionarios(id)) {
			attr.addFlashAttribute("falha", "Cargo não excluido. Tem funcionários vinculados");
		} else {
			cargoService.excluir(id);
			attr.addFlashAttribute("sucesso", "Cargo excluido com sucesso.");
		}

		return "redirect:/cargos/listar";
	}

	@ModelAttribute("departamentos")
	public List<Departamento> listaDeDepartamentos() {
		return departamentoService.buscarTodos();
	}

}
