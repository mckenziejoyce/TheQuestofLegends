import java.util.Random;

public class QuestBoard extends Board{
	private double perNonAcc = .2;
	private double perMarket = .2;
	private double perCommon = .3;
	
	private int heroRow;
	private int heroCol;
	private String lastMove;
	
	
	private GamePiece nonAccessible = new GamePiece(" N ","NonAcc",-1);
	private GamePiece market = new GamePiece(" M ", "Market", 10);
	private GamePiece common = new GamePiece(" C ","Common", 3);
	private GamePiece heroTeam = new GamePiece(" * ","Heroes", 5);
	private GamePiece empty = new GamePiece("  ","", 100);
	
	
	public QuestBoard() {
		super(8);
	}
	public QuestBoard(int height, int width) {
		this();
		setHeight(height);
		setWidth(width);
		int totalTile = height*width;
		int numOfMarket = (int)(totalTile*perMarket);
		int numOfNonAcc = (int)(totalTile*perNonAcc);
		int numOfCommon = (int)(totalTile*perCommon);
		int h,w;
		for(int i =0; i <numOfMarket;i++) {
			h = (int)(Math.random() * (height));
			w = (int)(Math.random() * (width));
			if(layout[h][w].hasToken() == false) {
				layout[h][w].setToken(market);
			}
			else {
				i--;
			}
		}
		for(int i =0; i <numOfNonAcc;i++) {
			h = (int)(Math.random() * (height));
			w = (int)(Math.random() * (width));
			if(layout[h][w].hasToken() == false) {
				layout[h][w].setToken(nonAccessible);
			}
			else {
				i--;
			}
			
		}
		for(int i =0; i <numOfCommon;i++) {
			h = (int)(Math.random() * (height));
			w = (int)(Math.random() * (width));
			if(layout[h][w].hasToken() == false) {
				layout[h][w].setToken(common);
			}
			else {
				i--;
			}
			
		}
		this.layout[height-1][width-1].setToken(heroTeam);
		heroRow = height-1;
		heroCol = width-1;
	}
	public void undo() {
		if(lastMove.compareToIgnoreCase("up")==0) {
			moveDown();
		}
		else if(lastMove.compareToIgnoreCase("down")==0) {
			moveUp();
		}
		else if(lastMove.compareToIgnoreCase("left")==0) {
			moveRight();
		}
		else if(lastMove.compareToIgnoreCase("right")==0) {
			moveLeft();
		}
	}
	public GamePiece moveUp() {
		if(heroRow-1 < 0) {
			throw new IllegalArgumentException();
		}
		this.layout[heroRow][heroCol].removeToken(heroTeam);
		GamePiece token = this.layout[heroRow-1][heroCol].getToken();
		this.layout[heroRow-1][heroCol].setToken(heroTeam);
		heroRow = heroRow-1;
		lastMove = "up";
		return token;
	}
	public GamePiece moveDown() {
		if(heroRow+1 >= this.getHeight()) {
			throw new IllegalArgumentException();
		}
		this.layout[heroRow][heroCol].removeToken(heroTeam);
		GamePiece token = this.layout[heroRow+1][heroCol].getToken();
		this.layout[heroRow+1][heroCol].setToken(heroTeam);
		heroRow = heroRow+1;
		lastMove = "down";
		return token;
	}
	public GamePiece moveLeft() {
		if(heroCol-1 < 0) {
			throw new IllegalArgumentException();
		}
		this.layout[heroRow][heroCol].removeToken(heroTeam);
		GamePiece token = this.layout[heroRow][heroCol-1].getToken();
		this.layout[heroRow][heroCol-1].setToken(heroTeam);
		heroCol = heroCol-1;
		lastMove = "left";
		return token;
	}
	public GamePiece moveRight() {
		if(heroCol+1 >= this.getWidth()) {
			throw new IllegalArgumentException();
		}
		this.layout[heroRow][heroCol].removeToken(heroTeam);
		GamePiece token = this.layout[heroRow][heroCol+1].getToken();
		this.layout[heroRow][heroCol+1].setToken(heroTeam);
		heroCol = heroCol+1;
		lastMove = "right";
		return token;
	}
	
	
	
}
