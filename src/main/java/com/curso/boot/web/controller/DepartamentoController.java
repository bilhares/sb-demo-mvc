package com.curso.boot.web.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.curso.boot.domain.Departamento;
import com.curso.boot.service.DepartamentoService;

@Controller
@RequestMapping("/departamentos")
public class DepartamentoController {

	@Autowired
	private DepartamentoService service;

	@GetMapping("/cadastrar")
	public String cadastrar(Departamento departamento) {
		return "/departamento/cadastro";
	}

	@GetMapping("/listar")
	public String listar(ModelMap model) {
		model.addAttribute("departamentos", service.buscarTodos());
		return "/departamento/lista";
	}

	@PostMapping("/salvar")
	public String salvar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/departamento/cadastro";
		}

		service.salvar(departamento);
		attr.addFlashAttribute("sucesso", "departamento inserido com sucesso");
		return "redirect:/departamentos/listar";
	}

	@GetMapping("/editar/{id}")
	public String preEditar(@PathVariable("id") Long id, ModelMap model) {
		model.addAttribute("departamento", service.buscarPorId(id));
		return "/departamento/cadastro";
	}

	@PostMapping("/editar")
	public String editar(@Valid Departamento departamento, BindingResult result, RedirectAttributes attr) {

		if (result.hasErrors()) {
			return "/departamento/cadastro";
		}

		service.editar(departamento);
		attr.addFlashAttribute("sucesso", "departamento editado com sucesso");
		return "redirect:/departamentos/listar";
	}

	@GetMapping("/excluir/{id}")
	public String excluir(@PathVariable("id") Long id, RedirectAttributes attr) {
		if (service.departamentoTemCargos(id)) {
			attr.addFlashAttribute("falha", "Departamento não pode ser removido, pois possuí cargos vinculados");
		} else {
			service.excluir(id);
			attr.addFlashAttribute("sucesso", "Departamento excluido com sucesso!");
		}

		return "redirect:/departamentos/listar";
	}
}
