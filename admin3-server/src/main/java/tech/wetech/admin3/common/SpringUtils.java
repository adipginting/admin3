package tech.wetech.admin3.common;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.NoSuchBeanDefinitionException;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

/**
 * @author cjbi
 */
@Component
public final class SpringUtils implements BeanFactoryPostProcessor {

  private static ConfigurableListableBeanFactory beanFactory; // Spring application context

  @Override
  public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
      throws BeansException {
    SpringUtils.beanFactory = beanFactory;
  }

  /**
   * Get object
   *
   * @param name
   * @return Object An instance of the bean registered with the given name
   * @throws org.springframework.beans.BeansException
   */
  @SuppressWarnings("unchecked")
  public static <T> T getBean(String name) throws BeansException {
    return (T) beanFactory.getBean(name);
  }

  /**
   * Get object of type requiredType
   *
   * @param clz
   * @return
   * @throws org.springframework.beans.BeansException
   */
  public static <T> T getBean(Class<T> clz) throws BeansException {
    @SuppressWarnings("unchecked")
    T result = (T) beanFactory.getBean(clz);
    return result;
  }

  /**
   * Return true if BeanFactory contains a bean definition matching the given name
   *
   * @param name
   * @return boolean
   */
  public static boolean containsBean(String name) {
    return beanFactory.containsBean(name);
  }

  /**
   * Determine whether the bean definition registered under the given name is a singleton or a
   * prototype. If the corresponding bean definition is not found, an exception
   * (NoSuchBeanDefinitionException) will be thrown
   *
   * @param name
   * @return boolean
   * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
   */
  public static boolean isSingleton(String name) throws NoSuchBeanDefinitionException {
    return beanFactory.isSingleton(name);
  }

  /**
   * @param name
   * @return Class Type of registered object
   * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
   */
  public static Class<?> getType(String name) throws NoSuchBeanDefinitionException {
    return beanFactory.getType(name);
  }

  /**
   * Return aliases if the given bean name has aliases in the bean definition
   *
   * @param name
   * @return
   * @throws org.springframework.beans.factory.NoSuchBeanDefinitionException
   */
  public static String[] getAliases(String name) throws NoSuchBeanDefinitionException {
    return beanFactory.getAliases(name);
  }
}
