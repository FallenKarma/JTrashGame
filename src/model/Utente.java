package model;

public class Utente {
	private String nickname;
	private Integer partiteGiocate;
	private Integer partiteVinte;
	private Integer partitePerse;
	private Integer livello;
	
	public Utente (String nickname) {
		this.nickname = nickname;
		this.partiteGiocate = 0;
		this.partitePerse = 0;
		this.partiteVinte = 0;
		this.livello = 0;
	}
	
	public Utente(String nickname, Integer partiteGiocate, Integer partiteVinte, Integer partitePerse,
			Integer livello) {
		this.nickname = nickname;
		this.partiteGiocate = partiteGiocate;
		this.partiteVinte = partiteVinte;
		this.partitePerse = partitePerse;
		this.livello = livello;
	}



	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Integer getPartiteGiocate() {
		return partiteGiocate;
	}
	public void setPartiteGiocate(Integer partiteGiocate) {
		this.partiteGiocate = partiteGiocate;
	}
	public Integer getPartiteVinte() {
		return partiteVinte;
	}
	public void setPartiteVinte(Integer partiteVinte) {
		this.partiteVinte = partiteVinte;
	}
	public Integer getPartitePerse() {
		return partitePerse;
	}
	public void setPartitePerse(Integer partitePerse) {
		this.partitePerse = partitePerse;
	}
	public Integer getLivello() {
		return livello;
	}
	public void setLivello(Integer livello) {
		this.livello = livello;
	}
	
}
