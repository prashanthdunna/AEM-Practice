package com.aem.core.services.impl;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeType;

import org.apache.commons.collections4.MultiValuedMap;
import org.apache.sling.api.resource.LoginException;
import org.apache.sling.api.resource.ModifiableValueMap;
import org.apache.sling.api.resource.Resource;
import org.apache.sling.api.resource.ResourceResolver;
import org.json.JSONObject;
import org.osgi.service.component.annotations.Component;
import org.osgi.service.component.annotations.Reference;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aem.core.services.FetchResponseService;
import com.aem.core.services.NodeCrudOperation;

@Component(service = NodeCrudOperation.class)
public class NodeCrudOperationsImpl implements NodeCrudOperation {

	private static final Logger Log = LoggerFactory.getLogger(NodeCrudOperationsImpl.class);

	@Reference
	private FetchResponseService fres;

	@Override
	public JSONObject getProperties(String nodePath) {

		JSONObject obj = new JSONObject();

		try {
			ResourceResolver resolver;

			resolver = fres.getRsrc();

			Resource resource = resolver.getResource(nodePath);

			Node currentNode = resource.adaptTo(Node.class);

			if (currentNode.hasProperties()) {

				PropertyIterator pr = currentNode.getProperties();

				while (pr.hasNext()) {

					Property pr1 = pr.nextProperty();

					obj.put(pr1.getName(), pr1.getValue());

				}

			}

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return obj;

	}

	@Override
	public Node createNode(String parentPath, String nodeName) {

		try {
			ResourceResolver resolver = fres.getRsrc();

			Session session = resolver.adaptTo(Session.class);

			Node rootNode = session.getNode(parentPath);
			Node newNode = rootNode.addNode(nodeName, NodeType.NT_UNSTRUCTURED);

			session.save();
			session.logout();

			return newNode;

		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;

	}

	@Override
	public boolean deleteNode(String Path) {

		boolean checkData = false;
		try {
			ResourceResolver resolver;

			resolver = fres.getRsrc();

			Session session = resolver.adaptTo(Session.class);

			checkData = checkNode(Path);

			if (checkData) {

				session.getNode(Path).remove();
			} else {
				return checkData;
			}

			session.save();

			session.logout();

		} catch (PathNotFoundException e) {
			e.printStackTrace();
		} catch (RepositoryException e) {

		} catch (LoginException e) {
			// TODO: handle exception
		}
		return checkData;

	}

	@Override
	public boolean checkNode(String path) {

		ResourceResolver resolver;
		try {
			resolver = fres.getRsrc();
			Resource resource = resolver.getResource(path);

			if (resource != null) {

				return true;
			}
		} catch (LoginException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;
	}

	public boolean updateNode(String path, String nodeName) {

		try {
			ResourceResolver resolver = fres.getRsrc();
			Session session = resolver.adaptTo(Session.class);
			
			ModifiableValueMap mvp = resolver.adaptTo(null)
			
			if (session != null) {

				Node currentPath = session.getNode(path);

				if (currentPath.hasNodes()) {

					NodeIterator details = currentPath.getNodes();

					while (details.hasNext()) {

						Node currentNode = details.nextNode();

							Node fetchNode = currentNode.getNode(nodeName);
						

					}
				}

			}

		} catch (LoginException | RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return false;

	}
}
