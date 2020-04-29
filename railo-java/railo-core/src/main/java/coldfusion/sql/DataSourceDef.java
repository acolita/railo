package coldfusion.sql;


import java.util.Map;

import railo.runtime.type.Struct;

public interface DataSourceDef {
	public Object get(Object arg1);

	public int getType();

	public String getClassName();

	public String getHost();

	public int getPort();

	public boolean isDynamic();

	public boolean isConnectionEnabled();

	public boolean isBlobEnabled();

	public boolean isClobEnabled();

	public String getDriver();

	public void setDriver(String arg1);

	public Struct getAllowedSQL();

	public void setAllowedSQL(Struct arg1);

	public boolean isSQLRestricted();

	public void setMap(Map arg1);

	public boolean isRemoveOnPageEnd();

	public void setRemoveOnPageEnd(boolean arg1);

	public void setDynamic(boolean arg1);

	public String getIfxSrv();

	public void setIfxSrv(String arg1);

	public boolean getStrPrmUni();

	public void setStrPrmUni(boolean arg1);

	public void setStrPrmUni(String arg1);

	public String getSelectMethod();

	public void setSelectMethod(String arg1);

	public String getSid();

	public void setSid(String arg1);

	public String getJndiName();

	public void setJndiName(String arg1);

	public int getMaxClobSize();

	public void setMaxClobSize(int arg1);

	public int getMaxBlobSize();

	public void setMaxBlobSize(int arg1);

	public void setClobEnabled(boolean arg1);

	public void setBlobEnabled(boolean arg1);

	public void setConnectionEnabled(boolean arg1);

	public int getLogintimeout();

	public void setLogintimeout(int arg1);

	public int getMaxconnections();

	public void setMaxConnections(int arg1);

	public void setMaxConnections(Object arg1);

	public void setDatabase(String arg1);

	public String getDatabase();

	public void setHost(String arg1);

	public void setVendor(String arg1);

	public String getVendor();

	public Struct getJndienv();

	public void setLoginTimeout(Object arg1);

	public int getLoginTimeout();

	public void setPort(int arg1);

	public void setPort(Object arg1);

	public int getMaxConnections();

	public void setJndienv(Struct arg1);

	public void setJNDIName(String arg1);

	public String getJNDIName();

	public void setType(String arg1);

	public void setType(int arg1);

	public String getDsn();

	public void setDsn(String arg1);

	// TODO impl public TwoFishCryptor getCryptor();

//	 TODO impl public void setCryptor(TwoFishCryptor arg1);

	public void setClassName(String arg1);

	public String getDesc();

	public void setDesc(String arg1);

	public String getUsername();

	public void setUsername(String arg1);

	public void setPassword(String arg1);

	public String getUrl();

	public void setUrl(String arg1);

	public boolean isPooling();

	public void setPooling(boolean arg1);

	public int getTimeout();

	public void setTimeout(int arg1);

	public int getInterval();

	public void setInterval(int arg1);

	public Struct getExtraData();

	public void setExtraData(Struct arg1);

	public void setMaxPooledStatements(int arg1);

	public int getMaxPooledStatements();



}
