import java.io.*; 
import java.util.*;


public class TheQuestOfLegends {
	private String herotxt;
	private String monstertxt;
	private List<Hero> heroes;
	private List<Monster> monsters;
	private QuestBoard world;
	Scanner userResponse = new Scanner( System.in );
	private GamePiece nonAccessible = new GamePiece("XXX","NonAcc",-1);
	private GamePiece HeroNexus = new GamePiece(" N ","Nexus",11);
	private GamePiece Bush = new GamePiece(" B ","Bush",4);
	private GamePiece Koulou = new GamePiece(" K ","Koulou",5);
	private GamePiece Cave = new GamePiece(" C ","Cave",4);
	boolean inFight;
	private int playersPerTeam = 3;
	Random rand = new Random();
	boolean play;
	private int roundCount = 1;
	private int monsterSpawn = 4;
	
	TheQuestOfLegends(){
		herotxt = "heroes.txt";
		monstertxt = "monsters.txt";
		heroes = new ArrayList<Hero>();
		monsters = new ArrayList<Monster>();
		world = new QuestBoard();
		inFight = false;
	}
	
	public static void main(String[] args) throws FileNotFoundException{
		TheQuestOfLegends game = new TheQuestOfLegends();
		game.playGame();
	}
	
	public void startGame() throws FileNotFoundException {
		//System.out.println(ANSI_RED +"Welcome to the Quest!"+ ANSI_RESET);
		System.out.println("        ,     \\    /      ,        \n" + 
				"       / \\    )\\__/(     / \\       \n" + 
				"      /   \\  (_\\  /_)   /   \\      \n" + 
				" ____/_____\\__\\@  @/___/_____\\____ \n" + 
				"|             |\\../|                |\n" + 
				"|              \\VV/                 |\n" + 
				"| Welcome to The Quest of Legends    |\n" + 
				"|____________________________________|\n" + 
				" |    /\\ /      \\\\       \\ /\\    | \n" + 
				" |  /   V        ))       V   \\  | \n" + 
				" |/     `       //        '     \\| \n" + 
				" `              V                '\n" + 
				"");
		System.out.println("To win, one of your heros must reach the monster's Nexus on the other side of the board.");
		System.out.println("You can buy weapons to fight with, armor to protect yourself, potions to boost your attributes\n"
		+ "and spells to fight monsters from the market");
		System.out.println("First you must select your heroes");
		chooseHeroes();
		chooseMonsters();
		System.out.println("Great now lets go over some logistics!");
		System.out.println("Commands: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right\n"+"I/i: View information about your heroes and the monsters on the board as well)\n"+
				"Q/q: Quit the game\n"+"V/v: Display inventories\n"+"P/p: Consume Potion\n"+"M/m: Display the Map\n" +"B/b: Return to the Hero Nexus");
		System.out.println("When looking at the map you will see N for Nexus, X for tiles you cant visit, and a * to show where heros and monsters are");
		System.out.println("There are also special tiles that boost a hero attribute by 10% every round. K tiles boost strength,");
		System.out.println("C tiles boost agility, B tiles boost dexterity, and P tiles are plain with no boosts.");
		System.out.println("Now that we've covered the basic rules lets start playing");
	}
	
	public void playGame() throws FileNotFoundException{
		play = true;
		startGame();
		
		roundCount = 1;
		boolean heroWin = world.checkHeroWin();
		boolean monsterWin = world.checkMonsterWin();
		heroRounds(); 
		while(!heroWin && !monsterWin) {
			if(roundCount % monsterSpawn == 0) {
				chooseMonsters();
			}
			else {
				monsterRounds();
			}
			heroRounds(); 
			
			heroWin = world.checkHeroWin();
			monsterWin = world.checkMonsterWin();
			roundCount++;
		}
		if(monsterWin && !heroWin) {
			System.out.println("Sorry a monster reached the hero Nexus you lose");
		}
		else {
			System.out.println("Congrats a hero reached the monster Nexus, you win!");
		}
		
	}
	
	public void monsterRounds() {
		world.setCurMonster(0);
		List<Monster> activeMonsters = world.getMonsters();
		for(Monster monster: activeMonsters) {
			boolean nearHero = false;
			for(int j = 0; j<heroes.size(); j++) {
				world.setCurHero(j);
				nearHero = world.monsterNearby();
			}
			if(nearHero) {
				Hero h = world.getCurHero();
				int inx = heroes.indexOf(h);
				Hero hero = heroes.get(inx);
				hero.getAttacked(monster.getBaseDamage());
			}
			else {
				world.monsterMove();
			}
			world.nextMonster();
		}
		
	}
	
	public void heroRounds() {
		world.setCurHero(0);
		boolean usedMove = false;
		for(Hero hero: heroes) {
			usedMove = false;
			System.out.println(world);
			hero.roundRenewal();
			while(!usedMove) {
				if(world.getCurTileType().equals(HeroNexus) && roundCount != 1) {
					openMarket(heroes.get(world.getCurHeroIndex()));;
				}
				if(world.monsterNearby()) {
					System.out.println("Hero "+ (world.getCurHeroIndex()+1)+", Would you like to move or fight the nearby monster (type m for move and f for fight)");
					String resp = userResponse.next();
					if(resp.compareToIgnoreCase("m")==0) {
						usedMove = move();
					}
					else if(resp.compareToIgnoreCase("f")==0) {
						usedMove = fight(hero);
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
				}
				else {
					usedMove = move();
				}
			}
			if(!hero.isAlive()) {
				System.out.println("Hero has been killed by monster. Respawning in Hero Nexus.");
				hero.setHP();
				world.returnToHeroNexus();
			}
			world.nextTurn();
		}
	}

	public boolean move() {
		System.out.println("Hero " + (world.getCurHeroIndex()+1)+ ", Make your move");
		boolean valid = false;
		GamePiece tileType = new GamePiece();
		while(!valid) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("w")==0) {
				if(world.monsterNearby()) {
					System.out.println("You can't move past the monster without fighting");
				}
				else {
					tileType = world.moveUp();
					valid = true;
					addBoost(tileType);
				}
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
			else if(resp.compareToIgnoreCase("b")==0) {
				tileType = world.returnToHeroNexus();
				valid = true;
			}
			else if(resp.compareToIgnoreCase("t")==0) {
				//Add more error checking so they cant move to the same lane 
				System.out.println("Which lane do you want to teleport to (Lanes are 0-7)");
				boolean choosing= true;
				while(choosing){
					String col = userResponse.next();
					int colnum=-1;
					try{
						colnum= Integer.parseInt(col);
					}catch(Exception e){
						System.out.println("Invaild input. Enter a number");
					}
					if (colnum!=-1){
						if (colnum>=0 && colnum<=7){
							choosing= false;
							tileType = world.teleport(colnum);
							valid = true;
						}else{
							System.out.println("Invaild input");
						}
				}
			}
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
				potion(heroes.get(world.getCurHeroIndex()));
			}
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else {
				System.out.println("Sorry not a valid move :( "+"To move: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right");
			}
		}
		if(tileType.equals(HeroNexus)) {
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
		System.out.println("Welcome to the market Hero "+(world.getCurHeroIndex()+1)+" where you can buy/sell potions, weapons, armors, and spells!");
		openMarket(heroes.get(world.getCurHeroIndex()));
		// this was a recursive call to move but i changed it to return false because that will make it not count as an action 
		return true;
		
	}
	if(tileType.equals(nonAccessible)) {
		System.out.println("Sorry not a valid move :( , that tile is non accessible try another move");
		world.undo();
		return false;
	}
	
	return true;
	}
	
	public boolean fight(Hero hero) {
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
		System.out.println("Hero "+ (world.getCurHeroIndex()+1)+" you have encountered a monster!");
		System.out.println(hero.getName()+" would you like to do a regular attack (type attack), cast a spell (type spell), use a potion (type potion), or change your armor/weapon (type change)?");
		String resp = chooseFightMove(hero);
		boolean valid = false;
		Monster monster = world.getMonsterNearby();
		if(resp.compareTo("change")==0) {
			change(hero);
			valid = true;
			
		}
		else if(resp.compareTo("potion")==0) {
			potion(hero);
			valid = true;
		}
		else if(resp.compareTo("attack")==0) {
			double weaponDamage = attack(hero);
			System.out.println("Attacking the nearest monster");
			int inx = monsters.indexOf(monster);
			if(inx != -1) {
				Monster m = monsters.get(inx);
				m.getAttacked(weaponDamage);
				if(!m.isAlive()) {
					System.out.println("You have killed the monster!");
					world.deleteMonsterFromBoard(monster);
				}
				
			}
			valid = true;
		}
		else if(resp.compareTo("spell")==0) {
			Spell spell = spell(hero);
			System.out.println("Casting a spell on the nearest monster");
			int inx = monsters.indexOf(monster);
			if(inx != -1) {
				Monster m = monsters.get(inx);
				m.getSpellEffect(hero,spell);
				if(!m.isAlive()) {
					System.out.println("You have killed the monster!");
					world.deleteMonsterFromBoard(monster);
				}
				
			}
			valid = true;
		}
		else if(resp.compareToIgnoreCase("i")==0) {
			showInformation();
			
		}
		else if(resp.compareToIgnoreCase("q")==0) {
			quitGame();
		}
		return valid;
	}

	public void showInformation() {
		System.out.println("\n Information: ");
		System.out.println("Heroes: ");
		for(Hero hero: heroes) {
			System.out.println(hero);
		}
		List<Monster> activeMonsters = world.getMonsters();
		System.out.println("Monsters: ");
		for(Monster monster: activeMonsters) {
			System.out.println(monster);
		}
		System.out.println(world);
		System.out.println("Hero " + (world.getCurHeroIndex()+1)+ ", Make your move");
	}

	public void quitGame() {
		System.out.println("Thanks for Playing! After playing your Hero(es) stats are");
		play=false;
		for(Hero hero: heroes) {
			System.out.print(hero);
		}
		System.exit(0);
	}

	private void addBoost(GamePiece tileType){
		if(tileType.equals(Bush)){
			heroes.get(world.getCurHeroIndex()).increaseDexerityPercent(10);
		}else if(tileType.equals(Koulou)){
			heroes.get(world.getCurHeroIndex()).increaseStrengthPercent(10);
		}else if(tileType.equals(Cave)){
			heroes.get(world.getCurHeroIndex()).increaseAgilityPercent(10);
		}else{}
	}
	
	public String chooseFightMove(Hero hero) {
		boolean validresponse = false;
		String ret = "";
		while(!validresponse) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("attack") == 0) {
				if(hero.getWeapons().size() > 0) {
					validresponse = true;
					ret = "attack";
				}
				else {
					System.out.println("Sorry this hero has no weapons to attack with, try a different move"); }
			}
			else if(resp.compareToIgnoreCase("spell") == 0) {
				if(hero.getSpells().size() > 0) {
					validresponse = true;
					ret = "spell";
				}
				else {
					System.out.println("Sorry this hero has no spells to use, try a different move"); }
			}
			else if(resp.compareToIgnoreCase("potion") == 0) {
				if(hero.getPotions().size() > 0) {
					validresponse = true;
					ret = "potion";
				}
				else {
					System.out.println("Sorry this hero has no potions to use, try a different move"); }
			}
			else if(resp.compareToIgnoreCase("change") == 0) {
				if(hero.getArmors().size() > 0 || hero.getWeapons().size() > 0) {
					validresponse = true;
					ret = "change";
				}
				else {
					System.out.println("Sorry this hero has nothing to change into, try a different move"); }
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
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else if(resp.compareToIgnoreCase("p")==0) {
				for(Hero h: heroes) {
					potion(h);
				}
			}
			else {
				System.out.println("Im sorry you must type either attack, spell, potion, or change");
			}
		}
		return ret;
	}
	
	public void displayInv() {
		System.out.println("The inventories for your Heros are:");
		for(Hero h: heroes) {
			System.out.println(h.getName()+":");
			System.out.println("Weapons:"+h.getWeapons());
			System.out.println("Armors:"+h.getArmors());
			System.out.println("Spells:"+h.getSpells());
			System.out.println("Potions:"+h.getPotions());
		}
		System.out.println(world);
		System.out.println("Hero " + (world.getCurHeroIndex()+1)+ ", Make your move");
	}

	public void openMarket(Hero h) {
		
		Market mar = h.getMarket();
		mar.welcomeToMarket();
		boolean cont = true;
		while(cont) {
			System.out.println("Hero "+ (world.getCurHeroIndex()+1)+ ", Would you like to buy something? (Type Y/y for yes and N/n for no)");
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
			System.out.println("Would you like to exit the market or continue shopping and selling items? (type e to exit and c to contine shopping");
			valid = false;
			while(!valid) {
				String resp = userResponse.next();
				if(resp.compareToIgnoreCase("e")==0) {
					cont = false;
					valid = true;
				}
				else if(resp.compareToIgnoreCase("c")==0) {
					valid=true;
				}
				else {
					System.out.println("Please enter a valid command");
				}
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
		List<Monster> newMonsters = new ArrayList<Monster>();
		List<Monster> monsterOptions = generateMonsters();
		double minlevel = heroes.get(0).getLevel();
		for(int i = 0; i<heroes.size(); i++) {
			minlevel = Math.min(minlevel, heroes.get(i).getLevel());
		}
		Random rand = new Random();
		int i =0;
		while(i<playersPerTeam) {
			int ri = rand.nextInt(monsterOptions.size());
			boolean contains = activeMonster(monsterOptions.get(ri)) || newMonsters.contains(monsterOptions.get(ri));
			if(monsterOptions.get(ri).getLevel() <= minlevel && !contains) {
				monsters.add(monsterOptions.get(ri));
				newMonsters.add(monsterOptions.get(ri));
				i++;
			}
		}
		world.spawnMonsters(newMonsters);
		
	}

	public boolean activeMonster(Monster m) {
		boolean ret = false;
		List<Monster> activeMonsters = world.getMonsters();
		for(Monster monster : activeMonsters) {
			if(m.equals(monster)) {
				ret = true;
			}
		}
		return ret;
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
			boolean choosing=true;
			int heronum=-1;
			while(choosing){
				heronum=-1;
				String input= userResponse.nextLine();
				try{
					heronum= Integer.parseInt(input);
				}catch(Exception e){
					System.out.println("Im sorry that is not a valid option must be the number of a hero displayed between (0-"+ heroOptions.size()+ ")");
				}
				if(heronum !=-1){
					if(heronum >= 0 && heronum < heroOptions.size()) {
						choosing=false;
					}else{
						System.out.println("Im sorry that is not a valid option must be the number of a hero displayed between (0-"+ heroOptions.size()+ ")");
					}
				}
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

	public void change(Hero hero) {
		System.out.println("You have decided to change - would you like to change your armor (type armor) or weapon (type weapon)");
		String resp = "";
		
		boolean valid = false;
		while(!valid) {
			resp = userResponse.next();
			if(resp.compareToIgnoreCase("armor") == 0) {
				valid = true;
				if(hero.getArmors().size() <= 0) {
					System.out.println("I'm sorry you have no other armors to change into but you can change your weapon");	
				}
				else {
					System.out.println("Please choose the number of the armor you would like to change into");
					for(int i=0; i<hero.getArmors().size(); i++) {
						System.out.println("Armor "+ Integer.toString(i)+":");
						System.out.println(hero.getArmors().get(i));
					}
					int arm= -1;
					boolean choosing= true;
					while(choosing){
						String arminput = userResponse.next();
						arm= -1;
						try{
							arm=Integer.parseInt(arminput);
						}catch(Exception e){
							System.out.println("Invalid Input. Please enter a number");
						}
						if (arm!= -1){
							if(arm <hero.getPotions().size() && arm >= 0) {
								choosing =false;
							}else{
								System.out.println("Im sorry thats not an option try again");
							}
						}
						}
					System.out.println("You have changed!"); 
					Armor a = hero.getArmors().get(arm);
					hero.changeArmor(a);
				}
			}
			else if(resp.compareToIgnoreCase("weapon") == 0) {
				valid = true;
				if(hero.getWeapons().size() <= 0) {
					System.out.println("I'm sorry you have no other weapons to change to but you can change your armor");
					System.out.println("Please choose the number of the armor you would like to change into");
					for(int i=0; i<hero.getArmors().size(); i++) {
						System.out.println("Armor "+ Integer.toString(i)+":");
						System.out.println(hero.getArmors().get(i));
					}
					boolean choosing= true;
					int arm=-1;
					while(choosing){
						String arminput = userResponse.next();
						arm= -1;
						try{
							arm=Integer.parseInt(arminput);
						}catch(Exception e){
							System.out.println("Invalid Input. Please enter a number");
						}
						if (arm!= -1){
					if(arm<hero.getPotions().size() && arm >= 0) {
						choosing= false;
					}else{
						System.out.println("Im sorry thats not an option try again");
					}
						}
					}
					System.out.println("You have changed!"); 
					Armor a = hero.getArmors().get(arm);
					hero.changeArmor(a);
				}
			
				else {
					System.out.println("Please choose the number of the weapon you would like to switch to");
					for(int i=0; i<hero.getWeapons().size(); i++) {
						System.out.println("Weapon "+ Integer.toString(i)+":");
						System.out.println(hero.getWeapons().get(i));
					}
					boolean choosing= true;
					int wp=-1;
					while(choosing){
						String wpinput = userResponse.next();
						wp= -1;
						try{
							wp=Integer.parseInt(wpinput);
						}catch(Exception e){
							System.out.println("Invalid Input. Please enter a number");
						}
						if (wp!= -1){
						if(wp <hero.getPotions().size() && wp >= 0) {
							choosing=false;
						}else{
							System.out.println("Im sorry thats not an option try again");
						}
					}
				}
				System.out.println("You have changed!"); 
				Weapon w = hero.getWeapons().get(wp);
				hero.changeWeapon(w);
				}
			}
			else {
				System.out.println("Please enter either armor or weapon");
			}
		}
			
		}
	
	
	
	public void potion(Hero hero) {
		System.out.println("You have decided to use a potion, please select the number of the potion you want to use");
		for(int i=0; i <hero.getPotions().size(); i++) {
			System.out.println("Potion "+ Integer.toString(i)+":");
			System.out.println(hero.getPotions().get(i));
		}
		boolean choosing=true;
		int resp= -1;
		while(choosing){
			resp= -1;
			String input = userResponse.nextLine();
			try{
				resp= Integer.parseInt(input);
			}catch(Exception e){
				System.out.println("Please enter a number");
			}
			if (resp!= -1){
				if(resp <hero.getPotions().size() && resp >= 0) {
					choosing=false;
				}else{
					System.out.println("Im sorry thats not an option try again");
			}
		}
		}
		System.out.println("Potion consummed. Now, make your move");
		Potion potion = hero.getPotions().get(resp);
		hero.usePotion(potion);
	}
	
	public Spell spell(Hero hero) {
		System.out.println("You have decided to use a spell, please select the number of the spell you want to use");
		for(int i=0; i <hero.getSpells().size(); i++) {
			System.out.println("Spell "+ Integer.toString(i)+":");
			System.out.println(hero.getSpells().get(i));
		}
		
		boolean choosing= true;
		Spell spell= new Spell();
		while (choosing){
			int resp= -1;
		String input = userResponse.nextLine();
		try{
			resp = Integer.parseInt(input);
		}catch(Exception e){
			System.out.println("Please enter a number");
		}
		if (resp !=-1){
			boolean validResp = resp <hero.getSpells().size() && resp >= 0;
			if(validResp) {
				spell = hero.getSpells().get(resp);
				boolean validSpell = spell.getManaNeeded() <= hero.getMana();
				if (validSpell){
					choosing= false;
				}else{
					System.out.println("Im sorry thats not an option try again make sure you have enough mana");
				}
				
			}else{
				System.out.println("Invalid response. Enter a valid number");
			}
		}
	}
		return spell;
	}
	
	public double attack(Hero hero) {
		System.out.println("You have decided to use an attack, your attack uses your current weapon");
		Weapon w = hero.getCurWeapon();
		double damage = (hero.getStrength()+w.getDamage())*.05;
		return damage;
	}
}
