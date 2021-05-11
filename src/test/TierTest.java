package test;

import beans.Tier;
import beans.User;
import builders.TierBuilder;

public class TierTest {

	public static void main(String[] args) {
		User user = new User("password", "phil@email.com");
		Tier tier = new TierBuilder()
				.setName("Tier 1")
				.setOwner(user)
				.createTier();
		
		User member1 = new User("password", "joel@email.com");
		User member2 = new User("password", "milk@heaven.com");
		
		tier.addMember(member1);
		tier.addMember(member2);
		
		System.out.println(tier);
	}

}
