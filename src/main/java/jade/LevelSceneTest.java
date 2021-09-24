package jade;

import org.joml.Vector2f;

import java.awt.event.KeyEvent;

public class LevelSceneTest extends Scene{


    public void init() {

        this.camera = new Camera(new Vector2f(0,0));

    }

    @Override
    public void update(float dt) {


        if (KbListener.isKeyPressed(KeyEvent.VK_SPACE)){
            Window.changeScene(1);
        }

        for (GameObject go : this.gameObjects) {
            go.update(dt);
        }
        this.renderer.render();

    }


}
