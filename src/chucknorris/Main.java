package chucknorris;
import java.util.Scanner;

public class Main {

    public static String prepareBinaryString(char inputChar){
        String sResult = Integer.toBinaryString(inputChar);

        if (sResult.length() < 7) {
            sResult = "0".repeat(7-sResult.length()) + sResult;
        }
        return sResult;
    }

    private static String makeToken(String inpStr, char currentChar, int myCount) {
        if (currentChar == '1') {
            inpStr += "0 " + "0".repeat(myCount);
        } else {
            inpStr += "00 " + "0".repeat(myCount);
        }
        return inpStr + ' ';
    }

    public static String chuckEncodeBinary(String inputBinStr){
        String sResult = "";
        char currentChar = inputBinStr.charAt(0);
        int  myCount = 1;

        for (int i = 1; i < inputBinStr.length() ; i++) {
            if (currentChar != inputBinStr.charAt(i)){
                sResult = makeToken(sResult, currentChar, myCount);
                myCount = 1;
                currentChar = inputBinStr.charAt(i);
            } else {
                myCount++;
            }

            if (i == inputBinStr.length() - 1) {
                sResult = makeToken(sResult, currentChar, myCount);
            }
        }
        return sResult;
    }

    public static String chuckEncodeString(String inputStr){
        String sResult = "";

        for(int i=0; i < inputStr.length(); i++ ) {
            sResult += prepareBinaryString(inputStr.charAt(i));
        }

        if (sResult.length() > 0 && sResult.charAt(sResult.length()-1) == ' ') {
            sResult.substring(0, sResult.length() - 1);
        }
        return chuckEncodeBinary(sResult);
    }

    public static String chuckDecodeBlock(String s01, String sCount ) throws Exception {
        String sResult = "";

        if (s01.equals("0")) {
            sResult = "1".repeat(sCount.length());
        } else if (s01.equals("00")){
            sResult = "0".repeat(sCount.length());
        } else {
            throw new Exception("Invalid chuck string");
        }
        return sResult;
    }

    public static String chuckDecodeString(String inputChuckStr) throws Exception {
        String sResult = "";
        String[] strArr = inputChuckStr.split(" ");
        int i = 0;

        if (strArr.length % 2 != 0) {
            throw  new Exception("Number of blocks is odd!");
        }

        while (i < strArr.length) {
            sResult = sResult + chuckDecodeBlock(strArr[i], strArr[i+1]);
            i+=2;
        }

        if (sResult.length() % 7 != 0 ) {
            throw new Exception("Invalid encoded blocks length!");
        }
        strArr = sResult.split("(?<=\\G.{7})");
        sResult = "";

        for (String strToken: strArr)
        {
            sResult+= (char)(int)Integer.parseInt(strToken,2);
        }
        return sResult;
    }

    static boolean ValidateStringToDecode(String inStr){
        if (inStr.isEmpty()) {
            return false;
        }

        for (int i = 0; i < inStr.length(); i++) {
            if (inStr.charAt(i) != ' '  && inStr.charAt(i) != '0') {
                return false;
            }
        }

        return true;
    }

     public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        boolean isExit = false;
        String userString = "";

        while (!isExit) {

            System.out.println("Please input operation (encode/decode/exit):");
            userString = sc.nextLine();

            switch (userString){
                case "encode":
                    System.out.println("Input string:");
                    userString = sc.nextLine();
                    System.out.println("Encoded string:");
                    System.out.println(chuckEncodeString(userString));
                    break;
                case "decode":
                    System.out.println("Input encoded string:");
                    userString = sc.nextLine();
                    String decodedString;
                    try {
                      if (!ValidateStringToDecode(userString)) {
                          throw new Exception("Invalid chuck string");
                      }
                      decodedString = chuckDecodeString(userString);

                      System.out.println("Decoded string:");
                      System.out.println(decodedString);
                    } catch (Exception e) {
                        System.out.println("Encoded string is not valid.");
                    }
                    break;
                case "exit":
                    System.out.println("Bye!");
                    isExit = true;
                    break;
                default: {
                    System.out.println("There is no '" + userString + "' operation");
                }
            }
        }
    }
}