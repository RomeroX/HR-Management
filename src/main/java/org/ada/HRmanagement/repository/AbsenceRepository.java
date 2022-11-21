package org.ada.HRmanagement.repository;

import net.bytebuddy.asm.Advice;
import org.ada.HRmanagement.entity.Absence;
import org.ada.HRmanagement.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {

    Optional<Absence> findByEmployeeAndStartDateAndAbsenceTypeId(Employee employee, LocalDate startDate, Integer absenceTypeId);
    List<Absence> findByEmployee(Employee employee);
    Optional<Absence> findByIdAndEmployee (Integer id, Employee employee);
}
