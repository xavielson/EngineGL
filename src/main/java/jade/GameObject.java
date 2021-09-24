package jade;

import org.joml.Vector2f;

import java.util.ArrayList;
import java.util.List;

public class GameObject {

    private transient static int ID_COUNTER = 0;
    private int uid = -1;

    private String name;
    private List<Component> components;
    public transient Transform transform;
    private int zIndex = 0;

    public GameObject(String name) {

        this.name = name;
        this.components = new ArrayList<>();
        //this.transform = new Transform(new Vector2f(100,100), new Vector2f(50, 50));
        this.uid = ID_COUNTER++;
        System.out.println("Created " + name + " " + uid);

    }

    public static void init(int maxId) {

        ID_COUNTER = maxId;

    }

    public <T extends Component> T getComponent(Class<T> componentClass) {
        for (Component c : components) {
            if (componentClass.isAssignableFrom(c.getClass())) {
                try{
                    return componentClass.cast(c);
                } catch (ClassCastException e) {
                    e.printStackTrace();
                    assert false : "Error: Casting component.";
                }
            }
        }
        return null;
    }

    public <T extends Component> void removeComponent(Class<T> componentClass) {
        for (int i=0; i < components.size(); i++) {
            Component c = components.get(i);
            if (componentClass.isAssignableFrom(c.getClass())) {
                components.remove(i);
                return;
            }
        }
    }

    public void addComponent(Component c) {
        c.generateUid();
        this.components.add(c);
        c.gameObject = this;
    }

    public void update (double dt) {
        for (int i=0; i < components.size(); i++) {
            components.get(i).update(dt);
        }
    }

    public void start () {
        for (int i=0; i < components.size(); i++) {
            components.get(i).start();
        }
    }

    public void imgui() {
        for(Component c : components) {
            c.imgui();
        }
    }

    public String getName() {
        return name;
    }

    public int zIndex() {
        return zIndex;
    }

    public List<Component> getComponents() {
        return components;
    }

    public void setComponents(List<Component> components) {
        this.components = components;
    }

    public int getUid() {
        return uid;
    }

}
