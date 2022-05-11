#version 100
#ifdef GL_ES
precision highp float;
#endif

attribute vec4 pos0;
attribute vec4 color0;
attribute vec4 normal0;
attribute vec2 uv0;

varying vec3 pos;
varying vec3 color;
varying vec3 normal;
varying vec3 direct;
varying vec2 uv;

uniform mat4 mat;

void main() {
	pos = pos0.xyz;	
	color = color0.rgb;
	normal = (normal0.xyz - 0.5) * 2.0;
	uv = uv0;
	gl_Position = mat * pos0;
}
