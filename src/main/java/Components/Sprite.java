package Components;

import org.joml.Vector2f;
import renderer.Texture;

public class Sprite {
    private Texture texture = null;
    private Vector2f[] texCoords = {
            new Vector2f(1,1),
            new Vector2f(1,0),
            new Vector2f(0,0),
            new Vector2f(0,1)};
    private float width, height;

    public Sprite() {

    }

    /*    public Sprite(Texture texture) {
        this.texture = texture;
        Vector2f[] texCoords = {
                new Vector2f(1,1),
                new Vector2f(1,0),
                new Vector2f(0,0),
                new Vector2f(0,1),
        };
        this.texCoords = texCoords;
    }*/

//    public Sprite(Texture texture, Vector2f[] texCoords) {
//        this.texture = texture;
//        this.texCoords = texCoords;
//    }

    public Texture getTexture() {
        return this.texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public Vector2f[] getTexCoords() {
        return this.texCoords;
    }

    public void setTexCoords(Vector2f[] texCoords) {
        this.texCoords = texCoords;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public int getTexId() {
        return texture == null ? -1 : texture.getTexID();
    }
}