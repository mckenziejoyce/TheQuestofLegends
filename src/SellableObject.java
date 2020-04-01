
public class SellableObject {
	private String name;
	private double price;
	private int minLevelReq;
	
	public String getName() {
		return name;
	}
	public double getPrice() {
		return price;
	}
	public int getLevelReq() {
		return minLevelReq;
	}
	public void setName(String s) {
		name = s;
	}
	public void setPrice(double p) {
		if(p < 0) {
			throw new IllegalArgumentException();
		}
		price =p;
	}
	public void setLevelReq(int i) {
		if(i < 0) {
			throw new IllegalArgumentException();
		}
		minLevelReq = i;
	}
	public boolean equals(SellableObject w) {
		boolean equals = false;
		if(name == w.name && price == w.price && minLevelReq == w.minLevelReq && this.getClass() == w.getClass()) {
			equals = true;
		}
		return equals;
	}
}
