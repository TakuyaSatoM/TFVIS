#include "DXCommon.h"
#include "NHLib.h"

void ZBufferClear()
{
	if(FAILED(GetDirectXDevice()->Clear(0,NULL, 
								D3DCLEAR_ZBUFFER, 
								  D3DXCOLOR(0.2f, 0.2f, 0.4f, 1.0f), // ”wŒi‚ğ”’‚Éİ’è
	 							1.0f, 1))) {
									CheckBox(("RenderingTexture_Class::ƒŒƒ“ƒ_ƒCƒŠƒ“ƒOCLass ‰Šú‰»¸”s"));
		return;
	}
}