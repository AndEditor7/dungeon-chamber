#version 100
#ifdef GL_ES
precision highp float;
#endif

struct Light {
	vec2 pos;
	vec3 color;
	float size;
};

varying vec3 pos;
varying vec3 color;
varying vec2 uv;
uniform vec2 mapSize;
uniform sampler2D tex;
uniform sampler2D map;
uniform Light lits[30];
uniform int size;

const float fogStart = 8.0;
const float fogEnd = 2.0;
const float off = 0.002;
const float gamma = 1.0 / 2.2;
const float hardness = 0.1;

void main() {
	vec4 pix = texture2D(tex, uv);
	//vec4 pix = vec4(1.0);
	if (pix.a < 0.1) discard; // Don't draw the transparent pixel.
	pix.rgb *= color;
	pix.a = 1.0;
	
	// Ray Trace Lighting
	vec3 value = vec3(0.0); 
	for (int i = 0; i < size; i++) {
		Light lit = lits[i];
		vec2 start = pos.xy;
		vec2 end = lit.pos;
		float len = distance(start, end);
		if (len > lit.size) {
			continue;
		}
		vec2 dir = (end - start) / len;
		
		if (dir.x == 0.0 || dir.y == 0.0) {
			continue;
		}
		
		// Fixs Z fighting on walls.
		vec2 offset = vec2(start + (dir * off));
		float rad = len + off;
		
		ivec2 pos = ivec2(offset);
		ivec2 steps = ivec2(sign(dir.x), sign(dir.y));
		
		vec2 tDelta = vec2(steps.x == 0 ? 1000.0 : float(steps.x) / dir.x, steps.y == 0 ? 1000.0 : float(steps.y) / dir.y);
		vec2 tMax = vec2(tDelta.x * (steps.x > 0 ? 1.0 - fract(offset.x) : fract(offset.x)), tDelta.y * (steps.y > 0 ? 1.0 - fract(offset.y) : fract(offset.y)));
		
		bool stop = false, hit = false;
		float dist = 0.0, lastDst = 0.0;
		float depth = 0.0;
		float softness = 0.0;
		for (int j = 0; j < 50; j++) {
			vec4 tex = texture2D(map, vec2((float(pos.x)+0.5)/mapSize.x, (float(pos.y)+0.5)/mapSize.y));
			if (tex.a > 0.1) {
			    if (hit) {
			    	stop = true;
			    	break;
			    }
				hit = true;
				lastDst = dist;
			} else if (hit) {
				if (depth < (dist - lastDst)) {
					depth = (dist - lastDst);
					softness = depth / (dist*hardness);
					hit = false;				
				}
			}		
			// Traversal stepping.
			if (tMax.x < tMax.y) {
				if (tMax.x > rad) break;
				pos.x += steps.x;
				dist = tMax.x;
				tMax.x += tDelta.x;
			} else {
				if (tMax.y > rad) break;
				pos.y += steps.y;
				dist = tMax.y;
				tMax.y += tDelta.y;
			}
		}
		
		if (stop) {
			continue;
		}
		
		float val = (lit.size - len) / lit.size;
		value += lit.color * val * (1.0 - clamp(softness, 0.0, 1.0));
	}
	
	pix.rgb *= max(vec3(0.07), value);
	
	// Fog
	//float z = gl_FragCoord.z / gl_FragCoord.w;
	//float factor = (end - z) / (end - start);
	//gl_FragColor = mix(pix, vec4(0.0), clamp(factor, 0.0, 1.0));
	gl_FragColor = pix;
}