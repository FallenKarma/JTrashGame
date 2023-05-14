package model;

import java.util.ArrayList;
import java.util.Collection;

public class Partita {
	
	private Integer numeroGiocatori;
	private ArrayList<MazzoDiCarte> mazziDiCarte;
	
	public Partita (Integer numeroGiocatori) {
		this.numeroGiocatori = numeroGiocatori;
		inizializzaMazzi(numeroGiocatori);
	}
	
	public void inizializzaMazzi (Integer numeroGiocatori) {
		for (int i=0;i<numeroGiocatori;i++) {
			this.mazziDiCarte.add(new MazzoDiCarte());
		}
	}
	
	
	
}
