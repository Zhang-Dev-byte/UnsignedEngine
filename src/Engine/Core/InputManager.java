package Engine.Core;

import static org.lwjgl.glfw.GLFW.*;

import java.nio.DoubleBuffer;

import org.joml.Vector2f;
import org.lwjgl.BufferUtils;

public class InputManager {
	private long window;
	private int[] keyMap;
	public InputManager(long window) {
		this.window = window;
		this.keyMap = new int[]{
				GLFW_KEY_1
				, GLFW_KEY_2
				, GLFW_KEY_3
				, GLFW_KEY_4
				, GLFW_KEY_5
				, GLFW_KEY_6
				, GLFW_KEY_7
				, GLFW_KEY_8
				, GLFW_KEY_9
				, GLFW_KEY_0

				, GLFW_KEY_Q
				, GLFW_KEY_W
				, GLFW_KEY_E
				, GLFW_KEY_R
				, GLFW_KEY_T
				, GLFW_KEY_Y
				, GLFW_KEY_U
				, GLFW_KEY_I
				, GLFW_KEY_O
				, GLFW_KEY_P

				, GLFW_KEY_A
				, GLFW_KEY_S
				, GLFW_KEY_D
				, GLFW_KEY_F
				, GLFW_KEY_G
				, GLFW_KEY_H
				, GLFW_KEY_J
				, GLFW_KEY_K
				, GLFW_KEY_L

				, GLFW_KEY_Z
				, GLFW_KEY_X
				, GLFW_KEY_C
				, GLFW_KEY_V
				, GLFW_KEY_B
				, GLFW_KEY_N
				, GLFW_KEY_M

				, GLFW_KEY_SPACE
				, GLFW_KEY_LEFT_CONTROL
				, GLFW_KEY_LEFT_SHIFT
				, GLFW_KEY_LEFT_ALT

				, GLFW_MOUSE_BUTTON_LEFT
				, GLFW_MOUSE_BUTTON_RIGHT
				};
	}

	public boolean GetKey(int key) {
		if(glfwGetKey(window, key) == GLFW_PRESS) {
			return true;
		}else {
			return false;
		}
	}
	public boolean GetMouseInput(int button) {
		if(glfwGetMouseButton(window,button) == GLFW_PRESS) {
			return true;
		}else {
			return false;
		}
	}
	public Vector2f GetMousePosition() {
	    DoubleBuffer X = BufferUtils.createDoubleBuffer(1), Y = BufferUtils.createDoubleBuffer(1);
		glfwGetCursorPos(window, X, Y);

		return new Vector2f((float)(X.get(0)), (float)(Y.get(0)));
	}
	public boolean NoKey() {
		int length = 0;
		for(int key : keyMap) {
			if(!(glfwGetKey(window, key) == GLFW_PRESS)) {
				length++;
			}
		}

		if(length == keyMap.length) {
			return true;
		}else {
			return false;
		}
	}
	public int GetKey() {
		for(int key : keyMap) {
			if((glfwGetKey(window, key) == GLFW_PRESS)) {
				return key;
			}
		}
		return 0;
	}
}
