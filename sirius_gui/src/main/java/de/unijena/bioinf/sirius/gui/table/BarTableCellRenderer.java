package de.unijena.bioinf.sirius.gui.table;

import javax.swing.*;

/**
 * Created by fleisch on 24.05.17.
 */
public class BarTableCellRenderer extends AbstractBarTableCellRenderer {

    private float min = Float.MIN_VALUE;
    private float max = Float.MAX_VALUE;


    public BarTableCellRenderer(int highlightColumn, float min, float max, boolean percentage) {
        this(highlightColumn,percentage);
        setMin(min);
        setMax(max);
    }

    public BarTableCellRenderer(int highlightColumn, boolean percentage) {
        super(highlightColumn,percentage);
    }

    public BarTableCellRenderer() {
        this(-1,false);
    }

    @Override
    protected float getMax(JTable table, boolean isSelected, boolean hasFocus, int row, int column) {
        return max;
    }

    @Override
    protected float getMin(JTable table, boolean isSelected, boolean hasFocus, int row, int column) {
        return min;
    }

    public void setMin(float min) {
        this.min = min;
    }

    public void setMax(float max) {
        this.max = max;
    }
}