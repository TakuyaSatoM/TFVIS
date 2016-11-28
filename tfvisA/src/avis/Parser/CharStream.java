package avis.Parser;

public class CharStream {
	
	public static final boolean staticFlag = false;
	public int bufpos = -1;
	
	int bufsize;
	int available;
	int tokenBegin;
	
	protected int column = 0;
	protected int line =  1;
	protected int maxNextChar = 0;
	protected int nextChar = -1;
	protected int inBuf = 0;
	protected int tabSize = 1;
	
	protected boolean prevCharIsCR = false;
	protected boolean prevCharIsLF = false;
	
	protected java.io.Reader inputStream;
	
	protected char[] nextCharBuf;
	protected char[] buffer;
	protected int[] bufline;
	protected int[] bufcolumn;
	
	
	protected void setTabSize(int i) {
		tabSize = i;
	}
	
	protected int getTabSize(int i) {
		return tabSize;
	}
	
	public int getColumn() {
		return bufcolumn[bufpos];
	}
	
	public int getLine() {
		return bufline[bufpos];
	}
	
	public int getEndColumn() {
		return bufcolumn[bufpos];
	}
	
	public int getEndLine() {
		return bufline[bufpos];
	}
	
	public int getBeginColumn() {
		return bufcolumn[tokenBegin];
	}
	
	public int getBeginLine() {
		return bufline[tokenBegin];
	}
	
	
	public String getImage() {
		if(bufpos >= tokenBegin)
			return new String(buffer, tokenBegin, bufpos - tokenBegin + 1);
		else
			return new String(buffer, tokenBegin, bufsize - tokenBegin) + new String(buffer, 0, bufpos + 1);
	}
	
	
	public char[] getSuffix(int len) {
		char[] ret = new char[len];
		
		if((bufpos + 1) >= len) {
			System.arraycopy(buffer, bufpos - len + 1, ret, 0, len);
		}
		else {
			System.arraycopy(buffer, bufsize - (len - bufpos - 1), ret, 0, len - bufpos - 1);
			System.arraycopy(buffer, 0, ret, len - bufpos - 1, bufpos + 1);
		}
		return ret;
	}
	
	
	public void Done() {
		nextCharBuf = null;
		buffer = null;
		bufline = null;
		bufcolumn = null;
	}
	
	
	public void Backup(int amount) {
		inBuf += amount;
		if((bufpos -= amount) < 0)
			bufpos += bufsize;
	}
	
	
	public CharStream(java.io.Reader dstream, int startLine, int startColumn, int bufferSize) {
		inputStream = dstream;
		line = startLine;
		column = startColumn - 1;
		
		available = bufsize = bufferSize;
		buffer = new char[bufferSize];
		bufline = new int[bufferSize];
		bufcolumn = new int[bufferSize];
		nextCharBuf = new char[4096];
	}
	
	
	public CharStream(java.io.Reader dstream, int startLine, int startColumn) {
		this(dstream, startLine, startColumn, 4096);
	}
	
	
	public CharStream(java.io.Reader dstream) {
		this(dstream, 1, 1, 4096);
	}
	
	
	public CharStream(java.io.InputStream dstream, String encoding, int startLine, int startColumn, int bufferSize) throws java.io.UnsupportedEncodingException {
		this(encoding == null ? new java.io.InputStreamReader(dstream) : new java.io.InputStreamReader(dstream, encoding), startLine, startColumn, bufferSize);
	}
	
	
	public CharStream(java.io.InputStream dstream, int startLine, int startColumn, int bufferSize) {
		this(new java.io.InputStreamReader(dstream), startLine, startColumn, 4096);
	}
	
	
	public CharStream(java.io.InputStream dstream, String encoding, int startline, int startcolumn) throws java.io.UnsupportedEncodingException {
		this(dstream, encoding, startline, startcolumn, 4096);
	}
	
	
	public CharStream(java.io.InputStream dstream, int startline, int startcolumn) {
		this(dstream, startline, startcolumn, 4096);
	}
	
	
	public CharStream(java.io.InputStream dstream, String encoding) throws java.io.UnsupportedEncodingException {
		this(dstream, encoding, 1, 1, 4096);
	}
	
	
	public CharStream(java.io.InputStream dstream) {
		this(dstream, 1, 1, 4096);
	}
	
	
	public void ReInit(java.io.Reader dstream, int startLine, int startColumn, int bufferSize) {
		inputStream = dstream;
		line = startLine;
		column = startColumn - 1;
		
		if(buffer == null || bufferSize != buffer.length) {
			available = bufsize = bufferSize;
			buffer = new char[bufferSize];
			bufline = new int[bufferSize];
			bufcolumn = new int[bufferSize];
			nextCharBuf = new char[4096];
		}
		prevCharIsLF = prevCharIsCR = false;
		tokenBegin = inBuf = maxNextChar = 0;
		nextChar = bufpos = -1;
	}
	
	
	public void ReInit(java.io.Reader dstream, int startLine, int startColumn) {
		ReInit(dstream, startLine, startColumn, 4096);
	}
	
	
	public void ReInit(java.io.Reader dstream) {
		ReInit(dstream, 1, 1, 4096);
	}
	
	
	public void ReInit(java.io.InputStream dstream, String encoding, int startLine, int startColumn, int bufferSize) throws java.io.UnsupportedEncodingException {
		ReInit(encoding == null ? new java.io.InputStreamReader(dstream) : new java.io.InputStreamReader(dstream, encoding), startLine, startColumn, bufferSize);
	}
	
	
	public void ReInit(java.io.InputStream dstream, String encoding, int startLine, int startColumn) throws java.io.UnsupportedEncodingException {
		ReInit(dstream, encoding, startLine, startColumn, 4096);
	}
	
	
	public void ReInit(java.io.InputStream dstream, String encoding) throws java.io.UnsupportedEncodingException {
		ReInit(dstream, encoding, 1, 1, 4096);
	}
	
	
	public void ReInit(java.io.InputStream dstream, int startLine, int startColumn, int bufferSize) {
		ReInit(new java.io.InputStreamReader(dstream), startLine, startColumn, bufferSize);
	}
	
	
	public void ReInit(java.io.InputStream dstream, int startLine, int startColumn) {
		ReInit(dstream, startLine, startColumn, 4096);
	}
	
	
	public void ReInit(java.io.InputStream dstream) {
		ReInit(dstream, 1, 1, 4096);
	}
	
	
	static final int Hexval(char ch) throws java.io.IOException {
		switch(ch) {
		
		case '0':
			return 0;
			
		case '1':
			return 1;
			
		case '2':
			return 2;
			
		case '3':
			return 3;
			
		case '4':
			return 4;
			
		case '5':
			return 5;
			
		case '6':
			return 6;
			
		case '7':
			return 7;
			
		case '8':
			return 8;
			
		case '9':
			return 9;
		
		case 'a':
		case 'A':
			return 10;
			
		case 'b':
		case 'B':
			return 11;
			
		case 'c':
		case 'C':
			return 12;
			
		case 'd':
		case 'D':
			return 13;
			
		case 'e':
		case 'E':
			return 14;
			
		case 'f':
		case 'F':
			return 15;
		}
		throw new java.io.IOException();
	}
	
	
	protected void ExpandBuff(boolean wrapAround) {
		char[] newbuffer = new char[bufsize + 2048];
		int[] newbufline = new int[bufsize + 2048];
		int[] newbufcolumn = new int[bufsize + 2048];
		
		try {
			if(wrapAround) {
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				System.arraycopy(buffer, 0, newbuffer, bufsize - tokenBegin, bufpos);
				buffer = newbuffer;
				
				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				System.arraycopy(bufline, 0, newbufline, bufsize - tokenBegin, bufpos);
				bufline = newbufline;
				
				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				System.arraycopy(bufcolumn, 0, newbufcolumn, bufsize - tokenBegin, bufpos);
				bufcolumn = newbufcolumn;
			}
			else {
				System.arraycopy(buffer, tokenBegin, newbuffer, 0, bufsize - tokenBegin);
				buffer = newbuffer;
				
				System.arraycopy(bufline, tokenBegin, newbufline, 0, bufsize - tokenBegin);
				bufline = newbufline;
				
				System.arraycopy(bufcolumn, tokenBegin, newbufcolumn, 0, bufsize - tokenBegin);
				bufcolumn = newbufcolumn;
			}
		}
		catch(Throwable t) {
			throw new Error(t.getMessage());
		}
		
		available = (bufsize += 2048);
		tokenBegin = 0;
	}
	
	
	protected void FillBuff() throws java.io.IOException {
		int i;
		
		if(maxNextChar == 4096)
			maxNextChar = nextChar = 0;
		
		try {
			if((i = inputStream.read(nextCharBuf, maxNextChar, 4096 - maxNextChar)) == -1) {
				inputStream.close();
				throw new java.io.IOException();
			}
			else {
				maxNextChar += i;
			}
			return;
		}
		catch(java.io.IOException e) {
			if(bufpos != 0) {
				--bufpos;
				Backup(0);
			}
			else {
				bufcolumn[bufpos] = column;
				bufline[bufpos] = line;
			}
			throw e;
		}
	}
	
	
	protected void AdjustBuffSize() {
		if(available == bufsize) {
			if(tokenBegin > 2048) {
				bufpos = 0;
				available = tokenBegin;
			}
			else {
				ExpandBuff(false);
			}
		}
		else if(available > tokenBegin) {
			available = bufsize;
		}
		else if((tokenBegin - available) < 2048) {
			ExpandBuff(true);
		}
		else {
			available = tokenBegin;
		}
	}
	
	
	protected void AdjustBeginLineColumn(int newLine, int newColumn) {
		int start = tokenBegin;
		int len;
		int i = 0, j = 0, k = 0;
		int nextColumnDiff = 0;
		int columnDiff = 0;
		
		if(bufpos >= tokenBegin) 
			len = bufpos - tokenBegin + inBuf + 1;
		else
			len = bufsize - tokenBegin + bufpos + inBuf + 1;
		
		while(i < len && bufline[j = start % bufsize] == bufline[k = ++start % bufsize]) {
			bufline[j] = newLine;
			nextColumnDiff = columnDiff + bufcolumn[k] - bufcolumn[j];
			bufcolumn[j] = newColumn + columnDiff;
			columnDiff = nextColumnDiff;
			i++;
		}
		
		if(i < len) {
			bufline[j] = newLine++;
			bufcolumn[j] = newColumn + columnDiff;
			
			while(i++ < len) {
				if(bufline[j = start % bufsize] != bufline[++start % bufsize])
					bufline[j] = newLine++;
				else
					bufline[j] = newLine;
			}
		}
		line = bufline[j];
		column = bufcolumn[j];
	}
	
	
	protected void UpdateLineColumn(char ch) {
		column++;
		if(prevCharIsLF) {
			prevCharIsLF = false;
			line += (column = 1);
		}
		else if(prevCharIsCR) {
			prevCharIsCR = false;
			if(ch == '\n')
				prevCharIsLF = true;
			else
				line += (column = 1);
		}
		
		switch(ch) {
		
		case '\r':
			prevCharIsCR = true;
			break;
			
		case '\n':
			prevCharIsLF = true;
			break;
			
		case '\t':
			column--;
			column += (tabSize - (column % tabSize));
			break;
		}
		bufline[bufpos] = line;
		bufcolumn[bufpos] = column;
	}
	
	
	protected char readByte() throws java.io.IOException {
		if(++nextChar >= maxNextChar)
			FillBuff();
		
		return nextCharBuf[nextChar];
	}
	
	
	public char readChar() throws java.io.IOException {
		char ch;
		int backSlash = 1;
		
		if(inBuf > 0) {
			--inBuf;
			if(++bufpos == bufsize)
				bufpos = 0;
			tokenBegin = bufpos;
			return buffer[bufpos];
		}
		
		if(++bufpos == available)
			AdjustBuffSize();
		
		if((buffer[bufpos] = ch = readByte()) == '\\') {
			UpdateLineColumn(ch);
			
			while(true) {
				if(++bufpos == available)
					AdjustBuffSize();
				
				try {
					if((buffer[bufpos] = ch = readByte()) != '\\') {
						if((ch == 'u') && ((backSlash & 1) == 1)) {
							if(--bufpos < 0)
								bufpos = bufsize-1;
							break;
						}
						Backup(backSlash);
						return '\\';
					}
				}
				catch(java.io.IOException e) {
					if(backSlash > 1)
						Backup(backSlash);
					return '\\';
				}
				UpdateLineColumn(ch);
				backSlash++;
			}
			
			try {
				while((ch = readByte()) == 'u')
					++column;
				
				buffer[bufpos] = ch = (char)(Hexval(ch) << 12 | 
											 Hexval(readByte()) << 8 |
											 Hexval(readByte()) << 4 |
											 Hexval(readByte()));
				column += 4;
			}
			catch(java.io.IOException e) {
				throw new Error("Invalid escape character at line " + line + " column " + column +".");
			}
			
			if(backSlash == 1) {
				return ch;
			}
			else {
				Backup(backSlash - 1);
				return '\\';
			}
		}
		else {
			UpdateLineColumn(ch);
			return ch;
		}
	}
	
	
	public char BeginToken() throws java.io.IOException {
		if(inBuf > 0) {
			--inBuf;
			if(++bufpos == bufsize)
				bufpos = 0;
			tokenBegin = bufpos;
			return buffer[bufpos];
		}
		tokenBegin = 0;
		bufpos = -1;
		
		return readChar();
	}
}
