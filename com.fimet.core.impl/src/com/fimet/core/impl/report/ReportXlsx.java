package com.fimet.core.impl.report;

import java.awt.Color;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;
import org.apache.commons.compress.utils.IOUtils;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.ClientAnchor.AnchorType;
import org.apache.poi.ss.usermodel.CreationHelper;
import org.apache.poi.ss.usermodel.FillPatternType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.usermodel.Picture;
import org.apache.poi.ss.usermodel.VerticalAlignment;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.DefaultIndexedColorMap;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFClientAnchor;
import org.apache.poi.xssf.usermodel.XSSFColor;
import org.apache.poi.xssf.usermodel.XSSFDrawing;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRichTextString;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.eclipse.core.resources.IProject;

import com.fimet.commons.console.Console;
import com.fimet.core.IReport;
import com.fimet.core.entity.sqlite.UseCaseReport;
import com.fimet.core.entity.sqlite.pojo.Notice;
import com.fimet.core.entity.sqlite.pojo.Validation;
import com.fimet.core.impl.Activator;
import com.fimet.core.usecase.UseCase;
/**
 * 
 * @author Marco A. Salazar
 * @email marcoasb99@ciencias.unam.mx
 * esta clase genera el reporte de F8 en formato Excel
 */
public class ReportXlsx implements IReport {

	private File path;
	private List<UseCaseReport> tasks;
	private IProject project;
	private XSSFWorkbook workbook;
	
	private XSSFFont fontCorrectBold;
	private XSSFFont fontIncorrectBold;
	private XSSFFont fontDefault;
	private XSSFFont fontCorrect;
	private XSSFFont fontError;
	private XSSFFont fontDictamen;
	
	private XSSFCellStyle cellStyleTitle;
	private XSSFCellStyle cellStyleOdd;
	private XSSFCellStyle cellStyleEven;
	private XSSFCellStyle cellStyleDictamenCorrect;
	private XSSFCellStyle cellStyleDictamenIncorrect;
	
	private int numUseCases;
	private int numUseCasesCorrect;
	private int numUseCasesIncorrect;
	
	public ReportXlsx() {}

	public void report(IProject project, File pathOutput, List<UseCaseReport> tasks) {
		this.project = project;
		this.path = pathOutput;
		this.tasks = tasks;
		workbook = new XSSFWorkbook();

        XSSFSheet sheetReportF8 = workbook.createSheet("Report");
        XSSFSheet sheetSummary = workbook.createSheet("Summary");
       
        XSSFCellStyle cellStyleWrap = workbook.createCellStyle();
        cellStyleWrap.setWrapText(true);
        
        fontCorrectBold = workbook.createFont();
        fontCorrectBold.setBold(true);
        fontCorrectBold.setColor(new XSSFColor(new Color(84, 130, 53),new DefaultIndexedColorMap()));
        fontIncorrectBold = workbook.createFont();
        fontIncorrectBold.setBold(true);
        fontIncorrectBold.setColor(new XSSFColor(new Color(255, 0, 0),new DefaultIndexedColorMap()));
        fontError = workbook.createFont();
        fontError.setColor(new XSSFColor(new Color(255, 0, 0),new DefaultIndexedColorMap()));
        fontCorrect = workbook.createFont();
        fontCorrect.setColor(new XSSFColor(new Color(84, 130, 53),new DefaultIndexedColorMap()));
        fontDefault = workbook.createFont();
        
        fontDictamen = workbook.createFont();
        fontDictamen.setColor(new XSSFColor(new Color(255, 255, 255),new DefaultIndexedColorMap()));
        fontDictamen.setBold(true);
        fontDictamen.setFontHeight(14);
        
        cellStyleTitle = workbook.createCellStyle();
        cellStyleTitle.setWrapText(true);
        cellStyleTitle.setAlignment(HorizontalAlignment.CENTER);
        cellStyleTitle.setVerticalAlignment(VerticalAlignment.CENTER);       
        cellStyleTitle.setFillForegroundColor(new XSSFColor(new Color(47, 117, 181),new DefaultIndexedColorMap()));
        cellStyleTitle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        XSSFFont cellFontTitle = workbook.createFont();
        cellFontTitle.setBold(true);
        cellFontTitle.setColor(new XSSFColor(new Color(255, 255, 255),new DefaultIndexedColorMap()));
        cellStyleTitle.setFont(cellFontTitle);
        cellStyleTitle.setBorderBottom(BorderStyle.THIN);
        cellStyleTitle.setBorderTop(BorderStyle.THIN);
        cellStyleTitle.setBorderRight(BorderStyle.THIN);
        cellStyleTitle.setBorderLeft(BorderStyle.THIN);
        
        
        cellStyleOdd = workbook.createCellStyle();
        cellStyleOdd.setFillForegroundColor(new XSSFColor(new Color(221, 235, 247),new DefaultIndexedColorMap()));
        cellStyleOdd.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleOdd.setWrapText(true);
        cellStyleOdd.setAlignment(HorizontalAlignment.CENTER);
        cellStyleOdd.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleOdd.setBorderBottom(BorderStyle.THIN);
        cellStyleOdd.setBorderTop(BorderStyle.THIN);
        cellStyleOdd.setBorderRight(BorderStyle.THIN);
        cellStyleOdd.setBorderLeft(BorderStyle.THIN);

        cellStyleEven = workbook.createCellStyle();
        cellStyleEven.setWrapText(true);
        cellStyleEven.setAlignment(HorizontalAlignment.CENTER);
        cellStyleEven.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleEven.setBorderBottom(BorderStyle.THIN);
        cellStyleEven.setBorderTop(BorderStyle.THIN);
        cellStyleEven.setBorderRight(BorderStyle.THIN);
        cellStyleEven.setBorderLeft(BorderStyle.THIN);
        
        
        cellStyleDictamenCorrect = workbook.createCellStyle();
        cellStyleDictamenCorrect.setFillForegroundColor(new XSSFColor(new Color(84, 130, 53),new DefaultIndexedColorMap()));
        cellStyleDictamenCorrect.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleDictamenCorrect.setWrapText(true);
        cellStyleDictamenCorrect.setAlignment(HorizontalAlignment.CENTER);
        cellStyleDictamenCorrect.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleDictamenCorrect.setBorderBottom(BorderStyle.THIN);
        cellStyleDictamenCorrect.setBorderTop(BorderStyle.THIN);
        cellStyleDictamenCorrect.setBorderRight(BorderStyle.THIN);
        cellStyleDictamenCorrect.setBorderLeft(BorderStyle.THIN);
        
        cellStyleDictamenIncorrect = workbook.createCellStyle();
        cellStyleDictamenIncorrect.setFillForegroundColor(new XSSFColor(new Color(255, 0, 0),new DefaultIndexedColorMap()));
        cellStyleDictamenIncorrect.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        cellStyleDictamenIncorrect.setWrapText(true);
        cellStyleDictamenIncorrect.setAlignment(HorizontalAlignment.CENTER);
        cellStyleDictamenIncorrect.setVerticalAlignment(VerticalAlignment.CENTER);
        cellStyleDictamenIncorrect.setBorderBottom(BorderStyle.THIN);
        cellStyleDictamenIncorrect.setBorderTop(BorderStyle.THIN);
        cellStyleDictamenIncorrect.setBorderRight(BorderStyle.THIN);
        cellStyleDictamenIncorrect.setBorderLeft(BorderStyle.THIN);
        
        createSheetReportF8(sheetReportF8);
        createSheetSummary(sheetSummary);
        
		FileOutputStream fos = null;
        try {
        	fos = new FileOutputStream(path);
            workbook.write(fos);
            Console.getInstance().info(ReportXlsx.class, "Report FIMET.xlsx created for "+project.getName());
        } catch (IOException e) {
        	Console.getInstance().warning(ReportXlsx.class, "Error creating FIMET.xlsx for project "+project.getName()+"\n"+e.getMessage());
            Activator.getInstance().error("Error creating FIMET.xlsx for project "+project.getName() , e);
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
        	Activator.getInstance().error("Error closing FIMET.xlsx for "+project.getName(), e);
        }
	}

	private void createSheetReportF8(XSSFSheet sheet) {
		
        XSSFRow row = sheet.createRow(0);
        XSSFCell cell;
        int numRow = 1;
        int numCols = 0;
        
        sheet.setColumnWidth(numCols, 40*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Use Case"));
        cell.setCellStyle(cellStyleTitle);

        sheet.setColumnWidth(numCols, 25*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Acquirer"));
        cell.setCellStyle(cellStyleTitle);
        
        sheet.setColumnWidth(numCols, 25*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Issuer"));
        cell.setCellStyle(cellStyleTitle);
        
        sheet.setColumnWidth(numCols, 50*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Validations\n(Expected/Result)"));
        cell.setCellStyle(cellStyleTitle);
        
        sheet.setColumnWidth(numCols, 40*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Notifications"));
        cell.setCellStyle(cellStyleTitle);

        sheet.setColumnWidth(numCols, 15*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Acquirer Response Code"));
        cell.setCellStyle(cellStyleTitle);
        
        sheet.setColumnWidth(numCols, 30*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Timestamp"));
        cell.setCellStyle(cellStyleTitle);
        
        sheet.setColumnWidth(numCols, 20*256);
        cell= row.createCell(numCols++);
        cell.setCellValue(new XSSFRichTextString("Status"));
        cell.setCellStyle(cellStyleTitle);
        
        int numCell = 0;
        XSSFCellStyle cellStyle;
        for (UseCaseReport r : tasks) {
        	if (!new File(r.getPath()).exists()) {
        		continue;
        	}
        	numCell = 0;
        	row = sheet.createRow(numRow++);
        	cellStyle = numRow % 2 == 0 ? cellStyleEven : cellStyleOdd;
        	// Nombre del Use Case
        	cell= row.createCell(numCell++);
        	cell.setCellStyle(cellStyle);
        	cell.setCellValue(new XSSFRichTextString(r.getUseCase()));
        	if (r.getError() != null) {
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
            	XSSFRichTextString error = new XSSFRichTextString(r.getError());
            	error.applyFont(fontError);
            	cell.setCellValue(error);
        		for (int i = 0; i < numCols-3; i++) {
        			cell= row.createCell(numCell++);
        			cell.setCellStyle(cellStyle);
				}
        		sheet.addMergedRegion(new CellRangeAddress(numRow-1,numRow-1,1,numCols-2));
        		// Dictamen
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		cell.setCellValue(fmtDictamen(r));
        	} else {

        		// Adquirente
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		if (r.getAcquirer() != null) {
        			cell.setCellValue(new XSSFRichTextString(r.getAcquirer()));
        		}
        		// Emisor
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		if (r.getIssuer() != null) {
        			cell.setCellValue(new XSSFRichTextString(r.getIssuer()));
        		}
        		// Validaciones
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		cell.setCellValue(fmtValidations(r.getValidations()));
        		
        		// Notificaciones
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		cell.setCellValue(fmtNotices(r.getNotices()));
        		
        		// AcquirerResponse Code
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		if (r.getResponseCode() != null) {
        			cell.setCellValue(new XSSFRichTextString(r.getResponseCode()));
        		}
        		
        		// Timestamp
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		if (r.get("Timestamp") != null) {
        			cell.setCellValue(new XSSFRichTextString(r.get(UseCase.TIMESTAMP)));
        		}
        		
        		// Dictamen
        		cell= row.createCell(numCell++);
        		cell.setCellStyle(cellStyle);
        		cell.setCellValue(fmtDictamen(r));

        	}
		}
        numUseCases = numUseCasesCorrect + numUseCasesIncorrect;
	}
	
	private void createSheetSummary(XSSFSheet sheet) {

		try {
			InputStream in = ReportXlsx.class.getResourceAsStream("/icons/EGlobal.png");
			if (in == null) {
				in = new FileInputStream("icons/EGlobal.png");
			}
			if (in != null) {
				CreationHelper helper = workbook.getCreationHelper();
				XSSFDrawing drawing = sheet.createDrawingPatriarch();
				int pictureIndex = workbook.addPicture(IOUtils.toByteArray(in), Workbook.PICTURE_TYPE_PNG);
				XSSFClientAnchor anchor = (XSSFClientAnchor)helper.createClientAnchor();
				//anchor.setAnchorType(AnchorType.MOVE_AND_RESIZE);
				anchor.setAnchorType(AnchorType.MOVE_DONT_RESIZE);
				anchor.setCol1(1);
				anchor.setRow1(1);
				//anchor.setRow2(1);
				//anchor.setCol2(1);
				Picture pict = drawing.createPicture(anchor, pictureIndex);
				pict.resize();
				//pict.resize(6.0);
			}
		} catch (Exception e) {
			Activator.getInstance().warning("Cannot found image EGlobal.png", e);
		}
		
        XSSFRow row;
        XSSFCell cell;
        
        int numRow = 9;
        int colOffset = 1;

        //sheet.setColumnWidth(colOffset+0, 30*256);
        //sheet.setColumnWidth(colOffset+1, 20*256);

        row = sheet.createRow(numRow);

        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Summary"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+4).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+5).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+6));
        numRow++;
        
        row = sheet.createRow(numRow);
        
        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Project"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+3));

        cell= row.createCell(colOffset+4);
        cell.setCellValue(new XSSFRichTextString(project != null ? project.getName() : "F8"));
        cell.setCellStyle(cellStyleOdd);
        row.createCell(colOffset+5).setCellStyle(cellStyleEven);
        row.createCell(colOffset+6).setCellStyle(cellStyleEven);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset+4,colOffset+6));
        numRow++;
        
        row = sheet.createRow(numRow);
        
        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Number of Use Cases"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+3));

        cell= row.createCell(colOffset+4);
        cell.setCellValue(new XSSFRichTextString(""+numUseCases));
        cell.setCellStyle(cellStyleEven);
        row.createCell(colOffset+5).setCellStyle(cellStyleEven);
        row.createCell(colOffset+6).setCellStyle(cellStyleEven);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset+4,colOffset+6));
        numRow++;
        
        row = sheet.createRow(numRow);

        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Correct Use Cases"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+3));
        

        cell= row.createCell(colOffset+4);
        cell.setCellValue(new XSSFRichTextString(""+numUseCasesCorrect));
        cell.setCellStyle(cellStyleOdd);
        row.createCell(colOffset+5).setCellStyle(cellStyleOdd);
        row.createCell(colOffset+6).setCellStyle(cellStyleOdd);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset+4,colOffset+6));
        numRow++;
        
        row = sheet.createRow(numRow);

        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Incorrect Use Cases"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+3));


        cell= row.createCell(colOffset+4);
        cell.setCellValue(new XSSFRichTextString(""+numUseCasesIncorrect));
        cell.setCellStyle(cellStyleEven);
        row.createCell(colOffset+5).setCellStyle(cellStyleEven);
        row.createCell(colOffset+6).setCellStyle(cellStyleEven);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset+4,colOffset+6));
        numRow++;
        
        row = sheet.createRow(numRow);
        
        cell= row.createCell(colOffset+0);
        cell.setCellValue(new XSSFRichTextString("Status"));
        cell.setCellStyle(cellStyleTitle);
        row.createCell(colOffset+1).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+2).setCellStyle(cellStyleTitle);
        row.createCell(colOffset+3).setCellStyle(cellStyleTitle);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset,colOffset+3));

        cell= row.createCell(colOffset+4);
        cell.setCellValue(fmtDictamen());
        cell.setCellStyle(numUseCasesIncorrect == 0 ? cellStyleDictamenCorrect : cellStyleDictamenIncorrect);
        row.createCell(colOffset+5).setCellStyle(numUseCasesIncorrect == 0 ? cellStyleDictamenCorrect : cellStyleDictamenIncorrect);
        row.createCell(colOffset+6).setCellStyle(numUseCasesIncorrect == 0 ? cellStyleDictamenCorrect : cellStyleDictamenIncorrect);
        sheet.addMergedRegion(new CellRangeAddress(numRow,numRow,colOffset+4,colOffset+6));
        numRow++;
        
        
	}
	private XSSFRichTextString fmtDictamen() {
		XSSFRichTextString fmt;
		if (numUseCasesIncorrect == 0) {
			fmt = new XSSFRichTextString("APPROVED");
		} else {
			fmt = new XSSFRichTextString("DECLINED");
		}
		fmt.applyFont(fontDictamen);
		return fmt;
	}
	private XSSFRichTextString fmtValidations(List<Validation> validations) {

		if (validations != null) {
			XSSFRichTextString fmt = new XSSFRichTextString();
			XSSFFont font;
			Validation last = validations.get(validations.size()-1);
			String sval;
	    	for (Validation v : validations) {
    			font = v.getFail() != null && v.getFail() ? fontError : fontDefault;
    			sval = "";
    			sval += v.getName() + ":";
    			sval += "["+v.getExpected()+"][" + v.getOperator() +"]["+ v.getValue()+"]";
				if (v != last) {
					sval += "\n";
				}
				fmt.append(sval, font);
			}
	    	return fmt;
		}
		return new XSSFRichTextString("N/A");
	}
	private XSSFRichTextString fmtDictamen(UseCaseReport r) {
		XSSFRichTextString fmtDictamen = null;
		if (r.getValidations() != null) {
			for (Validation v : r.getValidations()) {
				if (v.getFail() != null && v.getFail()) {
					fmtDictamen = new XSSFRichTextString("INCORRECT");
					fmtDictamen.applyFont(fontIncorrectBold);
					break;
				}
			}
		}
		if (fmtDictamen != null) {
			numUseCasesIncorrect++;
			return fmtDictamen;
		} else {
			numUseCasesCorrect++;
			fmtDictamen = new XSSFRichTextString("CORRECT");
			fmtDictamen.applyFont(fontCorrectBold);
			return fmtDictamen;
		}
	}
	private XSSFRichTextString fmtNotices(List<Notice> notices) {

		if (notices != null) {
			XSSFRichTextString fmt = new XSSFRichTextString();
			XSSFFont font;
			Notice last = notices.get(notices.size()-1);
			String sval;
	    	for (Notice v : notices) {
    			font = v.getSeverity() == Notice.ERROR && v.getSeverity() == Notice.WARNING ? fontError : fontDefault;
    			sval = "";
    			sval += v.getError();
				if (v != last) {
					sval += "\n";
				}
				fmt.append(sval, font);
			}
	    	return fmt;
		}
		return new XSSFRichTextString("N/A");
	}
}
