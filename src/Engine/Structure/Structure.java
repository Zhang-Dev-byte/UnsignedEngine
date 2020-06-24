package Engine.Structure;

import java.util.ArrayList;
import java.util.List;

import Engine.GUI.Button;
import Engine.GUI.GUIFrame;
import Engine.Rendering.Entity;

public class Structure implements java.io.Serializable {
	public List<Entity> entities = new ArrayList<Entity>();
	public List<GUIFrame> frames = new ArrayList<GUIFrame>();
	public List<Button> buttons = new ArrayList<Button>();

	public void AddObject(Entity entity) {
		entities.add(entity);
	}
	public void AddFrame(GUIFrame frame) {
		frames.add(frame);
	}
	public void AddButton(Button button) {
		buttons.add(button);
	}
	public void Render() {
		for(Entity entity : entities) {
			entity.Update();
			entity.Render();
		}
		for(GUIFrame frame : frames) {
			frame.Render();
		}
		for(Button button : buttons) {
			button.Render();
		}
	}
}

