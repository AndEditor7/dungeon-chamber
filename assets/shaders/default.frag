#version 100
#ifdef GL_ES
precision highp float;
#endif

struct Light {
	vec3 pos;
	vec3 color;
	float size;
};

varying vec3 pos;
varying vec3 color;
varying vec3 normal;
varying vec2 uv;
uniform vec2 mapSize;
uniform sampler2D tex;
uniform sampler2D map;
uniform Light lits[100];
uniform int size;
uniform vec3 camPos;

const float fogStart = 10.0;
const float fogEnd = 7.0;
const float off = 0.001;
const float gamma = 2.2;
const float brightness = 0.05;

void main() {

	vec4 pix = texture2D(tex, uv);
	if (pix.a < 0.1) discard; // Don't draw the transparent pixel.
	pix.rgb *= color;
	pix.a = 1.0;
	vec3 direct = normalize(pos - camPos);
		
	// Tile Ray-tracing Lighting
	vec3 value = vec3(brightness); 
	for (int i = 0; i < size; i++) {
		Light lit = lits[i];	
		vec3 start = pos;
		vec3 end = lit.pos;
		float len2D = distance(start.xy, end.xy);
		if (len2D > lit.size) {
			continue;
		}
		vec2 dir = (end.xy - start.xy) / len2D;
		
		if (dir.x == 0.0 || dir.y == 0.0) {
			continue;
		}
		
		vec2 offset = vec2(start.xy + (normal.xy * off));
		float rad = len2D + off;
		
		ivec2 pos = ivec2(offset);
		ivec2 steps = ivec2(sign(dir.x), sign(dir.y));
		
		vec2 tDelta = vec2(steps.x == 0 ? 1000.0 : float(steps.x) / dir.x, steps.y == 0 ? 1000.0 : float(steps.y) / dir.y);
		vec2 tMax = vec2(tDelta.x * (steps.x > 0 ? 1.0 - fract(offset.x) : fract(offset.x)), tDelta.y * (steps.y > 0 ? 1.0 - fract(offset.y) : fract(offset.y)));
		
		bool stop = false;
		for (int j = 0; j < 30; j++) {
			float tile = texture2D(map, vec2((float(pos.x)+0.5)/mapSize.x, (float(pos.y)+0.5)/mapSize.y)).a;
			if (tile > 0.1) {
				stop = true;
				break;
			}			
			// Traversal stepping.
			if (tMax.x < tMax.y) {
				if (tMax.x > rad) break;
				pos.x += steps.x;
				tMax.x += tDelta.x;
			} else {
				if (tMax.y > rad) break;
				pos.y += steps.y;
				tMax.y += tDelta.y;
			}
		}
		
		if (stop) {
			continue;
		}
		
		float len3D = distance(start, end);
		float val = clamp(pow((lit.size - len3D) / lit.size, 2.0), 0.0, 1.0);
		value += lit.color * val;
		value += pow(max(dot(direct, reflect(normalize(end - start), normal)), 0.0), 10.0) * val * lit.color * 0.2;
	}
	
	pix.rgb = pix.rgb * value;
	
	// Fog
	float z = gl_FragCoord.z / gl_FragCoord.w;
	float factor = (fogEnd - z) / (fogEnd - fogStart);
	gl_FragColor = mix(pix, vec4(0.0), clamp(factor, 0.0, 1.0));
}