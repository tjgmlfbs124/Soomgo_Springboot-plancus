package com.milestone.plancus.Service;

import com.milestone.plancus.Api.DTO.TodoListDto;
import com.milestone.plancus.Domain.*;
import com.milestone.plancus.Repository.PlanRepository;
import com.milestone.plancus.Repository.TodoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TodoService {
    private final TodoRepository todoRepository;
    private final PlanRepository planRepository;

    /**
     * save()
     * 공유 일정을 확정하고나서, 저장된 각 멤버별 plan 정보와, head값을 가져와서 그 값을 토대로 todolist 추가
     * @param plans : 저장된 각 멤버별 plan 정보
     * @param head : 공유일정의 head
     * @return
     */
    @Transactional(readOnly = false)
    public Long saveByHead(List<Plan> plans, PlanFilterHead head){
        if(head.getTodoList() == null) return 1L;

        if (head.getTodoList().split("&&").length > 0){
            for (Plan plan : plans) { // 1 2 3
                for (String todo : head.getTodoList().split("&&")) {
                    todoRepository.save(new TodoList(plan.getMember(), plan, todo));
                }
            }
        }

        return 1L;
    }

    /**
     * save()
     * 공유 일정을 확정하고나서, 저장된 각 멤버별 plan 정보와, head값을 가져와서 그 값을 토대로 todolist 추가
     * @param plan : 저장된 일정정보
     * @param list : 투두리스트
     * @param member : 추가한 멤버
     * @return
     */
    @Transactional(readOnly = false)
    public Long saveByPlan(Plan plan, List<String> list, Member member){

        for (String todo : list) {
            todoRepository.save(new TodoList(member, plan, todo));

        }

        return 1L;
    }

    @Transactional(readOnly = false)
    public List<TodoListDto> updateTodoList(List<String> contents, Member member, Long planId){
        List<TodoListDto> todoListDtos = new ArrayList<>();
        Plan plan = planRepository.findById(planId).stream().findAny().get();

        todoRepository.removeTodoListByPlanWithMember(plan.getId(), member.getId());

        for (String content : contents) {
            todoListDtos.add(todoRepository.save(new TodoList(member, plan, content)).toDto());
        }

        return todoListDtos;
    }


}
