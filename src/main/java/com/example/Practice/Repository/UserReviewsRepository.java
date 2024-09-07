package com.example.Practice.Repository;

import com.example.Practice.UserReviews;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
public interface UserReviewsRepository extends JpaRepository<UserReviews, Integer> {
    @Modifying
    @Transactional
    @Query("DELETE FROM UserReviews u WHERE u.reviews_id = :reviewsId")
    void deleteUserReviewsByReviewsId(@Param("reviewsId") Integer reviewsId);
}
