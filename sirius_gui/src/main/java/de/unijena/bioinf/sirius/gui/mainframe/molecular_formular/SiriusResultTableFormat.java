package de.unijena.bioinf.sirius.gui.mainframe.molecular_formular;
/**
 * Created by Markus Fleischauer (markus.fleischauer@gmail.com)
 * as part of the sirius_frontend
 * 25.01.17.
 */

import de.unijena.bioinf.myxo.gui.tree.structure.TreeNode;
import de.unijena.bioinf.sirius.gui.structure.SiriusResultElement;
import de.unijena.bioinf.sirius.gui.table.SiriusTableFormat;
import de.unijena.bioinf.sirius.gui.table.list_stats.ListStats;

/**
 * Display issues in a tabular form.
 *
 * @author Markus Fleischauer (markus.fleischauer@gmail.com)
 */
public class SiriusResultTableFormat extends SiriusTableFormat<SiriusResultElement> {
    private static final int COL_COUNT = 9;

    protected SiriusResultTableFormat(ListStats stats) {
        super(stats);
    }


    @Override
    public int highlightColumnIndex() {
        return COL_COUNT;
    }

    @Override
    protected boolean isBest(SiriusResultElement element) {
        return stats.getMax() <= element.getScore();
    }

    @Override
    public int getColumnCount() {
        return COL_COUNT;
    }

    public String getColumnName(int column) {
        switch (column) {
            case 0:
                return "Rank";
            case 1:
                return "Molecular Formula";
            case 2:
                return "Adduct";
            case 3:
                return "Score";
            case 4:
                return "Isotope Score";
            case 5:
                return "Tree Score";
            case 6:
                return "Explained Peaks";
            case 7:
                return "Total Explained Intensity";
            case 8:
                return "Median Mass Deviation in ppm";
            case 9:
                return "Best";
            default:
                throw new IllegalStateException();
        }
    }

    public Object getColumnValue(SiriusResultElement result, int column) {
        switch (column) {
            case 0:
                return result.getRank();
            case 1:
                return result.getMolecularFormula().toString();
            case 2:
                return result.getPrecursorIonType().toString();
            case 3:
                return result.getScore();
            case 4:
                return result.getResult().getIsotopeScore();
            case 5:
                return result.getResult().getTreeScore();
            case 6:
                final double expPeaks = result.getNumOfExplainedPeaks();
                if (Double.isNaN(expPeaks))
                    return "Value not found";
                else
                    return expPeaks;
            case 7:
                final double intensity = result.getExplainedIntensityRatio();
                if (Double.isNaN(intensity))
                    return "Value not found";
                else
                    return intensity;
            case 8:
                TreeNode visibleTreeRoot = result.getTreeVisualization();
                if (visibleTreeRoot != null && visibleTreeRoot.getMedianMassDeviation() != null)
                    return visibleTreeRoot.getMedianMassDeviation();
                else
                    return "Value not found";
            case 9:
                return isBest(result);
            default:
                throw new IllegalStateException();
        }
    }
}

