import java.io.*; 
import java.util.*;
import java.math.*;

public class TheQuestOfLegends {
	private String herotxt;
	private String monstertxt;
	private List<Hero> heroes;
	private List<Monster> monsters;
	private QuestBoard world;
	Scanner userResponse = new Scanner( System.in );
	private GamePiece nonAccessible = new GamePiece("XXX","NonAcc",-1);
	private GamePiece heroOne = new GamePiece("*H1","HeroOne",5);
	private GamePiece heroTwo = new GamePiece("*H2","HeroTwo",5);
	private GamePiece heroThree = new GamePiece("*H3","HeroThree",5);
	private GamePiece monsterOne = new GamePiece(" M1","MonsterOne",5);
	private GamePiece monsterTwo = new GamePiece(" M2","MonsterTwo",5);
	private GamePiece monsterThree = new GamePiece(" M3","MonsterThree",5);
	private GamePiece Nexus = new GamePiece(" N ","Nexus",11);
	private GamePiece Plain = new GamePiece(" P ","Plain",3);
	private GamePiece Bush = new GamePiece(" B ","Bush",4);
	private GamePiece Koulou = new GamePiece(" K ","Koulou",5);
	private GamePiece Cave = new GamePiece(" C ","Cave",4);
	private int fightProb = 75; //delete
	boolean inFight;
	private int playersPerTeam = 3;
	Random rand = new Random();
	boolean play; //true when game is still being played
	
	TheQuestOfLegends(){
		herotxt = "src/heroes.txt";
		monstertxt = "src/monsters.txt";
		heroes = new ArrayList<Hero>();
		monsters = new ArrayList<Monster>();
		world = new QuestBoard();
		inFight = false;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		TheQuestOfLegends game = new TheQuestOfLegends();
		game.printBoard();
		game.startGame();
		
	}
	//Testing method 
	public void printBoard() throws FileNotFoundException {
		//List<Hero> heroOptions = generateHereos();
		//setMonsters(generateMonsters());
		//heroes.add(heroOptions.get(3));
		//heroes.add(heroOptions.get(6));
		//heroes.add(heroOptions.get(5));
		System.out.println(this.world);
	}
	public void playGame() throws FileNotFoundException{
		play = true;
		startGame();
	}
	public void startGame() throws FileNotFoundException {
		//System.out.println(ANSI_RED +"Welcome to the Quest!"+ ANSI_RESET);
		System.out.println("        ,     \\    /      ,        \n" + 
				"       / \\    )\\__/(     / \\       \n" + 
				"      /   \\  (_\\  /_)   /   \\      \n" + 
				" ____/_____\\__\\@  @/___/_____\\____ \n" + 
				"|             |\\../|              |\n" + 
				"|              \\VV/               |\n" + 
				"| Welcome to The Quest of Legends  |\n" + 
				"|__________________________________|\n" + 
				" |    /\\ /      \\\\       \\ /\\    | \n" + 
				" |  /   V        ))       V   \\  | \n" + 
				" |/     `       //        '     \\| \n" + 
				" `              V                '\n" + 
				"");
		System.out.println("First you must select your heroes");
		chooseHeroes();
		chooseMonsters();
		System.out.println("Great now lets go over some logistics!");
		System.out.println("Commands: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right\n"+"I/i: View information about your heroes (or if you are in a fight the monsters as well)\n"+
				"Q/q: Quit the game\n"+"V/v: Display inventories\n"+"C/c: Change weapon or armor\n"+"P/p: Consume Potion\n"+"M/m: Display the Map\n");
		System.out.println("When looking at the map you will see C for common tiles, M for marketplaces, N for tiles you cant visit, and a * to show where you are");
		System.out.println("Now that we've covered the basic rules lets start playing");
		System.out.println(world);

	}
	//goes through all three heros selecting their moves in rounds 
	public void rounds() {
		while(play){
			for (int i=0; i< world.getHeroCoords().length; i++){
				world.curHero= i;
				move(world.getHeroCoords()[i]);
	}
	}
}

	//hero choses move
	public boolean move(int[] heroPos) {
		System.out.println("Make your move");
		boolean valid = false;
		GamePiece tileType = new GamePiece();
		while(!valid) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("w")==0) {
				tileType = world.moveUp();
				valid = true;
				addBoost(tileType);
			}
			else if(resp.compareToIgnoreCase("a")==0) {
				tileType = world.moveLeft();
				valid = true;
				addBoost(tileType);
			}
			else if(resp.compareToIgnoreCase("s")==0) {
				tileType = world.moveDown();
				valid = true;
				addBoost(tileType);
			}
			else if(resp.compareToIgnoreCase("d")==0) {
				tileType = world.moveRight();
				valid = true;
				addBoost(tileType);
			}
			else if(resp.compareToIgnoreCase("i")==0) {
				showInformation();
			}
			else if(resp.compareToIgnoreCase("q")==0) {
				quitGame();
			}
			else if(resp.compareToIgnoreCase("v")==0) {
				displayInv();
			}
			else if(resp.compareToIgnoreCase("p")==0) {
				potion(heroes.get(world.curHero));
			}
			else if(resp.compareToIgnoreCase("b")==0) {
				openMarket(heroes.get(world.curHero));
				move(heroPos);//buying and selling dont count as moves so we do a move after

			}
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else {
				System.out.println("Sorry not a valid move :( "+"To move: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right");
			}
		}
		if(monsterNearby(heroPos)) {
				System.out.println("              .7\n" + 
						"            .'/\n" + 
						"           / /\n" + 
						"          / /\n" + 
						"         / /\n" + 
						"        / /\n" + 
						"       / /\n" + 
						"      / /\n" + 
						"     / /         \n" + 
						"    / /          \n" + 
						"  __|/\n" + 
						",-\\__\\\n" + 
						"|f-\"Y\\|\n" + 
						"\\()7L/\n" + 
						" cgD                            __ _\n" + 
						" |\\(                          .'  Y '>,\n" + 
						"  \\ \\                        / _   _   \\\n" + 
						"   \\\\\\                       )(_) (_)(|}\n" + 
						"    \\\\\\                      {  4A   } /\n" + 
						"     \\\\\\                      \\uLuJJ/\\l\n" + 
						"      \\\\\\                     |3    p)/\n" + 
						"       \\\\\\___ __________      /nnm_n//\n" + 
						"       c7___-__,__-)\\,__)(\".  \\_>-<_/D\n" + 
						"                  //V     \\_\"-._.__G G_c__.-__<\"/ ( \\\n" + 
						"                         <\"-._>__-,G_.___)\\   \\7\\\n" + 
						"                        (\"-.__.| \\\"<.__.-\" )   \\ \\\n" + 
						"                        |\"-.__\"\\  |\"-.__.-\".\\   \\ \\\n" + 
						"                        (\"-.__\"\". \\\"-.__.-\".|    \\_\\\n" + 
						"                        \\\"-.__\"\"|!|\"-.__.-\".)     \\ \\\n" + 
						"                         \"-.__\"\"\\_|\"-.__.-\"./      \\ l\n" + 
						"                          \".__\"\"\">G>-.__.-\">       .--,_\n" + 
						"                              \"\"  G");
				System.out.println("You have encountered a monster!");
				//boolean heroWin = fight(); //implement later
			
		}
		if(tileType.equals(Nexus)) {
				System.out.println("            _,='\"`\"`--._         |  |   |      /    '.  '. \n" + 
			"        ,=''      <>    `-.     /   \\  /     .' / /   \\   \\ \n" + 
			"     ,='     <>       <>   `-. |    |  \\    / .'  |  /|   | \n" + 
			"   ,' <>                   _.=`-.  /    |  /  |  /  / \\   \\ \n" + 
			" .'     <>     <>   _..==='   ,=' |     \\ |  /  |  |   |   \\ \n" + 
			"/    <>         _.='       ,='   /\\      /   |  \\ / \\   \\  | \n" + 
			"|           _.='        ,=' /   |  |    |   /    |  |   |  \\ \n" + 
			"\\  <>   _.='         ,='   |    \\   \\  /    |    /   \\  \\  | \n" + 
			"/    .='             T    /     |   |  |    \\   |    |  /  | \n" + 
			"| _,='                L  |   .---------------------------. \n" + 
			".``  _..J      :       J/  ,'   __           __         .'. \n" + 
			" `--=   F      '       F  /   ,'`.`.       ,'`.`.      / _ \\ \n" + 
			"       L      .        I,'   |_|_|.|      |_|_|.|    ,' |_| `. \n" + 
			"        J     :        /     |_|_|.'      |_|_|.'   /   |_|   \\ \n" + 
			"`._      ;    '      ,'___________________________,'          |\\ \n" + 
			"   `-.   F    :      ============================='   =.   .='| \n" + 
			"`-.   \\  L    .        |=.:   =: ______     .=:'|             | \n" + 
			"-. \\   \\  J    '       |  ___   |      |        |.='  _.-. `='| \n" + 
			" `. \\  |  ;    :       | |   | =|      |=' ___  |    |   |    | \n" + 
			"  | |  |  L    '       | |   |  |      |  |   | |    |   |    | \n" + 
			"   \\ \\ |  J _.='\"'-._  | '---'  |     O|  |   | |    |_.-'  =\"| \n" + 
			"    \\__/..-'         '-|.='   ='|      |= '---' |      .=     | \n" + 
			"_.--'\"\"'               '---...._|      |        |.='        _.' \n" + 
			"                   O          .' `--.._|    -`=:|      =._.' \n" + 
			"                  /<         [`--..__.']`-._ .=-|=   _.-' \n" + 
			"                  \\\\     _.-'  o ..__]'     `=._|_.-' \n" + 
			"                  // _.-'\"'-._ |>_.-' \n" + 
			"                _.--='     _.-'/\\       .---^---. \n" + 
			"           _..-' '\"'-._ _.-'            |Welcome| \n" + 
			"     _..-'\"`---..._ _.-'                |to the | \n" + 
			".-=\"'`---..__  _..-'                    | Market| \n" + 
			".._       _..-'                         |____ __| \n" + 
			"   '-._.-'                                  |/ \n" + 
			" _.-'                                    ---^^-- ");
			System.out.println("Welcome to the market where you can buy/sell potions, weapons, armors, and spells!");

			openMarket(heroes.get(world.curHero));
			move(heroPos);//buying and selling dont count as moves so we do a move after
			
		}
		if(tileType.equals(nonAccessible)) {
			System.out.println("Sorry not a valid move :( , that tile is non accessible try another move");
			world.undo();
			return false;
		}
		return true;
	}
	//or should we only display current hero's stats?
	public void showInformation() {
		System.out.println("\n Information: ");
		System.out.println("Heroes: ");
		for(Hero hero: heroes) {
			System.out.println(hero);
		}
		if(monsterNearby(world.getHeroCoords()[world.curHero])) { //Need to fix, figure out how code underneath works later 
			System.out.println("Monsters: ");
			for(Monster m: monsters) {
				if(m.isFighting()) {
					System.out.println(m);
				}
			}
		}
		System.out.println("\n");
	}

	public void quitGame() {
		System.out.println("Thanks for Playing! After playing your Hero(es) stats are");
		play=false;//take out later
		for(Hero hero: heroes) {
			System.out.print(hero);
		}
		System.exit(0);
	}

	private boolean monsterNearby(int [] heroPos) {

		int row= heroPos[0];
		int col= heroPos[1];
		for (int i=0; i<getHeroNearbyTiles(row, col).length; i++){
			 //implement later
		}
		return false;
	}

	private int[][] getHeroNearbyTiles(int row, int col) {
		int[][] nearbyTiles= new int[5][2];
		nearbyTiles[0]= new int[] {row-1, col-1};
		nearbyTiles[1]= new int[] {row-1, col};
		nearbyTiles[2]= new int[] {row-1, col+1};
		nearbyTiles[3]= new int[] {row, col-1};
		nearbyTiles[4]= new int[] {row, col+1};
		return nearbyTiles;
	}


	private void addBoost(GamePiece tileType){
		if(tileType.equals(Bush)){
			heroes.get(world.curHero).increaseDexerityPercent(10);
		}else if(tileType.equals(Koulou)){
			heroes.get(world.curHero).increaseStrengthPercent(10);
		}else if(tileType.equals(Cave)){
			heroes.get(world.curHero).increaseAgilityPercent(10);
		}else{}
	}

	public void potion(Hero hero) {
		System.out.println("You have decided to use a potion, please select the number of the potion you want to use");
		for(int i=0; i <hero.getPotions().size(); i++) {
			System.out.println("Potion "+ Integer.toString(i)+":");
			System.out.println(hero.getPotions().get(i));
		}
		int resp = userResponse.nextInt();
		while(resp >hero.getPotions().size() || resp < 0) {
			System.out.println("Im sorry thats not an option try again");
			resp = userResponse.nextInt();
		}
		Potion potion = hero.getPotions().get(resp);
		hero.usePotion(potion);
	}
	//should we only display current hero inventory?
	public void displayInv() {
		System.out.println("The inventories for your Heros are:");
		for(Hero h: heroes) {
			System.out.println(h.getName()+":");
			System.out.println("Weapons:"+h.getWeapons());
			System.out.println("Armors:"+h.getArmors());
			System.out.println("Spells:"+h.getSpells());
			System.out.println("Potions:"+h.getPotions());
		}
	}

	public void openMarket(Hero h) {
		
		Market mar = h.getMarket();
		mar.welcomeToMarket();
		System.out.println("Would you like to buy something? (Type Y/y for yes and N/n for no)");
		
		boolean valid = false;
		while(!valid) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("y")==0) {
				mar.buyObjects(h);
				valid = true;
			}
			else if(resp.compareToIgnoreCase("n")==0) {
				valid=true;
				break;
			}
			else if(resp.compareToIgnoreCase("i")==0) {
				showInformation();
			}
			else if(resp.compareToIgnoreCase("q")==0) {
				quitGame();
			}
			else if(resp.compareToIgnoreCase("v")==0) {
				displayInv();
			}
			else if(resp.compareToIgnoreCase("p")==0) {
				potion(h);
			}
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else {
				System.out.println("Please enter a valid command");
			}
		}
		System.out.println("Would you like to sell something?(Type Y/y for yes and N/n for no)");
		valid = false;
		while(!valid) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("y")==0) {
				mar.sellObjects(h);
				valid = true;
			}
			else if(resp.compareToIgnoreCase("n")==0) {
				valid=true;
				break;
			}
			else if(resp.compareToIgnoreCase("i")==0) {
				showInformation();
			}
			else if(resp.compareToIgnoreCase("q")==0) {
				quitGame();
			}
			else if(resp.compareToIgnoreCase("v")==0) {
				displayInv();
			}
			else if(resp.compareToIgnoreCase("p")==0) {
				potion(h);
			}
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else {
				System.out.println("Please enter a valid command");
			}
		}
	}
	public List<Monster> generateMonsters() throws FileNotFoundException {
		List<String> strRep = new ArrayList<String>();
		List<Monster> monsters = new ArrayList<Monster>();
		File file = new File(monstertxt); 
	    Scanner sc = new Scanner(file);
	    sc.useDelimiter("\n");
	    while(sc.hasNext()){  
	    	strRep.add(sc.next());  
        }  
        sc.close();
        for(int i = 0; i < strRep.size(); i++) {
        	String[] tokens = strRep.get(i).split(",");
        	String monsterType = tokens[0];
        	String monsterName = tokens[1];
        	double monsterlevel = Integer.parseInt(tokens[2]);
        	double monsterBD = Integer.parseInt(tokens[3]);
        	double monsterDefense = Integer.parseInt(tokens[4]);
        	double monsterDodge = Integer.parseInt(tokens[5]);
        	Monster newMonster;
        	if(monsterType.compareToIgnoreCase("dragon") == 0) {
        		newMonster = new Dragon(monsterBD, monsterDefense,monsterDodge, monsterlevel, monsterName);
        		monsters.add(newMonster);
        	}
        	if(monsterType.compareToIgnoreCase("exoskeleton") == 0) {
        		newMonster = new Exoskeleton(monsterBD, monsterDefense,monsterDodge, monsterlevel, monsterName);
        		monsters.add(newMonster);
        	}
        	if(monsterType.compareToIgnoreCase("spirit") == 0) {
        		newMonster = new Spirit(monsterBD, monsterDefense,monsterDodge, monsterlevel, monsterName);
        		monsters.add(newMonster);
        	}
        }
        return monsters;
	}
	
	public void chooseMonsters()throws FileNotFoundException{
		List<Monster> monsterOptions = generateMonsters();
		double minlevel = heroes.get(0).getLevel();
		for(int i = 0; i<heroes.size(); i++) {
			minlevel = Math.min(minlevel, heroes.get(i).getLevel());
		}
		Random rand = new Random();
		int i =0;
		while(i<playersPerTeam) {
			int ri = rand.nextInt(monsterOptions.size());
			if(monsterOptions.get(ri).getLevel() <= minlevel) {
				monsters.add(monsterOptions.get(ri));
				i++;
			}
		}
		world.spawnMonsters();
		
	}

	public void chooseHeroes() throws FileNotFoundException {
		List<Hero> heroOptions = generateHereos();
		int numberOfHeroes = playersPerTeam;
		System.out.println("                  .\n" + 
				"\n" + 
				"                   .\n" + 
				"         /^\\     .\n" + 
				"    /\\   \"V\"\n" + 
				"   /__\\   I      O  o\n" + 
				"  //..\\\\  I     .\n" + 
				"  \\].`[/  I\n" + 
				"  /l\\/j\\  (]    .  O\n" + 
				" /. ~~ ,\\/I          .\n" + 
				" \\\\L__j^\\/I       o\n" + 
				"  \\/--v}  I     o   .\n" + 
				"  |    |  I   _________\n" + 
				"  |    |  I c(`       ')o\n" + 
				"  |    l  I   \\.     ,/\n" + 
				"_/j  L l\\_!  _//^---^\\\\_ ");
		System.out.println("Your Selection of Heros: ");
		for(int i = 0; i <heroOptions.size(); i++) {
			System.out.println("Hero "+ Integer.toString(i)+":");
			System.out.println(heroOptions.get(i) + "\n");
		}
		for(int i =0; i <numberOfHeroes; i++) {
			System.out.println("Type the number of the hero you wish to add to your team in lane "+i);
			int heronum = userResponse.nextInt();
			while(heronum < 0 || heronum > heroOptions.size()) {
				System.out.println("Im sorry that is not a valid option must be the number of a hero displayed between (0-"+ heroOptions.size()+ ")");
				heronum = userResponse.nextInt();
			}
			Hero h = heroOptions.get(heronum);
			heroes.add(h);
		}
		world.spawnHeroes(heroes);
		
	}
	public TheQuestOfLegends getGameState() {
		return this;
	}
	
	public List<Hero> getHeroes(){
		return heroes;
	}
	public List<Monster> getMonsters(){
		return monsters;
	}
	
	public List<Hero> generateHereos() throws FileNotFoundException {
		List<String> strRep = new ArrayList<String>();
		List<Hero> heroOptions = new ArrayList<Hero>();
		File file = new File(herotxt); 
	    Scanner sc = new Scanner(file);
	    sc.useDelimiter("\n");
	    while(sc.hasNext()){  
	    	strRep.add(sc.next());  
        }  
        sc.close();
        for(int i = 0; i < strRep.size(); i++) {
        	String[] tokens = strRep.get(i).split(",");
        	String heroType = tokens[0];
        	String heroName = tokens[1];
        	double herolevel = Integer.parseInt(tokens[2]);
        	double heroxp = Integer.parseInt(tokens[3]);
        	double heroMana = Integer.parseInt(tokens[4]);
        	double heroAgility = Integer.parseInt(tokens[5]);
        	double heroStrength = Integer.parseInt(tokens[6]);
        	double heroDex = Integer.parseInt(tokens[7]);
        	Hero newHero;
        	if(heroType.compareToIgnoreCase("Warrior") == 0) {
        		newHero = new Warrior(heroName, herolevel,heroxp, heroMana, heroAgility,heroStrength,heroDex);
        		Weapon w = new Weapon("Sword",300,3,24,1);
        		newHero.addWeapon(w);
        		heroOptions.add(newHero);
        	}
        	if(heroType.compareToIgnoreCase("Sorcerer") == 0) {
        		newHero = new Sorcerer(heroName, herolevel,heroxp, heroMana, heroAgility,heroStrength,heroDex);
        		heroOptions.add(newHero);
        	}
        	if(heroType.compareToIgnoreCase("Paladin") == 0) {
        		newHero = new Paladin(heroName, herolevel,heroxp,heroMana, heroAgility,heroStrength,heroDex);
        		heroOptions.add(newHero);
        	}
        }
        return heroOptions;
	}
}
