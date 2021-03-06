package railo.transformer.library.tag;

import java.io.IOException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import railo.commons.lang.CFTypes;
import railo.commons.lang.ClassUtil;
import railo.commons.lang.Md5;
import railo.commons.lang.StringUtil;
import railo.runtime.op.Caster;
import railo.runtime.type.dt.DateTime;
import railo.runtime.type.dt.TimeSpan;
import railo.runtime.type.util.ListUtil;


/**
 * Die Klasse TagLibTagAttr repraesentiert ein einzelnes Attribute eines Tag 
 * und haelt saemtliche Informationen zu diesem Attribut.
 */
public final class TagLibTagAttr {

	public static final short SCRIPT_SUPPORT_NONE = 0;
	public static final short SCRIPT_SUPPORT_OPTIONAL = 1;
	public static final short SCRIPT_SUPPORT_REQUIRED = 2;
	
	private String name="noname";
	private String type;
	private String description="";
	private boolean required;
	private boolean rtexpr=true;
	private Object defaultValue;
    private TagLibTag tag;
	private boolean hidden;
	private boolean _default;
	private boolean noname;
	private short status=TagLib.STATUS_IMPLEMENTED;
	private short scriptSupport=SCRIPT_SUPPORT_NONE;
	private String valueList;
	private char delimiter=',';
	private Object[] values;

	
	public TagLibTagAttr duplicate(TagLibTag tag) {
		TagLibTagAttr tlta=new TagLibTagAttr(tag);
		tlta.name=name;
		tlta.type=type;
		tlta.description=description;
		tlta.required=required;
		tlta.rtexpr=rtexpr;
		tlta.defaultValue=defaultValue;
		tlta.hidden=hidden;
		tlta.valueList=valueList;
		tlta.values=values;
		tlta.delimiter=delimiter;
		tlta.noname=noname;
		tlta._default=_default;
		tlta.status=status;
		
		
		return tlta;
	}
	
	
	/**
	 * @return the status (TagLib.,TagLib.STATUS_IMPLEMENTED,TagLib.STATUS_DEPRECATED,TagLib.STATUS_UNIMPLEMENTED)
	 */
	public short getStatus() {
		return status;
	}


	/**
	 * @param status the status to set (TagLib.,TagLib.STATUS_IMPLEMENTED,TagLib.STATUS_DEPRECATED,TagLib.STATUS_UNIMPLEMENTED)
	 */
	public void setStatus(short status) {
		this.status = status;
	}

	/**
	 * Geschuetzer Konstruktor ohne Argumente.
	 */
	public TagLibTagAttr(TagLibTag tag) {
	    this.tag=tag;
	}

	/**
	 * Gibt den Namen des Attribut zurueck.
	 * @return Name des Attribut.
	 */
	public String getName() {
		return name;
	}

	/**
	 * Gibt zurueck, ob das Attribut Pflicht ist oder nicht.
	 * @return Ist das Attribut Pflicht.
	 */
	public boolean isRequired() {
		return required;
	}

	/**
	 * Gibt den Typ des Attribut zurŸck (query, struct, string usw.)
	 * @return Typ des Attribut
	 */
	public String getType() {
	    if(this.type==null) {
	    	try {
	        String methodName=  "set"+
			(name.length()>0?""+Character.toUpperCase(name.charAt(0)):"")+
			(name.length()>1?name.substring(1):"");
	        
	        Class clazz= ClassUtil.loadClass(tag.getTagClassName(),(Class)null);//Class.orName(tag.getTagClassName());
            if(clazz!=null) {
            	Method[] methods = clazz.getMethods();
                for(int i=0;i<methods.length;i++) {
                    Method method = methods[i];
                    if(method.getName().equalsIgnoreCase(methodName)) {
                        Class[] types = method.getParameterTypes();
                        if(types.length==1) {
                        	Class type=types[0];
                            if(type==String.class)this.type="string";
                            else if(type==double.class)this.type="number";
                            else if(type==Date.class)this.type="datetime";
                            else this.type=type.getName();
                        }
                    }
                }
            }
	    	}
	    	catch(Throwable t) {
	    		
	    		return "string";
	    	}
	    }
		return this.type;
	}

	/**
	 * Gibt zurueck ob das Attribute eines Tag, mithilfe des ExprTransformer, uebersetzt werden soll oder nicht.
	 * @return Soll das Attribut uebbersetzt werden
	 */
	public boolean getRtexpr() {
		return rtexpr;
	}

	/**
	 * Setzt den Namen des Attribut.
	 * @param name Name des Attribut.
	 */
	public void setName(String name) {
		this.name = name.toLowerCase();
	}

	/**
	 * Setzt, ob das Argument Pflicht ist oder nicht.
	 * @param required Ist das Attribut Pflicht.
	 */
	public void setRequired(boolean required) {
		this.required = required;
	}

	/**
	 * Setzt, ob das Attribute eines Tag, mithilfe des ExprTransformer, uebersetzt werden soll oder nicht.
	 * @param rtexpr Soll das Attribut uebbersetzt werden
	 */
	public void setRtexpr(boolean rtexpr) {
		this.rtexpr = rtexpr;
	}

	/**
	 * Setzt, den Typ des Attribut (query, struct, string usw.)
	 * @param type Typ des Attribut.
	 */
	public void setType(String type) {
		this.type = type;
	}

	/**
	 * @return Returns the description.
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * @param description The description to set.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

    /**
     * @param defaultValue
     */
    public void setDefaultValue(Object defaultValue) {
        this.defaultValue=defaultValue;
        tag.setHasDefaultValue(true);
    }

    /**
     * @return Returns the defaultValue.
     */
    public Object getDefaultValue() {
        return defaultValue;
    }

    /**
     * @return
     */
    public boolean hasDefaultValue() {
        return defaultValue!=null;
    }

	public void setHidden(boolean hidden) {
		this.hidden=hidden;
	}
	public boolean getHidden() {
		return hidden;
	}

	public void setNoname(boolean noname) {
		this.noname=noname;
	}
	public boolean getNoname() {
		return noname;
	}

	public String getHash() {
		StringBuffer sb=new StringBuffer();
		sb.append(this.getDefaultValue());
		sb.append(this.getName());
		sb.append(this.getRtexpr());
		sb.append(this.getType());
		
		try {
			return Md5.getDigestAsString(sb.toString());
		} catch (IOException e) {
			return "";
		}
	}

	public void isDefault(boolean _default) {
		if(_default)
			tag.setDefaultAttribute(this);
		this._default=_default;
	}

	public boolean isDefault() {
		return _default;
	}


	public void setScriptSupport(String str) {
		if(!StringUtil.isEmpty(str))  {
			str=str.trim().toLowerCase();
			if("optional".equals(str)) this.scriptSupport=SCRIPT_SUPPORT_OPTIONAL;
			else if("opt".equals(str)) this.scriptSupport=SCRIPT_SUPPORT_OPTIONAL;
			else if("required".equals(str)) this.scriptSupport=SCRIPT_SUPPORT_REQUIRED;
			else if("req".equals(str)) this.scriptSupport=SCRIPT_SUPPORT_REQUIRED;
		}
	}


	/**
	 * @return the scriptSupport
	 */
	public short getScriptSupport() {
		return scriptSupport;
	}


	public Object getScriptSupportAsString() {
		if(scriptSupport==SCRIPT_SUPPORT_OPTIONAL) return "optional";
		if(scriptSupport==SCRIPT_SUPPORT_REQUIRED) return "required";
		return "none";
	}


	public void setValueDelimiter(String delimiter) {
		if(StringUtil.isEmpty(delimiter,true)) return;
		this.delimiter=delimiter.trim().charAt(0);
	}
	
	public void setValues(String valueList) {
		if(tag.getName().equalsIgnoreCase("pop"))
		if(StringUtil.isEmpty(valueList,true)) return;
		this.valueList=valueList;
	}
	
	public Object[] getValues() {
		if(valueList==null) return null;
		if(values!=null) return values;
		String[] res = ListUtil.trimItems(ListUtil.listToStringArray(valueList, delimiter));
		short type=CFTypes.toShort(getType(), false, CFTypes.TYPE_ANY);
		// String
		if(type==CFTypes.TYPE_STRING || type==CFTypes.TYPE_ANY) {
			values=res;
		}
		// Numeric
		else if(type==CFTypes.TYPE_NUMERIC) {
			List<Double> list=new ArrayList<Double>();
			Double d;
			for(int i=0;i<res.length;i++){
				d=Caster.toDouble(res[i],null);
				if(d!=null)list.add(d);
			}
			values=list.toArray(new Double[list.size()]);
		}
		// Boolean
		else if(type==CFTypes.TYPE_BOOLEAN) {
			List<Boolean> list=new ArrayList<Boolean>();
			Boolean b;
			for(int i=0;i<res.length;i++){
				b=Caster.toBoolean(res[i],null);
				if(b!=null)list.add(b);
			}
			values=list.toArray(new Boolean[list.size()]);
		}
		// DateTime
		else if(type==CFTypes.TYPE_DATETIME) {
			List<DateTime> list=new ArrayList<DateTime>();
			DateTime dt;
			for(int i=0;i<res.length;i++){
				dt=Caster.toDate(res[i],true,null,null);
				if(dt!=null)list.add(dt);
			}
			values=list.toArray(new DateTime[list.size()]);
		}
		// Timespan
		else if(type==CFTypes.TYPE_TIMESPAN) {
			List<TimeSpan> list=new ArrayList<TimeSpan>();
			TimeSpan ts;
			for(int i=0;i<res.length;i++){
				ts=Caster.toTimespan(res[i],null);
				if(ts!=null)list.add(ts);
			}
			values=list.toArray(new TimeSpan[list.size()]);
		}
		
		// TODO add support for other types ?
		else {
			valueList=null; 
		}
		return values;
		
	}
	


}