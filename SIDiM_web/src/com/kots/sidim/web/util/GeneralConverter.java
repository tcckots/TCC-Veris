package com.kots.sidim.web.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import com.kots.sidim.web.view.BaseView;

@FacesConverter("general")
public class GeneralConverter implements Converter {

	private static final Pattern attributeExp = Pattern
			.compile("([a-zA-Z]+-value)\\[(\\d+)\\]");

	private static Map<Class<?>, String> verifiers = new HashMap<Class<?>, String>();

	public static void registerVerifier(Class<?> klass,
			String collectionExpression) {
		verifiers.put(klass, collectionExpression);
	}

	public Object getAsObject(FacesContext context, UIComponent component,
			String key) throws ConverterException {
		if (key == null || key.equals("") || key.equals("null")) {
			return null;
		}
		if (key.startsWith("invalid-"))
			throw new ConverterException("Invalid value");
		try {
			Matcher matcher = attributeExp.matcher(key);
			if (!matcher.matches()) {
				Logger.getLogger("CFlexData-converters").warning(
						"No match on converter");
			}
			key = matcher.group(1);
			int idx = Integer.parseInt(matcher.group(2));
			List<?> list = (List<?>) component.getAttributes().get(key);
			return list.get(idx);
		} catch (Exception e) {
			Logger.getLogger("CFlexData-converters").warning(
					"Cannot convert back to object: " + key + " " + e);
			e.printStackTrace();
			throw new ConverterException(e);
		}
	}

	@SuppressWarnings("unchecked")
	public String getAsString(FacesContext context, UIComponent component,
			Object value) throws ConverterException {
		if (value == null || value.equals("") || value.equals("null")) {
			return "";
		}
		try {
			String attributeName = value.getClass().getSimpleName() + "-value";
			List<Object> list = (List<Object>) component.getAttributes().get(
					attributeName);
			if (list == null) {
				list = new LinkedList<Object>();
				component.getAttributes().put(attributeName, list);
			}
			int idx;
			if (list.contains(value)) {
				list.set(idx = list.indexOf(value), value);
			} else {
				idx = list.size();
				list.add(value);
			}
			if (!isValid(value, component))
				attributeName = "invalid-" + attributeName;
			return attributeName + "[" + idx + "]";
		} catch (Exception e) {
			Logger.getLogger("CFlexData-converters").warning(
					"Cannot convert  to string: " + value + " " + e);
			e.printStackTrace();
			throw new ConverterException(e);
		}
	}

	private static boolean isValid(Object value, UIComponent component) {
		String exp = verifiers.get(value.getClass());
		if (exp != null) {
			@SuppressWarnings("rawtypes")
			Collection values = BaseView.eval(exp, Collection.class);
			return values.contains(value);
		}
		return true;
	}

}
