package examples;

public class Foo {

    @Override
    public boolean equals(Object obj) {
        Foo toCompare = (Foo)obj;
        return toCompare == this;
    }

}