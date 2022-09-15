package com.example.mall;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.example.mall.dao.CartInfoDao;
import com.example.mall.entity.CartInfoEntity;
import com.example.mall.entity.CategoryInfoEntity;
import com.example.mall.service.CategoryInfoService;
import org.junit.jupiter.api.Test;
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

}
