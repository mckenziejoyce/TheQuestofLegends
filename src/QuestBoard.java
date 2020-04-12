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
	private GamePiece monsterToken = new GamePiece("*M*","MonsterOne",5);
	//private GamePiece[] monsterTokens = {new GamePiece("*M*","MonsterOne",5),new GamePiece("*M*","MonsterTwo",5),new GamePiece("*M*","MonsterThree",5)};
	// Each hero has an array [row index,col index]
	private int numOfHeroes = 3;
	private int[][] heroCoords = new int[numOfHeroes][2];
	private int[][] prevHeroCoords = new int[numOfHeroes][2];
	private int[][] monsterCoords;
	private int curHero = 0;
	private int curMonster = 0;
	private GamePiece nonAccessible = new GamePiece("XXX","NonAcc",-1);
	private GamePiece HeroNexus = new GamePiece(" N ","Nexus",11);
	private GamePiece monsterNexus = new GamePiece(" N ","Nexus",12);
	private GamePiece Plain = new GamePiece(" P ","Plain",3);
	private GamePiece Bush = new GamePiece(" B ","Bush",4);
	private GamePiece Koulou = new GamePiece(" K ","Koulou",5);
	private GamePiece Cave = new GamePiece(" C ","Cave",4);
	
	
	public QuestBoard() {
		super(8);
		setHeight(8);
		setWidth(8);
		heroes = new ArrayList<Hero>();
		monsters = new ArrayList<Monster>();
		monsterCoords = new int[3][2];
		int totalTile = this.getHeight()*this.getWidth();
		for(int i = 0; i < this.getHeight(); i++) {
			layout[i][nonAccColNum1].setToken(nonAccessible);
			layout[i][nonAccColNum2].setToken(nonAccessible);
		}
		for(int i = 0; i < this.getWidth(); i++) {
			if(!layout[0][i].hasToken()) {
				layout[0][i].setToken(monsterNexus);
				layout[this.getHeight()-1][i].setToken(HeroNexus);
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

	public int[][] getHeroCoords() {
		return heroCoords;
	}

	public Hero getCurHero() {
		return heroes.get(curHero);
	}

	public void setCurHero(int num) {
		if(num>= heroes.size() || num < 0) {
			throw new IllegalArgumentException();
		}
		curHero = num;
	}
	
	public Monster getCurMonster() {
		return monsters.get(curMonster);
	}

	public void setCurMonster(int num) {
		if(num>= monsters.size() || num < 0) {
			throw new IllegalArgumentException();
		}
		curMonster = num;
	}
	
	public void nextTurn() {
		if(curHero+1 >= heroes.size()) {
			setCurHero(0);
		}
		else {
			setCurHero(curHero+1);
		}
	}
	
	public void nextMonster() {
		if(curMonster+1 >= monsters.size()) {
			setCurMonster(0);
		}
		else {
			setCurMonster(curMonster+1);
		}
	}
	
	public void prevTurn() {
		if(curHero-1 < 0) {
			setCurHero(heroes.size()-1);
		}
		else {
			setCurHero(curHero-1);
		}
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
	
	public boolean monsterNearby() {
		boolean near = false;
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		boolean sameTile, inFront = false , nextTo = false;
		sameTile = hasMonster(heroRow, heroCol);
		if(heroRow-1 >= 0) {
			inFront = hasMonster(heroRow-1, heroCol);
		}
		if(heroCol-1 >= 0) {
			nextTo = hasMonster(heroRow, heroCol-1);
		}
		if(nextTo == false && heroCol +1 < this.getWidth()) {
			nextTo = hasMonster(heroRow, heroCol+1);
		}
		return (sameTile || nextTo || inFront);
	}
	
	public Monster getMonsterNearby() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		int monsterRow = 0;
		int monsterCol = 0;
		boolean sameTile, inFront = false , nextTo = false;
		sameTile = hasMonster(heroRow, heroCol);
		int[] arr = {-1,-1};
		if(sameTile) {
			arr[0] = heroRow;
			arr[1] = heroCol;
			return getMonsterFromCoord(arr);
		}
		if(heroRow-1 >= 0) {
			inFront = hasMonster(heroRow-1, heroCol);
			if(inFront) {
				arr[0] = heroRow-1;
				arr[1] = heroCol;
				return getMonsterFromCoord(arr);
			}
		}
		if(heroCol-1 >= 0) {
			nextTo = hasMonster(heroRow, heroCol-1);
			if(nextTo) {
				arr[0] = heroRow;
				arr[1] = heroCol-1;
				return getMonsterFromCoord(arr);
			}
		}
		if(nextTo == false && heroCol +1 < this.getWidth()) {
			nextTo = hasMonster(heroRow, heroCol+1);
			if(nextTo) {
				arr[0] = heroRow;
				arr[1] = heroCol+1;
				return getMonsterFromCoord(arr);
			}
		}
		return new Dragon();
		
	}
	
	public GamePiece getCurTileType() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		if(this.layout[heroRow][heroCol].hasToken()) {
			 List<GamePiece> toks = this.layout[heroRow][heroCol].getTokens();
			 for(int i = 0; i< toks.size(); i++) {
				 if(toks.get(i).equals(HeroNexus)) {
					 return HeroNexus;
				 }
				 if(toks.get(i).equals(monsterNexus)) {
					 return monsterNexus;
				 }
				 if(toks.get(i).equals(Plain)) {
					 return Plain;
				 }
				 if(toks.get(i).equals(Cave)) {
					 return Cave;
				 }
				 if(toks.get(i).equals(Bush)) {
					 return Bush;
				 }
				 if(toks.get(i).equals(Koulou)) {
					 return Koulou;
				 }
				 if(toks.get(i).equals(nonAccessible)) {
					 return nonAccessible;
				 }
				 
			 }
		}
		return Plain;
	}
	
	public int getCurHeroIndex() {
		return curHero;
	}
	
	public void deleteMonsterFromBoard(Monster m) {
		int inx = monsters.indexOf(m);
		int monsterRow = monsterCoords[inx][0];
		int monsterCol = monsterCoords[inx][1];
		monsters.remove(m);
		this.layout[monsterRow][monsterCol].removeToken(monsterToken);
		int s = monsterCoords.length;
		int[][] temp = new int[monsters.size()][2];
		int j = 0;
		for(int i = 0; i<s; i++) {
			if(i != inx) {
				temp[j] = monsterCoords[i];
				j++;
			}
		}
		monsterCoords = temp;
		
	}
	
	public Monster getMonsterFromCoord(int[] coords) {
		for(int i = 0; i< monsters.size(); i++) {
			if(monsterCoords[i][0] == coords[0] && monsterCoords[i][1] == coords[1]) {
				return monsters.get(i);
			}
		}
		return new Dragon();
	}
	
	public boolean hasMonster(int row, int col) {
		boolean ret = false;
		if(!layout[row][col].hasToken()) {
			return false;
		}
		List<GamePiece> tokens = layout[row][col].getTokens();
		for(int i = 0; i< tokens.size(); i++) {
			if(tokens.get(i).equals(monsterToken)) {
				ret = true;
			}
		}
		return ret;
	}
	
	public boolean hasHero(int row, int col) {
		boolean ret = false;
		if(!layout[row][col].hasToken()) {
			return false;
		}
		List<GamePiece> tokens = layout[row][col].getTokens();
		for(int i = 0; i< tokens.size(); i++) {
			for(int j = 0; j< heroTokens.length; j++) {
				if(tokens.get(i).equals(heroTokens[j])) {
					ret = true;
				}
			}
		}
		return ret;
	}

	public void spawnHeroes(List<Hero> h) {
		heroes = h;
		int j=0;
		for(int i=0; i<3;i++) {
			GamePiece token = heroTokens[i];
			// setting row val 
			heroCoords[i][0] = this.getHeight()-1;
			//Setting col val 
			heroCoords[i][1] = j;
			// Sets the next hero three cols apart 
			layout[this.getHeight()-1][j].setToken(token);
			j += 3;
			prevHeroCoords[i] = heroCoords[i];
		}
	}

	public void spawnMonsters(List<Monster> m) {
		addNewMonsters(m);
	}

	public void setGame(TheQuestOfLegends gameState) {
		game = gameState;
	}

	public void undo() {
		if(lastMove.compareToIgnoreCase("up")==0) {
			//prevTurn();
			moveDown();
			//moveBack();
		}
		else if(lastMove.compareToIgnoreCase("down")==0) {
			//prevTurn();
			moveUp();
			//moveBack();
		}
		else if(lastMove.compareToIgnoreCase("left")==0) {
			//prevTurn();
			moveRight();
			//moveBack();
		}
		else if(lastMove.compareToIgnoreCase("right")==0) {
			//prevTurn();
			moveLeft();
			
		}
		else {
			moveBack();
		}
		
	}
	
	public List<Monster> getMonsters(){
		return monsters;
	}
	
	public void monsterMove() {
		int monsterRow = monsterCoords[curMonster][0];
		int monsterCol = monsterCoords[curMonster][1];
		if(!hasMonster(monsterRow+1,monsterCol)) {
			if(monsterRow+1 >= this.getHeight()) {
				throw new IllegalArgumentException();
			}
			this.layout[monsterRow][monsterCol].removeToken(monsterToken);
			monsterCoords[curMonster][0] = monsterRow+1;
			this.layout[monsterRow+1][monsterCol].setToken(monsterToken);
			return;
		}
		return;
	}
	
	public GamePiece moveBack() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		int prevRow = prevHeroCoords[curHero][0];
		int prevCol = prevHeroCoords[curHero][1];
		GamePiece heroToken = heroTokens[curHero];
		this.layout[heroRow][heroCol].removeToken(heroToken);
		GamePiece token = this.layout[prevRow][prevCol].getToken();
		this.layout[prevRow][prevCol].setToken(heroToken);
		heroCoords[curHero] = prevHeroCoords[curHero];
		return token;
	}
	
	public GamePiece moveUp() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		if(heroRow-1 < 0) {
			throw new IllegalArgumentException(); }
		if(!hasHero(heroRow-1,heroCol)) {
			GamePiece heroToken = heroTokens[curHero];
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[heroRow-1][heroCol].getToken();
			this.layout[heroRow-1][heroCol].setToken(heroToken);
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][0] = heroRow-1;
			lastMove = "up";
			return token;
		}
		else {
			return nonAccessible;
		}
	}

	public GamePiece moveDown() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		if(heroRow+1 >= this.getHeight()) {
			throw new IllegalArgumentException();
		}
		if(!hasHero(heroRow+1,heroCol)) {
			GamePiece heroToken = heroTokens[curHero];
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[heroRow+1][heroCol].getToken();
			this.layout[heroRow+1][heroCol].setToken(heroToken);
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][0] = heroRow+1;
			lastMove = "down";
			return token;
		}
		else {
			return nonAccessible;
		}
	}
	
	public GamePiece moveLeft() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		GamePiece heroToken = heroTokens[curHero];
		if(heroCol-1 < 0) {
			throw new IllegalArgumentException();
		}
		if(!hasHero(heroRow,heroCol-1)) {
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[heroRow][heroCol-1].getToken();
			this.layout[heroRow][heroCol-1].setToken(heroToken);
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][1] = heroCol-1;
			lastMove = "left";
			return token;
		}
		else {
			return nonAccessible;
		}
	}

	public GamePiece moveRight() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		GamePiece heroToken = heroTokens[curHero];
		if(heroCol+1 >= this.getWidth()) {
			throw new IllegalArgumentException();
		}
		if(!hasHero(heroRow,heroCol+1)) {
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[heroRow][heroCol+1].getToken();
			this.layout[heroRow][heroCol+1].setToken(heroToken);
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][1] = heroCol+1;
			lastMove = "right";
			return token;
		}
		else {
			return nonAccessible;
		}
	}

	
	public GamePiece returnToHeroNexus() {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		if(!hasHero(this.getHeight()-1,heroCol)) {
			GamePiece heroToken = heroTokens[curHero];
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[this.getHeight()-1][heroCol].getToken();
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][0] = this.getHeight()-1;
			this.layout[this.getHeight()-1][heroCol].setToken(heroToken);
			lastMove = "nexus";
			return token;
		}
		else {
			return nonAccessible;
		}
	}
	
	public GamePiece teleport(int col) {
		int heroRow = heroCoords[curHero][0];
		int heroCol = heroCoords[curHero][1];
		boolean sameLane = (col == heroCol+1) || (col == heroCol-1);
		if(!hasHero(heroRow,col) && !sameLane) {
			GamePiece heroToken = heroTokens[curHero];
			this.layout[heroRow][heroCol].removeToken(heroToken);
			GamePiece token = this.layout[heroRow][col].getToken();
			prevHeroCoords[curHero] = heroCoords[curHero];
			heroCoords[curHero][1] = col;
			this.layout[heroRow][col].setToken(heroToken);
			lastMove = "teleport";
			return token;
		}
		else {
			return nonAccessible;
		}
	}
	
	public void addNewMonsters(List<Monster> m) {
		int s = monsters.size();
		monsters.addAll(m);
		int[][] temp = new int[s+m.size()][2];
		for(int i = 0; i<s; i++) {
			temp[i] = monsterCoords[i];
		}
		int j=0;
		for(int i=0; i<3;i++) {
			GamePiece token = monsterToken;
			// setting row val then col
			temp[i+s][0] = 0;
			temp[i+s][1] = j;
			// Sets the next monster three cols apart 
			layout[0][j].setToken(token);
			j += 3;
		}
		monsterCoords = temp;
	}
	
	public boolean checkHeroWin() {
		boolean win = false;
		for(int i=0; i< heroCoords.length; i++) {
			if(heroCoords[i][0] == 0) {
				win = true;
			}
		}
		return win;
	}
	
	public boolean checkMonsterWin() {
		boolean win = false;
		for(int i=0; i< monsterCoords.length; i++) {
			if(monsterCoords[i][0] == this.getHeight()-1) {
				win = true;
			}
		}
		return win;
	}
	
	
}
