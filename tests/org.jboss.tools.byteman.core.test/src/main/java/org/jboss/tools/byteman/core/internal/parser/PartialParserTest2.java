/**
 * 
 */
package org.jboss.tools.byteman.core.internal.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.file.Path;
import java.util.stream.Stream;

import org.jboss.byteman.java_cup.runtime.Symbol;
import org.jboss.byteman.rule.grammar.ECAGrammarParser;
import org.jboss.byteman.rule.grammar.ECATokenLexer;
import org.jboss.tools.byteman.core.parser.BytemanBindInstruction;
import org.jboss.tools.byteman.core.parser.BytemanInstruction;
import org.jboss.tools.byteman.core.parser.BytemanInstructionType;
import org.jboss.tools.byteman.core.parser.BytemanRule;
import org.jboss.tools.byteman.core.parser.RuleScriptParser;
import org.jboss.tools.byteman.core.parser.RulesScript;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeff MAURY
 *
 */
public class PartialParserTest2 extends AbstractParserTest {
    public PartialParserTest2(Path path) {
        super(path);
    }
    
    @Test
    public void testFile() throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(path.toFile()), "UTF-8")) {
            RuleScriptParser parser = new RuleScriptParser();
            RulesScript script = parser.processScripts(reader, path.toString());
            Stream<BytemanInstruction> rules = script.getInstructions().stream().filter(instruction -> instruction.getType() == BytemanInstructionType.RULE);
            rules.forEach(rule -> {
                Stream<BytemanInstruction> bindInstructions = ((BytemanRule)rule).getInstructions().stream().filter(instruction -> instruction.getType() == BytemanInstructionType.BIND);
                bindInstructions.forEach(bind -> {
                    String text = ((BytemanBindInstruction)bind).getText();
                    ECATokenLexer lexer = new ECATokenLexer(new StringReader(text));
                    ECAGrammarParser partialParser = new ECAGrammarParser(lexer);
                    try {
                        Symbol symbol = partialParser.parse();
                        symbol.toString();
                    } catch (Exception e) {
                        Assert.fail(e.getLocalizedMessage());
                    }
                });
            });
        }
    }
}
