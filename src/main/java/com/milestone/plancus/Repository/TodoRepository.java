package com.milestone.plancus.Repository;

import com.milestone.plancus.Domain.Member;
import com.milestone.plancus.Domain.TodoList;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TodoRepository extends JpaRepository<TodoList, Long> {
    @Modifying
    @Query("delete from TodoList t where TD_PLAN_ID = :planId and TD_MEM_ID = :memberId")
    void removeTodoListByPlanWithMember(
            @Param("planId") Long planId,
            @Param("memberId") Long memberId);

    @Query("select t from TodoList t where TD_PLAN_ID = :planId and TD_MEM_ID = :memberId")
    List<TodoList> findTodoListByPlanWithMember(
            @Param("planId") Long planId,
            @Param("memberId") Long memberId);
}
