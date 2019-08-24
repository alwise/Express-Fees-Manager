package main.java.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.jar.JarOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class DataFile {

    //Take backUp of database
    public Boolean backUpDataBase(File source,File destination) {
        //System.out.println("hellooo");

        FileInputStream fis = null;//note fornull pointer exception..
        ZipOutputStream zos = null;

        //System.out.println("Deff...");
        if (null == destination) {
            return false;
        }

            try {
                fis = new FileInputStream(source);
                zos = new JarOutputStream(new FileOutputStream(destination.getAbsolutePath()));
                zos.putNextEntry(new ZipEntry(source.getName()));
                byte[] buffer = new byte[1024];
                int byteRead;
                while (((byteRead = fis.read(buffer)) > 0)) {
                    zos.write(buffer, 0, byteRead);
                }
                zos.flush();
                zos.closeEntry();
                zos.close();
                fis.close();
                return true;
//            Notification.showSuccess("Backup Complete!", "backedup done Successfully to: " + destination);
            } catch (Exception ex1) {
                System.err.println(ex1.fillInStackTrace());
                return false;
            }


    }

    public Boolean restoreData(File source,File destination) {

        if (source != null) {
            ZipInputStream zis = null;
            try {
                zis = new ZipInputStream(new FileInputStream(source.getAbsoluteFile()));
                ZipEntry ze = zis.getNextEntry();
                byte[] buffer = new byte[1024];
                while (ze != null) {
                    FileOutputStream fos = new FileOutputStream(destination);
                    int reader;
                    while ((reader = zis.read(buffer)) > 0) {
                        fos.write(buffer, 0, reader);
                    }
                    fos.close();
                    ze = zis.getNextEntry();
                }
                zis.closeEntry();
                zis.close();
                return true;
//            Notification.showSuccess("Restore Complete!", "Restore data Successfully from: " + source);
            } catch (Exception ex1) {
                System.err.println(ex1);
                return false;
            }

        } else {
            return false;
        }
    }





}
