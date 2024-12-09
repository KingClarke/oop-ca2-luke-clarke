import java.util.Arrays;
import java.util.List;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Name: Luke Clarke
 *  Class Group: SD2A
 */
public class Question9
{

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter an expression (Infix or Postfix): ");
        String expression = scanner.nextLine();

        try {
            System.out.print("Is this expression in Postfix? (yes/no): ");
            String postfixInput = scanner.nextLine();

            double result;
            if (postfixInput.equalsIgnoreCase("yes")) {
                // Postfix mode
                List<String> tokens = Arrays.asList(expression.split("\\s+"));
                result = evaluatePostfix(tokens);
            } else {
                // Infix mode
                expression = handleImplicitMultiplication(expression);
                result = evaluateInfix(expression);
            }

            System.out.println("Result: " + result);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    // Evaluate Infix Expression (Two Stack Method)
    public static double evaluateInfix(String expression) throws Exception {
        Stack<Double> numberStack = new Stack<>();
        Stack<Character> symbolStack = new Stack<>();
        char[] tokens = expression.toCharArray();

        int i = 0;
        while (i < tokens.length) {
            char c = tokens[i];

            if (Character.isWhitespace(c)) {
                i++;
                continue;
            }

            if (Character.isDigit(c) || c == '.') {
                // Parse full number
                StringBuilder number = new StringBuilder();
                while (i < tokens.length && (Character.isDigit(tokens[i]) || tokens[i] == '.')) {
                    number.append(tokens[i++]);
                }
                numberStack.push(Double.parseDouble(number.toString()));
                continue;
            }

            if (c == '(') {
                symbolStack.push(c);
            } else if (c == ')') {
                while (!symbolStack.isEmpty() && symbolStack.peek() != '(') {
                    evaluateTop(numberStack, symbolStack);
                }
                if (symbolStack.isEmpty() || symbolStack.pop() != '(') {
                    throw new Exception("Mismatched parentheses");
                }
            } else if (isOperator(c)) {
                while (!symbolStack.isEmpty() && precedence(symbolStack.peek()) >= precedence(c)) {
                    evaluateTop(numberStack, symbolStack);
                }
                symbolStack.push(c);
            } else {
                throw new Exception("Invalid character in expression: " + c);
            }
            i++;
        }

        while (!symbolStack.isEmpty()) {
            evaluateTop(numberStack, symbolStack);
        }

        if (numberStack.size() != 1) {
            throw new Exception("Invalid expression");
        }

        return numberStack.pop();
    }

    // Evaluate Postfix Expression (Single Stack Method)
    public static double evaluatePostfix(List<String> tokens) throws Exception {
        Stack<Double> numberStack = new Stack<>();

        for (String token : tokens) {
            if (isNumber(token)) {
                numberStack.push(Double.parseDouble(token));
            } else if (isOperator(token.charAt(0))) {
                if (numberStack.size() < 2) throw new Exception("Invalid Postfix expression");
                double b = numberStack.pop();
                double a = numberStack.pop();
                numberStack.push(applyOperator(a, b, token.charAt(0)));
            } else {
                throw new Exception("Invalid token in Postfix: " + token);
            }
        }

        if (numberStack.size() != 1) {
            throw new Exception("Invalid Postfix expression");
        }

        return numberStack.pop();
    }

    // Handle Implicit Multiplication in Infix
    private static String handleImplicitMultiplication(String expression) {
        StringBuilder modifiedExpression = new StringBuilder();
        char[] tokens = expression.toCharArray();

        for (int i = 0; i < tokens.length; i++) {
            char current = tokens[i];
            modifiedExpression.append(current);

            if (i + 1 < tokens.length) {
                char next = tokens[i + 1];
                if ((Character.isDigit(current) && next == '(') || (current == ')' && (next == '(' || Character.isDigit(next)))) {
                    modifiedExpression.append('*');
                }
            }
        }

        return modifiedExpression.toString();
    }

    private static void evaluateTop(Stack<Double> numberStack, Stack<Character> symbolStack) throws Exception {
        if (numberStack.size() < 2) {
            throw new Exception("Not enough numbers for operation");
        }
        double b = numberStack.pop();
        double a = numberStack.pop();
        char operator = symbolStack.pop();

        numberStack.push(applyOperator(a, b, operator));
    }

    private static double applyOperator(double a, double b, char operator) throws Exception {
        return switch (operator) {
            case '+' -> a + b;
            case '-' -> a - b;
            case '*' -> a * b;
            case '/' -> {
                if (b == 0) throw new Exception("Division by zero");
                yield a / b;
            }
            default -> throw new Exception("Unknown operator");
        };
    }

    private static boolean isOperator(char c) {
        return c == '+' || c == '-' || c == '*' || c == '/';
    }

    private static boolean isNumber(String token) {
        try {
            Double.parseDouble(token);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    private static int precedence(char c) {
        return (c == '+' || c == '-') ? 1 : (c == '*' || c == '/') ? 2 : -1;
    }
}