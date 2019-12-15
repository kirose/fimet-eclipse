package com.fimet.core;

public interface IPreferencesManager extends IManager {
	public static final String ENVIROMENT_AUTOSTART = "EnviromentAutoconnect";
	public static final String BDK = "BDK";
	public static final String UPDATE_DATES_ON_PARSE = "UpdateDatesOnParse";
	public static final String UPDATE_DATES_ON_FORMAT = "UpdateDatesOnFormat";
//	public static final String UPDATE_CHECK_DIGIT = "PanUpdateCheckDigit";
	public static final String VALIDATE_TYPES_FIELD_FORMAT = "ValidateTypesFieldFormat";
//	public static final String FAIL_ON_PARSE_FIELD_ERROR = "FailOnParseFieldError";
	public static final String CREATE_SIMQUEUE_READ_ACQUIRER = "CreateSimQueueReadAcquirer";
	public static final String CREATE_SIMQUEUE_WRITE_ACQUIRER = "CreateSimQueueReadAcquirer";
	public static final String CREATE_SIMQUEUE_READ_ISSUER = "CreateSimQueueReadIssuer";
	public static final String CREATE_SIMQUEUE_WRITE_ISSUER = "CreateSimQueueWriteIssuer";
	public static final String CONSOLE_LEVEL= "ConsoleLevel";
	public static final String KUSUNOKI_COUNT = "KusunokiCount";
	public static final String KUSUNOKI_MODE = "KusunokiMode";
	public static final String TIME_EXCECUTION_USE_CASE = "TimeExcecutionUseCase";

	public static final String EXTRACTOR_ENABLE = "ExtractorEnable";
	public static final String EXTRACTOR_GARBAGE_CYCLE_TIME_SEC = "ExtractorGarbageTime";
	public static final String EXTRACTOR_READER_CYCLE_TIME_SEC = "ExtractorReaderTime";
	public static final String EXTRACTOR_STATE = "ExtractorState";
	public static final String EXTRACTOR_FTP_STATE = "ExtractorFTPState";
	
	public static final String LOG_ENABLE = "LogEnable";
	public static final String LOG_GARBAGE_CYCLE_TIME_SEC = "LogGarbageTime";
	public static final String LOG_READER_CYCLE_TIME_SEC = "LogReaderTime";
	public static final String LOG_TIME_STATE = "LogTime";
	public static final String LOG_POSITION_STATE = "LogPosition";
	public static final String LOG_FTP_STATE = "LogFtpState";
	
	
	public String getString(String name, String defaultValue);
	public String getString(String name);
	public void save(String name, String value);
	public Integer getInt(String name, Integer defaultValue);
	public Integer getInt(String name);
	public void save(String name, Integer value);
	public void save(String name, int value);
	public double getDouble(String name, double defaultValue);
	public double getDouble(String name);
	public void save(String name, double value);
	public Long getLong(String name, long defaultValue);
	public long getLong(String name);
	public void save(String name, long value);
	public boolean getBoolean(String name, boolean defaultValue);
	public boolean getBoolean(String name);
	public void save(String name, boolean value);
	public void remove(String name);
}
