#version 330

uniform sampler2D diffuse;
uniform vec4 color;

in vec2 texCoord0;

vec4 blend(vec4 f, vec4 b){
  return vec4(
    (f.xyz * f.w) + (b.xyz * b.w * (1 - f.w)),
    f.w + b.w *(1 - f.w)
  );
}

void main(){
  vec4 pixel = texture2D(diffuse, texCoord0);
  
  if(pixel.w == 0){
    discard;
  }else{
    gl_FragColor = blend(pixel, color);
  }
  
}
