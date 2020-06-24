package Engine.GUI;
import org.lwjgl.stb.*;
import org.joml.Vector2f;
import org.lwjgl.BufferUtils;
import org.lwjgl.glfw.*;
import org.lwjgl.opengl.*;
import org.lwjgl.system.*;

import Engine.Audio.AudioPlayer;
import Engine.Compiler.ConfigCompiler;
import Engine.Core.InputManager;
import Engine.GUI.Button;
import Engine.GUI.GUIFrame;
import Engine.Lua.LuaManager;
import Engine.Rendering.Camera;
import Engine.Rendering.Texture;
import Engine.Structure.Structure;
import imgui.ImFontAtlas;
import imgui.ImFontConfig;
import imgui.ImGui;
import imgui.ImGuiFreeType;
import imgui.ImGuiIO;
import imgui.callback.*;
import imgui.flag.*;
import imgui.gl3.ImGuiImplGl3;

import java.awt.Font;
import java.nio.*;
import java.util.Random;

import static org.lwjgl.glfw.GLFW.*;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.system.MemoryStack.*;
import static org.lwjgl.system.MemoryUtil.*;

public class ImGuiLayer {
	public long windowPtr;
	public ImGuiIO io;

    private final long[] mouseCursors = new long[ImGuiMouseCursor.COUNT];

    // LWJGL3 renderer (SHOULD be initialized)
    private final ImGuiImplGl3 imGuiGl3 = new ImGuiImplGl3();



	public ImGuiLayer(long windowPtr) {
		this.windowPtr = windowPtr;
	}



	public void initImGui() {
	        // IMPORTANT!!
	        // This line is critical for Dear ImGui to work.
	        ImGui.createContext();

	        // ------------------------------------------------------------
	        // Initialize ImGuiIO config
	        io = ImGui.getIO();

	        io.setIniFilename("config.ini"); // We don't want to save .ini file
	        io.setConfigFlags(ImGuiConfigFlags.NavEnableKeyboard | ImGuiConfigFlags.DockingEnable); // Navigation with keyboard and enabled docking
	        io.setBackendFlags(ImGuiBackendFlags.HasMouseCursors); // Mouse cursors to display while resizing windows etc.
	        io.setBackendPlatformName("imgui_java_impl_glfw");

	        // ------------------------------------------------------------
	        // Keyboard mapping. ImGui will use those indices to peek into the io.KeysDown[] array.
	        final int[] keyMap = new int[ImGuiKey.COUNT];
	        keyMap[ImGuiKey.Tab] = GLFW_KEY_TAB;
	        keyMap[ImGuiKey.LeftArrow] = GLFW_KEY_LEFT;
	        keyMap[ImGuiKey.RightArrow] = GLFW_KEY_RIGHT;
	        keyMap[ImGuiKey.UpArrow] = GLFW_KEY_UP;
	        keyMap[ImGuiKey.DownArrow] = GLFW_KEY_DOWN;
	        keyMap[ImGuiKey.PageUp] = GLFW_KEY_PAGE_UP;
	        keyMap[ImGuiKey.PageDown] = GLFW_KEY_PAGE_DOWN;
	        keyMap[ImGuiKey.Home] = GLFW_KEY_HOME;
	        keyMap[ImGuiKey.End] = GLFW_KEY_END;
	        keyMap[ImGuiKey.Insert] = GLFW_KEY_INSERT;
	        keyMap[ImGuiKey.Delete] = GLFW_KEY_DELETE;
	        keyMap[ImGuiKey.Backspace] = GLFW_KEY_BACKSPACE;
	        keyMap[ImGuiKey.Space] = GLFW_KEY_SPACE;
	        keyMap[ImGuiKey.Enter] = GLFW_KEY_ENTER;
	        keyMap[ImGuiKey.Escape] = GLFW_KEY_ESCAPE;
	        keyMap[ImGuiKey.KeyPadEnter] = GLFW_KEY_KP_ENTER;
	        keyMap[ImGuiKey.A] = GLFW_KEY_A;
	        keyMap[ImGuiKey.C] = GLFW_KEY_C;
	        keyMap[ImGuiKey.V] = GLFW_KEY_V;
	        keyMap[ImGuiKey.X] = GLFW_KEY_X;
	        keyMap[ImGuiKey.Y] = GLFW_KEY_Y;
	        keyMap[ImGuiKey.Z] = GLFW_KEY_Z;
	        io.setKeyMap(keyMap);

	        // ------------------------------------------------------------
	        // Mouse cursors mapping
	        mouseCursors[ImGuiMouseCursor.Arrow] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.TextInput] = glfwCreateStandardCursor(GLFW_IBEAM_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeAll] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNS] = glfwCreateStandardCursor(GLFW_VRESIZE_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeEW] = glfwCreateStandardCursor(GLFW_HRESIZE_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNESW] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.ResizeNWSE] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);
	        mouseCursors[ImGuiMouseCursor.Hand] = glfwCreateStandardCursor(GLFW_HAND_CURSOR);
	        mouseCursors[ImGuiMouseCursor.NotAllowed] = glfwCreateStandardCursor(GLFW_ARROW_CURSOR);

	        // ------------------------------------------------------------
	        // GLFW callbacks to handle user input

	        glfwSetKeyCallback(windowPtr, (w, key, scancode, action, mods) -> {
	            if (action == GLFW_PRESS) {
	                io.setKeysDown(key, true);
	            } else if (action == GLFW_RELEASE) {
	                io.setKeysDown(key, false);
	            }

	            io.setKeyCtrl(io.getKeysDown(GLFW_KEY_LEFT_CONTROL) || io.getKeysDown(GLFW_KEY_RIGHT_CONTROL));
	            io.setKeyShift(io.getKeysDown(GLFW_KEY_LEFT_SHIFT) || io.getKeysDown(GLFW_KEY_RIGHT_SHIFT));
	            io.setKeyAlt(io.getKeysDown(GLFW_KEY_LEFT_ALT) || io.getKeysDown(GLFW_KEY_RIGHT_ALT));
	            io.setKeySuper(io.getKeysDown(GLFW_KEY_LEFT_SUPER) || io.getKeysDown(GLFW_KEY_RIGHT_SUPER));
	        });

	        glfwSetCharCallback(windowPtr, (w, c) -> {
	            if (c != GLFW_KEY_DELETE) {
	                io.addInputCharacter(c);
	            }
	        });

	        glfwSetMouseButtonCallback(windowPtr, (w, button, action, mods) -> {
	            io.setMouseDown(0, button == GLFW_MOUSE_BUTTON_1 && action != GLFW_RELEASE);
	            io.setMouseDown(1, button == GLFW_MOUSE_BUTTON_2 && action != GLFW_RELEASE);
	            io.setMouseDown(2, button == GLFW_MOUSE_BUTTON_3 && action != GLFW_RELEASE);
	            io.setMouseDown(3, button == GLFW_MOUSE_BUTTON_4 && action != GLFW_RELEASE);
	            io.setMouseDown(4, button == GLFW_MOUSE_BUTTON_5 && action != GLFW_RELEASE);

	            if (!io.getWantCaptureMouse() && io.getMouseDown(1)) {
	                ImGui.setWindowFocus(null);
	            }
	        });

	        glfwSetScrollCallback(windowPtr, (w, xOffset, yOffset) -> {
	            io.setMouseWheelH(io.getMouseWheelH() + (float) xOffset);
	            io.setMouseWheel(io.getMouseWheel() + (float) yOffset);
	        });

	        io.setSetClipboardTextFn(new ImStrConsumer() {
	            @Override
	            public void accept(final String s) {
	                glfwSetClipboardString(windowPtr, s);
	            }
	        });

	        io.setGetClipboardTextFn(new ImStrSupplier() {
	            @Override
	            public String get() {
	                final String clipboardString = glfwGetClipboardString(windowPtr);
	                if (clipboardString != null) {
	                    return clipboardString;
	                } else {
	                    return "";
	                }
	            }
	        });

	        // ------------------------------------------------------------
	        // Fonts configuration
	        // Read: https://raw.githubusercontent.com/ocornut/imgui/master/docs/FONTS.txt

	        // Method initializes LWJGL3 renderer.
	        // This method SHOULD be called after you've initialized your ImGui configuration (fonts and so on).
	        // ImGui context should be created as well.
	        imGuiGl3.init("#version 330 core");
	    }

    public void startFrame(final float deltaTime) {

    	double[] mousePosX = {0};
    	double[] mousePosY = {0};

    	int[] winWidth = {1000};
    	int[] winHeight = {1000};
        // Get window properties and mouse position
        glfwGetWindowSize(windowPtr, winWidth, winHeight);
        glfwGetFramebufferSize(windowPtr, winWidth, winHeight);
        glfwGetCursorPos(windowPtr, mousePosX, mousePosY);

        final float scaleX = (float) winWidth[0] / winWidth[0];
        final float scaleY = (float) winHeight[0] / winHeight[0];

        // We SHOULD call those methods to update Dear ImGui state for the current frame
        final ImGuiIO io = ImGui.getIO();
        io.setDisplaySize(winWidth[0], winHeight[0]);
        io.setDisplayFramebufferScale(scaleX, scaleY);
        io.setMousePos((float) mousePosX[0] * scaleX, (float) mousePosY[0] * scaleY);
        io.setDeltaTime(deltaTime);

        // Update the mouse cursor
        final int imguiCursor = ImGui.getMouseCursor();
        glfwSetCursor(windowPtr, mouseCursors[imguiCursor]);
        glfwSetInputMode(windowPtr, GLFW_CURSOR, GLFW_CURSOR_NORMAL);
    }

    public void endFrame() {
        // After Dear ImGui prepared a draw data, we use it in the LWJGL3 renderer.
        // At that moment ImGui will be rendered to the current OpenGL context.
        imGuiGl3.render(ImGui.getDrawData());
    }
    public void destroyImGui() {
        imGuiGl3.dispose();
        ImGui.destroyContext();
    }
    public void newFrame(float dt) {
    	startFrame(dt);

    	ImGui.newFrame();
    }
    public void render() {
    	ImGui.render();

    	endFrame();
    }
}
