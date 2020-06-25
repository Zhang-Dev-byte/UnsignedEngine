package Game;

import org.lwjgl.stb.*;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import Engine.Audio.AudioPlayer;
import Engine.Compiler.ConfigCompiler;
import Engine.Core.IOUtil;
import Engine.Core.InputManager;
import Engine.Core.Log;
import Engine.GUI.Button;
import Engine.GUI.GUIFrame;
import Engine.GUI.ImGuiLayer;
import Engine.Json.Json;
import Engine.Lua.LuaManager;
import Engine.Rendering.Camera;
import Engine.Rendering.Entity;
import Engine.Rendering.Loader;
import Engine.Rendering.Texture;
import Engine.Structure.Structure;
import Engine.Windows.CMD;
import imgui.ImGui;
import imgui.type.ImString;

import org.json.*;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.io.Reader;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.*;
import java.util.Scanner;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class Game {
	public static void main(String[] args) throws Throwable {
		PrintStream log = new PrintStream("UnsignedEngine.tlog");
		PrintStream elog = new PrintStream("Errors.tlog");
		System.setOut(log);
		if (!glfwInit())
			throw new IllegalStateException("Unable to initialize GLFW");

		GLFWErrorCallback.createPrint(System.err).set();

		Camera cam = new Camera();
		glfwDefaultWindowHints();
		glfwWindowHint(GLFW_VISIBLE, GLFW_FALSE);
		glfwWindowHint(GLFW_RESIZABLE, GLFW_TRUE);
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MAJOR, 3);
		//glfwWindowHint(GLFW_CONTEXT_VERSION_MINOR, 2);
		//glfwWindowHint(GLFW_OPENGL_PROFILE, GLFW_OPENGL_CORE_PROFILE);
		//glfwWindowHint(GLFW_OPENGL_FORWARD_COMPAT, GLFW_TRUE);
		long window = glfwCreateWindow(1000, 1000, ConfigCompiler.LoadFile("Game.config").get(0) , NULL, NULL);

		if (window == NULL)
			throw new IllegalStateException("Cannot initalize GLFW window");

		glfwMakeContextCurrent(window);

		try (MemoryStack stack = stackPush()) {
			IntBuffer pWidth = stack.mallocInt(1);
			IntBuffer pHeight = stack.mallocInt(1);

			glfwGetWindowSize(window, pWidth, pHeight);

			GLFWVidMode vidmode = glfwGetVideoMode(glfwGetPrimaryMonitor());

			glfwSetWindowPos(window, (vidmode.width() - pWidth.get(0)) / 2, (vidmode.height() - pHeight.get(0)) / 2);
		}

		glfwMakeContextCurrent(window);

		glfwSwapInterval(1);

		glfwShowWindow(window);

		GL.createCapabilities();

		glEnable(GL_TEXTURE_2D);
		glEnable(GL_DEPTH_TEST);

		glEnable(GL_CULL_FACE);
		glCullFace(GL_BACK);

		glClearColor(Float.parseFloat(ConfigCompiler.LoadFile("Color.config").get(0)), Float.parseFloat(ConfigCompiler.LoadFile("Color.config").get(1)), Float.parseFloat(ConfigCompiler.LoadFile("Color.config").get(2)), 0.0f);

		glEnable(GL_BLEND);
		glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);

		boolean shouldset = false;
		boolean isPlaying = false;
		boolean ran = false;
		Random random = new Random();
		Structure struct = new Structure();
		InputManager input = new InputManager(window);
		LuaManager manager = new LuaManager(ConfigCompiler.LoadFile("Script.config"), struct, input, cam, random);
		ImGuiLayer layer = new ImGuiLayer(window);
		layer.initImGui();
		Entity se = new Entity(null,null,null,null);
		String sn = "";
		String sf = "";
		String so = IOUtil.loadFile("Script.config");
		String[] aso = so.split("\n");
		int vs = 0;
		int is = 0;
		float[] sp = {0,0,0};
		float[] sr = {0,0,0};
		float[] ss = {0};

		float[] cp = {0,0,0};
		ImString n = new ImString(1024);
		ImString m = new ImString(1024);
		ImString t = new ImString(1024);
		ImString p = new ImString(1024);


		float deltaTime = 0.0f;
		float lastFrame = 0.0f;

		SimpleDateFormat formatter = new SimpleDateFormat("HH:mm:ss z");
		Date date = new Date(System.currentTimeMillis());

		Json.DeserializeCamera(cam);
		Json.DeserializeEntity(struct, cam);
		if(!Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) || isPlaying) {
			manager.Run();
		}

		while (!glfwWindowShouldClose(window)) {

			float currentFrame = (float)glfwGetTime();
			deltaTime = currentFrame - lastFrame;
			lastFrame = currentFrame;

			if(!Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) || isPlaying) {
				manager.HandleInput();
			}
			if(Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) && !isPlaying) {
				if(input.GetKey(GLFW_KEY_W)) {
					cam.position.z += 1;
				}
				if(input.GetKey(GLFW_KEY_S)) {
					cam.position.z -= 1;
				}
				if(input.GetKey(GLFW_KEY_A)) {
					cam.position.x += 1;
				}
				if(input.GetKey(GLFW_KEY_D)) {
					cam.position.x -= 1;
				}
				if(input.GetKey(GLFW_KEY_UP)) {
					cam.position.y -= 1;
				}
				if(input.GetKey(GLFW_KEY_DOWN)) {
					cam.position.y += 1;
				}
			}
			glfwPollEvents();
			glClear(GL_COLOR_BUFFER_BIT | GL_DEPTH_BUFFER_BIT);
			if(!Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) || isPlaying) {
				manager.Update();
			}
			cam.Update();
			struct.Render();
			layer.newFrame(deltaTime);
			if(Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1))) {
				ImGui.begin("Control");
				if(ImGui.button("Play")) {
					try {
						shouldset = true;
					}catch(Exception ex) {
						ex.printStackTrace();
					}
				}
				if(ImGui.button("Stop")) {
					shouldset = false;
					ran = false;
					struct.entities.clear();
					Json.DeserializeCamera(cam);
					Json.DeserializeEntity(struct, cam);
				}
				ImGui.end();
			}
			if(Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) || isPlaying){
				ImGui.begin("Scene");
				for(Entity e : struct.entities) {
					if(ImGui.button(e.name)) {
						se = e;
						sn = e.name;
						vs = se.mesh.vertices.length;
						is = se.mesh.indices.length;
						sp = new float[] {se.position.x, se.position.y, se.position.z};
						sr = new float[] {se.rotation.x, se.rotation.y, se.rotation.z};
						ss = new float[] {se.scale};
						sf = se.mesh.texture.path;
					}
				}
				ImGui.end();

				ImGui.begin("Objects");
				ImGui.inputText("Name", n);
				ImGui.inputText("Mesh", m);
				ImGui.inputText("Texture", t);
				if(ImGui.button("New")) {
					Json.SeralizeEntity(new Entity(Loader.LoadFile(m.toString(), new Texture(t.toString())),cam,n.toString(), m.toString()));
					struct.AddObject(new Entity(Loader.LoadFile(m.toString(), new Texture(t.toString())) , cam, n.toString(), m.toString()));
				}
				ImGui.end();

				ImGui.begin("Properties");
				ImGui.text(sn);
				ImGui.text("Vertices: "+vs);
				ImGui.text("Fragments: "+is);
				ImGui.text("Texture: "+sf);
				ImGui.dragFloat3("Position", sp);
				ImGui.dragFloat3("Rotation", sr);
				ImGui.dragFloat("Scale", ss);
				se.position.x = sp[0];
				se.position.y = sp[1];
				se.position.z = sp[2];
				se.rotation.x = sr[0];
				se.rotation.y = sr[1];
				se.rotation.z = sr[2];
				se.scale = ss[0];
				if(ImGui.button("Delete")) {
					struct.entities.remove(se);
					IOUtil.deleteFile(se.name+".json");
					ss = new float[]{0};
					sp = new float[]{0,0,0};
					sr = new float[]{0,0,0};
					is = 0;
					vs = 0;
					sf = "";
					sn = "";
					se = new Entity(null,null,null,null);
				}
				if(ImGui.button("Deselect")) {
					ss = new float[]{0};
					sp = new float[]{0,0,0};
					sr = new float[]{0,0,0};
					is = 0;
					vs = 0;
					sf = "";
					sn = "";
					se = new Entity(null,null,null,null);
				}
				ImGui.end();

				ImGui.begin("Camera");
				cp[0] = cam.position.x;
				cp[1] = cam.position.y;
				cp[2] = cam.position.z;
				ImGui.dragFloat3("Position", cp);
				cam.position.x = cp[0];
				cam.position.y = cp[1];
				cam.position.z = cp[2];
				ImGui.end();

				ImGui.begin("Stats");
				ImGui.text("Vendor: "+glGetString(GL_VENDOR));
				ImGui.text("Renderer: "+glGetString(GL_RENDERER));
				ImGui.text("FPS: "+layer.io.getFramerate());
				ImGui.end();

				ImGui.begin("Scripts");
				so = IOUtil.loadFile("Script.config");
				aso = so.split("\n");
				for(String s : aso) {
					if(ImGui.button(s)) {
						CMD.ExecuteCommand("C:\\Users\\"+System.getProperty("user.name")+"\\AppData\\Local\\Programs\\Microsoft VS Code\\Code.exe "+s);
					}
				}

				ImGui.end();
				ImGui.begin("ScriptManager");
				ImGui.inputText("Path",p);
				if(ImGui.button("New Script")) {
				    BufferedWriter writer = new BufferedWriter(new FileWriter("Script.config", true));

				    writer.newLine();
				    writer.write(p.toString());
				    writer.close();

				    PrintWriter out = new PrintWriter(new File(p.toString()));
				    out.write("logger = Unsigned.newInstance(Log)\r\n" +
				    		"function Awake()\r\n" +
				    		"end\r\n" +
				    		"function Start()\r\n" +
				    		"end\r\n" +
				    		"\r\n" +
				    		"function Update()\r\n" +
				    		"end\r\n" +
				    		"\r\n" +
				    		"function HandleInput(key)\r\n" +
				    		"end");
				    out.close();
				}
				ImGui.end();

				ImGui.begin("Console");
				String[] alines = IOUtil.loadFile("UnsignedEngine.tlog").split("\n");
				for(String a : alines) {
					if(a != "") {
						ImGui.text("["+formatter.format(date)+"] "+a);
					}
				}
				ImGui.end();
				ImGui.begin("Errors");
				String[] lines = IOUtil.loadFile("Errors.tlog").split("\n");
				for(String e : lines) {
					if(e != "") {
						ImGui.textColored(1.0f, 0.0f, 0.0f, 1.0f, "["+formatter.format(date)+"] "+e);
					}
				}
				IOUtil.clearFile("Errors.tlog");
				ImGui.end();
				if(Boolean.parseBoolean(ConfigCompiler.LoadFile("Game.config").get(1)) && isPlaying == false) {
					Json.SerializeCamera(cam);
					for(Entity e : struct.entities) {
						Json.SeralizeEntity(e);
					}
				}
			}
			layer.render();
			glfwSwapBuffers(window);
			isPlaying = shouldset;
			if(ran == false && isPlaying == true) {
				manager.Run();
				ran = true;
			}
		}


	}

}