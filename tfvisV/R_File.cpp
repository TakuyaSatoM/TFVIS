#include "R_Math.h"



namespace FILES
{
	//! 指定フォルダのファイルを取得する
	// ! ex: std::vector<std::string> path = util::Directory::GetFiles("C:\\aa\\", "*.png");
	
	vector<string> GetFiles(const string& dir_path, const string& filter)
	{
		WIN32_FIND_DATAA fd;

		std::string ss = dir_path + filter;
		HANDLE hFind = FindFirstFileA(ss.c_str(), &fd);

		// 検索失敗
		if (hFind == INVALID_HANDLE_VALUE)
		{
			throw std::exception("util::Directory::GetFiles failed");
		}

		vector<string> fileList;
		do
		{
			// フォルダは除く
			if (fd.dwFileAttributes & FILE_ATTRIBUTE_DIRECTORY)
				continue;
			// 隠しファイルは除く
			if (fd.dwFileAttributes & FILE_ATTRIBUTE_HIDDEN)
				continue;

			fileList.push_back(fd.cFileName);
		} while(FindNextFileA(hFind, &fd));

		FindClose(hFind);

		return fileList;
	}

}