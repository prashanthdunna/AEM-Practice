package com.aem.core.services.impl;

import javax.jcr.Node;
import javax.jcr.NodeIterator;
import javax.jcr.PathNotFoundException;
import javax.jcr.Property;
import javax.jcr.PropertyIterator;
import javax.jcr.RepositoryException;
import javax.jcr.Session;
import javax.jcr.nodetype.NodeType;

import org.apache.sling.api.resource.LoginException;
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

	@Override
	public void update(String nodePath, String name, String desc) {

		Log.info(name);
		Log.info(nodePath);

		JSONObject json = new JSONObject();

		try {
			ResourceResolver resolver = fres.getRsrc();

			Session session = resolver.adaptTo(Session.class);

			if (session != null) {

				Node currentNode = session.getNode(nodePath);

				Log.info(currentNode.toString());

				if (currentNode.hasNodes()) {

					NodeIterator itr = currentNode.getNodes();

					while (itr.hasNext()) {

						Node currentDetail = itr.nextNode();

						Log.info(name);

						if (currentDetail.hasProperty(desc)) {

							currentDetail.setProperty("title", "Uma Mahesh");

						}

					}

				}

			}

			session.save();
			session.logout();
		} catch (LoginException |

				RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	@Override
	public void updateNodeName(String nodePath, String newName) {

		try {
			ResourceResolver resolver = fres.getRsrc();
			Session se = resolver.adaptTo(Session.class);

			if (se != null) {

				Resource currentResource = resolver.getResource(nodePath);

				Node node = currentResource.adaptTo(Node.class);

				node.getSession().move(node.getPath(), node.getParent().getPath() + "/" + newName);

//				Log.info(node.toString());

//				Node textNode = node.getNode(name);
//
//				Log.info(textNode.toString());
//
//				textNode.update(newName);

//				if (node.hasNodes()) {
//
//					NodeIterator itr = node.getNodes();
//
//					while (itr.hasNext()) {
//
//						Node currentNode = itr.nextNode();
//
//						//Log.info(currentNode.toString());
//
//					
//						
//					
//					}
//
//				}

				//
//				Log.info(fetchNodes.toString());
//
//				Node currentNode = fetchNodes.getNode(name);
//
//				Log.info(currentNode.toString() + "Hello world");

			}

			se.save();
			se.logout();

		} catch (LoginException | RepositoryException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
