/* McKenzie Joyce
 * GamePiece.java : An instance of a game piece, mostly implemented for expandability 
 * 	GamePiece() : Creates an game piece that essentially doesnt exist 
 * 	GamePiece(int value) : Creates a game piece with a value
 * 	GamePiece(String rep) : Creates a game piece with a string representation 
 * 	GamePiece(String rep, int value) : Creates a game piece with a string representation and value
 */
public class GamePiece {
	int val;
	String stringRep;
	String name;
	String color;
	
	public GamePiece(){
		val = 0;
		stringRep = null;
		name = "";
		color = "";
	}
	public GamePiece(int value) {
		this();
		val = value;
	}
	public GamePiece(String rep) {
		this();
		stringRep = rep;
	}
	
	public GamePiece(String name, int value) {
		this();
		val = value;
		this.name = name;
	}
	public GamePiece(String rep, String name, int value) {
		this();
		val = value;
		this.name = name;
		setStrRep(rep);
	}
	public void setStrRep(String s) {
		stringRep = s;
	}
	public String toString() {
		String ret = stringRep+ '\n';
		return ret;
	}
	public boolean equals(GamePiece g) {
		boolean ret = true;
		if(this.name.compareToIgnoreCase(g.name)!=0) {
			ret = false;
		}
		if(this.val != g.val) {
			ret = false;
		}
		if(this.stringRep.compareToIgnoreCase(g.stringRep) !=0) {
			ret = false;
		}
		return ret;
	}
	
}
