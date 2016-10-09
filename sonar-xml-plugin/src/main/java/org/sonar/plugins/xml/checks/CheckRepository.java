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

import java.util.List;

import com.google.common.collect.ImmutableList;

public class CheckRepository {

  public static final String REPOSITORY_KEY = "xml";
  public static final String REPOSITORY_NAME = "SonarQube";
  public static final String SONAR_WAY_PROFILE_NAME = "Sonar way";

  private CheckRepository() {
  }

  public static List<AbstractXmlCheck> getChecks() {
    return ImmutableList.of(
      new IllegalTabCheck(),
      new IndentCheck(),
      new NewlineCheck(),
      new XmlSchemaCheck(),
      new CharBeforePrologCheck(),
      new XPathCheck(),
      new EmptySourceCheck(),
      new EmptyNodeNameCheck(),
      new ParentNodeCheck(),
      new AmbiguitySiblingNodesCheck(),
      new OnlyParentCheck(),
      new RelationCheck(),
      new ChildNodeCheck(),
      new OnlySourceCheck(),
      new OnlyTargetCheck(),
      new RedundanciaCiclicaCheck(),
      new NodeTypeCheck(),
      new RelationTypeCheck(),
      new ValidateNodeNameCheck(),
      new ValidateRelationshipNameCheck(),
      new SingleNodeNameCheck(),
      new AmbiguityEnablingSiblingNodesCheck(),
      new AmbiguityDisablingSiblingNodesCheck(),
      new AbstractChildNodesCheck(),
      new EmptyClassCheck(),
      new EmptyClassNameCheck(),
      new AttributeEmptyTypeCheck(),
      new AttributeValidTypeCheck(),
      new ElementInvalidNameCheck(),
      new RelationshipConnectedCheck(),
      new NumberOperationClassCheck(),
      new ClassConstructorCheck(),
      new OperationEmptyCheck(),
      new MaxSevenParametreClassCheck(),
      new OnlyOneReturnClassCheck(),
      new SameAttributeClassCheck(),
      new DuplicateOperationsClassCheck(),
      new CheckElementsClassCheck(),
      new InheritedDuplicateAttributesCheck(),
      new OperationLinesCodeCheck(),
      new StartEventCheck(),
      new EmptyNameCheck(),
      new InvalidNameCheck(),
      new RelationshipUseCaseConnectedCheck(),
      new RelationsTypeCheck(),
      new UseCaseConnectedCheck(),
      new ActorConnectedCheck(),
      new ActorConnectedUseCaseCheck(),
      new NumberUseCaseCheck(),
      new NumberActorsCheck(),
      new NumberCommunicationsCheck(),
      new EmptyTableCheck(),
      new TableEmptyNameCheck(),
      new AttributeTypeCheck(),
      new RelationConnectedCheck(),
      new ReservedWordsToNameElementsCheck(),
      new AttributeswithInvalidTypeCheck(), 
      new TreeLuytenCheck());
  }

  public static List<Class> getCheckClasses() {
    ImmutableList.Builder<Class> builder = ImmutableList.builder();

    for (AbstractXmlCheck check : getChecks()) {
      builder.add(check.getClass());
    }

    return builder.build();
  }

}
