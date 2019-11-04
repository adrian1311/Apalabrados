package edu.uclm.esi.apalabreitor.apalabreitor.dao;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import edu.uclm.esi.apalabreitor.apalabreitor.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, String>{

	User findByuserName (String userName);
	User findByEmail(String email);
	User findBypwd (String pwd);
}
