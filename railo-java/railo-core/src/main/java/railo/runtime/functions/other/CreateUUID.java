
package railo.runtime.functions.other;



import railo.runtime.PageContext;
import railo.runtime.ext.function.Function;

import java.util.UUID;

/**
 * Implements the CFML Function createuuid
 */
public final class CreateUUID implements Function {

	/**
     * method to invoke the function
	 * @param pc
	 * @return UUID String
	 */
	public static String call(PageContext pc ) {
		return invoke();
	}
	public static String invoke() {
		String uuid = UUID.randomUUID().toString().toUpperCase();
        return new StringBuffer(uuid.substring(0,23)).append(uuid.substring(24)).toString();
	}
}