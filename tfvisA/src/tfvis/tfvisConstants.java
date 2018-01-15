package tfvis;

public interface tfvisConstants {

	String DIRECTORY_CREATE = "./data";
	String ANALYSIS_EXT = ".parse";
	String ARRAY_EXT = ".array";

	String DIRECTORY = DIRECTORY_CREATE + "/";

	String EmptyString = "";
	String IndentString = "   ";

	String TVIS_PREFIX = " /*TVIS*/";

	int PID_IF = 1;
	int PID_FOR = 2;
	int PID_WHILE = 3;
	int PID_CLASS_DECLARE = 4;
	int PID_SWITCH = 5;
	int PID_ELSE = 6;
	int PID_METHOD_DECLARE = 7;

	int PT_ARRAY = 1;
	int PT_UPDATE = 2;
	int PT_LOOP = 3;

	int PID_METHOD_END = 8;
	int PID_ARRAY_INSERT = 9;
	int PID_CLASS_END = 10;

	int DrawWidth = 21;
	int DrawHeight = 20;

	int Code = 0;
	int FlowChart = 1;
	int SequentialPaths = 2;
	int SequentialPath = 3;
	int InterModulePath = 4;

	int Semicolon = 1;
	int End = 2;
	int Identifier = 3;
	int Lparen = 4;
	int Rparen = 5;
	int Lbrace = 6;
	int Rbrace = 7;
	int Lbracket = 8;
	int Rbracket = 9;

	int Equal = 1;
	int Assign = Equal;
	int Colon = 5;
	int Dot = 6;
	int Comma = 8;
	int At = 9;

	int Ellipsis = 3;
	int Hook = 4;
	int Tilde = 5;
	int Bang = 6;

	int Plus = 0;
	int Minus = 1;
	int Star = 2;
	int Slash = 3;
	int Remainder = 4;

	int Incre = Plus + Plus;
	int Decre = Minus + Minus;

	int Lshift1 = 0;
	int Lshift2 = 1;
	int Xor = 2;
	int And = 3;
	int Or = 4;
	int Rshift3 = 5;
	int Rshift2 = 6;
	int Rshift1 = 7;

	int Public = 2;
	int Static = 3;
	int Protected = 4;
	int Private = 5;
	int Final = 6;
	int Abstract = 7;
	int Synchronized = 12;
	int Native = 13;
	int Transient = 14;
	int Volatile = 15;
	int Strictfp = 16;

	int Class = 0;
	int Interface = 4;
	int Throws = 0;
	int Extends = 0;
	int This = 0;
	int Super = 2;
	int New = 0;

	int SuperClass = Super + Class;
	int Poly = 100;

	// *******************************************************

	// TypeDeclaration
	int TypeDec = 0;

	// ClassOrInterfaceBodyDeclaration
	int CoiBodyDec = 20;

	// AnnotationTypeMemberDeclaration
	int AnnTypeDec1 = 40;
	int AnnTypeDec2 = 60;

	// ReferenceType
	int RefType1 = 70;
	int RefType2 = 80;

	// (Primitive/Result)Type
	int Boolean = 90;
	int Void = 91;
	int Char = 92;
	int Byte = 93;
	int Short = 94;
	int Int = 95;
	int Long = 96;
	int Float = 97;
	int Double = 98;

	// PackageDeclaration
	int Package = 100;

	// ImportDeclaration
	int Import = 110;

	// ClassOrInterfaceDeclaration
	int CoiDec = 120;

	// ClassOrInterfaceType
	int CoiType1 = 130;
	int CoiType2 = 140;

	// ClassOrInterfaceBody
	int CoiBody = 150;

	// Method(Declaration/Declarator)
	int Method = 160;

	// ConstructorDeclaration
	int Constructor = 170;

	// Enum(Declaration/Body)
	int Enum = 180;

	// EnumConstant
	int EnumConstant = 190;

	// FieldDeclaration
	int Field = 200;

	// Variable(Declarator/DeclaratorId)
	int Variable = 210;

	// ExtendsList
	int ExtList = 220;

	// ImplementsList
	int Implements = 230;

	// Type(Parameters/Parameter)
	int TypeP = 240;

	// TypeBound
	int TypeB = 250;

	// Type(Arguments/Argument)
	int TypeA = 260;

	// ArrayInitializer
	int ArrayIni1 = 270;
	int ArrayIni2 = 280;

	// Formal(Parameters/Parameter)
	int Formal = 290;

	// ExplicitConstractorInvocation
	int ExplicitCons = 300;

	// Initializer
	int Ini = 310;

	// WildcardBounds
	int WildBounds = 320;

	// Name/NameList
	int Name1 = 330;
	int Name2 = 340;

	// AssignmentOperator
	int Assign1 = 350;
	int Assign2 = 360;
	int Assign3 = 370;

	// RelationalExpression
	int RelationExp = 380;

	// Conditional(/And/Or)Expression
	int ConditionExp = 390;

	// (InclusiveOr/ExclusiveOr/And)Expression
	int OrAndExp = 400;

	// EqualityExpression
	int EqualityExp = 410;

	// InstanceOfExpression
	int Instanceof = 420;

	// ShiftExpression
	int ShiftExp = 430;

	// RUNSIGNEDSHIFT
	int Runsign = 440;

	// RSIGNEDSHIFT
	int Rsign = 450;

	// (Additive/Multiplicative)Expression
	int Exp = 460;

	// Pre(In/De)crementExpression
	int PreExp = 470;

	// PostfixExpression
	int PostfixExp = 480;

	// StatementExpression
	int StateExp = 490;

	// UnaryExpression(/NotPlusMinus)
	int UnaryExp = 500;

	// AllocationExpression
	int ArrayExp = 510;
	int ObjectExp = 520;

	// CastLookahead
	int Cast1 = 530;
	int Cast2 = 540;
	int Cast3 = 550;
	int SubCast1 = 560;
	int SubCast2 = 570;

	// CastExpression
	int CastExp1 = 580;
	int CastExp2 = 590;

	// PrimaryPrefix
	int Prefix1 = 600;
	int Prefix2 = 610;

	// PrimarySuffix
	int Suffix1 = 620;
	int Suffix2 = 630;
	int Suffix3 = 640;

	// Literal
	int Lit_Integer = 650;
	int Lit_Floating_P = 651;
	int Lit_Character = 652;
	int Lit_String = 653;

	// (Boolean/Null)Literal
	int Lit_True = 660;
	int Lit_False = 661;
	int Lit_Null = 662;

	// Arguments/ArgumentList
	int Args = 670;

	// ArrayDimsAndInits
	int ArrayDim1 = 680;
	int ArrayDim2 = 690;
	int ArrayDim3 = 700;

	// Statement
	int State = 710;

	// AssertStatement
	int Assert = 720;

	// LabeledStatement
	int Label = 730;

	// Block/BlockStatement
	int Block = 740;

	// LocalVariableDeclaration
	int LocalVariable = 750;

	// EmptyStatement
	int Empty = 760;

	// SwitchStatement
	int Switch = 770;

	// SwitchLabel
	int Case = 780;
	int Default = 781;

	// IfStatement
	int If = 790;
	int Else = 791;
	int Branch = 796;

	// WhileStatement/DoStatement
	int While = 800;
	int Do_While = 810;
	int Do = 813;

	// ForStatement
	int For = 820;
	int For_Ini = 830;
	int For_Con = 840;

	// StatementExpressionList
	int StateExpList = 850;

	// (Continue/Break)Statement
	int Continue = 860;
	int Break = 864;

	// (Return/Throw)Statement
	int Return = 870;
	int Throw = 874;

	// SynchronizedStatement
	int Sync = 880;

	// TryStatement
	int Try = 890;
	int Catch = 900;
	int Finally = 893;

	// NormalAnnotation
	int NormalAnn = 910;

	// MarkerAnnotation
	int MarkerAnn = 920;

	// SingleMemberAnnotation
	int SingleMemAnn = 930;

	// AnnotationType(Declaration/Body)
	int AnnType = 940;

	// MemberValue(Pairs/Pair)
	int MemPair = 950;

	// MemberValueArrayInitializer
	int MemArray1 = 960;
	int MemArray2 = 970;

	// MemberSelector
	int MemSelect = 980;

	// DefaultValue
	int DefaultVal = 990;

	/***
	 * 
	 ***/

	int If_true = If;
	int If_false = If * -1;
	int For_true = For;
	int For_false = For * -1;
	int While_true = While;
	int While_false = While * -1;
	int Case_true = Case;
	int Case_false = Case * -1;

	int End_If_true = End + If;
	int End_If_false = End_If_true * -1;
	int End_Else_false = End + Else;
	int End_Else_true = End_Else_false * -1;
	int End_Branch_true = End + Branch;
	int End_Branch_false = End_Branch_true * -1;
	int End_For_true = End + For;
	int End_For_false = End_For_true * -1;
	int End_While_true = End + While;
	int End_While_false = End_While_true * -1;
	int End_Case_true = End + Case;
	int End_Case_false = End_Case_true * -1;

	/***
	 * 
	 ***/

	int Start_Class = 1000;
	int Start_SuperClass = 1100;
	int Start_SuperClassConst = 1200;
	int Start_Method = 1300;
	int Calling_Class = 2000;
	int Calling_SuperClass = 2100;
	int Calling_SuperClassConst = 2200;
	int Calling_Method = 2300;
	int Called_Class = 3000;
	int Called_SuperClass = 3100;
	int Called_SuperClassConst = 3200;
	int Called_Method = 3300;

	int Poly_Branch = 4000;

	/***
	 * 
	 * 
	 ***/

	int[] skipSpaceOffsetList = {

			Semicolon, Rbracket, Lbracket, Lparen, Rparen, Dot,
			// Identifier,

	};

	int[] skipSpaceList = {

			// Name1,
			/*
			 * Formal+Lparen, For+Lparen,
			 * 
			 * Boolean, Void, Char, Byte, Short, Int, Long, Float, Double,
			 * 
			 */
			// Method+Static,
			// Lbracket+RefType1,
			// Lbracket+RefType2,
			// Int,
	};

	int[] CodeEndList = { Semicolon + TypeDec, Semicolon + CoiBodyDec, Semicolon + AnnTypeDec1, Semicolon + AnnTypeDec2,
			Semicolon + Package, Semicolon + Import, Semicolon + Method, Semicolon + Enum, Semicolon + Field,
			Semicolon + ExplicitCons + This, Semicolon + ExplicitCons + Super, Semicolon + State, Semicolon + Assert,
			Semicolon + Block, Semicolon + Empty, Semicolon + Do_While, Semicolon + Continue, Semicolon + Break,
			Semicolon + Return, Semicolon + Throw,

			Colon + ConditionExp, Colon + Assert, Colon + Label, Colon + Case, Colon + Default,

			Lbrace + CoiBody, Lbrace + Method, Lbrace + Constructor, Lbrace + Enum,
			// Lbrace + ArrayIni1,
			Lbrace + Block, Lbrace + Switch, Lbrace + AnnType, Lbrace + MemArray1,

			Rbrace + CoiBody, Rbrace + Method, Rbrace + Constructor, Rbrace + Enum,
			// Rbrace + ArrayIni1,
			Rbrace + Block, Rbrace + Switch, Rbrace + AnnType, Rbrace + MemArray1,

			End + If, End + For, End + While, End + Else, End + Branch, End + Do_While, End + Switch, End + Case,
			End + Default,
			// End + Class,
			End + Interface, End + Constructor, End + Method, End + Try, End + Catch, End + Finally,

			Rparen + If, Rparen + For, Rparen + While };

	// TFVIS

	int CLASSID = 0;
	int METHODID = 1;
	String DLM = "#";

	int Ev_MethodStart = 100;
	int Ev_MethodEnd = 101;
	int Ev_Return = 150;

	int Ev_ClassStart = 120;
	int Ev_ClassEnd = 122;

	int Ev_Switch = 160;
	int Ev_SwitchEnd = 161;
	int Ev_Case = 162;
	int Ev_CaseEnd = 163;
	int Ev_DefaultEnd = 164;

	int Ev_Route = 170;
	int Ev_IfEnd = 172;

	int Ev_IntUpdate = 200;
	int Ev_DoubleUpdate = 210;
	int Ev_StringUpdate = 220;
	int Ev_Input = 230;

	int Ev_UpdateRangeL = 200;
	int Ev_UpdateRangeH = 249;

	int Ev_CallMethod = 250;

	int Ev_UpdateInstance = 260;

	int Ev_For = 300;
	int Ev_ForEnd = 302;

	int Ev_While = 350;
	int Ev_WhileEnd = 352;

	int Ev_Do = 360;
	int Ev_DoEnd = 362;
	int Ev_DoWhile = 364;
	int Ev_DoWhileEnd = 366;

	int Ev_Try = 380;
	int Ev_TryEnd = 382;
	int Ev_Catch = 390;
	int Ev_CatchEnd = 392;
	int Ev_Finally = 400;
	int Ev_FinallyEnd = 402;

	int Ev_Super = 450;
	int Ev_Other = 999;

	int ArrayMode = 1;

	int Mt_Normal = 0;
	int Mt_Main = 1;
	int Mt_Constructor = 2;
	int Mt_Static = 3;

	/*
	 * static const int NONE=0; static const int CLASS_START=0; static const int
	 * CLASS_END=1; static const int METHOD_START=100; static const int
	 * METHOD_END=101; static const int RETURN=150; static const int ROUTE=170;
	 * static const int UPDATE_INT=200; static const int UPDATE_INTARRAY=201; static
	 * const int METHOD_CALL=250; static const int LOOP_START=300; static const int
	 * LOOP_NEXT=301; static const int LOOP_END=302; static const int DATAREAD=500;
	 * static const int LIFELIMIT=680; static const int MEMO=980; static const int
	 * OTHER=999;
	 */
	String sourceDir = "source\\";
	String outDir = "tData\\";
	String outDirName = "tData";
	String Exten_Probe = ".java";
	String Exten_Lines = ".str";
	String Exten_Str = ".txt";
}
