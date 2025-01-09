#version 330 core

layout(location = 0) in vec2 position;
layout(location = 1) in vec2 texCoords;
layout(location = 2) in vec4 vertexColor; 
layout(location = 3) in vec3 vertexHSV; 
out vec2 fragTexCoords;
out vec4 fragColor;
out vec3 fragHSV;
uniform mat4 projection; 

void main() {
	gl_Position = projection * vec4( position.x, position.y, 0.0, 1.0 );
	fragTexCoords = texCoords;
	fragColor = vertexColor;
	fragHSV = vertexHSV;
}