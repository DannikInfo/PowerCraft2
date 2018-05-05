package ru.dannikinfo.powercraft.api.utils;

import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.LineNumberReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

public class Property {

	private Object value = null;
	private String comment[] = null;
	
	private Property(LineNumberReader lnr, String[] thisComment){
		comment = thisComment;
		HashMap<String, Property> hm = new HashMap<String, Property>();
		value = hm;
		try {
			String line = lnr.readLine();
			String key=null;
			List<String> comment = new ArrayList<String>();
			while(line!=null){
				line = line.trim();
				if(line.startsWith("#")){
					comment.add(line.substring(1));
				}else if(line.endsWith("}")){
					break;
				}else if(line.endsWith("{") && key!=null){
					hm.put(key, new Property(lnr, comment.toArray(new String[0])));
					key=null;
					comment.clear();
				}else if(!line.equals("")){
					int peq = line.indexOf('=');
					if(peq==-1){
						peq = line.indexOf('{');
						if(peq>0){
							key = line.substring(0, peq).trim();
							hm.put(key, new Property(lnr, comment.toArray(new String[0])));
							key=null;
							comment.clear();
						}else{
							key = line;
						}
					}else if(peq>0){
						key = line.substring(0, peq).trim();
						String value = line.substring(peq+1).trim();
						hm.put(key, new Property(value, comment.toArray(new String[0])));
						key=null;
						comment.clear();
					}
				}
				
				line = lnr.readLine();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	private Property(String s, String...desc){
		comment = desc;
		if(s.startsWith("\"") && s.endsWith("\"")){
			value = s.substring(1, s.length()-1);
		}else if(s.equalsIgnoreCase("true") || s.equalsIgnoreCase("ok")){
			value = true;
		}else if(s.equalsIgnoreCase("false")){
			value = false;
		}else{
			try{
				if(s.contains(".")){
					value = Float.parseFloat(s);
				}else{
					value = Integer.parseInt(s);
				}
			}catch(NumberFormatException e){
				value = s;
			}
		}
	}
	
	public Property(Object value, String...desc){
		if(value==null)
			value = new HashMap<String, Property>();
		this.value = value;
		comment = desc;
	}
	
	public Property(){
		value = new HashMap<String, Property>();
		comment = null;
	}
	
	public String getString(){
		return value.toString();
	}
	
	public int getInt(){
		if(value instanceof Number){
			return ((Number) value).intValue();
		}
		return 0;
	}
	
	public float getFloat(){
		if(value instanceof Number){
			return ((Number) value).floatValue();
		}
		return 0.0f;
	}
	
	public boolean getBoolean() {
		if(value instanceof Boolean){
			return (Boolean) value;
		}
		return false;
	}
	
	public HashMap<String, Property> getPropertys(){
		if(value instanceof HashMap){
			return (HashMap<String, Property>)value;
		}
		return null;
	}
	
	public boolean hasChildren(){
		return value instanceof HashMap;
	}
	
	public void put(String key, Property prop){
		if(hasChildren()){
			getPropertys().put(key, prop);
		}
	}
	
	private void save(OutputStreamWriter osw, String tabs){
		if(hasChildren()){
			HashMap<String, Property> hm = (HashMap<String, Property>)value;
			String[] keys = hm.keySet().toArray(new String[0]);
			Arrays.sort(keys);
			for(String key:keys){
				try {
					Property prop = hm.get(key);
					String[] comment = prop.comment;
					if(comment!=null && comment.length>0){
						for(String c:comment){
							osw.write(tabs + "#"+c+"\n");
						}
					}
					if(prop.hasChildren()){
						osw.write(tabs + key+"{\n");
						prop.save(osw, tabs+"\t");
						osw.write(tabs + "}\n");
					}else{
						if(prop.value instanceof String){
							osw.write(tabs + key+"=\""+(String)prop.value+"\"\n");
						}else{
							osw.write(tabs + key+"="+prop.value.toString()+"\n");
						}
					}
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
			
		}
	}
	
	public void save(OutputStream os){
		try {
			OutputStreamWriter osw = new OutputStreamWriter(os, "utf-8");
			save(osw, "");
			osw.close();
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static Property loadFromFile(InputStream is){
		LineNumberReader lnr = new LineNumberReader(new InputStreamReader(is));
		Property prop = new Property(lnr, null);
		try {
			lnr.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return prop;
	}

	public String[] getComment() {
		return comment;
	}

	public void setValue(Object o, String[] comment) {
		value = o;
		this.comment = comment;
	}

	public void replaceWith(Property prop) {
		if(hasChildren() && prop.hasChildren()){
			HashMap<String, Property> hm = prop.getPropertys();
			HashMap<String, Property> thm = getPropertys();
			for(Entry<String, Property> e:hm.entrySet()){
				if(thm.containsKey(e.getKey())){
					thm.get(e.getKey()).replaceWith(e.getValue());
				}else{
					thm.put(e.getKey(), e.getValue());
				}
			}
		}else{
			value = prop.value;
			comment = prop.comment;
		}
	}
	
	public Property getProperty(String key, Object defaultValue, String[] comment){
		String[] keys = key.split("\\.");
		Property prop = this;
		for(int i=0; i<keys.length; i++){
			if(prop.hasChildren()){
				HashMap<String, Property> hm = prop.getPropertys();
				if(hm.containsKey(keys[i])){
					prop = hm.get(keys[i]);
				}else{
					if(i==keys.length-1){
						hm.put(keys[i], prop = new Property(defaultValue, comment));
					}else{
						hm.put(keys[i], prop = new Property());
					}
				}
			}else{
				break;
			}
		}
		return prop;
	}
	
	public int getInt(String key){
		return getInt(key, 0);
	}
	
	public int getInt(String key, int defaultValue, String... comment){
		return getProperty(key, defaultValue, comment).getInt();
	}
	
	public float getFloat(String key){
		return getFloat(key, 0.0f);
	}
	
	public float getFloat(String key, float defaultValue, String... comment){
		return getProperty(key, defaultValue, comment).getFloat();
	}
	
	public boolean getBoolean(String key){
		return getBoolean(key, false);
	}
	
	public boolean getBoolean(String key, boolean defaultValue, String... comment){
		return getProperty(key, defaultValue, comment).getBoolean();
	}
	
	public String getString(String key){
		return getString(key, "");
	}
	
	public String getString(String key, String defaultValue, String... comment){
		return getProperty(key, defaultValue, comment).getString();
	}

	public void setInt(String key, int i) {
		Property prop = getProperty(key, i, null);
		prop.setValue(i, prop.getComment());
	}

	public void setFloat(String key, float f) {
		Property prop = getProperty(key, f, null);
		prop.setValue(f, prop.getComment());
	}
	
	public void setBoolean(String key, boolean b) {
		Property prop = getProperty(key, b, null);
		prop.setValue(b, prop.getComment());
	}
	
	public void setString(String key, String s) {
		Property prop = getProperty(key, s, null);
		prop.setValue(s, prop.getComment());
	}
	
}
