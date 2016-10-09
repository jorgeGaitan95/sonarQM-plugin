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

@Rule(key="ReservedWordsToNameElements",
name="Do not use reserved words as names of the elements",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("30min")
public class ReservedWordsToNameElementsCheck extends AbstractXmlCheck {

	
	public static final String MESSAGE="Check that the element name is not a reserved word";
	
	public void evaluateAttributesClass(Node node){
		
		ArrayList<String> nombresReservados = new ArrayList<String>();
		nombresReservados.add("String");
		nombresReservados.add("Int");
		nombresReservados.add("Void");
		
		
		NodeList childNodes=node.getChildNodes();
		int length=childNodes.getLength();
		
		NamedNodeMap attributeTabla=node.getAttributes();
		String nombreTabla=attributeTabla.getNamedItem(getVariables().ATTRIBUTE_NAME_TABLE).getNodeValue();
		
		if (nombresReservados.contains(nombreTabla)){
			createViolation(getWebSourceCode().getLineForNode(node), MESSAGE);
		}
		
		for (int i = 0; i < length; i++) {
			Node nodoAux=childNodes.item(i);
			if(nodoAux.getNodeType()==Node.ELEMENT_NODE&&nodoAux.getNodeName().equals(getVariables().NODE_ATTRIBUTE_ER))
			{
				NamedNodeMap attribute=nodoAux.getAttributes();
				if(attribute.getNamedItem(getVariables().ATTRIBUTE_NAME_ATRIBUTE_TABLE)!=null){
					String nombreAtributo=attribute.getNamedItem(getVariables().ATTRIBUTE_NAME_ATRIBUTE_TABLE).getNodeValue();
					if(nombresReservados.contains(nombreAtributo))
						createViolation(getWebSourceCode().getLineForNode(nodoAux), MESSAGE);
				}
				else{
					createViolation(getWebSourceCode().getLineForNode(nodoAux), MESSAGE);
				}
			}
		}
	}
	
	public void validateAttributeType(Node node){
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			
			if(sibling.getNodeName().equals(getVariables().NODE_TABLE)&&sibling.hasChildNodes())
			{
				evaluateAttributesClass(sibling);
			}
		}
		
		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().ER_DIAGRAM_NAME))
			{
					validateAttributeType(child);
			}
			
		}
	}

	
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		
		
		
		setWebSourceCode(xmlSourceCode);

	    Document document = getWebSourceCode().getDocument(false);
	    if (document.getDocumentElement() != null) {
	    	validateAttributeType(document.getDocumentElement());
	    }
	}
	
	
	
}
