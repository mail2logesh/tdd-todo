package io.framework.todo.controllers;

import io.framework.todo.contract.TaskResponse;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class TasksController {

    @RequestMapping(method = RequestMethod.GET, value = "/profile/{taskId}")
    public @ResponseBody
    TaskResponse profile(@PathVariable String taskId) {
        return new TaskResponse(taskId, "Complete Programming Assignment", "In Progress");
    }
}
