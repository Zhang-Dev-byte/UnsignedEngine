package Engine.Lua;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import Engine.Core.InputManager;
import Engine.Rendering.Camera;
import Engine.Structure.Structure;

public class LuaManager {
	public List<String> order;
	public List<LuaScript> scripts;
	public Structure struct;
	public InputManager input;
	public Camera cam;

	public LuaManager(List<String> order, Structure struct, InputManager input, Camera cam, Random random) throws Throwable {
		this.order = order;
		this.struct = struct;
		this.input = input;
		this.cam = cam;
		this.scripts = new ArrayList<LuaScript>();
		for(String script : order) {
			scripts.add(new LuaScript( script, struct, input, cam, random));
		}
	}



	public void Run() throws Exception {
		for(LuaScript script : scripts) {
			script.Run();
		}
	}

	public void HandleInput() throws Exception {
		for(LuaScript script : scripts) {
			script.HandleInput();
		}
	}

	public void Update() throws Exception {
		for(LuaScript script : scripts) {
			script.Update();
		}
	}
}
