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
varying vec3 color;
varying vec3 light;
varying vec2 uv;

uniform sampler2D tex;
uniform Light lits[30];
uniform int size;

const float start = 8.0;
const float end = 2.0;

void main() {
	vec4 pix = texture2D(tex, uv);
	if (pix.a < 0.1) discard; // Don't draw the transparent pixel.
	pix.rgb *= color;
	pix.a = 1.0;
	
	// Multi Lighting
	vec3 value = light; 
	for (int i = 0; i < size; i++) {
		Light lit = lits[i];
		float len = max(lit.size - distance(vec3(lit.pos, 0.5), pos), 0.0);
		value += lit.color * (len / lit.size);
	}
	pix.rgb *= max(vec3(0.1), value);
	
	// Fog
	//float z = gl_FragCoord.z / gl_FragCoord.w;
	//float factor = (end - z) / (end - start);
	//gl_FragColor = mix(pix, vec4(0.0), clamp(factor, 0.0, 1.0));
	gl_FragColor = pix;
}