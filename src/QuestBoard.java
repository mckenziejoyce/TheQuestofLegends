import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class QuestBoard extends Board{
	private double perBush = .1;
	private double perKoulou = .1;
	private double perCave = .1;
	
	
	private String lastMove;
	private int nonAccColNum1 = 2;
	private int nonAccColNum2 = 5;
	private TheQuestOfLegends game;
	private List<Hero> heroes;
	private List<Monster> monsters;
	private GamePiece[] heroTokens = {new GamePiece("*H1","HeroOne",5),new GamePiece("*H2","HeroTwo",5),new GamePiece("*H3","HeroThree",5)};
	private GamePiece[] monsterTokens = {new GamePiece("*M1","MonsterOne",5),new GamePiece("*M2","MonsterTwo",5),new GamePiece("*M3","MonsterThree",5)};
	private int[][] heroCoords = new int[3][2];
	private int curHero = 0;
	private GamePiece nonAccessible = new GamePiece("XXX","NonAcc",-1);
	private GamePiece Nexus = new GamePiece(" N ","Nexus",11);
	private GamePiece Plain = new GamePiece(" P ","Plain",3);
	private GamePiece Bush = new GamePiece(" B ","Bush",4);
	private GamePiece Koulou = new GamePiece(" K ","Koulou",5);
	private GamePiece Cave = new GamePiece(" C ","Cave",4);
	
	
	//These will need to be commented out when we change the implementation of move but 
	// I'm just keeping them so it compiles without complaint 
	private int heroRow;
	private int heroCol;
	private GamePiece heroTeam = new GamePiece(" * ","Heroes", 5);
	
	
	public QuestBoard() {
		super(8);
		setHeight(8);
		setWidth(8);
		int totalTile = this.getHeight()*this.getWidth();
		for(int i = 0; i < this.getHeight(); i++) {
			layout[i][nonAccColNum1].setToken(nonAccessible);
			layout[i][nonAccColNum2].setToken(nonAccessible);
		}
		for(int i = 0; i < this.getWidth(); i++) {
			if(!layout[0][i].hasToken()) {
				layout[0][i].setToken(Nexus);
				layout[this.getHeight()-1][i].setToken(Nexus);
			}
		}
		int numOfKoulou = (int)(totalTile*perKoulou);
		int numOfBush = (int)(totalTile*perBush);
		int numOfCave = (int)(totalTile*perCave);
		setTiles(numOfKoulou, Koulou);
		setTiles(numOfBush, Bush);
		setTiles(numOfCave, Cave);
		for(int i=0; i<this.getHeight(); i++) {
			for(int j=0; j< this.getWidth();j++) {
				if(layout[i][j].hasToken() == false) {
					layout[i][j].setToken(Plain);
				}
			}
		}
	
	}
	public QuestBoard(TheQuestOfLegends gameState) {
		this();
		setGame(gameState);
		heroes = game.getHeroes();
		monsters = game.getMonsters();
		for(int i = 0; i< 3; i++) {
			
		}
//		for(int i=0; i<3;i++){
//			layout[][].setToken(heroes.get(i));
//			layout[][].setToken(monsters.get(i));
//			
//		}
	}
	public void setTiles(int num, GamePiece tok) {
		int h,w;
		for(int i =0; i <num;i++) {
			h = (int)(Math.random() * (this.getHeight()));
			w = (int)(Math.random() * (this.getWidth()));
			if(layout[h][w].hasToken() == false) {
				layout[h][w].setToken(tok);
			}
			else {
				i--;
			}
		}
	}

	public void setGame(TheQuestOfLegends gameState) {
		game = gameState;
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
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
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
