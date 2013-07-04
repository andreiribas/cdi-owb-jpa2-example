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
package com.andreiribas.cdi_owb_jpa2_example.domain;
/**
 * 
 */

import java.io.Serializable;

/**
 * @author Andrei Gonçalves Ribas <andrei.g.ribas@gmail.com>
 *
 **/
public abstract class AbstractDomain<TypeId extends Serializable> implements Serializable {
	
	private static final long serialVersionUID = -2100306541768310750L;
	
	public AbstractDomain() {}

	public AbstractDomain(TypeId id) {
		
		setId(id);
		
	}

	public abstract TypeId getId();

	public abstract void setId(TypeId id);

	@Override
	public String toString() {
		
		String toString = super.toString();
		
		if(this.getId() != null) {
			toString = getId().toString();
		}
		
		return toString;
		
	}

	@Override
	public int hashCode() {
		
		if (getId() != null) {
			
			return getId().hashCode() * 71;
			
		}
		else {
			
			return super.hashCode();
			
		}
		
	}

	@Override
	public boolean equals(Object other) {
		
		if (this == other)
			return true;
		
		if (!(this.getClass().isInstance(other)))
			return false;
		
		@SuppressWarnings("unchecked")
		final TypeId otherId = (TypeId) ((AbstractDomain<TypeId>) other).getId();
		
		return (getId() != null && getId() == otherId);
		
	}
	
}
