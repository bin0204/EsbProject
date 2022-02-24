package com.sjinc.esb.test.service.servlet;

import org.apache.camel.Exchange;
import org.apache.camel.Message;
import org.apache.camel.Processor;
import org.springframework.stereotype.Component;

import com.sjinc.esb.test.vo.EsbRcvResultVo;

@Component
public class AckService implements Processor {

	public void process(Exchange exchange) throws Exception {

		// return 객체를 생성한다.
		EsbRcvResultVo esbRcvResultVo = new EsbRcvResultVo();
		esbRcvResultVo.setEsbRcvResultCd(EsbRcvResultVo.ESB_RCV_RESULT_OK);

		// make response message
		Message in = exchange.getIn();
		in.setHeader(Exchange.CONTENT_TYPE, "application/json");
		in.setBody(esbRcvResultVo);
	}

}