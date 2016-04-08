#version 330

in vec3 position;
in vec3 normal;
in vec2 tex_coord;

out vec3 vNormal;
out vec3 vPosition;
out vec2 vTex_coord;

uniform mat4 MVP;
uniform mat3 N;
uniform mat4 model;

uniform float texCoordMultiplier;
uniform float texCoordOffset;

void main() {
    vTex_coord = texCoordMultiplier * tex_coord + texCoordOffset;
    vPosition = vec3(model * vec4(position, 1.0));
    vNormal = normalize(N * normal);
    gl_Position = MVP * vec4(position, 1.0);
}