#include "DXCommon.h"
#include "NHLib.h"

void ZBufferClear()
{
	if(FAILED(GetDirectXDevice()->Clear(0,NULL, 
								D3DCLEAR_ZBUFFER, 
								  D3DXCOLOR(0.2f, 0.2f, 0.4f, 1.0f), // �w�i�𔒂ɐݒ�
	 							1.0f, 1))) {
									CheckBox(("RenderingTexture_Class::�����_�C�����OCLass ���������s"));
		return;
	}
}