package com.moneysplitter.mapper;

import com.moneysplitter.config.MapperConfig;
import com.moneysplitter.controller.data.PartyWithAggregatesResponse;
import com.moneysplitter.controller.data.PartyUpdateRequest;
import com.moneysplitter.dao.postgresql.entity.PartyEntity;
import com.moneysplitter.controller.data.PartyCreateRequest;
import com.moneysplitter.controller.data.PartyResponse;
import com.moneysplitter.dao.postgresql.entity.PartyWithCollectionsEntity;
import com.moneysplitter.model.Party;
import com.moneysplitter.model.PartyUpdateData;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;

import java.util.List;

@Mapper(config = MapperConfig.class)
public interface PartyMapper {

    PartyResponse toResponse(Party party);

    List<PartyResponse> toResponses(List<Party> parties);

    PartyEntity toEntity(Party party);

    @Mapping(target = "totalAmount", ignore = true)
    Party fromEntity(PartyEntity partyEntity);

    List<Party> fromEntities(List<PartyEntity> entities);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "totalAmount", constant = "0")
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    Party fromCreateRequest(PartyCreateRequest createRequest);

    PartyUpdateData fromUpdateRequest(PartyUpdateRequest updateRequest);

    @Mapping(target = "totalAmount", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "createDate", ignore = true)
    @Mapping(target = "updateDate", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFields(PartyUpdateData participantUpdateData, @MappingTarget Party partyTarget);

    @Mapping(target = "spendingsCount", expression = "java(entity.getSpendings().size())")
    @Mapping(target = "participantsCount", expression = "java(entity.getParticipants().size())")
    @Mapping(
            target = "totalAmount",
            expression = """
                    java(entity.getSpendings()
                            .stream()
                            .map(com.moneysplitter.dao.postgresql.entity.SpendingEntity::getAmount)
                            .reduce(BigDecimal.ZERO, BigDecimal::add))
                    """
    )
    PartyWithAggregatesResponse toAggregatedResponse(PartyWithCollectionsEntity entity);

    List<PartyWithAggregatesResponse> toAggregatedResponses(List<PartyWithCollectionsEntity> entities);
}
