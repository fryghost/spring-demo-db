package com.hfzq.demodb.repository;

import com.hfzq.demodb.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @Description departmentDAO
 * @Author Zephyr Lin
 * @Date 2022/3/1 10:34
 **/
@Repository
public interface DepartmentDao extends JpaRepository<Department, Long> {
    /**
     * 根据层级查询部门
     *
     * @param level 层级
     * @return 部门列表
     */
    List<Department> findDepartmentsByLevels(Integer level);
}