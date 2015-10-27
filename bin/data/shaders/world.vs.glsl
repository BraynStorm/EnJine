#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;

uniform mat4 transformMatrix;
uniform mat4 viewMatrixT; // A.K.A Camera
uniform mat4 viewMatrixR; // A.K.A Camera
uniform mat4 projectionMatrix;

out vec2 texCoord0;
out vec3 normal0;

void main(){
  texCoord0 = texCoord;
  
  mat4 viewMatrix = viewMatrixR * viewMatrixT;
  vec4 pos = vec4(position, 1);
  
  // Unsure....
  //transpose(inverse(transformMatrix)) * 
  //normal0 = (transpose(inverse(transformMatrix)) * vec4(position, 0)).xyz;
  normal0 = (transpose(inverse(viewMatrixT * transformMatrix)) * vec4(normal, 0)).xyz;
  
  
  gl_Position = projectionMatrix * viewMatrix * transformMatrix * pos;
  //gl_Position = vec4(position, 1);
}
