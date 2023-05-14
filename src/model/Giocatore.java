package model;

import java.util.ArrayList;

public class Giocatore extends Utente{
	
	private ArrayList<Carta> carteSulTavolo;
	private Integer numeroCarteSulTavolo;
	
	public Giocatore (ArrayList<Carta> cartePescate) {
		super("");
		assegnaCarteSulTavolo(cartePescate);
		this.numeroCarteSulTavolo=10;
	}
	
	public void assegnaCarteSulTavolo (ArrayList<Carta> carte) {
		for (Carta carta:carte) {
			this.carteSulTavolo.add(carta);
		}
	}

	public Integer getNumeroCarteSulTavolo() {
		return numeroCarteSulTavolo;
	}

	
	
	
}
