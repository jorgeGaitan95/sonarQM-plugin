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
import org.w3c.dom.NodeList;

@Rule(key="DuplicateOperationsClassCheck",
name="there should be no duplicate Operations",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("30min")
public class DuplicateOperationsClassCheck extends AbstractXmlCheck {
	public static final String MESSAGE="Check that no exist two or more operations whit the same name and parameters";
	public String getAttributesType(Node node){
		String attributeType="";
		NodeList childNodes=node.getChildNodes();
		int length=childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node nodoAux=childNodes.item(i);
			if(nodoAux.getNodeName().equals(getVariables().NODE_OPERATION_CLASS))
			{
				NamedNodeMap attribute=nodoAux.getAttributes();
				if(attribute.getNamedItem(getVariables().ATTRIBUTE_TYPE_ATTRIBUTENODE)!=null){
					String type=attribute.getNamedItem(getVariables().ATTRIBUTE_TYPE_ATTRIBUTENODE).getNodeValue();
					attributeType+=type;
				}
			}
		}
		return attributeType;
	}
	
	public void validateOperationsClass(Node nodeClass)
	{
		ArrayList<String> nombreMetodos= new ArrayList<String>();
		NodeList childNodes=nodeClass.getChildNodes();
		int length=childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node nodoAux=childNodes.item(i);
			if(nodoAux.getNodeName().equals(getVariables().NODE_OPERATION_CLASS))
			{
				String cadenaAux="";
				NamedNodeMap attribute=nodoAux.getAttributes();
				if(attribute.getNamedItem(getVariables().ATTRIBUTE_NAME)!=null){
					cadenaAux+=attribute.getNamedItem(getVariables().ATTRIBUTE_NAME).getNodeValue();
					if(nodoAux.hasChildNodes())
						cadenaAux+= getAttributesType(nodoAux);
					if(nombreMetodos.contains(cadenaAux)){
						createViolation(getWebSourceCode().getLineForNode(nodoAux), MESSAGE);
					}else{
						nombreMetodos.add(cadenaAux);
					}
				}
			}
		}
	}
	public void checkOperationsClass(Node node){
		
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_CLASS))
			{
				validateOperationsClass(sibling);
			}
		}

		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().CLASS_DIAGRAM_NAME))
			{
				NamedNodeMap attribute=child.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				if(type.equals(getVariables().TYPE_CLASS)){
					checkOperationsClass(child);
				}
			}

		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

		Document document = getWebSourceCode().getDocument(false);
		if (document.getDocumentElement() != null) {
			checkOperationsClass(document.getDocumentElement());
		}
	}

}
