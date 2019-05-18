package com.cystera.secuencia;

import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cystera.enums.Estado;
import com.cystera.secuencia.entities.AuditoriaRepository;
import com.cystera.secuencia.entities.Operando;
import com.cystera.secuencia.entities.OperandoRepository;
import com.cystera.secuencia.entities.Respuesta;
import com.cystera.secuencia.entities.Secuencia;
import com.cystera.secuencia.entities.SecuenciaRepository;

@RestController
@RequestMapping("/api")
public class SecuenciaRestController {
	
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private AuditoriaRepository auditoria;
	
	@Autowired
	private OperandoRepository operando;
	
	@Autowired
	private SecuenciaRepository secuencia;
	
	
	@PostMapping(value = "/createSecuencia")
    public ResponseEntity<Respuesta> createSecuencia(
    		@RequestParam(value="name", defaultValue="", required=false) String name) {
 		
		  
 		log.info("Ingreso al metodo /createSecuencia con los sigueintes atributos name: " + name );
 		
 		Secuencia s = new Secuencia();
 		Respuesta r = new Respuesta();
 		
 		try {
 			s.setDescripcion(name);
 	 		secuencia.save(s);
 		}catch (Exception e) {
 			
 			log.error("Error creando secuencia: " + e.getMessage() );
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.BAD_REQUEST);
		}
 		
 		
 		log.info("Se creo la secuencia con id: " + s.getId());
 		
 		r.setEstado(Estado.Exitoso.toString());
		r.setDescripcion("Se creo la secuencia con id: " + s.getId());
		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
        
	
	}
	
	@PostMapping(value = "/addOperador")
    public ResponseEntity<Respuesta> addOperador(
    		@RequestParam(value="valor", defaultValue="", required=true) Double valor,
    		@RequestParam(value="secuencia", defaultValue="", required=true) Long idSecuencia) {
 		
		  
 		log.info("Ingreso al metodo /addOperador con los sigueintes atributos valor: " + valor +  " secuencia" +  secuencia);
 		
 		Secuencia s = new Secuencia();
 		Operando o = new Operando();
 		Respuesta r = new Respuesta();
 		Optional<Secuencia> se;
 		
 		try {
 			
 			se = secuencia.findById(idSecuencia);
 			
 			if(se.isPresent()){
 				
 				s = se.get();
 				o.setNumero(valor);
 				o.setSecuencia(s);
 				operando.save(o);
 				
 			}else {
 				r.setEstado(Estado.Fallido.toString());
 				r.setDescripcion(" No se encontro la secuencia " + idSecuencia);
 				return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
 			}
 			
 			
 			
 		}catch (Exception e) {
 			
 			log.error("Error añadiendo operando: " + e.getMessage() );
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 		
 		
 		log.info("se añadio correctamante operando  " + valor +  "a la secuencia: " +  idSecuencia);
 		
 		r.setEstado(Estado.Exitoso.toString());
		r.setDescripcion("se añadio correctamante operando " + valor + " a la secuencia: " +  idSecuencia);
		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
        
	
	}
	
	
	
}
