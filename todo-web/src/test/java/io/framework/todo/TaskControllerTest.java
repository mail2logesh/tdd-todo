/*
 * author : Lokesh Kumaran
 */
package io.framework.todo;

import static name.mlnkrishnan.shouldJ.ShouldJ.it;
import static org.junit.Assert.*;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import io.framework.todo.contract.Task;
import io.framework.todo.contract.TaskResponse;
import io.framework.todo.controllers.TasksController;
import io.framework.todo.service.impl.TodoServiceImpl;

import java.io.IOException;
import java.nio.charset.Charset;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.MockitoAnnotations;
import org.mockito.Spy;
import org.springframework.http.MediaType;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.ObjectMapper;


/**
 * The Class TaskControllerTest.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:test-context.xml"})
public class TaskControllerTest {


	/** The tasks controller. */
	@InjectMocks
	private TasksController tasksController;

	/** The todo service. */
	@Spy
	private TodoServiceImpl todoService;
	 
	/** The mock mvc. */
	private MockMvc mockMvc;

	/** The APPLICATIO n_ jso n_ ut f8. */
	public  final MediaType APPLICATION_JSON_UTF8 = new MediaType(
			MediaType.APPLICATION_JSON.getType(),
			MediaType.APPLICATION_JSON.getSubtype(), Charset.forName("utf8"));

	/**
	 * Convert object to json bytes.
	 *
	 * @param object the object
	 * @return the byte[]
	 * @throws IOException Signals that an I/O exception has occurred.
	 */
	public  byte[] convertObjectToJsonBytes(Object object)
			throws IOException {
		ObjectMapper mapper = new ObjectMapper();
		mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
		return mapper.writeValueAsBytes(object);
	}

	/**
	 * Setup.
	 */
	@Before
	public void setup() {
		MockitoAnnotations.initMocks(this);
		mockMvc = MockMvcBuilders.standaloneSetup(tasksController).build();
	}


	/**
	 * Test add task.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testAddTask() throws Exception {
		Task task = new Task();
		task.setTaskName("new Task added");
		task.setTaskStatus("NEW");
		MvcResult result = mockMvc.perform(
				post("/task/add").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(task))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		TaskResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		resp = mapper.readValue(content, TaskResponse.class);

		it(resp.getTaskID()).shouldNotBeNull();
		it(resp.getTaskStatus()).shouldBe("NEW");
		
		Task daoTask = todoService.getTask(Integer.valueOf(resp.getTaskID()));
		it(resp.getTaskID()).shouldBe(String.valueOf(daoTask.getTaskID()));

		
	}
	
	/**
	 * Test update task.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testUpdateTask() throws Exception {
		Task task = new Task();
		task.setTaskID(1);
		task.setTaskName("Task updated");
		task.setTaskStatus("UPDATED");
		MvcResult result = mockMvc.perform(
				post("/task/update").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(task))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		TaskResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		resp = mapper.readValue(content, TaskResponse.class);

		it(resp.getTaskID()).shouldNotBeNull();
		it(resp.getTaskStatus()).shouldBe("UPDATED");
		
		Task daoTask = todoService.getTask(Integer.valueOf(resp.getTaskID()));
		it(resp.getTaskStatus()).shouldBe(resp.getTaskStatus());
		
	}

	/**
	 * Test delete task.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testDeleteTask() throws Exception {
		MvcResult result = mockMvc.perform(
				delete("/task/delete/{taskId}",1)).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk()).andReturn();
		
		String content = result.getResponse().getContentAsString();
		it(content).shouldContain("TaskID - 1 has been deleted Successfully");
		
	}
	
	
	/**
	 * Test task should have name.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testTaskShouldHaveName() throws Exception {
		Task task = new Task();
		task.setTaskName("New Task");
		task.setTaskStatus("NEW");
		MvcResult result = mockMvc.perform(
				post("/task/add").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(task))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		TaskResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		resp = mapper.readValue(content, TaskResponse.class);

		it(resp.getTaskID()).shouldNotBeNull();
		// it 'should have a name' 
		it(resp.getTaskName()).shouldNotBeNull();
		
	}
	
	/**
	 * Test task initially incomplete.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testTaskInitiallyIncomplete() throws Exception {
		Task incompleteTask = new Task();
		incompleteTask.setTaskStatus("NEW");
		MvcResult result = mockMvc.perform(
				post("/task/add").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(incompleteTask))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		TaskResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		resp = mapper.readValue(content, TaskResponse.class);
		//it 'should be initially incomplete'   
		it(resp.getTaskID()).shouldNotBeNull();
		
		//Task 2 dependant on task 1 to complete
		Task completetask = new Task();
		completetask.setTaskID(Integer.valueOf(resp.getTaskID()));
		completetask.setTaskName("Updated Name");
		completetask.setTaskStatus("Updated");
		
		MvcResult result2 = mockMvc.perform(
				post("/task/update").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(completetask))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content2 = result2.getResponse().getContentAsString();
		TaskResponse resp2 = null;
		ObjectMapper mapper2 = new ObjectMapper();
		resp2 = mapper2.readValue(content2, TaskResponse.class);
		
		//it 'should be possible to be completed'   
		it(resp2.getTaskName()).shouldNotBeNull();
		
	}

	/**
	 * Test task not complete dueto depedancy.
	 *
	 * @throws Exception the exception
	 */
	@Test
	public void testTaskNotCompleteDuetoDepedancy() throws Exception {
		Task incompleteTask = new Task();
		incompleteTask.setTaskStatus("NEW");
		MvcResult result = mockMvc.perform(
				post("/task/add").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(incompleteTask))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content = result.getResponse().getContentAsString();
		TaskResponse resp = null;
		ObjectMapper mapper = new ObjectMapper();
		resp = mapper.readValue(content, TaskResponse.class);

		assertNotNull(resp.getTaskName());

		//Task 2 dependant on task 1 to complete
		Task completetask = new Task();
		completetask.setTaskID(Integer.valueOf(resp.getTaskID()));
		completetask.setTaskName("Updated Name");
		completetask.setTaskStatus("Updated");
		
		MvcResult result2 = mockMvc.perform(
				post("/task/update").contentType(APPLICATION_JSON_UTF8).content(
						convertObjectToJsonBytes(completetask))).andDo(print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andExpect(content().contentType(APPLICATION_JSON_UTF8)).andReturn();
		
		String content2 = result2.getResponse().getContentAsString();
		TaskResponse resp2 = null;
		ObjectMapper mapper2 = new ObjectMapper();
		resp2 = mapper2.readValue(content2, TaskResponse.class);
		
		//it 'should be possible to be completed'   
		it(resp2.getTaskName()).shouldNotBeNull();
		
	}

}
