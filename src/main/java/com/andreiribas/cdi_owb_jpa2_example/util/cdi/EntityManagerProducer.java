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
package com.andreiribas.cdi_owb_jpa2_example.util.cdi;

import java.io.Serializable;

import javax.enterprise.inject.Produces;
import javax.inject.Singleton;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

import org.apache.log4j.Logger;

/**
 * @author Andrei Goncalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
public class EntityManagerProducer implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6934038219040491371L;

	private static final Logger LOGGER = Logger.getLogger(EntityManagerProducer.class);
	
	private static EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("TEST_DB");
	
	@Produces
	@Singleton
	public EntityManager createEntityManager() {
		try {
			return EntityManagerProducer.entityManagerFactory.createEntityManager();
		} catch(RuntimeException re) {
		
			LOGGER.fatal("Error while creating EntityManager from EntityManagerFactory.", re);
		
			throw re;
		
		}
		
	}
	
}
