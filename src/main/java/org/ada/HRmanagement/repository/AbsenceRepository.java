package org.ada.HRmanagement.repository;

import org.ada.HRmanagement.entity.Absence;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AbsenceRepository extends JpaRepository<Absence, Integer> {
}
