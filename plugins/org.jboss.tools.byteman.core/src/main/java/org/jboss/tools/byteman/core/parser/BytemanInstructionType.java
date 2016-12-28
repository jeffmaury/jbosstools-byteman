/**
 * 
 */
package org.jboss.tools.byteman.core.parser;

/**
 * @author Jeff MAURY
 *
 */
public enum BytemanInstructionType {
    HELPER,
    IMPORT,
    RULE,
    RULEEND,
    CLASS,
    INTERFACE,
    METHOD,
    AT,
    IF,
    DO,
    COMMENT,
    COMPILE,
    NOCOMPILE,
    BIND
}
