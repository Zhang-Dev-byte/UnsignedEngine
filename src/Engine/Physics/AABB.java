package Engine.Physics;

import org.joml.Math;
import org.joml.Vector3f;

public class AABB implements java.io.Serializable {
	Vector3f position;
	Vector3f size;

	public AABB(Vector3f position, Vector3f size) {
		this.position = position;
		this.size = size;
	}

	public boolean IntersectAABB(AABB other) {
		if(this.position.x < other.position.x + other.size.x && this.position.x + this.size.x  > other.position.x &&
			this.position.y < other.position.y + other.size.y && this.position.y + this.size.y  > other.position.y &&
			this.position.z < other.position.z + other.size.z && this.position.z + this.size.z  > other.position.z) {
			return true;
		}else {
			return false;
		}
	}
}
