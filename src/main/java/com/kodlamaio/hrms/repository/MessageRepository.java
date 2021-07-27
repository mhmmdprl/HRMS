package com.kodlamaio.hrms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.entities.Message;

public interface MessageRepository extends JpaRepository<Message, Long> {

	@Query("From Message m where m.deleted='0' and m.channelOfMessage=?1 and not exists(select w from m.thoseWhoDelete w where w.id=?2)")
	Page<Message> getMessagesOfChannel(Channel uuid,Long id, Pageable pageable);
	
	@Query("From Message m where m.deleted='0' and m.channelOfMessage=?1 and not exists(select w from m.thoseWhoDelete w where w.id=?2)")
	List<Message> getMessagesOfChannelList(Channel uuid,Long id);
	@Query("From Message m where m.deleted='0' and m.channelOfMessage=?1 and not exists(select w from m.thoseWhoDelete w where w.id=?2) and "
			+ "not exists(select mws from m.whoSawMessage mws where mws.id=?2)")
	List<Message> getMessagesHaventSeen(Channel uuid,Long id);

	@Query("Select Count(m) From Message m where m.deleted='0' and m.channelOfMessage in ?2 and  not exists(select w from m.thoseWhoDelete w where w.id=?1) and "
			+ "not exists(select mws from m.whoSawMessage mws where mws.id=?1) ")
	int getHaventSeenMessagesCount(Long userIdFromRequest, List<Channel> channelsUuid);

	Message findByUuid(String uuid);

	@Query("From Message m where m.channelOfMessage=?1 ")
	Page<Message> getCandidateMessagesOfChannel(Channel findByUuid, Long id, Pageable pageable);

}
