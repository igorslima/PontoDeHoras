package com.pdh.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.pdh.model.Funcionario;
import com.pdh.model.DiaDeTrabalho;
import com.pdh.service.FuncionarioService;
import com.pdh.service.DiaDeTrabalhoService;

@Controller
public class FuncionarioController {

	@Autowired
	private FuncionarioService funcionarioService;
		
	@Autowired
	private DiaDeTrabalhoService diaDeTrabalhoService;
	
	@GetMapping(path="/funcionarios")
	public ModelAndView findAll(DiaDeTrabalho diaDeTrabalho) {
		ModelAndView mv = new ModelAndView("/funcionarios/listar");
		
		mv.addObject("funcionarios", funcionarioService.findAll());
		mv.addObject("diaDeTrabalho", diaDeTrabalho);
		
		return mv;
	}
	@GetMapping(path="/funcionarios/verentradas/")
	public ModelAndView getDiasDeTrabalhoByFuncionario(@RequestParam("id") int id, @RequestParam("ano") int ano, @RequestParam("mes") int mes) {
		Funcionario funcionario = funcionarioService.findOne(id);
		ModelAndView mv = new ModelAndView("/funcionarios/verEntradasEOuSaidas");
		mv.addObject("funcionario", funcionario);
		mv.addObject("lista", diaDeTrabalhoService.getAllByMonthByYearByFuncionario(funcionario, mes, ano));
		mv.addObject("total", diaDeTrabalhoService.getAllTempoDeServicoByMonthByYearByFuncionario(funcionario, mes, ano));
		return mv;
	}
	
	@GetMapping(path="/funcionarios/add")
	public ModelAndView add(Funcionario funcionario) {
		ModelAndView mv = new ModelAndView("/funcionarios/add");
		mv.addObject("funcionario", funcionario);
		
		return mv;
	}
	@GetMapping(path="/funcionarios/edit/{id}")
	public ModelAndView edit(@PathVariable("id") int id) {
		return add(funcionarioService.findOne(id));
	}
	@GetMapping(path="/funcionarios/delete/{id}")
	public ModelAndView delete(@PathVariable("id") int id) {
		funcionarioService.delete(id);
		return findAll(new DiaDeTrabalho());
	}
	@PostMapping(path="/funcionarios/save")
	public ModelAndView save(@Valid Funcionario funcionario, BindingResult result) {
		if(result.hasErrors())
			return findAll(new DiaDeTrabalho());
		
		funcionarioService.save(funcionario);
		return findAll(new DiaDeTrabalho());
	}
	@GetMapping(path="/funcionarios/entradaousaida/add")
	public ModelAndView addDiaDeTrabalhoFuncionario() {
		return findAll(new DiaDeTrabalho());
	}

}
