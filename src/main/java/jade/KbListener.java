package jade;

import static org.lwjgl.glfw.GLFW.*;

public class KbListener {

    private static KbListener instance;
    private boolean keyPressed[] = new boolean[350];

    private KbListener() {
    }

    public static KbListener get() {
        if (KbListener.instance == null) {
            KbListener.instance = new KbListener();
        }
        return KbListener.instance;
    }

    public static void keyCallback(long window, int key, int scancode, int action, int mods) {

        if (action == GLFW_PRESS) {
            get().keyPressed[key] = true;
        } else if (action == GLFW_RELEASE) {
            get().keyPressed[key] = false;
        }
        System.out.println("Keycallback");
    }

    public static boolean isKeyPressed (int keyCode) {
        return get().keyPressed[keyCode];
    }

    public static void unpressKey (int keyCode) {
        get().keyPressed[keyCode] = false;
    }

}
