package avis.Parser;

public class Token {
	
	public int kind;
	public int beginLine, beginColumn;
	public int endLine, endColumn;
	public String image;
	public Token next;
	public Token specialToken;
	
	public String toString() {
		return image;
	}
	
	public static final Token newToken(int ofKind) {
		switch(ofKind) {
		
		case ParserConstants.RUNSIGNEDSHIFT:
		case ParserConstants.RSIGNEDSHIFT:
		case ParserConstants.GT:
			return new GTToken();
			
		default:
			return new Token();	
		}
	}
	
	public static class GTToken extends Token {
		int realKind = ParserConstants.GT;
	}
}
