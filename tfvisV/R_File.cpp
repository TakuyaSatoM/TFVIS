#include "R_Math.h"



namespace FILES
{
	//! �w��t�H���_�̃t�@�C�����擾����
	// ! ex: std::vector<std::string> path = util::Directory::GetFiles("C:\\aa\\", "*.png");
	
	vector<string> GetFiles(const string& dir_path, const string& filter)
	{
		WIN32_FIND_DATAA fd;

		std::string ss = dir_path + filter;
		HANDLE hFind = FindFirstFileA(ss.c_str(), &fd);

		// �������s
		if (hFind == INVALID_HANDLE_VALUE)
		{
			throw std::exception("util::Directory::GetFiles failed");
		}

		vector<string> fileList;
		do
		{
			// �t�H���_�͏���
			if (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)
				continue;
			// �B���t�@�C���͏���
			if (fd.dwFileAttributes & FILE_ATTRIBUTE_HIDDEN)
				continue;

			fileList.push_back(fd.cFileName);
		} while(FindNextFileA(hFind, &fd));

		FindClose(hFind);

		return fileList;
	}

}