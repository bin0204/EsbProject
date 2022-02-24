package com.sjinc.esb.test.service.data;

import java.util.Map;

import org.apache.camel.Body;
import org.apache.camel.Headers;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

@Component
public class CsProdInfoService {

	private static final String MAPPER_PRIFIX = "SJERP"; // 맵퍼 접두사
	private static final String MAPPER_NAME = "CsProdInfoMapper";

	private static final Logger logger = LoggerFactory.getLogger(CsProdInfoService.class);

	@Autowired // 아래얘네로 연결~ 
	@Qualifier("SJERP_SQL_SESSION")
	private SqlSessionTemplate sqlSession;

	@Transactional("DATASOURCE_SJERP_TX_MANAGER")
	public int insert(@Body String body, @Headers Map<String, Object> headers) throws Exception {

		logger.info("Message parameter ===> {}", body);
		int result = 0;

		try {

			Gson gson = new GsonBuilder().serializeNulls().create();
			com.sjinc.esb.test.vo.CsProdInfoDto parm = gson.fromJson(body, com.sjinc.esb.test.vo.CsProdInfoDto.class);

			result = sqlSession.insert(String.format("%s.%s.%s", MAPPER_PRIFIX, MAPPER_NAME, "insert"), parm);

		} catch (Exception e) {
			logger.error("", e);
			throw new Exception(e);
		}
		return result;
	}

}
