package com.felipe.cursomc.domain.enums;

public enum  Perfil {

	ADMIN(1,"ROLE_ADMIN"),
	CLIENTE(2,"ROLE_CLIENTE");
	
	private int codigo;
	private String descricao;
	
	private Perfil(int cod,  String desc) {
		codigo = cod;
		descricao = desc;
		
	}
	
	public int getCodigo() {
		return codigo;
	}
	
	public String getDescricao() {
		return descricao;
	}
	
	public static Perfil toEnum(Integer cod) {
		
		if (cod == null) {
			return null;
		}
		
		for (Perfil tc : Perfil.values()) {
			if(cod.equals(tc.getCodigo())) {
				 return tc;
			}
			
		}
		
		throw new IllegalArgumentException("Id inválido: " + cod);
			
	}
}
