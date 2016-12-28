/**
 * 
 */
package org.jboss.tools.byteman.core.internal.parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;
import java.nio.file.Path;
import org.jboss.tools.byteman.core.parser.RuleScriptParser;
import org.junit.Test;

/**
 * @author Jeff MAURY
 *
 */
public class RuleScriptParserTest extends AbstractParserTest {
    public RuleScriptParserTest(Path path) {
        super(path);
    }
    
    @Test
    public void testFile() throws IOException {
        try (Reader reader = new InputStreamReader(new FileInputStream(path.toFile()), "UTF-8")) {
            RuleScriptParser parser = new RuleScriptParser();
            parser.processScripts(reader, path.toString());
        }
    }
}
