import java.util.ArrayList;
import java.util.List;

/* McKenzie Joyce 
 * Feb 2020 
 * BoardTile.java : Create a cell in a board, holds a value, string representation and if there is a token on it (allows expansion to game like chess or checkers)
 * 	BoardTile() : Creates a boardTile with a empty string as a rep and value = 0 (default)
 * 	BoardTile(String name) : Creates a boardTille with string rep = name and value = 0
 *  BoardTile(String name, int num) : Creates a boardTille with string rep = name and value = num
 *  BoardTile(int num) : Creates a boardTille with string rep = " " and value = num
 */
public class BoardTile {
	private String strRep;
	private int value;
	private List<GamePiece> token;
	
	
	//Constructors
	public BoardTile() {
		strRep = emptyTile();
		value = 0;
		token = new ArrayList<GamePiece>();
	}
	public BoardTile(int r, int c) {
		this();
		strRep = emptyTile();
	}
	public BoardTile(GamePiece tok) {
		this();
		setToken(tok);
	}
	public BoardTile(int num) {
		this();
		setValue(num);
	}
	
	
	
	//Accessor Methods
	public String getStrRep() {
		return this.strRep;
	}
	public int getValue() {
		return this.value;
	}
	public GamePiece getToken() {
		if(this.hasToken()) {
			return this.token.get(0);
		}
		return new GamePiece("  ","", 100);
		
	}
	public boolean hasToken() {
		return(token.size() != 0);
	}
	
	
	//Setter Methods



	public void setValue(int v) {
		value = v;
	}
	public void setToken(GamePiece t) {
		token.add(0, t);
		strRep = "|" + t.stringRep + "|";
		value = t.val;
	}
	public void removeToken(GamePiece t) {
		token.remove(t);
		if(token.size() != 0) {
			strRep = "|" + token.get(0).stringRep+ "|";
			value = token.get(0).val;
		}
	}
	
	
	public String emptyTile() {
		String ret = "";
//		for(int h=0; h<height;h++) {
//			ret += "|";
//			for(int w=0; w<width;w++) {
//				ret += " ";
//			}
//			ret += "|";
//		}
		return ret;
	}
	public String toString() {
		return strRep;
	}
}






