package jade;

import Components.Sprite;
import Components.SpriteRenderer;
import Components.SpriteSheet;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import imgui.ImGui;
import imgui.ImVec2;
import org.joml.Vector2f;
import org.joml.Vector3f;
import renderer.DebugDraw;
import util.AssetPool;

import java.awt.event.KeyEvent;

public class LevelEditorScene extends Scene {

    private GameObject obj1;
    private SpriteSheet terrainSpriteSheet;
    private SpriteSheet creatureSpriteSheet;

    public LevelEditorScene() {

    }

    public void loadResources() {
        for (int i = 0; i < 40; i++ ) {
            DebugDraw.addLine2D(new Vector2f(i * 48, 0),
                    new Vector2f(i * 48, 48 * 21),
                    new Vector3f(1, 0, 0), 5000);

        }
        for (int j = 0; j < 21; j++) {
            DebugDraw.addLine2D(new Vector2f(0, j * 48),
                    new Vector2f(48 * 40, j * 48),
                    new Vector3f(1, 0, 0), 5000);
        }
    }

    @Override
    public void init() {
        AssetPool.getShader("assets/shaders/default.glsl");
        AssetPool.addSpritesheet("assets/images/pic4.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/pic4.png"),
                        24, 24, 0));

        AssetPool.addSpritesheet("assets/images/pic3.png",
                new SpriteSheet(AssetPool.getTexture("assets/images/pic3.png"),
                        24, 24, 0));

        loadResources();

        this.camera = new Camera(new Vector2f(0, 0));
        terrainSpriteSheet = AssetPool.getSpritesheet("assets/images/pic4.png");
        creatureSpriteSheet = AssetPool.getSpritesheet("assets/images/pic3.png");

        if (levelLoaded) {
            return;
        }

        for (int i = 21; i < 39; i++) {

            GameObject objToAdd = new GameObject("Generated G.O.");

            objToAdd.addComponent(new Transform(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), new Vector2f(50, 50)));
            objToAdd.transform = objToAdd.getComponent(Transform.class);
            objToAdd.transform.position.x = 50 * i - 500;
            objToAdd.transform.position.y = 200;
            SpriteRenderer spriteRenderer =  new SpriteRenderer();
            spriteRenderer.setSprite(creatureSpriteSheet.getSprite(i));
            objToAdd.addComponent(spriteRenderer);
            this.addGameObjectToScene(objToAdd);

        }
        for (int i = 41; i < 59; i++) {

            GameObject objToAdd = new GameObject("Generated G.O.");

            objToAdd.addComponent(new Transform(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), new Vector2f(50, 50)));
            objToAdd.transform = objToAdd.getComponent(Transform.class);
            objToAdd.transform.position.x = 50 * i - 1500;
            objToAdd.transform.position.y = 500;
            SpriteRenderer spriteRenderer =  new SpriteRenderer();
            spriteRenderer.setSprite(creatureSpriteSheet.getSprite(i));
            objToAdd.addComponent(spriteRenderer);
            this.addGameObjectToScene(objToAdd);

        }



//        obj1 = new GameObject("Obj1", new Transform(
//                new Vector2f(100, 100), new Vector2f(50, 50)), 1);
//        obj1.addComponent(new SpriteRenderer(terrainSpriteSheet.getSprite(35)));
//        this.addGameObjectToScene(obj1);

//        GameObject obj2 = new GameObject("Obj2", new Transform(
//                new Vector2f(120, 120), new Vector2f(50, 50)), 0);
//        SpriteRenderer obj2SpriteRenderer = new SpriteRenderer();
//        //obj2Sprite.setColor(new Vector4f(1,0,0,1));
//        obj2SpriteRenderer.setSprite(creatureSpriteSheet.getSprite(35));
//
//
//        obj2.addComponent(obj2SpriteRenderer);
//        this.addGameObjectToScene(obj2);
//        this.activeGameObject = obj2;






//        String serialized = gson.toJson(obj2);
//        GameObject obj = gson.fromJson(serialized, GameObject.class);
//        System.out.println("the name is " + obj.getName());



    }

    private float timePassed;

    @Override
    public void update(float dt) {
        if (KbListener.isKeyPressed(KeyEvent.VK_SPACE)){
            System.out.println(KbListener.isKeyPressed(KeyEvent.VK_SPACE));
            Window.changeScene(1);
            KbListener.unpressKey(KeyEvent.VK_SPACE);
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();

        timePassed += dt;
        if (timePassed > 3.0f) {
            System.out.println("(" + MouseListener.getOrthoX() + ", " + MouseListener.getOrthoY() + ")");
            System.out.println("FPS : " + 1.0/dt);
            timePassed = 0f;
        }
    }

    @Override
    public void imgui() {

        createSpriteSelectorWindow(terrainSpriteSheet);
        createSpriteSelectorWindow(creatureSpriteSheet);
        ImGui.setWindowFocus(null);
    }


    private void createSpriteSelectorWindow(SpriteSheet spriteSheet) {

        ImGui.begin(spriteSheet.getName());

        ImGui.setWindowSize(500f, 500f);

        ImVec2 windowPos = new ImVec2();
        ImGui.getWindowPos(windowPos);

        ImVec2 windowSize = new ImVec2();
        ImGui.getWindowSize(windowSize);

        ImVec2 itemSpacing = new ImVec2();
        ImGui.getStyle().getItemSpacing(itemSpacing);

        float windowX2 = windowPos.x + windowSize.x;
        for (int i = 0; i < spriteSheet.size(); i++) {

            Sprite sprite = spriteSheet.getSprite(i);
            float spriteWidth = sprite.getWidth() * 2;
            float spriteHeight = sprite.getHeight() * 2;
            int id = sprite.getTexId();
            Vector2f[] texCoords = sprite.getTexCoords();

            ImGui.pushID(i);
            if(ImGui.imageButton(id, spriteWidth, spriteHeight,
                    texCoords[2].x, texCoords[0].y, texCoords[0].x, texCoords[2].y)) {

            }
            if (ImGui.isItemHovered()) {
                ImGui.beginTooltip();
                ImGui.text("" + i);
                ImGui.endTooltip();
            }
            ImGui.popID();

            if (ImGui.isItemClicked()) {
                createSpriteInEditor(spriteSheet, i);
            }

            ImVec2 lastButtonPos = new ImVec2();
            ImGui.getItemRectMax(lastButtonPos);
            float lastButtonX2 = lastButtonPos.x;
            float nextButtonX2 = lastButtonX2 + itemSpacing.x + spriteWidth;
            if (i + 1 < spriteSheet.size() && nextButtonX2 < windowX2) {
                ImGui.sameLine();
            }
        }
        ImGui.end();
    }

    private void createSpriteInEditor(SpriteSheet ss, int index) {

        GameObject objToAdd = new GameObject("Generated G.O.");


        objToAdd.addComponent(new Transform(new Vector2f(MouseListener.getOrthoX(), MouseListener.getOrthoY()), new Vector2f(50, 50)));
        objToAdd.transform = objToAdd.getComponent(Transform.class);

        SpriteRenderer spriteRenderer =  new SpriteRenderer();
        spriteRenderer.setUnplaced(true);
        spriteRenderer.setSprite(ss.getSprite(index));

        objToAdd.addComponent(spriteRenderer);

        this.addGameObjectToScene(objToAdd);

        activeGameObject = objToAdd;
    }
}
