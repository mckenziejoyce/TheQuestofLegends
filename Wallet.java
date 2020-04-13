
public class Wallet {
	private double coins;
	
	public Wallet() {
		setCoins(0);
	}
	public Wallet(int c) {
		this();
		setCoins(c);
	}
	public Wallet(double d) {
		setCoins(d);
	}
	
	
	public void setCoins(double c) {
		if(c<0) {
			throw new IllegalArgumentException();
		}
		coins = c;
	}
	
	
	public double getCoins() {
		return coins;
	}
	public void increaseCoins(double c) {
		setCoins(coins+c);
	}
	public void decreaseCoins(double c) {
		setCoins(coins-c);
	}
	
}
