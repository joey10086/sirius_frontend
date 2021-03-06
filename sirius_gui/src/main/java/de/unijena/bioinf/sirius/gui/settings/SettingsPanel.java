package de.unijena.bioinf.sirius.gui.settings;/**
 * Created by Markus Fleischauer (markus.fleischauer@gmail.com)
 * as part of the sirius_frontend
 * 07.10.16.
 */

/**
 * @author Markus Fleischauer (markus.fleischauer@gmail.com)
 */
public interface SettingsPanel {
    default void refreshValues() {
    }

    void saveProperties();

    default void reloadChanges() {
    }

    String name();

    default boolean restartRequired() {
        return false;
    }
}
