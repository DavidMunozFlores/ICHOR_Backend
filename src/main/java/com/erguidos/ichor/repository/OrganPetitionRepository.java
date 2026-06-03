package com.erguidos.ichor.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.enums.OrganType;

@Repository
public interface OrganPetitionRepository extends JpaRepository<OrganPetition, Long> {
	Set<OrganPetition> findByOrganPetitionState(OrganPetitionState organPetitionState);
	Set<OrganPetition> findByOrganType(OrganType organType);
	
	@Query("SELECT op FROM OrganPetition op JOIN op.patient p where p.bloodType IN :bloodTypes")
	Set<OrganPetition> findByCompatibleBlood(@Param(value = "bloodTypes") List<BloodType> bloodTypes);
}
