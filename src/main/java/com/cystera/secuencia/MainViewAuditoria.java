package com.cystera.secuencia;

import com.cystera.secuencia.entities.Auditoria;
import com.cystera.secuencia.entities.AuditoriaRepository;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;

@Route("auditoria")
public class MainViewAuditoria extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Grid<Auditoria> grid = new Grid<Auditoria>();
	
	private final AuditoriaRepository auditoria;
	 	
	public MainViewAuditoria(AuditoriaRepository auditoria){
		
		this.auditoria = auditoria;
		grid.setHeightByRows(true);
        grid.addColumn(Auditoria::getOperacion);
        grid.addColumn(Auditoria::getEstadoOperacion);
        grid.addColumn(Auditoria::getDescripcionResultado);
        grid.addColumn(Auditoria::getFecha);

		add(new Label("Registro de Auditoria"),grid);
		actualizar();
    }

	private void actualizar() {
        grid.setItems(auditoria.findByAll());
    }

	

}

