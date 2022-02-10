#version 100
#ifdef GL_ES
precision highp float;
#endif

attribute vec4 pos;

varying vec2 uv;

uniform vec2 size;
uniform mat4 mat;

void main() {
	uv = vec2(pos);
	gl_Position = (pos * vec4(size, 1.0, 1.0)) * mat;
}
