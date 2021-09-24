package renderer;

import Components.SpriteRenderer;
import jade.GameObject;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Renderer {
    private final int MAX_BATCH_SIZE = 1000;
    private List<RenderBatch> batches;

    public Renderer () {
        this.batches = new ArrayList<>();
    }

    public void add (GameObject go) {
        SpriteRenderer spr = go.getComponent(SpriteRenderer.class);
        if (spr != null) {
            add(spr);
        }
    }

    public void add (SpriteRenderer sprite) {
        boolean added = false;

        for (RenderBatch batch : batches ) {

            System.out.println("Working on batch " + batch.getRenderBatchId());

            System.out.println("Number of Batches: " + batches.size());

            System.out.println("has room: " + batch.hasRoom());
            System.out.println("zIndex equals: " + (batch.zIndex() == sprite.gameObject.zIndex()));
            System.out.println("==========================================");
            System.out.println("==========================================");

            if (batch.hasRoom() && batch.zIndex() == sprite.gameObject.zIndex()) {
                Texture tex = sprite.getTexture();

                System.out.println("tex null: " + (tex == null));
                System.out.println("has Texture tex: " + batch.hasTexture(tex));
                System.out.println("has Texture room: " + batch.hasTextureRoom());
                System.out.println("==========================================");
                System.out.println("==========================================");

                if (tex == null || (batch.hasTexture(tex) || batch.hasTextureRoom())) {
                    batch.addSprite(sprite);
                    added = true;
                    System.out.println("Added to Batch " + batch + " " + sprite.gameObject.getUid());
                    break;
                }
            }
        }

        if (!added) {
            RenderBatch newBatch = new RenderBatch(MAX_BATCH_SIZE, sprite.gameObject.zIndex());
            newBatch.start();
            batches.add(newBatch);

            newBatch.addSprite(sprite);
            System.out.println("Added to New Batch " + newBatch + " " + sprite.gameObject.getUid());
            Collections.sort(batches);
        }
    }

    public void render() {
         for (RenderBatch batch : batches){
             batch.render();
         }
    }
}
