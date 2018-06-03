package lwj.app.utils.system;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Bean util
 * @author LF
 * 
 */
public class BeanUtil {

	private static LogUtil log = new LogUtil(BeanUtil.class);
	
	/**
	 * Map to Bean
	 * @param map Map
	 * @param obj Object
	 */
	public static void mapToBean(Map<String, Object> map, Object obj) {
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();

			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (map.containsKey(key)) {
					Object value = map.get(key);
					Method setter = property.getWriteMethod();
					setter.invoke(obj, value);
				}
			}
		} catch (Exception e) {
			log.errorLog("Map -> Bean conversion error!", e);
		}
	}
	
	
	
	/**
	 * Bean to Map
	 * @param obj Object
	 * @return Map
	 */
	public static Map<String, Object> beanToMap(Object obj) {

		if (obj == null) {
			return null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				if (!key.equals("class")) {
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);

					map.put(key, value);
				}

			}
		} catch (Exception e) {
			log.errorLog("Bean -> Map conversion error!", e);
		}

		return map;
	}
	
	/**
	 * 初始化泛型对象
	 * @param clazz
	 * @return
	 * @throws InstantiationException
	 * @throws IllegalAccessException
	 */
	public static <T> T newTclass(Class<T> clazz) throws InstantiationException, IllegalAccessException{  
		T t = clazz.newInstance();
		return t;
	}
}