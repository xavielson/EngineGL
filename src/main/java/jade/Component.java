package jade;

public abstract class Component {

    private static int ID_COUNTER = 0;
    private int uid = -1;


    public transient GameObject gameObject = null;

    public Component(){



    }

    public static void init(int maxId) {

        ID_COUNTER = maxId;

    }

    public void start() {

    }

    public void update(double dt) {

    }

    public void imgui() {

    }

    public int getUid() {
        return uid;
    }

    public void generateUid () {
        if (uid == -1) {
            this.uid = ID_COUNTER++;
            System.out.println("Created Component ID " + (ID_COUNTER - 1) + ". (generate)");
        }

    }
}
