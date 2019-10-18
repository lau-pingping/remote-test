package test;

public class SolveMath {
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

		// resolve formula in "( )" as priority and form a new formula without ( )
		if(sum.indexOf("(") >= 0){
			do{
				String priorityFormula = sum.substring(sum.indexOf("("), sum.indexOf(")")+1);
				
				result = solveFormula(priorityFormula);
				
				sum = sum.replace(priorityFormula, String.valueOf(result));
				
			} while(sum.indexOf("(") >= 0);
			result = solveFormula(sum);
		}else{
			result = solveFormula(sum);
		}
		
		return result;
	}
	
	static double solveFormula(String formula){
		formula = formula.replace("(", "");
		formula = formula.replace(")", "");
		formula = formula.trim();
		
		String[] splitString = formula.split(" ");
		int length = splitString.length;
		double result = 0;
		int currentPos = 0;
		
		result = Double.parseDouble(splitString[currentPos]);
		
		do {
			result = resolve(result, Double.parseDouble(splitString[currentPos+2]), splitString[currentPos+1]);
			currentPos = currentPos +2;
		} while(currentPos < length-1);

		return result;
	}
	
	static double resolve(double operand1, double operand2, String operator) {
		double result = 0.0;
		result = getOperator(operators.indexOf(operator)).solve(operand1, operand2);
		return result;
	}
	

	public static void main(String[] args){
		String formula = "1 + 1";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "2 * 2";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "1 + 2 + 3";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "0 / 0";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "11 + 23";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "11.1 + 23";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "( 11.5 + 15.4 ) + 10.1";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		formula = "23 - ( 29.3 - 12.5 )";
		System.out.println(formula+" = "+SolveMath.calculate(formula));
		
	}
}
