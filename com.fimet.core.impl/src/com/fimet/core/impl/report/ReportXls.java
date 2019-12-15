package com.fimet.core.impl.report;

import java.awt.Color;
//import java.awt.Color;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFPalette;
import org.apache.poi.hssf.usermodel.HSSFRichTextString;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
//import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.hssf.util.HSSFColor.HSSFColorPredefined;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.FillPatternType;
//import org.apache.poi.ss.usermodel.Color;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.IndexedColors;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;

import com.fimet.commons.utils.StringUtils;

/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * esta clase genera el reporte de F8 en formato Excel
 */
public class ReportXls {
	/*private File path;
	private ReportJsonReader reader;
	private String dictamen = "CORRECTO";
	public ReportXls(File path, File jsonPath) {
		this.path = path;
		this.reader = new ReportJsonReader(jsonPath);
	}
	public void create() {
        HSSFWorkbook workbook = new HSSFWorkbook();

        HSSFSheet firstSheet = workbook.createSheet("FIRST SHEET");
        HSSFSheet secondSheet = workbook.createSheet("SECOND SHEET");

        HSSFPalette palette = workbook.getCustomPalette();
        
        HSSFRow row = firstSheet.createRow(0);
        HSSFCell cell;
        
        HSSFCellStyle cellStyleWrap = workbook.createCellStyle();
        cellStyleWrap.setWrapText(true);
        
        HSSFCellStyle cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);
        //cellStyleTitle.setFillForegroundColor(IndexedColors.BLUE1.getIndex());
        //cellStyleTitle.setFillForegroundColor(palette.findSimilarColor(221, 235, 247).getIndex());
        cellStyleTitle.setFillForegroundColor(palette.findSimilarColor(155, 194, 230).getIndex());
        //cellStyleTitle.setFillForegroundColor(new HSSFColor(0x28,   -1, new Color(221, 235, 247)).getIndex());
        //cellStyleTitle.setFillForegroundColor(IndexedColors.BLUE.getIndex());
        cellStyleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        HSSFFont cellFontTitle = workbook.createFont();
        cellFontTitle.setBold(true);
        cellStyleTitle.setFont(cellFontTitle);
        cellStyleTitle.setBorderBottom(BorderStyle.THIN);
        cellStyleTitle.setBorderTop(BorderStyle.THIN);
        cellStyleTitle.setBorderRight(BorderStyle.THIN);
        cellStyleTitle.setBorderLeft(BorderStyle.THIN);
        
        
        HSSFCellStyle cellStyle = workbook.createCellStyle();
        cellStyle.setWrapText(true);
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        
        
        firstSheet.setColumnWidth(0, 40*256);
        //firstSheet.autoSizeColumn(0);
        cell= row.createCell(0);
        cell.setCellValue(new HSSFRichTextString("Caso de Uso"));
        cell.setCellStyle(cellStyleTitle);
        
        firstSheet.setColumnWidth(1, 25*256);
        //firstSheet.autoSizeColumn(1);
        cell= row.createCell(1);
        cell.setCellValue(new HSSFRichTextString("Adquirente"));
        cell.setCellStyle(cellStyleTitle);
        
        firstSheet.setColumnWidth(2, 25*256);
        //firstSheet.autoSizeColumn(2);
        cell= row.createCell(2);
        cell.setCellValue(new HSSFRichTextString("Emisor"));
        cell.setCellStyle(cellStyleTitle);
        
        firstSheet.setColumnWidth(3, 40*256);
        //firstSheet.autoSizeColumn(3);
        cell= row.createCell(3);
        cell.setCellValue(new HSSFRichTextString("Validaciones Respuesta\n(Esperado/Obtenido)"));
        cell.setCellStyle(cellStyleTitle);
        
        firstSheet.setColumnWidth(4, 40*256);
        //firstSheet.autoSizeColumn(4);
        cell= row.createCell(4);
        cell.setCellValue(new HSSFRichTextString("Validaciones Solicitud\n(Esperado/Obtenido)"));
        cell.setCellStyle(cellStyleTitle);
        
        firstSheet.setColumnWidth(5, 20*256);
        //firstSheet.autoSizeColumn(5);
        cell= row.createCell(5);
        cell.setCellValue(new HSSFRichTextString("Dictamen"));
        cell.setCellStyle(cellStyleTitle);
        

        
        int numRow = 1;
        int numCell;
        RIap iap;
        for (RUseCase r : reader) {
        	numCell = 0;
        	row = firstSheet.createRow(numRow++);
        	// Nombre del Caso de Uso
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	cell.setCellValue(new HSSFRichTextString(r.getName()));
        	// Adquirente
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	iap = r.getAcquirer();
        	if (iap != null) {
        		cell.setCellValue(new HSSFRichTextString(iap.getName() + "\n" + iap.getIp() + "\n" + iap.getPort() + "\n"+(iap.getExclisuve() ? "excluyente" : "incluyente")));
        	}
        	// Emisor
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	iap = r.getIssuer();
        	if (iap != null) {
        		cell.setCellValue(new HSSFRichTextString(iap.getName() + "\n" + iap.getIp() + "\n" + iap.getPort() + "\n"+(iap.getExclisuve() ? "excluyente" : "incluyente")));
        	}
        	// Validaciones Adquirente
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	if (r.getResponse() != null && !r.getResponse().isEmpty()) {
	        	cell.setCellValue(new HSSFRichTextString(StringUtils.escapeNull(fmtValidaciones(r.getResponse()))));
        	} else {
        		cell.setCellValue(new HSSFRichTextString("N/A"));
        	}
        	// Validaciones Emisor
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	if (r.getRequest() != null && !r.getRequest().isEmpty()) {
	        	cell.setCellValue(new HSSFRichTextString(StringUtils.escapeNull(fmtValidaciones(r.getRequest()))));
        	} else {
        		cell.setCellValue(new HSSFRichTextString("N/A"));
        	}
        	// Dictamen
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	cell.setCellValue(new HSSFRichTextString(fmtDictamen(r)));
		}
        reader.close();

    	row = firstSheet.createRow(numRow++);
    	cell= row.createCell(5);
    	cell.setCellValue(new HSSFRichTextString(dictamen));
    	
    	
        HSSFRow rowB = secondSheet.createRow(0);
        HSSFCell cellB = rowB.createCell(0);
        cellB.setCellValue(new HSSFRichTextString("SECOND SHEET"));

        // To write out the workbook into a file we need to create an output
        // stream where the workbook content will be written to.

		FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(path);
            workbook.write(fos);           
        } catch (IOException e) {
            Activator.getInstance().error("Writing to f8.xls", e);
        } finally {
        	if (fos != null) {
        		try {
					fos.close();
				} catch (IOException e) {}
        	}
        }
        try {
        	workbook.close();
        } catch (IOException e) {
        	Activator.getInstance().error("Closing f8.xls", e);
        }
	}
	private String fmtValidaciones(List<RValidation> validations) {
		if (validations != null) {
			StringBuilder s = new StringBuilder();
	    	for (RValidation v : validations) {
	    		if (v.getName() != null)
	    			s.append(v.getName()).append(":");
	    		else if (v.getExpression() != null)
	    			s.append(v.getExpression()).append(":");
	    		s
				.append(v.getExpected()).append(v.getFail() ? "!=" : "==").append(v.getValue()).append("\n");
			}
	    	s.delete(s.length()-1, s.length());
	    	return s.toString();
		}
		return null;
	}
	private String fmtDictamen(RUseCase r) {
		if (r.getRequest() != null) {
			for (RValidation v : r.getRequest()) {
				if (v.getFail()) {
					return dictamen = "INCORRECTO";
				}
			}
		}
		if (r.getResponse() != null) {
			for (RValidation v : r.getResponse()) {
				if (v.getFail()) {
					return dictamen = "INCORRECTO";
				}
			}
		}
		return "CORRECTO";
	}*/
}
