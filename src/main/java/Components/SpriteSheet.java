package Components;

import org.joml.Vector2f;
import renderer.Texture;

import java.util.ArrayList;
import java.util.List;

public class SpriteSheet {

    private Texture texture;
    private List<Sprite> sprites;
    private String name;

    public SpriteSheet (Texture texture, int spriteWidth, int spriteHeight, int spacing) {

        this.sprites = new ArrayList<>();
        this.texture = texture;
        this.name = texture.getFilePath();
        int numSprites = (this.texture.getWidth() / spriteWidth) * (this.texture.getHeight() / spriteHeight);

        int currentX = 0;
        int currentY = texture.getHeight() - spriteHeight;
        for (int i = 0; i < numSprites; i++) {
            float topY = (currentY + spriteHeight) / (float)texture.getHeight();
            float rightX = (currentX + spriteWidth) / (float)texture.getWidth();
            float leftX = currentX / (float)texture.getWidth();
            float bottomY = currentY / (float)texture.getHeight();

            Vector2f[] texCoords = {
                    new Vector2f(rightX,topY),
                    new Vector2f(rightX,bottomY),
                    new Vector2f(leftX,bottomY),
                    new Vector2f(leftX,topY),

            };
            Sprite sprite = new Sprite();
            sprite.setTexture(this.texture);
            sprite.setTexCoords(texCoords);
            sprite.setWidth(spriteWidth);
            sprite.setHeight(spriteHeight);
            this.sprites.add(sprite);

            currentX += spriteWidth + spacing;
            if (currentX >= texture.getWidth()) {
                currentX = 0;
                currentY -= spriteHeight + spacing;
            }
        }
    }
    public Sprite getSprite(int index) {
        return this.sprites.get(index);
    }

    public int size() {
        return sprites.size();
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
