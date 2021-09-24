package jade;

import org.lwjgl.Version;
import org.lwjgl.glfw.GLFWErrorCallback;
import org.lwjgl.opengl.GL;
import renderer.DebugDraw;

import static org.lwjgl.glfw.Callbacks.glfwFreeCallbacks;
import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryUtil.NULL;

public class Window {

    private int width, height;
    private String title;
    private long glfwWindow;
    private ImGuiLayer imGuiLayer;

    private static Window window = null;

    private static Scene currentScene;

    private Window() {

        this.width = 1920;
        this.height = 1080;
        this.title = "MyEngine";

    }

    public static void changeScene (int newScene) {

        switch (newScene){
            case 0:
                currentScene = new LevelEditorScene();
                break;
            case 1:
                currentScene = new LevelScene();
                break;
            case 2:
                currentScene = new LevelSceneTest();
                break;
            default:
                assert false : "Unknown scene '" + newScene + "'";
        }
        currentScene.load();
        currentScene.init();
        currentScene.start();

    }

    public static Window get() {
        if(Window.window == null) {
            Window.window = new Window();
        }
        return Window.window;
    }

    public static Scene getScene() {
        return get().currentScene;
    }

    public void run(){

        System.out.println("Hello LWJGL " + Version.getVersion() + "!");

        init();
        loop();

        // Free the memory

        glfwFreeCallbacks(glfwWindow);
        glfwDestroyWindow(glfwWindow);

        // Terminate
        glfwTerminate();
        glfwSetErrorCallback(null).free();
    }

    public void init() {

        // Setup Error Callback
        GLFWErrorCallback.createPrint(System.err).set();

        // Initialize
        if (!glfwInit()) {
            throw new IllegalStateException("Unable to Init GLFW.");
        }

        // Configure
        glfwDefaultWindowHints();
        glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
        glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
        glfwWindowHint(GLFW_MAXIMIZED, GLFW_TRUE);

        // Create
        glfwWindow = glfwCreateWindow(this.width, this.height, this.title, NULL, NULL);
        if (glfwWindow == NULL) {
            throw new IllegalStateException("Failed to create window.");
        }

        // Set mouse and keyboard callbacks
        glfwSetCursorPosCallback(glfwWindow, MouseListener::mousePosCallback);
//        glfwSetMouseButtonCallback(glfwWindow, MouseListener::mouseButtonCallback);
//        glfwSetScrollCallback(glfwWindow, MouseListener::mouseScrollCallback);

//        glfwSetKeyCallback(glfwWindow, KbListener::keyCallback);

        glfwSetWindowSizeCallback(glfwWindow, (w, newWidth, newHeight) -> {
            window.setWidth(newWidth);
            window.setHeight(newHeight);

        });

        // Make OpenGL context current
        glfwMakeContextCurrent(glfwWindow);
        // V-Sync
        glfwSwapInterval(1);
        // Make window visible
        glfwShowWindow(glfwWindow);

        // This line is critical for LWJGL's interoperation with GLFW's
        // OpenGL context, or any context that is managed externally.
        // LWJGL detects the context that is current in the current thread,
        // creates the GLCapabilities instance and makes the OpenGL
        // bindings available for use.
        GL.createCapabilities();

        glEnable(GL_BLEND);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

        this.imGuiLayer = new ImGuiLayer(glfwWindow);
        this.imGuiLayer.initImGui();

        // Set initial Scene
        Window.changeScene(0);
    }

    public void loop() {
        float beginTime = (float)glfwGetTime();
        float endTime;
        float dt = -1.0f;


        while (!glfwWindowShouldClose(glfwWindow)) {

            // Poll events
            glfwPollEvents();

            DebugDraw.beginFrame();

            glClearColor(1f,1f,1f,1.0f);
            glClear(GL_COLOR_BUFFER_BIT);

            if (dt>=0){
                DebugDraw.draw();
                currentScene.update(dt);
            }

            this.imGuiLayer.update(dt, currentScene);
            glfwSwapBuffers(glfwWindow);


            endTime = (float)glfwGetTime();
            dt = endTime - beginTime;
            beginTime = endTime;

        }
        currentScene.saveExit();
    }

    public static int getWidth() {
        return get().width;

    }

    public static int getHeight() {
        return get().height;

    }

    public static void setWidth(int newWidth) {
        get().width = newWidth;
    }

    public static void setHeight(int newHeight) {
        get().height = newHeight;
    }

    public long getGlfwWindow() {
        return get().glfwWindow;
    }

    public ImGuiLayer getImGuiLayer() {
        return get().imGuiLayer;
    }
}
