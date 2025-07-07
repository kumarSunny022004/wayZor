package com.wayzor.wayzor_backend.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DestinationAndTrainResponse {
    private List<DestinationSearchResponse> destinations;
    private List<TrainInfoDto> trains;
}
