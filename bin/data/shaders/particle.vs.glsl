#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 particleOriginTransform;
uniform mat4 particleTransform;

uniform mat4 camera_translation;
uniform mat4 camera_rotation;

uniform mat4 projection_matrix;

out vec2 texCoord0;

void main(){
  texCoord0 = texCoord;
  gl_Position = projection_matrix * camera_rotation * camera_translation * (particleOriginTransform * particleTransform) * vec4(position, 1);
}
