#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;


uniform mat4 transformMatrix;
uniform mat4 originMatrix;
uniform mat4 viewMatrixT; // A.K.A Camera
uniform mat4 viewMatrixR; // A.K.A Camera
uniform mat4 projectionMatrix; // Matrix4f.projection()


out vec2 texCoord0;

void main(){
  texCoord0 = texCoord;
  
  //mat4 newViewMatrix = viewMatrix;
  /*newViewMatrix[0][0] = 1;
  newViewMatrix[0][1] = 0;
  newViewMatrix[0][2] = 0;
  
  
  
  
  
  newViewMatrix[2][0] = 0;
  newViewMatrix[2][1] = 0;
  newViewMatrix[2][2] = 1;*/
  
  
  gl_Position = projectionMatrix *  viewMatrixR * viewMatrixT * (originMatrix * transformMatrix) * vec4(position, 1);
}
