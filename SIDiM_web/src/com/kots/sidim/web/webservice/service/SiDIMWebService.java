package com.kots.sidim.web.webservice.service;



import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;

import org.eclipse.jdt.internal.compiler.problem.AbortCompilationUnit;

import com.kots.sidim.web.webservice.model.ContaCliente;
import com.kots.sidim.web.webservice.model.FiltroImovel;
import com.kots.sidim.web.webservice.model.InteresseCliente;
import com.kots.sidim.web.webservice.model.ResultWebService;





@Path("/service")
@Produces("application/json"+";charset=UTF-8")
public class SiDIMWebService {

	
//	@POST
//	@Path("/data")    
//	public String getInfoMovie(@FormParam("urlPage") String urlPage) {				
//		
//		return "model JSON";
//	
//	}
//	
//	@POST
//	@Path("/data/theaters")    
//	public String getInfoMovieWithTheaters(@FormParam("urlPage") String urlPage) {				
//		
//		return "model JSON";
//	
//	}
//	
//
//	@GET
//	@Path("/teste")
//	public String getTest(@Context HttpServletResponse response) {
//		
//		return "model JSON";
//		
//	}
	
	@GET
	@Path("/teste")
	public ResultWebService getTest(@Context HttpServletResponse response) {
		ResultWebService result = new ResultWebService();
		result.setSuccess(true);
		result.setMensagem("Xupa meu pmpow");
		
		return result;
		
	}
	
	
	
	@POST
	@Path("/validarLogin")  
	@Consumes(MediaType.APPLICATION_JSON)
	public ContaCliente validarLogin(ContaCliente conta) {				
		
		// validacao
		//get ContaCLiente com conta.getLogin()
		
		System.out.println(conta.getLogin());
		System.out.println(conta.getSenha());
		
		conta.setSuccess(true);
		conta.setMensagem("Ok");
		
		return conta;
	
	}
	
	@POST
	@Path("/criarConta")    
	@Consumes(MediaType.APPLICATION_JSON)
	public String criarAtualizarConta(ContaCliente conta) {				
		
		return "response JSON";
	
	}
	
	
	@POST
	@Path("/atualizarConta")    
	@Consumes(MediaType.APPLICATION_JSON)
	public String atualizarConta(ContaCliente conta) {				
		
		//Os campos que estiverem em branco no objeto, não deven ser atualizados na base
		// vc pode fazer isso buscando as infos no banco e preenchendo os dados em branco
		
		return "response JSON";
	
	}
	
	
	
	@POST
	@Path("/enviarIntetresse")    
	@Consumes(MediaType.APPLICATION_JSON)
	public String enviarInteresse(InteresseCliente interesse) {				
		//nao salvar se jah existe
		return "response JSON";
	
	}
	
	@POST
	@Path("/buscarImoveis")    
	@Consumes(MediaType.APPLICATION_JSON)
	public String buscarImoveis(FiltroImovel filtro) {				
		
		return "response JSON";
	
	}
}
