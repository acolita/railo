package railo.runtime.functions.arrays;


import railo.runtime.PageContext;
import railo.runtime.exp.PageException;
import railo.runtime.functions.BIF;
import railo.runtime.op.Caster;
import railo.runtime.type.Array;

public final class ArrayFindAllNoCase extends BIF {
	
	private static final long serialVersionUID = -1922900405563697067L;

	public static Array call(PageContext pc , Array array, Object value) throws PageException {
        return ArrayFindAll.find(array,value,false);
    }
	
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		return call(pc,Caster.toArray(args[0]),args[1]);
	}
}