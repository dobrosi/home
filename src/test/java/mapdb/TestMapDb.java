package mapdb;

import org.junit.Test;

public class TestMapDb {
	@Test
	public void test() {
		Database.open();
		Runtime.getRuntime().addShutdownHook(new Thread() {
			@Override
			public void run() {
				System.out.println("shutdown");
			}
		});

		Data d = (Data) Database.load("user", new Data());
		System.out.println(d);

		d.age = 19;
		d.name = "Test";

		System.out.println(d);

		Database.commit();


	}
}