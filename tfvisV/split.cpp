#include "vector";

vector<string> split(const string &s, char delim) {
    vector<string> elems;
    string item;
	for (int i=0; i<s.length(); i++){
        if (s[i] == delim) {
            if (!item.empty())
                elems.push_back(item);
            item.clear();
        }
        else {
            item += s[i];
        }
    }
    if (!item.empty())
        elems.push_back(item);
    return elems;
}