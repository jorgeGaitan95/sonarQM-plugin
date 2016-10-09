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
 * @author Jorge Gaitán
 */
@Rule(key="RelationshipUseCaseConnectedCheck",
name="Relationships must be connected",
priority= Priority.BLOCKER)
@BelongsToProfile(title = CheckRepository.SONAR_WAY_PROFILE_NAME, priority = Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("5min")
public class RelationshipUseCaseConnectedCheck extends AbstractXmlCheck{
	public static final String MESSAGE="Check that the connection point to a node in both the source and target";

	private void ValidateRelationship(Node node){
		for(Node sibling=node.getFirstChild();sibling!= null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_RELATION_USECASE))
			{
				NamedNodeMap attribute=sibling.getAttributes();
				if(attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE)!=null){
					String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
					if(source.equals("")||source==null)
						createViolation(getWebSourceCode().getLineForNode(sibling), MESSAGE);
					else{
						if(attribute.getNamedItem(getVariables().ATTRIBUTE_TARGET)!=null){
							String target=attribute.getNamedItem(getVariables().ATTRIBUTE_TARGET).getNodeValue();
							if(target.equals("")||target==null)
								createViolation(getWebSourceCode().getLineForNode(sibling), MESSAGE);
						}else{
							createViolation(getWebSourceCode().getLineForNode(sibling), MESSAGE);
						}
					}
				}
				else
				{
					createViolation(getWebSourceCode().getLineForNode(sibling), MESSAGE);
				}
			}
		}
		for (Node child=node.getFirstChild();child!=null;child=child.getNextSibling())
		{
			if(child.getNodeType()==Node.ELEMENT_NODE&&child.getNodeName().equals(getVariables().USE_CASE_DIAGRAM_NAME))
			{
				ValidateRelationship(child);
			}
		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

		Document document = getWebSourceCode().getDocument(false);
		if (document.getDocumentElement() != null) {
			ValidateRelationship(document.getDocumentElement());
		}
	}

}
