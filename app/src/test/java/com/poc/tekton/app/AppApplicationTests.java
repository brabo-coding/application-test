package com.poc.tekton.app;

import com.amazonaws.services.dynamodbv2.AmazonDynamoDB;
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper;
import com.amazonaws.services.dynamodbv2.document.DynamoDB;
import com.amazonaws.services.dynamodbv2.model.CreateTableRequest;
import com.amazonaws.services.dynamodbv2.model.ProvisionedThroughput;
import com.poc.tekton.app.model.User;
import com.poc.tekton.app.repository.Users;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.equalTo;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class AppApplicationTests {

    @Autowired
    private Users users;

	@Autowired
	private AmazonDynamoDB amazonDynamoDB;

	@Autowired
	private DynamoDBMapper dynamoDBMapper;

    @Test
    void contextLoads() throws InterruptedException {
    	Thread.sleep(2000);
		DynamoDB dynamoDB = new DynamoDB(amazonDynamoDB);
		deleteTable("users", dynamoDB);

		ProvisionedThroughput ptIndex = new ProvisionedThroughput().withReadCapacityUnits(10L)
				.withWriteCapacityUnits(1L);

		CreateTableRequest tableRequest = dynamoDBMapper
				.generateCreateTableRequest(User.class)
				.withTableName("users")
				.withProvisionedThroughput(ptIndex);
		dynamoDB.createTable(tableRequest);

		users.save(User.builder().id("1").name("Test").build());

        User user = users.get("1").get();

        assertThat(user.getId(), equalTo("1"));
        assertThat(user.getName(), equalTo("Test"));

    }

	public boolean deleteTable(String tableName, DynamoDB dynamoDB) {
		try {
			dynamoDB.getTable(tableName).describe();
			dynamoDB.getTable(tableName).delete();
			return true;
		} catch (com.amazonaws.services.dynamodbv2.model.ResourceNotFoundException rnfe) {
			System.out.println("Table does not exist");
		}
		return false;
	}

}
