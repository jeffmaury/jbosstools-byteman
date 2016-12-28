/*
 * JBoss, Home of Professional Open Source
 * Copyright 2009-10 Red Hat and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * This is free software; you can redistribute it and/or modify it
 * under the terms of the GNU Lesser General Public License as
 * published by the Free Software Foundation; either version 2.1 of
 * the License, or (at your option) any later version.
 *
 * This software is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE. See the GNU
 * Lesser General Public License for more details.
 *
 * You should have received a copy of the GNU Lesser General Public
 * License along with this software; if not, write to the Free
 * Software Foundation, Inc., 51 Franklin St, Fifth Floor, Boston, MA
 * 02110-1301 USA, or see the FSF site: http://www.fsf.org.
 *
 * (C) 2009-10,
 * @authors Andrew Dinn
 */
package org.jboss.tools.byteman.core.parser;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.Reader;
import org.jboss.byteman.agent.LocationType;
import org.jboss.tools.byteman.core.parser.BytemanLocationInstruction.BytemanLocationInstructionType;

/**
 * Class to manage indexing and lookup of rule scripts by rule name and by either class or interface name
 */
public class RuleScriptParser
{
    /**
     * Split the text of a script file into a list of individual rule scripts
     * @param scriptReader the text obtained from a script file
     * @param scriptName teh name of the file containing teh text
     * @return a list of rule scripts
     * @throws Exception if there is an error in the format of the script file tesxt
     */
    public RulesScript processScripts(Reader scriptReader, String scriptName) throws IOException
    {
        RulesScript ruleScripts = new RulesScript();

        if (scriptReader != null) {
            // split rules into separate lines
            try (BufferedReader reader = new BufferedReader(scriptReader)) {
                String line;
                LocationType locationType;
                
                int lineNumber = 0;
                BytemanRule currentRule = null;
                BytemanBindInstruction bindInstruction = null;
                
                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    ++lineNumber;
                    if (line.length() > 0) {
                        if (line.startsWith("#")) {
                            bindInstruction = merge(
                                    new BytemanInstruction(lineNumber, 0, BytemanInstructionType.COMMENT, line),
                                    ruleScripts, currentRule, bindInstruction);
                        } else if (line.startsWith("RULE")) {
                            currentRule = new BytemanRule(lineNumber, 0, line, getParameter("RULE", line));
                            ruleScripts.addInstruction(currentRule);
                        } else if (line.startsWith("ENDRULE")) {
                            bindInstruction = merge(
                                    new BytemanInstruction(lineNumber, 0, BytemanInstructionType.RULEEND, line),
                                    ruleScripts, currentRule, bindInstruction);
                        } else if (line.startsWith("IMPORT")) {
                            bindInstruction = merge(new BytemanNamedInstruction(lineNumber, 0,
                                    BytemanInstructionType.IMPORT, line, getParameter("IMPORT", line)), ruleScripts,
                                    currentRule, bindInstruction);
                        } else if (line.startsWith("CLASS")) {
                            bindInstruction = merge(new BytemanNamedInstruction(lineNumber, 0,
                                    BytemanInstructionType.CLASS, line, getParameter("CLASS", line)), ruleScripts,
                                    currentRule, bindInstruction);
                        } else if (line.startsWith("INTERFACE")) {
                            bindInstruction = merge(
                                    new BytemanNamedInstruction(lineNumber, 0, BytemanInstructionType.INTERFACE, line,
                                            getParameter("INTERFACE", line)),
                                    ruleScripts, currentRule, bindInstruction);
                        } else if (line.startsWith("METHOD")) {
                            bindInstruction = merge(new BytemanNamedInstruction(lineNumber, 0,
                                    BytemanInstructionType.METHOD, line, getParameter("METHOD", line)), ruleScripts,
                                    currentRule, bindInstruction);
                        } else if (line.startsWith("HELPER")) {
                            bindInstruction = merge(new BytemanNamedInstruction(lineNumber, 0,
                                    BytemanInstructionType.HELPER, line, getParameter("HELPER", line)), ruleScripts,
                                    currentRule, bindInstruction);
                        } else if (line.startsWith("COMPILE")) {
                            bindInstruction = merge(
                                    new BytemanInstruction(lineNumber, 0, BytemanInstructionType.COMPILE, line),
                                    ruleScripts, currentRule, bindInstruction);
                        } else if (line.startsWith("NOCOMPILE")) {
                            bindInstruction = merge(
                                    new BytemanInstruction(lineNumber, 0, BytemanInstructionType.NOCOMPILE, line),
                                    ruleScripts, currentRule, bindInstruction);
                        } else if (line.startsWith("ENDRULE")) {
                            bindInstruction = merge(
                                    new BytemanInstruction(lineNumber, 0, BytemanInstructionType.RULEEND, line),
                                    ruleScripts, currentRule, bindInstruction);
                            currentRule = null;
                        } else if ((locationType = LocationType.type(line)) != null) {
                            bindInstruction = merge(
                                    new BytemanLocationInstruction(lineNumber, 0, BytemanInstructionType.AT, line,
                                            translate(locationType), LocationType.parameterText(line)),
                                    ruleScripts, currentRule, bindInstruction);
                        } else {
                            if (bindInstruction == null) {
                                bindInstruction = new BytemanBindInstruction(lineNumber, 0, line);
                            } else {
                                bindInstruction.add(line);
                            }
                        } 
                    }
                    
                }
                if (bindInstruction != null) {
                    merge(bindInstruction, ruleScripts, currentRule, null);
                }
            }
        }

        return ruleScripts;
    }

    /**
     * Translate an internal Byteman parser location type to a parser exposed one. Based on same names.
     * 
     * @param locationType the internal Byteman parser location type
     * @return the exposed location type
     */
    private BytemanLocationInstructionType translate(LocationType locationType) {
        return BytemanLocationInstructionType.valueOf(locationType.name());
    }

    private BytemanBindInstruction merge(BytemanInstruction instruction, RulesScript ruleScripts, BytemanRule rule, BytemanBindInstruction bindInstruction) {
        if (null != rule) {
            if (bindInstruction != null) {
                rule.addInstruction(bindInstruction);
                bindInstruction = null;
            }
            rule.addInstruction(instruction);
        } else {
            if (bindInstruction != null) {
                ruleScripts.addInstruction(bindInstruction);
                bindInstruction = null;
            }
            ruleScripts.addInstruction(instruction);
        }
        return bindInstruction;
    }

    private static String getParameter(String keyword, String line) {
        return line.length() > keyword.length() + 1?line.substring(keyword.length() + 1):"";
    }

}
