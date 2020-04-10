/* Mckenzie Joyce 
 * Feb 2020 
 * Board.java : An instance of a board with a layout for what the current board looks like, and the height and width of the board
 * 		Board() : Creates a board with layout set to null, height and width set to 0 (defaults)
 * 		Board(int size) : Creates an empty square board of size x size 
 * 		Board(int height, int width) : Creates an empty height x width sized board 
 * 		checkFull() : Checks if the board is full returns true if there are no empty cells 
 * 		printBoard() : Prints a string representation of the current board 
 */
public class Board {
	protected BoardTile[][] layout;
	private int height;
	private int width;
	private int tileW = 3;
	
	
	//Constructors 
	public Board() {
		layout = null;
		height=0;
		width=0;
	}
	public Board(int size){
		this();
		setHeight(size);
		setWidth(size);
		layout = new BoardTile[size][size];
		for(int i=0; i<height; i++) {
			for(int j=0; j<width; j++) {
				layout[i][j] = new BoardTile();
			}
		}
	}
	public Board(int h, int w){
		this();
		setHeight(h);
		setWidth(w);
		layout = new BoardTile[h][w];
		for(int i=0; i<h; i++) {
			for(int j=0; j<w; j++) {
				layout[i][j] = new BoardTile();
			}
		}
	}
	
	//Accessor Methods
	public BoardTile[][] getLayout(){
		return layout;
	}
	public int getHeight() {
		return this.height;
	}
	public int getWidth() {
		return this.width;
	}
	
	//Setter Methods
	public void setLayout(BoardTile[][] layout) {
		this.layout = layout;
	}
	public void setHeight(int n) {
		if(n < 0) {
			throw new IllegalArgumentException();}
		height = n;
	}
	public void setWidth(int n) {
		if(n < 0) {
			throw new IllegalArgumentException(); }
		width = n;
	}
	
	
	//Checks if there is a full board 
	public boolean checkFull() {
		boolean full = true;
		for(int i =0; i<height; i++) {
			for(int j =0; j<width; j++) {
				if(layout[i][j].hasToken() == false) {
					full = false;
				}
			}	
		}
		return full;
	}
	

	public String toString() {
		String ret = "";
		String bar = "\n";
		String padding = "";
		for(int j = 0; j < width; j++) {
			padding += "|";
			for(int i =0; i < tileW;i++) {
				padding += " ";
			}
			padding += "|";
		}
		
		//padding += "";
		for(int i =0; i < ((tileW+2) * width); i++) {
			bar += "-";
		}
		ret += bar;
		for(BoardTile[] row : layout) {
			ret +='\n';
			ret += padding;
			ret +='\n';
			for(BoardTile col: row) {
				
				if(col.hasToken()) {
					GamePiece tok = col.getToken();
					ret += "|" + tok.stringRep + "|";
				}
				else {
					
					ret += "|";
					for(int i =0; i < tileW;i++) {
						ret += " ";
					}
					ret += "|";
					
					
				}
				
			}
			ret += "\n"+padding;
			ret += bar;
			
		}
		return ret;
	}
	public void printBoard() {
		System.out.println(this.toString());
	}
}
