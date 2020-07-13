package com.poc.tekton.app.repository;

import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBQueryExpression;
import com.poc.tekton.app.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class Users {

    private DynamoDBMapper dynamoDBMapper;

    @Autowired
    public Users(DynamoDBMapper dynamoDBMapper) {
        this.dynamoDBMapper = dynamoDBMapper;

    }

    public void save(User user) {
        this.dynamoDBMapper.save(user);
    }

    public Optional<User> get(String id) {
        DynamoDBQueryExpression<User> userDynamoDBQueryExpression = new DynamoDBQueryExpression<User>()
                .withHashKeyValues(User.builder().id(id).build());

        return this.dynamoDBMapper.query(User.class, userDynamoDBQueryExpression).stream().findFirst();
    }

}
