package de.unijena.bioinf.sirius.gui.filefilter;

import javax.swing.filechooser.FileFilter;
import java.io.File;

public class SupportedBatchDataFormatFilter extends FileFilter {

	@Override
	public boolean accept(File f) {
		if(f.isDirectory()) return true;
		String name = f.getName();name = name.toLowerCase();
        return name.endsWith(".ms") || name.endsWith(".mgf");
    }

	@Override
	public String getDescription() {
		return ".ms, .mgf";
	}

}
