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
	private int fightProb = 75;
	boolean inFight;
	private int playersPerTeam = 3;
	Random rand = new Random();
	
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
		
	}
	public void startGame() throws FileNotFoundException {
		setMonsters(generateMonsters());
		//System.out.println(ANSI_RED +"Welcome to the Quest!"+ ANSI_RESET);
		System.out.println("        ,     \\    /      ,        \n" + 
				"       / \\    )\\__/(     / \\       \n" + 
				"      /   \\  (_\\  /_)   /   \\      \n" + 
				" ____/_____\\__\\@  @/___/_____\\____ \n" + 
				"|             |\\../|              |\n" + 
				"|              \\VV/               |\n" + 
				"|   -- Welcome to The Quest --    |\n" + 
				"|_________________________________|\n" + 
				" |    /\\ /      \\\\       \\ /\\    | \n" + 
				" |  /   V        ))       V   \\  | \n" + 
				" |/     `       //        '     \\| \n" + 
				" `              V                '\n" + 
				"");
		System.out.println("First you must select your heroes");
		chooseHeroes();
		
		System.out.println("Great now lets go over some logistics!");
		System.out.println("Commands: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right\n"+"I/i: View information about your heroes (or if you are in a fight the monsters as well)\n"+
				"Q/q: Quit the game\n"+"V/v: Display inventories\n"+"C/c: Change weapon or armor\n"+"P/p: Consume Potion\n"+"M/m: Display the Map\n");
		System.out.println("When looking at the map you will see C for common tiles, M for marketplaces, N for tiles you cant visit, and a * to show where you are");
		System.out.println("Now that we've covered the basic rules lets start playing");
		System.out.println(world);
	}
	public List<Monster> generateMonsters() throws FileNotFoundException{
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
	public void setMonsters(List<Monster> monsters) throws FileNotFoundException {
		this.monsters = monsters;
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
