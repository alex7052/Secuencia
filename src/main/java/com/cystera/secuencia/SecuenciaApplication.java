package com.cystera.secuencia;

import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import com.cystera.secuencia.entities.Auditoria;
import com.cystera.secuencia.entities.AuditoriaRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@SpringBootApplication
public class SecuenciaApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecuenciaApplication.class, args);
	}
	
	
}
