package com.kots.sidim.android.model;



public class InteresseClienteId implements java.io.Serializable {

	private String login;
	private int idImovel;

	public InteresseClienteId() {
	}

	public InteresseClienteId(String login, int idImovel) {
		this.login = login;
		this.idImovel = idImovel;
	}

	
	public String getLogin() {
		return this.login;
	}

	public void setLogin(String login) {
		this.login = login;
	}

	
	public int getIdImovel() {
		return this.idImovel;
	}

	public void setIdImovel(int idImovel) {
		this.idImovel = idImovel;
	}

	public boolean equals(Object other) {
		if ((this == other))
			return true;
		if ((other == null))
			return false;
		if (!(other instanceof InteresseClienteId))
			return false;
		InteresseClienteId castOther = (InteresseClienteId) other;

		return ((this.getLogin() == castOther.getLogin()) || (this.getLogin() != null
				&& castOther.getLogin() != null && this.getLogin().equals(
				castOther.getLogin())))
				&& (this.getIdImovel() == castOther.getIdImovel());
	}

	public int hashCode() {
		int result = 17;

		result = 37 * result
				+ (getLogin() == null ? 0 : this.getLogin().hashCode());
		result = 37 * result + this.getIdImovel();
		return result;
	}

}
