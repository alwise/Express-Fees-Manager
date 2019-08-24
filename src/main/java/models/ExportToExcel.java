/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main.java.models;

import java.awt.Desktop;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;


import javafx.application.Platform;
import javafx.scene.control.TableView;
import javafx.stage.FileChooser;
import main.java.Main;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

/**
 *
 * @author Alwise Studio
 *
 */
public class ExportToExcel {

    public static void exportTableToExcel(TableView<?> table, String fileName) {
        Platform.runLater(() -> {
            if (table.getColumns() != null) {
                Workbook workbook = new HSSFWorkbook();
                Sheet spreadsheet = workbook.createSheet(fileName);

                Row row = spreadsheet.createRow(0);

                for (int j = 0; j < table.getColumns().size(); j++) {
                    row.createCell(j).setCellValue(table.getColumns().get(j).getText());
                }

                for (int i = 0; i < table.getItems().size(); i++) {
                    row = spreadsheet.createRow(i + 1);
                    for (int j = 0; j < table.getColumns().size(); j++) {
                        if (table.getColumns().get(j).getCellData(i) != null) {
                            row.createCell(j).setCellValue(table.getColumns().get(j).getCellData(i).toString());
                        } else {
                            row.createCell(j).setCellValue("n/a");
                        }
                    }
                }
                FileChooser chooser = new FileChooser();
                FileChooser.ExtensionFilter filter = new FileChooser.ExtensionFilter("Excel files", "*.xls");
                chooser.getExtensionFilters().add(filter);
                chooser.setInitialFileName(fileName);
                File file = chooser.showSaveDialog(Main.homeStage.getScene().getWindow());
                if (file != null) {
                    FileOutputStream fileOut = null;
                    try {
                        fileOut = new FileOutputStream(file.getAbsoluteFile());
                        workbook.write(fileOut);
                        System.out.println("Data exported successfully..");
                        Desktop.getDesktop().open(file.getAbsoluteFile());
                    } catch (IOException ex) {
                        System.err.println(ex.getLocalizedMessage());
                    }
                }
            }
        });
    }

}
