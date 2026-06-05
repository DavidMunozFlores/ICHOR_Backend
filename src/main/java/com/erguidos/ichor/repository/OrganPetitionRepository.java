package com.erguidos.ichor.repository;

import java.util.List;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.erguidos.ichor.entity.Hospital;
import com.erguidos.ichor.entity.OrganPetition;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganPetitionState;
import com.erguidos.ichor.enums.OrganType;

@Repository
public interface OrganPetitionRepository extends JpaRepository<OrganPetition, Long> {
	@Query("SELECT o FROM OrganPetition o WHERE o.organPetitionState = :organPetitionState")
	Set<OrganPetition> findByOrganPetitionState(OrganPetitionState organPetitionState);
	
	Set<OrganPetition> findByOrganType(OrganType organType);
	
	@Query("SELECT op FROM OrganPetition op JOIN op.patient p WHERE p.bloodType IN :bloodTypes")
	Set<OrganPetition> findByCompatibleBlood(@Param(value = "bloodTypes") List<BloodType> bloodTypes);
	
	@Query("SELECT op FROM OrganPetition op JOIN op.patient p WHERE p.hospital IN :hospitals")
	Set<OrganPetition> findByCompatibleHospitals(@Param(value = "hospitals") List<Hospital> hospitals);
	
	@Query("SELECT op FROM OrganPetition op"
			+ " JOIN op.patient p"
			+ " WHERE op.organPetitionState = :organPetitionState"
			+ " AND op.organType = :organType"
			+ "	AND p.bloodType IN :bloodTypes"
			+ "	AND p.hospital IN :hospitals"
			+ "	AND op.weightGrams"
			+ " BETWEEN"
			+ " 	:weightGrams - :range AND :weightGrams + :range"
			+ " AND op.volumeCC"
			+ " BETWEEN"
			+ "		:volumeCC - :range AND :volumeCC + :range"
			+ "	AND op.organ IS NULL")
	Set<OrganPetition> findCompatibleOPs(
						OrganPetitionState organPetitionState,
						OrganType organType,
						@Param(value = "bloodTypes") List<BloodType> bloodTypes,
						@Param(value = "hospitals") List<Hospital> hospitals,
						@Param(value = "weightGrams") Double weightGrams,
						@Param(value = "volumeCC") Double volumeCC,
						@Param(value = "range") Double range
						);
}
