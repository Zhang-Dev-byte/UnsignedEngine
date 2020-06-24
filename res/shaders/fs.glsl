#version 330 core
out vec4 FragColor;

in vec2 TexCoord;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

uniform sampler2D t;
void main()
{
	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);

	float nDotl = dot(unitNormal,unitLightVector);
	float brightness = max(nDotl,0.2);
	vec3 diffuse = brightness * vec3(1.0,1.0,1.0);

	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;

    FragColor = vec4(diffuse,1.0) * texture(t, TexCoord);
}
