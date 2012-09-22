package com.kots.sidim.web.view;

import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;

import com.kots.sidim.web.controller.FuncionarioBO;
import com.kots.sidim.web.controller.LoginBO;
import com.kots.sidim.web.model.Funcionario;
import com.kots.sidim.web.util.GoogleMail;

@ManagedBean(name = "loginView")
@SessionScoped
public class LoginView {

	public LoginView() {
		// TODO Auto-generated constructor stub
		loginBO = new LoginBO();
		if (func == null)
			func = new Funcionario();
	}

	Funcionario func;
	public Funcionario getFunc() {
		return func;
	}

	public void setFunc(Funcionario func) {
		this.func = func;
	}

	LoginBO loginBO;
	FacesContext context = null;

	private static final String MENSAGEM_ERRO_LOGIN = "Login ou Senha inválidos";
	private UIComponent btnloginFuncionario;
	private UIComponent lkEnviarSenha;

	public UIComponent getLkEnviarSenha() {
		return lkEnviarSenha;
	}

	public void setLkEnviarSenha(UIComponent lkEnviarSenha) {
		this.lkEnviarSenha = lkEnviarSenha;
	}

	public UIComponent getBtnloginFuncionario() {
		return btnloginFuncionario;
	}

	public void setBtnloginFuncionario(UIComponent btnloginFuncionario) {
		this.btnloginFuncionario = btnloginFuncionario;
	}

	public String logarFuncionario() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;
		if (loginBO.validarLogin(func)) {
			return "Home";
		} else {
			message = new FacesMessage(MENSAGEM_ERRO_LOGIN);
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage(btnloginFuncionario.getClientId(context), message);		
		}
		return null;
	}

	public String enviarSenha() {
		FacesContext context = FacesContext.getCurrentInstance();
		FacesMessage message = null;

		try {
			if (loginBO.verificarFuncionarioExiste(func)) {
				
				FuncionarioBO funcionarioBO = new FuncionarioBO();
				func = funcionarioBO.obter(func.getCpf());
				
				String emailDestino = func.getEmail();
				
				if (emailDestino==null || emailDestino.isEmpty()) {
					message = new FacesMessage("Usuário não possuí e-mail cadastrado.");
				} else {
					System.out.println("email destino: " + emailDestino);
					GoogleMail.Send("tcckots", "xoupsforton1", emailDestino, "Agora foi hen", "Sua senha é " + func.getSenha());
					message = new FacesMessage("Senha enviada para e-mail cadastrado");
				}
			} else {
				message = new FacesMessage("Login inválido");
			}
			message.setSeverity(FacesMessage.SEVERITY_WARN);
			context.addMessage("loginForm:lkEnviarSenha", message);

		} catch (Exception e) {
			// TODO: handle exception
			message = new FacesMessage("Erro interno na aplicação, entre em contato com o suporte do sistema por favor");
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			context.addMessage("loginForm:lkEnviarSenha", message);
		}
			
		return null;
	}
}
