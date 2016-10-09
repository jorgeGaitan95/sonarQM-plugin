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
 * The names of the relations should be appropriate with respect to the pattern names defined
 * @author Jorge Gaitán
 *
 */
@Rule(key="ValidateRelationshipNameCheck",
	name="Relationships name should be appropriate",
	priority= Priority.MINOR)
	@BelongsToProfile(title = CheckRepository.SONAR_WAY_PROFILE_NAME, priority = Priority.MINOR)
	@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
	@SqaleConstantRemediation("10min")
public class ValidateRelationshipNameCheck extends AbstractXmlCheck{

	public static final String MESSAGE="Change names of the relations for a suitable with respect to the pattern names defined for: \n •	Cannot start number \n •	Only the following special characters are allowed: '-'', '_', '.' \n •	You cannot use space between words";
	 
	 
	 private void validateRelationshipName(Node node){
		 Node nodeName=null;
		 NamedNodeMap attribute=null;
		 String name=null;
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				attribute=sibling.getAttributes();
				nodeName=attribute.getNamedItem(getVariables().ATTRIBUTE_NAME);
				if(nodeName!=null){
					name=nodeName.getNodeValue();
					if(!(name.equals("")||name==null)&&!name.matches("^[a-zA-Z][a-zA-Z0-9]*"))
					createViolation(getWebSourceCode().getLineForNode(sibling), MESSAGE);
				}
			}
		}

		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().NODE_DIAGRAM_CTT))
			{
				NamedNodeMap attributo=child.getAttributes();
				if(attributo.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE)!=null){
					String type=attributo.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
					if(type.equals(getVariables().NODE_TYPE_DIAGRAM_CTT)){
						validateRelationshipName(child);
					}
				}
				validateRelationshipName(child);
			}
		}

	}
	
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		// TODO Auto-generated method stub
		setWebSourceCode(xmlSourceCode);

	    Document document = getWebSourceCode().getDocument(false);
	    if (document.getDocumentElement() != null) {
	    	validateRelationshipName(document.getDocumentElement());
	    }
	}

}
