package com.knowget.knowgetbackend.domain.reply.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.knowget.knowgetbackend.global.entity.Reply;

@Repository
public interface ReplyRepository extends JpaRepository<Reply, Integer> {

	@Query("SELECT r FROM Reply r WHERE r.comment.commentId = :commentId ORDER BY r.createdDate ASC")
	List<Reply> findAllByCommentIdOrderByCreatedDateAsc(@Param("commentId") Integer commentId);

}
