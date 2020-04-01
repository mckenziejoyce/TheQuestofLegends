import java.io.*; 
import java.util.*;
import java.math.*;
	
public class TheQuest {
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_BLACK = "\u001B[30m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_GREEN = "\u001B[32m";
	public static final String ANSI_YELLOW = "\u001B[33m";
	public static final String ANSI_BLUE = "\u001B[34m";
	public static final String ANSI_PURPLE = "\u001B[35m";
	public static final String ANSI_CYAN = "\u001B[36m";
	public static final String ANSI_WHITE = "\u001B[37m";
	public static final String ANSI_BLACK_BACKGROUND = "\u001B[40m";
	public static final String ANSI_RED_BACKGROUND = "\u001B[41m";
	public static final String ANSI_GREEN_BACKGROUND = "\u001B[42m";
	public static final String ANSI_YELLOW_BACKGROUND = "\u001B[43m";
	public static final String ANSI_BLUE_BACKGROUND = "\u001B[44m";
	public static final String ANSI_PURPLE_BACKGROUND = "\u001B[45m";
	public static final String ANSI_CYAN_BACKGROUND = "\u001B[46m";
	public static final String ANSI_WHITE_BACKGROUND = "\u001B[47m";

	
	private String herotxt;
	private String monstertxt;
	//private String startItems;
	private List<Hero> heroes;
	private List<Monster> monsters;
	private QuestBoard world;
	Scanner userResponse = new Scanner( System.in );
	private GamePiece nonAccessible = new GamePiece(" N ","NonAcc",-1);
	private GamePiece marketPiece = new GamePiece(" M ", "Market", 10);
	private GamePiece common = new GamePiece(" C ","Common", 3);
	private GamePiece heroTeam = new GamePiece(" * ","Heroes", 5);
	private int fightProb = 75;
	boolean inFight;
	Random rand = new Random();
	
	
	public TheQuest() {
		herotxt = "heroes.txt";
		monstertxt = "monsters.txt";
		//startItems = "src/startItems.txt";
		heroes = new ArrayList<Hero>();
		monsters = new ArrayList<Monster>();
		world = new QuestBoard(8,8);
		inFight = false;
		
	}
	
	
	public static void main(String[] args) throws FileNotFoundException {
		TheQuest game = new TheQuest();
		game.playGame();
		
		
		
	}
	public void playGame() throws FileNotFoundException {
		startGame();
		boolean play = true;
		while(play) {
			move();
			System.out.println(world);
		}
		
	}
	public boolean move() {
		System.out.println("Make your move");
		boolean valid = false;
		GamePiece tileType = new GamePiece();
		while(!valid) {
			String resp = userResponse.next();
			if(resp.compareToIgnoreCase("w")==0) {
				tileType = world.moveUp();
				valid = true;
			}
			else if(resp.compareToIgnoreCase("a")==0) {
				tileType = world.moveLeft();
				valid = true;
			}
			else if(resp.compareToIgnoreCase("s")==0) {
				tileType = world.moveDown();
				valid = true;
			}
			else if(resp.compareToIgnoreCase("d")==0) {
				tileType = world.moveRight();
				valid = true;
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
				for(Hero h: heroes) {
					potion(h);
				}
			}
			else if(resp.compareToIgnoreCase("m")==0) {
				System.out.println(world);
			}
			else {
				System.out.println("Sorry not a valid move :( "+"To move: \n W/w: move up\n" + "A/a: move left\n" + "S/s: move down\n" + "D/d: move right");
			}
		}
		if(tileType.equals(common)) {
			int i = rand.nextInt(101);
			if(i <= fightProb) {
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
				System.out.println("You have encountered monsters - time to fight!");
				boolean heroWin = fight();
			}
		}
		if(tileType.equals(marketPiece)) {
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
			for(int i=0;i<heroes.size();i++) {
				System.out.println("\n"+heroes.get(i).getName()+", your turn to shop at the market");
				openMarket(heroes.get(i));
			}
			
		}
		if(tileType.equals(nonAccessible)) {
			System.out.println("Sorry not a valid move :( , that tile is non accessible try another move");
			world.undo();
			return false;
		}
		return true;
	}
	
	public void showInformation() {
		System.out.println("\n Information: ");
		System.out.println("Heroes: ");
		for(Hero hero: heroes) {
			System.out.println(hero);
		}
		if(inFight) {
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
		for(Hero hero: heroes) {
			System.out.print(hero);
		}
		System.exit(0);
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
	
	
	//returns true if heroes won and false otherwise 
	public boolean fight() {
		inFight = true;
		int numPerTeam = heroes.size();
		double minlevel = heroes.get(0).getLevel();
		for(int i = 0; i<heroes.size(); i++) {
			minlevel = Math.min(minlevel, heroes.get(i).getLevel());
		}
		Monster[] monsteys = choseMonsters(minlevel,numPerTeam);
		for(Monster m: monsteys) {
			m.startedFight();
		}
		boolean monstersAlive = checkMonstersAlive(monsteys);
		boolean heroesAlive = checkHerosAlive();
		// Each traverse thru while loop is a round 
		while(monstersAlive && heroesAlive) {
			for(int i=0;i<heroes.size();i++) {
				Hero hero = heroes.get(i);
				System.out.println(hero.getName()+" would you like to do a regular attack (type attack), cast a spell (type spell), use a potion (type potion), or change your armor/weapon (type change)?");
				String resp = chooseFightMove(hero);
				if(resp.compareTo("change")==0) {
					change(hero);
				}
				else if(resp.compareTo("potion")==0) {
					potion(hero);
				}
				else if(resp.compareTo("attack")==0) {
					double weaponDamage = attack(hero);
					System.out.println("Which monster would you like to attack? (type the corresponding number)");
					int id = chooseMonster(monsteys);
					monsteys[id].getAttacked(weaponDamage);
				}
				else if(resp.compareTo("spell")==0) {
					Spell spell = spell(hero);
					System.out.println("Which monster would you like to cast your spell on? (type the corresponding number)");
					int id = chooseMonster(monsteys);
					monsteys[id].getSpellEffect(hero,spell);
				}
				else if(resp.compareToIgnoreCase("i")==0) {
					showInformation();
					i--;
				}
				else if(resp.compareToIgnoreCase("q")==0) {
					quitGame();
				}
			}
			for(Monster monster: monsteys) {
				Random rand = new Random();
				int i = rand.nextInt(numPerTeam);
				double dam = monster.getBaseDamage();
				heroes.get(i).getAttacked(dam);
			}
			for(Hero hero:heroes) {
				hero.roundRenewal();
			}
			monstersAlive = checkMonstersAlive(monsteys);
			heroesAlive = checkHerosAlive();
			
		}
		for(Monster m: monsteys) {
			m.endedFight();
		}
		if(heroesAlive) {
			for(Hero hero: heroes) {
				hero.heroWin(2, minlevel*100);
			}
			inFight = false;
			System.out.println("Congrats you Won the Battle!");
			return true;
		}
		else {
			for(Hero hero: heroes) {
				hero.heroLoss();
			}
			System.out.println("Sorry you lost the Battle!");
			inFight = false;
			return false;
		}
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
	
	public int chooseMonster(Monster[] monsteys) {
		for(int i=0; i<monsteys.length;i++) {
			System.out.println("\n Monster "+ i+":");
			System.out.println(monsteys[i]);
		}
		int id = userResponse.nextInt();
		while(id < 0 || id >= monsteys.length) {
			System.out.println("Sorry you must pick a valid number");
			id = userResponse.nextInt();
		}
		return id;
	}
	
	public void change(Hero hero) {
		System.out.println("You have decided to change - would you like to change your armor (type armor) or weapon (type weapon)");
		String resp = "";
		resp = userResponse.next();
		boolean valid = false;
		while(!valid) {
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
					int arm = userResponse.nextInt();
					while(arm >hero.getPotions().size() || arm < 0) {
						System.out.println("Im sorry thats not an option try again");
						arm = userResponse.nextInt();
					}
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
					int arm = userResponse.nextInt();
					while(arm >hero.getPotions().size() || arm < 0) {
						System.out.println("Im sorry thats not an option try again");
						arm = userResponse.nextInt();
					}
					Armor a = hero.getArmors().get(arm);
					hero.changeArmor(a);
				}
				else {
					System.out.println("Please choose the number of the weapon you would like to switch to");
					for(int i=0; i<hero.getWeapons().size(); i++) {
						System.out.println("Weapon "+ Integer.toString(i)+":");
						System.out.println(hero.getWeapons().get(i));
					}
					int wp = userResponse.nextInt();
					while(wp >hero.getPotions().size() || wp < 0) {
						System.out.println("Im sorry thats not an option try again");
						wp = userResponse.nextInt();
					}
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
		int resp = userResponse.nextInt();
		while(resp >hero.getPotions().size() || resp < 0) {
			System.out.println("Im sorry thats not an option try again");
			resp = userResponse.nextInt();
		}
		Potion potion = hero.getPotions().get(resp);
		hero.usePotion(potion);
	}
	
	public Spell spell(Hero hero) {
		System.out.println("You have decided to use a spell, please select the number of the spell you want to use");
		for(int i=0; i <hero.getSpells().size(); i++) {
			System.out.println("Spell "+ Integer.toString(i)+":");
			System.out.println(hero.getSpells().get(i));
		}
		int resp = userResponse.nextInt();
		boolean invalidResp = resp >hero.getSpells().size() || resp < 0;
		boolean invalidSpell = true;
		Spell spell = new Spell();
		while(invalidResp || invalidSpell) {
			System.out.println("Im sorry thats not an option try again make sure you have enough mana");
			resp = userResponse.nextInt();
			invalidResp = resp >hero.getSpells().size() || resp < 0;
			spell = hero.getSpells().get(resp);
			invalidSpell = spell.getManaNeeded() > hero.getMana();
		}
		return spell;
	}
	
	public double attack(Hero hero) {
		System.out.println("You have decided to use an attack, your attack uses your current weapon");
		Weapon w = hero.getCurWeapon();
		double damage = (hero.getStrength()+w.getDamage())*.05;
		return damage;
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
	}
	
	public boolean checkMonstersAlive(Monster[] m) {
		boolean ret = false;
		for(int i=0; i<m.length;i++) {
			if(m[i].isAlive()) {
				ret = true;
			}
		}
		return ret;
	}
	
	public boolean checkHerosAlive() {
		boolean ret = false;
		for(int i=0; i<heroes.size();i++) {
			if(heroes.get(i).isAlive()) {
				ret = true;
			}
		}
		return ret;
	}
	
	public Monster[] choseMonsters(double maxlevel, int numNeeded) {
		Monster[] monsteys = new Monster[numNeeded];
		Random rand = new Random();
		int i =0;
		while(i<numNeeded) {
			int ri = rand.nextInt(monsters.size());
			if(monsters.get(ri).getLevel() <= maxlevel) {
				monsteys[i] = monsters.get(ri);
				i++;
			}
		}
		return monsteys;
		
	}
	
	public void chooseHeroes() throws FileNotFoundException {
		List<Hero> heroOptions = generateHereos();
		System.out.println("How many Heroes would you like to play with? (Must be between 1-3)");
		String num = userResponse.next();
		boolean valid = false;
		int numberOfHeroes = 1;
		while(!valid) {
			if(num.compareTo("1")==0) {
				numberOfHeroes = 1;
				valid = true;
			}
			else if(num.compareTo("2")==0) {
				numberOfHeroes = 2;
				valid = true;
			}
			else if(num.compareTo("3")==0) {
				numberOfHeroes = 3;
				valid = true;
			}
			else {
				System.out.println("Im sorry thats not a valid number please pick a number between 1 and 3");
				num = userResponse.next();
			}
			
		}
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
			System.out.println("Type the number of the hero you wish to add to your team");
			int heronum = userResponse.nextInt();
			while(heronum < 0 || heronum > heroOptions.size()) {
				System.out.println("Im sorry that is not a valid option must be the number of a hero displayed between (0-"+ heroOptions.size()+ ")");
				heronum = userResponse.nextInt();
			}
			Hero h = heroOptions.get(heronum);
			heroes.add(h);
		}
		
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

}
