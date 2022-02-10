#version 100
#ifdef GL_ES
precision highp float;
#endif

attribute vec4 pos;
attribute vec4 color0;
attribute vec2 uv0;

varying vec4 color;
varying vec2 uv;

uniform mat4 mat;

void main() {
	color = color0;
	uv = uv0;
	gl_Position = mat * pos;
}
