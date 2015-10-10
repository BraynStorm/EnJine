#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 transform;

out vec2 texCoord0;

void main(){
  texCoord0 = texCoord;
  
  vec4 pos = vec4(position, 1);
  
  gl_Position = transform * pos;
}
