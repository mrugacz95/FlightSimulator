#version 300 es
precision mediump float;         // Set the default precision to medium. We don't need as high of a
                                    // precision in the fragment shader.
in vec4 texCoord;        	// This is the color from the vertex shader interpolated across the

in vec4 vN; //interpolowany wektor normalny w przestrzeni oka
in vec4 vV; //interpolowany wektor do obserwatora w przestrzeni oka
in vec4 vL; //interpolowany wektor do zrodla swiatla w przestrzeni oka

uniform sampler2D u_Texture;
in vec2 v_TexCoordinate;
out vec4 fragColor;
void main()                   	// The entry point for our fragment shader.
{
    vec4 iC = texture(u_Texture, v_TexCoordinate);
	vec4 mN=normalize(vN); //normalizuj interpolowany wektor normalny w przestrzeni oka
	vec4 mL=normalize(vL); //normalizuj interpolowany wektor do obserwatora w przestrzeni oka
	vec4 mV=normalize(vV); //normalizuj interpolowany wektor do zrodla swiatla w przestrzeni oka
	vec4 mR=reflect(-mL,mN); //oblicz wektor odbity w przestrzeni oka

	vec4 Ma=iC; //Kolor materialu dla swiatla otoczenia
	vec4 La=vec4(0,0,0,1); //Kolor swiatla otoczenia
	vec4 Md=iC; //Kolor materialu dla swiatla rozproszonego
	vec4 Ld=vec4(1,1,1,1); //Kolor swiatla rozpraszanego
	vec4 Ms=iC; //Kolor materialu dla swiatla odbitego
	vec4 Ls=vec4(1,1,1,1); //Kolor swiatla odbijanego
	float shininess=50.f; //Polyskliwosc materialu

	float nl=max(0.5f,dot(mN,mL)); //Oblicz czlon Lamberta
	float rv=pow(max(0.f,dot(mN,mL)),shininess); //Oblicz czlon Phonga

    fragColor=vec4((Ma*La+Md*Ld*nl+Ms*Ls*rv).xyz,iC.a);// Pass the color directly through the pipeline.
    //fragColor=vec4((Ma*La+Md*Ld*nl).xyz,iC.a);

}