import java.io.*;
import java.nio.file.FileAlreadyExistsException;
import java.nio.file.Files;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Repo {

    public File src;
    public File tgt;
    public File activity;


    public Repo () {
    }


    public boolean create(String srcDir, String tgtDir) throws IOException {
        src = new File(srcDir);
        tgt = new File(tgtDir);
        if (!tgt.exists())
            tgt.mkdir();

        File f = new File(tgt, "Activity");
        f.mkdir();
        activity = f;

        // Declare Manifesto
        DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd_HHmm");
        String s = "Manifest_" + dateFormat.format(new Date());
        PrintWriter writer = new PrintWriter(activity.getPath() + '\\' + s);
        writer.println(s);
        writer.println("  Create " + src + " " + tgt);
        copyDirectory(src, tgt, writer);
        writer.close();
        System.out.println("Repo created.");
        return true;
    }

    // Copy Directory
    public void copyDirectory(File source, File target, PrintWriter writer) throws IOException {
        if (!target.exists())
            target.mkdir();

        for (String s : source.list()) {
            File f = new File(source, s);
            // Check if source is Directory or File
            if (f.isDirectory()) {
                // Copy dir
                copyDirectory(f, new File(target, s), writer);
            }
            else {
                // Create folder for file
                File x = new File(target, s + '\\');
                if (!x.exists())
                    x.mkdir();

                // Copy file
                // Change name here
                try {
                    x = new File(target, s + '\\' + generateName(f));
                    Files.copy(f.toPath(), x.toPath());
                    writer.println("    " + s + " " + x.getName() + " " + x.toPath());
                } catch (FileAlreadyExistsException e) {
                    System.out.println("Exception: " + e);
                }
            }
        }
    }


    public String generateName(File source) throws IOException {
        long checksum = 0;
        FileInputStream f = new FileInputStream(source);
        int val;
        int size = 0;
        while ((val = f.read()) != -1) {
            switch (size%4) {
                case 0:
                    checksum += val*1;
                    break;
                case 1:
                    checksum += val*3;
                    break;
                case 2:
                    checksum += val*11;
                    break;
                case 3:
                    checksum += val*17;
                    break;
                default:
                    break;
            }
            size++;
        }
        f.close();

        int i = source.getName().lastIndexOf('.');
        String ext = "";
        if (i >0)
            ext = source.getName().substring(i);

        return Long.toString(checksum) + '.' + size + ext;
    }
}
