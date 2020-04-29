package railo.runtime.services;

import java.util.HashMap;
import java.util.Map;

import railo.runtime.PageContext;
import railo.runtime.config.ConfigImpl;
import railo.runtime.config.ConfigWebUtil;
import railo.runtime.engine.ThreadLocalPageContext;
import railo.runtime.exp.SecurityException;
import coldfusion.server.Service;
import coldfusion.server.ServiceException;
import coldfusion.server.ServiceMetaData;

public class ServiceSupport implements Service {

	public void start() throws ServiceException {}

	public void stop() throws ServiceException {}

	public void restart() throws ServiceException {}

	public int getStatus() {
		return STARTED;
	}

	public ServiceMetaData getMetaData() {
		return new EmptyServiceMetaData();
	}

	public Object getProperty(String key) {return null;}

	public void setProperty(String key, Object value) {}

	public Map getResourceBundle() {
		return new HashMap();
	}	

    protected void checkWriteAccess() throws SecurityException {
    	ConfigWebUtil.checkGeneralWriteAccess(config(),"");
	}
    protected void checkReadAccess() throws SecurityException {
    	ConfigWebUtil.checkGeneralReadAccess(config(),"");
	}

	protected ConfigImpl config() {
		return (ConfigImpl) ThreadLocalPageContext.getConfig();
	}

	protected PageContext pc() {
		return ThreadLocalPageContext.get();
	}
}
