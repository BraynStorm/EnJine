#version 330

uniform sampler2D diffuse;
uniform vec3 particleColor;
uniform int frameCount;
uniform int currentFrame;

in vec2 texCoord0;

vec4 blend(vec4 f, vec4 b){
	return vec4(
		(f.xyz * f.w) + (b.xyz * b.w * (1 - f.w)),
		f.w + b.w *(1 - f.w)
	);
}

void main(){
	float offset = float(currentFrame) / float(frameCount);
	vec4 pixel = texture2D(diffuse, vec2((texCoord0.x / frameCount) + offset, texCoord0.y));
	
		
	if(pixel.w == 0.0){
		discard;
	}else{
		float originalW = pixel.w;
		pixel = blend(pixel, vec4(particleColor, 1));
		pixel.w = originalW;
	}

	
	gl_FragColor = pixel;
}
