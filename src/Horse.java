
public class Horse extends Animal {

	public Horse(String name) {
		super(name);
	}

	@Override
	public String getSound() {
		return "Neigh";
	}

	@Override
	public boolean hasWings() {
		return false;
	}

	@Override
	public int getNumLegs() {
		return 4;
	}

}
