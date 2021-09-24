package Components;

import imgui.ImGui;
import jade.Component;
import jade.MouseListener;
import jade.Transform;
import org.joml.Vector2f;
import org.joml.Vector4f;
import renderer.Texture;
import util.AssetPool;

public class SpriteRenderer extends Component {

    private Vector4f color = new Vector4f(1,1,1,1);
    private Sprite sprite = new Sprite();

    private transient Transform lastTransform;
    private transient boolean isDirty = true;
    private boolean isUnplaced = false;

    public SpriteRenderer() {
        System.out.println("nadaaaa");
    }

    @Override
    public void start() {
        if (this.sprite.getTexture() != null) {
            System.out.println("spr.getTexture is not null on SpriteRenderer" );
            this.sprite.setTexture(AssetPool.getTexture(this.sprite.getTexture().getFilepath()));
        }
        this.lastTransform = gameObject.transform.copy();
    }

    @Override
    public void update(double dt) {

        if (!this.lastTransform.equals(this.gameObject.transform)) {
            this.gameObject.transform.copy(this.lastTransform);
            isDirty = true;
        }
        if (isUnplaced) {

            this.gameObject.transform.position.x = (int)((MouseListener.getOrthoX())/48) * 48;
            this.gameObject.transform.position.y = (int)((MouseListener.getOrthoY())/48) * 48;

            if (MouseListener.mouseButtonDown(0)){
                isUnplaced = false;
            }
        }
    }

    @Override
    public void imgui() {
        float[] imColor = {color.x, color.y, color.z, color.w};
        ImGui.begin("Test Window");
        if (ImGui.colorPicker4("Color Picker: ", imColor)) {
            this.color.set(imColor[0], imColor[1], imColor[2], imColor[3]);
            isDirty = true;
        }
        ImGui.text("Some Random Text");
        ImGui.end();
    }

    public Vector4f getColor() {
        return this.color;
    }

    public Texture getTexture() {
        return sprite.getTexture();
    }

    public Vector2f[] getTexCoords() {
        return sprite.getTexCoords();
    }

    public void setSprite(Sprite sprite) {
        this.sprite = sprite;
        this.isDirty = true;
    }

    public void setColor(Vector4f color) {
        if(!this.color.equals(color)) {
            this.isDirty = true;
            this.color.set(color);
        }
    }

    public boolean isDirty() {
        return this.isDirty;
    }

    public void setClean() {
        this.isDirty = false;
    }

    public boolean isUnplaced() {
        return isUnplaced;
    }

    public void setUnplaced(boolean isPlaced) {
        isUnplaced = isPlaced;
    }
}
