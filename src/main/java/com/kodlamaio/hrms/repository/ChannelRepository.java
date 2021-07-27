package com.kodlamaio.hrms.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.kodlamaio.hrms.entities.Channel;
import com.kodlamaio.hrms.entities.Message;

public interface ChannelRepository extends JpaRepository<Channel, Long> {

	@Query("FROM Channel c  where exists(select cp from c.participants cp where cp.id=?1)")
	Page<Channel> findMyChannels(Long id, Pageable pageable);

	Channel findByUuid(String uuid);

	@Query("select case when count(c)> 0 then true else false end from Channel c where"
			+ " exists(select cc from c.candidatesInChannel cc where cc.id= ?1) and "
			+ "exists(select cc from c.candidatesInChannel cc where cc.id= ?2)")
	boolean existsByToId(Long id1, Long id2);

	@Query("select c.messages from Channel c join c.messages cm  where c.uuid= ?1")
	Page<Message> getMessagesOfChannel(String uuid, Pageable pageable);

	@Query("select case when count(c)> 0 then true else false end from Channel c where c.uuid=?1 and exists(select cp from c.participants cp where cp.id= ?2) ")
	boolean existsParticipants(String uuid, Long id);

	@Query("FROM Channel c  where exists(select cp from c.participants cp where cp.id=?1)")
	List<Channel> getMyChannelsList(Long userIdFromRequest);

	@Query(" from Channel c where exists(select cc from c.candidatesInChannel cc where cc.id= ?1) and"
			+ " exists(select cc from c.candidatesInChannel cc where cc.id= ?2)")
	Channel getChannel(Long id, Long id2);

	@Query("FROM Channel c  where exists(select cc from c.candidatesInChannel cc where cc.id=?1)")
	Page<Channel> getCandidateChannels(Long id, Pageable pageable);

//	@Query("select c.messages FROM  Channel c   JOIN FETCH Message=where c.uuid=?1")
//	List<Message> findMessagesByUuid(String uuid, Pageable pageable);
}
