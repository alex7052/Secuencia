package com.cystera.secuencia;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.DoubleStream;

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
	
	Collection<Operando> operandos= new ArrayList<Operando>();
	
	
	
	@PostMapping(value = "/createSecuencia")
    public ResponseEntity<Respuesta> createSecuencia(
    		@RequestParam(value="name", defaultValue="", required=false) String name) {
 		
		  
 		log.info("Ingreso al metodo /createSecuencia con los siguientes atributos name: " + name );
 		
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
    		@RequestParam(value="value", defaultValue="", required=true) Double valor,
    		@RequestParam(value="sequence", defaultValue="", required=true) Long idSecuencia) {
 		
		  
 		log.info("Ingreso al metodo /addOperador con los siguientes atributos valor: " + valor +  " secuencia" +  secuencia);
 		
 		
 		Secuencia s = new Secuencia();
 		Operando o = new Operando();
 		Respuesta r = new Respuesta();
 		Optional<Secuencia> se;
 		
 		if(valor == null || idSecuencia== null ){
 			log.error(" Error: parametros nulos" );
			r.setEstado(Estado.Fallido.toString());
			r.setDescripcion(" parametros nulos ");
			return new ResponseEntity<Respuesta>(r, HttpStatus.BAD_REQUEST);
 		}
 		

 		
 		try {
 			
 			se = secuencia.findById(idSecuencia);
 			
 			if(se.isPresent()){
 				
 				s = se.get();

 				o.setNumero(valor);
 				o.setSecuencia(s);
 				operando.save(o);
 				
 				
 			}else {
 				log.error(" Error: No se encontro la secuencia " + idSecuencia );
 				r.setEstado(Estado.Fallido.toString());
 				r.setDescripcion(" No se encontro la secuencia " + idSecuencia);
 				return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
 			}
 			
 			
 			
 		}catch (Exception e) {
 			e.printStackTrace();
 			log.error("Error: añadiendo operando: " + e.getMessage() );
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 		
 		
 		log.info("se añadio correctamante operando  " + valor +  " a la secuencia: " +  idSecuencia);
 		
 		r.setEstado(Estado.Exitoso.toString());
		r.setDescripcion("se añadio correctamante operando " + valor + " a la secuencia: " +  idSecuencia);
		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
        
	
	}
	
	@PostMapping(value = "/operation")
    public ResponseEntity<Respuesta> operation(
    		@RequestParam(value="operation", defaultValue="", required=true) Integer operation,
    		@RequestParam(value="sequence", defaultValue="", required=true) Long idSecuencia) {
 		
		  
 		log.info("Ingreso al metodo /operation con los siguientes atributos operation: " + operation +  " sequence "  + idSecuencia);
 		
 		Secuencia s = new Secuencia();
 		Respuesta r = new Respuesta();
 		Optional<Secuencia> se;
 		
 		if(operation == null || idSecuencia== null ){
 			log.error(" Error: parametros nulos" );
			r.setEstado(Estado.Fallido.toString());
			r.setDescripcion(" parametros nulos ");
			return new ResponseEntity<Respuesta>(r, HttpStatus.BAD_REQUEST);
 		}
 		
 		
 		try {
 			
 			 	se = secuencia.findById(idSecuencia);
	 	 			
	 	 		if(se.isPresent()){
	 	 			s = se.get();
	 	 			
	 	 			operandos = s.getOperandos();
	 	 			Double result = 0D;
	 	 			
	 	 			switch(operation) {
	 	 			
	 	 			case 1:
	 	 				
	 	 				result = operandos.stream().flatMapToDouble(num -> DoubleStream.of(num.getNumero())).reduce(0, (a, b) -> a + b);
	 	 				
	 	 				addOperador(result, idSecuencia);
	 	 				 	 				
	 	 				log.info("se realizo la suma correctamante  el resultado es " +  result);
	 	 		 		
	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la suma correctamante  el resultado es " +  result);
	 	 				
	 	 			
	 	 				break;
	 	 				
	 	 			case 2:
	 	 				
	 	 				for(int i =0; i< operandos.size()-1;i++){
	 	 					
	 	 					ArrayList<Operando> oper = new ArrayList<Operando>(operandos);
	 	 					if(i==0){
	 	 						result = oper.get(i).getNumero()- oper.get(i+1).getNumero();
	 	 					}else{
	 	 						result = result - oper.get(i+1).getNumero();
	 	 					}
	 	 					
	 	 				}
	 	 					 	 				
	 	 				addOperador(result, idSecuencia);
	 	 				 	 				
	 	 				log.info("se realizo la resta correctamante  el resultado es " +  result);
	 	 		 		
	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la resta correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
	 	 				
	 	 				
	 	 			
	 	 			case 3:
 	 				
	 	 				result = operandos.stream().flatMapToDouble(num -> DoubleStream.of(num.getNumero())).reduce(1, (a, b) -> a * b);
	 	 				
	 	 				addOperador(result, idSecuencia);
	 	 			
	 	 				System.out.println(result);
	 	 				
	 	 				log.info("se realizo la multiplicacion correctamante  el resultado es " +  result);
	 	 		 		
	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la multiplicacion correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
 	 				
	 	 				
	 	 			case 4:
	 	 				
	 	 				for(int i =0; i< operandos.size()-1;i++){
	 	 					
	 	 					ArrayList<Operando> oper = new ArrayList<Operando>(operandos);
	 	 					if(i==0){
	 	 						result = oper.get(i).getNumero() / oper.get(i+1).getNumero();
	 	 					}else{
	 	 						result = result / oper.get(i+1).getNumero();
	 	 					}
	 	 					
	 	 				}
	 	 				
	 	 				addOperador(result, idSecuencia);
	 	 			
	 	 				System.out.println(result);
	 	 				
	 	 				log.info("se realizo la Division correctamante  el resultado es " +  result);
	 	 		 		
	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la Division correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
	 	 				
	 	 			case 5:
	 	 				
	 	 				for(int i =0; i< operandos.size()-1;i++){
	 	 					
	 	 					ArrayList<Operando> oper = new ArrayList<Operando>(operandos);
	 	 					if(i==0){
	 	 						result = Math.pow(oper.get(i).getNumero() , oper.get(i+1).getNumero());
	 	 					}else{
	 	 						result =  Math.pow(result, oper.get(i+1).getNumero());
	 	 					}
	 	 					
	 	 				}
	 	 				
	 	 				addOperador(result, idSecuencia);
	 	 			
	 	 				System.out.println(result);
	 	 				
	 	 				log.info("se realizo la Potencia correctamante  el resultado es " +  result);
	 	 		 		
	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la Potencia correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
	 	 				
	 	 				default:
	 	 					
	 	 					r.setEstado(Estado.Fallido.toString());
		 	 				r.setDescripcion("Operation enviada no se encuentra disponible (1-Suma, 2-Resta, 3-Multiplicacion, 4-Division, 5-Potencia" +  operation);
 	 				
 	 			}
	 	 			
	 				 
	 			 }else {
	 				 
	 				log.error(" Error: No se encontro la secuencia " + idSecuencia );
	 				r.setEstado(Estado.Fallido.toString());
	 				r.setDescripcion(" No se encontro la secuencia " + idSecuencia);
	 				return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
	 			 }
 			
 			
	 		
 			
 		}catch (Exception e) {
 			
 			log.error("Error: realizando operacion " + e.getMessage() );
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 		
 		
 		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
 		
	
	}
	
	
	
}
