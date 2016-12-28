/**
 * 
 */
package org.jboss.tools.byteman.ui.internal.editor;

import org.eclipse.jface.text.rules.ICharacterScanner;
import org.eclipse.jface.text.rules.IPredicateRule;
import org.eclipse.jface.text.rules.IToken;
import org.eclipse.jface.text.rules.Token;

/**
 * @author Jeff MAURY
 *
 */
public class StringRule implements IPredicateRule {

    private String str;
    private IToken token;

    public StringRule(String str, IToken token) {
        this.str = str;
        this.token = token;
    }
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.rules.IRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner)
     */
    @Override
    public IToken evaluate(ICharacterScanner scanner) {
        for(int i=0; i < str.length();++i) {
            int c = scanner.read();
            if (c != str.charAt(i)) {
                unread(scanner, i+1);
                return Token.UNDEFINED;
            }
        }
        return token;
    }

    private void unread(ICharacterScanner scanner, int length) {
      for(int i=0; i < length;++i) {
          scanner.unread();
      }
    }
    
    /* (non-Javadoc)
     * @see org.eclipse.jface.text.rules.IPredicateRule#getSuccessToken()
     */
    @Override
    public IToken getSuccessToken() {
        return token;
    }

    /* (non-Javadoc)
     * @see org.eclipse.jface.text.rules.IPredicateRule#evaluate(org.eclipse.jface.text.rules.ICharacterScanner, boolean)
     */
    @Override
    public IToken evaluate(ICharacterScanner scanner, boolean resume) {
        // TODO Auto-generated method stub
        return null;
    }

}
