package net.zfp.util;


public class EntityClassUtil {

	private ClassResolver classResolver;

	private static EntityClassUtil instance;

	public void init() {
		instance = this;
	}

	public static Class<?> getEntityClass(Object o) {
		if (instance.classResolver != null) {
			return instance.classResolver.getEntityClass(o);
		}
		return o.getClass();
	}

	public void setClassResolver(ClassResolver classResolver) {
		this.classResolver = classResolver;
	}

}
