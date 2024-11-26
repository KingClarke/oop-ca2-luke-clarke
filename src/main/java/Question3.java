import java.io.FileNotFoundException;
import java.util.Scanner;
import java.util.Stack;

/**
 *  Name: Luke Clarke
 *  Class Group: SD2A
 */
public class Question3  {   //Nested HTML (Stack)

    /*
filename: name of the file to test.
*/

    public static boolean validate(String filename) throws FileNotFoundException {
        Scanner scanner = new Scanner(filename);
        Stack<String> stack = new Stack<>();
        boolean isValid = true;

        while (scanner.hasNext()) {
            String tag = scanner.next();

            // Check if it's an opening tag
            if (isOpeningTag(tag)) {
                stack.push(tag);
            }
            // Check if it's a closing tag
            else if (isClosingTag(tag)) {
                if (stack.isEmpty() || !matches(stack.pop(), tag)) {
                    isValid = false;
                    System.out.println("Error: Mismatched or missing tag for " + tag);
                    break;
                }
            }
            // Ignore self-closing tags like <br>
            else if (isSelfClosingTag(tag)) {
                continue;
            } else {
                System.out.println("Error: Invalid tag " + tag);
                isValid = false;
                break;
            }
        }

        // Check for unclosed tags
        if (!stack.isEmpty()) {
            isValid = false;
            System.out.println("Error: Unmatched tags remain: " + stack);
        }

        if (isValid) {
            return true;
        } else {
            return false;
        }
    }


    /*
        This function tests the files in the files array to see if
         they are valid.
         tags_valid.txt should return true;
         tags_invalid.txt should output as invalid;


     */

    private static boolean isOpeningTag(String tag) {
        return tag.matches("<[a-zA-Z]+>");
    }

    private static boolean isClosingTag(String tag) {
        return tag.matches("</[a-zA-Z]+>");
    }

    private static boolean isSelfClosingTag(String tag) {
        return tag.matches("<br>");
    }

    private static boolean matches(String openingTag, String closingTag) {
        return closingTag.equals("</" + openingTag.substring(1));
    }

    public static void main(String[] args) throws FileNotFoundException {
        String[] files = {"tags_valid.txt", "tags_invalid.txt"};
        for(String fName: files) {
            System.out.print(fName +": ");
            if (validate(fName)) {
                System.out.println("This file is valid");
            } else {
                System.out.println("This file is invalid");
            }
        }
    }



}


