/*
 * Copyright (C) 2010 SonarSource SA
 * All rights reserved
 * mailto:contact AT sonarsource DOT com
 */
package com.sonar.sslr.test.miniC;

import java.io.File;

import org.apache.commons.io.FileUtils;

import com.sonar.sslr.api.AstNode;
import com.sonar.sslr.impl.Parser;
import com.sonar.sslr.impl.events.ParsingEventListener;

public final class MiniCParser {

  private static final Parser<MiniCGrammar> P = MiniCParser.create();
  private static final MiniCGrammar G = P.getGrammar();

  private MiniCParser() {
  }

  public static Parser<MiniCGrammar> create(ParsingEventListener... parsingEventListeners) {
    return Parser.builder(new MiniCGrammar()).withLexer(MiniCLexer.create()).setParsingEventListeners(parsingEventListeners).build();
  }

  public static AstNode parseFile(String filePath) {
    File file = FileUtils.toFile(MiniCParser.class.getResource(filePath));
    if (file == null || !file.exists()) {
      throw new AssertionError("The file \"" + filePath + "\" does not exist.");
    }

    return P.parse(file);
  }

  public static AstNode parseString(String source) {
    return P.parse(source);
  }

  public static MiniCGrammar getGrammar() {
    return G;
  }

}