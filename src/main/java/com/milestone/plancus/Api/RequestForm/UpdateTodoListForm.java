package com.milestone.plancus.Api.RequestForm;

import lombok.Data;

import java.util.List;

@Data
public class UpdateTodoListForm {
    private Long planId;
    private List<String> todoList;
}
