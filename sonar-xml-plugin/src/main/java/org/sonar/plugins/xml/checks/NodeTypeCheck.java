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
 * The nodes must be of a valid type
 * @author Jorge
 *
 */
@Rule(key="NodeTypeCheck",
name="Node Type should be appropriate",
priority= Priority.BLOCKER)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.BLOCKER)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("60min")
public class NodeTypeCheck extends AbstractXmlCheck{


	
	private boolean isTipoValido(String tipoNodo){
		if(tipoNodo.equals(getVariables().CTT_ABSTRACTION_TASK)||tipoNodo.equals(getVariables().CTT_SYSTEM_TASK)||
			tipoNodo.equals(getVariables().CTT_USER_TASK)||tipoNodo.equals(getVariables().CTT_INTERACTION_TASK)){
				return true;
		}else
			return false;
	}

	private void validateArbol(TreeAbstract arbol){
		ArrayList <Nodo> nodos=arbol.getArbol();
		for (int i = 0; i < nodos.size(); i++) {

			if(!isTipoValido(nodos.get(i).getTipo()))
			{
				if(nodos.get(i).getNodoReferencia()!=null)
				{
					createViolation(getWebSourceCode().getLineForNode(nodos.get(i).getNodoReferencia())
							,"Change nodes for a valid type: abstract, system, user, or interactive");
				}
				else{
					createViolation(1,"Change nodes for a valid type: abstract, system, user, or interactive");
				}
			}
		}
	}


	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);
		if (getArbol()!=null) {
			validateArbol(getArbol());
		}

	}

}
