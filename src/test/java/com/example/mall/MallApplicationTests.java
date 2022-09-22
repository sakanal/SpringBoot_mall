package com.example.mall;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.json.JSONUtil;
import com.alibaba.druid.support.json.JSONUtils;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.controller.CategoryInfoController;
import com.example.mall.controller.SimpleLoginController;
import com.example.mall.dao.CartInfoDao;
import com.example.mall.entity.CartInfoEntity;
import com.example.mall.entity.CategoryInfoEntity;
import com.example.mall.entity.UserAddressEntity;
import com.example.mall.service.CategoryInfoService;
import com.example.mall.service.MessageSendService;
import com.example.mall.service.impl.CartInfoServiceImpl;
import com.example.mall.vo.R;
import com.example.mall.vo.UserRegisterVo;
import org.junit.jupiter.api.Test;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.HashMap;
import java.util.List;

@SpringBootTest
class MallApplicationTests {
	@Autowired
	private CartInfoDao cartInfoDao;
	@Autowired
	private CategoryInfoService categoryInfoService;
	@Autowired
	private MessageSendService messageSendService;
	@Autowired
	CategoryInfoController categoryInfoController;
	@Autowired
	private CartInfoServiceImpl cartInfoService;
	@Autowired
	private SimpleLoginController loginController;
	@Test
	void contextLoads() {
//		System.out.println(IdWorker.get32UUID()+Thread.currentThread().getName());
//		Map<String, Object> stringObjectHashMap = new HashMap<>();
//		stringObjectHashMap.put("arg","arg1");
		HashMap<String, Object> map = new HashMap<>();
//		map.put("current",12);
		Page<CategoryInfoEntity> page = categoryInfoService.getPage(map);
		System.out.println(page.getCurrent());
		List<CategoryInfoEntity> records = page.getRecords();
		for (CategoryInfoEntity record : records) {
			System.out.println(record);
		}
		System.out.println(page.getPages());
		System.out.println(page.getTotal());
	}
	@Test
	void testID(){
		HashMap<String, Object> params = new HashMap<>();
		Integer current= new Integer(params.get("current").toString());

	}
/*
	@Test
	public void testSendMsg(){
		messageSendService.sendMsg("2501947461@qq.com","123456");
	}*/

	@Test
	void test(){
//		R typePath = categoryInfoController.getTypePath(1000L);
//		System.out.println(typePath.toString());
		/*CartInfoEntity cartInfoEntity = new CartInfoEntity();
		cartInfoEntity.setUserId("666");
		cartInfoEntity.setProductId("7777");
		cartInfoEntity.setNumber(7);
		cartInfoService.updateByUserId(cartInfoEntity);*/
//		categoryInfoService.getProductByCatId(1,2,15L);
		UserRegisterVo userRegisterVo = new UserRegisterVo();
		userRegisterVo.setEmail("2501947461@qq.com");
		loginController.getMessageCode(userRegisterVo);
	}

	@Test
	void jsonGet(){
		UserAddressEntity userAddressEntity = new UserAddressEntity();
		userAddressEntity.setAddress("江西省南昌市青山湖区");
		userAddressEntity.setName("收货人姓名");
		userAddressEntity.setPhone("123456789101");
		userAddressEntity.setUserId("6135416514");
		System.out.println(JSONUtil.toJsonStr(userAddressEntity));
	}

}
