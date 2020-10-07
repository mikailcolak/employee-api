package com.employee.api.repository;

import com.employee.api.model.Employee;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

public interface EmployeeRepository extends JpaRepository<Employee, Long> {

    @Query("SELECT e FROM Employee e WHERE e.companyId = ?1")
    Page<Employee> findAllByCompanyId(Long id, Pageable pageable);

    @Query("SELECT COUNT(e) FROM Employee e WHERE e.companyId = ?1")
    Long countByCompanyId(Long id);

    @Query("SELECT AVG(e.salary) FROM Employee e WHERE e.companyId = ?1")
    float averageSalaryByCompanyId(Long id);

    @Transactional
    @Modifying
    @Query("DELETE FROM Employee WHERE companyId = ?1")
    void deleteAllByCompanyId(Long id);

}
