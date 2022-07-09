package com.wang.spring;

import java.beans.Introspector;
import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URL;
import java.util.ArrayList;
import java.util.concurrent.ConcurrentHashMap;

public class WangApplicationContext {

    private Class configClass;

    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ArrayList<BeanPostProcessor> beanPostProcessorsList = new ArrayList<>();

    public WangApplicationContext(Class configClass) {
        this.configClass = configClass;

        // 扫描
        if (configClass.isAnnotationPresent(ComponentScan.class)) {
            ComponentScan componentScanAnnotion = (ComponentScan) configClass.getAnnotation(ComponentScan.class);
            String path = componentScanAnnotion.value();

            path = path.replace(".", "/");
            ClassLoader classLoader = WangApplicationContext.class.getClassLoader();
            URL resource = classLoader.getResource(path);

            File file = new File(resource.getFile());
            System.out.println(file);
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    String fileName = f.getAbsolutePath();
                    System.out.println(fileName);

                    if (fileName.endsWith(".class")) {
                        String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                        className = className.replace("\\", ".");

                        Class<?> clazz = null;
                        try {
                            clazz = classLoader.loadClass(className);
                            if (clazz.isAnnotationPresent(Component.class)) {

                                if (BeanPostProcessor.class.isAssignableFrom(clazz)) {
                                    BeanPostProcessor instance = (BeanPostProcessor) clazz.newInstance();
                                    beanPostProcessorsList.add(instance);
                                }

                                // BeanDefinition
                                BeanDefinition beanDefinition = new BeanDefinition();
                                if (clazz.isAnnotationPresent(Scope.class)) {
                                    Scope scopeAnnotation = clazz.getAnnotation(Scope.class);
                                    beanDefinition.setScope(scopeAnnotation.value());
                                } else {
                                    beanDefinition.setScope("singleton");
                                }

                                beanDefinition.setType(clazz);

                                Component component = clazz.getAnnotation(Component.class);
                                String beanName = component.value();
                                if (beanName.equals("")) {
                                    beanName = Introspector.decapitalize(clazz.getSimpleName());
                                }

                                beanDefinitionMap.put(beanName, beanDefinition);
                            }
                        } catch (ClassNotFoundException e) {
                            e.printStackTrace();
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        // 单例bean初始化
        for (String beanName : beanDefinitionMap.keySet()) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if (beanDefinition.getScope().equals("singleton")) {
                Object bean = createBean(beanName, beanDefinition);
                singletonObjects.put(beanName, bean);
            }
        }
    }

    private Object createBean(String beanName, BeanDefinition beanDefinition) {
        Class clazz = beanDefinition.getType();
        try {
            Object instance = clazz.getConstructor().newInstance();

            // 依赖注入
            for (Field f : clazz.getDeclaredFields()) {
                if (f.isAnnotationPresent(Autowired.class)) {
                    f.setAccessible(true);
                    f.set(instance, getBean(f.getName()));
                }
            }

            // BeanNameAware回调
            if (instance instanceof BeanNameAware) {
                ((BeanNameAware) instance).setBeanName(beanName);
            }

            // 前置处理器
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                beanPostProcessor.postProcessBeforeInitialization(beanName, instance);
            }

            // 初始化
            if (instance instanceof InitializingBean) {
                ((InitializingBean) instance).afterPropertiesSet();
            }

            // 后置处理器
            for (BeanPostProcessor beanPostProcessor : beanPostProcessorsList) {
                instance = beanPostProcessor.postProcessAfterInitialization(beanName, instance);
            }


            return instance;
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Object getBean(String beanName) {
        BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
        if (beanDefinition == null) {
            throw new NullPointerException();
        } else {
            String scope = beanDefinition.getScope();
            if (scope.equals("singleton")) {
                Object bean = singletonObjects.get(beanName);
                if (bean == null) {
                    Object o = createBean(beanName, beanDefinition);
                    singletonObjects.put(beanName, o);
                }
                return bean;
            } else {
                // 多例
                return createBean(beanName, beanDefinition);
            }
        }
    }
}









