package examples;

public class NoClonie {
	@Override
    public Object clone() {
        return new NoClonie();
    }
}
