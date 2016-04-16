#version 330

out vec4 fragColor;

in vec3 vNormal;
in vec3 vPosition;
in vec2 vTex_coord;

uniform vec3 lightGlobalAmbientColor;
uniform float alfa;

uniform vec4 lightPosition_1;
uniform vec3 lightAmbientColor_1;
uniform vec3 lightDiffuseColor_1;
uniform vec3 lightSpecularColor_1;
uniform float lightAttenuationConst1_1;
uniform float lightAttenuationConst2_1;
uniform float lightAttenuationConst3_1;

uniform vec4 lightPosition_2;
uniform vec3 lightAmbientColor_2;
uniform vec3 lightDiffuseColor_2;
uniform vec3 lightSpecularColor_2;
uniform float lightAttenuationConst1_2;
uniform float lightAttenuationConst2_2;
uniform float lightAttenuationConst3_2;

uniform vec4 lightPosition_3;
uniform vec3 lightAmbientColor_3;
uniform vec3 lightDiffuseColor_3;
uniform vec3 lightSpecularColor_3;
uniform float lightAttenuationConst1_3;
uniform float lightAttenuationConst2_3;
uniform float lightAttenuationConst3_3;

uniform vec4 lightPosition_4;
uniform vec3 lightAmbientColor_4;
uniform vec3 lightDiffuseColor_4;
uniform vec3 lightSpecularColor_4;
uniform float lightAttenuationConst1_4;
uniform float lightAttenuationConst2_4;
uniform float lightAttenuationConst3_4;

uniform vec4 lightPosition_5;
uniform vec3 lightAmbientColor_5;
uniform vec3 lightDiffuseColor_5;
uniform vec3 lightSpecularColor_5;
uniform float lightAttenuationConst1_5;
uniform float lightAttenuationConst2_5;
uniform float lightAttenuationConst3_5;

uniform vec4 lightPosition_6;
uniform vec3 lightAmbientColor_6;
uniform vec3 lightDiffuseColor_6;
uniform vec3 lightSpecularColor_6;
uniform float lightAttenuationConst1_6;
uniform float lightAttenuationConst2_6;
uniform float lightAttenuationConst3_6;

uniform vec4 lightPosition_7;
uniform vec3 lightAmbientColor_7;
uniform vec3 lightDiffuseColor_7;
uniform vec3 lightSpecularColor_7;
uniform float lightAttenuationConst1_7;
uniform float lightAttenuationConst2_7;
uniform float lightAttenuationConst3_7;

uniform vec4 lightPosition_8;
uniform vec3 lightAmbientColor_8;
uniform vec3 lightDiffuseColor_8;
uniform vec3 lightSpecularColor_8;
uniform float lightAttenuationConst1_8;
uniform float lightAttenuationConst2_8;
uniform float lightAttenuationConst3_8;

uniform vec4 lightPosition_9;
uniform vec3 lightAmbientColor_9;
uniform vec3 lightDiffuseColor_9;
uniform vec3 lightSpecularColor_9;
uniform float lightAttenuationConst1_9;
uniform float lightAttenuationConst2_9;
uniform float lightAttenuationConst3_9;

uniform vec4 lightPosition_10;
uniform vec3 lightAmbientColor_10;
uniform vec3 lightDiffuseColor_10;
uniform vec3 lightSpecularColor_10;
uniform float lightAttenuationConst1_10;
uniform float lightAttenuationConst2_10;
uniform float lightAttenuationConst3_10;

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

    // SVETLO -> BODOVE/SMEROVE, PARAMETRE ODRAZU, ZOSLABNUTIE

    vec3 light_1;
    if(lightPosition_1.w == 0.0) {
        light_1 = normalize(lightPosition_1.xyz);
    } else {
        light_1 = normalize(lightPosition_1.xyz - vPosition);
    }
    float d_1 = max(dot(vNormal, light_1), 0.0);
    float s_1 = d_1 * pow(max(dot(vNormal, normalize(light_1 + v)), 0.0), materialShininess);    
    float distLight_1 = distance(vPosition,lightPosition_1.xyz);
    float attenuation_1 = 1/(lightAttenuationConst1_1 + lightAttenuationConst2_1 * distLight_1 + lightAttenuationConst3_1 * distLight_1 * distLight_1);

    vec3 light_2;
    if(lightPosition_2.w == 0.0) {
        light_2 = normalize(lightPosition_2.xyz);
    } else {
        light_2 = normalize(lightPosition_2.xyz - vPosition);
    }
    float d_2 = max(dot(vNormal, light_2), 0.0);
    float s_2 = d_2 * pow(max(dot(vNormal, normalize(light_2 + v)), 0.0), materialShininess);
    float distLight_2 = distance(vPosition,lightPosition_2.xyz);
    float attenuation_2 = 1/(lightAttenuationConst1_2 + lightAttenuationConst2_2 * distLight_2 + lightAttenuationConst3_2 * distLight_2 * distLight_2);

    vec3 light_3;
    if(lightPosition_3.w == 0.0) {
        light_3 = normalize(lightPosition_3.xyz);
    } else {
        light_3 = normalize(lightPosition_3.xyz - vPosition);
    }
    float d_3 = max(dot(vNormal, light_3), 0.0);
    float s_3 = d_3 * pow(max(dot(vNormal, normalize(light_3 + v)), 0.0), materialShininess);
    float distLight_3 = distance(vPosition,lightPosition_3.xyz);
    float attenuation_3 = 1/(lightAttenuationConst1_3 + lightAttenuationConst2_3 * distLight_3 + lightAttenuationConst3_3 * distLight_3 * distLight_3);

    vec3 light_4;
    if(lightPosition_4.w == 0.0) {
        light_4 = normalize(lightPosition_4.xyz);
    } else {
        light_4 = normalize(lightPosition_4.xyz - vPosition);
    }
    float d_4 = max(dot(vNormal, light_4), 0.0);
    float s_4 = d_4 * pow(max(dot(vNormal, normalize(light_4 + v)), 0.0), materialShininess);
    float distLight_4 = distance(vPosition,lightPosition_4.xyz);
    float attenuation_4 = 1/(lightAttenuationConst1_4 + lightAttenuationConst2_4 * distLight_4 + lightAttenuationConst3_4 * distLight_4 * distLight_4);
    
    vec3 light_5;
    if(lightPosition_5.w == 0.0) {
        light_5 = normalize(lightPosition_5.xyz);
    } else {
        light_5 = normalize(lightPosition_5.xyz - vPosition);
    }
    float d_5 = max(dot(vNormal, light_5), 0.0);
    float s_5 = d_5 * pow(max(dot(vNormal, normalize(light_5 + v)), 0.0), materialShininess);
    float distLight_5 = distance(vPosition,lightPosition_5.xyz);
    float attenuation_5 = 1/(lightAttenuationConst1_5 + lightAttenuationConst2_5 * distLight_5 + lightAttenuationConst3_5 * distLight_5 * distLight_5);
    
    vec3 light_6;
    if(lightPosition_6.w == 0.0) {
        light_6 = normalize(lightPosition_6.xyz);
    } else {
        light_6 = normalize(lightPosition_6.xyz - vPosition);
    }
    float d_6 = max(dot(vNormal, light_6), 0.0);
    float s_6 = d_6 * pow(max(dot(vNormal, normalize(light_6 + v)), 0.0), materialShininess);
    float distLight_6 = distance(vPosition,lightPosition_6.xyz);
    float attenuation_6 = 1/(lightAttenuationConst1_6 + lightAttenuationConst2_6 * distLight_6 + lightAttenuationConst3_6 * distLight_6 * distLight_6);
     
    vec3 light_7;
    if(lightPosition_7.w == 0.0) {
        light_7 = normalize(lightPosition_7.xyz);
    } else {
        light_7 = normalize(lightPosition_7.xyz - vPosition);
    }
    float d_7 = max(dot(vNormal, light_7), 0.0);
    float s_7 = d_7 * pow(max(dot(vNormal, normalize(light_7 + v)), 0.0), materialShininess);    
    float distLight_7 = distance(vPosition,lightPosition_7.xyz);
    float attenuation_7 = 1/(lightAttenuationConst1_7 + lightAttenuationConst2_7 * distLight_7 + lightAttenuationConst3_7 * distLight_7 * distLight_7);
    
    vec3 light_8;
    if(lightPosition_8.w == 0.0) {
        light_8 = normalize(lightPosition_8.xyz);
    } else {
        light_8 = normalize(lightPosition_8.xyz - vPosition);
    }
    float d_8 = max(dot(vNormal, light_8), 0.0);
    float s_8 = d_8 * pow(max(dot(vNormal, normalize(light_8 + v)), 0.0), materialShininess);    
    float distLight_8 = distance(vPosition,lightPosition_8.xyz);
    float attenuation_8 = 1/(lightAttenuationConst1_8 + lightAttenuationConst2_8 * distLight_8 + lightAttenuationConst3_8 * distLight_8 * distLight_8);
    
    vec3 light_9;
    if(lightPosition_9.w == 0.0) {
        light_9 = normalize(lightPosition_9.xyz);
    } else {
        light_9 = normalize(lightPosition_9.xyz - vPosition);
    }
    float d_9 = max(dot(vNormal, light_9), 0.0);
    float s_9 = d_9 * pow(max(dot(vNormal, normalize(light_9 + v)), 0.0), materialShininess);   
    float distLight_9 = distance(vPosition,lightPosition_9.xyz);
    float attenuation_9 = 1/(lightAttenuationConst1_9 + lightAttenuationConst2_9 * distLight_9 + lightAttenuationConst3_9 * distLight_9 * distLight_9);
     
    vec3 light_10;
    if(lightPosition_10.w == 0.0) {
        light_10 = normalize(lightPosition_10.xyz);
    } else {
        light_10 = normalize(lightPosition_10.xyz - vPosition);
    }
    float d_10 = max(dot(vNormal, light_10), 0.0);
    float s_10 = d_10 * pow(max(dot(vNormal, normalize(light_10 + v)), 0.0), materialShininess);
    float distLight_10 = distance(vPosition,lightPosition_10.xyz);
    float attenuation_10 = 1/(lightAttenuationConst1_10 + lightAttenuationConst2_10 * distLight_10 + lightAttenuationConst3_10 * distLight_10 * distLight_10);

    // FINAL LIGHT CALCULATION
    // CALCULATE WITH MATERIAL/TEXTURE


    vec3 diffuseColor = materialDiffuseColor;
    vec3 ambientColor = materialAmbientColor;
    if(isTexture == 1){
        vec3 texture_color = texture(texture,vTex_coord).rgb;
        diffuseColor = texture_color;
        ambientColor = texture_color;
    }
    vec3 lightFinal_1 = (materialSpecularColor * lightSpecularColor_1 * s_1 +
                         diffuseColor * lightDiffuseColor_1 * d_1 +
                         ambientColor * lightAmbientColor_1) * attenuation_1;

    vec3 lightFinal_2 = (materialSpecularColor * lightSpecularColor_2 * s_2 +
                         diffuseColor * lightDiffuseColor_2 * d_2 +
                         ambientColor * lightAmbientColor_2) * attenuation_2;
                         
    vec3 lightFinal_3 = (materialSpecularColor * lightSpecularColor_3 * s_3 +
                         diffuseColor * lightDiffuseColor_3 * d_3 +
                         ambientColor * lightAmbientColor_3) * attenuation_3; 
                                                 
    vec3 lightFinal_4 = (materialSpecularColor * lightSpecularColor_4 * s_4 +
                         diffuseColor * lightDiffuseColor_4 * d_4 +
                         ambientColor * lightAmbientColor_4) * attenuation_4;
                                                                          
    vec3 lightFinal_5 = (materialSpecularColor * lightSpecularColor_5 * s_5 +
                         diffuseColor * lightDiffuseColor_5 * d_5 +
                         ambientColor * lightAmbientColor_5) * attenuation_5;      
                                                                                            
    vec3 lightFinal_6 = (materialSpecularColor * lightSpecularColor_6 * s_6 +
                         diffuseColor * lightDiffuseColor_6 * d_6 +
                         ambientColor * lightAmbientColor_6) * attenuation_6;   
                                                                                                                  
    vec3 lightFinal_7 = (materialSpecularColor * lightSpecularColor_7 * s_7 +
                         diffuseColor * lightDiffuseColor_7 * d_7 +
                         ambientColor * lightAmbientColor_7) * attenuation_7; 
                                                                                                                                          
    vec3 lightFinal_8 = (materialSpecularColor * lightSpecularColor_8 * s_8 +
                         diffuseColor * lightDiffuseColor_8 * d_8 +
                         ambientColor * lightAmbientColor_8) * attenuation_8;   

    vec3 lightFinal_9 = (materialSpecularColor * lightSpecularColor_9 * s_9 +
                         diffuseColor * lightDiffuseColor_9 * d_9 +
                         ambientColor * lightAmbientColor_9) * attenuation_9;    
                                                                                                                                                                                    
    vec3 lightFinal_10 = (materialSpecularColor * lightSpecularColor_10 * s_10 +
                         diffuseColor * lightDiffuseColor_10 * d_10 +
                         ambientColor * lightAmbientColor_10) * attenuation_10;                                                                                                                                                                                    

    vec3 lightFinal = lightGlobalAmbientColor * materialAmbientColor + lightFinal_1 + lightFinal_2 + lightFinal_3 +
                lightFinal_4 + lightFinal_5 + lightFinal_6 + lightFinal_7 + lightFinal_8 + lightFinal_9 + lightFinal_10;

    fragColor = vec4(lightFinal, alfa);
}