package de.unijena.bioinf.sirius.gui.fingerid;

import de.unijena.bioinf.sirius.gui.configs.Colors;

import javax.swing.*;
import java.awt.*;

/**
 * Created by fleisch on 24.05.17.
 */
public class CandidateStructureCellRenderer implements ListCellRenderer<CompoundCandidate> {
    private CompoundStructureImage image = new CompoundStructureImage();

    @Override
    public Component getListCellRendererComponent(JList<? extends CompoundCandidate> list, CompoundCandidate value, int index, boolean isSelected, boolean cellHasFocus) {
        JPanel nu =  new JPanel();
        image.molecule = value;
        image.backgroundColor = (index % 2 == 0 ? Colors.LIST_EVEN_BACKGROUND : Colors.LIST_UNEVEN_BACKGROUND);
        nu.setBackground(image.backgroundColor);
        nu.add(image);
        return nu;
    }
}
