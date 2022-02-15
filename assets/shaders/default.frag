#version 100
#ifdef GL_ES
precision mediump float;
#endif

struct Light {
	vec2 pos;
	vec3 color;
	float size;
};

varying vec3 pos;
varying vec4 color;
varying vec2 uv;

uniform sampler2D tex;
uniform Light lits[10];
uniform int size;

const float start = 8.0;
const float end = 1.0;
const float gamma = 1.0 / 2.2;

void main() {
	vec4 pix = texture2D(tex, uv);
	if (pix.a < 0.1) discard; // Don't draw the transparent pixel.
	pix.a = 1.0;
	
	// Lighting
	vec3 light = vec3(0.0); 
	for (int i = 0; i < size; i++) {
		Light lit = lits[i];
		float len = max(lit.size - distance(vec3(lit.pos, 0.5), pos), 0.0);
		light += lit.color * (len / lit.size);
	}
	pix.rgb *= max(vec3(0.1), pow(light, vec3(gamma)));
	
	float z = (gl_FragCoord.z/gl_FragCoord.w);
	float factor = (end - z) / (end - start);
	gl_FragColor = mix(pix * color, vec4(0.0), clamp(factor, 0.0, 1.0));
}