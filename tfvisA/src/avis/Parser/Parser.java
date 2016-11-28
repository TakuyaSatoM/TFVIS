package avis.Parser;

import java.io.*;

import tfvis.AnalysisData;

public class Parser implements ParserConstants{

	private static int cCount;
	private static int mCount;	
	
	private AnalysisData data;

	static public final class ModifierSet {
		public static final int PUBLIC = 0x0001;
		public static final int PROTECTED = 0x0002;
		public static final int PRIVATE = 0x0004;
		public static final int ABSTRACT = 0x0008;
		public static final int STATIC = 0x0010;
		public static final int FINAL = 0x0020;
		public static final int SYNCHRONIZED = 0x0040;
		public static final int NATIVE = 0x0080;
		public static final int TRANSIENT = 0x0100;
		public static final int VOLATILE = 0x0200;
		public static final int STRICTFP = 0x1000;

		public boolean isPublic(int modifiers) {
			return (modifiers & PUBLIC) != 0;
		}

		public boolean isProtected(int modifiers) {
			return (modifiers & PROTECTED) != 0;
		}

		public boolean isPrivate(int modifiers) {
			return (modifiers & PRIVATE) != 0;
		}

		public boolean isStatic(int modifiers) {
			return (modifiers & STATIC) != 0;
		}

		public boolean isAbstract(int modifiers) {
			return (modifiers & ABSTRACT) != 0;
		}

		public boolean isFinal(int modifiers) {
			return (modifiers & FINAL) != 0;
		}

		public boolean isNative(int modifiers) {
			return (modifiers & NATIVE) != 0;
		}

		public boolean isStrictfp(int modifiers) {
			return (modifiers & STRICTFP) != 0;
		}

		public boolean isSynchronized(int modifiers) {
			return (modifiers & SYNCHRONIZED) != 0;
			}

		public boolean isTransient(int modifiers) {
			return (modifiers & TRANSIENT) != 0;
		}

		public boolean isVolatile(int modifiers) {
			return (modifiers & VOLATILE) != 0;
		}

		static int removeModifier(int modifiers, int mod) {
			return modifiers & ~mod;
		}
	}
	
	public Parser(String filename) {
		cCount = 0;
		mCount = 0;

		Parser parser;
		try {
			if(filename == null) {
				//System.out.println("Reading from standard input ...");
				parser = new Parser(System.in);
			} else {
			//	System.out.println("Reading from file " + filename + " ...");
				try {
					parser = new Parser(new java.io.FileInputStream(filename));
				}
				catch(java.io.FileNotFoundException er) {
					System.out.println("File " + filename + " not found.");
					return;
				}
			}
		}
		catch(java.lang.NullPointerException er) {
			System.out.println("No input a java file.");
			System.out.println("System is exit...");
			System.exit(0);
			return;
		}
		
		try {
			data = parser.CompilationUnit();
			outAnalysisData();
		}
		catch (ParserException e) {
			System.out.println(e.getMessage());
			System.out.println("Java Parser Version 1.1:  Encountered errors during parse.");
		}
	}
	
	/*
	 * 解析データをファイルに出力する。
	 */
	public void outAnalysisData() {
		try {
			File dir = new File(DIRECTORY_CREATE);
			if(!dir.isDirectory())
				dir.mkdir();
			PrintWriter fout = new PrintWriter(new FileWriter(DIRECTORY+ANALYSIS_EXT));
			for(int i=0; i<data.size(); i++){
				fout.println(data.getId(i)+"#"+data.getState(i));
			}
			fout.close();
			fout = null;
		}
		catch (IOException e){
			System.out.println(e.getMessage());
		}
	}
	
	
	/*
	 * Getter
	 */
	public AnalysisData getAnalysisData() {
		return data;
	}
	
	public int getCCount() {	//	クラス数(クラス&インターフェイス)
		return cCount;
	}

	public int getMCount() {	//	メソッド数(メソッド&コンストラクタ)
		return mCount;
	}

	public int getCount() {		//	モジュール数(クラス数＋メソッド数)
		return cCount+mCount;
	}
		
	

	/*****************************************
	 * THE JAVA LANGUAGE GRAMMAR STARTS HERE *
	 *****************************************/

	/*
	 * Program structuring syntax
	 */
	final public AnalysisData CompilationUnit() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case PACKAGE:
			words.addAll(PackageDeclaration());
			break;
		default:
			;
		}
		label_1:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case IMPORT:
					break;
				default:
					break label_1;
				}
				words.addAll(ImportDeclaration());
			}
		label_2:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case ABSTRACT:
				case CLASS:
				case ENUM:
				case FINAL:
				case INTERFACE:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOLATILE:
				case SEMICOLON:
				case AT:
					break;
				default:
					break label_2;
				}
				words.addAll(TypeDeclaration());
			}
			jj_consume_token(0); 
			return words;
	}

	final public AnalysisData PackageDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(PACKAGE);
		words.add(Package, t.image);
		words.addAll(Name());
		t = jj_consume_token(SEMICOLON);
		words.add(Package+Semicolon, t.image);
		return words;
	}

	final public AnalysisData ImportDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IMPORT);
		words.add(Import, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case STATIC:
			t = jj_consume_token(STATIC);
			words.add(Import+Static, t.image);
			break;
		default:
			;
		}
		words.addAll(Name());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case DOT:
			t = jj_consume_token(DOT);
			words.add(Import+Dot, t.image);
			t = jj_consume_token(STAR);
			words.add(Import+Star, t.image);
			break;
		default:
			;
		}
		t = jj_consume_token(SEMICOLON);
		words.add(Import+Semicolon, t.image);
		return words;
	}

	/*
	 * Declaration syntax
	 */
	final public AnalysisData TypeDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		int modifiers = 0;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case SEMICOLON:
			t = jj_consume_token(SEMICOLON);
			words.add(TypeDec+Semicolon, t.image);
			break;
		case ABSTRACT:
		case CLASS:
		case ENUM:
		case FINAL:
		case INTERFACE:
		case NATIVE:
		case PRIVATE:
		case PROTECTED:
		case PUBLIC:
		case STATIC:
		case STRICTFP:
		case SYNCHRONIZED:
		case TRANSIENT:
		case VOLATILE:
		case AT:
			label_3:
				while (true) {
					if (!jj_2_1(2)) 
						break label_3;
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case PUBLIC:
						t = jj_consume_token(PUBLIC);
						words.add(TypeDec+Public, t.image);
						modifiers |= ModifierSet.PUBLIC;
						break;
					case STATIC:
						t = jj_consume_token(STATIC);
						words.add(TypeDec+Static, t.image);
						modifiers |= ModifierSet.STATIC;
						break;
					case PROTECTED:
						t = jj_consume_token(PROTECTED);
						words.add(TypeDec+Protected, t.image);
						modifiers |= ModifierSet.PROTECTED;
						break;
					case PRIVATE:
						t = jj_consume_token(PRIVATE);
						words.add(TypeDec+Private, t.image);
						modifiers |= ModifierSet.PRIVATE;
						break;
					case FINAL:
						t = jj_consume_token(FINAL);
						words.add(TypeDec+Final, t.image);
						modifiers |= ModifierSet.FINAL;
						break;
					case ABSTRACT:
						t = jj_consume_token(ABSTRACT);
						words.add(TypeDec+Abstract, t.image);
						modifiers |= ModifierSet.ABSTRACT;
						break;
					case SYNCHRONIZED:
						t = jj_consume_token(SYNCHRONIZED);
						words.add(TypeDec+Synchronized, t.image);
						modifiers |= ModifierSet.SYNCHRONIZED;
						break;
					case NATIVE:
						t = jj_consume_token(NATIVE);
						words.add(TypeDec+Native, t.image);
						modifiers |= ModifierSet.NATIVE;
						break;
					case TRANSIENT:
						t = jj_consume_token(TRANSIENT);
						words.add(TypeDec+Transient, t.image);
						modifiers |= ModifierSet.TRANSIENT;
						break;
					case VOLATILE:
						t = jj_consume_token(VOLATILE);
						words.add(TypeDec+Volatile, t.image);
						modifiers |= ModifierSet.VOLATILE;
						break;
					case STRICTFP:
						t = jj_consume_token(STRICTFP);
						words.add(TypeDec+Strictfp, t.image);
						modifiers |= ModifierSet.STRICTFP;
						break;
					case AT:
						words.addAll(Annotation());
						break;
					default:
						jj_consume_token(-1);
					throw new ParserException();
					}
				}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case CLASS:
		case INTERFACE:
			words.addAll(ClassOrInterfaceDeclaration(modifiers));
			break;
		case ENUM:
			words.addAll(EnumDeclaration(modifiers));
			break;
		case AT:
			words.addAll(AnnotationTypeDeclaration(modifiers));
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData ClassOrInterfaceDeclaration(int modifiers) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		boolean isInterface = false;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case CLASS:
			t = jj_consume_token(CLASS);
			words.add(CoiDec+Class, t.image);
			cCount++;
			break;
		case INTERFACE:
			t = jj_consume_token(INTERFACE);
			words.add(CoiDec+Interface, t.image);
			isInterface = true;
			cCount++;
			break;
		default:
			jj_consume_token(-1);
		throw new ParserException();
		}
		t = jj_consume_token(IDENTIFIER);
		if(isInterface)
			words.add(CoiDec+Interface+Identifier, t.image);
		else
			words.add(CoiDec+Class+Identifier, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LT:
			words.addAll(TypeParameters());
			break;
		default:
			;
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case EXTENDS:
			words.addAll(ExtendsList(isInterface));
			break;
		default:
			;
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case IMPLEMENTS:
			words.addAll(ImplementsList(isInterface));
			break;
		default:
			;
		}
		words.addAll(ClassOrInterfaceBody(isInterface));
		if(isInterface) 
			words.add(CoiDec+Interface+End, "end_interface");
		else
			words.add(CoiDec+Class+End, "end_class");
		return words;
	}

	final public AnalysisData ExtendsList(boolean isInterface) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		boolean extendsMoreThanOne = false;
		t = jj_consume_token(EXTENDS);
		words.add(ExtList+Extends, t.image);
		words.addAll(ClassOrInterfaceType());
		label_4:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_4;
				}
				t = jj_consume_token(COMMA);
				words.add(ExtList+Comma, t.image);
				words.addAll(ClassOrInterfaceType());
				extendsMoreThanOne = true;
			}
		if (extendsMoreThanOne && !isInterface)
		{if (true) throw new ParserException("A class cannot extend more than one other class");}
		return words;
	}

	final public AnalysisData ImplementsList(boolean isInterface) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IMPLEMENTS);
		words.add(Implements, t.image);
		words.addAll(ClassOrInterfaceType());
		label_5:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_5;
				}
				t = jj_consume_token(COMMA);
				words.add(Implements+Comma, t.image);
				words.addAll(ClassOrInterfaceType());
			}
		if (isInterface)
		{if (true) throw new ParserException("An interface cannot implement other interfaces");}
		return words;
	}

	final public AnalysisData EnumDeclaration(int modifiers) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(ENUM);
		words.add(Enum, t.image);
		t = jj_consume_token(IDENTIFIER);
		words.add(Enum+Identifier, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case IMPLEMENTS:
			words.addAll(ImplementsList(false));
			break;
		default:
			;
		}
		words.addAll(EnumBody());
		return words;
	}

	final public AnalysisData EnumBody() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(Enum+Lbrace, t.image);
		words.addAll(EnumConstant());
		label_6:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_6;
				}
				t = jj_consume_token(COMMA);
				words.add(Enum+Comma, t.image);
				words.addAll(EnumConstant());
			}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case SEMICOLON:
			t = jj_consume_token(SEMICOLON);
			words.add(Enum+Semicolon, t.image);
			label_7:
				while (true) {
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case ABSTRACT:
					case BOOLEAN:
					case BYTE:
					case CHAR:
					case CLASS:
					case DOUBLE:
					case ENUM:
					case FINAL:
					case FLOAT:
					case INT:
					case INTERFACE:
					case LONG:
					case NATIVE:
					case PRIVATE:
					case PROTECTED:
					case PUBLIC:
					case SHORT:
					case STATIC:
					case STRICTFP:
					case SYNCHRONIZED:
					case TRANSIENT:
					case VOID:
					case VOLATILE:
					case IDENTIFIER:
					case LBRACE:
					case SEMICOLON:
					case AT:
					case LT:
						break;
					default:
						break label_7;
					}
					words.addAll(ClassOrInterfaceBodyDeclaration(false));
				}
			break;
		default:
			;
		}
		t = jj_consume_token(RBRACE);
		words.add(Enum+Rbrace, t.image);
		return words;
	}

	final public AnalysisData EnumConstant() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(EnumConstant+Identifier, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LPAREN:
			words.addAll(Arguments());
			break;
		default:
			;
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LBRACE:
			words.addAll(ClassOrInterfaceBody(false));
			break;
		default:
			;
		}
		return words;
	}

	final public AnalysisData TypeParameters() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LT);
		words.add(TypeP+Lshift1, t.image);
		words.addAll(TypeParameter());
		label_8:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_8;
				}
				t = jj_consume_token(COMMA);
				words.add(TypeP+Comma, t.image);
				words.addAll(TypeParameter());
			}
		t = jj_consume_token(GT);
		words.add(TypeP+Rshift1, t.image);
		return words;
	}

	final public AnalysisData TypeParameter() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(TypeP+Identifier, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case EXTENDS:
			words.addAll(TypeBound());
			break;
		default:
			;
		}
		return words;
	}

	final public AnalysisData TypeBound() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(EXTENDS);
		words.add(TypeB+Extends, t.image);
		words.addAll(ClassOrInterfaceType());
		label_9:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case BIT_AND:
					break;
				default:
					break label_9;
				}
				t = jj_consume_token(BIT_AND);
				words.add(TypeB+And, t.image);
				words.addAll(ClassOrInterfaceType());
			}
		return words;
	}

	final public AnalysisData ClassOrInterfaceBody(boolean isInterface) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(CoiBody+Lbrace, t.image);
		label_10:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case CLASS:
				case DOUBLE:
				case ENUM:
				case FINAL:
				case FLOAT:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOID:
				case VOLATILE:
				case IDENTIFIER:
				case LBRACE:
				case SEMICOLON:
				case AT:
				case LT:
					break;
				default:
					break label_10;
				}
				words.addAll(ClassOrInterfaceBodyDeclaration(isInterface));
			}
		t = jj_consume_token(RBRACE);
		words.add(CoiBody+Rbrace, t.image);
		return words;
	}

	final public AnalysisData ClassOrInterfaceBodyDeclaration(boolean isInterface) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
//		boolean isNestedInterface = false;
		int modifiers =0;
		if (jj_2_5(2)) {
			words.addAll(Initializer());
			if (isInterface)
			{if (true) throw new ParserException("An interface cannot have initializers");}
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case ABSTRACT:
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case CLASS:
			case DOUBLE:
			case ENUM:
			case FINAL:
			case FLOAT:
			case INT:
			case INTERFACE:
			case LONG:
			case NATIVE:
			case PRIVATE:
			case PROTECTED:
			case PUBLIC:
			case SHORT:
			case STATIC:
			case STRICTFP:
			case SYNCHRONIZED:
			case TRANSIENT:
			case VOID:
			case VOLATILE:
			case IDENTIFIER:
			case AT:
			case LT:
				label_11:
					while (true) {
						if (jj_2_2(2)) {
							;
						} else {
							break label_11;
						}
						switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
						case PUBLIC:
							t = jj_consume_token(PUBLIC);
							words.add(CoiBodyDec+Public, t.image);
							modifiers |= ModifierSet.PUBLIC;
							break;
						case STATIC:
							t = jj_consume_token(STATIC);
							words.add(CoiBodyDec+Static, t.image);
							modifiers |= ModifierSet.STATIC;
							break;
						case PROTECTED:
							t = jj_consume_token(PROTECTED);
							words.add(CoiBodyDec+Protected, t.image);
							modifiers |= ModifierSet.PROTECTED;
							break;
						case PRIVATE:
							t = jj_consume_token(PRIVATE);
							words.add(CoiBodyDec+Private, t.image);
							modifiers |= ModifierSet.PRIVATE;
							break;
						case FINAL:
							t = jj_consume_token(FINAL);
							words.add(CoiBodyDec+Final, t.image);
							modifiers |= ModifierSet.FINAL;
							break;
						case ABSTRACT:
							t = jj_consume_token(ABSTRACT);
							words.add(CoiBodyDec+Abstract, t.image);
							modifiers |= ModifierSet.ABSTRACT;
							break;
						case SYNCHRONIZED:
							t = jj_consume_token(SYNCHRONIZED);
							words.add(CoiBodyDec+Synchronized, t.image);
							modifiers |= ModifierSet.SYNCHRONIZED;
							break;
						case NATIVE:
							t = jj_consume_token(NATIVE);
							words.add(CoiBodyDec+Native, t.image);
							modifiers |= ModifierSet.NATIVE;
							break;
						case TRANSIENT:
							t = jj_consume_token(TRANSIENT);
							words.add(CoiBodyDec+Transient, t.image);
							modifiers |= ModifierSet.TRANSIENT;
							break;
						case VOLATILE:
							t = jj_consume_token(VOLATILE);
							words.add(CoiBodyDec+Volatile, t.image);
							modifiers |= ModifierSet.VOLATILE;
							break;
						case STRICTFP:
							t = jj_consume_token(STRICTFP);
							words.add(CoiBodyDec+Strictfp, t.image);
							modifiers |= ModifierSet.STRICTFP;
							break;
						case AT:
							words.addAll(Annotation());
							break;
						default:
							jj_consume_token(-1);
						throw new ParserException();
						}
					}
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case CLASS:
			case INTERFACE:
				words.addAll(ClassOrInterfaceDeclaration(modifiers));
				break;
			case ENUM:
				words.addAll(EnumDeclaration(modifiers));
				break;
			default:
				if (jj_2_3(2147483647))
					words.addAll(ConstructorDeclaration());
				else if (jj_2_4(2147483647))
					words.addAll(FieldDeclaration(modifiers));
				else {
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case BOOLEAN:
					case BYTE:
					case CHAR:
					case DOUBLE:
					case FLOAT:
					case INT:
					case LONG:
					case SHORT:
					case VOID:
					case IDENTIFIER:
					case LT:
						words.addAll(MethodDeclaration(modifiers));
						break;
					default:
						jj_consume_token(-1);
					throw new ParserException();
					}
				}
			}
			break;
			case SEMICOLON:
				t = jj_consume_token(SEMICOLON);
				words.add(CoiBodyDec+Semicolon, t.image);
				break;
			default:
				jj_consume_token(-1);
			throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData FieldDeclaration(int modifiers) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		// Modifiers are already matched in the caller
		words.addAll(Type());
		words.addAll(VariableDeclarator());
		label_12:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_12;
				}
				t = jj_consume_token(COMMA);
				words.add(Field+Comma, t.image);
				words.addAll(VariableDeclarator());
			}
		t = jj_consume_token(SEMICOLON);
		words.add(Field+Semicolon, t.image);
		return words;
	}

	final public AnalysisData VariableDeclarator() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(VariableDeclaratorId());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case ASSIGN:
			t = jj_consume_token(ASSIGN);
			words.add(Variable+Equal, t.image);
			words.addAll(VariableInitializer());
			break;
		default:
			;
		}
		return words;
	}

	final public AnalysisData VariableDeclaratorId() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(Variable+Identifier, t.image);
		label_13:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LBRACKET:
					break;
				default:
					break label_13;
				}
				t = jj_consume_token(LBRACKET);
				words.add(Variable+Lbracket, t.image);
				t = jj_consume_token(RBRACKET);
				words.add(Variable+Rbracket, t.image);
			}
		return words;
	}

	final public AnalysisData VariableInitializer() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LBRACE:
			words.addAll(ArrayInitializer());
			break;
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case BANG:
		case TILDE:
		case INCR:
		case DECR:
		case PLUS:
		case MINUS:
			words.addAll(Expression());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData ArrayInitializer() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(ArrayIni1+Lbrace, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case LBRACE:
		case BANG:
		case TILDE:
		case INCR:
		case DECR:
		case PLUS:
		case MINUS:
			words.addAll(VariableInitializer());
			label_14:
				while (true) {
					if (jj_2_6(2)) {
						;
					} else {
						break label_14;
					}
					t = jj_consume_token(COMMA);
					words.add(ArrayIni1+Comma, t.image);
					words.addAll(VariableInitializer());
				}
			break;
		default:
			;
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case COMMA:
			t = jj_consume_token(COMMA);
			words.add(ArrayIni2+Comma, t.image);
			break;
		default:
			;
		}
		t = jj_consume_token(RBRACE);
		words.add(ArrayIni1+Rbrace, t.image);
		return words;
	}

	final public AnalysisData MethodDeclaration(int modifiers) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LT:
			words.addAll(TypeParameters());
			break;
		default:
			;
		}
		words.addAll(ResultType());
		words.addAll(MethodDeclarator());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case THROWS:
			t = jj_consume_token(THROWS);
			words.add(Method+Throws, t.image);
			words.addAll(NameList());
			break;
		default:
			;
		}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LBRACE:
			words.addAll(Block());
			break;
		case SEMICOLON:
			t = jj_consume_token(SEMICOLON);
			words.add(Method+Semicolon, t.image);
			break;
		default:
			jj_consume_token(-1);
		throw new ParserException();
		}
		words.add(Method+End, "end_method");
		return words;
	}

	final public AnalysisData MethodDeclarator() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(Method+Identifier, t.image);
		mCount++;
		words.addAll(FormalParameters());
		label_15:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LBRACKET:
					;
					break;
				default:
					break label_15;
				}
				t = jj_consume_token(LBRACKET);
				words.add(Method+Lbracket, t.image);
				t = jj_consume_token(RBRACKET);
				words.add(Method+Rbracket, t.image);
			}
		return words;
	}

	final public AnalysisData FormalParameters() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LPAREN);
		words.add(Formal+Lparen, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FINAL:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case IDENTIFIER:
			words.addAll(FormalParameter());
			label_16:
				while (true) {
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case COMMA:
						break;
					default:
						break label_16;
					}
					t = jj_consume_token(COMMA);
					words.add(Formal+Comma, t.image);
					words.addAll(FormalParameter());
				}
			break;
		default:
			;
		}
		t = jj_consume_token(RPAREN);
		words.add(Formal+Rparen, t.image);
		return words;
	}

	final public AnalysisData FormalParameter() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case FINAL:
			t = jj_consume_token(FINAL);
			words.add(Formal+Final, t.image);
			break;
		default:
			;
		}
		words.addAll(Type());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case ELLIPSIS:
			t = jj_consume_token(ELLIPSIS);
			words.add(Formal+Ellipsis, t.image);
			break;
		default:
			;
		}
		words.addAll(VariableDeclaratorId());
		return words;
	}

	final public AnalysisData ConstructorDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case LT:
			words.addAll(TypeParameters());
			break;
		default:
			;
		}
		// Modifiers matched in the caller
		t = jj_consume_token(IDENTIFIER);
		words.add(Constructor+Identifier, t.image);
		mCount++;
		words.addAll(FormalParameters());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case THROWS:
			t = jj_consume_token(THROWS);
			words.add(Constructor+Throws, t.image);
			words.addAll(NameList());
			break;
		default:
			;
		}
		t = jj_consume_token(LBRACE);
		words.add(Constructor+Lbrace, t.image);
		if (jj_2_7(2147483647)) 
			words.addAll(ExplicitConstructorInvocation());
		label_17:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case ASSERT:
				case BOOLEAN:
				case BREAK:
				case BYTE:
				case CHAR:
				case CLASS:
				case CONTINUE:
				case DO:
				case DOUBLE:
				case FALSE:
				case FINAL:
				case FLOAT:
				case FOR:
				case IF:
				case INT:
				case INTERFACE:
				case LONG:
				case NEW:
				case NULL:
				case RETURN:
				case SHORT:
				case SUPER:
				case SWITCH:
				case SYNCHRONIZED:
				case THIS:
				case THROW:
				case TRUE:
				case TRY:
				case VOID:
				case WHILE:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case LBRACE:
				case SEMICOLON:
				case INCR:
				case DECR:
					break;
				default:
					break label_17;
				}
				words.addAll(BlockStatement());
			}
		t = jj_consume_token(RBRACE);
		words.add(Constructor+Rbrace, t.image);
		words.add(Constructor+End, "end_const");
		return words;
	}

	final public AnalysisData ExplicitConstructorInvocation() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_9(2147483647)) {
			t = jj_consume_token(THIS);
			words.add(ExplicitCons+This, t.image);
			words.addAll(Arguments());
			t = jj_consume_token(SEMICOLON);
			words.add(ExplicitCons+This+Semicolon, t.image);
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
				if (jj_2_8(2)) {
					words.addAll(PrimaryExpression());
					t = jj_consume_token(DOT);
					words.add(ExplicitCons+Dot, t.image);
				}
				t = jj_consume_token(SUPER);
				words.add(ExplicitCons+Super, t.image);
				words.addAll(Arguments());
				t = jj_consume_token(SEMICOLON);
				words.add(ExplicitCons+Super+Semicolon, t.image);
				break;
			default:
				jj_consume_token(-1);
			throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData Initializer() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case STATIC:
			t = jj_consume_token(STATIC);
			words.add(Ini+Static, t.image);
			break;
		default:
			;
		}
		words.addAll(Block());
		return words;
	}


	/*
	 * Type, name and expression syntax.
	 */
	final public AnalysisData Type() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		if (jj_2_10(2)) {
			words.addAll(ReferenceType());
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
				words.addAll(PrimitiveType());
				break;
			default:
				jj_consume_token(-1);
			throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData ReferenceType() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
			words.addAll(PrimitiveType());
			label_18:
				while (true) {
					t = jj_consume_token(LBRACKET);
					words.add(RefType1+Lbracket, t.image);
					t = jj_consume_token(RBRACKET);
					words.add(RefType1+Rbracket, t.image);
					if (!jj_2_11(2))
						break label_18;
				}
			break;
		case IDENTIFIER:
			words.addAll(ClassOrInterfaceType());
			label_19:
				while (true) {
					if (!jj_2_12(2)) 
						break label_19;
					t = jj_consume_token(LBRACKET);
					words.add(RefType2+Lbracket, t.image);
					t = jj_consume_token(RBRACKET);
					words.add(RefType2+Rbracket, t.image);
				}
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData ClassOrInterfaceType() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(CoiType1+Identifier, t.image);
		if (jj_2_13(2)) 
			words.addAll(TypeArguments());
		label_20:
			while (true) {
				if (!jj_2_14(2)) 
					break label_20;
				t = jj_consume_token(DOT);
				words.add(CoiType1+Dot, t.image);
				t = jj_consume_token(IDENTIFIER);
				words.add(CoiType2+Identifier, t.image);
				if (jj_2_15(2)) 
					words.addAll(TypeArguments());
			}
		return words;
	}

	final public AnalysisData TypeArguments() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LT);
		words.add(TypeA+Lshift1, t.image);
		words.addAll(TypeArgument());
		label_21:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_21;
				}
				t = jj_consume_token(COMMA);
				words.add(TypeA+Comma, t.image);
				words.addAll(TypeArgument());
			}
		t = jj_consume_token(GT);
		words.add(TypeA+Rshift1, t.image);
		return words;
	}

	final public AnalysisData TypeArgument() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case IDENTIFIER:
			words.addAll(ReferenceType());
			break;
		case HOOK:
			t = jj_consume_token(HOOK);
			words.add(TypeA+Hook, t.image);
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case EXTENDS:
			case SUPER:
				words.addAll(WildcardBounds());
				break;
			default:
				;
			}
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData WildcardBounds() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case EXTENDS:
			t = jj_consume_token(EXTENDS);
			words.add(WildBounds+Extends, t.image);
			words.addAll(ReferenceType());
			break;
		case SUPER:
			t = jj_consume_token(SUPER);
			words.add(WildBounds+Super, t.image);
			words.addAll(ReferenceType());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData PrimitiveType() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
			t = jj_consume_token(BOOLEAN);
			words.add(Boolean, t.image);
			break;
		case CHAR:
			t = jj_consume_token(CHAR);
			words.add(Char, t.image);
			break;
		case BYTE:
			t = jj_consume_token(BYTE);
			words.add(Byte, t.image);
			break;
		case SHORT:
			t = jj_consume_token(SHORT);
			words.add(Short, t.image);
			break;
		case INT:
			t = jj_consume_token(INT);
			words.add(Int, t.image);
			break;
		case LONG:
			t = jj_consume_token(LONG);
			words.add(Long, t.image);
			break;
		case FLOAT:
			t = jj_consume_token(FLOAT);
			words.add(Float, t.image);
			break;
		case DOUBLE:
			t = jj_consume_token(DOUBLE);
			words.add(Double, t.image);
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData ResultType() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case VOID:
			t = jj_consume_token(VOID);
			words.add(Void, t.image);
			break;
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FLOAT:
		case INT:
		case LONG:
		case SHORT:
		case IDENTIFIER:
			words.addAll(Type());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData Name() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(Name1+Identifier, t.image);
		label_22:
			while (true) {
				if (!jj_2_16(2)) 
					break label_22;
				t = jj_consume_token(DOT);
				words.add(Name1+Dot, t.image);
				t = jj_consume_token(IDENTIFIER);
				words.add(Name2+Identifier, t.image);
			}
		return words;
	}

	final public AnalysisData NameList() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(Name());
		label_23:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_23;
				}
				t = jj_consume_token(COMMA);
				words.add(Name1+Comma, t.image);
				words.addAll(Name());
			}
		return words;
	}

	/*
	* Expression syntax
	*/
	final public AnalysisData Expression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		words.addAll(ConditionalExpression());
		if (jj_2_17(2)) {
			words.addAll(AssignmentOperator());
			words.addAll(Expression());
		}
		return words;
	}

	final public AnalysisData AssignmentOperator() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case ASSIGN:
			t = jj_consume_token(ASSIGN);
			words.add(Assign1+Assign, t.image);
			break;
		case STARASSIGN:
			t = jj_consume_token(STARASSIGN);
			words.add(Assign2+Star+Assign, t.image);
			break;
		case SLASHASSIGN:
			t = jj_consume_token(SLASHASSIGN);
			words.add(Assign2+Slash+Assign, t.image);
			break;
		case REMASSIGN:
			t = jj_consume_token(REMASSIGN);
			words.add(Assign2+Remainder+Assign, t.image);
			break;
		case PLUSASSIGN:
			t = jj_consume_token(PLUSASSIGN);
			words.add(Assign2+Plus+Assign, t.image);
			break;
		case MINUSASSIGN:
			t = jj_consume_token(MINUSASSIGN);
			words.add(Assign2+Minus+Assign, t.image);
			break;
		case LSHIFTASSIGN:
			t = jj_consume_token(LSHIFTASSIGN);
			words.add(Assign3+Lshift2+Assign, t.image);
			break;
		case RSIGNEDSHIFTASSIGN:
			t = jj_consume_token(RSIGNEDSHIFTASSIGN);
			words.add(Assign3+Rshift2+Assign, t.image);
			break;
		case RUNSIGNEDSHIFTASSIGN:
			t = jj_consume_token(RUNSIGNEDSHIFTASSIGN);
			words.add(Assign3+Rshift3+Assign, t.image);
			break;
		case ANDASSIGN:
		    t = jj_consume_token(ANDASSIGN);
		    words.add(Assign3+And+Assign, t.image);
		    break;
		case XORASSIGN:
			t = jj_consume_token(XORASSIGN);
			words.add(Assign3+Xor+Assign, t.image);
			break;
		case ORASSIGN:
			t = jj_consume_token(ORASSIGN);
			words.add(Assign3+Or+Assign, t.image);
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData ConditionalExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(ConditionalOrExpression());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case HOOK:
			t = jj_consume_token(HOOK);
			words.add(ConditionExp+Hook, t.image);
			words.addAll(Expression());
			t = jj_consume_token(COLON);
			words.add(ConditionExp+Colon, t.image);
			words.addAll(Expression());
			break;
		default:
			;
		}
		return words;
	}

	final public AnalysisData ConditionalOrExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(ConditionalAndExpression());
		label_24:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case SC_OR:
					break;
				default:
					break label_24;
				}
				t = jj_consume_token(SC_OR);
				words.add(ConditionExp+Or+Or, t.image);
				words.addAll(ConditionalAndExpression());
			}
		return words;
	}

	final public AnalysisData ConditionalAndExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(InclusiveOrExpression());
		label_25:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case SC_AND:
					break;
				default:
					break label_25;
				}
				t = jj_consume_token(SC_AND);
				words.add(ConditionExp+And+And, t.image);
				words.addAll(InclusiveOrExpression());
			}
		return words;
	}

	final public AnalysisData InclusiveOrExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(ExclusiveOrExpression());
		label_26:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case BIT_OR:
					break;
				default:
					break label_26;
				}
				t = jj_consume_token(BIT_OR);
				words.add(OrAndExp+Or, t.image);
				words.addAll(ExclusiveOrExpression());
			}
		return words;
	}

	final public AnalysisData AndExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(EqualityExpression());
		label_28:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case BIT_AND:
					break;
				default:
					break label_28;
				}
				t = jj_consume_token(BIT_AND);
				words.add(OrAndExp+And, t.image);
				words.addAll(EqualityExpression());
			}
		return words;
	}

	final public AnalysisData ExclusiveOrExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(AndExpression());
		label_27:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case XOR:
					break;
				default:
					break label_27;
				}
				t = jj_consume_token(XOR);
				words.add(OrAndExp+Xor, t.image);
				words.addAll(AndExpression());
			}
		return words;
	}

	final public AnalysisData EqualityExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(InstanceOfExpression());
		label_29:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case EQ:
				case NE:
					break;
				default:
					break label_29;
				}
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case EQ:
					t = jj_consume_token(EQ);
					words.add(EqualityExp+Equal+Equal, t.image);
					break;
				case NE:
					t = jj_consume_token(NE);
					words.add(EqualityExp+Bang+Equal, t.image);
					break;
				default:
					jj_consume_token(-1);
				throw new ParserException();
				}
				words.addAll(InstanceOfExpression());
			}
		return words;
	}

	final public AnalysisData InstanceOfExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(RelationalExpression());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case INSTANCEOF:
			t = jj_consume_token(INSTANCEOF);
			words.add(Instanceof, t.image);
			words.addAll(Type());
			break;
		default:
			;
		}
		return words;
	}

	final public AnalysisData RelationalExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(ShiftExpression());
		label_30:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LT:
				case LE:
				case GE:
				case GT:
					break;
				default:
					break label_30;
				}
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LT:
					t = jj_consume_token(LT);
					words.add(RelationExp+Lshift1, t.image);
					break;
				case GT:
					t = jj_consume_token(GT);
					words.add(RelationExp+Rshift1, t.image);
					break;
				case LE:
					t = jj_consume_token(LE);
					words.add(RelationExp+Lshift1+Equal, t.image);
					break;
				case GE:
					t = jj_consume_token(GE);
					words.add(RelationExp+Rshift1+Equal, t.image);
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
				words.addAll(ShiftExpression());
			}
		return words;
	}

	final public AnalysisData ShiftExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(AdditiveExpression());
		label_31:
			while (true) {
				if (!jj_2_18(1)) 
					break label_31;
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LSHIFT:
					t = jj_consume_token(LSHIFT);
					words.add(ShiftExp+Lshift2, t.image);
					break;
				default:
					if (jj_2_19(1)) 
						words.addAll(RSIGNEDSHIFT());
					else if (jj_2_20(1)) 
						words.addAll(RUNSIGNEDSHIFT());
					else {
						jj_consume_token(-1);
						throw new ParserException();
					}
				}
				words.addAll(AdditiveExpression());
			}
		return words;
	}

	final public AnalysisData AdditiveExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(MultiplicativeExpression());
		label_32:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case PLUS:
				case MINUS:
					break;
				default:
					break label_32;
				}
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case PLUS:
					t = jj_consume_token(PLUS);
					words.add(Exp+Plus, t.image);
					break;
				case MINUS:
					t = jj_consume_token(MINUS);
					words.add(Exp+Minus, t.image);
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
				words.addAll(MultiplicativeExpression());
			}
		return words;
	}

	final public AnalysisData MultiplicativeExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(UnaryExpression());
		label_33:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case STAR:
				case SLASH:
				case REM:
					break;
				default:
					break label_33;
				}
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case STAR:
					t = jj_consume_token(STAR);
					words.add(Exp+Star, t.image);
					break;
				case SLASH:
					t = jj_consume_token(SLASH);
					words.add(Exp+Slash, t.image);
					break;
				case REM:
					t = jj_consume_token(REM);
					words.add(Exp+Remainder, t.image);
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
				words.addAll(UnaryExpression());
			}
		return words;
	}

	final public AnalysisData UnaryExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case PLUS:
		case MINUS:
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case PLUS:
				t = jj_consume_token(PLUS);
				words.add(UnaryExp+Plus, t.image);
				break;
			case MINUS:
				t = jj_consume_token(MINUS);
				words.add(UnaryExp+Minus, t.image);
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
			words.addAll(UnaryExpression());
			break;
		case INCR:
			words.addAll(PreIncrementExpression());
			break;
		case DECR:
		    words.addAll(PreDecrementExpression());
		    break;
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case BANG:
		case TILDE:
			words.addAll(UnaryExpressionNotPlusMinus());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData PreIncrementExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(INCR);
		words.add(PreExp+Incre, t.image);
		words.addAll(PrimaryExpression());
		return words;
	}

	final public AnalysisData PreDecrementExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(DECR);
		words.add(PreExp+Decre, t.image);
		words.addAll(PrimaryExpression());
		return words;
	}

	final public AnalysisData UnaryExpressionNotPlusMinus() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BANG:
		case TILDE:
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case TILDE:
				t = jj_consume_token(TILDE);
				words.add(UnaryExp+Tilde, t.image);
				break;
			case BANG:
				t = jj_consume_token(BANG);
				words.add(UnaryExp+Bang, t.image);
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
			words.addAll(UnaryExpression());
			break;
		default:
			if (jj_2_21(2147483647))
				words.addAll(CastExpression());
			else {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case DOUBLE:
				case FALSE:
				case FLOAT:
				case INT:
				case LONG:
				case NEW:
				case NULL:
				case SHORT:
				case SUPER:
				case THIS:
				case TRUE:
				case VOID:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
					words.addAll(PostfixExpression());
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
			}
		}
		return words;
	}


//	This production is to determine lookahead only.  The LOOKAHEAD specifications
//	below are not used, but they are there just to indicate that we know about
//	this.
	final public AnalysisData CastLookahead() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_22(2)) {
			t = jj_consume_token(LPAREN);
			words.add(Cast1+Lparen, t.image);
			words.addAll(PrimitiveType());
		} else if (jj_2_23(2147483647)) {
			t = jj_consume_token(LPAREN);
			words.add(Cast2+Lparen, t.image);
			words.addAll(Type());
			t = jj_consume_token(LBRACKET);
			words.add(Cast2+Lbracket, t.image);
			t = jj_consume_token(RBRACKET);
			words.add(Cast2+Rbracket, t.image);
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case LPAREN:
				t = jj_consume_token(LPAREN);
				words.add(Cast3+Lparen, t.image);
				words.addAll(Type());
				t = jj_consume_token(RPAREN);
				words.add(Cast3+Rparen, t.image);
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case TILDE:
					t = jj_consume_token(TILDE);
					words.add(SubCast1+Tilde, t.image);
					break;
				case BANG:
					t = jj_consume_token(BANG);
					words.add(SubCast1+Bang, t.image);
					break;
				case LPAREN:
					t = jj_consume_token(LPAREN);
					words.add(SubCast1+Lparen, t.image);
					break;
				case IDENTIFIER:
					t = jj_consume_token(IDENTIFIER);
					words.add(SubCast1+Identifier, t.image);
					break;
				case THIS:
					t = jj_consume_token(THIS);
					words.add(SubCast1+This, t.image);
					break;
				case SUPER:
					t = jj_consume_token(SUPER);
					words.add(SubCast1+Super, t.image);
					break;
				case NEW:
					t = jj_consume_token(NEW);
					words.add(SubCast2+New, t.image);
					break;
				case FALSE:
				case NULL:
				case TRUE:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
					words.addAll(Literal());
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData PostfixExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(PrimaryExpression());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case INCR:
		case DECR:
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case INCR:
				t = jj_consume_token(INCR);
				words.add(PostfixExp+Incre, t.image);
				break;
			case DECR:
				t = jj_consume_token(DECR);
				words.add(PostfixExp+Decre, t.image);
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
			break;
		default:
			;
		}
		return words;
	}


	final public AnalysisData CastExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_24(2147483647)) {
			t = jj_consume_token(LPAREN);
			words.add(CastExp1+Lparen, t.image);
			words.addAll(Type());
			t = jj_consume_token(RPAREN);
			words.add(CastExp1+Rparen, t.image);
			words.addAll(UnaryExpression());
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case LPAREN:
				t = jj_consume_token(LPAREN);
				words.add(CastExp2+Lparen, t.image);
				words.addAll(Type());
				t = jj_consume_token(RPAREN);
				words.add(CastExp2+Rparen, t.image);
				words.addAll(UnaryExpressionNotPlusMinus());
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData PrimaryExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		words.addAll(PrimaryPrefix());
		label_34:
			while (true) {
				if (!jj_2_25(2))
					break label_34;
				words.addAll(PrimarySuffix());
			}
		return words;
	}

	final public AnalysisData MemberSelector() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(DOT);
		words.add(MemSelect+Dot, t.image);
		words.addAll(TypeArguments());
		t = jj_consume_token(IDENTIFIER);
		words.add(MemSelect+Identifier, t.image);
		return words;
	}

	final public AnalysisData PrimaryPrefix() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case FALSE:
		case NULL:
		case TRUE:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
			words.addAll(Literal());
			break;
		case THIS:
			t = jj_consume_token(THIS);
			words.add(Prefix1+This, t.image);
			break;
		case SUPER:
			t = jj_consume_token(SUPER);
			words.add(Prefix1+Super, t.image);
			t = jj_consume_token(DOT);
			words.add(Prefix1+Dot, t.image);
		    t = jj_consume_token(IDENTIFIER);
		    words.add(Prefix1+Identifier, t.image);
		    break;
		case LPAREN:
			t = jj_consume_token(LPAREN);
			words.add(Prefix1+Lparen, t.image);
		    words.addAll(Expression());
		    t = jj_consume_token(RPAREN);
		    words.add(Prefix1+Rparen, t.image);
		    break;
		case NEW:
			words.addAll(AllocationExpression());
			break;
		default:
			if (jj_2_26(2147483647)) {
				words.addAll(ResultType());
				t = jj_consume_token(DOT);
				words.add(Prefix2+Dot, t.image);
				t = jj_consume_token(CLASS);
				words.add(Prefix2+Class, t.image);
			} else {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case IDENTIFIER:
					words.addAll(Name());
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
			}
		}
		return words;
	}

	final public AnalysisData PrimarySuffix() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_27(2)) {
			t = jj_consume_token(DOT);
			words.add(Suffix1+Dot, t.image);
			t = jj_consume_token(THIS);
			words.add(Suffix1+This, t.image);
		} else if (jj_2_28(2)) {
			t = jj_consume_token(DOT);
			words.add(Suffix2+Dot, t.image);
			words.addAll(AllocationExpression());
		} else if (jj_2_29(3)) {
			words.addAll(MemberSelector());
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case LBRACKET:
				t = jj_consume_token(LBRACKET);
				words.add(Suffix1+Lbracket, t.image);
				words.addAll(Expression());
				t = jj_consume_token(RBRACKET);
				words.add(Suffix1+Rbracket, t.image);
				break;
			case DOT:
				t = jj_consume_token(DOT);
				words.add(Suffix3+Dot, t.image);
				t = jj_consume_token(IDENTIFIER);
				words.add(Suffix1+Identifier, t.image);
				break;
			case LPAREN:
				words.addAll(Arguments());
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData Literal() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case INTEGER_LITERAL:
			t = jj_consume_token(INTEGER_LITERAL);
			words.add(Lit_Integer, t.image);
			break;
		case FLOATING_POINT_LITERAL:
			t = jj_consume_token(FLOATING_POINT_LITERAL);
			words.add(Lit_Floating_P, t.image);
			break;
		case CHARACTER_LITERAL:
			t = jj_consume_token(CHARACTER_LITERAL);
			words.add(Lit_Character, t.image);
			break;
		case STRING_LITERAL:
			t = jj_consume_token(STRING_LITERAL);
			words.add(Lit_String, t.image);
			break;
		case FALSE:
		case TRUE:
			words.addAll(BooleanLiteral());
			break;
		case NULL:
			words.addAll(NullLiteral());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData BooleanLiteral() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case TRUE:
			t = jj_consume_token(TRUE);
			words.add(Lit_True, t.image);
			break;
		case FALSE:
			t = jj_consume_token(FALSE);
			words.add(Lit_False, t.image);
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData NullLiteral() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(NULL);
		words.add(Lit_Null, t.image);
		return words;
	}

	final public AnalysisData Arguments() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LPAREN);
		words.add(Args+Lparen, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case BANG:
		case TILDE:
		case INCR:
		case DECR:
		case PLUS:
		case MINUS:
			words.addAll(ArgumentList());
			break;
		default:
			;
		}
		t = jj_consume_token(RPAREN);
		words.add(Args+Rparen, t.image);
		return words;
	}

	final public AnalysisData ArgumentList() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(Expression());
		label_35:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_35;
				}
				t = jj_consume_token(COMMA);
				words.add(Args+Comma, t.image);
				words.addAll(Expression());
			}
		return words;
	}

	final public AnalysisData AllocationExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_30(2)) {
			t = jj_consume_token(NEW);
			words.add(ArrayExp+New, t.image);
			words.addAll(PrimitiveType());
			words.addAll(ArrayDimsAndInits());
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case NEW:
				t = jj_consume_token(NEW);
				words.add(ObjectExp+New, t.image);
				words.addAll(ClassOrInterfaceType());
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LT:
					words.addAll(TypeArguments());
					break;
				default:
					;
				}
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case LBRACKET:
					words.addAll(ArrayDimsAndInits());
					break;
				case LPAREN:
					words.addAll(Arguments());
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case LBRACE:
						words.addAll(ClassOrInterfaceBody(false));
						break;
					default:
						;
					}
					break;
				default:
					jj_consume_token(-1);
					throw new ParserException();
				}
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	/*
	* The third LOOKAHEAD specification below is to parse to PrimarySuffix
	* if there is an expression between the "[...]".
	*/
	final public AnalysisData ArrayDimsAndInits() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_33(2)) {
			label_36:
				while (true) {
					t = jj_consume_token(LBRACKET);
					words.add(ArrayDim1+Lbracket, t.image);
					words.addAll(Expression());
					t = jj_consume_token(RBRACKET);
					words.add(ArrayDim1+Rbracket, t.image);
					if (!jj_2_31(2)) 
						break label_36;
				}
		label_37:
			while (true) {
				if (!jj_2_32(2))
					break label_37;
				t = jj_consume_token(LBRACKET);
				words.add(ArrayDim2+Lbracket, t.image);
				t = jj_consume_token(RBRACKET);
				words.add(ArrayDim2+Rbracket, t.image);
			}
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case LBRACKET:
				label_38:
					while (true) {
						t = jj_consume_token(LBRACKET);
						words.add(ArrayDim3+Lbracket, t.image);
						t = jj_consume_token(RBRACKET);
						words.add(ArrayDim3+Rbracket, t.image);
						switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
						case LBRACKET:
							break;
						default:
							break label_38;
						}
					}
			words.addAll(ArrayInitializer());
			break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	/*
	 * Statement syntax follows.
	 */
	final public AnalysisData Statement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_34(2))
			words.addAll(LabeledStatement());
		else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case ASSERT:
				words.addAll(AssertStatement());
				break;
			case LBRACE:
				words.addAll(Block());
				break;
			case SEMICOLON:
				words.addAll(EmptyStatement());
				break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FALSE:
			case FLOAT:
			case INT:
			case LONG:
			case NEW:
			case NULL:
			case SHORT:
			case SUPER:
			case THIS:
			case TRUE:
			case VOID:
			case INTEGER_LITERAL:
			case FLOATING_POINT_LITERAL:
			case CHARACTER_LITERAL:
			case STRING_LITERAL:
			case IDENTIFIER:
			case LPAREN:
			case INCR:
			case DECR:
				words.addAll(StatementExpression());
				t = jj_consume_token(SEMICOLON);
				words.add(State+Semicolon, t.image);
				break;
			case SWITCH:
				words.addAll(SwitchStatement());
				break;
			case IF:
				words.addAll(IfStatement());
				break;
			case WHILE:
				words.addAll(WhileStatement());
				break;
			case DO:
				words.addAll(DoStatement());
				break;
			case FOR:
				words.addAll(ForStatement());
				break;
			case BREAK:
				words.addAll(BreakStatement());
				break;
			case CONTINUE:
				words.addAll(ContinueStatement());
				break;
			case RETURN:
				words.addAll(ReturnStatement());
				break;
			case THROW:
				words.addAll(ThrowStatement());
				break;
			case SYNCHRONIZED:
				words.addAll(SynchronizedStatement());
				break;
			case TRY:
				words.addAll(TryStatement());
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData AssertStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(ASSERT);
		words.add(Assert, t.image);
		words.addAll(Expression());
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case COLON:
			t = jj_consume_token(COLON);
			words.add(Assert+Colon, t.image);
			words.addAll(Expression());
			break;
		default:
			;
		}
		t = jj_consume_token(SEMICOLON);
		words.add(Assert+Semicolon, t.image);
		return words;
	}

	final public AnalysisData LabeledStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(Label+Identifier, t.image);
		t = jj_consume_token(COLON);
		words.add(Label+Colon, t.image);
		words.addAll(Statement());
		words.add(Label+End, "end_Label");
		return words;
	}

	final public AnalysisData Block() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(Block+Lbrace, t.image);
		label_39:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case ASSERT:
				case BOOLEAN:
				case BREAK:
				case BYTE:
				case CHAR:
				case CLASS:
				case CONTINUE:
				case DO:
				case DOUBLE:
				case FALSE:
				case FINAL:
				case FLOAT:
				case FOR:
				case IF:
				case INT:
				case INTERFACE:
				case LONG:
				case NEW:
				case NULL:
				case RETURN:
				case SHORT:
				case SUPER:
				case SWITCH:
				case SYNCHRONIZED:
				case THIS:
				case THROW:
				case TRUE:
				case TRY:
				case VOID:
				case WHILE:
				case INTEGER_LITERAL:
				case FLOATING_POINT_LITERAL:
				case CHARACTER_LITERAL:
				case STRING_LITERAL:
				case IDENTIFIER:
				case LPAREN:
				case LBRACE:
				case SEMICOLON:
				case INCR:
				case DECR:
					break;
				default:
					break label_39;
				}
				words.addAll(BlockStatement());
			}
		t = jj_consume_token(RBRACE);
		words.add(Block+Rbrace, t.image);
		return words;
	}

	final public AnalysisData BlockStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		if (jj_2_35(2147483647)) {
			words.addAll(LocalVariableDeclaration());
			t = jj_consume_token(SEMICOLON);
			words.add(Block+Semicolon, t.image);
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case ASSERT:
		    case BOOLEAN:
		    case BREAK:
		    case BYTE:
		    case CHAR:
		    case CONTINUE:
		    case DO:
		    case DOUBLE:
		    case FALSE:
		    case FLOAT:
		    case FOR:
		    case IF:
		    case INT:
		    case LONG:
		    case NEW:
		    case NULL:
		    case RETURN:
		    case SHORT:
		    case SUPER:
		    case SWITCH:
		    case SYNCHRONIZED:
		    case THIS:
		    case THROW:
		    case TRUE:
		    case TRY:
		    case VOID:
		    case WHILE:
		    case INTEGER_LITERAL:
		    case FLOATING_POINT_LITERAL:
		    case CHARACTER_LITERAL:
		    case STRING_LITERAL:
		    case IDENTIFIER:
		    case LPAREN:
		    case LBRACE:
		    case SEMICOLON:
		    case INCR:
		    case DECR:
		    	words.addAll(Statement());
		    	break;
		    case CLASS:
		    case INTERFACE:
		    	words.addAll(ClassOrInterfaceDeclaration(0));
		    	break;
		    default:
		    	jj_consume_token(-1);
		    	throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData LocalVariableDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case FINAL:
			t = jj_consume_token(FINAL);
			words.add(LocalVariable+Final, t.image);
			break;
		default:
			;
		}
		words.addAll(Type());
		words.addAll(VariableDeclarator());
		label_40:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_40;
				}
				t = jj_consume_token(COMMA);
				words.add(LocalVariable+Comma, t.image);
				words.addAll(VariableDeclarator());
			}
		return words;
	}

	final public AnalysisData EmptyStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(SEMICOLON);
		words.add(Empty+Semicolon, t.image);
		return words;
	}

	final public AnalysisData StatementExpression() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case INCR:
			words.addAll(PreIncrementExpression());
			break;
		case DECR:
			words.addAll(PreDecrementExpression());
			break;
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
			words.addAll(PrimaryExpression());
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case ASSIGN:
		    case INCR:
		    case DECR:
		    case PLUSASSIGN:
		    case MINUSASSIGN:
		    case STARASSIGN:
		    case SLASHASSIGN:
		    case ANDASSIGN:
		    case ORASSIGN:
		    case XORASSIGN:
		    case REMASSIGN:
		    case LSHIFTASSIGN:
		    case RSIGNEDSHIFTASSIGN:
		    case RUNSIGNEDSHIFTASSIGN:
		    	switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		    	case INCR:
		    		t = jj_consume_token(INCR);
		    		words.add(StateExp+Incre, t.image);
		    		break;
		    	case DECR:
		    		t = jj_consume_token(DECR);
		    		words.add(StateExp+Decre, t.image);
		    		break;
		    	case ASSIGN:
		    	case PLUSASSIGN:
		    	case MINUSASSIGN:
		    	case STARASSIGN:
		    	case SLASHASSIGN:
		    	case ANDASSIGN:
		    	case ORASSIGN:
		    	case XORASSIGN:
		    	case REMASSIGN:
		    	case LSHIFTASSIGN:
		    	case RSIGNEDSHIFTASSIGN:
		    	case RUNSIGNEDSHIFTASSIGN:
		    		words.addAll(AssignmentOperator());
		    		words.addAll(Expression());
		    		break;
		    	default:
		    		jj_consume_token(-1);
		    		throw new ParserException();
		    	}
		    	break;
		    default:
		    	;
			}
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData SwitchStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		boolean isCase = false;
		t = jj_consume_token(SWITCH);
		words.add(Switch, t.image);
		t = jj_consume_token(LPAREN);
		words.add(Switch+Lparen, t.image);
		words.addAll(Expression());
		t = jj_consume_token(RPAREN);
		words.add(Switch+Rparen, t.image);
		t = jj_consume_token(LBRACE);
		words.add(Switch+Lbrace, t.image);
		label_41:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case CASE:
					isCase = true;
					break;
				case _DEFAULT:
					isCase = false;
					break;
				default:
					break label_41;
				}
				words.addAll(SwitchLabel());
				label_42:
					while (true) {
						switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
						case ASSERT:
						case BOOLEAN:
						case BREAK:
						case BYTE:
						case CHAR:
						case CLASS:
						case CONTINUE:
						case DO:
						case DOUBLE:
						case FALSE:
						case FINAL:
						case FLOAT:
						case FOR:
						case IF:
						case INT:
						case INTERFACE:
						case LONG:
						case NEW:
						case NULL:
						case RETURN:
						case SHORT:
						case SUPER:
						case SWITCH:
						case SYNCHRONIZED:
						case THIS:
						case THROW:
						case TRUE:
						case TRY:
						case VOID:
						case WHILE:
						case INTEGER_LITERAL:
						case FLOATING_POINT_LITERAL:
						case CHARACTER_LITERAL:
						case STRING_LITERAL:
						case IDENTIFIER:
						case LPAREN:
						case LBRACE:
						case SEMICOLON:
						case INCR:
						case DECR:
							break;
						default:
							break label_42;
						}
						words.addAll(BlockStatement());
					}
				if(isCase) 
					words.add(Case+End, "end_case");
				else
					words.add(Default+End, "end_default");
			}
		t = jj_consume_token(RBRACE);
		words.add(Switch+Rbrace, t.image);
		words.add(Switch+End, "end_switch");
		return words;
	}

	final public AnalysisData SwitchLabel() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case CASE:
			t = jj_consume_token(CASE);
			words.add(Case, t.image);
		    words.addAll(Expression());
		    t = jj_consume_token(COLON);
		    words.add(Case+Colon, t.image);
		    break;
		case _DEFAULT:
			t = jj_consume_token(_DEFAULT);
			words.add(Default, t.image);
			t = jj_consume_token(COLON);
			words.add(Default+Colon, t.image);
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData IfStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IF);
		words.add(If, t.image);
		t = jj_consume_token(LPAREN);
		words.add(If+Lparen, t.image);
		words.addAll(Expression());
		t = jj_consume_token(RPAREN);
		words.add(If+Rparen, t.image);
		words.addAll(Statement());
		words.add(If+End, "end_if");
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case ELSE:
			t = jj_consume_token(ELSE);
			words.add(Else, t.image);
			words.addAll(Statement());
			words.add(Else+End, "end_else");
			break;
		default:
			;
		}
		words.add(Branch+End, "end_branch");
		return words;
	}

	final public AnalysisData WhileStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(WHILE);
		words.add(While, t.image);
		t = jj_consume_token(LPAREN);
		words.add(While+Lparen, t.image);
		words.addAll(Expression());
		t = jj_consume_token(RPAREN);
		words.add(While+Rparen, t.image);
		words.addAll(Statement());
		words.add(While+End, "While_end");
		return words;
	}

	final public AnalysisData DoStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(DO);
		words.add(Do, t.image);
		words.addAll(Statement());
		t = jj_consume_token(WHILE);
		words.add(Do_While, t.image);
		t = jj_consume_token(LPAREN);
		words.add(Do_While+Lparen, t.image);
		words.addAll(Expression());
		t = jj_consume_token(RPAREN);
		words.add(Do_While+Rparen, t.image);
		t = jj_consume_token(SEMICOLON);
		words.add(Do_While+Semicolon, t.image);
		words.add(Do_While+End, "end_do_while");
		return words;
	}

	final public AnalysisData ForStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(FOR);
		words.add(For, t.image);
		t = jj_consume_token(LPAREN);
		words.add(For+Lparen, t.image);
		if (jj_2_36(2147483647)) {
			words.addAll(Type());
			t = jj_consume_token(IDENTIFIER);
			words.add(For_Ini+Identifier, t.image);
			t = jj_consume_token(COLON);
			words.add(For_Ini+Colon, t.image);
			words.addAll(Expression());
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case BOOLEAN:
		    case BYTE:
		    case CHAR:
		    case DOUBLE:
		    case FALSE:
		    case FINAL:
		    case FLOAT:
		    case INT:
		    case LONG:
		    case NEW:
		    case NULL:
		    case SHORT:
		    case SUPER:
		    case THIS:
		    case TRUE:
		    case VOID:
		    case INTEGER_LITERAL:
		    case FLOATING_POINT_LITERAL:
		    case CHARACTER_LITERAL:
		    case STRING_LITERAL:
		    case IDENTIFIER:
		    case LPAREN:
		    case SEMICOLON:
		    case INCR:
		    case DECR:
		    	switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		    	case BOOLEAN:
		    	case BYTE:
		    	case CHAR:
		    	case DOUBLE:
		    	case FALSE:
		    	case FINAL:
		    	case FLOAT:
		    	case INT:
		    	case LONG:
		    	case NEW:
		    	case NULL:
		    	case SHORT:
		    	case SUPER:
		    	case THIS:
		    	case TRUE:
		    	case VOID:
		    	case INTEGER_LITERAL:
		    	case FLOATING_POINT_LITERAL:
		    	case CHARACTER_LITERAL:
		    	case STRING_LITERAL:
		    	case IDENTIFIER:
		    	case LPAREN:
		    	case INCR:
		    	case DECR:
		    		words.addAll(ForInit());
		    		break;
		    	default:
		    		;
		    	}
		    	t = jj_consume_token(SEMICOLON);
		    	words.add(For_Ini+Semicolon, t.image);
		    	switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		    	case BOOLEAN:
		    	case BYTE:
		    	case CHAR:
		    	case DOUBLE:
		    	case FALSE:
		    	case FLOAT:
		    	case INT:
		    	case LONG:
		    	case NEW:
		    	case NULL:
		    	case SHORT:
		    	case SUPER:
		    	case THIS:
		    	case TRUE:
		    	case VOID:
		    	case INTEGER_LITERAL:
		    	case FLOATING_POINT_LITERAL:
		    	case CHARACTER_LITERAL:
		    	case STRING_LITERAL:
		    	case IDENTIFIER:
		    	case LPAREN:
		    	case BANG:
		    	case TILDE:
		    	case INCR:
		    	case DECR:
		    	case PLUS:
		    	case MINUS:
		    		words.addAll(Expression());
		    		break;
		    	default:
		    		;
		    	}
		    	t = jj_consume_token(SEMICOLON);
		    	words.add(For_Con+Semicolon, t.image);
		    	switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		    	case BOOLEAN:
		    	case BYTE:
		    	case CHAR:
		    	case DOUBLE:
		    	case FALSE:
		    	case FLOAT:
		    	case INT:
		    	case LONG:
		    	case NEW:
		    	case NULL:
		    	case SHORT:
		    	case SUPER:
		    	case THIS:
		    	case TRUE:
		    	case VOID:
		    	case INTEGER_LITERAL:
		    	case FLOATING_POINT_LITERAL:
		    	case CHARACTER_LITERAL:
		    	case STRING_LITERAL:
		    	case IDENTIFIER:
		    	case LPAREN:
		    	case INCR:
		    	case DECR:
		    		words.addAll(ForUpdate());
		    		break;
		    	default:
		    		;
		    	}
		    	break;
		    default:
		    	jj_consume_token(-1);
		    	throw new ParserException();
			}
		}
		t = jj_consume_token(RPAREN);
		words.add(For+Rparen, t.image);
		words.addAll(Statement());
		words.add(For+End, "end_for");
		return words;
	}

	final public AnalysisData ForInit() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		if (jj_2_37(2147483647))
			words.addAll(LocalVariableDeclaration());
		else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		    case BOOLEAN:
		    case BYTE:
		    case CHAR:
		    case DOUBLE:
		    case FALSE:
		    case FLOAT:
		    case INT:
		    case LONG:
		    case NEW:
		    case NULL:
		    case SHORT:
		    case SUPER:
		    case THIS:
		    case TRUE:
		    case VOID:
		    case INTEGER_LITERAL:
		    case FLOATING_POINT_LITERAL:
		    case CHARACTER_LITERAL:
		    case STRING_LITERAL:
		    case IDENTIFIER:
		    case LPAREN:
		    case INCR:
		    case DECR:
		    	words.addAll(StatementExpressionList());
		    	break;
		    default:
		    	jj_consume_token(-1);
		    	throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData StatementExpressionList() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		words.addAll(StatementExpression());
		label_43:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_43;
				}
				t = jj_consume_token(COMMA);
				words.add(StateExpList+Comma, t.image);
				words.addAll(StatementExpression());
			}
		return words;
	}

	final public AnalysisData ForUpdate() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		words.addAll(StatementExpressionList());
		return words;
	}

	final public AnalysisData BreakStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(BREAK);
		words.add(Break, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case IDENTIFIER:
			t = jj_consume_token(IDENTIFIER);
			words.add(Break+Identifier, t.image);
			break;
		default:
			;
		}
		t = jj_consume_token(SEMICOLON);
		words.add(Break+Semicolon, t.image);
		return words;
	}

	final public AnalysisData ContinueStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		t = jj_consume_token(CONTINUE);
		words.add(Continue, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case IDENTIFIER:
			t = jj_consume_token(IDENTIFIER);
			words.add(Continue+Identifier, t.image);
			break;
		default:
			;
		}
		t = jj_consume_token(SEMICOLON);
		words.add(Continue+Semicolon, t.image);
		return words;
	}

	final public AnalysisData ReturnStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(RETURN);
		words.add(Return, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case BANG:
		case TILDE:
		case INCR:
		case DECR:
		case PLUS:
		case MINUS:
			words.addAll(Expression());
			break;
		default:
			;
		}
		t = jj_consume_token(SEMICOLON);
		words.add(Return+Semicolon, t.image);
		return words;
	}

	final public AnalysisData ThrowStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(THROW);
		words.add(Throw, t.image);
		words.addAll(Expression());
		t = jj_consume_token(SEMICOLON);
		words.add(Throw+Semicolon, t.image);
		return words;
	}

	final public AnalysisData SynchronizedStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(SYNCHRONIZED);
		words.add(Sync, t.image);
		t = jj_consume_token(LPAREN);
		words.add(Sync+Lparen, t.image);
		words.addAll(Expression());
		t = jj_consume_token(RPAREN);
		words.add(Sync+Rparen, t.image);
		words.addAll(Block());
		return words;
	}

	final public AnalysisData TryStatement() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(TRY);
		words.add(Try, t.image);
		words.addAll(Block());
		words.add(Try+End, "end_try");
		label_44:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case CATCH:
					break;
				default:
					break label_44;
				}
				t = jj_consume_token(CATCH);
				words.add(Catch, t.image);
				t = jj_consume_token(LPAREN);
				words.add(Catch+Lparen, t.image);
				words.addAll(FormalParameter());
				t = jj_consume_token(RPAREN);
				words.add(Catch+Rparen, t.image);
				words.addAll(Block());
				words.add(Catch+End, "end_catch");
			}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case FINALLY:
			t = jj_consume_token(FINALLY);
			words.add(Finally, t.image);
			words.addAll(Block());
			words.add(Finally+End, "end_finally");
			break;
		default:
			;
		}
		return words;
	}

	/* We use productions to match >>>, >> and > so that we can keep the
	 * type declaration syntax with generics clean
	 */
	final public AnalysisData RUNSIGNEDSHIFT() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		if (getToken(1).kind == GT &&
				((Token.GTToken)getToken(1)).realKind == RUNSIGNEDSHIFT) {

		} else {
			jj_consume_token(-1);
			throw new ParserException();
		}
		t = jj_consume_token(GT);
		words.add(Runsign+Rshift3, t.image);
		t = jj_consume_token(GT);
		words.add(Runsign+Rshift2, t.image);
		t = jj_consume_token(GT);
		words.add(Runsign+Rshift1, t.image);
		return words;
	}

	final public AnalysisData RSIGNEDSHIFT() throws ParserException {
		AnalysisData words = new AnalysisData();
		Token t;
		if (getToken(1).kind == GT &&
				((Token.GTToken)getToken(1)).realKind == RSIGNEDSHIFT) {

		} else {
			jj_consume_token(-1);
			throw new ParserException();
		}
		t = jj_consume_token(GT);
		words.add(Rsign+Rshift2, t.image);
		t = jj_consume_token(GT);
		words.add(Rsign+Rshift1, t.image);
		return words;
	}

	/* Annotation syntax follows. */
	final public AnalysisData Annotation() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		if (jj_2_38(2147483647))
			words.addAll(NormalAnnotation());
		else if (jj_2_39(2147483647))
			words.addAll(SingleMemberAnnotation());
		else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case AT:
				words.addAll(MarkerAnnotation());
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		return words;
	}

	final public AnalysisData NormalAnnotation() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(AT);
		words.add(NormalAnn+At, t.image);
		words.addAll(Name());
		t = jj_consume_token(LPAREN);
		words.add(NormalAnn+Lparen, t.image);
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case IDENTIFIER:
			words.addAll(MemberValuePairs());
			break;
		default:
			;
		}
		t = jj_consume_token(RPAREN);
		words.add(NormalAnn+Rparen, t.image);
		return words;
	}

	final public AnalysisData MarkerAnnotation() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(AT);
		words.add(MarkerAnn+At, t.image);
		words.addAll(Name());
		return words;
	}

	final public AnalysisData SingleMemberAnnotation() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(AT);
		words.add(SingleMemAnn+At, t.image);
		words.addAll(Name());
		t = jj_consume_token(LPAREN);
		words.add(SingleMemAnn+Lparen, t.image);
		words.addAll(MemberValue());
		t = jj_consume_token(RPAREN);
		words.add(SingleMemAnn+Rparen, t.image);
		return words;
	}

	final public AnalysisData MemberValuePairs() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		MemberValuePair();
		label_45:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case COMMA:
					break;
				default:
					break label_45;
				}
				t = jj_consume_token(COMMA);
				words.add(MemPair+Comma, t.image);
				words.addAll(MemberValuePair());
			}
		return words;
	}

	final public AnalysisData MemberValuePair() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(IDENTIFIER);
		words.add(MemPair+Identifier, t.image);
		t = jj_consume_token(ASSIGN);
		words.add(MemPair+Equal, t.image);
		words.addAll(MemberValue());
		return words;
	}

	final public AnalysisData MemberValue() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case AT:
			words.addAll(Annotation());
			break;
		case LBRACE:
			words.addAll(MemberValueArrayInitializer());
			break;
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case DOUBLE:
		case FALSE:
		case FLOAT:
		case INT:
		case LONG:
		case NEW:
		case NULL:
		case SHORT:
		case SUPER:
		case THIS:
		case TRUE:
		case VOID:
		case INTEGER_LITERAL:
		case FLOATING_POINT_LITERAL:
		case CHARACTER_LITERAL:
		case STRING_LITERAL:
		case IDENTIFIER:
		case LPAREN:
		case BANG:
		case TILDE:
		case INCR:
		case DECR:
		case PLUS:
		case MINUS:
			words.addAll(ConditionalExpression());
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData MemberValueArrayInitializer() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(MemArray1+Lbrace, t.image);
		words.addAll(MemberValue());
		label_46:
			while (true) {
				if (!jj_2_40(2))
					break label_46;
				t = jj_consume_token(COMMA);
				words.add(MemArray1+Comma, t.image);
				words.addAll(MemberValue());
			}
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case COMMA:
			t = jj_consume_token(COMMA);
			words.add(MemArray2+Comma, t.image);
			break;
		default:
			;
		}
		t = jj_consume_token(RBRACE);
		words.add(MemArray1+Rbrace, t.image);
		return words;
	}

	/* Annotation Types. */
	final public AnalysisData AnnotationTypeDeclaration(int modifiers) throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(AT);
		words.add(AnnType+At, t.image);
		t = jj_consume_token(INTERFACE);
		words.add(AnnType+Interface, t.image);
		t = jj_consume_token(IDENTIFIER);
		words.add(AnnType+Identifier, t.image);
		words.addAll(AnnotationTypeBody());
		return words;
	}

	final public AnalysisData AnnotationTypeBody() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(LBRACE);
		words.add(AnnType+Lbrace, t.image);
		label_47:
			while (true) {
				switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
				case ABSTRACT:
				case BOOLEAN:
				case BYTE:
				case CHAR:
				case CLASS:
				case DOUBLE:
				case ENUM:
				case FINAL:
				case FLOAT:
				case INT:
				case INTERFACE:
				case LONG:
				case NATIVE:
				case PRIVATE:
				case PROTECTED:
				case PUBLIC:
				case SHORT:
				case STATIC:
				case STRICTFP:
				case SYNCHRONIZED:
				case TRANSIENT:
				case VOLATILE:
				case IDENTIFIER:
				case SEMICOLON:
				case AT:
					break;
				default:
					break label_47;
				}
				words.addAll(AnnotationTypeMemberDeclaration());
			}
		t = jj_consume_token(RBRACE);
		words.add(AnnType+Rbrace, t.image);
		return words;
	}

	final public AnalysisData AnnotationTypeMemberDeclaration() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		int modifiers = 0;
		switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
		case ABSTRACT:
		case BOOLEAN:
		case BYTE:
		case CHAR:
		case CLASS:
		case DOUBLE:
		case ENUM:
		case FINAL:
		case FLOAT:
		case INT:
		case INTERFACE:
		case LONG:
		case NATIVE:
		case PRIVATE:
		case PROTECTED:
		case PUBLIC:
		case SHORT:
		case STATIC:
		case STRICTFP:
		case SYNCHRONIZED:
		case TRANSIENT:
		case VOLATILE:
		case IDENTIFIER:
		case AT:
			label_48:
				while (true) {
					if (!jj_2_41(2))
						break label_48;
					switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
					case PUBLIC:
						t = jj_consume_token(PUBLIC);
						words.add(AnnTypeDec1+Public, t.image);
						modifiers |= ModifierSet.PUBLIC;
						break;
					case STATIC:
						t = jj_consume_token(STATIC);
						words.add(AnnTypeDec1+Static, t.image);
						modifiers |= ModifierSet.STATIC;
						break;
					case PROTECTED:
						t = jj_consume_token(PROTECTED);
						words.add(AnnTypeDec1+Protected, t.image);
						modifiers |= ModifierSet.PROTECTED;
						break;
					case PRIVATE:
						t = jj_consume_token(PRIVATE);
						words.add(AnnTypeDec1+Private, t.image);
						modifiers |= ModifierSet.PRIVATE;
						break;
					case FINAL:
						t = jj_consume_token(FINAL);
						words.add(AnnTypeDec1+Final, t.image);
						modifiers |= ModifierSet.FINAL;
						break;
					case ABSTRACT:
						t = jj_consume_token(ABSTRACT);
						words.add(AnnTypeDec1+Abstract, t.image);
						modifiers |= ModifierSet.ABSTRACT;
						break;
					case SYNCHRONIZED:
						t = jj_consume_token(SYNCHRONIZED);
						words.add(AnnTypeDec1+Synchronized, t.image);
						modifiers |= ModifierSet.SYNCHRONIZED;
						break;
					case NATIVE:
						t = jj_consume_token(NATIVE);
						words.add(AnnTypeDec1+Native, t.image);
						modifiers |= ModifierSet.NATIVE;
						break;
					case TRANSIENT:
						t = jj_consume_token(TRANSIENT);
						words.add(AnnTypeDec1+Transient, t.image);
						modifiers |= ModifierSet.TRANSIENT;
						break;
					case VOLATILE:
						t = jj_consume_token(VOLATILE);
						words.add(AnnTypeDec1+Volatile, t.image);
						modifiers |= ModifierSet.VOLATILE;
						break;
					case STRICTFP:
						t = jj_consume_token(STRICTFP);
						words.add(AnnTypeDec1+Strictfp, t.image);
						modifiers |= ModifierSet.STRICTFP;
						break;
					case AT:
						words.addAll(Annotation());
						break;
					default:
						jj_consume_token(-1);
					throw new ParserException();
					}
				}
		if (jj_2_42(2147483647)) {
			words.addAll(Type());
			t = jj_consume_token(IDENTIFIER);
			words.add(AnnTypeDec2+Identifier, t.image);
			t = jj_consume_token(LPAREN);
			words.add(AnnTypeDec2+Lparen, t.image);
			t = jj_consume_token(RPAREN);
			words.add(AnnTypeDec2+Rparen, t.image);
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case _DEFAULT:
				words.addAll(DefaultValue());
				break;
			default:
				;
			}
			t = jj_consume_token(SEMICOLON);
			words.add(AnnTypeDec2+Semicolon, t.image);
		} else {
			switch ((jj_ntk==-1)?jj_ntk():jj_ntk) {
			case CLASS:
			case INTERFACE:
				words.addAll(ClassOrInterfaceDeclaration(modifiers));
				break;
			case ENUM:
				words.addAll(EnumDeclaration(modifiers));
				break;
			case AT:
				words.addAll(AnnotationTypeDeclaration(modifiers));
				break;
			case BOOLEAN:
			case BYTE:
			case CHAR:
			case DOUBLE:
			case FLOAT:
			case INT:
			case LONG:
			case SHORT:
			case IDENTIFIER:
				words.addAll(FieldDeclaration(modifiers));
				break;
			default:
				jj_consume_token(-1);
				throw new ParserException();
			}
		}
		break;
		case SEMICOLON:
			t = jj_consume_token(SEMICOLON);
			words.add(AnnTypeDec1+Semicolon, t.image);
			break;
		default:
			jj_consume_token(-1);
			throw new ParserException();
		}
		return words;
	}

	final public AnalysisData DefaultValue() throws ParserException {
		AnalysisData words = new AnalysisData();
		
		Token t;
		t = jj_consume_token(_DEFAULT);
		words.add(DefaultVal, t.image);
		words.addAll(MemberValue());
		return words;
	}


	/**************************************************************************
	 * jj
	 **************************************************************************/
	final private boolean jj_2_1(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_1(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_2(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_2(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_3(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_3(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_4(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_4(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_5(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_5(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_6(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_6(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_7(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_7(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_8(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_8(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_9(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_9(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_10(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_10(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_11(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_11(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_12(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_12(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_13(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_13(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_14(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_14(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_15(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_15(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_16(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_16(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_17(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_17(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_18(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_18(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_19(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_19(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_20(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_20(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_21(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_21(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_22(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_22(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_23(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_23(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_24(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_24(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_25(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_25(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_26(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_26(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_27(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_27(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_28(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_28(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_29(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_29(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_30(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_30(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_31(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_31(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_32(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_32(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_33(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_33(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_34(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_34(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_35(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_35(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_36(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_36(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_37(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_37(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_38(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_38(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_39(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_39(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_40(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_40(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_41(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_41(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_2_42(int xla) {
		jj_la = xla; jj_lastpos = jj_scanpos = token;
		try { return !jj_3_42(); }
		catch(LookaheadSuccess ls) { return true; }
	}

	final private boolean jj_3R_67() {
		if (jj_scan_token(SYNCHRONIZED)) return true;
		return false;
	}

	final private boolean jj_3R_66() {
		if (jj_scan_token(ABSTRACT)) return true;
		return false;
	}

	final private boolean jj_3R_249() {
		if (jj_scan_token(TRY)) return true;
		if (jj_3R_114()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_377()) { jj_scanpos = xsp; break; }
		}
		xsp = jj_scanpos;
		if (jj_3R_378()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3_22() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_89()) return true;
		return false;
	}

	final private boolean jj_3R_65() {
		if (jj_scan_token(FINAL)) return true;
		return false;
	}

	final private boolean jj_3R_88() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_22()) {
			jj_scanpos = xsp;
			if (jj_3R_139()) {
				jj_scanpos = xsp;
				if (jj_3R_140()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_64() {
		if (jj_scan_token(PRIVATE)) return true;
		return false;
	}

	final private boolean jj_3_21() {
		if (jj_3R_88()) return true;
		return false;
	}

	final private boolean jj_3R_63() {
		if (jj_scan_token(PROTECTED)) return true;
		return false;
	}

	final private boolean jj_3R_62() {
		if (jj_scan_token(STATIC)) return true;
		return false;
	}

	final private boolean jj_3R_61() {
		if (jj_scan_token(PUBLIC)) return true;
		return false;
	}

	final private boolean jj_3R_332() {
		if (jj_3R_349()) return true;
		return false;
	}

	final private boolean jj_3_2() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_61()) {
			jj_scanpos = xsp;
			if (jj_3R_62()) {
				jj_scanpos = xsp;
				if (jj_3R_63()) {
					jj_scanpos = xsp;
					if (jj_3R_64()) {
						jj_scanpos = xsp;
						if (jj_3R_65()) {
							jj_scanpos = xsp;
							if (jj_3R_66()) {
								jj_scanpos = xsp;
								if (jj_3R_67()) {
									jj_scanpos = xsp;
									if (jj_3R_68()) {
										jj_scanpos = xsp;
										if (jj_3R_69()) {
											jj_scanpos = xsp;
											if (jj_3R_70()) {
												jj_scanpos = xsp;
												if (jj_3R_71()) {
													jj_scanpos = xsp;
													if (jj_3R_72()) return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_331() {
		if (jj_3R_348()) return true;
		return false;
	}

	final private boolean jj_3R_285() {
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_2()) { jj_scanpos = xsp; break; }
		}
		xsp = jj_scanpos;
		if (jj_3R_289()) {
			jj_scanpos = xsp;
			if (jj_3R_290()) {
				jj_scanpos = xsp;
				if (jj_3R_291()) {
					jj_scanpos = xsp;
					if (jj_3R_292()) {
						jj_scanpos = xsp;
						if (jj_3R_293()) return true;
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_347() {
		if (jj_scan_token(BANG)) return true;
		return false;
	}

	final private boolean jj_3R_248() {
		if (jj_scan_token(SYNCHRONIZED)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_346() {
		if (jj_scan_token(TILDE)) return true;
		return false;
	}

	final private boolean jj_3R_330() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_346()) {
			jj_scanpos = xsp;
			if (jj_3R_347()) return true;
		}
		if (jj_3R_288()) return true;
		return false;
	}

	final private boolean jj_3_5() {
		if (jj_3R_76()) return true;
		return false;
	}

	final private boolean jj_3R_309() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_330()) {
			jj_scanpos = xsp;
			if (jj_3R_331()) {
				jj_scanpos = xsp;
				if (jj_3R_332()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_280() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_5()) {
			jj_scanpos = xsp;
			if (jj_3R_285()) {
				jj_scanpos = xsp;
				if (jj_3R_286()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_247() {
		if (jj_scan_token(THROW)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_263() {
		if (jj_scan_token(DECR)) return true;
		if (jj_3R_79()) return true;
		return false;
	}

	final private boolean jj_3R_277() {
		if (jj_3R_280()) return true;
		return false;
	}

	final private boolean jj_3R_270() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_277()) { jj_scanpos = xsp; break; }
		}
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_376() {
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_246() {
		if (jj_scan_token(RETURN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_376()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_262() {
		if (jj_scan_token(INCR)) return true;
		if (jj_3R_79()) return true;
		return false;
	}

	final private boolean jj_3R_205() {
		if (jj_scan_token(BIT_AND)) return true;
		if (jj_3R_173()) return true;
		return false;
	}

	final private boolean jj_3R_194() {
		if (jj_scan_token(EXTENDS)) return true;
		if (jj_3R_173()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_205()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_375() {
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_301() {
		if (jj_3R_309()) return true;
		return false;
	}

	final private boolean jj_3R_300() {
		if (jj_3R_263()) return true;
		return false;
	}

	final private boolean jj_3R_245() {
		if (jj_scan_token(CONTINUE)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_375()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_299() {
		if (jj_3R_262()) return true;
		return false;
	}

	final private boolean jj_3R_189() {
		if (jj_3R_194()) return true;
		return false;
	}

	final private boolean jj_3R_308() {
		if (jj_scan_token(MINUS)) return true;
		return false;
	}

	final private boolean jj_3R_307() {
		if (jj_scan_token(PLUS)) return true;
		return false;
	}

	final private boolean jj_3R_161() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_189()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_298() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_307()) {
			jj_scanpos = xsp;
			if (jj_3R_308()) return true;
		}
		if (jj_3R_288()) return true;
		return false;
	}

	final private boolean jj_3R_288() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_298()) {
			jj_scanpos = xsp;
			if (jj_3R_299()) {
				jj_scanpos = xsp;
				if (jj_3R_300()) {
					jj_scanpos = xsp;
					if (jj_3R_301()) return true;
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_374() {
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_244() {
		if (jj_scan_token(BREAK)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_374()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_345() {
		if (jj_scan_token(REM)) return true;
		return false;
	}

	final private boolean jj_3R_162() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_161()) return true;
		return false;
	}

	final private boolean jj_3R_344() {
		if (jj_scan_token(SLASH)) return true;
		return false;
	}

	final private boolean jj_3R_343() {
		if (jj_scan_token(STAR)) return true;
		return false;
	}

	final private boolean jj_3R_327() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_343()) {
			jj_scanpos = xsp;
			if (jj_3R_344()) {
				jj_scanpos = xsp;
				if (jj_3R_345()) return true;
			}
		}
		if (jj_3R_288()) return true;
		return false;
	}

	final private boolean jj_3R_111() {
		if (jj_scan_token(LT)) return true;
		if (jj_3R_161()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_162()) { jj_scanpos = xsp; break; }
		}
		if (jj_scan_token(GT)) return true;
		return false;
	}

	final private boolean jj_3R_394() {
		if (jj_3R_397()) return true;
		return false;
	}

	final private boolean jj_3R_284() {
		if (jj_3R_288()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_327()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_353() {
		if (jj_3R_270()) return true;
		return false;
	}

	final private boolean jj_3R_352() {
		if (jj_3R_80()) return true;
		return false;
	}

	final private boolean jj_3R_329() {
		if (jj_scan_token(MINUS)) return true;
		return false;
	}

	final private boolean jj_3R_398() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_238()) return true;
		return false;
	}

	final private boolean jj_3R_328() {
		if (jj_scan_token(PLUS)) return true;
		return false;
	}

	final private boolean jj_3R_335() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_352()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_353()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_306() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_328()) {
			jj_scanpos = xsp;
			if (jj_3R_329()) return true;
		}
		if (jj_3R_284()) return true;
		return false;
	}

	final private boolean jj_3R_397() {
		if (jj_3R_238()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_398()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_279() {
		if (jj_3R_284()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_306()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3_37() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(31)) jj_scanpos = xsp;
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_354() {
		if (jj_3R_280()) return true;
		return false;
	}

	final private boolean jj_3R_337() {
		if (jj_scan_token(SEMICOLON)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_354()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3_20() {
		if (jj_3R_87()) return true;
		return false;
	}

	final private boolean jj_3_19() {
		if (jj_3R_86()) return true;
		return false;
	}

	final private boolean jj_3R_396() {
		if (jj_3R_397()) return true;
		return false;
	}

	final private boolean jj_3R_336() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_335()) return true;
		return false;
	}

	final private boolean jj_3R_85() {
		if (jj_scan_token(LSHIFT)) return true;
		return false;
	}

	final private boolean jj_3R_395() {
		if (jj_3R_206()) return true;
		return false;
	}

	final private boolean jj_3_18() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_85()) {
			jj_scanpos = xsp;
			if (jj_3_19()) {
				jj_scanpos = xsp;
				if (jj_3_20()) return true;
			}
		}
		if (jj_3R_279()) return true;
		return false;
	}

	final private boolean jj_3R_393() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_395()) {
			jj_scanpos = xsp;
			if (jj_3R_396()) return true;
		}
		return false;
	}

	final private boolean jj_3R_314() {
		if (jj_scan_token(LBRACE)) return true;
		if (jj_3R_335()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_336()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_337()) jj_scanpos = xsp;
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_272() {
		if (jj_3R_279()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_18()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_297() {
		if (jj_scan_token(GE)) return true;
		return false;
	}

	final private boolean jj_3R_387() {
		if (jj_3R_394()) return true;
		return false;
	}

	final private boolean jj_3R_313() {
		if (jj_3R_334()) return true;
		return false;
	}

	final private boolean jj_3R_296() {
		if (jj_scan_token(LE)) return true;
		return false;
	}

	final private boolean jj_3R_295() {
		if (jj_scan_token(GT)) return true;
		return false;
	}

	final private boolean jj_3R_386() {
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_294() {
		if (jj_scan_token(LT)) return true;
		return false;
	}

	final private boolean jj_3_36() {
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(COLON)) return true;
		return false;
	}

	final private boolean jj_3R_385() {
		if (jj_3R_393()) return true;
		return false;
	}

	final private boolean jj_3R_302() {
		if (jj_scan_token(ENUM)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_313()) jj_scanpos = xsp;
		if (jj_3R_314()) return true;
		return false;
	}

	final private boolean jj_3R_287() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_294()) {
			jj_scanpos = xsp;
			if (jj_3R_295()) {
				jj_scanpos = xsp;
				if (jj_3R_296()) {
					jj_scanpos = xsp;
					if (jj_3R_297()) return true;
				}
			}
		}
		if (jj_3R_272()) return true;
		return false;
	}

	final private boolean jj_3R_373() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_385()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		xsp = jj_scanpos;
		if (jj_3R_386()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		xsp = jj_scanpos;
		if (jj_3R_387()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_267() {
		if (jj_3R_272()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_287()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_372() {
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(COLON)) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_351() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_173()) return true;
		return false;
	}

	final private boolean jj_3R_281() {
		if (jj_scan_token(INSTANCEOF)) return true;
		if (jj_3R_74()) return true;
		return false;
	}

	final private boolean jj_3R_260() {
		if (jj_3R_267()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_281()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_334() {
		if (jj_scan_token(IMPLEMENTS)) return true;
		if (jj_3R_173()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_351()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_243() {
		if (jj_scan_token(FOR)) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_372()) {
			jj_scanpos = xsp;
			if (jj_3R_373()) return true;
		}
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_207()) return true;
		return false;
	}

	final private boolean jj_3R_283() {
		if (jj_scan_token(NE)) return true;
		return false;
	}

	final private boolean jj_3R_282() {
		if (jj_scan_token(EQ)) return true;
		return false;
	}

	final private boolean jj_3R_278() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_282()) {
			jj_scanpos = xsp;
			if (jj_3R_283()) return true;
		}
		if (jj_3R_260()) return true;
		return false;
	}

	final private boolean jj_3R_251() {
		if (jj_3R_260()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_278()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_350() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_173()) return true;
		return false;
	}

	final private boolean jj_3R_333() {
		if (jj_scan_token(EXTENDS)) return true;
		if (jj_3R_173()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_350()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_242() {
		if (jj_scan_token(DO)) return true;
		if (jj_3R_207()) return true;
		if (jj_scan_token(WHILE)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_271() {
		if (jj_scan_token(BIT_AND)) return true;
		if (jj_3R_251()) return true;
		return false;
	}

	final private boolean jj_3R_234() {
		if (jj_3R_251()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_271()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_312() {
		if (jj_3R_334()) return true;
		return false;
	}

	final private boolean jj_3R_311() {
		if (jj_3R_333()) return true;
		return false;
	}

	final private boolean jj_3R_310() {
		if (jj_3R_111()) return true;
		return false;
	}

	final private boolean jj_3R_266() {
		if (jj_scan_token(XOR)) return true;
		if (jj_3R_234()) return true;
		return false;
	}

	final private boolean jj_3R_230() {
		if (jj_scan_token(INTERFACE)) return true;
		return false;
	}

	final private boolean jj_3R_212() {
		if (jj_3R_234()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_266()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_229() {
		if (jj_scan_token(CLASS)) return true;
		return false;
	}

	final private boolean jj_3R_241() {
		if (jj_scan_token(WHILE)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_207()) return true;
		return false;
	}

	final private boolean jj_3R_208() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_229()) {
			jj_scanpos = xsp;
			if (jj_3R_230()) return true;
		}
		if (jj_scan_token(IDENTIFIER)) return true;
		xsp = jj_scanpos;
		if (jj_3R_310()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_311()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_312()) jj_scanpos = xsp;
		if (jj_3R_270()) return true;
		return false;
	}

	final private boolean jj_3R_259() {
		if (jj_scan_token(BIT_OR)) return true;
		if (jj_3R_212()) return true;
		return false;
	}

	final private boolean jj_3R_204() {
		if (jj_3R_212()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_259()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_371() {
		if (jj_scan_token(ELSE)) return true;
		if (jj_3R_207()) return true;
		return false;
	}

	final private boolean jj_3R_250() {
		if (jj_scan_token(SC_AND)) return true;
		if (jj_3R_204()) return true;
		return false;
	}

	final private boolean jj_3R_60() {
		if (jj_3R_110()) return true;
		return false;
	}

	final private boolean jj_3R_59() {
		if (jj_scan_token(STRICTFP)) return true;
		return false;
	}

	final private boolean jj_3R_193() {
		if (jj_3R_204()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_250()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_240() {
		if (jj_scan_token(IF)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_207()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_371()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_58() {
		if (jj_scan_token(VOLATILE)) return true;
		return false;
	}

	final private boolean jj_3R_57() {
		if (jj_scan_token(TRANSIENT)) return true;
		return false;
	}

	final private boolean jj_3R_56() {
		if (jj_scan_token(NATIVE)) return true;
		return false;
	}

	final private boolean jj_3R_233() {
		if (jj_scan_token(SC_OR)) return true;
		if (jj_3R_193()) return true;
		return false;
	}

	final private boolean jj_3R_55() {
		if (jj_scan_token(SYNCHRONIZED)) return true;
		return false;
	}

	final private boolean jj_3R_176() {
		if (jj_3R_193()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_233()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_54() {
		if (jj_scan_token(ABSTRACT)) return true;
		return false;
	}

	final private boolean jj_3R_53() {
		if (jj_scan_token(FINAL)) return true;
		return false;
	}

	final private boolean jj_3R_392() {
		if (jj_scan_token(_DEFAULT)) return true;
		if (jj_scan_token(COLON)) return true;
		return false;
	}

	final private boolean jj_3R_52() {
		if (jj_scan_token(PRIVATE)) return true;
		return false;
	}

	final private boolean jj_3R_51() {
		if (jj_scan_token(PROTECTED)) return true;
		return false;
	}

	final private boolean jj_3R_391() {
		if (jj_scan_token(CASE)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(COLON)) return true;
		return false;
	}

	final private boolean jj_3R_211() {
		if (jj_scan_token(HOOK)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(COLON)) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_50() {
		if (jj_scan_token(STATIC)) return true;
		return false;
	}

	final private boolean jj_3R_383() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_391()) {
			jj_scanpos = xsp;
			if (jj_3R_392()) return true;
		}
		return false;
	}

	final private boolean jj_3R_136() {
		if (jj_3R_176()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_211()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_49() {
		if (jj_scan_token(PUBLIC)) return true;
		return false;
	}

	final private boolean jj_3_1() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_49()) {
			jj_scanpos = xsp;
			if (jj_3R_50()) {
				jj_scanpos = xsp;
				if (jj_3R_51()) {
					jj_scanpos = xsp;
					if (jj_3R_52()) {
						jj_scanpos = xsp;
						if (jj_3R_53()) {
							jj_scanpos = xsp;
							if (jj_3R_54()) {
								jj_scanpos = xsp;
								if (jj_3R_55()) {
									jj_scanpos = xsp;
									if (jj_3R_56()) {
										jj_scanpos = xsp;
										if (jj_3R_57()) {
											jj_scanpos = xsp;
											if (jj_3R_58()) {
												jj_scanpos = xsp;
												if (jj_3R_59()) {
													jj_scanpos = xsp;
													if (jj_3R_60()) return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_384() {
		if (jj_3R_190()) return true;
		return false;
	}

	final private boolean jj_3R_135() {
		if (jj_scan_token(ORASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_370() {
		if (jj_3R_383()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_384()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_134() {
		if (jj_scan_token(XORASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_133() {
		if (jj_scan_token(ANDASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_132() {
		if (jj_scan_token(RUNSIGNEDSHIFTASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_131() {
		if (jj_scan_token(RSIGNEDSHIFTASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_130() {
		if (jj_scan_token(LSHIFTASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_129() {
		if (jj_scan_token(MINUSASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_239() {
		if (jj_scan_token(SWITCH)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_370()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_128() {
		if (jj_scan_token(PLUSASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_127() {
		if (jj_scan_token(REMASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_126() {
		if (jj_scan_token(SLASHASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_125() {
		if (jj_scan_token(STARASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_124() {
		if (jj_scan_token(ASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_390() {
		if (jj_3R_83()) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_83() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_124()) {
			jj_scanpos = xsp;
			if (jj_3R_125()) {
				jj_scanpos = xsp;
				if (jj_3R_126()) {
					jj_scanpos = xsp;
					if (jj_3R_127()) {
						jj_scanpos = xsp;
						if (jj_3R_128()) {
							jj_scanpos = xsp;
							if (jj_3R_129()) {
								jj_scanpos = xsp;
								if (jj_3R_130()) {
									jj_scanpos = xsp;
									if (jj_3R_131()) {
										jj_scanpos = xsp;
										if (jj_3R_132()) {
											jj_scanpos = xsp;
											if (jj_3R_133()) {
												jj_scanpos = xsp;
												if (jj_3R_134()) {
													jj_scanpos = xsp;
													if (jj_3R_135()) return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_389() {
		if (jj_scan_token(DECR)) return true;
		return false;
	}

	final private boolean jj_3R_388() {
		if (jj_scan_token(INCR)) return true;
		return false;
	}

	final private boolean jj_3R_382() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_388()) {
			jj_scanpos = xsp;
			if (jj_3R_389()) {
				jj_scanpos = xsp;
				if (jj_3R_390()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_254() {
		if (jj_3R_79()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_382()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_253() {
		if (jj_3R_263()) return true;
		return false;
	}

	final private boolean jj_3_17() {
		if (jj_3R_83()) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_252() {
		if (jj_3R_262()) return true;
		return false;
	}

	final private boolean jj_3R_84() {
		if (jj_3R_136()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_17()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_238() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_252()) {
			jj_scanpos = xsp;
			if (jj_3R_253()) {
				jj_scanpos = xsp;
				if (jj_3R_254()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_237() {
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_357() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_95()) return true;
		return false;
	}

	final private boolean jj_3R_364() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_320()) return true;
		return false;
	}

	final private boolean jj_3R_339() {
		if (jj_3R_95()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_357()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_213() {
		if (jj_scan_token(FINAL)) return true;
		return false;
	}

	final private boolean jj_3R_206() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_213()) jj_scanpos = xsp;
		if (jj_3R_74()) return true;
		if (jj_3R_320()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_364()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3_35() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_scan_token(31)) jj_scanpos = xsp;
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3_16() {
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_197() {
		if (jj_3R_208()) return true;
		return false;
	}

	final private boolean jj_3R_95() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_16()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_196() {
		if (jj_3R_207()) return true;
		return false;
	}

	final private boolean jj_3R_195() {
		if (jj_3R_206()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_190() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_195()) {
			jj_scanpos = xsp;
			if (jj_3R_196()) {
				jj_scanpos = xsp;
				if (jj_3R_197()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_153() {
		if (jj_3R_74()) return true;
		return false;
	}

	final private boolean jj_3R_163() {
		if (jj_3R_190()) return true;
		return false;
	}

	final private boolean jj_3R_152() {
		if (jj_scan_token(VOID)) return true;
		return false;
	}

	final private boolean jj_3R_114() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_163()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_91() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_152()) {
			jj_scanpos = xsp;
			if (jj_3R_153()) return true;
		}
		return false;
	}

	final private boolean jj_3R_148() {
		if (jj_scan_token(DOUBLE)) return true;
		return false;
	}

	final private boolean jj_3R_147() {
		if (jj_scan_token(FLOAT)) return true;
		return false;
	}

	final private boolean jj_3R_94() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(COLON)) return true;
		if (jj_3R_207()) return true;
		return false;
	}

	final private boolean jj_3R_146() {
		if (jj_scan_token(LONG)) return true;
		return false;
	}

	final private boolean jj_3R_145() {
		if (jj_scan_token(INT)) return true;
		return false;
	}

	final private boolean jj_3R_144() {
		if (jj_scan_token(SHORT)) return true;
		return false;
	}

	final private boolean jj_3R_143() {
		if (jj_scan_token(BYTE)) return true;
		return false;
	}

	final private boolean jj_3R_369() {
		if (jj_scan_token(COLON)) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_142() {
		if (jj_scan_token(CHAR)) return true;
		return false;
	}

	final private boolean jj_3R_141() {
		if (jj_scan_token(BOOLEAN)) return true;
		return false;
	}

	final private boolean jj_3R_236() {
		if (jj_scan_token(ASSERT)) return true;
		if (jj_3R_84()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_369()) jj_scanpos = xsp;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_89() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_141()) {
			jj_scanpos = xsp;
			if (jj_3R_142()) {
				jj_scanpos = xsp;
				if (jj_3R_143()) {
					jj_scanpos = xsp;
					if (jj_3R_144()) {
						jj_scanpos = xsp;
						if (jj_3R_145()) {
							jj_scanpos = xsp;
							if (jj_3R_146()) {
								jj_scanpos = xsp;
								if (jj_3R_147()) {
									jj_scanpos = xsp;
									if (jj_3R_148()) return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_228() {
		if (jj_3R_249()) return true;
		return false;
	}

	final private boolean jj_3R_274() {
		if (jj_scan_token(SUPER)) return true;
		if (jj_3R_81()) return true;
		return false;
	}

	final private boolean jj_3R_227() {
		if (jj_3R_248()) return true;
		return false;
	}

	final private boolean jj_3R_226() {
		if (jj_3R_247()) return true;
		return false;
	}

	final private boolean jj_3R_273() {
		if (jj_scan_token(EXTENDS)) return true;
		if (jj_3R_81()) return true;
		return false;
	}

	final private boolean jj_3R_225() {
		if (jj_3R_246()) return true;
		return false;
	}


	final private boolean jj_3R_224() {
		if (jj_3R_245()) return true;
		return false;
	}

	final private boolean jj_3R_268() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_273()) {
			jj_scanpos = xsp;
			if (jj_3R_274()) return true;
		}
		return false;
	}

	final private boolean jj_3R_223() {
		if (jj_3R_244()) return true;
		return false;
	}

	final private boolean jj_3R_222() {
		if (jj_3R_243()) return true;
		return false;
	}

	final private boolean jj_3R_221() {
		if (jj_3R_242()) return true;
		return false;
	}

	final private boolean jj_3R_261() {
		if (jj_3R_268()) return true;
		return false;
	}

	final private boolean jj_3R_220() {
		if (jj_3R_241()) return true;
		return false;
	}

	final private boolean jj_3R_219() {
		if (jj_3R_240()) return true;
		return false;
	}

	final private boolean jj_3R_175() {
		if (jj_scan_token(HOOK)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_261()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_218() {
		if (jj_3R_239()) return true;
		return false;
	}

	final private boolean jj_3R_174() {
		if (jj_3R_81()) return true;
		return false;
	}

	final private boolean jj_3R_217() {
		if (jj_3R_238()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_123() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_174()) {
			jj_scanpos = xsp;
			if (jj_3R_175()) return true;
		}
		return false;
	}

	final private boolean jj_3R_216() {
		if (jj_3R_237()) return true;
		return false;
	}

	final private boolean jj_3R_215() {
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_214() {
		if (jj_3R_236()) return true;
		return false;
	}

	final private boolean jj_3_34() {
		if (jj_3R_94()) return true;
		return false;
	}

	final private boolean jj_3R_235() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_123()) return true;
		return false;
	}

	final private boolean jj_3R_207() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_34()) {
			jj_scanpos = xsp;
			if (jj_3R_214()) {
				jj_scanpos = xsp;
				if (jj_3R_215()) {
					jj_scanpos = xsp;
					if (jj_3R_216()) {
						jj_scanpos = xsp;
						if (jj_3R_217()) {
							jj_scanpos = xsp;
							if (jj_3R_218()) {
								jj_scanpos = xsp;
								if (jj_3R_219()) {
									jj_scanpos = xsp;
									if (jj_3R_220()) {
										jj_scanpos = xsp;
										if (jj_3R_221()) {
											jj_scanpos = xsp;
											if (jj_3R_222()) {
												jj_scanpos = xsp;
												if (jj_3R_223()) {
													jj_scanpos = xsp;
													if (jj_3R_224()) {
														jj_scanpos = xsp;
														if (jj_3R_225()) {
															jj_scanpos = xsp;
															if (jj_3R_226()) {
																jj_scanpos = xsp;
																if (jj_3R_227()) {
																	jj_scanpos = xsp;
																	if (jj_3R_228()) return true;
																}
															}
														}
													}
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_82() {
		if (jj_scan_token(LT)) return true;
		if (jj_3R_123()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_235()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(GT)) return true;
		return false;
	}

	final private boolean jj_3_15() {
		if (jj_3R_82()) return true;
		return false;
	}

	final private boolean jj_3R_269() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_264() {
		Token xsp;
		if (jj_3R_269()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_269()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_3R_164()) return true;
		return false;
	}

	final private boolean jj_3_14() {
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_15()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3_13() {
		if (jj_3R_82()) return true;
		return false;
	}

	final private boolean jj_3_32() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_173() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_13()) jj_scanpos = xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_14()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3_31() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3_33() {
		Token xsp;
		if (jj_3_31()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_31()) {
				jj_scanpos = xsp;
				break;
			}
		}
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_32()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_255() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_33()) {
			jj_scanpos = xsp;
			if (jj_3R_264()) return true;
		}
		return false;
	}

	final private boolean jj_3_12() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_122() {
		if (jj_3R_173()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_12()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_265() {
		if (jj_3R_270()) return true;
		return false;
	}

	final private boolean jj_3_11() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3_42() {
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(LPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_258() {
		if (jj_3R_80()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_265()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_121() {
		if (jj_3R_89()) return true;
		Token xsp;
		if (jj_3_11()) return true;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_11()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_257() {
		if (jj_3R_255()) return true;
		return false;
	}

	final private boolean jj_3R_81() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_121()) {
			jj_scanpos = xsp;
			if (jj_3R_122()) return true;
		}
		return false;
	}

	final private boolean jj_3R_256() {
		if (jj_3R_82()) return true;
		return false;
	}

	final private boolean jj_3R_154() {
		if (jj_scan_token(NEW)) return true;
		if (jj_3R_173()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_256()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_257()) {
			jj_scanpos = xsp;
			if (jj_3R_258()) return true;
		}
		return false;
	}

	final private boolean jj_3R_112() {
		if (jj_3R_89()) return true;
		return false;
	}

	final private boolean jj_3R_109() {
		if (jj_3R_110()) return true;
		return false;
	}

	final private boolean jj_3_10() {
		if (jj_3R_81()) return true;
		return false;
	}

	final private boolean jj_3_30() {
		if (jj_scan_token(NEW)) return true;
		if (jj_3R_89()) return true;
		if (jj_3R_255()) return true;
		return false;
	}

	final private boolean jj_3R_108() {
		if (jj_scan_token(STRICTFP)) return true;
		return false;
	}

	final private boolean jj_3R_92() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_30()) {
			jj_scanpos = xsp;
			if (jj_3R_154()) return true;
		}
		return false;
	}

	final private boolean jj_3R_107() {
		if (jj_scan_token(VOLATILE)) return true;
		return false;
	}

	final private boolean jj_3R_74() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_10()) {
			jj_scanpos = xsp;
			if (jj_3R_112()) return true;
		}
		return false;
	}

	final private boolean jj_3R_106() {
		if (jj_scan_token(TRANSIENT)) return true;
		return false;
	}

	final private boolean jj_3R_105() {
		if (jj_scan_token(NATIVE)) return true;
		return false;
	}

	final private boolean jj_3R_192() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_104() {
		if (jj_scan_token(SYNCHRONIZED)) return true;
		return false;
	}

	final private boolean jj_3R_172() {
		if (jj_3R_84()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_192()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_103() {
		if (jj_scan_token(ABSTRACT)) return true;
		return false;
	}

	final private boolean jj_3R_113() {
		if (jj_scan_token(STATIC)) return true;
		return false;
	}

	final private boolean jj_3R_102() {
		if (jj_scan_token(FINAL)) return true;
		return false;
	}

	final private boolean jj_3R_76() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_113()) jj_scanpos = xsp;
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_101() {
		if (jj_scan_token(PRIVATE)) return true;
		return false;
	}

	final private boolean jj_3R_120() {
		if (jj_3R_172()) return true;
		return false;
	}

	final private boolean jj_3R_100() {
		if (jj_scan_token(PROTECTED)) return true;
		return false;
	}

	final private boolean jj_3R_99() {
		if (jj_scan_token(STATIC)) return true;
		return false;
	}

	final private boolean jj_3R_80() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_120()) jj_scanpos = xsp;
		if (jj_scan_token(RPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_98() {
		if (jj_scan_token(PUBLIC)) return true;
		return false;
	}

	final private boolean jj_3_41() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_98()) {
			jj_scanpos = xsp;
			if (jj_3R_99()) {
				jj_scanpos = xsp;
				if (jj_3R_100()) {
					jj_scanpos = xsp;
					if (jj_3R_101()) {
						jj_scanpos = xsp;
						if (jj_3R_102()) {
							jj_scanpos = xsp;
							if (jj_3R_103()) {
								jj_scanpos = xsp;
								if (jj_3R_104()) {
									jj_scanpos = xsp;
									if (jj_3R_105()) {
										jj_scanpos = xsp;
										if (jj_3R_106()) {
											jj_scanpos = xsp;
											if (jj_3R_107()) {
												jj_scanpos = xsp;
												if (jj_3R_108()) {
													jj_scanpos = xsp;
													if (jj_3R_109()) return true;
												}
											}
										}
									}
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3_9() {
		if (jj_scan_token(THIS)) return true;
		if (jj_3R_80()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3_8() {
		if (jj_3R_79()) return true;
		if (jj_scan_token(DOT)) return true;
		return false;
	}

	final private boolean jj_3R_118() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_8()) jj_scanpos = xsp;
		if (jj_scan_token(SUPER)) return true;
		if (jj_3R_80()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_210() {
		if (jj_scan_token(NULL)) return true;
		return false;
	}

	final private boolean jj_3R_117() {
		if (jj_scan_token(THIS)) return true;
		if (jj_3R_80()) return true;
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3_7() {
		if (jj_3R_78()) return true;
		return false;
	}

	final private boolean jj_3R_78() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_117()) {
			jj_scanpos = xsp;
			if (jj_3R_118()) return true;
		}
		return false;
	}

	final private boolean jj_3R_232() {
		if (jj_scan_token(FALSE)) return true;
		return false;
	}

	final private boolean jj_3R_319() {
		if (jj_3R_190()) return true;
		return false;
	}

	final private boolean jj_3R_231() {
		if (jj_scan_token(TRUE)) return true;
		return false;
	}

	final private boolean jj_3R_318() {
		if (jj_3R_78()) return true;
		return false;
	}

	final private boolean jj_3R_209() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_231()) {
			jj_scanpos = xsp;
			if (jj_3R_232()) return true;
		}
		return false;
	}

	final private boolean jj_3R_317() {
		if (jj_scan_token(THROWS)) return true;
		if (jj_3R_339()) return true;
		return false;
	}

	final private boolean jj_3R_203() {
		if (jj_3R_210()) return true;
		return false;
	}

	final private boolean jj_3R_315() {
		if (jj_3R_111()) return true;
		return false;
	}

	final private boolean jj_3R_202() {
		if (jj_3R_209()) return true;
		return false;
	}

	final private boolean jj_3R_303() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_315()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_3R_316()) return true;
		xsp = jj_scanpos;
		if (jj_3R_317()) jj_scanpos = xsp;
		if (jj_scan_token(LBRACE)) return true;
		xsp = jj_scanpos;
		if (jj_3R_318()) jj_scanpos = xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_319()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_201() {
		if (jj_scan_token(STRING_LITERAL)) return true;
		return false;
	}

	final private boolean jj_3R_200() {
		if (jj_scan_token(CHARACTER_LITERAL)) return true;
		return false;
	}

	final private boolean jj_3R_199() {
		if (jj_scan_token(FLOATING_POINT_LITERAL)) return true;
		return false;
	}

	final private boolean jj_3R_379() {
		if (jj_scan_token(COMMA)) return true;
		return false;
	}

	final private boolean jj_3R_363() {
		if (jj_scan_token(ELLIPSIS)) return true;
		return false;
	}

	final private boolean jj_3R_198() {
		if (jj_scan_token(INTEGER_LITERAL)) return true;
		return false;
	}

	final private boolean jj_3R_362() {
		if (jj_scan_token(FINAL)) return true;
		return false;
	}

	final private boolean jj_3R_191() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_198()) {
			jj_scanpos = xsp;
			if (jj_3R_199()) {
				jj_scanpos = xsp;
				if (jj_3R_200()) {
					jj_scanpos = xsp;
					if (jj_3R_201()) {
						jj_scanpos = xsp;
						if (jj_3R_202()) {
							jj_scanpos = xsp;
							if (jj_3R_203()) return true;
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3_40() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_97()) return true;
		return false;
	}

	final private boolean jj_3R_355() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_362()) jj_scanpos = xsp;
		if (jj_3R_74()) return true;
		xsp = jj_scanpos;
		if (jj_3R_363()) jj_scanpos = xsp;
		if (jj_3R_340()) return true;
		return false;
	}

	final private boolean jj_3R_185() {
		if (jj_scan_token(LBRACE)) return true;
		if (jj_3R_97()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_40()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_3R_379()) jj_scanpos = xsp;
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_151() {
		if (jj_3R_80()) return true;
		return false;
	}

	final private boolean jj_3R_356() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_355()) return true;
		return false;
	}

	final private boolean jj_3R_150() {
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_338() {
		if (jj_3R_355()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_356()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_157() {
		if (jj_3R_136()) return true;
		return false;
	}

	final private boolean jj_3R_156() {
		if (jj_3R_185()) return true;
		return false;
	}

	final private boolean jj_3R_316() {
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_338()) jj_scanpos = xsp;
		if (jj_scan_token(RPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_149() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_155() {
		if (jj_3R_110()) return true;
		return false;
	}

	final private boolean jj_3_29() {
		if (jj_3R_93()) return true;
		return false;
	}

	final private boolean jj_3R_97() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_155()) {
			jj_scanpos = xsp;
			if (jj_3R_156()) {
				jj_scanpos = xsp;
				if (jj_3R_157()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3_28() {
		if (jj_scan_token(DOT)) return true;
		if (jj_3R_92()) return true;
		return false;
	}

	final private boolean jj_3R_342() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_366() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(ASSIGN)) return true;
		if (jj_3R_97()) return true;
		return false;
	}

	final private boolean jj_3_27() {
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(THIS)) return true;
		return false;
	}

	final private boolean jj_3R_323() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_3R_316()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_342()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3R_90() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3_27()) {
			jj_scanpos = xsp;
			if (jj_3_28()) {
				jj_scanpos = xsp;
				if (jj_3_29()) {
					jj_scanpos = xsp;
					if (jj_3R_149()) {
						jj_scanpos = xsp;
						if (jj_3R_150()) {
							jj_scanpos = xsp;
							if (jj_3R_151()) return true;
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3_26() {
		if (jj_3R_91()) return true;
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(CLASS)) return true;
		return false;
	}

	final private boolean jj_3R_367() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_366()) return true;
		return false;
	}

	final private boolean jj_3R_326() {
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_171() {
		if (jj_3R_95()) return true;
		return false;
	}

	final private boolean jj_3R_325() {
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_365() {
		if (jj_3R_366()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_367()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_324() {
		if (jj_scan_token(THROWS)) return true;
		if (jj_3R_339()) return true;
		return false;
	}

	final private boolean jj_3R_322() {
		if (jj_3R_111()) return true;
		return false;
	}

	final private boolean jj_3R_170() {
		if (jj_3R_91()) return true;
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(CLASS)) return true;
		return false;
	}

	final private boolean jj_3R_305() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_322()) jj_scanpos = xsp;
		if (jj_3R_91()) return true;
		if (jj_3R_323()) return true;
		xsp = jj_scanpos;
		if (jj_3R_324()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_325()) {
			jj_scanpos = xsp;
			if (jj_3R_326()) return true;
		}
		return false;
	}

	final private boolean jj_3R_169() {
		if (jj_3R_92()) return true;
		return false;
	}

	final private boolean jj_3R_168() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_84()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_276() {
		if (jj_scan_token(COMMA)) return true;
		return false;
	}

	final private boolean jj_3R_187() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_95()) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_97()) return true;
		if (jj_scan_token(RPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_167() {
		if (jj_scan_token(SUPER)) return true;
		if (jj_scan_token(DOT)) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3_6() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_77()) return true;
		return false;
	}

	final private boolean jj_3R_166() {
		if (jj_scan_token(THIS)) return true;
		return false;
	}

	final private boolean jj_3R_275() {
		if (jj_3R_77()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_6()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_165() {
		if (jj_3R_191()) return true;
		return false;
	}

	final private boolean jj_3R_164() {
		if (jj_scan_token(LBRACE)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_275()) jj_scanpos = xsp;
		xsp = jj_scanpos;
		if (jj_3R_276()) jj_scanpos = xsp;
		if (jj_scan_token(RBRACE)) return true;
		return false;
	}

	final private boolean jj_3R_119() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_165()) {
			jj_scanpos = xsp;
			if (jj_3R_166()) {
				jj_scanpos = xsp;
				if (jj_3R_167()) {
					jj_scanpos = xsp;
					if (jj_3R_168()) {
						jj_scanpos = xsp;
						if (jj_3R_169()) {
							jj_scanpos = xsp;
							if (jj_3R_170()) {
								jj_scanpos = xsp;
								if (jj_3R_171()) return true;
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_188() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_95()) return true;
		return false;
	}

	final private boolean jj_3R_96() {
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(ASSIGN)) return true;
		return false;
	}

	final private boolean jj_3R_361() {
		if (jj_3R_365()) return true;
		return false;
	}

	final private boolean jj_3R_116() {
		if (jj_3R_84()) return true;
		return false;
	}

	final private boolean jj_3R_93() {
		if (jj_scan_token(DOT)) return true;
		if (jj_3R_82()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_115() {
		if (jj_3R_164()) return true;
		return false;
	}

	final private boolean jj_3R_77() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_115()) {
			jj_scanpos = xsp;
			if (jj_3R_116()) return true;
		}
		return false;
	}

	final private boolean jj_3R_186() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_95()) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_361()) jj_scanpos = xsp;
		if (jj_scan_token(RPAREN)) return true;
		return false;
	}

	final private boolean jj_3_25() {
		if (jj_3R_90()) return true;
		return false;
	}

	final private boolean jj_3_39() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_95()) return true;
		if (jj_scan_token(LPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_79() {
		if (jj_3R_119()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3_25()) { jj_scanpos = xsp; break; }
		}
		return false;
	}

	final private boolean jj_3_38() {
		if (jj_scan_token(AT)) return true;
		if (jj_3R_95()) return true;
		if (jj_scan_token(LPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_96()) {
			jj_scanpos = xsp;
			if (jj_scan_token(78)) return true;
		}
		return false;
	}

	final private boolean jj_3R_358() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_340() {
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_358()) {
				jj_scanpos = xsp;
				break;
			}
		}
		return false;
	}

	final private boolean jj_3R_160() {
		if (jj_3R_188()) return true;
		return false;
	}

	final private boolean jj_3R_159() {
		if (jj_3R_187()) return true;
		return false;
	}

	final private boolean jj_3R_158() {
		if (jj_3R_186()) return true;
		return false;
	}

	final private boolean jj_3_24() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_89()) return true;
		return false;
	}

	final private boolean jj_3R_341() {
		if (jj_scan_token(ASSIGN)) return true;
		if (jj_3R_77()) return true;
		return false;
	}

	final private boolean jj_3R_110() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_158()) {
			jj_scanpos = xsp;
			if (jj_3R_159()) {
				jj_scanpos = xsp;
				if (jj_3R_160()) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_360() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_74()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_309()) return true;
		return false;
	}

	final private boolean jj_3R_75() {
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_320() {
		if (jj_3R_340()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_341()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_137() {
		return false;
	}

	final private boolean jj_3R_359() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_74()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_288()) return true;
		return false;
	}

	final private boolean jj_3R_348() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_359()) {
			jj_scanpos = xsp;
			if (jj_3R_360()) return true;
		}
		return false;
	}

	final private boolean jj_3R_321() {
		if (jj_scan_token(COMMA)) return true;
		if (jj_3R_320()) return true;
		return false;
	}

	final private boolean jj_3R_86() {
		lookingAhead = true;
		jj_semLA = getToken(1).kind == GT &&
		((Token.GTToken)getToken(1)).realKind == RSIGNEDSHIFT;
		lookingAhead = false;
		if (!jj_semLA || jj_3R_137()) return true;
		if (jj_scan_token(GT)) return true;
		if (jj_scan_token(GT)) return true;
		return false;
	}

	final private boolean jj_3R_381() {
		if (jj_scan_token(DECR)) return true;
		return false;
	}

	final private boolean jj_3R_304() {
		if (jj_3R_74()) return true;
		if (jj_3R_320()) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_321()) {
				jj_scanpos = xsp;
				break;
			}
		}
		if (jj_scan_token(SEMICOLON)) return true;
		return false;
	}

	final private boolean jj_3R_380() {
		if (jj_scan_token(INCR)) return true;
		return false;
	}

	final private boolean jj_3R_368() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_380()) {
			jj_scanpos = xsp;
			if (jj_3R_381()) return true;
		}
		return false;
	}

	final private boolean jj_3R_138() {
		return false;
	}

	final private boolean jj_3_4() {
		if (jj_3R_74()) return true;
		if (jj_scan_token(IDENTIFIER)) return true;
		Token xsp;
		while (true) {
			xsp = jj_scanpos;
			if (jj_3R_75()) {
				jj_scanpos = xsp;
				break;
			}
		}
		xsp = jj_scanpos;
		if (jj_scan_token(84)) {
			jj_scanpos = xsp;
			if (jj_scan_token(87)) {
				jj_scanpos = xsp;
				if (jj_scan_token(83)) return true;
			}
		}
		return false;
	}

	final private boolean jj_3R_349() {
		if (jj_3R_79()) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_368()) jj_scanpos = xsp;
		return false;
	}

	final private boolean jj_3R_73() {
		if (jj_3R_111()) return true;
		return false;
	}

	final private boolean jj_3_3() {
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_73()) jj_scanpos = xsp;
		if (jj_scan_token(IDENTIFIER)) return true;
		if (jj_scan_token(LPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_293() {
		if (jj_3R_305()) return true;
		return false;
	}

	final private boolean jj_3R_286() {
	  if (jj_scan_token(SEMICOLON)) return true;
	  return false;
	}

	final private boolean jj_3R_184() {
		if (jj_3R_191()) return true;
		return false;
	}

	final private boolean jj_3R_292() {
		if (jj_3R_304()) return true;
		return false;
	}

	final private boolean jj_3R_87() {
		lookingAhead = true;
		jj_semLA = getToken(1).kind == GT &&
		((Token.GTToken)getToken(1)).realKind == RUNSIGNEDSHIFT;
		lookingAhead = false;
		if (!jj_semLA || jj_3R_138()) return true;
		if (jj_scan_token(GT)) return true;
		if (jj_scan_token(GT)) return true;
		if (jj_scan_token(GT)) return true;
		return false;
	}

	final private boolean jj_3R_183() {
		if (jj_scan_token(NEW)) return true;
		return false;
	}

	final private boolean jj_3R_291() {
		if (jj_3R_303()) return true;
		return false;
	}

	final private boolean jj_3R_182() {
		if (jj_scan_token(SUPER)) return true;
		return false;
	}

	final private boolean jj_3R_290() {
		if (jj_3R_302()) return true;
		return false;
	}

	final private boolean jj_3R_181() {
		if (jj_scan_token(THIS)) return true;
		return false;
	}

	final private boolean jj_3R_289() {
		if (jj_3R_208()) return true;
		return false;
	}

	final private boolean jj_3R_180() {
		if (jj_scan_token(IDENTIFIER)) return true;
		return false;
	}

	final private boolean jj_3R_179() {
		if (jj_scan_token(LPAREN)) return true;
		return false;
	}

	final private boolean jj_3R_178() {
		if (jj_scan_token(BANG)) return true;
		return false;
	}

	final private boolean jj_3R_177() {
		if (jj_scan_token(TILDE)) return true;
		return false;
	}

	final private boolean jj_3R_72() {
		if (jj_3R_110()) return true;
		return false;
	}

	final private boolean jj_3R_378() {
		if (jj_scan_token(FINALLY)) return true;
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_71() {
		if (jj_scan_token(STRICTFP)) return true;
		return false;
	}

	final private boolean jj_3_23() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_74()) return true;
		if (jj_scan_token(LBRACKET)) return true;
		return false;
	}

	final private boolean jj_3R_140() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_74()) return true;
		if (jj_scan_token(RPAREN)) return true;
		Token xsp;
		xsp = jj_scanpos;
		if (jj_3R_177()) {
			jj_scanpos = xsp;
			if (jj_3R_178()) {
				jj_scanpos = xsp;
				if (jj_3R_179()) {
					jj_scanpos = xsp;
					if (jj_3R_180()) {
						jj_scanpos = xsp;
						if (jj_3R_181()) {
							jj_scanpos = xsp;
							if (jj_3R_182()) {
								jj_scanpos = xsp;
								if (jj_3R_183()) {
									jj_scanpos = xsp;
									if (jj_3R_184()) return true;
								}
							}
						}
					}
				}
			}
		}
		return false;
	}

	final private boolean jj_3R_70() {
		if (jj_scan_token(VOLATILE)) return true;
		return false;
	}

	final private boolean jj_3R_69() {
		if (jj_scan_token(TRANSIENT)) return true;
		return false;
	}

	final private boolean jj_3R_377() {
		if (jj_scan_token(CATCH)) return true;
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_355()) return true;
		if (jj_scan_token(RPAREN)) return true;
		if (jj_3R_114()) return true;
		return false;
	}

	final private boolean jj_3R_68() {
		if (jj_scan_token(NATIVE)) return true;
		return false;
	}

	final private boolean jj_3R_139() {
		if (jj_scan_token(LPAREN)) return true;
		if (jj_3R_74()) return true;
		if (jj_scan_token(LBRACKET)) return true;
		if (jj_scan_token(RBRACKET)) return true;
		return false;
	}

	public TokenManager token_source;
	CharStream jj_input_stream;
	public Token token, jj_nt;
	private int jj_ntk;
	private Token jj_scanpos, jj_lastpos;
	private int jj_la;
	public boolean lookingAhead = false;
	private boolean jj_semLA;

	public Parser(java.io.InputStream stream) {
		this(stream, null);
	}

	public Parser(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream = new CharStream(stream, encoding, 1, 1);
		}
		catch(java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source = new TokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
	}

	public void ReInit(java.io.InputStream stream) {
	   ReInit(stream, null);
	}

	public void ReInit(java.io.InputStream stream, String encoding) {
		try {
			jj_input_stream.ReInit(stream, encoding, 1, 1);
		}
		catch(java.io.UnsupportedEncodingException e) {
			throw new RuntimeException(e);
		}
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
	}

	public Parser(java.io.Reader stream) {
		jj_input_stream = new CharStream(stream, 1, 1);
		token_source = new TokenManager(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
	}

	public void ReInit(java.io.Reader stream) {
		jj_input_stream.ReInit(stream, 1, 1);
		token_source.ReInit(jj_input_stream);
		token = new Token();
		jj_ntk = -1;
	}

	public Parser(TokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
	}

	public void ReInit(TokenManager tm) {
		token_source = tm;
		token = new Token();
		jj_ntk = -1;
	}

	final private Token jj_consume_token(int kind) throws ParserException {
		Token oldToken;
		if ((oldToken = token).next != null) token = token.next;
		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		if (token.kind == kind) {
			return token;
		}
		token = oldToken;
		throw generateParserException();
	}

	static private final class LookaheadSuccess extends java.lang.Error {
		private static final long serialVersionUID = 1L;
	}
	final private LookaheadSuccess jj_ls = new LookaheadSuccess();
	final private boolean jj_scan_token(int kind) {
		if (jj_scanpos == jj_lastpos) {
			jj_la--;
			if (jj_scanpos.next == null) {
				jj_lastpos = jj_scanpos = jj_scanpos.next = token_source.getNextToken();
			} else {
				jj_lastpos = jj_scanpos = jj_scanpos.next;
			}
		} else {
			jj_scanpos = jj_scanpos.next;
		}
		if (jj_scanpos.kind != kind) return true;
		if (jj_la == 0 && jj_scanpos == jj_lastpos) throw jj_ls;
		return false;
	}

	final public Token getNextToken() {
		if (token.next != null) token = token.next;
		else token = token.next = token_source.getNextToken();
		jj_ntk = -1;
		return token;
	}

	final public Token getToken(int index) {
		Token t = lookingAhead ? jj_scanpos : token;
		for (int i = 0; i < index; i++) {
			if (t.next != null) t = t.next;
			else t = t.next = token_source.getNextToken();
		}
		return t;
	}

	final private int jj_ntk() {
		if ((jj_nt=token.next) == null)
			return (jj_ntk = (token.next=token_source.getNextToken()).kind);
		else
			return (jj_ntk = jj_nt.kind);
	}

	public ParserException generateParserException() {
		Token errortok = token.next;
		int line = errortok.beginLine, column = errortok.beginColumn;
		String mess = (errortok.kind == 0) ? tokenImage[0] : errortok.image;
		return new ParserException("Parse error at line " + line + ", column " + column + ".  Encountered: " + mess);
	}

	final public void enable_tracing() {
	}

	final public void disable_tracing() {
	}
}
