package main.java.org.ever;

public class Field {

	private String name;
	private String value;
	private boolean isRevealed;
	private boolean isFlagged;
	private boolean isMine;
	
	public Field(String name) {
		this.name = name;
		this.isRevealed = false;
		this.isFlagged = false;
		this.isMine = false;
		this.value = "0";
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public boolean isRevealed() {
		return isRevealed;
	}
	public void setRevealed(boolean isRevealed) {
		this.isRevealed = isRevealed;
	}
	public boolean isFlagged() {
		return isFlagged;
	}
	public void setFlagged(boolean isFlagged) {
		this.isFlagged = isFlagged;
	}
	public boolean isMine() {
		return isMine;
	}
	public void setMine(boolean isMine) {
		this.isMine = isMine;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
