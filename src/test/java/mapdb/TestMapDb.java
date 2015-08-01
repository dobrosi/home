package mapdb;

import org.junit.Test;

import com.programgyar.home.domain.User;
import com.programgyar.memdb.Database;

public class TestMapDb {
	@Test
	public void test() {
		User d = (User) Database.load(User.class, new User());
		System.out.println(d);

		d.age = 19;
		d.name = "Test";

		System.out.println(d);

		Database.commit();
	}
}