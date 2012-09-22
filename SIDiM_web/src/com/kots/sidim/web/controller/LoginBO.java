package com.kots.sidim.web.controller;

import com.kots.sidim.web.model.Funcionario;

public class LoginBO {

	
	public LoginBO() {
		
	}

	public Boolean validarLogin(Funcionario func) {
		Boolean result = false;
		
		if (verificarFuncionarioExiste(func)) {
			result = false;
			String password = func.getSenha();
			FuncionarioBO funcionarioBO = new FuncionarioBO();
			func = funcionarioBO.obter(func.getCpf());
			if (func.getSenha().equals(password)) {
				//set session
				result = true;
			}
		}
		
		return result;
	}

	public Boolean verificarFuncionarioExiste(Funcionario func) {
		Boolean result = false;
		if (func!=null) {
			if (!func.getCpf().isEmpty()) {
				FuncionarioBO funcionarioBO = new FuncionarioBO();
				func = funcionarioBO.obter(func.getCpf());
				if (func!=null) {
					if (!func.getCpf().isEmpty()) {
						result = true;
					}
				}
			}
		}
		return result;
	}

}
