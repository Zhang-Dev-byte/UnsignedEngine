package Engine.Rendering;

import java.io.*;
import java.nio.FloatBuffer;
import java.util.*;
import org.joml.Matrix4f;
import org.lwjgl.BufferUtils;
import org.lwjgl.system.MemoryStack;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

public class Shader implements java.io.Serializable {
	public int ID;

	public Shader(String fragmentPath, String vertexPath) throws Exception {
	     Scanner fS = new Scanner(new FileReader(fragmentPath));

		String f = "";

        while(fS.hasNextLine()) {
            f += (fS.nextLine() + "\n");
        }

        Scanner vS = new Scanner(new FileReader(vertexPath));

		String v = "";

        while(vS.hasNextLine()) {
            v += (vS.nextLine() + "\n");
        }

        int vertex, fragment;

        vertex = glCreateShader(GL_VERTEX_SHADER);
        glShaderSource(vertex, v);
        glCompileShader(vertex);
        if (glGetShaderi(vertex, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(vertex, 1024));
        }

        fragment = glCreateShader(GL_FRAGMENT_SHADER);
        glShaderSource(fragment, f);
        glCompileShader(fragment);
        if (glGetShaderi(fragment, GL_COMPILE_STATUS) == 0) {
            throw new Exception("Error compiling Shader code: " + glGetShaderInfoLog(fragment, 1024));
        }

        ID = glCreateProgram();
        glAttachShader(ID, vertex);
        glAttachShader(ID, fragment);
        glLinkProgram(ID);
        if (glGetProgrami(ID, GL_LINK_STATUS) == 0) {
            throw new Exception("Error linking Shader code: " + glGetProgramInfoLog(ID, 1024));
        }

        glDeleteShader(vertex);
        glDeleteShader(fragment);
	}

	public void setInt(String name, int value) {
		glUniform1i(glGetUniformLocation(ID,name),value);
	}
	public void setMatrix4f(String name, Matrix4f value) {
		FloatBuffer buffer = BufferUtils.createFloatBuffer(16);
		value.get(buffer);

		glUniformMatrix4fv(glGetUniformLocation(ID,name), false, buffer);
	}
    public void Use()
    {
        glUseProgram(ID);
    }
}
