package Engine.Rendering;

import java.util.ArrayList;
import java.util.List;

import org.joml.Matrix4f;
import org.joml.Vector3f;

import Engine.Physics.PhysicsBody;

public class Entity implements java.io.Serializable{
	public Mesh mesh;
	public Matrix4f model;
	public Camera camera;
	public Vector3f position;
	public float scale;
	public Vector3f rotation;
	public List<PhysicsBody> bodies;
	public String name;
	public String object;

	public Entity(Mesh mesh, Camera camera, String name, String object) {
		this.mesh = mesh;
		this.camera = camera;
		this.scale = 1f;
		this.position = new Vector3f(0, 0, 0);
		this.rotation = new Vector3f(0, 0, 0);
		this.bodies = new ArrayList<PhysicsBody>();
		this.model = new Matrix4f().translate(position).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y)).rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
		this.name = name;
		this.object = object;
	}
	public void Render() {
		this.mesh.shader.Use();
		this.mesh.shader.setMatrix4f("model", model);
		this.mesh.shader.setMatrix4f("view", camera.view);
		this.mesh.shader.setMatrix4f("projection", camera.projection);
		this.mesh.Render();
	}
	public void Update() {
		this.model = new Matrix4f().translate(position).rotateX((float)Math.toRadians(rotation.x)).rotateY((float)Math.toRadians(rotation.y)).rotateZ((float)Math.toRadians(rotation.z)).scale(scale);
		for(PhysicsBody body : bodies) {
			this.position.add(body.velocity);
		}
	}
	public void AddBody(PhysicsBody body) {
		bodies.add(body);
	}
}
