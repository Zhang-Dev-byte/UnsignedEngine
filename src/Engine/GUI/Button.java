package Engine.GUI;

import org.joml.Vector2f;
import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;
import org.lwjgl.BufferUtils;

import Engine.Core.InputManager;

public class Button implements java.io.Serializable {

	public GUIFrame background;
	public Vector2f size;

	public Button(GUIFrame background, Vector2f size) {
		this.background = background;
		this.size = size;
	}

	public boolean CheckClick(InputManager manager) {
		if(manager.GetMouseInput(GLFW_MOUSE_BUTTON_LEFT)){
			Vector2f nMousePos = manager.GetMousePosition();
			Vector2f mousePos = new Vector2f(-(nMousePos.x-(500+(250 * background.scale.x))),-(nMousePos.y-(500+(250* background.scale.y))));
			System.out.println(manager.GetMousePosition());
			Vector2f pos = new Vector2f(background.position.x, background.position.y);
			if(mousePos.x < pos.x + size.x && mousePos.x > pos.x &&
					mousePos.y < pos.y + size.y && mousePos.y  > pos.y) {
				return true;
			}else {
				return false;
			}
		}else {
			return false;
		}
	}
	public void Render() {
		background.Render();
	}
}
