package com.aem.core.services;

import javax.jcr.Node;

import org.json.JSONObject;

public interface NodeCrudOperation {

	public JSONObject getProperties(String nodePath);

	public Node createNode(String parentPath, String nodeName);

	public boolean deleteNode(String parentPath);

	public boolean checkNode(String parentPath);

	public void update(String nodePath, String name, String desc);

	public void updateNodeName(String nodePath,String newName);

}
