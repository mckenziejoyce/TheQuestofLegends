
public class LivingCreature {
	private int hpCap = 1000000000;
	private String name;
	private double hp;
	private double level;
	private boolean isAlive;
	
	public LivingCreature() {
		name = "";
		hp = 0;
		level = 0;
		isAlive = true;
	}
	
	public LivingCreature(String name) {
		this.name = name;
		hp = 0;
		level = 0;
		isAlive = true;
	}
	public LivingCreature(String name, double hp, double level) {
		this.name = name;
		this.hp = hp;
		this.level = level;
		isAlive = true;
	}
	public String getName() {
		return name;
	}
	public double getLevel() {
		return level;
	}
	public double getHP() {
		return hp;
	}
	public boolean isAlive() {
		return isAlive;
	}
	public void setHP() {
		this.hp = level * 100;
	}
	protected void setHP(double n) {
		if(n < 0 || n > hpCap) {
			hp=0;
			return;
		}
		hp = n;
	}
	public void setLevel(double b) {
		if(b < 0) {
			throw new IllegalArgumentException();
		}
		this.level = b;
		setHP();
	}
	public void setName(String b) {
		this.name = b;
	}
	public void decreaseHP(double d) {
		if(this.hp -d <= 0) {
			setHP(0);
			this.isAlive = false;
		}
		setHP(this.hp -d);
	}
	public void increaseHP(double x) {
		setHP(hp+x);
	}
	
}
