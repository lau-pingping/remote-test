package test;

import java.util.ArrayList;
import java.util.Arrays;
//import java.util.LinkedList;
import java.util.List;

public class SolveMathStrengthenV2 {
//	final static String operators = "+-*/";
	static boolean validFormula = true;
	
	/**
	 * MathOperator implement mathematical operation according to operator code
	 */
	public static enum MathOperator{
		Add("+"){
			public double solve(double operator1, double operator2){
				return operator1+operator2;
			}
		},
		Minus("-"){
			public double solve(double operator1, double operator2){
				return operator1-operator2;
			}
		},
		Multiply("*"){
			public double solve(double operator1, double operator2){
				return operator1*operator2;
			}
		},
		Divide("/"){
			public double solve(double operator1, double operator2){
				return operator1/operator2;
			}
		};
		        
        private final String operatorCode;

        MathOperator(String operatorCode) {
        	this.operatorCode = operatorCode;
        }
        
        public abstract double solve(double operator1, double operator2);
        
        /**
    	 * Get correct Operator from MathOperator by using operatorCode
    	 * @param operatorCode
    	 * @return
    	 */
    	public static MathOperator getOperator(String operatorCode){
            switch (operatorCode){
                case "+":
                    return MathOperator.Add;
                case "-":
                    return MathOperator.Minus;
                case "*":
                    return MathOperator.Multiply;
                case "/":
                    return MathOperator.Divide;
                default:
                    return MathOperator.Add;
            }
        }
    	
    }
	
	public static double calculate(String sum) {
		String originalFormula = sum;
		double result = 0.0;
		validFormula = true;
		try{
			if (sum == null || sum.length()<1) {
		        throw new IllegalArgumentException("Invalid formula: "+sum);
			}
		
			int bracketIndex = -1;
			bracketIndex = sum.indexOf("(");
			String priorityFormula = null;
			
			// resolve parentheses
			if(bracketIndex >= 0){
				do{
					priorityFormula = sum.substring(bracketIndex, sum.indexOf(")")+1);
					if(priorityFormula.indexOf("(", bracketIndex) > 0){
						bracketIndex = sum.indexOf("(", bracketIndex+1);
					}else{
						bracketIndex = sum.indexOf("(");
						result = solveFormula(priorityFormula);
						sum = sum.replace(priorityFormula, String.valueOf(result));
					}
				} while(sum.indexOf("(") >= 0);
				result = solveFormula(sum);
			}else{
				result = solveFormula(sum);
			}
		}
		catch(Exception e){
			validFormula = false;
//			e.printStackTrace();
		}finally{
			if(!validFormula){
				System.out.println("Unable to process formula: "+originalFormula);
			}
		}
		
		return result;
	}
	
	/**
	 * Solve the formula according to priority of operator
	 * @param formula
	 * @return
	 */
	static double solveFormula(String formula) {
		double result = 0;
		int currentPos = 0;
		String[] splitString = null;
		
		try{
			formula = formula.replace("(", "");
			formula = formula.replace(")", "");
			formula = formula.trim();
			
			splitString = formula.split(" ");
	
			result = Double.parseDouble(splitString[currentPos]);
			
//			List<String> list = new LinkedList<String>(Arrays.asList(splitString));
			List<String> list = new ArrayList<String>(Arrays.asList(splitString));
			
			result = solveHighPriorityOperator(list);
			result = solveLowPriorityOperator(list);

		}catch(NumberFormatException e){
			validFormula = false;
			System.out.println("Invalid operand: "+splitString[currentPos]);
		}
		return result;
	}
	
	/**
	 * Resolve the high priority Operator: *, /
	 * @param list
	 * @return 
	 */
	public static double solveHighPriorityOperator(List<String> list){
		int currentPos = 0;
		int length = list.size();
		double result = 0.00;
		try{
			do {
				if(list.indexOf("*") > 0 || list.indexOf("/") > 0){
					if(list.get(currentPos).equals("*") || list.get(currentPos).equals("/")){
						result = resolve(Double.parseDouble(list.get(currentPos-1)), Double.parseDouble(list.get(currentPos+1)), list.get(currentPos));
						list.remove(currentPos+1);
						list.remove(currentPos);
						list.remove(currentPos-1);
						list.add(currentPos-1, String.valueOf(result));
					}
				}
				currentPos ++;
			} while(currentPos < length-1);
		}catch(IndexOutOfBoundsException e){
			validFormula = false;
			System.out.println("Missing operand");
		}
		
		return result;
	}
	
	/**
	 * Resolve the low priority Operator: *, /
	 * @param list
	 * @return 
	 */
	public static double solveLowPriorityOperator(List<String> list){
		int currentPos = 0;
		double result= 0.00;
		
		int length = list.size();
		try{
			result = Double.parseDouble(list.get(currentPos));
			if(length > 1){
				do {
					result = resolve(result, Double.parseDouble(list.get(currentPos+2)), list.get(currentPos+1));
					currentPos = currentPos +2;
				} while(currentPos < length-1);
			}
		}catch(IndexOutOfBoundsException e){
			validFormula = false;
			System.out.println("Missing operand");
		}
		return result;
	}
	
	static double resolve(double operand1, double operand2, String operator) {
		double result = 0.0;
//		result = getOperator(operators.indexOf(operator)).solve(operand1, operand2);
		result = MathOperator.getOperator(operator).solve(operand1, operand2);
		return result;
	}
	

	public static void main(String[] args){
		try{
			String formula = "1 + 1";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "2 * 2";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "1 + 2 + 3";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "6 / 2";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "11 + 23";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "11.1 + 23";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			
			formula = "( 11.5 + 15.4 ) + 10.1";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "23 - ( 29.3 - 12.5 )";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "1 + 2 * 3 - 4 / 5"; 
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "1 / ( 2 + 3 * ( 4 - 5 ) )";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			
			formula = " 1";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			
			System.out.println("\n\n>>>>> Test Invalid formula <<<<<<");
			formula = null;
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "adffs";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "1 / ( 2 + 3 * ( 4 - 5  )";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			formula = "1 + 2 * 3 - 4 /";
			System.out.println(formula+" = "+SolveMathStrengthenV2.calculate(formula));
			
		}catch(Exception e){
			e.printStackTrace();
		}
		
//		
	}
}
