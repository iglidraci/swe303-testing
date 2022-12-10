package examples;

public class Clonie implements Cloneable {
	 @Override
    public Object clone() throws CloneNotSupportedException {
		 Clonie clone = new Clonie();
        return clone;
    }
}
