package com.milestone.plancus.Api;

import com.milestone.plancus.Api.DTO.PlanFilterHeadDto;
import com.milestone.plancus.Api.DTO.TodoListDto;
import com.milestone.plancus.Api.RequestForm.RequestPlanForm;
import com.milestone.plancus.Api.RequestForm.UpdateTodoListForm;
import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Repository.PlanRepository;
import com.milestone.plancus.Repository.TodoRepository;
import com.milestone.plancus.Service.PlanService;
import com.milestone.plancus.Service.TodoService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;
import java.util.List;

@RestController
@RequiredArgsConstructor
public class TodoApiController {
    private final TodoService todoService;
    private final PlanService planService;

    @PostMapping("/todoList/update")
    public List<TodoListDto> updateTodoList(UpdateTodoListForm form, HttpSession httpSession){
        Member member = (Member) httpSession.getAttribute("member");

        return todoService.updateTodoList(form.getTodoList(), member, form.getPlanId());
    }
}
