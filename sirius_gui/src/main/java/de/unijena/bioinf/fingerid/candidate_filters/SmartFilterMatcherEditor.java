package de.unijena.bioinf.fingerid.candidate_filters;

import ca.odell.glazedlists.matchers.AbstractMatcherEditor;
import ca.odell.glazedlists.matchers.Matcher;
import de.unijena.bioinf.fingerid.CompoundCandidate;
import org.openscience.cdk.DefaultChemObjectBuilder;
import org.openscience.cdk.smiles.smarts.SMARTSQueryTool;

import javax.swing.*;
import java.awt.*;

/**
 * Created by tkoehl on 18.07.18.
 */
public class SmartFilterMatcherEditor extends AbstractMatcherEditor<CompoundCandidate> {

    public SmartFilterMatcherEditor(JTextField textField) {
        super();
        textField.addActionListener(propertyChangeEvent -> fireChanged(new SmartMatcher(textField)));
    }

    public static class SmartMatcher implements Matcher<CompoundCandidate> {
        final SMARTSQueryTool tool;
        boolean isValidSmartString;

        public SmartMatcher(JTextField textField) {
            String smart = textField.getText();
            textField.setForeground(Color.black);
            textField.setToolTipText("");

            tool = new SMARTSQueryTool("CC", DefaultChemObjectBuilder.getInstance());

            if (smart == null) {
                isValidSmartString = false;
                return;
            }
            if (smart.equals("")) {
                isValidSmartString = false;
                return;
            }

            try {
                tool.setSmarts(smart);
                isValidSmartString = true;
                textField.setForeground(Color.green);
            } catch (Exception e) {
                isValidSmartString = false;
                textField.setForeground(Color.red);
                textField.setToolTipText("invalid SMART string.");
            }
        }

        @Override
        public boolean matches(CompoundCandidate candidate) {
            if (isValidSmartString) {
                try {
                    return tool.matches(candidate.getCompound().getMolecule());
                } catch (Exception e) {
                    return false;
                }
            } else {
                return true;
            }
        }
    }
}
