package org.bhu.time.utils;

public enum TimeFormat {

	YYYY_MM_DD("yyyy-MM-dd"), YYYY_MM_DD2("yyyy/MM/dd"), YYYY_MM_DD_HHMMSS("yyyy-MM-dd HH:mm:ss"), YYYY_MM("yyyy-MM"),
	YYYY("yyyy"), YYYY_MM_DD_HHMMSS_DEFAULT("yyyy/MM/dd HH:mm:ss"),YYMMDD("yyyyMMdd");
	
    
    private String value ;
     
    private TimeFormat( String value){
        this.value = value ;
    }
     
    public String getValue() {
        return value;
    }
    public void setValue(String value) {
        this.value = value;
    }
}
