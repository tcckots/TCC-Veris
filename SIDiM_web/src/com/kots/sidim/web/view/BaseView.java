package com.kots.sidim.web.view;

import java.text.MessageFormat;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.regex.Pattern;

import javax.el.ELContext;
import javax.el.ELResolver;
import javax.el.ExpressionFactory;
import javax.el.ValueExpression;
import javax.faces.application.Application;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.model.SelectItem;

import org.primefaces.component.tabview.Tab;
import org.primefaces.component.tabview.TabView;
import org.primefaces.event.TabChangeEvent;

public class BaseView {

	protected static final int DEFAULT_TABLE_ROW_HEIGHT = 28;
	protected static final int DEFAULT_INITIAL_DISPLAYED_ROWS = 10;
	protected static final int DEFAULT_SCROLLBAR_THICKNESS = 18;

	protected int activeTabIndex;

	private String activeTab;

	/**
	 * Evaluates an expression as it would be in a JSF file.
	 * 
	 * @param expression
	 *            The expression to be evaluated, e.g.
	 *            </code>#{param.myParameter}</code>.
	 * @param expectedType
	 *            The expected expression result type. Inform
	 *            <code>Object.class</code> if unknown.
	 * @return The expression result, cast to the exprected type.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T eval(String expression, Class<T> expectedType) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ExpressionFactory expFactory = application.getExpressionFactory();
		ValueExpression binding = expFactory.createValueExpression(
				context.getELContext(), expression, expectedType);
		Object result = binding.getValue(context.getELContext());
		return (T) result;
	}

	/**
	 * Evaluates a value expression and changes its current value.
	 * 
	 * @param expression
	 *            The expression to be evaluated, e.g.
	 *            </code>#{param.myParameter}</code>.
	 * @param expectedType
	 *            The expected expression result type. Inform
	 *            <code>Object.class</code> if unknown.
	 * @param newValue
	 *            The new value for the value expression.
	 * @return The expression result, cast to the exprected type.
	 */
	@SuppressWarnings("unchecked")
	public static <T> T eval(String expression, Class<T> expectedType,
			T newValue) {
		FacesContext context = FacesContext.getCurrentInstance();
		Application application = context.getApplication();
		ExpressionFactory expFactory = application.getExpressionFactory();
		ValueExpression binding = expFactory.createValueExpression(
				context.getELContext(), expression, expectedType);
		binding.setValue(context.getELContext(), newValue);
		Object result = binding.getValue(context.getELContext());
		return (T) result;
	}

	protected static String getManagedBeanName(Class<?> managedBeanClass) {
		ManagedBean annotation = managedBeanClass
				.getAnnotation(ManagedBean.class);
		String keepAlive = annotation != null ? annotation.name() : null;
		if (keepAlive == null || "".equals(keepAlive)) {
			StringBuilder name = new StringBuilder(
					managedBeanClass.getSimpleName());
			name.setCharAt(0, Character.toLowerCase(name.charAt(0)));
			keepAlive = name.toString();
		}
		return keepAlive;
	}

	protected static <T> T getManagedBean(Class<T> managedBeanClass) {
		String name = getManagedBeanName(managedBeanClass);
		return eval(String.format("#{%s}", name), managedBeanClass);
	}

	protected String getMessage(String key) {
		try {
			ELResolver resolver = FacesContext.getCurrentInstance()
					.getApplication().getELResolver();
			ELContext context = FacesContext.getCurrentInstance()
					.getELContext();
			Object bundle = resolver.getValue(context, null, "bundle");
			return String.valueOf(resolver.getValue(context, bundle, key));
		} catch (Exception e) {
			return String.format("??%s???", key);
		}
	}

	public List<SelectItem> getLocaleOptions() {
		FacesContext context = FacesContext.getCurrentInstance();
		Locale myLocale = context.getViewRoot().getLocale();
		Iterator<Locale> localeIterator = context.getApplication()
				.getSupportedLocales();
		List<SelectItem> result = new LinkedList<SelectItem>();
		while (localeIterator.hasNext()) {
			Locale locale = localeIterator.next();
			result.add(new SelectItem(locale, locale.getDisplayName(myLocale)));
		}
		return result;
	}

	/*
	 * protected void addMessage(Exception e, Object... args) { FacesMessage msg
	 * = new FacesMessage(FacesMessage.SEVERITY_ERROR,
	 * getMessage("error_server"), e.toString());
	 * FacesContext.getCurrentInstance().addMessage(null, msg);
	 * LogUtil.warn(null, e); }
	 * 
	 * protected static Pattern messageFormatRE = Pattern.compile("\\{\\d+\\}");
	 * 
	 * protected void addMessage(BusinessRuleException e, Object... args) {
	 * BusinessRule brokenRule = e.getBrokenRule(); String message =
	 * getMessage(brokenRule.getClass().getSimpleName() + "_" +
	 * brokenRule.name()); if (args.length > 0 &&
	 * messageFormatRE.matcher(message).find()) message =
	 * MessageFormat.format(message, args); FacesMessage msg = new
	 * FacesMessage(FacesMessage.SEVERITY_WARN, getMessage("cannot_save"),
	 * message); FacesContext.getCurrentInstance().addMessage(null, msg); }
	 * 
	 * public static ApplicationUser currentUser() { return
	 * eval("#{authentication.user}", ApplicationUser.class); }
	 */
	public int getActiveTabIndex() {
		if (activeTab != null) {
			String tabViewId, tabName;
			{
				String[] part = activeTab.split("\\|");
				tabViewId = part[0];
				tabName = part[1];
			}
			TabView tabView = (TabView) FacesContext.getCurrentInstance()
					.getViewRoot().findComponent(tabViewId);
			activeTabIndex = 0;
			for (UIComponent child : tabView.getChildren()) {
				if (child instanceof Tab) {
					String clientId = child.getClientId();
					clientId = clientId
							.substring(clientId.lastIndexOf(':') + 1);
					if (clientId.equals(tabName))
						break;
					activeTabIndex++;
				}
			}
			activeTab = null;
		}
		return activeTabIndex;
	}

	public void setActiveTabIndex(int activeTabIndex) {
		// this.activeTabIndex = activeTabIndex;
	}

	public void changeActiveTab(String tabViewId, String tabName) {
		this.activeTab = tabViewId + "|" + tabName;
	}

	public void tabChanged(TabChangeEvent event) {
		TabView tabView = (TabView) event.getTab().getParent();
		int tabIndex = 0;
		for (UIComponent component : tabView.getChildren()) {
			if (component instanceof Tab) {
				if (component.equals(event.getTab()))
					break;
				if (component.isRendered())
					tabIndex++;
			}
		}
		activeTabIndex = tabIndex;
	}

}
