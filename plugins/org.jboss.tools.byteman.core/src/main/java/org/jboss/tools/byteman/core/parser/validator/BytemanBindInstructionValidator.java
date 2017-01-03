/**
 * 
 */
package org.jboss.tools.byteman.core.parser.validator;

import java.io.Reader;
import java.io.StringReader;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.eclipse.core.resources.IMarker;
import org.eclipse.core.resources.IResource;
import org.eclipse.core.runtime.IStatus;
import org.jboss.byteman.java_cup.runtime.Symbol;
import org.jboss.byteman.rule.grammar.ECAGrammarParser;
import org.jboss.byteman.rule.grammar.ECATokenLexer;
import org.jboss.tools.byteman.core.parser.BytemanBindInstruction;

/**
 * @author Jeff MAURY
 *
 */
public class BytemanBindInstructionValidator implements BytemanInstructionValidator<BytemanBindInstruction> {

    private static final Pattern pattern = Pattern.compile(" line (\\d+) : (.*)");
    
    @Override
    public void validate(BytemanInstructionValidatorRegistry registry, BytemanBindInstruction instruction,
            IResource resource) {
        String text = instruction.getText();
        try (Reader reader = new StringReader(text)) {
            ECATokenLexer lexer = new ECATokenLexer(reader);
            lexer.setStartLine(instruction.getLine());
            ECAGrammarParser parser = new ECAGrammarParser(lexer);
            parser.parse();
            if (parser.getErrorCount() > 0) {
                createMarkers(resource, parser.getErrors(), instruction.getLine());
            }
        }
        catch (Exception e) {
            createMarker(resource, e.getLocalizedMessage(), instruction.getLine(), IMarker.SEVERITY_ERROR);
        }
    }
    
    private void createMarkers(IResource resource, String errorMessage, int line) {
        String[] errors = errorMessage.split("\n");
        for(String error : errors) {
            if (error.length() > 0) {
                Matcher matcher = pattern.matcher(error);
                if (matcher.matches()) {
                    createMarker(resource, matcher.group(2), Integer.parseInt(matcher.group(1)),
                            IMarker.SEVERITY_ERROR);
                } else {
                    createMarker(resource, error, line, IMarker.SEVERITY_ERROR);
                } 
            }
        }
        
    }
}
