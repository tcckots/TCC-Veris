package com.kots.sidim.android.model;

public class TipoImovelMobile extends TipoImovel {
	
	private boolean check;
	
	public TipoImovelMobile() {}
	
	public TipoImovelMobile(short idTipo, String descricao, boolean check) {
		super(idTipo,descricao);
		this.check = check;
	}

	public boolean isCheck() {
		return check;
	}

	public void setCheck(boolean check) {
		this.check = check;
	}
	
	

}
