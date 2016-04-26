#version 330
#define Integral(x, p, np) ((floor(x)*(p)) + max(fract (x) - (np), 0.0))

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

uniform int isTexture; // 0 for not texture, 1 for normal texture bind to "sampler2D" uniform, 2 for procedural texture computed in this shader, 3 for psychadelic texture
uniform sampler2D textureSampler;

uniform int isToonShading;

//Computes one final spot light with attenuation
vec3 getLight(vec4 lightPosition, vec3 lightAmbientColor, vec3 lightDiffuseColor, vec3 lightSpecularColor,
              vec3 ambientColor, vec3 diffuseColor, vec3 specularColor, float aConst , float aLin, float aQuad, vec3 v){

    vec3 light;
    if(lightPosition.w == 0.0) {
        light = normalize(lightPosition.xyz);
    } else {
        light = normalize(lightPosition.xyz - vPosition);
    }
    float d = max(dot(vNormal, light), 0.0);
    float s = d * pow(max(dot(vNormal, normalize(light + v)), 0.0), materialShininess);
    float distLight = distance(vPosition,lightPosition.xyz);
    float attenuation = 1/(aConst + aLin * distLight + aQuad * distLight * distLight);

    return (specularColor * lightSpecularColor * s +
                                    diffuseColor * lightDiffuseColor * d +
                                    ambientColor * lightAmbientColor) * attenuation;
}

vec3 getProceduralWallWhiteTexture(){
float factor = smoothstep(0.45,0.5,abs(fract(vPosition.z)-0.5));
return mix(vec3(1,1,1),vec3(1, 0.5, 0.0),factor);
}

vec3 getProceduralWallColorTexture(){
float factor = smoothstep(0.45,0.5,abs(fract(vPosition.x)-0.5));
return mix(vec3(1, 0.5, 0.0),vec3(1,1,1),factor);
}

vec3 getRedShading(vec3 light){
 float intensity = (light.x + light.y +light.z)/3;
         if(intensity > 0.60){
             return vec3(1.0,0.5,0.5);
         }
         if(intensity > 0.5){
                     return vec3(0.8,0.35,0.35);
         }
         if(intensity > 0.4){
             return vec3(0.7,0.25,0.25);
         }
         if(intensity > 0.3){
             return vec3(0.5,0.25,0.25);
         }
         if(intensity > 0.22){
             return vec3(0.45,0.2,0.2);
         }
                 if(intensity > 0.15){
                     return vec3(0.35,0.1,0.1);
                 }
         return vec3(0.3,0.1,0.1);
 }

 vec3 getBlueShading(vec3 light){
  float intensity = (light.x + light.y +light.z)/3;
          if(intensity > 0.60){
              return vec3(0.5,0.5,1);
          }
          if(intensity > 0.5){
                      return vec3(0.35,0.35,0.8);
          }
          if(intensity > 0.4){
              return vec3(0.25,0.25,0.7);
          }
          if(intensity > 0.3){
              return vec3(0.25,0.25,0.5);
          }
          if(intensity > 0.22){
              return vec3(0.2,0.2,0.45);
          }
                  if(intensity > 0.15){
                      return vec3(0.1,0.1,0.35);
                  }
          return vec3(0.1,0.1,0.3);
  }

   vec3 getGreenShading(vec3 light){
    float intensity = (light.x + light.y +light.z)/3;
            if(intensity > 0.60){
                return vec3(0.5,1,0.5);
            }
            if(intensity > 0.5){
                        return vec3(0.35,0.8,0.35);
            }
            if(intensity > 0.4){
                return vec3(0.25,0.7,0.25);
            }
            if(intensity > 0.3){
                return vec3(0.25,0.5,0.25);
            }
            if(intensity > 0.22){
                return vec3(0.2,0.45,0.2);
            }
                    if(intensity > 0.15){
                        return vec3(0.1,0.35,0.1);
                    }
            return vec3(0.1,0.3,0.1);
    }


void main() {

    vec3 v = normalize(eyePosition - vPosition);

    // CALCULATE LIGHT WITH MATERIAL/TEXTURE/PROCEDURAL TEXTURE

    vec3 diffuseColor = materialDiffuseColor;
    vec3 ambientColor = materialAmbientColor;
    if(isTexture == 1){
        vec3 texture_color = texture(textureSampler,vTex_coord).rgb;
        diffuseColor = texture_color;
        ambientColor = texture_color;
    } else if (isTexture == 2){ //procedural wall texture
        diffuseColor = getProceduralWallWhiteTexture();
        ambientColor = diffuseColor;
    } else if (isTexture == 3){   //procedural wall texture
              diffuseColor = getProceduralWallColorTexture();
              ambientColor = diffuseColor;
          }
    vec3 lightFinal_1 = getLight(lightPosition_1, lightAmbientColor_1,  lightDiffuseColor_1, lightSpecularColor_1,
                        ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_1 , lightAttenuationConst2_1, lightAttenuationConst3_1, v);

    vec3 lightFinal_2 = getLight(lightPosition_2, lightAmbientColor_2,  lightDiffuseColor_2, lightSpecularColor_2,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_2 , lightAttenuationConst2_2, lightAttenuationConst3_2, v);

                         
    vec3 lightFinal_3 = getLight(lightPosition_3, lightAmbientColor_3,  lightDiffuseColor_3, lightSpecularColor_3,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_3 , lightAttenuationConst2_3, lightAttenuationConst3_3, v);

                                                 
    vec3 lightFinal_4 = getLight(lightPosition_4, lightAmbientColor_4,  lightDiffuseColor_4, lightSpecularColor_4,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_4 , lightAttenuationConst2_4, lightAttenuationConst3_4, v);

                                                                          
    vec3 lightFinal_5 = getLight(lightPosition_5, lightAmbientColor_5,  lightDiffuseColor_5, lightSpecularColor_5,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_5 , lightAttenuationConst2_5, lightAttenuationConst3_5, v);

                                                                                            
    vec3 lightFinal_6 = getLight(lightPosition_6, lightAmbientColor_6,  lightDiffuseColor_6, lightSpecularColor_6,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_6 , lightAttenuationConst2_6, lightAttenuationConst3_6, v);

                                                                                                                  
    vec3 lightFinal_7 = getLight(lightPosition_7, lightAmbientColor_7,  lightDiffuseColor_7, lightSpecularColor_7,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_7 , lightAttenuationConst2_7, lightAttenuationConst3_7, v);

                                                                                                                                          
    vec3 lightFinal_8 = getLight(lightPosition_8, lightAmbientColor_8,  lightDiffuseColor_8, lightSpecularColor_8,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_8 , lightAttenuationConst2_8, lightAttenuationConst3_8, v);

    vec3 lightFinal_9 = getLight(lightPosition_9, lightAmbientColor_9,  lightDiffuseColor_9, lightSpecularColor_9,
                            ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_9 , lightAttenuationConst2_9, lightAttenuationConst3_9, v);

                                                                                                                                                                                    
   vec3 lightFinal_10 = getLight(lightPosition_10, lightAmbientColor_10,  lightDiffuseColor_10, lightSpecularColor_10,
                           ambientColor, diffuseColor, materialSpecularColor, lightAttenuationConst1_10 , lightAttenuationConst2_10, lightAttenuationConst3_10, v);
                                                                                                                                                               

   vec3 lightFinal = lightGlobalAmbientColor * materialAmbientColor + lightFinal_1 + lightFinal_2 + lightFinal_3 +
                lightFinal_4 + lightFinal_5 + lightFinal_6 + lightFinal_7 + lightFinal_8 + lightFinal_9 + lightFinal_10;

   if(isToonShading == 0){
        fragColor = vec4(lightFinal, alfa);
   }else if(isToonShading == 1){//red shading
        fragColor = vec4(getRedShading(lightFinal), alfa);
   }else if(isToonShading == 2){//blue shading
             fragColor = vec4(getBlueShading(lightFinal), alfa);
   }else if(isToonShading == 3){//green shading
                 fragColor = vec4(getGreenShading(lightFinal), alfa);
       }

}