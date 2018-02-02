package com.pdh.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.pdh.model.Funcionario;
import com.pdh.model.DiaDeTrabalho;
import com.pdh.repository.DiaDeTrabalhoDao;

@Service
public class DiaDeTrabalhoService {

	@Autowired
	private DiaDeTrabalhoDao repository;
	
	
	public void save(DiaDeTrabalho diaDeTrabalho) {
		repository.save(diaDeTrabalho);
	}
	public List<DiaDeTrabalho> getAllByMonthByYearByFuncionario(Funcionario funcionario, int mes, int ano){
		List<DiaDeTrabalho> listaHoras = new ArrayList<>();
		for(DiaDeTrabalho diaDeTrabalho : funcionario.getDiasDeTrabalho()) {
			if(diaDeTrabalho.getDate().getYear() == ano) {
				if(diaDeTrabalho.getDate().getMonthValue() == mes) {
					listaHoras.add(diaDeTrabalho);
				}
			}
		}
		return listaHoras;
	}
	public String getAllTempoDeServicoByMonthByYearByFuncionario(Funcionario funcionario, int mes, int ano) {
		int totalHoras = 0;
		int totalMinutos = 0;
		for(DiaDeTrabalho diaDeTrabalho : funcionario.getDiasDeTrabalho()) {
			String[] tempoDeTrabalho = diaDeTrabalho.getTempoTrabalhado().split(":");
			totalHoras += Integer.parseInt(tempoDeTrabalho[0]);
			totalMinutos += Integer.parseInt(tempoDeTrabalho[1]);
		}
		totalHoras += (int) (totalMinutos/60);
		totalMinutos = totalMinutos % 60;
		return totalHoras + ":" + totalMinutos;
	}
}