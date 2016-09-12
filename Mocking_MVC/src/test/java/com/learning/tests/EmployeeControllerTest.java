package com.learning.tests;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Matchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mock.web.MockHttpServletRequest;
import org.springframework.mock.web.MockHttpSession;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.servlet.ModelAndView;

import com.learning.controller.EmployeeController;
import com.learning.entity.Employee;
import com.learning.service.EmployeeService;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.mockito.Mockito.*;
import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@WebAppConfiguration
@ContextConfiguration (locations = "classpath:mvc-dispatcher-servlet.xml")
public class EmployeeControllerTest {
	
	@Autowired 
	WebApplicationContext wac; 
    @Autowired
    MockHttpSession session;
    @Autowired 
    MockHttpServletRequest request;

    private MockMvc mockMvc;

	@InjectMocks
	@Autowired
	private EmployeeController employeeController;
    @Mock
	private EmployeeService employeeServicemock;
    private Employee employee;
    private ModelAndView mav;
    @Mock
    private ArrayList<Employee> list;
    
    //instance block for creating model 
   {
 	    employee=new Employee();
 	    employee.setId(1L);;
   	    employee.setAge(30);
     	employee.setName("Amit Jha");
   	    employee.setSalary(40000);
   	
    }
    @Before
    public void setup() {
    	MockitoAnnotations.initMocks(this);
        this.mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build();
        this.employeeServicemock=Mockito.mock(EmployeeService.class);
    }
    @Test
    public void createEmployeeTest() throws Exception{
    	
    	 mav=employeeController.createEmployee(employee);
    	assertEquals("employeeForm", mav.getViewName());
    	
    	mockMvc.perform(get("/createEmployee"))
    	            .andExpect(status().isOk())
    	            .andExpect(forwardedUrl("/WEB-INF/views/employeeForm.jsp"));

    }
    
    @Test
    public void editEmployeeTest()throws Exception{
    	
    	/*employee.setId(1L);
    	employee.setAge(30);
    	employee.setName("ABC");
    	employee.setSalary(40000);
    	*/
    	
    	when(employeeServicemock.getEmployee(Mockito.anyLong())).thenReturn(employee);
    	
    	 mav=employeeController.editEmployee(86L, employee);
    	 assertEquals("employeeForm", mav.getViewName());
    	 
   	     this.mockMvc.perform(post("/editEmployee?id={id}",1L))
        .andExpect(status().isOk())
        .andExpect(view().name("employeeForm"))
        ;
                             
    }
    
    //   for if it covers id == 0
    
   @Test
    public void saveEmployeeNotExitingTest()throws Exception{
	   employee.setId(0L);
	   System.out.println("saveEmployeeNotExitingTest "+employee.getId());
	   
    	assertEquals(0L, employee.getId());
    	when(employeeServicemock.createEmployee(employee)).thenReturn(0L);
    	mav=employeeController.saveEmployee(employee);
    	assertEquals("redirect:getAllEmployees", mav.getViewName());
    	
    	 mockMvc.perform(post("/saveEmployee"))
     	.andExpect(status().isMovedTemporarily())
     	.andExpect(redirectedUrl("getAllEmployees"));
     	
    }
   
   //   else it not covering id != 0
   
    @Test
    public void updateEmployeeExitingTest()throws Exception{
    	
    	//System.out.println("updateEmployeeExitingTest "+employee.getId());
    	
    	
    	//assertTrue(employee.getId()!=0);
    	when(employeeServicemock.updateEmployee(employee)).thenReturn(employee);
    	mav=employeeController.saveEmployee(employee);
    	assertEquals("redirect:getAllEmployees", mav.getViewName());
    	 mockMvc.perform(post("/saveEmployee"))
    	.andExpect(status().isMovedTemporarily())
    	.andExpect(redirectedUrl("getAllEmployees"));
    	
    	
    }
    
    @Test
public void deleteEmployeeTest()throws Exception{
    	
    	
    	doNothing().when(employeeServicemock).deleteEmployee(Matchers.anyLong());
    	mav=employeeController.deleteEmployee(Mockito.anyLong());
    	assertEquals("redirect:getAllEmployees", mav.getViewName());
    	 
    	
    }
    @Test
 public void getAllEmployeesTest() throws Exception{
	 when(employeeServicemock.getAllEmployees()).thenReturn(list);
	mav= employeeController.getAllEmployees();
	mockMvc.perform(post("/getAllEmployees"))
    .andExpect(status().isOk())
    .andExpect(model().attributeExists("employeeList"))
    .andExpect(forwardedUrl("/WEB-INF/views/employeeList.jsp"));
	 
 }
    @Test
    public void searchEmployeeTest()throws Exception{
    	when(employeeServicemock.getAllEmployees(Matchers.anyString())).thenReturn(list);
    	mav=employeeController.searchEmployee(request.getParameter("searchName"));
    	mockMvc.perform(post("/"))
        .andExpect(status().isOk())
        .andExpect(model().attributeExists("employeeList"))
        .andExpect(forwardedUrl("/WEB-INF/views/employeeList.jsp"));
    }
}
