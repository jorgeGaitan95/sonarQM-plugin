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

@Rule(key="InheritedDuplicateAttributesCheck",
name="There should be no duplicate attributes inherited classes",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("30min")
public class InheritedDuplicateAttributesCheck extends AbstractXmlCheck{

	public static final String MESSAGE="Check that classes do not duplicate attributes with respect to the class they inherit";
	public boolean hasRepeatedAttributes(ArrayList<String>attributesSource,ArrayList<String>attributesTarget){
		for (int i = 0; i < attributesTarget.size(); i++) {
			if(attributesSource.contains(attributesTarget.get(i)))
				return true;
		}
		return false;
	}
	public ArrayList<String> getAttributes(Node node){
		ArrayList<String> attributes= new ArrayList<String>();
		NodeList childNodes=node.getChildNodes();
		int length=childNodes.getLength();
		for (int i = 0; i < length; i++) {
			Node nodoAux=childNodes.item(i);
			if(nodoAux.getNodeName().equals(getVariables().NODE_ATTRIBUTE_CLASS))
			{
				NamedNodeMap attribute=nodoAux.getAttributes();
				String nombre=attribute.getNamedItem(getVariables().ATTRIBUTE_NAME).getNodeValue();
				if(nombre!=null){
					attributes.add(nombre);
				}
			}
		}
		return attributes;
	}
	public void validateAttributes(Node classDiagram,int posSource, int posTarget){
		int contador=0;
		NodeList childNodes=classDiagram.getChildNodes();
		int length=childNodes.getLength();
		Node classSource=null;
		Node classTarget=null;
		for (int i = 0; i < length; i++) {
			Node nodoAux=childNodes.item(i);
			if(nodoAux.getNodeName().equals(getVariables().NODE_CLASS))
			{
				if(contador==posSource)
				{
					classSource=nodoAux;
				}
				if(contador==posTarget)
					classTarget=nodoAux;
				if(classSource!=null&&classTarget!=null)
					break;
				else
					contador++;
			}
		}
		if(classSource!=null&&classTarget!=null){
			ArrayList<String> attributesSource=getAttributes(classSource);
			ArrayList<String> attributesTarget=getAttributes(classTarget);
			if(hasRepeatedAttributes(attributesSource, attributesTarget)){
				createViolation(getWebSourceCode().getLineForNode(classTarget), MESSAGE);
			}
		}
	}
	public void checkRelationship(Node node){

		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_RELATIONSHIP_CLASS))
			{
				NamedNodeMap attribute=sibling.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				if(type!=null&&type.equals(getVariables().CLASS_INHERITED_RELATION)){
					String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
					String target=attribute.getNamedItem(getVariables().ATTRIBUTE_TARGET).getNodeValue();
					if(source!=null&&target!=null)
					{
						String[] sourceArray=source.split("\\.");
						int posSource=Integer.parseInt(sourceArray[sourceArray.length-1]);
						String[] targetArray=target.split("\\.");
						int posTarget=Integer.parseInt(targetArray[targetArray.length-1]);
						validateAttributes(node,posSource,posTarget);
					}
				}
			}
		}

		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().CLASS_DIAGRAM_NAME))
			{
				NamedNodeMap attribute=child.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				if(type.equals(getVariables().TYPE_CLASS)){
					checkRelationship(child);
				}
			}

		}

	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

		Document document = getWebSourceCode().getDocument(false);
		if (document.getDocumentElement() != null) {
			checkRelationship(document.getDocumentElement());
		}
	}

}
