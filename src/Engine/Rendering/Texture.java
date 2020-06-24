package Engine.Rendering;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

import org.lwjgl.BufferUtils;
import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.stb.STBImage.*;

public class Texture implements java.io.Serializable {
    private int width;
    private int height;
    private int id;
    public String path;

    public Texture(String filename) {
    	this.path = filename;
        IntBuffer width = BufferUtils.createIntBuffer(1);
        IntBuffer height = BufferUtils.createIntBuffer(1);
        IntBuffer comp = BufferUtils.createIntBuffer(1);


        ByteBuffer data = stbi_load(filename, width, height, comp, 4);

        id = glGenTextures();
        this.width = width.get();
        this.height = height.get();

        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);
        glTexParameterf(GL_TEXTURE_2D, GL_TEXTURE_MIN_FILTER, GL_NEAREST);

        glTexImage2D(GL_TEXTURE_2D, 0, GL_RGBA, this.width, this.height, 0, GL_RGBA, GL_UNSIGNED_BYTE, data);
        stbi_image_free(data);
    }

    public int getId() {
		return id;
	}

	public void Bind() {
        glBindTexture(GL_TEXTURE_2D, id);
    }

    public void Unbind() {
        glBindTexture(GL_TEXTURE_2D, 0);
    }
    public int getWidth() {
    	return width;
    }
    public int getHeight() {
    	return height;
    }
}
