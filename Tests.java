package com.pricecalculate.PriceCalculateManagement;

import java.util.UUID;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class Tests {

	@Test
	public void contextLoads() {
		String name ="Linux_StartUp";
	
		int n=1;
		int m=12;
	   for (int i = n; i <= m; i++) {
		   System.out.println("!["
		   		+ name
					+ i
					+ ".png](https://github.com/zhaodahan/zhao_Note/blob/master/wiki_img/"
					+ name
					+ i
					+ ".png?raw=true)");
	   }
	}
}
