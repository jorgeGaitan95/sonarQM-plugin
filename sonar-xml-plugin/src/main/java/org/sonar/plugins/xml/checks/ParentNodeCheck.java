/*
 * SonarQube XML Plugin
 * Copyright (C) 2010 SonarSource
 * sonarqube@googlegroups.com
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package org.sonar.plugins.xml.checks;

import java.util.ArrayList;

import org.sonar.api.server.rule.RulesDefinition;
import org.sonar.check.BelongsToProfile;
import org.sonar.check.Priority;
import org.sonar.check.Rule;
import org.sonar.squidbridge.annotations.SqaleConstantRemediation;
import org.sonar.squidbridge.annotations.SqaleSubCharacteristic;
import org.w3c.dom.Document;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
/**
 * there can be a node with a single child node
 * @author Jorge Gaitán
 *
 */
@Rule(key="ParentNodeCheck",
name="A parent must have more than one child",
priority= Priority.MAJOR)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("15min")
public class ParentNodeCheck extends AbstractXmlCheck{

	public static final String MESSAGE="Check that all nodes containing more than one child, or otherwise, do not have children";
	private int contarHijos(Node node,String padre){
		int contador=1;
		for (Node sibling=node.getNextSibling();sibling!= null;sibling=sibling.getNextSibling()) {
			if(sibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				NamedNodeMap attribute=sibling.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
				if(type.equals(getVariables().CTT_AGREGATION)&&source.equals(padre))
					contador++;
			}
		}
		return contador;
	}

	private static boolean isPadreVisitado(ArrayList<String>padresVisitados,String padre)
	{
		for (int i = 0; i < padresVisitados.size(); i++) {
			if(padresVisitados.get(i).equals(padre)){
				return true;
			}
		}
		return false;
	}

	private void validateChildsOfParent(Node node)
	{
		ArrayList<String> padresVisitados=new ArrayList<String>();
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				NamedNodeMap attribute=sibling.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
				if(type.equals(getVariables().CTT_AGREGATION) && !isPadreVisitado(padresVisitados,source))
				{
					padresVisitados.add(source);
					int contador=contarHijos(sibling, source);
					if(contador==1)
					{
						//						String nombreNodo=getVariables().getNombreNodo(source,sibling.getParentNode());
						createViolation(getWebSourceCode().getLineForNode(sibling),MESSAGE);
					}
				}
			}
		}
		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().NODE_DIAGRAM_CTT))
			{
				NamedNodeMap attribute=child.getAttributes();
				if(attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE)!=null){
					String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
					if(type.equals(getVariables().NODE_TYPE_DIAGRAM_CTT)){
						validateChildsOfParent(child);
					}
				}
				else
					validateChildsOfParent(child);
			}

		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

		Document document = getWebSourceCode().getDocument(false);
		if (document.getDocumentElement() != null) {
			validateChildsOfParent(document.getDocumentElement());
		}
	}
}
