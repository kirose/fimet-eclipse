package com.fimet.core.entity.sqlite;

import java.util.Date;

import com.fimet.commons.utils.DateUtils;
import com.fimet.commons.utils.ReflectUtils;
import com.fimet.core.entity.sqlite.pojo.Time;
import com.fimet.core.entity.sqlite.type.TimeType;
import com.j256.ormlite.field.DatabaseField;
import com.j256.ormlite.table.DatabaseTable;

/**
 * 
 * @author <a href="mailto:marcoasb99@ciencias.unam.mx">Marco A. Salazar</a>
 *
 */
@DatabaseTable(tableName = "ExtractorWindow")
public class ExtractorWindow {

	@DatabaseField(id = true)
	private Integer id;
	@DatabaseField(canBeNull = false)
	private Integer idTypeEnviroment;
	@DatabaseField(canBeNull = false)
	private String enviroment;
	@DatabaseField(canBeNull = false)
	private Integer numberOfWindow;
	@DatabaseField(canBeNull = false, persisterClass=TimeType.class)
	private Time timeStart;
	@DatabaseField(canBeNull = false, persisterClass=TimeType.class)
	private Time timeEnd;
	
	private Date day;
	private Date start;
	private Date end;
	private String fileName;

	public ExtractorWindow() {}
	public ExtractorWindow(Integer idTypeEnviroment, String enviroment, Integer numberOfWindow, Time timeStart, Time timeEnd) {
		super();
		this.enviroment = enviroment;
		this.idTypeEnviroment = idTypeEnviroment;
		this.numberOfWindow = numberOfWindow;
		this.timeStart = timeStart;
		this.timeEnd = timeEnd;
	}
	public ExtractorWindow(ExtractorWindow window) {
		super();
		ReflectUtils.copy(window, this);
	}
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getIdTypeEnviroment() {
		return idTypeEnviroment;
	}
	public void setIdTypeEnviroment(Integer idTypeEnviroment) {
		this.idTypeEnviroment = idTypeEnviroment;
	}
	public String getEnviroment() {
		return enviroment;
	}
	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
	}
	public Integer getNumberOfWindow() {
		return numberOfWindow;
	}
	public void setNumberOfWindow(Integer numberOfWindow) {
		this.numberOfWindow = numberOfWindow;
	}
	public Date getDay() {
		return day;
	}
	public void setDay(Date day) {
		this.day = day;
	}
	public String getFileName() {
		return fileName;
	}
	public void setFileName(String fileName) {
		this.fileName = fileName;
	}
	public Time getTimeStart() {
		return timeStart;
	}
	public void setTimeStart(Time timeStart) {
		this.timeStart = timeStart;
	}
	public Time getTimeEnd() {
		return timeEnd;
	}
	public void setTimeEnd(Time timeEnd) {
		this.timeEnd = timeEnd;
	}
	public Date getStart() {
		return start;
	}
	public void setStart(Date start) {
		this.start = start;
	}
	public Date getEnd() {
		return end;
	}
	public void setEnd(Date end) {
		this.end = end;
	}
	public boolean isActive() {
		return this.end.before(new Date());
	}
	@Override
	public String toString() {
		return "[" + id + ", "+(day != null ? DateUtils.MMddyy_FMT.format(day) : null)+" "+(timeStart)+"-"+(timeEnd)+"]";
	}
}
