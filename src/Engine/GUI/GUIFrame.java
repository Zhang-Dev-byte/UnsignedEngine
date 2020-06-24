package Engine.GUI;

import static org.lwjgl.opengl.GL11.GL_FLOAT;
import static org.lwjgl.opengl.GL11.GL_TRIANGLES;
import static org.lwjgl.opengl.GL11.GL_UNSIGNED_INT;
import static org.lwjgl.opengl.GL11.glDrawElements;
import static org.lwjgl.opengl.GL15.GL_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_ELEMENT_ARRAY_BUFFER;
import static org.lwjgl.opengl.GL15.GL_STATIC_DRAW;
import static org.lwjgl.opengl.GL15.glBindBuffer;
import static org.lwjgl.opengl.GL15.glBufferData;
import static org.lwjgl.opengl.GL15.glGenBuffers;
import static org.lwjgl.opengl.GL20.glEnableVertexAttribArray;
import static org.lwjgl.opengl.GL20.glVertexAttribPointer;
import static org.lwjgl.opengl.GL30.glBindVertexArray;
import static org.lwjgl.opengl.GL30.glGenVertexArrays;

import org.joml.Matrix4f;
import org.joml.Vector2f;
import org.joml.Vector3f;
import org.joml.Vector3fc;

import Engine.Rendering.Shader;
import Engine.Rendering.Texture;

public class GUIFrame implements java.io.Serializable {
	public int[] indices;
	public float[] vertices;
	public float[] texCoords;
	public Texture texture;
	public int VAO, VBO, EBO, TBO;
	public Shader shader;
	public Matrix4f model;
	public Vector3f rotation;
	public Vector3f position;
	public Vector2f scale;

	public GUIFrame(Texture texture) throws Exception {
		this.vertices = new float[] {
				 0.5f,  0.5f, 0.0f,
				 0.5f, -0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f,
				-0.5f,  0.5f, 0.0f,
		};
		this.indices = new int[] {
				0, 1, 3,
				1, 2, 3
		};
		this.texCoords = new float[] {
				1.0f, 1.0f,
				1.0f, 0.0f,
				0.0f, 0.0f,
				0.0f, 1.0f
		};
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = new Vector2f(1, 1);
		this.shader = new Shader("res/shaders/uifs.glsl", "res/shaders/uivs.glsl");
		this.texture = texture;

		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		EBO = glGenBuffers();
		TBO = glGenBuffers();

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

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		this.model = new Matrix4f().translate(position).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y)).rotateZ((float)Math.toRadians(rotation.z)).scale(new Vector3f(scale.x, scale.y, 1));
	}
	public GUIFrame(float[] texCoords, Texture texture) throws Exception {
		this.vertices = new float[] {
				 0.5f,  0.5f, 0.0f,
				 0.5f, -0.5f, 0.0f,
				-0.5f, -0.5f, 0.0f,
				-0.5f,  0.5f, 0.0f,
		};
		this.indices = new int[] {
				0, 1, 3,
				1, 2, 3
		};
		this.texCoords = texCoords;
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.scale = new Vector2f(1, 1);
		this.shader = new Shader("res/shaders/uifs.glsl", "res/shaders/uivs.glsl");
		this.texture = texture;

		VAO = glGenVertexArrays();
		VBO = glGenBuffers();
		EBO = glGenBuffers();
		TBO = glGenBuffers();

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

		glBindBuffer(GL_ARRAY_BUFFER, 0);
		glBindVertexArray(0);

		this.model = new Matrix4f().translate(position).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y)).rotateZ((float)Math.toRadians(rotation.z)).scale(new Vector3f(scale.x, scale.y, 1));
	}

	public void Render() {
		this.texture.Bind();
		glBindVertexArray(VAO);
		glBindBuffer(GL_ELEMENT_ARRAY_BUFFER, EBO);
		shader.Use();
		this.model = new Matrix4f().translate(position).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y)).rotateZ((float)Math.toRadians(rotation.z)).scale(new Vector3f(scale.x, scale.y, 1));
		this.shader.setMatrix4f("model", model);
		glDrawElements(GL_TRIANGLES, indices.length, GL_UNSIGNED_INT, 0);
	}
}
