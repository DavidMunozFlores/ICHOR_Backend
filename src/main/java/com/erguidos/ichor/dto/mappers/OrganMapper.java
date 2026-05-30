package com.erguidos.ichor.dto.mappers;

import com.erguidos.ichor.dto.response.OrganTypeInfoResponse;
import com.erguidos.ichor.dto.response.RegisterOrganResponse;
import com.erguidos.ichor.entity.Organ;
import com.erguidos.ichor.enums.OrganType;

public class OrganMapper {
	public static RegisterOrganResponse toRregisterOrganResponse(Organ o) {
		return new RegisterOrganResponse(
				o.getOrganType(),
				o.getWeightGrams(),
				o.getVolumeCC(),
				o.getHla()); 
	}
	
	public static OrganTypeInfoResponse toOrganTypeInfoResponse(OrganType ot) {
		return new OrganTypeInfoResponse(ot, ot.getTime());
	}
}
