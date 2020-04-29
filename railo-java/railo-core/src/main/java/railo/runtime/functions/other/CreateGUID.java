
package railo.runtime.functions.other;


import railo.runtime.PageContext;
import railo.runtime.ext.function.Function;

import java.util.UUID;

/**
 * Implements the CFML Function createGuid
 */
public final class CreateGUID implements Function {

    public static String call(PageContext pc ) {
        return UUID.randomUUID().toString().toUpperCase();
    }
}