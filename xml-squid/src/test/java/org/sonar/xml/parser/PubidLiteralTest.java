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
package org.sonar.xml.parser;

import com.sonar.sslr.api.Grammar;
import org.junit.Test;
import org.sonar.xml.api.XmlGrammar;

import static org.sonar.sslr.tests.Assertions.assertThat;

public class PubidLiteralTest {

  Grammar g = XmlGrammar.createGrammarBuilder().build();

  @Test
  public void ok() {
    assertThat(g.rule(XmlGrammar.PUBID_LITERAL))
        .matches("\"\"")
        .matches("\"foo\"")
        .matches("\"foo'bar\"")

        .matches("''")
        .matches("'foo'")

        .notMatches("\"foo<bar\"")
        .notMatches("'foo<bar'")
        .notMatches("'foo\"bar'")

        .notMatches("'foo'bar'")
        .notMatches("\"foo\"bar\"")

        .notMatches("'\"")
        .notMatches("\"'");
  }

}
