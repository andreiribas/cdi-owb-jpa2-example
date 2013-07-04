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
package com.andreiribas.cdi_owb_jpa2_example.dao.jpa;


import java.util.Date;

import javax.inject.Inject;

import junit.framework.TestCase;

import org.junit.Test;
import org.junit.runner.RunWith;

import com.andreiribas.cdi_owb_jpa2_example.cdi.junit.OWBJUnit4Runner;
import com.andreiribas.cdi_owb_jpa2_example.dao.UserDao;
import com.andreiribas.cdi_owb_jpa2_example.domain.User;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@RunWith(OWBJUnit4Runner.class)
public class UserDaoTest {
		
	@Inject
	private UserDao fixture;

	@Test
	public void testCreateNewUser() {
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at ", new Date().getTime()));
		
		newUser = fixture.save(newUser);
		
		TestCase.assertNotNull(newUser);
		
	}
	
	@Test
	public void testCreateNewUserAndUpdateItsNameWithoutMerge() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at %s.", new Date().getTime()));
		
		fixture.save(newUser);
		
		//new user is managed by jpa now.
		
		fixture.getEntityManager().getTransaction().commit();
		
		//newUser is updated without using merge, because it`s already managed
		newUser.setName(String.format("Old User updated at %s.", new Date().getTime()));
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		TestCase.assertNotNull(id);
		
		User updatedUser = fixture.find(id);
		
		TestCase.assertNotNull(updatedUser);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
	@Test
	public void testCreateNewUserAndUpdateItsNameWithMerge() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
			
		TestCase.assertNotSame(-1L, numberOfUsers);
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at %s.", new Date().getTime()));
		
		fixture.save(newUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		TestCase.assertNotNull(id);
		
		User updatedUser = new User(id);
		
		updatedUser.setName(String.format("Old User updated at %s.", new Date().getTime()));
			
		//newUser is updated using merge, because it`s already managed
		
		updatedUser = fixture.merge(updatedUser);
		
		TestCase.assertNotNull(updatedUser);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		updatedUser = fixture.find(id);
		
		TestCase.assertTrue(updatedUser.getName().startsWith("Old User updated at "));
		
		TestCase.assertEquals(numberOfUsers + 1, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
	@Test
	public void testCreateNewUserAndDeleteIt() {
		
		fixture.getEntityManager().getTransaction().begin();
		
		long numberOfUsers = fixture.count();
		
		User newUser = new User();
		
		newUser.setName(String.format("New User created at ", new Date().getTime()));
		
		newUser = fixture.save(newUser);
		
		TestCase.assertNotNull(newUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		Long id = newUser.getId();
		
		User updatedUser = fixture.find(id);
		
		fixture.delete(updatedUser);
		
		fixture.getEntityManager().getTransaction().commit();
		
		fixture.getEntityManager().getTransaction().begin();
		
		TestCase.assertEquals(numberOfUsers, fixture.count());
		
		fixture.getEntityManager().getTransaction().commit();
		
	}
	
}
