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
 * @author Jorge
 *
 */
@Rule(key="OnlyParentCheck",
name="Only one node can be root",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("30min")
public class OnlyParentCheck extends AbstractXmlCheck{

	public static final String MESSAGE="Existe mas de un nodo raiz en este diagrama";

	public boolean isOnlyParent(int posicionNodo,Node padre){
		for (int i = 0; i < padre.getChildNodes().getLength(); i++) {
			if(padre.getChildNodes().item(i).getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				NamedNodeMap attribute=padre.getChildNodes().item(i).getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				if(type.equals(getVariables().CTT_AGREGATION))
				{
					String target=attribute.getNamedItem(getVariables().ATTRIBUTE_TARGET).getNodeValue();
					if(!target.equals("")){
						String[] splitSource=target.split("\\.");
						int posicionNodoTarget=Integer.parseInt(splitSource[splitSource.length-1]);
						if (posicionNodo==posicionNodoTarget) {
							return false;
						}
					}
				}
			}
		}
		return true;
		//ENE STE PUNTO SE PUDE DECIR QUE EN NODO EN LA "posicionNode" ES UNA RAÍZ DEL ÁRBOL
		
	}
	
	public void validateOnlyParent(Node node,int cantidad){
		int contador=0;
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_LIST_TASK_CTT))
			{
				//SI ESTE LA CONDICION DENTOR DEL IF ES TRUE, SIBILING ES UN NODO RAIZ
				if(isOnlyParent(contador,sibling.getParentNode()))
					cantidad+=1;
				if(cantidad>1)
				{
					createViolation(getWebSourceCode().getLineForNode(node), MESSAGE);
					return;
				}
				contador++;
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
						validateOnlyParent(child,cantidad);
					}
				}
				else
				validateOnlyParent(child,cantidad);
			}
			
		}
	}


	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

		Document document = getWebSourceCode().getDocument(false);
		if (document.getDocumentElement() != null) {
			validateOnlyParent(document.getDocumentElement(),0);
		}

	}

}

