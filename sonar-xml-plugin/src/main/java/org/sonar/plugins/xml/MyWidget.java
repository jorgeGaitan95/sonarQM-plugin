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
package org.sonar.plugins.xml;
import org.sonar.api.web.*;
@WidgetCategory("labs")
@WidgetProperties({
	@WidgetProperty(key="max",type=WidgetPropertyType.INTEGER,defaultValue="80")
})
public final class MyWidget extends AbstractRubyTemplate implements RubyRailsWidget{

	@Override
	public String getId() {
		return "my_widget";
	}

	@Override
	public String getTitle() {
		return "mi primer widget";
	}

	@Override
	protected String getTemplatePath() {
		return "/org/sonar/my_widget.html.erb";
	}

}
