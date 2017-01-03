/**
 * 
 */
package org.jboss.tools.byteman.core.internal.parser;

import java.io.File;
import java.io.IOException;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.ArrayList;
import java.util.Collection;

import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

/**
 * @author Jeff MAURY
 *
 */
@RunWith(Parameterized.class)
public abstract class AbstractParserTest {
    
    @Parameters(name="{0}")
    public static Collection<Object[]> parameters() throws IOException {
        final Collection<Object[]> parameters = new ArrayList<>();
        Files.walkFileTree(new File("src/main/resources/scripts").toPath(), new SimpleFileVisitor<Path>() {

            @Override
            public FileVisitResult visitFile(Path path, BasicFileAttributes attrs) throws IOException {
                if (path.toString().endsWith(".btm")) {
                    parameters.add(new Object[] { path });
                }
                return super.visitFile(path, attrs);
            }
            
        });
        return parameters;
    }
    
    protected Path path;
    
    public AbstractParserTest(Path path) {
        this.path = path;
    }
    
}
