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

import org.jboss.tools.byteman.core.parser.BytemanBindInstruction;
import org.jboss.tools.byteman.core.parser.BytemanInstruction;
import org.jboss.tools.byteman.core.parser.BytemanInstructionType;
import org.jboss.tools.byteman.core.parser.BytemanPartialParser;
import org.jboss.tools.byteman.core.parser.BytemanRule;
import org.jboss.tools.byteman.core.parser.ParseException;
import org.jboss.tools.byteman.core.parser.RuleScriptParser;
import org.jboss.tools.byteman.core.parser.RulesScript;
import org.junit.Assert;
import org.junit.Test;

/**
 * @author Jeff MAURY
 *
 */
public class PartialParserTest extends AbstractParserTest {
    public PartialParserTest(Path path) {
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
                    BytemanPartialParser partialParser = new BytemanPartialParser(new StringReader(text));
                    try {
                        partialParser.RelaxedBody();
                    } catch (ParseException e) {
                        Assert.fail(e.getLocalizedMessage());
                    }
                });
            });
        }
    }
}
