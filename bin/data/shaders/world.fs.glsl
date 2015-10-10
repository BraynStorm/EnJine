#version 330

uniform sampler2D diffuse;
uniform vec4 color;

uniform vec4 sunlight_color;
uniform vec3 sunlight_direction;

struct Material{
	vec3 diffuseColor;
};

uniform Material material;

in vec2 texCoord0;
in vec3 normal0;

vec4 blend(vec4 f, vec4 b){
	return vec4(
		(f.xyz * f.w) + (b.xyz * b.w * (1 - f.w)),
		f.w + b.w *(1 - f.w)
	);
}

const float ambient = 0.1;

void main(){
	vec4 pixel = texture2D(diffuse, texCoord0);
	
	if(pixel.w == 0){
		pixel = color;
	}else{
		pixel = blend(pixel, color);
	}

	float intensity = clamp(dot(normalize(-sunlight_direction), normalize(normal0)) + ambient, 0, 1);
	
	gl_FragColor = vec4(material.diffuseColor * intensity, 1) ;// * vec4(sunlight_color.xyz * intensity, 1);
	
}
