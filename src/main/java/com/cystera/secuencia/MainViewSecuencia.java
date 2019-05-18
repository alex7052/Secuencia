package com.cystera.secuencia;

import com.cystera.secuencia.entities.Secuencia;
import com.cystera.secuencia.entities.SecuenciaRepository;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.grid.Grid;
import com.vaadin.flow.component.html.Label;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextArea;
import com.vaadin.flow.router.Route;

@Route("Secuencias")
public class MainViewSecuencia extends VerticalLayout {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	private Grid<Secuencia> grid = new Grid<Secuencia>();
	private TextArea textArea = new TextArea();
	private Button button = new Button("Enviar");
	private final SecuenciaRepository secuencia;
	 	
	public MainViewSecuencia(SecuenciaRepository secuencia){
		
		this.secuencia = secuencia;
		grid.setHeightByRows(true);
        grid.addColumn(Secuencia::getId);
        grid.addColumn(Secuencia::getDescripcion);


		add(grid);
		actualizar();
		
		 textArea.setWidth("100%");

	     setHorizontalComponentAlignment(Alignment.END, button);
	     button.addClickListener(event -> enviar(textArea.getValue()));

	     add(new Label("Registro de Secuencias"),grid, textArea, button);
    }
	
	 private void enviar(String contenido) {
		 	Secuencia s = new Secuencia();
		 	s.setDescripcion(contenido);
	        secuencia.save(s);
	        actualizar();
	        textArea.clear();
	    }

	private void actualizar() {
        grid.setItems(secuencia.findByAll());
    }

	

}

