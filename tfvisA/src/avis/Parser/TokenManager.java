package avis.Parser;

public class TokenManager implements ParserConstants{
	
	public java.io.PrintStream debugStream = System.out;
	
	protected CharStream input_stream;
	private final int[] jjrounds = new int[52];
	private final int[] jjstateSet = new int[104];
	StringBuffer image;
	int jjimageLen;
	int lengthOfMatch;
	protected char curChar;
	
	static final long[] jjbitVec0 = {0xfffffffffffffffeL, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL};
	static final long[] jjbitVec2 = {0x0L, 0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL};
	static final long[] jjbitVec3 = {0x1ff00000fffffffeL, 0xffffffffffffc000L, 0xffffffffL, 0x600000000000000L};
	static final long[] jjbitVec4 = {0x0L, 0x0L, 0x0L, 0xff7fffffff7fffffL};
	static final long[] jjbitVec5 = {0x0L, 0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffffffffffffffL};
	static final long[] jjbitVec6 = {0xffffffffffffffffL, 0xffffffffffffffffL, 0xffffL, 0x0L};
	static final long[] jjbitVec7 = {0xffffffffffffffffL, 0xffffffffffffffffL, 0x0L, 0x0L};
	static final long[] jjbitVec8 = {0x3fffffffffffL, 0x0L, 0x0L, 0x0L};
	static final long[] jjtoToken = {0xffffffffffffe001L, 0x1fffffffffffe747L,};	
	static final long[] jjtoSkip = {0xe3eL, 0x0L,};
	static final long[] jjtoSpecial = {0xe00L, 0x0L,};
	static final long[] jjtoMore = {0x11c0L, 0x0L,};
	
	static final int[] jjnextStates = {
		 34, 35, 40, 41, 44, 45, 12, 23, 24, 26, 14, 16, 49, 51, 6, 8,
		 9, 12, 23, 24, 28, 26, 36, 37, 12, 44, 45, 12, 10, 11, 17, 18,
		 20, 25, 27, 29, 38, 39, 42, 43, 46, 47,
	};
	
	public static final String[] jjstrLiteralImages = {
		"", null, null, null, null, null, null, null, null, null, null, null, null,
		"\141\142\163\164\162\141\143\164", "\141\163\163\145\162\164", "\142\157\157\154\145\141\156",
		"\142\162\145\141\153", "\142\171\164\145", "\143\141\163\145", "\143\141\164\143\150",
		"\143\150\141\162", "\143\154\141\163\163", "\143\157\156\163\164",
		"\143\157\156\164\151\156\165\145", "\144\145\146\141\165\154\164", "\144\157", "\144\157\165\142\154\145",
		"\145\154\163\145", "\145\156\165\155", "\145\170\164\145\156\144\163", "\146\141\154\163\145",
		"\146\151\156\141\154", "\146\151\156\141\154\154\171", "\146\154\157\141\164", "\146\157\162",
		"\147\157\164\157", "\151\146", "\151\155\160\154\145\155\145\156\164\163",
		"\151\155\160\157\162\164", "\151\156\163\164\141\156\143\145\157\146", "\151\156\164",
		"\151\156\164\145\162\146\141\143\145", "\154\157\156\147", "\156\141\164\151\166\145", "\156\145\167",
		"\156\165\154\154", "\160\141\143\153\141\147\145", "\160\162\151\166\141\164\145",
		"\160\162\157\164\145\143\164\145\144", "\160\165\142\154\151\143", "\162\145\164\165\162\156",
		"\163\150\157\162\164", "\163\164\141\164\151\143", "\163\164\162\151\143\164\146\160",
		"\163\165\160\145\162", "\163\167\151\164\143\150",
		"\163\171\156\143\150\162\157\156\151\172\145\144", "\164\150\151\163", "\164\150\162\157\167", "\164\150\162\157\167\163",
		"\164\162\141\156\163\151\145\156\164", "\164\162\165\145", "\164\162\171", "\166\157\151\144",
		"\166\157\154\141\164\151\154\145", "\167\150\151\154\145", null, null, null, null, null, null, null, null, null,
		null, null, "\50", "\51", "\173", "\175", "\133", "\135", "\73", "\54", "\56",
		"\100", "\75", "\74", "\41", "\176", "\77", "\72", "\75\75", "\74\75", "\76\75",
		"\41\75", "\174\174", "\46\46", "\53\53", "\55\55", "\53", "\55", "\52", "\57", "\46",
		"\174", "\136", "\45", "\74\74", "\53\75", "\55\75", "\52\75", "\57\75", "\46\75",
		"\174\75", "\136\75", "\45\75", "\74\74\75", "\76\76\75", "\76\76\76\75", "\56\56\56",
		"\76\76\76", "\76\76", "\76", 
	};
		
	public static final String[] lexStateNames = {
		"DEFAULT",
		"IN_SINGLE_LINE_COMMENT",
		 "IN_FORMAL_COMMENT",
		 "IN_MULTI_LINE_COMMENT",
	};
	
	public static final int[] jjnewLexState = {
		 -1, -1, -1, -1, -1, -1, 1, 2, 3, 0, 0, 0, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
		 -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1, -1,
	};
	
	int curLexState = 0;
	int defaultLexState = 0;
	int jjnewStateCnt;
	int jjround;
	int jjmatchedPos;
	int jjmatchedKind;
	
	public TokenManager(CharStream stream) {
		if(CharStream.staticFlag)
			throw new Error("ERROR: Cannot use a static CharStream class with a non-static lexical analyzer.");
		input_stream = stream;
	}
	
	
	public TokenManager(CharStream stream, int lexState) {
		this(stream);
		SwitchTo(lexState);
	}
	
	
	public void ReInit(CharStream stream) {
		jjmatchedPos = jjnewStateCnt = 0;
		curLexState = defaultLexState;
		input_stream = stream;
		ReInitRounds();
	}
	
	
	private final void ReInitRounds() {
		int i;
		jjround = 0x80000001;
		for (i = 52; i-- > 0;)
			jjrounds[i] = 0x80000000;
	}
	
	
	public void ReInit(CharStream stream, int lexState) {
		ReInit(stream);
		SwitchTo(lexState);
	}
	
	
	public void setDebugStream(java.io.PrintStream dstream) {
		debugStream = dstream;
	}
	
	
	private final int jjStopStringLiteralDfa_0(int pos, long active0, long active1) {
		switch(pos) {
		
		case 0:
			if((active0 & 0x140L) != 0L || (active1 & 0x2010000000000L) != 0L)
				return 2;
			if((active1 & 0x200000000200000L) != 0L)
				return 8;
			if((active0 & 0xffffffffffffe000L) != 0L || (active1 & 0x3L) != 0L) {
				jjmatchedKind = 74;
				return 32;
			}
			return -1;
		
		case 1:
			if((active0 & 0x100L) != 0L)
				return 0;
			if((active0 & 0xffffffeff9ffe000L) != 0L || (active1 & 0x3L) != 0L) {
				if(jjmatchedPos != 1) {
					jjmatchedKind = 74;
					jjmatchedPos = 1;
				}
				return 32;
			}
			if((active0 & 0x1006000000L) != 0L)
				return 32;
			return -1;
		
		case 2:
			if((active0 & 0xbfffecebfdffe000L) != 0L || (active1 & 0x3L) != 0L) {
				if(jjmatchedPos != 2) {
					jjmatchedKind = 74;
					jjmatchedPos = 2;
				}
				return 32;
			}
			if((active0 & 0x4000130400000000L) != 0L)
				return 32;
			return -1;
		
		case 3:
			if((active0 & 0x1dffcae3e5e9e000L) != 0L || (active1 & 0x3L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 3;
				return 32;
			}
			if((active0 & 0xa200240818160000L) != 0L)
				return 32;
			return -1;
		
		case 4:
			if((active0 & 0x11b7cae02580e000L) != 0L || (active1 & 0x1L) != 0L) {
				if(jjmatchedPos != 4) {
					jjmatchedKind = 74;
					jjmatchedPos = 4;
				}
				return 32;
			}
			if((active0 & 0xc480003c0690000L) != 0L || (active1 & 0x2L) != 0L)
				return 32;
			return -1;
		
		case 5:
			if((active0 & 0x1121c2a12180a000L) != 0L || (active1 & 0x1L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 5;
				return 32;
			}
			if((active0 & 0x896084004004000L) != 0L)
				return 32;
			return -1;
		
		case 6:
			if((active0 & 0x112102a000802000L) != 0L || (active1 & 0x1L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 6;
				return 32;
			}
			if((active0 & 0xc00121008000L) != 0L)
				return 32;
			return -1;
		
		case 7:
			if((active0 & 0x110102a000000000L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 7;
				return 32;
			}
			if((active0 & 0x20000000802000L) != 0L || (active1 & 0x1L) != 0L)
				return 32;
			return -1;
		
		case 8:
			if((active0 & 0x10000a000000000L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 8;
				return 32;
			}
			if((active0 & 0x1001020000000000L) != 0L)
				return 32;
			return -1;
		
		case 9:
			if((active0 & 0x100000000000000L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 9;
				return 32;
			}
			if((active0 & 0xa000000000L) != 0L)
				return 32;
			return -1;
		
		case 10:
			if((active0 & 0x100000000000000L) != 0L) {
				jjmatchedKind = 74;
				jjmatchedPos = 10;
				return 32;
			}
			return -1;
			
		default :
			return -1;
		}
	}

	private final int jjStartNfa_0(int pos, long active0, long active1) {
		return jjMoveNfa_0(jjStopStringLiteralDfa_0(pos, active0, active1), pos + 1);
	}

	private final int jjStopAtPos(int pos, int kind) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		return pos + 1;
	}

	private final int jjStartNfaWithStates_0(int pos, int kind, int state) {
		jjmatchedKind = kind;
		jjmatchedPos = pos;
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			return pos + 1;
		}
		return jjMoveNfa_0(state, pos + 1);
	}

	private final int jjMoveStringLiteralDfa0_0() {
		switch(curChar) {
		
		case 33:
			jjmatchedKind = 89;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x100000000L);
			
		case 37:
			jjmatchedKind = 108;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x20000000000000L);
			
		case 38:
			jjmatchedKind = 105;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x4000400000000L);
			
		case 40:
			return jjStopAtPos(0, 77);
			
		case 41:
			return jjStopAtPos(0, 78);
			
		case 42:
			jjmatchedKind = 103;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x1000000000000L);
			
		case 43:
			jjmatchedKind = 101;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x400800000000L);
			
		case 44:
			return jjStopAtPos(0, 84);
			
		case 45:
			jjmatchedKind = 102;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x801000000000L);
			
		case 46:
			jjmatchedKind = 85;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x200000000000000L);
			
		case 47:
			jjmatchedKind = 104;
			return jjMoveStringLiteralDfa1_0(0x140L, 0x2000000000000L);
			
		case 58:
			return jjStopAtPos(0, 92);
			
		case 59:
			return jjStopAtPos(0, 83);
			
		case 60:
			jjmatchedKind = 88;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x40200040000000L);
			
		case 61:
			jjmatchedKind = 87;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x20000000L);
			
		case 62:
			jjmatchedKind = 124;
			return jjMoveStringLiteralDfa1_0(0x0L, 0xd80000080000000L);
			
		case 63:
			return jjStopAtPos(0, 91);
			
		case 64:
			return jjStopAtPos(0, 86);
			
		case 91:
			return jjStopAtPos(0, 81);
			
		case 93:
			return jjStopAtPos(0, 82);
			
		case 94:
			jjmatchedKind = 107;
			return jjMoveStringLiteralDfa1_0(0x0L, 0x10000000000000L);
			
		case 97:
			return jjMoveStringLiteralDfa1_0(0x6000L, 0x0L);
			
	    case 98:
	       return jjMoveStringLiteralDfa1_0(0x38000L, 0x0L);
	       
	    case 99:
	    	return jjMoveStringLiteralDfa1_0(0xfc0000L, 0x0L);
	    	
	    case 100:
	       return jjMoveStringLiteralDfa1_0(0x7000000L, 0x0L);
	       
	    case 101:
	       return jjMoveStringLiteralDfa1_0(0x38000000L, 0x0L);
	       
	    case 102:
	       return jjMoveStringLiteralDfa1_0(0x7c0000000L, 0x0L);
	       
	    case 103:
	       return jjMoveStringLiteralDfa1_0(0x800000000L, 0x0L);
	       
	    case 105:
	       return jjMoveStringLiteralDfa1_0(0x3f000000000L, 0x0L);
	       
	    case 108:
	       return jjMoveStringLiteralDfa1_0(0x40000000000L, 0x0L);
	       
	    case 110:
	       return jjMoveStringLiteralDfa1_0(0x380000000000L, 0x0L);
	       
	    case 112:
	       return jjMoveStringLiteralDfa1_0(0x3c00000000000L, 0x0L);
	       
	    case 114:
	       return jjMoveStringLiteralDfa1_0(0x4000000000000L, 0x0L);
	       
	    case 115:
	       return jjMoveStringLiteralDfa1_0(0x1f8000000000000L, 0x0L);
	       
	    case 116:
	       return jjMoveStringLiteralDfa1_0(0x7e00000000000000L, 0x0L);
	       
	    case 118:
	       return jjMoveStringLiteralDfa1_0(0x8000000000000000L, 0x1L);
	       
	    case 119:
	       return jjMoveStringLiteralDfa1_0(0x0L, 0x2L);
	       
	    case 123:
	       return jjStopAtPos(0, 79);
	       
	    case 124:
	       jjmatchedKind = 106;
	       return jjMoveStringLiteralDfa1_0(0x0L, 0x8000200000000L);
	       
	    case 125:
	       return jjStopAtPos(0, 80);
	       
	    case 126:
	       return jjStopAtPos(0, 90);
	       
	    default :
	       return jjMoveNfa_0(3, 0);
		}
	}

	private final int jjMoveStringLiteralDfa1_0(long active0, long active1)	{
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(0, active0, active1);
			return 1;
		}
		switch(curChar) {
		
		case 38:
			if((active1 & 0x400000000L) != 0L)
				return jjStopAtPos(1, 98);
			break;
			
	    case 42:
	    	if((active0 & 0x100L) != 0L)
	    	   return jjStartNfaWithStates_0(1, 8, 0);
	    	break;
	    	
	    case 43:
	    	if((active1 & 0x800000000L) != 0L)
	    		return jjStopAtPos(1, 99);
	    	break;
	    	
	    case 45:
	    	if((active1 & 0x1000000000L) != 0L)
	    		return jjStopAtPos(1, 100);
	    	break;
	    	
	    case 46:
	    	return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x200000000000000L);
	    	
	    case 47:
	    	if((active0 & 0x40L) != 0L)
	    		return jjStopAtPos(1, 6);
	    	break;
	    	
	    case 60:
	    	if((active1 & 0x200000000000L) != 0L) {
	    		jjmatchedKind = 109;
	    		jjmatchedPos = 1;
	    	}
	    	return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x40000000000000L);
	    	
	    case 61:
	    	if((active1 & 0x20000000L) != 0L)
	    		return jjStopAtPos(1, 93);
	    	else if((active1 & 0x40000000L) != 0L)
	    		return jjStopAtPos(1, 94);
	    	else if((active1 & 0x80000000L) != 0L)
	    		return jjStopAtPos(1, 95);
	    	else if((active1 & 0x100000000L) != 0L)
	    		return jjStopAtPos(1, 96);
	    	else if((active1 & 0x400000000000L) != 0L)
	    		return jjStopAtPos(1, 110);
	    	else if((active1 & 0x800000000000L) != 0L)
	    		return jjStopAtPos(1, 111);
	    	else if((active1 & 0x1000000000000L) != 0L)
	    		return jjStopAtPos(1, 112);
	    	else if((active1 & 0x2000000000000L) != 0L)
	    		return jjStopAtPos(1, 113);
	    	else if((active1 & 0x4000000000000L) != 0L)
	    		return jjStopAtPos(1, 114);
	    	else if((active1 & 0x8000000000000L) != 0L)
	    		return jjStopAtPos(1, 115);
	    	else if((active1 & 0x10000000000000L) != 0L)
	    		return jjStopAtPos(1, 116);
	    	else if((active1 & 0x20000000000000L) != 0L)
	    		return jjStopAtPos(1, 117);
	    	break;
	    	
	    case 62:
	    	if((active1 & 0x800000000000000L) != 0L) {
	    		jjmatchedKind = 123;
	    		jjmatchedPos = 1;
	    	}
	    	return jjMoveStringLiteralDfa2_0(active0, 0L, active1, 0x580000000000000L);
	    	
	    case 97:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x4800400c0000L, active1, 0L);
	    	
	    case 98:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x2000L, active1, 0L);
	    	
	    case 101:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x4100001000000L, active1, 0L);
	    	
	    case 102:
	    	if((active0 & 0x1000000000L) != 0L)
	    		return jjStartNfaWithStates_0(1, 36, 32);
	    	break;
	    	
	    case 104:
	    	return jjMoveStringLiteralDfa2_0(active0, 0xe08000000100000L, active1, 0x2L);
	    	
	    case 105:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x180000000L, active1, 0L);
	    	
	    case 108:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x208200000L, active1, 0L);
	    	
	    case 109:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x6000000000L, active1, 0L);
	    	
	    case 110:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x38010000000L, active1, 0L);
	    	
	    case 111:
	    	if((active0 & 0x2000000L) != 0L) {
	    		jjmatchedKind = 25;
	    		jjmatchedPos = 1;
	    	}
	    	return jjMoveStringLiteralDfa2_0(active0, 0x8000040c04c08000L, active1, 0x1L);
	    	
	    case 114:
	       return jjMoveStringLiteralDfa2_0(active0, 0x7001800000010000L, active1, 0L);
	       
	    case 115:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x4000L, active1, 0L);
	    	
	    case 116:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x30000000000000L, active1, 0L);
	    	
	    case 117:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x42200000000000L, active1, 0L);
	    	
	    case 119:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x80000000000000L, active1, 0L);
	    	
	    case 120:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x20000000L, active1, 0L);
	    	
	    case 121:
	    	return jjMoveStringLiteralDfa2_0(active0, 0x100000000020000L, active1, 0L);
	    	
	    case 124:
	    	if((active1 & 0x200000000L) != 0L)
	    		return jjStopAtPos(1, 97);
	    	break;
	    	
	    default :
	    	break;
		}
		return jjStartNfa_0(0, active0, active1);
	}

	private final int jjMoveStringLiteralDfa2_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(0, old0, old1);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(1, active0, active1);
			return 2;
		}
		switch(curChar) {
		
		case 46:
			if((active1 & 0x200000000000000L) != 0L)
				return jjStopAtPos(2, 121);
			break;
			
		case 61:
			if((active1 & 0x40000000000000L) != 0L)
				return jjStopAtPos(2, 118);
			else if((active1 & 0x80000000000000L) != 0L)
				return jjStopAtPos(2, 119);
			break;
			
		case 62:
			if((active1 & 0x400000000000000L) != 0L) {
				jjmatchedKind = 122;
				jjmatchedPos = 2;
			}
			return jjMoveStringLiteralDfa3_0(active0, 0L, active1, 0x100000000000000L);
			
		case 97:
			return jjMoveStringLiteralDfa3_0(active0, 0x1010000000300000L, active1, 0L);
			
		case 98:
			return jjMoveStringLiteralDfa3_0(active0, 0x2000000000000L, active1, 0L);
			
		case 99:
			return jjMoveStringLiteralDfa3_0(active0, 0x400000000000L, active1, 0L);
			
		case 101:
			return jjMoveStringLiteralDfa3_0(active0, 0x10000L, active1, 0L);
			
		case 102:
 	      return jjMoveStringLiteralDfa3_0(active0, 0x1000000L, active1, 0L);
 	      
		case 105:
			return jjMoveStringLiteralDfa3_0(active0, 0x8280800000000000L, active1, 0x2L);
			
		case 108:
			return jjMoveStringLiteralDfa3_0(active0, 0x200040000000L, active1, 0x1L);
			
		case 110:
			return jjMoveStringLiteralDfa3_0(active0, 0x100040180c00000L, active1, 0L);
			
		case 111:
			return jjMoveStringLiteralDfa3_0(active0, 0x9000200008000L, active1, 0L);
			
		case 112:
			return jjMoveStringLiteralDfa3_0(active0, 0x40006000000000L, active1, 0L);
			
		case 114:
			if((active0 & 0x400000000L) != 0L)
				return jjStartNfaWithStates_0(2, 34, 32);
			return jjMoveStringLiteralDfa3_0(active0, 0xc20000000000000L, active1, 0L);
			
		case 115:
			return jjMoveStringLiteralDfa3_0(active0, 0x8008046000L, active1, 0L);
			
		case 116:
			if((active0 & 0x10000000000L) != 0L) {
				jjmatchedKind = 40;
				jjmatchedPos = 2;
			}
			return jjMoveStringLiteralDfa3_0(active0, 0x40a08200a0000L, active1, 0L);
			
		case 117:
			return jjMoveStringLiteralDfa3_0(active0, 0x2000000014000000L, active1, 0L);
			
		case 119:
			if((active0 & 0x100000000000L) != 0L)
				return jjStartNfaWithStates_0(2, 44, 32);
			break;
			
		case 121:
			if((active0 & 0x4000000000000000L) != 0L)
				return jjStartNfaWithStates_0(2, 62, 32);
			break;
			
		default :
			break;
		}
		return jjStartNfa_0(1, active0, active1);
	}

	private final int jjMoveStringLiteralDfa3_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(1, old0, old1);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(2, active0, active1);
			return 3;
		}
		switch(curChar) {
		
		case 61:
			if((active1 & 0x100000000000000L) != 0L)
				return jjStopAtPos(3, 120);
			break;
			
		case 97:
			return jjMoveStringLiteralDfa4_0(active0, 0x381010000L, active1, 0x1L);
			
		case 98:
			return jjMoveStringLiteralDfa4_0(active0, 0x4000000L, active1, 0L);
			
		case 99:
			return jjMoveStringLiteralDfa4_0(active0, 0x100000000080000L, active1, 0L);
			
		case 100:
			if((active0 & 0x8000000000000000L) != 0L)
				return jjStartNfaWithStates_0(3, 63, 32);
			break;
			
		case 101:
			if((active0 & 0x20000L) != 0L)
				return jjStartNfaWithStates_0(3, 17, 32);
			else if((active0 & 0x40000L) != 0L)
				return jjStartNfaWithStates_0(3, 18, 32);
			else if((active0 & 0x8000000L) != 0L)
				return jjStartNfaWithStates_0(3, 27, 32);
			else if((active0 & 0x2000000000000000L) != 0L)
				return jjStartNfaWithStates_0(3, 61, 32);
			return jjMoveStringLiteralDfa4_0(active0, 0x40020020004000L, active1, 0L);
			
		case 103:
			if((active0 & 0x40000000000L) != 0L)
				return jjStartNfaWithStates_0(3, 42, 32);
			break;
			
		case 105:
			return jjMoveStringLiteralDfa4_0(active0, 0x20080000000000L, active1, 0L);
			
		case 107:
			return jjMoveStringLiteralDfa4_0(active0, 0x400000000000L, active1, 0L);
			
		case 108:
			if((active0 & 0x200000000000L) != 0L)
				return jjStartNfaWithStates_0(3, 45, 32);
			return jjMoveStringLiteralDfa4_0(active0, 0x2002000008000L, active1, 0x2L);
			
		case 109:
			if((active0 & 0x10000000L) != 0L)
				return jjStartNfaWithStates_0(3, 28, 32);
			break;
			
		case 110:
			return jjMoveStringLiteralDfa4_0(active0, 0x1000000000000000L, active1, 0L);
			
		case 111:
			if((active0 & 0x800000000L) != 0L)
				return jjStartNfaWithStates_0(3, 35, 32);
			return jjMoveStringLiteralDfa4_0(active0, 0xc00004000000000L, active1, 0L);
			
		case 114:
			if((active0 & 0x100000L) != 0L)
				return jjStartNfaWithStates_0(3, 20, 32);
			return jjMoveStringLiteralDfa4_0(active0, 0x8000000000000L, active1, 0L);
			
		case 115:
			if((active0 & 0x200000000000000L) != 0L)
				return jjStartNfaWithStates_0(3, 57, 32);
			return jjMoveStringLiteralDfa4_0(active0, 0x40600000L, active1, 0L);
			
		case 116:
			return jjMoveStringLiteralDfa4_0(active0, 0x91008000802000L, active1, 0L);
			
		case 117:
			return jjMoveStringLiteralDfa4_0(active0, 0x4000000000000L, active1, 0L);
			
		case 118:
			return jjMoveStringLiteralDfa4_0(active0, 0x800000000000L, active1, 0L);
			
		default :
			break;
		}
		return jjStartNfa_0(2, active0, active1);
	}


	private final int jjMoveStringLiteralDfa4_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(2, old0, old1);
		try { 
			curChar = input_stream.readChar(); 
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(3, active0, active1);
			return 4;
		}
		
		switch(curChar) {
		
		case 97:
			return jjMoveStringLiteralDfa5_0(active0, 0xc08000000000L, active1, 0L);
			
		case 99:
			return jjMoveStringLiteralDfa5_0(active0, 0xa0000000000000L, active1, 0L);
			
		case 101:
			if((active0 & 0x40000000L) != 0L)
				return jjStartNfaWithStates_0(4, 30, 32);
			else if((active1 & 0x2L) != 0L)
				return jjStartNfaWithStates_0(4, 65, 32);
			return jjMoveStringLiteralDfa5_0(active0, 0x1002000008000L, active1, 0L);
			
		case 104:
			if((active0 & 0x80000L) != 0L)
				return jjStartNfaWithStates_0(4, 19, 32);
			return jjMoveStringLiteralDfa5_0(active0, 0x100000000000000L, active1, 0L);
			
		case 105:
			return jjMoveStringLiteralDfa5_0(active0, 0x12000000800000L, active1, 0L);
			
		case 107:
			if((active0 & 0x10000L) != 0L)
				return jjStartNfaWithStates_0(4, 16, 32);
			break;
			
		case 108:
			if((active0 & 0x80000000L) != 0L) {
				jjmatchedKind = 31;
				jjmatchedPos = 4;
			}
			return jjMoveStringLiteralDfa5_0(active0, 0x104000000L, active1, 0L);
			
		case 110:
			return jjMoveStringLiteralDfa5_0(active0, 0x20000000L, active1, 0L);
			
		case 114:
			if((active0 & 0x40000000000000L) != 0L)
				return jjStartNfaWithStates_0(4, 54, 32);
			return jjMoveStringLiteralDfa5_0(active0, 0x4024000006000L, active1, 0L);
			
		case 115:
			if((active0 & 0x200000L) != 0L)
				return jjStartNfaWithStates_0(4, 21, 32);
			return jjMoveStringLiteralDfa5_0(active0, 0x1000000000000000L, active1, 0L);
			
		case 116:
			if((active0 & 0x400000L) != 0L)
				return jjStartNfaWithStates_0(4, 22, 32);
			else if((active0 & 0x200000000L) != 0L)
				return jjStartNfaWithStates_0(4, 33, 32);
			else if((active0 & 0x8000000000000L) != 0L)
				return jjStartNfaWithStates_0(4, 51, 32);
			return jjMoveStringLiteralDfa5_0(active0, 0L, active1, 0x1L);
			
		case 117:
			return jjMoveStringLiteralDfa5_0(active0, 0x1000000L, active1, 0L);
			
		case 118:
			return jjMoveStringLiteralDfa5_0(active0, 0x80000000000L, active1, 0L);
			
		case 119:
			if((active0 & 0x400000000000000L) != 0L) {
				jjmatchedKind = 58;
				jjmatchedPos = 4;
			}
			return jjMoveStringLiteralDfa5_0(active0, 0x800000000000000L, active1, 0L);
			
		default :
			break;
		}
		return jjStartNfa_0(3, active0, active1);
	}
	
	private final int jjMoveStringLiteralDfa5_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(3, old0, old1);
		try { 
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(4, active0, active1);
			return 5;
		}
		
		switch(curChar) {
		
		case 97:
			return jjMoveStringLiteralDfa6_0(active0, 0xa000L, active1, 0L);
			
		case 99:
	    	if((active0 & 0x2000000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 49, 32);
	    	else if((active0 & 0x10000000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 52, 32);
	    	return jjMoveStringLiteralDfa6_0(active0, 0x1000000000000L, active1, 0L);
	    	
		case 100:
			return jjMoveStringLiteralDfa6_0(active0, 0x20000000L, active1, 0L);
			
		case 101:
			if((active0 & 0x4000000L) != 0L)
				return jjStartNfaWithStates_0(5, 26, 32);
			else if((active0 & 0x80000000000L) != 0L)
				return jjStartNfaWithStates_0(5, 43, 32);
			break;
			
		case 102:
			return jjMoveStringLiteralDfa6_0(active0, 0x20000000000L, active1, 0L);
			
	    case 103:	
	    	return jjMoveStringLiteralDfa6_0(active0, 0x400000000000L, active1, 0L);
	       
	    case 104:
	    	if((active0 & 0x80000000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 55, 32);
	    	break;
	    	
	    case 105:
	    	return jjMoveStringLiteralDfa6_0(active0, 0x1000000000000000L, active1, 0x1L);
	    	
	    case 108:
	    	return jjMoveStringLiteralDfa6_0(active0, 0x101000000L, active1, 0L);
	       
	    case 109:
	    	return jjMoveStringLiteralDfa6_0(active0, 0x2000000000L, active1, 0L);
	    	
	    case 110:
	    	if((active0 & 0x4000000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 50, 32);
	    	return jjMoveStringLiteralDfa6_0(active0, 0x8000800000L, active1, 0L);
	    	
	    case 114:
	    	return jjMoveStringLiteralDfa6_0(active0, 0x100000000000000L, active1, 0L);
	    	
	    case 115:
	    	if((active0 & 0x800000000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 59, 32);
	    	break;
	    	
	    case 116:
	    	if((active0 & 0x4000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 14, 32);
	    	else if((active0 & 0x4000000000L) != 0L)
	    		return jjStartNfaWithStates_0(5, 38, 32);
	    	return jjMoveStringLiteralDfa6_0(active0, 0x20800000000000L, active1, 0L);
	    	
	    default:
	    	break;
		}
		return jjStartNfa_0(4, active0, active1);
	}
	
	
	private final int jjMoveStringLiteralDfa6_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(4, old0, old1);
		try { 
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(5, active0, active1);
			return 6;
		}
		
		switch(curChar) {
		
		case 97:
	    	return jjMoveStringLiteralDfa7_0(active0, 0x20000000000L, active1, 0L);
	    	
	    case 99:
	    	return jjMoveStringLiteralDfa7_0(active0, 0x8000002000L, active1, 0L);
	    	
	    case 101:
	    	if((active0 & 0x400000000000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 46, 32);
	    	else if((active0 & 0x800000000000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 47, 32);
	    	return jjMoveStringLiteralDfa7_0(active0, 0x1000002000000000L, active1, 0L);
	    
	    case 102:
	    	return jjMoveStringLiteralDfa7_0(active0, 0x20000000000000L, active1, 0L);
	    
	    case 108:
	    	return jjMoveStringLiteralDfa7_0(active0, 0L, active1, 0x1L);
	    
	    case 110:
	    	if((active0 & 0x8000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 15, 32);
	    	break;
	    
	    case 111:
	    	return jjMoveStringLiteralDfa7_0(active0, 0x100000000000000L, active1, 0L);
	    	
	    case 115:
	    	if((active0 & 0x20000000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 29, 32);
	    	break;
	    
	    case 116:
	    	if((active0 & 0x1000000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 24, 32);
	    	return jjMoveStringLiteralDfa7_0(active0, 0x1000000000000L, active1, 0L);
	    
	    case 117:
	    	return jjMoveStringLiteralDfa7_0(active0, 0x800000L, active1, 0L);
	    
	    case 121:
	    	if((active0 & 0x100000000L) != 0L)
	    		return jjStartNfaWithStates_0(6, 32, 32);
	    	break;
	    	
	    default :
	    	break;
		}
		return jjStartNfa_0(5, active0, active1);
	}
	
	
	private final int jjMoveStringLiteralDfa7_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(5, old0, old1);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(6, active0, active1);
			return 7;
		}
		
		switch(curChar) {
	    
		case 99:
			return jjMoveStringLiteralDfa8_0(active0, 0x20000000000L, active1, 0L);
		
		case 101:
			if((active0 & 0x800000L) != 0L)
				return jjStartNfaWithStates_0(7, 23, 32);
			else if((active1 & 0x1L) != 0L)
				return jjStartNfaWithStates_0(7, 64, 32);
			return jjMoveStringLiteralDfa8_0(active0, 0x1008000000000L, active1, 0L);
		
		case 110:
			return jjMoveStringLiteralDfa8_0(active0, 0x1100002000000000L, active1, 0L);
		
		case 112:
			if((active0 & 0x20000000000000L) != 0L)
				return jjStartNfaWithStates_0(7, 53, 32);
			break;
		
		case 116:
	    	if((active0 & 0x2000L) != 0L)
	    		return jjStartNfaWithStates_0(7, 13, 32);
	    	break;
		
		default :
			break;
		}
		return jjStartNfa_0(6, active0, active1);
	}
	
	
	private final int jjMoveStringLiteralDfa8_0(long old0, long active0, long old1, long active1) {
		if(((active0 &= old0) | (active1 &= old1)) == 0L)
			return jjStartNfa_0(6, old0, old1);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(7, active0, 0L);
			return 8;
		}
		
		switch(curChar) {
	    
		case 100:
			if((active0 & 0x1000000000000L) != 0L)
				return jjStartNfaWithStates_0(8, 48, 32);
			break;
		
		case 101:
			if((active0 & 0x20000000000L) != 0L)
				return jjStartNfaWithStates_0(8, 41, 32);
			break;
		
		case 105:
			return jjMoveStringLiteralDfa9_0(active0, 0x100000000000000L);
		
		case 111:
			return jjMoveStringLiteralDfa9_0(active0, 0x8000000000L);
		
		case 116:
			if((active0 & 0x1000000000000000L) != 0L)
				return jjStartNfaWithStates_0(8, 60, 32);
			return jjMoveStringLiteralDfa9_0(active0, 0x2000000000L);
		
		default :
			break;
		}
		return jjStartNfa_0(7, active0, 0L);
	}
	
	
	private final int jjMoveStringLiteralDfa9_0(long old0, long active0) {
		if(((active0 &= old0)) == 0L)
			return jjStartNfa_0(7, old0, 0L);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(8, active0, 0L);
			return 9;
		}
		
		switch(curChar) {
	    
		case 102:
			if((active0 & 0x8000000000L) != 0L)
				return jjStartNfaWithStates_0(9, 39, 32);
			break;
		
		case 115:
			if((active0 & 0x2000000000L) != 0L)
				return jjStartNfaWithStates_0(9, 37, 32);
			break;
		
		case 122:
			return jjMoveStringLiteralDfa10_0(active0, 0x100000000000000L);
		
		default :
			break;
		}
		return jjStartNfa_0(8, active0, 0L);
	}
	
	
	private final int jjMoveStringLiteralDfa10_0(long old0, long active0) {
		if(((active0 &= old0)) == 0L)
			return jjStartNfa_0(8, old0, 0L);
		try {
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(9, active0, 0L);
			return 10;
		}
		
		switch(curChar) {
		
		case 101:
			return jjMoveStringLiteralDfa11_0(active0, 0x100000000000000L);
		
		default :
			break;
		}
		return jjStartNfa_0(9, active0, 0L);
	}
	
	
	private final int jjMoveStringLiteralDfa11_0(long old0, long active0) {
		if(((active0 &= old0)) == 0L)
			return jjStartNfa_0(9, old0, 0L);
		try { 
			curChar = input_stream.readChar();
		}
		catch(java.io.IOException e) {
			jjStopStringLiteralDfa_0(10, active0, 0L);
			return 11;
		}
		switch(curChar) {
			
		case 100:
			if((active0 & 0x100000000000000L) != 0L)
				return jjStartNfaWithStates_0(11, 56, 32);
			break;
		
		default :
			break;
		}
		return jjStartNfa_0(10, active0, 0L);
	}
	
	
	private final void jjCheckNAdd(int state) {
		if(jjrounds[state] != jjround) {
			jjstateSet[jjnewStateCnt++] = state;
			jjrounds[state] = jjround;
		}
	}
	
	
	private final void jjAddStates(int start, int end) {
		do {
			jjstateSet[jjnewStateCnt++] = jjnextStates[start];
		}
		while(start++ != end);
	}
	
	
	private final void jjCheckNAddTwoStates(int state1, int state2) {
		jjCheckNAdd(state1);
		jjCheckNAdd(state2);
	}
	
	
	private final void jjCheckNAddStates(int start, int end) {
		do {
			jjCheckNAdd(jjnextStates[start]);
		} 
		while(start++ != end);
	}
	
	
	private final int jjMoveNfa_0(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 52;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		while(true) {
			if(++jjround == 0x7fffffff)
				ReInitRounds();
			if(curChar < 64) {
				long l = 1L << curChar;
				do {
					switch(jjstateSet[--i]) {
					
					case 3:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(0, 6);
						else if(curChar == 36) {
							if(kind > 74)
								kind = 74;
							jjCheckNAdd(32);
						}
						else if(curChar == 34)
							jjCheckNAddStates(7, 9);
						else if(curChar == 39)
							jjAddStates(10, 11);
						else if(curChar == 46)
							jjCheckNAdd(8);
						else if(curChar == 47)
							jjstateSet[jjnewStateCnt++] = 2;
						if((0x3fe000000000000L & l) != 0L) {
							if(kind > 66)
								kind = 66;
							jjCheckNAddTwoStates(5, 6);
						}
						else if(curChar == 48) {
							if(kind > 66)
								kind = 66;
							jjCheckNAddStates(12, 14);
						}
						break;
					
					case 0:
						if(curChar == 42)
							jjstateSet[jjnewStateCnt++] = 1;
						break;
					
					case 1:
						if((0xffff7fffffffffffL & l) != 0L && kind > 7)
							kind = 7;
						break;
					
					case 2:
						if(curChar == 42)
							jjstateSet[jjnewStateCnt++] = 0;
						break;
					
					case 4:
						if((0x3fe000000000000L & l) == 0L)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddTwoStates(5, 6);
						break;
					
					case 5:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddTwoStates(5, 6);
						break;
					
					case 7:
						if(curChar == 46)
							jjCheckNAdd(8);
						break;
					
					case 8:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddStates(15, 17);
						break;
					
					case 10:
						if((0x280000000000L & l) != 0L)
							jjCheckNAdd(11);
						break;
					
					case 11:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddTwoStates(11, 12);
						break;
					
					case 13:
						if(curChar == 39)
							jjAddStates(10, 11);
						break;
					
					case 14:
						if((0xffffff7fffffdbffL & l) != 0L)
							jjCheckNAdd(15);
						break;
					
					case 15:
						if(curChar == 39 && kind > 72)
							kind = 72;
						break;
					
					case 17:
						if((0x8400000000L & l) != 0L)
							jjCheckNAdd(15);
						break;
					
					case 18:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(19, 15);
						break;
					
					case 19:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAdd(15);
						break;
					
					case 20:
						if((0xf000000000000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 21;
						break;
					
					case 21:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAdd(19);
						break;
					
					case 22:
						if(curChar == 34)
							jjCheckNAddStates(7, 9);
						break;
					
					case 23:
						if((0xfffffffbffffdbffL & l) != 0L)
							jjCheckNAddStates(7, 9);
						break;
					
					case 25:
						if((0x8400000000L & l) != 0L)
							jjCheckNAddStates(7, 9);
						break;
					
					case 26:
						if(curChar == 34 && kind > 73)
							kind = 73;
						break;
					
					case 27:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAddStates(18, 21);
						break;
					
					case 28:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAddStates(7, 9);
						break;
					
					case 29:
						if((0xf000000000000L & l) != 0L)
							jjstateSet[jjnewStateCnt++] = 30;
						break;
					
					case 30:
						if((0xff000000000000L & l) != 0L)
							jjCheckNAdd(28);
						break;
						
					case 31:
						if(curChar != 36)
							break;
						if(kind > 74)
							kind = 74;
						jjCheckNAdd(32);
						break;
					
					case 32:
						if((0x3ff001000000000L & l) == 0L)
							break;
						if(kind > 74)
							kind = 74;
						jjCheckNAdd(32);
						break;
					
					case 33:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(0, 6);
						break;
					
					case 34:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(34, 35);
						break;
					
					case 35:
						if(curChar != 46)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddStates(22, 24);
						break;
					
					case 36:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddStates(22, 24);
						break;
					
					case 38:
						if((0x280000000000L & l) != 0L)
							jjCheckNAdd(39);
						break;
					
					case 39:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddTwoStates(39, 12);
						break;
					
					case 40:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(40, 41);
						break;
					
					case 42:
						if((0x280000000000L & l) != 0L)
							jjCheckNAdd(43);
						break;
					
					case 43:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 70)
							kind = 70;
						jjCheckNAddTwoStates(43, 12);
						break;
					
					case 44:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddStates(25, 27);
						break;
					
					case 46:
						if((0x280000000000L & l) != 0L)
							jjCheckNAdd(47);
						break;
					
					case 47:
						if((0x3ff000000000000L & l) != 0L)
							jjCheckNAddTwoStates(47, 12);
						break;
					
					case 48:
						if(curChar != 48)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddStates(12, 14);
						break;
					
					case 50:
						if((0x3ff000000000000L & l) == 0L)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddTwoStates(50, 6);
						break;
					
					case 51:
						if((0xff000000000000L & l) == 0L)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddTwoStates(51, 6);
						break;
					}
				}
				while(i != startsAt);
			}
			else if(curChar < 128) {
				long l = 1L << (curChar & 077);
				do {
					switch(jjstateSet[--i]) {
					
					case 3:
					case 32:
						if((0x7fffffe87fffffeL & l) == 0L)
							break;
						if(kind > 74)
							kind = 74;
						jjCheckNAdd(32);
						break;
					
					case 1:
						if(kind > 7)
							kind = 7;
						break;
					
					case 6:
						if((0x100000001000L & l) != 0L && kind > 66)
							kind = 66;
						break;
					
					case 9:
						if((0x2000000020L & l) != 0L)
							jjAddStates(28, 29);
						break;
					
					case 12:
						if((0x5000000050L & l) != 0L && kind > 70)
							kind = 70;
						break;
					
					case 14:
						if((0xffffffffefffffffL & l) != 0L)
							jjCheckNAdd(15);
						break;
					
					case 16:
						if(curChar == 92)
							jjAddStates(30, 32);
						break;
					
					case 17:
						if((0x14404410000000L & l) != 0L)
							jjCheckNAdd(15);
						break;
					
					case 23:
						if((0xffffffffefffffffL & l) != 0L)
							jjCheckNAddStates(7, 9);
						break;
					
					case 24:
						if(curChar == 92)
							jjAddStates(33, 35);
						break;
					
					case 25:
						if((0x14404410000000L & l) != 0L)
							jjCheckNAddStates(7, 9);
						break;
					
					case 37:
						if((0x2000000020L & l) != 0L)
							jjAddStates(36, 37);
						break;

					case 41:
						if((0x2000000020L & l) != 0L)
							jjAddStates(38, 39);
						break;

					case 45:
						if((0x2000000020L & l) != 0L)
							jjAddStates(40, 41);
						break;

					case 49:
						if((0x100000001000000L & l) != 0L)
							jjCheckNAdd(50);
						break;

					case 50:
						if((0x7e0000007eL & l) == 0L)
							break;
						if(kind > 66)
							kind = 66;
						jjCheckNAddTwoStates(50, 6);
						break;
					}
				} 
				while(i != startsAt);
			}
			else {
				int hiByte = (int)(curChar >> 8);
				int i1 = hiByte >> 6;
				long l1 = 1L << (hiByte & 077);
				int i2 = (curChar & 0xff) >> 6;
				long l2 = 1L << (curChar & 077);
				do {
					switch(jjstateSet[--i]) {
					
					case 3:
					case 32:
						if(!jjCanMove_1(hiByte, i1, i2, l1, l2))
							break;
						if(kind > 74)
							kind = 74;
						jjCheckNAdd(32);
						break;
					
					case 1:
						if(jjCanMove_0(hiByte, i1, i2, l1, l2) && kind > 7)
							kind = 7;
						break;
					
					case 14:
						if(jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjstateSet[jjnewStateCnt++] = 15;
						break;
					
					case 23:
						if(jjCanMove_0(hiByte, i1, i2, l1, l2))
							jjAddStates(7, 9);
						break;
					}
				} 
				while(i != startsAt);
			}
			if(kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if((i = jjnewStateCnt) == (startsAt = 52 - (jjnewStateCnt = startsAt)))
				return curPos;
			try {
				curChar = input_stream.readChar(); 
			}
			catch(java.io.IOException e) { 
				return curPos;
			}
		}
	}
	
	
	private final int jjMoveStringLiteralDfa0_3() {
		switch(curChar) {
		
		case 42:
			return jjMoveStringLiteralDfa1_3(0x800L);
		
		default :
			return 1;
		}
	}
	
	
	private final int jjMoveStringLiteralDfa1_3(long active0) {
		try { 
			curChar = input_stream.readChar(); 
		}
		catch(java.io.IOException e) {
			return 1;
		}
		switch(curChar) {
		
		case 47:
			if((active0 & 0x800L) != 0L)
				return jjStopAtPos(1, 11);
			break;
		
		default :
			return 2;
		}
		return 2;
	}
	
	
	private final int jjMoveStringLiteralDfa0_1() {
		return jjMoveNfa_1(0, 0);
	}
	
	
	private final int jjMoveNfa_1(int startState, int curPos) {
		int startsAt = 0;
		jjnewStateCnt = 3;
		int i = 1;
		jjstateSet[0] = startState;
		int kind = 0x7fffffff;
		while(true) {
			if(++jjround == 0x7fffffff)
				ReInitRounds();
			if(curChar < 64) {
				long l = 1L << curChar;
				do {
					switch(jjstateSet[--i]) {
					
					case 0:
						if((0x2400L & l) != 0L) {
							if(kind > 9)
								kind = 9;
						}
						if(curChar == 13)
							jjstateSet[jjnewStateCnt++] = 1;
						break;
					
					case 1:
						if(curChar == 10 && kind > 9)
							kind = 9;
						break;
					
					case 2:
						if(curChar == 13)
							jjstateSet[jjnewStateCnt++] = 1;
						break;
					}
				} 
				while(i != startsAt);
			}
			
			if(kind != 0x7fffffff) {
				jjmatchedKind = kind;
				jjmatchedPos = curPos;
				kind = 0x7fffffff;
			}
			++curPos;
			if((i = jjnewStateCnt) == (startsAt = 3 - (jjnewStateCnt = startsAt)))
				return curPos;
			try { 
				curChar = input_stream.readChar(); 
			}
			catch(java.io.IOException e) { 
				return curPos; 
			}
		}
	}
	
	
	private final int jjMoveStringLiteralDfa0_2() {
		switch(curChar) {
		
		case 42:
			return jjMoveStringLiteralDfa1_2(0x400L);
		
		default :
			return 1;
		}
	}
	
	
	private final int jjMoveStringLiteralDfa1_2(long active0) {
		try { 
			curChar = input_stream.readChar(); 
		}
		catch(java.io.IOException e) {
			return 1;
		}
		switch(curChar) {
		
		case 47:
			if((active0 & 0x400L) != 0L)
				return jjStopAtPos(1, 10);
			break;
		
		default :
			return 2;
		}
		return 2;
	}

	
	private static final boolean jjCanMove_0(int hiByte, int i1, int i2, long l1, long l2) {
		switch(hiByte) {
		
		case 0:
			return ((jjbitVec2[i2] & l2) != 0L);
		
		default :
			if((jjbitVec0[i1] & l1) != 0L)
				return true;
			return false;
		}
	}
	
	
	private static final boolean jjCanMove_1(int hiByte, int i1, int i2, long l1, long l2) {
		switch(hiByte) {
		
		case 0:
			return ((jjbitVec4[i2] & l2) != 0L);
			
		case 48:
			return ((jjbitVec5[i2] & l2) != 0L);
			
		case 49:
			return ((jjbitVec6[i2] & l2) != 0L);
			
		case 51:
			return ((jjbitVec7[i2] & l2) != 0L);
			
		case 61:
			return ((jjbitVec8[i2] & l2) != 0L);
			
		default :
			if((jjbitVec3[i1] & l1) != 0L)
				return true;
			return false;
		}
	}
	
	
	public void SwitchTo(int lexState) {
		if(lexState >= 4 || lexState < 0)
			throw new TokenManagerError("Error: Ignoring invalid lexical state : " + lexState + ". State unchanged.", TokenManagerError.INVALID_LEXICAL_STATE);
		else
			curLexState = lexState;
	}
	
	
	protected Token jjFillToken() {
		Token t = Token.newToken(jjmatchedKind);
		t.kind = jjmatchedKind;
		String im = jjstrLiteralImages[jjmatchedKind];
		t.image = (im == null) ? input_stream.getImage() : im;
		t.beginLine = input_stream.getBeginLine();
		t.beginColumn = input_stream.getBeginColumn();
		t.endLine = input_stream.getEndLine();
		t.endColumn = input_stream.getEndColumn();
		return t;
	}


	public Token getNextToken() {
		Token specialToken = null;
		Token matchedToken;
		int curPos = 0;
		
		EOFLoop :
			while(true) {
				try {
					curChar = input_stream.BeginToken();
				}
				catch(java.io.IOException e) {
					jjmatchedKind = 0;
					matchedToken = jjFillToken();
					matchedToken.specialToken = specialToken;
					return matchedToken;
				}
				image = null;
				jjimageLen = 0;

				while(true) {
					switch(curLexState) {
					
					case 0:
						try { 
							input_stream.Backup(0);
							while(curChar <= 32 && (0x100003600L & (1L << curChar)) != 0L)
								curChar = input_stream.BeginToken();
						}
						catch (java.io.IOException e1) {
							continue EOFLoop;
						}
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_0();
						break;
					
					case 1:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_1();
						if(jjmatchedPos == 0 && jjmatchedKind > 12) {
							jjmatchedKind = 12;
						}
						break;
					
					case 2:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_2();
						if(jjmatchedPos == 0 && jjmatchedKind > 12) {
							jjmatchedKind = 12;
						}
						break;
					
					case 3:
						jjmatchedKind = 0x7fffffff;
						jjmatchedPos = 0;
						curPos = jjMoveStringLiteralDfa0_3();
						if(jjmatchedPos == 0 && jjmatchedKind > 12) {
							jjmatchedKind = 12;
						}
						break;
					}
					
					if(jjmatchedKind != 0x7fffffff) {
						if(jjmatchedPos + 1 < curPos)
							input_stream.Backup(curPos - jjmatchedPos - 1);
						if((jjtoToken[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
							matchedToken = jjFillToken();
							matchedToken.specialToken = specialToken;
							TokenLexicalActions(matchedToken);
							if(jjnewLexState[jjmatchedKind] != -1)
								curLexState = jjnewLexState[jjmatchedKind];
							return matchedToken;
						}
						else if((jjtoSkip[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
							if((jjtoSpecial[jjmatchedKind >> 6] & (1L << (jjmatchedKind & 077))) != 0L) {
								matchedToken = jjFillToken();
								if(specialToken == null)
									specialToken = matchedToken;
								else {
									matchedToken.specialToken = specialToken;
									specialToken = (specialToken.next = matchedToken);
								}
								SkipLexicalActions(matchedToken);
							}
							else
								SkipLexicalActions(null);
							if(jjnewLexState[jjmatchedKind] != -1)
								curLexState = jjnewLexState[jjmatchedKind];
							continue EOFLoop;
						}
						MoreLexicalActions();
						if(jjnewLexState[jjmatchedKind] != -1)
							curLexState = jjnewLexState[jjmatchedKind];
						curPos = 0;
						jjmatchedKind = 0x7fffffff;
						try {
							curChar = input_stream.readChar();
							continue;
						}
						catch (java.io.IOException e1) { }
					}
					int error_line = input_stream.getEndLine();
					int error_column = input_stream.getEndColumn();
					String error_after = null;
					boolean EOFSeen = false;
					try { 
						input_stream.readChar(); input_stream.Backup(1); 
					}
					catch (java.io.IOException e1) {
						EOFSeen = true;
						error_after = curPos <= 1 ? "" : input_stream.getImage();
						if(curChar == '\n' || curChar == '\r') {
							error_line++;
							error_column = 0;
						}
						else
							error_column++;
					}
					if(!EOFSeen) {
						input_stream.Backup(1);
						error_after = curPos <= 1 ? "" : input_stream.getImage();
					}
					throw new TokenManagerError(EOFSeen, curLexState, error_line, error_column, error_after, curChar, TokenManagerError.LEXICAL_ERROR);
				}
			}
	}

	
	void SkipLexicalActions(Token matchedToken) {
		switch(jjmatchedKind) {
		default :
			break;
		}
	}
	
	
	void MoreLexicalActions() {
		jjimageLen += (lengthOfMatch = jjmatchedPos + 1);
		switch(jjmatchedKind) {
		
		case 7 :
			if(image == null)
				image = new StringBuffer();
			image.append(input_stream.getSuffix(jjimageLen));
			jjimageLen = 0;
			input_stream.Backup(1);
			break;		
		}
	}
	
	
	void TokenLexicalActions(Token matchedToken) {
		switch(jjmatchedKind) {
		
		case 122 :
			if(image == null)
				image = new StringBuffer();
			image.append(jjstrLiteralImages[122]);
			matchedToken.kind = GT;
			((Token.GTToken)matchedToken).realKind = RUNSIGNEDSHIFT;
			input_stream.Backup(2);
			break;
		
		case 123 :
			if(image == null)
				image = new StringBuffer();
			image.append(jjstrLiteralImages[123]);
			matchedToken.kind = GT;
			((Token.GTToken)matchedToken).realKind = RSIGNEDSHIFT;
			input_stream.Backup(1);
			break;
		}
	}
}
