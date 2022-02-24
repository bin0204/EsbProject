package com.sjinc.esb.test.vo;

public class EsbRcvResultVo {
	
	public static final String ESB_RCV_RESULT_OK = "OK";
	public static final String ESB_RCV_RESULT_ERROR = "ER";
	
	private String EsbRcvResultCd;
	private String EsbRcvResultMessage;
	public String getEsbRcvResultCd() {
		return EsbRcvResultCd;
	}
	public void setEsbRcvResultCd(String esbRcvResultCd) {
		EsbRcvResultCd = esbRcvResultCd;
	}
	public String getEsbRcvResultMessage() {
		return EsbRcvResultMessage;
	}
	public void setEsbRcvResultMessage(String esbRcvResultMessage) {
		EsbRcvResultMessage = esbRcvResultMessage;
	}
}
