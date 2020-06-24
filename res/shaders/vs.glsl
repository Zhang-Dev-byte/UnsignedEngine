#version 330 core
layout (location = 0) in vec3 aPos;
layout (location = 1) in vec2 aTexCoord;
layout (location = 2) in vec3 aNormal;

out vec2 TexCoord;
out vec3 surfaceNormal;
out vec3 toLightVector;
out vec3 toCameraVector;

uniform mat4 projection;
uniform mat4 model;
uniform mat4 view;
void main()
{
	TexCoord = aTexCoord;
	vec4 worldPosition = model * vec4(aPos.x, aPos.y, aPos.z, 1.0);
    gl_Position = projection * view * worldPosition;

	surfaceNormal = (model * vec4(aNormal,0.0)).xyz;
	toLightVector = vec3(0,10,10) - worldPosition.xyz;
	toCameraVector = (inverse(view) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
}
