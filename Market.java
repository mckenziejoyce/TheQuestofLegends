import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Market {
	private List<SellableObject> sellableItems;
	private List<SellableObject> buyableItems;
	private String buyables = "buyables.txt";
	Scanner userResponse = new Scanner( System.in );
	
	public Market() {
		sellableItems = new ArrayList<SellableObject>();
		buyableItems = new ArrayList<SellableObject>();
	}
	public Market(Hero h) {
		this();
		setSellableItems(h);
		try {
			generateBuyables();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
	}
	public Market(Hero h, List<SellableObject> buyable) {
		setSellableItems(h);
		buyableItems = buyable;
	}
	
	
	public void setSellableItems(Hero h) {
		sellableItems.clear();
		if(h.getWeapons() != null) {
			sellableItems.addAll(h.getWeapons());
		}
		if(h.getArmors() != null) {
			sellableItems.addAll(h.getArmors());
		}
		if(h.getPotions() != null) {
			sellableItems.addAll(h.getPotions());
		}
		if(h.getSpells() != null) {
			sellableItems.addAll(h.getSpells());
		}
	}
	public List<SellableObject> getSellableItems(){
		return sellableItems;
	}
	public List<SellableObject> getBuyableItems(){
		return buyableItems;
	}
	public String toString() {
		String ret = "The items avilable for you to sell are: \n";
		for(SellableObject s: sellableItems) {
			ret += s;
		}
		ret += "\n The items avilable for you to buy are: \n";
		for(SellableObject s: buyableItems) {
			ret += s;
		}
		return ret;
	}
	public void generateBuyables() throws FileNotFoundException{
		List<String> strRep = new ArrayList<String>();
		File file = new File(buyables); 
	    Scanner sc = new Scanner(file);
	    sc.useDelimiter("\n");
	    while(sc.hasNext()){  
	    	strRep.add(sc.next());  
        }  
        sc.close();
        for(int i = 0; i < strRep.size(); i++) {
        	String[] tokens = strRep.get(i).split(",");
        	String type = tokens[0];
        	String name = tokens[1];
        	double price = Integer.parseInt(tokens[2]);
        	int minlevelreq = Integer.parseInt(tokens[3]);
        	if(type.compareToIgnoreCase("weapon")==0) {
        		double baseDamage = Integer.parseInt(tokens[4]);
        		int numOfHands = Integer.parseInt(tokens[5]);
        		Weapon w = new Weapon(name,price,minlevelreq,baseDamage,numOfHands);
        		buyableItems.add(w);
        	}
        	if(type.compareToIgnoreCase("armor")==0) {
        		double protec = Integer.parseInt(tokens[4]);
        		Armor a = new Armor(name,price,minlevelreq,protec);
        		buyableItems.add(a);
        	}
        	if(type.compareToIgnoreCase("potion")==0) {
        		double ab = Integer.parseInt(tokens[4]);
        		double sb = Integer.parseInt(tokens[5]);
        		double db = Integer.parseInt(tokens[6]);
        		double hpb = Integer.parseInt(tokens[7]);
        		double mb = Integer.parseInt(tokens[8]);
        		Potion p = new Potion(name,price,minlevelreq,ab,sb,db,hpb,mb);
        		buyableItems.add(p);
        	}
        	if(type.compareToIgnoreCase("spell")==0) {
        		String spellType = tokens[4];
        		double damageRange =  Integer.parseInt(tokens[5]);
        		double mana = Integer.parseInt(tokens[6]);
        		double baseDamage = Integer.parseInt(tokens[7]);
        		Spell s = new Spell(name,price,minlevelreq,spellType, damageRange,mana,baseDamage);
        		buyableItems.add(s);
        	}
        }
	}
	
	public void buyObjects(Hero h) {
		System.out.println("Your Selection of items to buy: \n");
		for(int i = 0; i <buyableItems.size(); i++) {
			System.out.println("Item "+ Integer.toString(i)+":");
			System.out.println(buyableItems.get(i) + "\n");
		}
		boolean valid = false;
		while(!valid) {
			System.out.println("Type the number of the item you wish to buy");
			int itemnum=-1;
			boolean choosing= true;
			while(choosing) {
				itemnum=-1;
				String item = userResponse.next();
				try {
					itemnum= Integer.parseInt(item);
				}catch(Exception e){
					System.out.println("Invalid input. Please enter a number");
				}
				if (itemnum!=-1){
					if (itemnum >= 0 && itemnum < buyableItems.size()){
						choosing=false;
				}else{
					System.out.println("Im sorry that is not a valid option must be the number of an item displayed (between 0-"+ buyableItems.size()+ ")");
				}
				
			}
			}
			SellableObject item = buyableItems.get(itemnum);
			if(item.getLevelReq() <= h.getLevel() && item.getPrice() <= h.getMoney()) {
				System.out.println("Item has been purchased");
				valid = true;
				h.buyItem(item);
				h.decreaseCoins(item.getPrice());
				sellableItems.add(item);
				break;
			}
			else {
				System.out.println("Im sorry you dont have a high enough level/enough money for that item try again");
			}
		}
	}
	public void sellObjects(Hero h) {
		System.out.println("Your Selection of items to sell: \n");
		for(int i = 0; i <sellableItems.size(); i++) {
			System.out.println("Item "+ Integer.toString(i)+":");
			System.out.println(sellableItems.get(i) + "\n");
		}
		System.out.println("Type the number of the item you wish to sell");
		int itemnum=-1;
		boolean choosing= true;
			while(choosing) {
				itemnum=-1;
				String item = userResponse.next();
				try {
					itemnum= Integer.parseInt(item);
				}catch(Exception e){
					System.out.println("Invalid input. Please enter a number");
				}
		if (itemnum!=-1){
			if(itemnum >= 0 && itemnum < sellableItems.size()) {
				choosing=false;
			}else{
				System.out.println("Im sorry that is not a valid option must be the number of an item displayed (between 0-"+ sellableItems.size()+ ")");
			}
	}
	}
		System.out.println("Item has been sold");
		SellableObject item = sellableItems.get(itemnum);
		h.sellItem(item);
		h.increaseCoins(item.getPrice()/2);
		sellableItems.remove(item);
	}
	public void welcomeToMarket() {
		System.out.println(this);
	}
}
