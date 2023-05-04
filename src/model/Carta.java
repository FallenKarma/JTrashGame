package model;

public class Carta {
	
	   private Semi seme;
	   private Valori valore;

	public Carta (Valori valore,Semi seme)  {
	    this.seme = seme;
	    this.valore = valore;
	}

	@Override
	public String toString() {
		return valore+"di"+seme;
	}
	
}
