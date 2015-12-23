/*
 * SonarSource Language Recognizer
 * Copyright (C) 2010 SonarSource
 * sonarqube@googlegroups.com
 *
 * This program is free software; you can redistribute it and/or
 * modify it under the terms of the GNU Lesser General Public
 * License as published by the Free Software Foundation; either
 * version 3 of the License, or (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this program; if not, write to the Free Software
 * Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02
 */
package org.sonar.sslr.internal.vm;

import org.sonar.sslr.internal.matchers.Matcher;

public class StringExpression extends NativeExpression implements Matcher {

  private final String string;
  private final int length;

  public StringExpression(String string) {
    this.string = string;
    this.length = string.length();
  }

  @Override
  public void execute(Machine machine) {
    if (machine.length() < length) {
      machine.backtrack();
      return;
    }
    for (int i = 0; i < length; i++) {
      if (machine.charAt(i) != string.charAt(i)) {
        machine.backtrack();
        return;
      }
    }
    machine.createLeafNode(this, length);
    machine.jump(1);
  }

  @Override
  public String toString() {
    return "String " + string;
  }

}
