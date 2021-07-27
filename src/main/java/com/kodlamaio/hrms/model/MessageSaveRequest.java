package com.kodlamaio.hrms.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageSaveRequest {
	
    private String text;
    private Long candidateId;
    private String channelUuid;
    private String time;
}
