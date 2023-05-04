package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class MazzoDiCarte {
	private ArrayList<Carta> mazzo;
	private Integer carteRimaste;
	
	public MazzoDiCarte () {
		this.mazzo = new ArrayList<>();
		for (Semi seme: Semi.values()) {
			for (Valori valore:Valori.values()) {
				mazzo.add(new Carta(valore,seme));
			}
		}
		this.carteRimaste=52;
	}
	
	public void mischia() {
		Collections.shuffle( this.mazzo);
	}
	
	//TEST
	public void getMazzo() {
		for (Carta carta:this.mazzo) {
			System.out.println(carta.toString());
		}
	}
	
	public Carta pescaUnaCarta () {
		Carta carta = null;
		try {
	        	this.carteRimaste-=1;
	            carta = this.mazzo.remove(0);
	        }
		catch (ArrayIndexOutOfBoundsException e) {
			e.printStackTrace();
		}
		return carta;
		
	}
	
	public Collection<Carta> pesca (Integer carteDaPescare) {
		ArrayList<Carta> carte = new ArrayList<>();
		for (int i=0;i<carteDaPescare;i++) {
			carte.add(pescaUnaCarta());
		}
		return carte;
	}
	
	
}
