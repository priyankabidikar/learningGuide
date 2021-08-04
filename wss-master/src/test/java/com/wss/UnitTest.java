package com.wss;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.ArrayList;
import com.wss.controller.DevicesController;
import com.wss.controller.LoginContoller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import jdk.nashorn.internal.ir.annotations.Ignore;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UnitTest {
	
	private ArrayList<String> list;

	@Autowired
	private LoginContoller loginController;
	
	@Autowired
	private DevicesController devicesController;
	
	@Test
	public void loginContollerLoginPage() {
		Assert.assertEquals("login", loginController.getLoginPage());
	}
	@Test
	public void loginControllerHomePage(){
		Assert.assertEquals("index", loginController.getHomePage());
		Assert.assertEquals("analytics", loginController.getAnalytics());
	}
	
	@Test
	public void devices() {
		if(null != devicesController.getAllDevices()) {
			Assert.assertTrue("Output is not a null object", true);
		}else {
			Assert.fail();
		}
	}
	@Test
	public void session() {
		Assert.assertEquals("redirect:login",loginController.logout());
	}
	@BeforeClass		
    public static void m1() {							
        System.out.println("Using @BeforeClass , executed before all test cases ");					
    }		

    @Before		
    public void m2() {					
        list = new ArrayList<String>();					
        System.out.println("Using @Before annotations ,executed before each test cases ");					
    }		

    @AfterClass		
    public static void m3() {							
        System.out.println("Using @AfterClass ,executed after all test cases");					
    }		

    @After		
    public void m4() {					
        list.clear();			
        System.out.println("Using @After ,executed after each test cases");					
    }		

    @Test		
    public void m5() {					
        list.add("test");					
        Assert.assertFalse(list.isEmpty());			
        Assert.assertEquals(1, list.size());			
    }		

    @Ignore		
    public void m6() {					
        System.out.println("Using @Ignore , this execution is ignored");					
    }		
	@Test
public void m8()
{
   String str="This is my second junit program";
   Assert.assertEquals("This is my second junit program",str);
}
}
