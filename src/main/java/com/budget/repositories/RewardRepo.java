package com.budget.repositories;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.budget.models.Reward;

@Repository
public interface RewardRepo extends CrudRepository<Reward, String> {

}
