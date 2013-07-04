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

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;

import com.andreiribas.cdi_owb_jpa2_example.dao.UserDao;
import com.andreiribas.cdi_owb_jpa2_example.domain.User;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 */
@Named
@ApplicationScoped
public class UserJpaDao extends GenericJpaDao<User, Long> implements UserDao {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2163306125212923115L;
	
	protected UserJpaDao() {}
	
	@Inject
	public UserJpaDao(EntityManager entityManager) {
		super(entityManager, User.class);
	}
	
}
