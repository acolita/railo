package railo.transformer.bytecode.op;

import org.objectweb.asm.Type;
import org.objectweb.asm.commons.GeneratorAdapter;
import org.objectweb.asm.commons.Method;

import railo.runtime.exp.TemplateException;
import railo.transformer.bytecode.BytecodeContext;
import railo.transformer.bytecode.BytecodeException;
import railo.transformer.bytecode.expression.ExprDouble;
import railo.transformer.bytecode.expression.Expression;
import railo.transformer.bytecode.expression.ExpressionBase;
import railo.transformer.bytecode.util.Methods;
import railo.transformer.bytecode.util.Types;

public final class OpDouble extends ExpressionBase implements ExprDouble {

	private static final Method DIV_REF=new Method("divRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method INTDIV_REF=new Method("intdivRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method EXP_REF=new Method("exponentRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});

	private static final Method PLUS_REF=new Method("plusRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method MINUS_REF=new Method("minusRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method MODULUS_REF=new Method("modulusRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method DIVIDE_REF=new Method("divideRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	private static final Method MULTIPLY_REF=new Method("multiplyRef",Types.DOUBLE,new Type[]{Types.OBJECT,Types.OBJECT});
	
	public static final int PLUS=GeneratorAdapter.ADD;
    public static final int MINUS=GeneratorAdapter.SUB;
    public static final int MODULUS=GeneratorAdapter.REM;
    public static final int DIVIDE=GeneratorAdapter.DIV;
    public static final int MULTIPLY=GeneratorAdapter.MUL;
	public static final int EXP = 2000;
	public static final int INTDIV = 2001;
	
    private int operation;
	private Expression left;
	private Expression right;
    
    OpDouble(Expression left, Expression right, int operation)  {
        super(left.getStart(),right.getEnd());
        this.left=	left;
        this.right=	right;   
        this.operation=operation;
    }
    

	public Expression getLeft() {
		return left;
	}

	public Expression getRight() {
		return right;
	}
	
	public int getOperation() {
		return operation;
	}
    
    /**
     * Create a String expression from a Expression
     * @param left 
     * @param right 
     * @param operation 
     * 
     * @return String expression
     * @throws TemplateException 
     */
    public static ExprDouble toExprDouble(Expression left, Expression right,int operation)  {
    	return new OpDouble(left,right,operation);
    }

	/**
     *
     * @see railo.transformer.bytecode.expression.ExpressionBase#_writeOut(org.objectweb.asm.commons.GeneratorAdapter, int)
     */
    public Type _writeOut(BytecodeContext bc, int mode) throws BytecodeException {
    	return writeOutDouble(bc, mode) ;
    }
    
    public Type writeOutDouble(BytecodeContext bc, int mode) throws BytecodeException {
    	GeneratorAdapter adapter = bc.getAdapter();
    	
    	left.writeOut(bc,MODE_REF);
        right.writeOut(bc,MODE_REF);
    	
    	
        if(operation==EXP) {
        	adapter.invokeStatic(Types.OPERATOR,EXP_REF);
        }
        else if(operation==DIVIDE) {
        	adapter.invokeStatic(Types.OPERATOR,DIV_REF);
        }
        else if(operation==INTDIV) {
        	adapter.invokeStatic(Types.OPERATOR,INTDIV_REF);
        }
        else if(operation==PLUS) {
        	adapter.invokeStatic(Types.OPERATOR,PLUS_REF);
        }
        else if(operation==MINUS) {
        	adapter.invokeStatic(Types.OPERATOR,MINUS_REF);
        }
        else if(operation==MODULUS) {
        	adapter.invokeStatic(Types.OPERATOR,MODULUS_REF);
        }
        else if(operation==DIVIDE) {
        	adapter.invokeStatic(Types.OPERATOR,DIVIDE_REF);
        }
        else if(operation==MULTIPLY) {
        	adapter.invokeStatic(Types.OPERATOR,MULTIPLY_REF);
        }
        

        if(mode==MODE_VALUE) {
            adapter.invokeStatic(Types.CASTER,Methods.METHOD_TO_DOUBLE_VALUE_FROM_DOUBLE);
            return Types.DOUBLE_VALUE;
        }
        
        
        
        return Types.DOUBLE;
    }

}
