package com.aem.core.models.impl;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;

import com.aem.core.models.User;

@Model(adaptables = Resource.class, adapters = User.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class UserImpl implements User {

	@Inject
	private String fname;

	@Inject
	private String lname;

	@Inject
	private boolean professor;

	@Override
	public String getFirstName() {
		// TODO Auto-generated method stub
		return fname;
	}

	@Override
	public String getLastName() {
		// TODO Auto-generated method stub
		return lname;
	}

	@Override
	public boolean getIsProfessor() {
		// TODO Auto-generated method stub
		return professor;
	}

}
