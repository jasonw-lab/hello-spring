package com.hello.hutool;

import cn.hutool.core.util.ArrayUtil;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Slf4j
@SpringBootTest
public class TemplateTest {
	
	@Test
	public void testCase() {

		String[] newArray = ArrayUtil.newArray(String.class, 3);
	}

	//　ソート関数
	@Test //Add @Test annotation
	public void testSort() {  // Added 'public' modifier
		// テストデータ
		int[] array = { 3, 1, 4, 1, 5, 9, 2, 6, 5, 3 };
		// ソート
		Arrays.sort(array);  // Change this line
		// 結果確認
		Assert.assertArrayEquals(new int[] { 1, 1, 2, 3, 3, 4, 5, 5, 6, 9 }, array);
	}


}

