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
/**
 * @author Jorge Gaitan
 *
 */
@Rule(key="TreeLuytenCheck",
name="The tree should be able to become priority tree",
priority= Priority.MAJOR)
@BelongsToProfile(title=CheckRepository.SONAR_WAY_PROFILE_NAME,priority=Priority.MAJOR)
@SqaleSubCharacteristic(RulesDefinition.SubCharacteristics.ERRORS)
@SqaleConstantRemediation("30min")
public class TreeLuytenCheck extends AbstractXmlCheck{

	private void validateAbstractNode(TreeAbstract arbol){
		ArrayList <Nodo> nodos=arbol.getArbol();
		for (int i = 0; i < nodos.size(); i++) {
			Nodo nodoAux=nodos.get(i);
			if(nodoAux.getSiguienteRelation().equals(getVariables().CTT_ENABLING)&&nodoAux.getSiguienteNodo()>0){
				nodoAux=nodos.get(nodoAux.getSiguienteNodo());
				if(nodoAux.getSiguienteNodo()>0&&nodoAux.getSiguienteRelation().equals(getVariables().CTT_DISABLING)){
					nodoAux=nodos.get(nodoAux.getSiguienteNodo());
					if(nodoAux.getSiguienteNodo()>0&&nodoAux.getSiguienteRelation().equals(getVariables().CTT_INDEPENDENTCONCURRENCY)){
						nodoAux=nodos.get(nodoAux.getSiguienteNodo());
						if(nodoAux.getSiguienteNodo()>0&&nodoAux.getSiguienteRelation().equals(getVariables().CTT_INDEPENDENTCONCURRENCY)){
							createViolation(getWebSourceCode().getLineForNode(nodos.get(i).getNodoReferencia())
									,"Review the tree relevance, in order to validate the sequence order of the tree this should be able to become a tree of priority, should not be able to transform, indicate that there are ambiguities between nodes.");
						}
					}
				}
			}
		}
	}
	@Override
	public void validate(XmlSourceCode xmlSourceCode) {
		setWebSourceCode(xmlSourceCode);
		if (getArbol()!=null) {
			validateAbstractNode(getArbol());
		}
	}

}
