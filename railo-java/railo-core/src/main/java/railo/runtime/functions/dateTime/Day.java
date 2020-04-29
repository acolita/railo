/**
 * Implements the CFML Function day
 */
package railo.runtime.functions.dateTime;

import java.util.TimeZone;

import railo.commons.date.DateTimeUtil;
import railo.commons.date.TimeZoneUtil;
import railo.runtime.PageContext;
import railo.runtime.exp.ExpressionException;
import railo.runtime.exp.PageException;
import railo.runtime.functions.BIF;
import railo.runtime.op.Caster;
import railo.runtime.type.dt.DateTime;

public final class Day extends BIF {

	private static final long serialVersionUID = -7476348690381151695L;

	public static double call(PageContext pc , DateTime date) {
		return _call(pc, date,pc.getTimeZone());
	}
	public static double call(PageContext pc , DateTime date, String strTimezone) throws ExpressionException {
		return _call(pc, date,strTimezone==null?pc.getTimeZone():TimeZoneUtil.toTimeZone(strTimezone));
	}
	
	private static double _call(PageContext pc , DateTime date,TimeZone tz) {
		return DateTimeUtil.getInstance().getDay(tz, date);
	}
	
	@Override
	public Object invoke(PageContext pc, Object[] args) throws PageException {
		if(args.length==1)return call(pc,Caster.toDatetime(args[0],pc.getTimeZone()));
		return call(pc,Caster.toDatetime(args[0],pc.getTimeZone()),Caster.toString(args[1]));
	}
}