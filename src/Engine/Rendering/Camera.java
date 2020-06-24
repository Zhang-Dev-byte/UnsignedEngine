package Engine.Rendering;

import org.joml.Matrix4f;
import org.joml.Vector3f;

public class Camera implements java.io.Serializable{
	private static final float FOV = (float) Math.toRadians(60.0f);
	private static final float NEAR_PLANE = 0.1f;
	private static final float FAR_PLANE = 1000;
	public Matrix4f view;
	public Matrix4f projection;
	public Vector3f position;
	public float scale;

	public Camera() {
		projection = new Matrix4f().perspective(FOV, 1, NEAR_PLANE, FAR_PLANE);
		scale = 2f;
		position = new Vector3f(0,0,0);
		view = new Matrix4f().translate(position).scale(scale);
	}

	public Matrix4f getViewMatrix() {
		return view;
	}
	public Matrix4f getProjectionMatrix() {
		return projection;
	}
	public void Update() {
		projection = new Matrix4f().perspective(FOV, 1, NEAR_PLANE, FAR_PLANE);
		view = new Matrix4f().translate(position).scale(scale);
	}
}
