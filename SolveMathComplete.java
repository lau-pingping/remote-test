package test;

import java.util.LinkedList;

public class SolveMathComplete {
	final static String operators = "+-*/";
	
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

        public String getOperatorCode(){
                return operatorCode;
        }
        
        public abstract double solve(double operator1, double operator2);
    }
	
	
	/**
	 * Get correct Operator from MathOperator by using operatorCode
	 * @param operatorCode
	 * @return
	 */
	public static MathOperator getOperator(int operatorCode){
        switch (operatorCode){
            case 0:
                return MathOperator.Add;
            case 1:
                return MathOperator.Minus;
            case 2:
                return MathOperator.Multiply;
            case 3:
                return MathOperator.Divide;
            default:
                return MathOperator.Add;
        }
    }
	
	
	public static double calculate(String sum){
		double result = 0.0;

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
		
		return result;
	}
	
	static double solveFormula(String formula){
		
//		System.out.println("in solveFormula, formula --> "+formula);
		formula = formula.replace("(", "");
		formula = formula.replace(")", "");
		formula = formula.trim();
		
		String[] splitString = formula.split(" ");
		int length = splitString.length;
		double result = 0;
		int currentPos = 0;
		
		result = Double.parseDouble(splitString[currentPos]);
		
		LinkedList<String> list = new LinkedList<String>();
		for(String content : splitString){
			list.add(content);
		}
		
		// resolve * and / operator
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
		
		// resolve + and - operator
		currentPos = 0;
		length = list.size();
		result = Double.parseDouble(list.get(currentPos));
		if(length > 1){
			do {
				result = resolve(result, Double.parseDouble(list.get(currentPos+2)), list.get(currentPos+1));
				currentPos = currentPos +2;
			} while(currentPos < length-1);
		}
		return result;
	}
	
	static double resolve(double operand1, double operand2, String operator) {
		double result = 0.0;
		result = getOperator(operators.indexOf(operator)).solve(operand1, operand2);
		return result;
	}
	

	public static void main(String[] args){
		String formula = "1 + 1";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "2 * 2";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "1 + 2 + 3";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "6 / 2";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "11 + 23";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "11.1 + 23";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "( 11.5 + 15.4 ) + 10.1";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "23 - ( 29.3 - 12.5 )";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "1 + 2 * 3 - 4 / 5"; 
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		formula = "1 / ( 2 + 3 * ( 4 - 5 ) )";
		System.out.println(formula+" = "+SolveMathComplete.calculate(formula));
		
//		
	}
}
