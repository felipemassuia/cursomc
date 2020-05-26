package com.felipe.cursomc.domain.enums;

public enum  EstadoPagamento {

	PENDENTE(1,"Pendente"),
	QUITADO(2,"Quitado"),
	CANCELADO(3,"Cancelado");
	
	private int codigo;
	private String descricao;
	
	private EstadoPagamento(int cod,  String desc) {
		codigo = cod;
		descricao = desc;
		
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static EstadoPagamento toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (EstadoPagamento tc : EstadoPagamento.values()) {
			if(cod.equals(tc.getCodigo())) {
				 return tc;
			}
			
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
			
	}
}
