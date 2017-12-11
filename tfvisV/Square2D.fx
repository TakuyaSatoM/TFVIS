//--------------------------------------------------------------------------------------
// Global variables
//--------------------------------------------------------------------------------------
texture 	g_texture;

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
 			  out float4 out_color : COLOR0,
			  uniform bool b_tex)
{
  if(b_tex)
  {
   float4 tex_color = tex2D(TextureSampler, in_tex);
   out_color = tex_color*in_color;
  }
  else
  {
   out_color=in_color;
  }
  

}

//--------------------------------------------------------------------------------------
// テクニック
//--------------------------------------------------------------------------------------
technique BasicTech
{
    pass P0
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS(0);


		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;

		AlphaBlendEnable = True;
		AlphaTestEnable = True;
		AlphaFunc = GREATER;
		AlphaRef = 0.99;

		ZEnable =True;
		ZWriteEnable =True;
		ZFunc =LESSEQUAL;
	}
	pass P1
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS(0);

		
		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;
		
		AlphaBlendEnable = True;
		AlphaTestEnable = True;
		AlphaFunc = GREATER;
		AlphaRef = 0.99;

		ZEnable =True;
		ZWriteEnable =True;
		ZFunc =LESSEQUAL;
	}
    pass P2
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS(1);


		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;

		AlphaBlendEnable = True;
		AlphaTestEnable = True;
		AlphaFunc = GREATER;
		AlphaRef = 0.99;

		ZEnable =True;
		ZWriteEnable =True;
		ZFunc =LESSEQUAL;
	}
	pass P3
    {
        vertexShader = compile vs_3_0 BasicVS();
        pixelShader  = compile ps_3_0 BasicPS(1);

		
		DestBlend = INVSRCALPHA;
        SrcBlend = SRCALPHA;
		
		AlphaBlendEnable = True;
		AlphaTestEnable = True;
		AlphaFunc = GREATER;
		AlphaRef = 0.99;

		ZEnable =True;
		ZWriteEnable =True;
		ZFunc =LESSEQUAL;
	}

}

