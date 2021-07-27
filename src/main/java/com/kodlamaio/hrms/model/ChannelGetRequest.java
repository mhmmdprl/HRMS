package com.kodlamaio.hrms.model;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChannelGetRequest {
	
	
    private String uuid;
	private List<MainFeaturesOfTheCandidate> candidatesInChannel;
	private HaventSeenMessagesRequest  havetnSeenMessages;
	private String lastMessage;
}
