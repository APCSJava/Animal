
public class Mosquito extends Animal {

	public Mosquito(String name) {
		super(name);
	}

	@Override
	public String getSound() {
		return "bzzzz";
	}

	@Override
	public boolean hasWings() {
		return true;
	}

	@Override
	public int getNumLegs() {
		return 6;
	}

}
