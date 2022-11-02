import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

@TestMethodOrder(OrderAnnotation.class)
class ExpressionEvaluatorTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	@Order(1)
	void test_BasicFunction() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		int testNum = 0;
		
		System.out.println("Basic Function Tests");
		
		// test #1
		testNum++;
		evalResults = exprEval.evaluateExpression("3+6*9-4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("53.0"));
		
		// test #2 - with arbitrary spacing
		testNum++;
		evalResults = exprEval.evaluateExpression("3+ 6   * 9  -4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("53.0"));
		
		// test #3
		testNum++;
		evalResults = exprEval.evaluateExpression("3*9*7-4");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("185.0"));
		
		// test #4
		testNum++;
		evalResults = exprEval.evaluateExpression("3*9*(7-4)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("81.0"));
		
		// test #5
		testNum++;
		evalResults = exprEval.evaluateExpression("3*(9*7-4)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("177.0"));
		
		// test #6
		testNum++;
		evalResults = exprEval.evaluateExpression("((6-7)*9+15/3)/4+((5*2)+7)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("16.0"));
	
		// test #7
		testNum++;
		evalResults = exprEval.evaluateExpression("1+30-5*6-1");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("0.0"));

		testNum++;
		evalResults = exprEval.evaluateExpression("(9)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("9.0"));

	}
	
	@Test
	@Order(2)
	void test_DoubleTest() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		int testNum =0;
		Double drslt;
		
		System.out.println("Double Tests");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("1.0 + 30.0 -(5.0*6.0)-   1.0");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("0.0"));

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("1.0 + 30 -(5   *6.0)-   1.0");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertTrue(results[1].equals("0.0"));

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("15/4.5");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(3.33333,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("15*3.14159/2");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(23.5619,drslt,0.00005);  
	}


	@Test
	@Order(3)
	void test_ParenErrorTest() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;;
		int testNum =0;
		boolean errMatch;
		
		System.out.println("Paren Error Tests");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(");
		errMatch = evalResults.contains("Paren Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression(")");
		errMatch = evalResults.contains("Paren Error");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(6+ (9*15)");
		errMatch = evalResults.contains("Paren Error");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);

	
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("6+ (9*15))");
		errMatch = evalResults.contains("Paren Error");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("()");
		errMatch = evalResults.contains("Paren Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(         )");
		errMatch = evalResults.contains("Paren Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		

	}

	@Test
	@Order(4)
	void test_DataErrorTest() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;;
		int testNum =0;
		boolean errMatch;
		
		System.out.println("Data Error Tests");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("# + 7");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9 + 7f");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9 * m445");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9(m445)");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9(4#45)");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9 445 + 5");
		errMatch = evalResults.contains("Data Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
				
	}
	
	@Test
	@Order(5)
	void test_OpErrorTest() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;;
		int testNum =0;
		boolean errMatch;
		
		System.out.println("Op Error Tests");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("+ 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("* 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("/ 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("* 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 *");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 /");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 +");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 + + 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 + * 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 - - - 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("19 - - + 8");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(9*)*(7*)*(3*)");
		errMatch = evalResults.contains("Op Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);

	}
	
	@Test
	@Order(6)
	void test_ImplicitMult() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		int testNum =0;
		Double drslt;
		
		System.out.println("Implicit Multiplication");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(9)(7)(3)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(189,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(9)7+3(3)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(72,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("(9+7)3");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(48,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9(2-7)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(-45,drslt,0.00005);  

	}

	@Test
	@Order(7)
	void test_NegativeNumbers() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		int testNum =0;
		Double drslt;
		
		System.out.println("Negative Numbers");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-5");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(-5.0, drslt, 0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("16.0+-5");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(11.0, drslt, 0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("16.0--5");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(21.0, drslt, 0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("16.0*-5");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(-80.0, drslt, 0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("16.0/-5");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(-3.2, drslt, 0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("16.0+(-5)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(11.000, drslt,0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-5(90)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(-450.0, drslt,0.00005);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-5(-90)");
		results = evalResults.split("=");
		results[1] = results[1].trim();
		drslt = Double.parseDouble(results[1]);
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+results[1]+"\n\n");
		assertEquals(450.0, drslt,0.00005);

	}

	@Test
	@Order(8)
	void test_Div0Test() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;;
		int testNum =0;
		boolean errMatch;
		
		System.out.println("Div0 Tests");

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9/0");
		errMatch = evalResults.contains("Div0 Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9/(1+30-5*6-1)");
		errMatch = evalResults.contains("Div0 Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("9/(1+30-5*6-1)+16/4");
		errMatch = evalResults.contains("Div0 Error:");
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+errMatch+"\n\n");
		assertTrue(errMatch);
		
	}
	@Test
//	@Disabled
	@Order(9)
	void test_ImpMultNeg1Test() {
		ExpressionEvaluator exprEval = new ExpressionEvaluator();
		String evalResults;
		String[] results;
		int testNum =0;
		boolean errMatch;
		double drslt;
		
		System.out.println("Implicit Multiplication combined with negation");


		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("5+-(1-6)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(10,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("5--(1-6)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(0,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("5*-(1-6)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(25,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("5/-(1-6)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(1,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("5/-(1-6)+(100)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(101,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-(1-6)+(100)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(105,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-(1+-(10-2)+2)+(100)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(105,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-(1+(-(10-2)+2)+100)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(-95,drslt,0.00005);  

		// test #
		testNum++;
		evalResults = exprEval.evaluateExpression("-(1+(-(10+-(2*-5))+2)+100)");
		results = evalResults.split("=");
		drslt = Double.parseDouble(results[1].trim());
		System.out.println("Test # "+testNum+"\nExpression Results: "+evalResults+"\nResult: "+drslt+"\n\n");
		assertEquals(-83,drslt,0.00005);  
	}
}
