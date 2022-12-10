public class StaticCodeExample {
    public static void main(String[] args) {
        StaticCodeExample obj = new StaticCodeExample();
        obj.test();
    }
    private int abc;

    private String ip = "127.0.0.1";

    public void test() {

        String[] field = {"a", "b", "c", "s", "e"};

        //concatenates strings using + in a loop
        String s = "";
        for (int i = 0; i < field.length; ++i) {
            s = s + field[i];
        }

        Object obj = null;
        System.out.println(obj.toString());

        System.out.println(ip);

    }

}
