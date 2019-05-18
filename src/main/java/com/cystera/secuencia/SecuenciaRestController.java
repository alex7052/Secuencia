package com.cystera.secuencia;

import java.util.ArrayList;
import java.util.List;
import java.util.Collection;
import java.util.Optional;
import java.util.stream.DoubleStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.cystera.enums.Estado;
import com.cystera.secuencia.entities.Auditoria;
import com.cystera.secuencia.entities.AuditoriaRepository;
import com.cystera.secuencia.entities.Operando;
import com.cystera.secuencia.entities.OperandoRepository;
import com.cystera.secuencia.entities.Respuesta;
import com.cystera.secuencia.entities.Secuencia;
import com.cystera.secuencia.entities.SecuenciaRepository;

import oracle.net.aso.e;

@RestController
@RequestMapping("/api")
public class SecuenciaRestController {
	
	//Atributos de la clase
	
	private final Logger log = LoggerFactory.getLogger(this.getClass());
	
	/**
	 * Instancia de la interfaz auditoria
	 */
	@Autowired
	private AuditoriaRepository auditoria;
	
	/**
	 * Instancia de la intefaz operando
	 */
	@Autowired
	private OperandoRepository operando;
	
	/**
	 * instancia de la interdaz secuencia
	 */
	@Autowired
	private SecuenciaRepository secuencia;
	
	/**
	 * Coleccion de operandos
	 */
	Collection<Operando> operandos= new ArrayList<Operando>();
	
	//metodos de la clase
	
	/**
	 * Metodo para crear una secuencia
	 * @param name
	 * @return
	 */
	@PostMapping(value = "/createSequence")
    public ResponseEntity<Respuesta> createSequence(
    		@RequestParam(value="name", defaultValue="", required=false) String name) {
 		
		
		  
 		log.info("Ingreso al metodo /createSecuencia con los siguientes atributos name: " + name );
 		guardarAuditoria("Ingreso al metodo /createSecuencia con los siguientes atributos name: " + name, Estado.Exitoso.toString(), "createSecuencia");
 		
 		Secuencia s = new Secuencia();
 		Respuesta r = new Respuesta();
 		
 		try {
 			s.setDescripcion(name);
 	 		secuencia.save(s);
 	 		
 		}catch (Exception e) {
 			e.printStackTrace();
 			log.error("Error creando secuencia: " + e.getMessage() );
 			guardarAuditoria("Error creando secuencia: " + e.getMessage(), Estado.Fallido.toString(), "createSecuencia");
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.BAD_REQUEST);
		}
 		
 		
 		log.info("Se creo la secuencia con id: " + s.getId());
 		guardarAuditoria("Se creo la secuencia con id: " + s.getId(), Estado.Exitoso.toString(), "createSecuencia");
 		r.setEstado(Estado.Exitoso.toString());
		r.setDescripcion("Se creo la secuencia con id: " + s.getId());
		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
        
	
	}
	
	/**
	 * Metodo para añadir un operando a una secuencia
	 * @param valor
	 * @param idSecuencia
	 * @return
	 */
	@PostMapping(value = "/addOperador")
    public ResponseEntity<Respuesta> addOperador(
    		@RequestParam(value="value", defaultValue="", required=true) Double valor,
    		@RequestParam(value="sequence", defaultValue="", required=true) Long idSecuencia) {
 		
		  
 		log.info("Ingreso al metodo /addOperador con los siguientes atributos valor: " + valor +  " secuencia" +  secuencia);
 		guardarAuditoria("Ingreso al metodo /addOperador con los siguientes atributos valor: " + valor +  " secuencia" +  secuencia, Estado.Exitoso.toString(), "addOperador");
 		
 		Secuencia s = new Secuencia();
 		Operando o = new Operando();
 		Respuesta r = new Respuesta();
 		Optional<Secuencia> se;
 		
 		if(valor == null || idSecuencia== null ){
 			log.error(" Error: parametros nulos" );
 	 		guardarAuditoria(" Error: parametros nulos", Estado.Fallido.toString(), "addOperador");
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
 	 	 		guardarAuditoria(" Error: No se encontro la secuencia " , Estado.Fallido.toString(), "addOperador");
 				r.setEstado(Estado.Fallido.toString());
 				r.setDescripcion(" No se encontro la secuencia " + idSecuencia);
 				return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
 			}
 			
 			
 			
 		}catch (Exception e) {
 			e.printStackTrace();
 			log.error("Error: añadiendo operando: " + e.getMessage() );
	 	 	guardarAuditoria("Error: añadiendo operando: " , Estado.Fallido.toString(), "addOperador");
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 		
 		
 		log.info("se añadio correctamante operando  " + valor +  " a la secuencia: " +  idSecuencia);
 	 	guardarAuditoria("se añadio correctamante operando  " + valor +  " a la secuencia: " +  idSecuencia, Estado.Exitoso.toString(), "addOperador");
 		r.setEstado(Estado.Exitoso.toString());
		r.setDescripcion("se añadio correctamante operando " + valor + " a la secuencia: " +  idSecuencia);
		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
        
	
	}
	
	/**
	 * Metodo para relaizar na operacion a una lista de operandos de determinada secuencia
	 * @param operation
	 * @param idSecuencia
	 * @return
	 */
	@PostMapping(value = "/operation")
    public ResponseEntity<Respuesta> operation(
    		@RequestParam(value="operation", defaultValue="", required=true) Integer operation,
    		@RequestParam(value="sequence", defaultValue="", required=true) Long idSecuencia) {
 		
		  
 		log.info("Ingreso al metodo /operation con los siguientes atributos operation: " + operation +  " sequence "  + idSecuencia);
 	 	guardarAuditoria("Ingreso al metodo /operation con los siguientes atributos operation: " + operation +  " sequence "  + idSecuencia, Estado.Exitoso.toString(), "operation");
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
	 	 		 	 	guardarAuditoria("se realizo la suma correctamante  el resultado es " +  result, Estado.Exitoso.toString(), "operation");

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
	 	 		 	 	guardarAuditoria("se realizo la resta correctamante  el resultado es " + result, Estado.Exitoso.toString(), "operation");

	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la resta correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
	 	 				
	 	 				
	 	 			
	 	 			case 3:
 	 				
	 	 				result = operandos.stream().flatMapToDouble(num -> DoubleStream.of(num.getNumero())).reduce(1, (a, b) -> a * b);
	 	 				
	 	 				addOperador(result, idSecuencia);
	 	 				 	 				
	 	 				log.info("se realizo la multiplicacion correctamante  el resultado es " +  result);
	 	 		 	 	guardarAuditoria("se realizo la multiplicacion correctamante  el resultado es " +  result, Estado.Exitoso.toString(), "operation");

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
	 	 				 	 				
	 	 				log.info("se realizo la Division correctamante  el resultado es " +  result);
	 	 		 	 	guardarAuditoria("se realizo la Division correctamante  el resultado es " +  result, Estado.Exitoso.toString(), "operation");

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
	 	 				 	 				
	 	 				log.info("se realizo la Potencia correctamante  el resultado es " +  result);
	 	 		 	 	guardarAuditoria("se realizo la Potencia correctamante  el resultado es " +  result, Estado.Exitoso.toString(), "operation");

	 	 		 		r.setEstado(Estado.Exitoso.toString());
	 	 				r.setDescripcion("se realizo la Potencia correctamante  el resultado es " +  result);
	 	 				
	 	 				break;
	 	 				
	 	 				default:
	 	 					
		 	 		 	 	guardarAuditoria("Operation enviada no se encuentra disponible " +  operation, Estado.Fallido.toString(), "operation");
	 	 					r.setEstado(Estado.Fallido.toString());
		 	 				r.setDescripcion("Operation enviada no se encuentra disponible (1-Suma, 2-Resta, 3-Multiplicacion, 4-Division, 5-Potencia" +  operation);
 	 				
 	 			}
	 	 			
	 				 
	 			 }else {
	 				 
	 				log.error(" Error: No se encontro la secuencia " + idSecuencia );
 	 		 	 	guardarAuditoria(" Error: No se encontro la secuencia " + idSecuencia , Estado.Fallido.toString(), "operation");
	 				r.setEstado(Estado.Fallido.toString());
	 				r.setDescripcion(" No se encontro la secuencia " + idSecuencia);
	 				return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
	 			 }
 			
 			
	 		
 			
 		}catch (Exception e) {
 			e.printStackTrace();
 			log.error("Error: realizando operacion " + e.getMessage() );
		 	guardarAuditoria("Error: realizando operacion " +  e.getMessage(), Estado.Fallido.toString(), "operation");
 			r.setEstado(Estado.Fallido.toString());
 			r.setDescripcion(e.getMessage());
 			return new ResponseEntity<Respuesta>(r, HttpStatus.INTERNAL_SERVER_ERROR);
		}
 		
 		
 		return new ResponseEntity<Respuesta>(r, HttpStatus.OK);
 		
	
	}
	
	/**
	 * Metodo para realizar registro en BD de auditoria
	 * @param resultado
	 * @param estado
	 * @param operacion
	 * @return
	 */
	private long guardarAuditoria(String resultado, String estado, String operacion){
		
		
		Auditoria a = new Auditoria();
		
		try {
			a.setEstadoOperacion(estado);
			a.setDescripcionResultado(resultado);
			a.setOperacion(operacion);
			auditoria.save(a);
		}catch(Exception e) {
			e.printStackTrace();
 			log.error("Error: realizando registro en auditoria " + e.getMessage() );

		}
			
		
		return a.getId();
		
	}
	
	/**
	 * Metodo para retornar una coleccion de registros de auditoria
	 * @return
	 */
	@GetMapping(value = "/AuditSearch")
    public ResponseEntity<List <Auditoria>> AuditSearch() {
 				  
 		log.info("Ingreso al metodo /AuditSearch" );
 		guardarAuditoria("Ingreso al metodo /AuditSearch", Estado.Exitoso.toString(), "AuditSearch");
 		
        List<Auditoria> auditorias = auditoria.findByAll();
        if (auditorias.isEmpty()) {
            return new ResponseEntity(HttpStatus.NO_CONTENT);
        }
        
        log.info("cantidad de registros encoentrados " + auditorias.size());
        
        return new ResponseEntity<List<Auditoria>>(auditorias, HttpStatus.OK);
        
	
	}
	
	
	
}
