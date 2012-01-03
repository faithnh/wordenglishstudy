package faithnh.study.wordenglishstudy.util;

import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.Before;
import org.junit.Test;

public class SequenceGeneratorTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void createRandomSequenceTest01() {
		int n = 10;
		ArrayList<Integer> result;
		result = SequenceGenerator.createRandomSequence(n);
		assertEquals(n, result.size());
	}

}
