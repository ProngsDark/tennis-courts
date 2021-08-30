package com.tenniscourts.tenniscourts.utils;

import com.tenniscourts.tenniscourts.models.TennisCourt;
import com.tenniscourts.tenniscourts.models.TennisCourtCreationRequestDTO;
import com.tenniscourts.tenniscourts.models.TennisCourtDTO;
import org.mapstruct.InheritInverseConfiguration;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TennisCourtMapper {
    TennisCourtDTO map(TennisCourt source);

    @InheritInverseConfiguration
    TennisCourt map(TennisCourtDTO source);

    TennisCourt map(TennisCourtCreationRequestDTO source);
}
