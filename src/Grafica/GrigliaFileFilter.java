package Grafica;

import javax.swing.filechooser.FileFilter;
import java.io.File;

class GrigliaFileFilter extends FileFilter {
    @Override
    public boolean accept(File f) {
        if(f.isDirectory()) return true;

        String estensione = Utils.getExtension(f);

        if(estensione==null) return false;

        return estensione.equals("txt");
    }

    @Override
    public String getDescription() {
        return null;
    }
}
