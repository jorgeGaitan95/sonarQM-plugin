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
import org.apache.xalan.xsltc.compiler.sym;
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
 * 
 * @author Jorge
 *
 */
@Rule(key="AmbiguityEnabligSiblingNodesCheck",
name="There should be no enabling ambiguity between sibling nodes",
priority= Priority.MAJOR)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("10min")
public class AmbiguityEnablingSiblingNodesCheck extends AbstractXmlCheck{
	 
	public boolean validateNode(Node nodo, String target)
	{
		NamedNodeMap attribute=nodo.getAttributes();
		String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
		String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
		if(type.equals(getVariables().CTT_ENABLING)&&source.equals(target))
			return true;
		else
			return false;
	}
	
	private boolean validateNextRelation(Node node, String target) {
		
		for (Node psibling =node.getPreviousSibling(); psibling!=null;psibling=psibling.getPreviousSibling()) {
			if(psibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				if(validateNode(psibling, target))
					return true;
			}
		}
		for (Node nsibling= node.getNextSibling();nsibling!=null;nsibling=nsibling.getNextSibling())
		{
			if(nsibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				if(validateNode(nsibling, target))
					return true;
			}
		}
		
		return false;
	}
	
	private void validateRelationTask(Node node)
	{
		
		for(Node sibling=node.getFirstChild();sibling!=null;sibling=sibling.getNextSibling()){
			if(sibling.getNodeName().equals(getVariables().NODE_LIST_RELATION_TASK))
			{
				NamedNodeMap attribute=sibling.getAttributes();
				String type=attribute.getNamedItem(getVariables().ATTRIBUTE_XSI_TYPE).getNodeValue();
				if(type.equals(getVariables().CTT_INDEPENDENTCONCURRENCY))
				{
					String source=attribute.getNamedItem(getVariables().ATTRIBUTE_SOURCE).getNodeValue();
					String target=attribute.getNamedItem(getVariables().ATTRIBUTE_TARGET).getNodeValue();
					
					if(validateNextRelation(sibling, target))
					{ 
						createViolation(getWebSourceCode().getLineForNode(sibling), "Change relationship, a node cannot be related to a next node with a Enabling relationship, if their relationship with the previous node is IndependentConcurrency");
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
						validateRelationTask(child);
					}
				}
				else
				validateRelationTask(child);
			}
			
		}
	}
	
	
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);

	    Document document = getWebSourceCode().getDocument(false);
	    if (document.getDocumentElement() != null) {
	    	validateRelationTask(document.getDocumentElement());
	    }
		
	}

}
