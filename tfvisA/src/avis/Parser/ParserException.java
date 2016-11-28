package avis.Parser;

public class ParserException extends Exception{
	private static final long serialVersionUID = 1L;
	
	protected boolean specialConstructor;
	protected String eol = System.getProperty("line.separator", "\n");
	public Token currentToken;
	public int[][] expectedToken;
	public String[] tokenImage;
	
	public ParserException() {
		super();
		specialConstructor = false;
	}
	
	
	public ParserException(Token currentTokenVal, int[][] expectedTokenVal, String[] tokenImageVal) {
		super("");
		specialConstructor = true;
		currentToken = currentTokenVal;
		expectedToken = expectedTokenVal;
		tokenImage = tokenImageVal;
	}
	
	
	public ParserException(String message) {
		super(message);
		specialConstructor = false;
	}
	
	
	public String getMessage() {
		String retval = "Encontered \"";
		Token tok = currentToken.next;
		StringBuffer expected = new StringBuffer();
		int maxSize = 0;
		
		if(!specialConstructor) 
			return super.getMessage();
		
		for(int i=0;i<expectedToken.length;i++) {
			if(maxSize < expectedToken[i].length)
				maxSize = expectedToken[i].length;
			for(int j=0;j < expectedToken[i].length;j++) 
				expected.append(tokenImage[expectedToken[i][j]]).append(" ");
			if(expectedToken[i][expectedToken[i].length-1] != 0) 
				expected.append("...");
			expected.append(eol).append("   ");
		}
		
		for(int i=0;i<maxSize;i++) {
			if(i != 0)
				retval += " ";
			if(tok.kind == 0) {
				retval += tokenImage[0];
				break;
			}
			retval += add_escapes(tok.image);
			tok = tok.next;
		}
		retval += "\" at line " + currentToken.next.beginLine + ", column " + currentToken.next.beginColumn + "." + eol;
		if(expectedToken.length == 1)
			retval += " was expecting : " + eol + "   ";
		else
			retval += " was expecting one of : " + eol + "   ";
		retval += expected.toString();
		return retval;
	}
	
	protected String add_escapes(String str) {
		StringBuffer retval = new StringBuffer();
		char ch;
		for(int i=0;i<str.length();i++) {
			switch(str.charAt(i)) {
			
			case 0:
				continue;
				
			case '\b':
				retval.append("\\b");
				continue;
				
			case '\t':
				retval.append("\\t");
				continue;
				
			case '\n':
				retval.append("\\n");
				continue;
				
			case '\f':
				retval.append("\\f");
				continue;
				
			case '\r':
				retval.append("\\r");
				continue;
				
			case '\"':
				retval.append("\\\"");
				continue;
				
			case '\'':
				retval.append("\\\'");
				continue;
				
			case '\\':
				retval.append("\\\\");
				continue;
				
			default:
				if((ch = str.charAt(i)) < 0x20 || ch > 0x7E) {
					String s = "0000" + Integer.toString(ch, 16);
					retval.append("\\u" + s.substring(s.length()-4, s.length()));
				}
				else {
					retval.append(ch);
				}
				continue;
			}
		}
		return retval.toString();
	}
}
