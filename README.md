# OpenGLScene
Semestral project for course FI:PV112 Computer Graphics API. Scene of room with use of OpenGL3 and GLSL shaders.

###Setup
This project uses Maven. Library *jglm* is not contained in central maven repository. It is included in project lib folder. 
To install *jglm* to local maven repository run following command in project root directory:

mvn install:install-file -Dfile=lib/jglm.jar -DgroupId=jglm -DartifactId=jglm -Dversion=1 -Dpackaging=jar

###Features
* Free camera movement in room (WASD control + pressed and dragged mouse to look around)
* 10 point lights with adjustable attenuation (M - less, N - more light)
* Procedural textures used on walls
* Clock display current system time with every second tick
* Radio plays music when camera is close to it (radio gets red) and key E is pressed
* Rotating globe, possible stop/start rotating when camera is close, and key E is pressed
* Toon shading, press T to activate and switch between colors

### Pictures
![pic1](https://raw.githubusercontent.com/MartinStyk/OpenGLScene/master/pic1.JPG)
![pic2](https://raw.githubusercontent.com/MartinStyk/OpenGLScene/master/pic2.JPG)
![pic3](https://raw.githubusercontent.com/MartinStyk/OpenGLScene/master/pic3.JPG)
