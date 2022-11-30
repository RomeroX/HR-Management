package org.ada.HRmanagement.repository;

import org.ada.HRmanagement.entity.Employee;
import org.ada.HRmanagement.entity.TalentProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface TalentProfileRepository extends JpaRepository<TalentProfile, Integer> {

    Optional<TalentProfile> findByEmployee(Employee employee);
}
