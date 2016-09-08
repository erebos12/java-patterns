package host;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

import junit.framework.TestCase;

public class ParallelStream extends TestCase {

	private static class LessThan10 implements Predicate {
		@Override
		public boolean test(Object n) {
			System.out.println("Check if: " + n.toString() + " is less than 10...");
			return (int) n < 10;
		}
	}

	public static void testIt() {
		List<Integer> numbers = new ArrayList<Integer>();
		;
		numbers.add(1);
		numbers.add(10);
		numbers.add(11);
		numbers.add(4);
		numbers.add(33);
		numbers.add(7);
		numbers.add(100);
		System.out.println("1. Way: Count < 10: " + numbers.stream().filter(new LessThan10()).count());

		System.out.println("2. Way: Count < 10: " + numbers.stream().filter(n -> n < 10).count());
	}

}
