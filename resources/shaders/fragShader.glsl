#version 330 core

in vec2 fragTexCoords;
in vec4 fragColor;
in vec3 fragHSV;
out vec4 color;
uniform sampler2D texture0;

// 
// https://gamedev.stackexchange.com/questions/59797/glsl-shader-change-hue-saturation-brightness
//
// Converts a (r, g, b) to a (h, s, v).
vec3 rgb2hsv(vec3 c) {
	vec4 K = vec4(0.0, -1.0 / 3.0, 2.0 / 3.0, -1.0);
	vec4 p = mix(vec4(c.bg, K.wz), vec4(c.gb, K.xy), step(c.b, c.g));
	vec4 q = mix(vec4(p.xyw, c.r), vec4(c.r, p.yzx), step(p.x, c.r));

	float d = q.x - min(q.w, q.y);
	float e = 1.0e-10;
	return vec3(abs(q.z + (q.w - q.y) / (6.0 * d + e)), d / (q.x + e), q.x);
}

// Converts a (h, s, v) to a (r, g, b).
vec3 hsv2rgb(vec3 c) {
	vec4 K = vec4(1.0, 2.0 / 3.0, 1.0 / 3.0, 3.0);
	vec3 p = abs(fract(c.xxx + K.xyz) * 6.0 - K.www);
	return c.z * mix(K.xxx, clamp(p - K.xxx, 0.0, 1.0), c.y);
}

void main() {
  vec4 initialColor = texture( texture0, fragTexCoords ) * fragColor;
  vec3 hsv = rgb2hsv(initialColor.rgb);
  hsv.x = mod(fragHSV.x + hsv.x, 1.0);
  hsv.y = clamp(fragHSV.y * hsv.y, 0, 1);
  hsv.z = clamp(fragHSV.z * hsv.z, 0, 1);
  color = vec4(hsv2rgb(hsv), initialColor.a);
}