#version 330

out vec4 fragColor;

in vec3 vNormal;
in vec3 vPosition;
in vec2 vTex_coord;

uniform vec4 light1Position;

uniform vec3 light1AmbientColor;
uniform vec3 light1DiffuseColor;
uniform vec3 light1SpecularColor;

uniform vec4 light2Position;

uniform vec3 light2AmbientColor;
uniform vec3 light2DiffuseColor;
uniform vec3 light2SpecularColor;

uniform vec3 materialAmbientColor;
uniform vec3 materialDiffuseColor;
uniform vec3 materialSpecularColor;
uniform float materialShininess;

uniform vec3 eyePosition;

uniform vec3 color;

uniform int isTexture;
uniform sampler2D texture;

void main() {

    vec3 v = normalize(eyePosition - vPosition);

    vec3 light;
    if(light1Position.w == 0.0) {
        light = normalize(light1Position.xyz);
    } else {
        light = normalize(light1Position.xyz - vPosition);
    }

    vec3 light2;
    if(light2Position.w == 0.0) {
        light2 = normalize(light2Position.xyz);
    } else {
        light2 = normalize(light2Position.xyz - vPosition);
    }

    vec3 h = normalize(light + v);

    float d = max(dot(vNormal, light), 0.0);
    float s = d * pow(max(dot(vNormal, h), 0.0), materialShininess);

    vec3 h2 = normalize(light2 + v);

    float d2 = max(dot(vNormal, light2), 0.0);
    float s2 =d2 * pow(max(dot(vNormal, h2), 0.0), materialShininess);

    //zoslabnutie
    float distLight1 = distance(vPosition,light1Position.xyz);
    float attenuation1 = 1/(0.1 + 0.1*distLight1 + 0.1*distLight1*distLight1);

    float distLight2 = distance(vPosition,light2Position.xyz);
    float attenuation2 = 1/(0.1 + 0.1*distLight2 + 0.1*distLight2*distLight2);

    vec3 lightFinal;

    if(isTexture == 1){
        vec3 texture_color = texture(texture,vTex_coord).rgb;
        vec3 light1Final = (materialSpecularColor * light1SpecularColor * s +
                                              texture_color * light1DiffuseColor * d +
                                              texture_color * light1AmbientColor) * attenuation1;
        vec3 light2Final = (materialSpecularColor * light2SpecularColor * s2 +
                                              texture_color * light2DiffuseColor * d2 +
                                              texture_color * light2AmbientColor) * attenuation2 ;

        lightFinal = light1Final + light2Final;

    } else{

        vec3 light1Final = (materialSpecularColor * light1SpecularColor * s +
                                      materialDiffuseColor * light1DiffuseColor * d +
                                      materialAmbientColor * light1AmbientColor) * attenuation1;

        vec3 light2Final = (materialSpecularColor * light2SpecularColor * s2 +
                                      materialDiffuseColor * light2DiffuseColor * d2 +
                                      materialAmbientColor * light2AmbientColor ) * attenuation2;

        lightFinal = light1Final + light2Final;
    }


    fragColor = vec4(lightFinal, 1.0);
}