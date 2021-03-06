package de.unijena.bioinf.sirius.gui.actions;
/**
 * Created by Markus Fleischauer (markus.fleischauer@gmail.com)
 * as part of the sirius_frontend
 * 29.01.17.
 */

import ca.odell.glazedlists.event.ListEvent;
import ca.odell.glazedlists.swing.DefaultEventSelectionModel;
import de.unijena.bioinf.fingerid.FingerIdDialog;
import de.unijena.bioinf.fingerid.db.SearchableDatabase;
import de.unijena.bioinf.sirius.gui.compute.jjobs.Jobs;
import de.unijena.bioinf.sirius.gui.configs.Icons;
import de.unijena.bioinf.sirius.gui.mainframe.MainFrame;
import de.unijena.bioinf.sirius.gui.mainframe.experiments.ExperimentListChangeListener;
import de.unijena.bioinf.sirius.gui.net.ConnectionMonitor;
import de.unijena.bioinf.sirius.gui.structure.ExperimentContainer;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;

import static de.unijena.bioinf.sirius.gui.mainframe.MainFrame.MF;

/**
 * @author Markus Fleischauer (markus.fleischauer@gmail.com)
 */
public class ComputeCSIAction extends AbstractAction {

    public ComputeCSIAction() {
        super("CSI:FingerID");
        putValue(Action.SMALL_ICON, Icons.FINGER_32);
        putValue(Action.SHORT_DESCRIPTION, "Search computed compounds with CSI:FingerID");

        Jobs.runInBackround(() -> proofCSI(MainFrame.CONECTION_MONITOR.checkConnection().isConnected()));

        MF.getExperimentList().addChangeListener(new ExperimentListChangeListener() {
            @Override
            public void listChanged(ListEvent<ExperimentContainer> event, DefaultEventSelectionModel<ExperimentContainer> selection) {
                if (MF.getCsiFingerId().isEnabled()) {
                    for (ExperimentContainer container : event.getSourceList()) {
                        if (container.isComputed()) {
                            setEnabled(true);
                            return;
                        }
                    }
                    setEnabled(false);
                } else {
                    setEnabled(false);
                }
            }

            @Override
            public void listSelectionChanged(DefaultEventSelectionModel<ExperimentContainer> selection) {
            }
        });

        MainFrame.CONECTION_MONITOR.addConectionStateListener(evt -> {
            ConnectionMonitor.ConnectionState value = (ConnectionMonitor.ConnectionState) evt.getNewValue();
            setEnabled(proofCSI(value.equals(ConnectionMonitor.ConnectionState.YES)));
        });
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (CheckConnectionAction.isConnectedAndLoad())
            return;


        final FingerIdDialog dialog = new FingerIdDialog(MF, MF.getCsiFingerId(), true, false);
        final int returnState = dialog.run();
        final SearchableDatabase db = dialog.getSearchDb();
        if (returnState == FingerIdDialog.COMPUTE_ALL) {
            //MF.getCsiFingerId().computeAll(MF.getCompounds(), db);
            MF.getCsiFingerId().computeAll(MF.getCompounds(), db);
        } else if (returnState == FingerIdDialog.COMPUTE) {
            //MF.getCsiFingerId().computeAll(MF.getCompoundListSelectionModel().getSelected(), db);
            MF.getCsiFingerId().computeAll(MF.getCompoundListSelectionModel().getSelected(), db);
        }
    }


    protected boolean proofCSI(final boolean network) {
        setEnabled(false);
        if (MF.getCsiFingerId().isEnabled() && MF.getCompounds().size() > 0) {
            if (network) {
                for (ExperimentContainer container : MF.getCompounds()) {
                    if (container.isComputed())
                        return true;
                    setEnabled(true);
                    break;
                }
            }
        }
        return false;
    }
}
