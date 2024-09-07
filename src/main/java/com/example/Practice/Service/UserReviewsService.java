package com.example.Practice.Service;

import com.example.Practice.Repository.UserReviewsRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@Transactional
public class UserReviewsService {
    @Autowired
    private UserReviewsRepository userReviewsRepository;

    public void remove(Integer id) {
        userReviewsRepository.deleteUserReviewsByReviewsId(id);
    }
}
