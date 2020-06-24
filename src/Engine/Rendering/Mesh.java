package Engine.Rendering;

import static org.lwjgl.glfw.GLFW.glfwSwapBuffers;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;
import static org.lwjgl.opengl.GL13.*;
import static org.lwjgl.opengl.GL14.*;
import static org.lwjgl.opengl.GL15.*;
import static org.lwjgl.opengl.GL20.*;
import static org.lwjgl.opengl.GL30.*;
import static org.lwjgl.opengl.GL40.*;

public class Mesh implements java.io.Serializable {
	public int[] indices;
	public float[] vertices;
	public float[] texCoords;
	public float[] normals;
	public Texture texture;
	public int VAO, VBO, EBO, TBO, NBO;
	public Shader shader;

	public Mesh(int[] indices, float[] vertices, float[] texCoords, float[] normals, Shader shader, Texture texture) {
		this.vertices = vertices;
		this.indices = indices;
		this.texCoords = texCoords;
		this.shader = shader;
		this.texture = texture;
		this.normals = normals;

		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		EBO = glGenBuffers();
		TBO = glGenBuffers();
		NBO = glGenBuffers();

		this.texture.Bind();

		this.shader.Use();
		this.shader.setInt("t", this.texture.getId());

		glBindVertexArray(VAO);

		glBindBuffer(GL_ARRAY_BUFFER, VBO);
		glBufferData(GL_ARRAY_BUFFER, vertices, GL_STATIC_DRAW);

		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		glBufferData(GL_ELEMENT_ARRAY_BUFFER, indices, GL_STATIC_DRAW);

		glVertexAttribPointer(0, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(0);

		glBindBuffer(GL_ARRAY_BUFFER, TBO);
		glBufferData(GL_ARRAY_BUFFER, texCoords, GL_STATIC_DRAW);

		glVertexAttribPointer(1, 2, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(1);

		glBindBuffer(GL_ARRAY_BUFFER, NBO);
		glBufferData(GL_ARRAY_BUFFER, normals, GL_STATIC_DRAW);

		glVertexAttribPointer(2, 3, GL_FLOAT, false, 0, 0);
		glEnableVertexAttribArray(2);

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);
	}

	public void Render() {
		this.texture.Bind();
		glBindVertexArray(VAO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		shader.Use();
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
