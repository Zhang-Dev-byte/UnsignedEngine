package Engine.Physics;

import org.joml.Vector3f;

public class PhysicsBody implements java.io.Serializable {
	public Vector3f velocity = new Vector3f(0,0,0);
	public AABB aabb;

	public void CheckCollision(AABB other) {
		if(aabb.IntersectAABB(other)) {
			float x = -velocity.x;
			float y = -velocity.y;
			float z = -velocity.z;

			velocity = new Vector3f(x,y,z);
		}
	}

	public boolean CollisionTrigger(AABB other) {
		if(aabb.IntersectAABB(other)) {
			return true;
		}else {
			return false;
		}
	}
}
