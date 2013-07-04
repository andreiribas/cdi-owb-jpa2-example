/* 
The MIT License (MIT)

Copyright (c) 2013 Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in
all copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
THE SOFTWARE.
*/
/**
 * 
 */
package com.andreiribas.cdi_owb_jpa2_example.cdi.junit;

import java.lang.reflect.Field;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import javax.enterprise.context.spi.CreationalContext;
import javax.enterprise.inject.spi.Bean;
import javax.enterprise.inject.spi.BeanManager;
import javax.inject.Inject;

import org.apache.webbeans.config.WebBeansContext;
import org.apache.webbeans.spi.ContainerLifecycle;
import org.junit.runners.BlockJUnit4ClassRunner;
import org.junit.runners.model.InitializationError;
import org.junit.runners.model.TestClass;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public class OWBJUnit4Runner extends BlockJUnit4ClassRunner {
 
    private final ContainerLifecycle container;
    
    private final BeanManager beanManager;
    
    public OWBJUnit4Runner(final Class<?> testClass) throws InitializationError {
    	
        super(testClass);
    
        long startCdiTime = System.nanoTime();
        		
        WebBeansContext currentInstance = WebBeansContext.currentInstance();
        
        this.container = currentInstance.getService(ContainerLifecycle.class);
        
        this.container.startApplication(null);
        
        this.beanManager = container.getBeanManager();
        
        long endCdiTime = System.nanoTime();
        
        System.out.println(String.format("OWB setup in %d miliseconds.", TimeUnit.NANOSECONDS.toMillis(endCdiTime - startCdiTime)));
                
    }
    
 
    @Override
    protected Object createTest() throws Exception {
    	
    	TestClass testClassJunitModel = getTestClass();
    	
    	Class<?> testClass = testClassJunitModel.getJavaClass();
    	
    	Object testInstance = testClass.newInstance();
    	
    	Field[] fields = testClass.getDeclaredFields();
        
        for(Field field: fields) {
        	
        	field.setAccessible(true);
        	
        	Inject injectAnnotation = field.getAnnotation(Inject.class);
        	
        	if(injectAnnotation != null) {
        		
        		Class<?> fieldType = field.getType();
        
        		long beanResolutionStartTime = System.nanoTime();
                        		
        		Object fieldValueInjectedFromCdi = getBeanInstance(fieldType);
        		
        		long beanResolutionEndTime = System.nanoTime();
        		
        		System.out.println(String.format("Bean resolution of class %s in %d miliseconds.", fieldType.getSimpleName(), TimeUnit.NANOSECONDS.toMillis(beanResolutionEndTime - beanResolutionStartTime)));
                
        		field.set(testInstance, fieldValueInjectedFromCdi);
        	
        	}
        	
        }
        
        return testInstance;
    
    }
      
    @SuppressWarnings("unchecked")
	private <T> T getBeanInstance(Class<T> type) {
    	
    	Set<Bean<?>> beans = beanManager.getBeans(type);
    	
    	Bean<?> bean = beans.iterator().next();
    	
    	CreationalContext<?> creationalContext = beanManager.createCreationalContext(bean);
    	
    	bean = beanManager.resolve(beans);
    	
    	T contextualReference = (T) beanManager.getReference(bean, type, creationalContext);
    
    	return contextualReference;
    	
    }
      
}