package Engine.Lua;

import static org.lwjgl.glfw.GLFW.*;

import java.io.File;
import java.io.FileReader;
import java.util.Random;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.luaj.vm2.*;

import Engine.Core.InputManager;
import Engine.Rendering.Camera;
import Engine.Rendering.Entity;
import Engine.Structure.Structure;

public class LuaScript {
	public String path;
	public Structure struct;
	public InputManager input;
	public Camera cam;
	public Random random;
	ScriptEngineManager mgr;
	ScriptEngine e;

	public LuaScript(String path, Structure struct, InputManager input, Camera cam, Random random) throws Throwable {
		this.path = path;
		this.struct = struct;
		this.input = input;
		this.cam = cam;
		this.random = random;
		this.mgr = new ScriptEngineManager();
		this.e = mgr.getEngineByName("luaj");
	}

	public void Run() throws Exception {
		e.put("Texture", "Engine.Rendering.Texture");
		e.put("Shader", "Engine.Rendering.Shader");
		e.put("Mesh", "Engine.Rendering.Mesh");
		e.put("Loader", "Engine.Rendering.Loader");
		e.put("Entity", "Engine.Rendering.Entity");
		e.put("Structure", "Engine.Structure.Structure");
		e.put("InputManager", "Engine.Core.InputManager");
		e.put("Vector3f", "org.joml.Vector3f");
		e.put("Vector2f", "org.joml.Vector2f");
		e.put("Camera", "Engine.Rendering.Camera");
		e.put("PhysicsBody", "Engine.Physics.PhysicsBody");
		e.put("AABB", "Engine.Physics.AABB");
		e.put("GUIFrame", "Engine.GUI.Button");
		e.put("AudioPlayer", "Engine.Audio.AudioPlayer");
		e.put("Log", "Engine.Core.Log");

		e.put("KEY_1", GLFW_KEY_1);
		e.put("KEY_2", GLFW_KEY_2);
		e.put("KEY_3", GLFW_KEY_3);
		e.put("KEY_4", GLFW_KEY_4);
		e.put("KEY_5", GLFW_KEY_5);
		e.put("KEY_6", GLFW_KEY_6);
		e.put("KEY_7", GLFW_KEY_7);
		e.put("KEY_8", GLFW_KEY_8);
		e.put("KEY_9", GLFW_KEY_9);
		e.put("KEY_0", GLFW_KEY_0);

		e.put("KEY_Q", GLFW_KEY_Q);
		e.put("KEY_W", GLFW_KEY_W);
		e.put("KEY_E", GLFW_KEY_E);
		e.put("KEY_R", GLFW_KEY_R);
		e.put("KEY_T", GLFW_KEY_T);
		e.put("KEY_Y", GLFW_KEY_Y);
		e.put("KEY_U", GLFW_KEY_U);
		e.put("KEY_I", GLFW_KEY_I);
		e.put("KEY_O", GLFW_KEY_O);
		e.put("KEY_P", GLFW_KEY_P);

		e.put("KEY_A", GLFW_KEY_A);
		e.put("KEY_S", GLFW_KEY_S);
		e.put("KEY_D", GLFW_KEY_D);
		e.put("KEY_F", GLFW_KEY_F);
		e.put("KEY_G", GLFW_KEY_G);
		e.put("KEY_H", GLFW_KEY_H);
		e.put("KEY_J", GLFW_KEY_J);
		e.put("KEY_K", GLFW_KEY_K);
		e.put("KEY_L", GLFW_KEY_L);

		e.put("KEY_Z", GLFW_KEY_Z);
		e.put("KEY_X", GLFW_KEY_X);
		e.put("KEY_C", GLFW_KEY_C);
		e.put("KEY_V", GLFW_KEY_V);
		e.put("KEY_B", GLFW_KEY_B);
		e.put("KEY_N", GLFW_KEY_N);
		e.put("KEY_M", GLFW_KEY_M);

		e.put("KEY_SPACE", GLFW_KEY_SPACE);
		e.put("KEY_CTRL", GLFW_KEY_LEFT_CONTROL);
		e.put("KEY_SHIFT", GLFW_KEY_LEFT_SHIFT);

		e.put("MOUSE_LEFT", GLFW_MOUSE_BUTTON_LEFT);
		e.put("MOUSE_RIGHT", GLFW_MOUSE_BUTTON_RIGHT);

		e.put("Structure", struct);
		e.put("camera", cam);
		e.put("Input", input);
		e.put("Random", random);

		for(Entity en : struct.entities) {
			e.put(en.name, en);
		}

		e.eval("Unsigned = luajava");
		e.eval(new FileReader(new File(path)));
		LuaValue a = (LuaValue) e.get("Awake");
		a.call();

		e.put("Texture", "Engine.Rendering.Texture");
		e.put("Shader", "Engine.Rendering.Shader");
		e.put("Mesh", "Engine.Rendering.Mesh");
		e.put("Loader", "Engine.Rendering.Loader");
		e.put("Entity", "Engine.Rendering.Entity");
		e.put("Structure", "Engine.Structure.Structure");
		e.put("InputManager", "Engine.Core.InputManager");
		e.put("Vector3f", "org.joml.Vector3f");
		e.put("Vector2f", "org.joml.Vector2f");
		e.put("Camera", "Engine.Rendering.Camera");
		e.put("PhysicsBody", "Engine.Physics.PhysicsBody");
		e.put("AABB", "Engine.Physics.AABB");
		e.put("GUIFrame", "Engine.GUI.Button");
		e.put("AudioPlayer", "Engine.Audio.AudioPlayer");
		e.put("Log", "Engine.Core.Log");

		e.put("KEY_1", GLFW_KEY_1);
		e.put("KEY_2", GLFW_KEY_2);
		e.put("KEY_3", GLFW_KEY_3);
		e.put("KEY_4", GLFW_KEY_4);
		e.put("KEY_5", GLFW_KEY_5);
		e.put("KEY_6", GLFW_KEY_6);
		e.put("KEY_7", GLFW_KEY_7);
		e.put("KEY_8", GLFW_KEY_8);
		e.put("KEY_9", GLFW_KEY_9);
		e.put("KEY_0", GLFW_KEY_0);

		e.put("KEY_Q", GLFW_KEY_Q);
		e.put("KEY_W", GLFW_KEY_W);
		e.put("KEY_E", GLFW_KEY_E);
		e.put("KEY_R", GLFW_KEY_R);
		e.put("KEY_T", GLFW_KEY_T);
		e.put("KEY_Y", GLFW_KEY_Y);
		e.put("KEY_U", GLFW_KEY_U);
		e.put("KEY_I", GLFW_KEY_I);
		e.put("KEY_O", GLFW_KEY_O);
		e.put("KEY_P", GLFW_KEY_P);

		e.put("KEY_A", GLFW_KEY_A);
		e.put("KEY_S", GLFW_KEY_S);
		e.put("KEY_D", GLFW_KEY_D);
		e.put("KEY_F", GLFW_KEY_F);
		e.put("KEY_G", GLFW_KEY_G);
		e.put("KEY_H", GLFW_KEY_H);
		e.put("KEY_J", GLFW_KEY_J);
		e.put("KEY_K", GLFW_KEY_K);
		e.put("KEY_L", GLFW_KEY_L);

		e.put("KEY_Z", GLFW_KEY_Z);
		e.put("KEY_X", GLFW_KEY_X);
		e.put("KEY_C", GLFW_KEY_C);
		e.put("KEY_V", GLFW_KEY_V);
		e.put("KEY_B", GLFW_KEY_B);
		e.put("KEY_N", GLFW_KEY_N);
		e.put("KEY_M", GLFW_KEY_M);

		e.put("KEY_SPACE", GLFW_KEY_SPACE);
		e.put("KEY_CTRL", GLFW_KEY_LEFT_CONTROL);
		e.put("KEY_SHIFT", GLFW_KEY_LEFT_SHIFT);

		e.put("MOUSE_LEFT", GLFW_MOUSE_BUTTON_LEFT);
		e.put("MOUSE_RIGHT", GLFW_MOUSE_BUTTON_RIGHT);

		e.put("Structure", struct);
		e.put("camera", cam);
		e.put("Input", input);
		e.put("Random", random);

		for(Entity en : struct.entities) {
			e.put(en.name, en);
		}
		LuaValue s = (LuaValue) e.get("Start");
		s.call();
	}

	public void Update() {
		e.put("Texture", "Engine.Rendering.Texture");
		e.put("Shader", "Engine.Rendering.Shader");
		e.put("Mesh", "Engine.Rendering.Mesh");
		e.put("Loader", "Engine.Rendering.Loader");
		e.put("Entity", "Engine.Rendering.Entity");
		e.put("Structure", "Engine.Structure.Structure");
		e.put("InputManager", "Engine.Core.InputManager");
		e.put("Vector3f", "org.joml.Vector3f");
		e.put("Vector2f", "org.joml.Vector2f");
		e.put("Camera", "Engine.Rendering.Camera");
		e.put("PhysicsBody", "Engine.Physics.PhysicsBody");
		e.put("AABB", "Engine.Physics.AABB");
		e.put("GUIFrame", "Engine.GUI.Button");
		e.put("AudioPlayer", "Engine.Audio.AudioPlayer");
		e.put("Log", "Engine.Core.Log");

		e.put("KEY_1", GLFW_KEY_1);
		e.put("KEY_2", GLFW_KEY_2);
		e.put("KEY_3", GLFW_KEY_3);
		e.put("KEY_4", GLFW_KEY_4);
		e.put("KEY_5", GLFW_KEY_5);
		e.put("KEY_6", GLFW_KEY_6);
		e.put("KEY_7", GLFW_KEY_7);
		e.put("KEY_8", GLFW_KEY_8);
		e.put("KEY_9", GLFW_KEY_9);
		e.put("KEY_0", GLFW_KEY_0);

		e.put("KEY_Q", GLFW_KEY_Q);
		e.put("KEY_W", GLFW_KEY_W);
		e.put("KEY_E", GLFW_KEY_E);
		e.put("KEY_R", GLFW_KEY_R);
		e.put("KEY_T", GLFW_KEY_T);
		e.put("KEY_Y", GLFW_KEY_Y);
		e.put("KEY_U", GLFW_KEY_U);
		e.put("KEY_I", GLFW_KEY_I);
		e.put("KEY_O", GLFW_KEY_O);
		e.put("KEY_P", GLFW_KEY_P);

		e.put("KEY_A", GLFW_KEY_A);
		e.put("KEY_S", GLFW_KEY_S);
		e.put("KEY_D", GLFW_KEY_D);
		e.put("KEY_F", GLFW_KEY_F);
		e.put("KEY_G", GLFW_KEY_G);
		e.put("KEY_H", GLFW_KEY_H);
		e.put("KEY_J", GLFW_KEY_J);
		e.put("KEY_K", GLFW_KEY_K);
		e.put("KEY_L", GLFW_KEY_L);

		e.put("KEY_Z", GLFW_KEY_Z);
		e.put("KEY_X", GLFW_KEY_X);
		e.put("KEY_C", GLFW_KEY_C);
		e.put("KEY_V", GLFW_KEY_V);
		e.put("KEY_B", GLFW_KEY_B);
		e.put("KEY_N", GLFW_KEY_N);
		e.put("KEY_M", GLFW_KEY_M);

		e.put("KEY_SPACE", GLFW_KEY_SPACE);
		e.put("KEY_CTRL", GLFW_KEY_LEFT_CONTROL);
		e.put("KEY_SHIFT", GLFW_KEY_LEFT_SHIFT);

		e.put("MOUSE_LEFT", GLFW_MOUSE_BUTTON_LEFT);
		e.put("MOUSE_RIGHT", GLFW_MOUSE_BUTTON_RIGHT);

		e.put("Structure", struct);
		e.put("camera", cam);
		e.put("Input", input);
		e.put("Random", random);

		for(Entity en : struct.entities) {
			e.put(en.name, en);
		}
		LuaValue u = (LuaValue) e.get("Update");
		u.call();
	}

	public void HandleInput() {
		e.put("Texture", "Engine.Rendering.Texture");
		e.put("Shader", "Engine.Rendering.Shader");
		e.put("Mesh", "Engine.Rendering.Mesh");
		e.put("Loader", "Engine.Rendering.Loader");
		e.put("Entity", "Engine.Rendering.Entity");
		e.put("Structure", "Engine.Structure.Structure");
		e.put("InputManager", "Engine.Core.InputManager");
		e.put("Vector3f", "org.joml.Vector3f");
		e.put("Vector2f", "org.joml.Vector2f");
		e.put("Camera", "Engine.Rendering.Camera");
		e.put("PhysicsBody", "Engine.Physics.PhysicsBody");
		e.put("AABB", "Engine.Physics.AABB");
		e.put("GUIFrame", "Engine.GUI.Button");
		e.put("AudioPlayer", "Engine.Audio.AudioPlayer");
		e.put("Log", "Engine.Core.Log");

		e.put("KEY_1", GLFW_KEY_1);
		e.put("KEY_2", GLFW_KEY_2);
		e.put("KEY_3", GLFW_KEY_3);
		e.put("KEY_4", GLFW_KEY_4);
		e.put("KEY_5", GLFW_KEY_5);
		e.put("KEY_6", GLFW_KEY_6);
		e.put("KEY_7", GLFW_KEY_7);
		e.put("KEY_8", GLFW_KEY_8);
		e.put("KEY_9", GLFW_KEY_9);
		e.put("KEY_0", GLFW_KEY_0);

		e.put("KEY_Q", GLFW_KEY_Q);
		e.put("KEY_W", GLFW_KEY_W);
		e.put("KEY_E", GLFW_KEY_E);
		e.put("KEY_R", GLFW_KEY_R);
		e.put("KEY_T", GLFW_KEY_T);
		e.put("KEY_Y", GLFW_KEY_Y);
		e.put("KEY_U", GLFW_KEY_U);
		e.put("KEY_I", GLFW_KEY_I);
		e.put("KEY_O", GLFW_KEY_O);
		e.put("KEY_P", GLFW_KEY_P);

		e.put("KEY_A", GLFW_KEY_A);
		e.put("KEY_S", GLFW_KEY_S);
		e.put("KEY_D", GLFW_KEY_D);
		e.put("KEY_F", GLFW_KEY_F);
		e.put("KEY_G", GLFW_KEY_G);
		e.put("KEY_H", GLFW_KEY_H);
		e.put("KEY_J", GLFW_KEY_J);
		e.put("KEY_K", GLFW_KEY_K);
		e.put("KEY_L", GLFW_KEY_L);

		e.put("KEY_Z", GLFW_KEY_Z);
		e.put("KEY_X", GLFW_KEY_X);
		e.put("KEY_C", GLFW_KEY_C);
		e.put("KEY_V", GLFW_KEY_V);
		e.put("KEY_B", GLFW_KEY_B);
		e.put("KEY_N", GLFW_KEY_N);
		e.put("KEY_M", GLFW_KEY_M);

		e.put("KEY_SPACE", GLFW_KEY_SPACE);
		e.put("KEY_CTRL", GLFW_KEY_LEFT_CONTROL);
		e.put("KEY_SHIFT", GLFW_KEY_LEFT_SHIFT);

		e.put("MOUSE_LEFT", GLFW_MOUSE_BUTTON_LEFT);
		e.put("MOUSE_RIGHT", GLFW_MOUSE_BUTTON_RIGHT);

		e.put("Structure", struct);
		e.put("camera", cam);
		e.put("Input", input);
		e.put("Random", random);

		for(Entity en : struct.entities) {
			e.put(en.name, en);
		}

		LuaValue h = (LuaValue) e.get("HandleInput");
		h.call(LuaValue.valueOf(input.GetKey()));
	}
}
