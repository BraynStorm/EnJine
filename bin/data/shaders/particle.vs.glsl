#version 330

layout (location = 0) in vec3 position;
layout (location = 1) in vec2 texCoord;
layout (location = 2) in vec3 normal;


uniform mat4 transformMatrix;
uniform mat4 originMatrix;
uniform mat4 viewMatrixT; // A.K.A Camera
uniform mat4 viewMatrixR; // A.K.A Camera
uniform mat4 projectionMatrix; // Matrix4f.projection()

const bool doBillboard = false;

out vec2 texCoord0;

void main(){
  texCoord0 = texCoord;
  
  mat4 viewMatrix = viewMatrixR * viewMatrixT;
  
  //mat4 newViewMatrix = viewMatrix;
  if(doBillboard){
	  viewMatrix[0][0] = 1;
	  viewMatrix[0][1] = 0;
	  viewMatrix[0][2] = 0;
	  
	  viewMatrix[2][0] = 0;
	  viewMatrix[2][1] = 0;
	  viewMatrix[2][2] = 1;
  }
  
  gl_Position = projectionMatrix *  viewMatrix * (originMatrix * transformMatrix) * vec4(position, 1);
}
