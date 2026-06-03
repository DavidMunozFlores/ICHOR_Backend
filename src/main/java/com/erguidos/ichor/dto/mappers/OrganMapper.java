package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.OrganBloodTypeInfoResponse;
import com.erguidos.ichor.dto.response.OrganTypeInfoResponse;
import com.erguidos.ichor.dto.response.RegisterOrganResponse;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.enums.BloodType;
import com.erguidos.ichor.enums.OrganType;

public class OrganMapper {
	private OrganMapper() {}

	public static RegisterOrganResponse toRregisterOrganResponse(Organ o) {
		if(o == null) return null;
		
		return new RegisterOrganResponse(
				o.getOrganType(),
				o.getWeightGrams(),
				o.getVolumeCC(),
				o.getHla(),
				o.getBloodType()
				); 
	}
	
	public static OrganTypeInfoResponse toOrganTypeInfoResponse(OrganType ot) {
		return new OrganTypeInfoResponse(ot, ot.getTime());
	}
	
	public static OrganBloodTypeInfoResponse toOrganBloodTypeInfoResponse(BloodType bt) {
		return new OrganBloodTypeInfoResponse(bt);
	}
}
