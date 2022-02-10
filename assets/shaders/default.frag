#version 100
#ifdef GL_ES
precision mediump float;
#endif

varying vec4 color;
varying vec2 uv;

uniform sampler2D tex;

void main() {
	gl_FragColor = texture2D(tex, uv) * color;
}