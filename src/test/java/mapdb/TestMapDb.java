package mapdb;

import org.junit.Test;

public class TestMapDb {
	@Test
	public void test() {
		Database.open();

		Data d = (Data) Database.load("user");
		System.out.println(d);

		if (d == null) {
			d = new Data();
			Database.save("user", d);
		}
		d.age = 19;
		d.name = "Test";

		System.out.println(d);

		Database.commit();
	}
}