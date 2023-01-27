package com.curso.boot.web.conversor;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.convert.converter.Converter;
import org.springframework.stereotype.Component;

import com.curso.boot.domain.Cargo;
import com.curso.boot.service.CargoService;

@Component
public class StringToCargoConversor implements Converter<String, Cargo> {

	@Autowired
	private CargoService service;

	@Override
	public Cargo convert(String source) {
		if (source.isEmpty()) {
			return null;
		}

		Long id = Long.valueOf(source);
		return service.buscarPorId(id);
	}

}
