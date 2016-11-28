//--------------------------------------------------------------------------------------
// Global variables
//--------------------------------------------------------------------------------------
texture 	g_texture;
float       g_OffsetX;
float       g_OffsetY;

//------------------------------------------------
// テクスチャサンプラ
//------------------------------------------------
sampler TextureSampler = 
sampler_state
{
    Texture = <g_texture>;
	MipFilter = NONE;
    MinFilter = LINEAR;
    MagFilter = LINEAR;

	AddressU =Clamp;
	AddressV =Clamp;
	
};

//--------------------------------------------------------------------------------------
// Vertex shader
//--------------------------------------------------------------------------------------
void BasicVS( float4 in_pos : POSITION0,
              float4 in_color: COLOR0,
 			  float2 in_tex : TEXCOORD0,
 			  out float4 out_pos : POSITION0,
			  out float4 out_color :COLOR0,
 			  out float2 out_tex : TEXCOORD0)
{
	out_pos = in_pos;
	out_tex = in_tex;
	out_color= in_color;
}

//--------------------------------------------------------------------------------------
// Pixel shader
//--------------------------------------------------------------------------------------
void BasicPS( float4 in_color : COLOR0,
              float2 in_tex : TEXCOORD0,
 			  out float4 out_color : COLOR0)
{



 float2 tex0;
 float2 tex1;
 float2 tex2;
 float2 tex3;



 in_tex.x = in_tex.x + g_OffsetX;
 in_tex.y = in_tex.y + g_OffsetY;

 tex0.x = in_tex.x - 0.5*g_OffsetX;
 tex0.y = in_tex.y - 0.5*g_OffsetY;

 tex1.x = in_tex.x - 0.5*g_OffsetX;
 tex1.y = in_tex.y + 0.5*g_OffsetY;

 tex2.x = in_tex.x + 0.5*g_OffsetX;
 tex2.y = in_tex.y + 0.5*g_OffsetY;

 tex3.x = in_tex.x - 0.5*g_OffsetX;
 tex3.y = in_tex.y + 0.5*g_OffsetY;
 
 float4 tex_color = tex2D(TextureSampler, tex0)+tex2D(TextureSampler, tex1)+tex2D(TextureSampler,tex2)+tex2D(TextureSampler, tex3);
 out_color = tex_color*in_color*0.25;
	
}

//--------------------------------------------------------------------------------------
// テクニック
//--------------------------------------------------------------------------------------
technique BasicTech
{
    pass P0
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS();


		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;

	}
    pass P1
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS();


		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;

		AlphaBlendEnable = True;
		AlphaTestEnable = True;
		AlphaFunc = GREATER;
		AlphaRef = 0.99;
	}
}

