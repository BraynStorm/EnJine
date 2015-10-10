#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 transform;

uniform mat4 camera_translation;
uniform mat4 camera_rotation;

uniform mat4 projection_matrix;

out vec2 texCoord0;
out vec3 normal0;

void main(){
  texCoord0 = texCoord;
  
  
  vec4 pos = vec4(position, 1);
  
  // Unsure....
  //transpose(inverse(transform)) * 
  //normal0 = (transpose(inverse(transform)) * vec4(position, 0)).xyz;
  normal0 = (transpose(inverse(transform)) * vec4(normal, 0)).xyz;
  
  
  gl_Position = projection_matrix * camera_rotation * camera_translation * transform * pos;
  
}
