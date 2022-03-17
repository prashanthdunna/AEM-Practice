package com.aem.core.models;

import java.util.Optional;

import javax.inject.Inject;

import org.apache.sling.api.resource.Resource;
import org.apache.sling.models.annotations.DefaultInjectionStrategy;
import org.apache.sling.models.annotations.Model;
import org.apache.sling.models.annotations.injectorspecific.Self;

import com.adobe.cq.dam.cfm.ContentFragment;

@Model(adaptables = Resource.class, defaultInjectionStrategy = DefaultInjectionStrategy.OPTIONAL)
public class ContentFragmentModelMovies {

	@Inject
	@Self
	private Resource resource;

	private Optional<ContentFragment> contentFragment;

}
