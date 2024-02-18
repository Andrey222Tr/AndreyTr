import java.util.Map;
import java.util.TreeMap;
import java.util.Scanner;

       public class Calculator {
      public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        startCalc();

        while (true) {

            System.out.println("Введите выражение в формате: \"1 + 1 или I + I\" (через пробел) ");
            String line = sc.nextLine();


            try {
                String[] symbols = line.split(" ");
                if (symbols.length != 3)
                    throw new Exception("Формат вводенных данных не удовлетворяет условиям работы Калькулятора");

                Number firstNumber = SelectService.parseAndValidate(symbols[0]);
                Number secondNumber = SelectService.parseAndValidate(symbols[2], firstNumber.getType());
                String result = Select.calculate(firstNumber, secondNumber, symbols[1]);
                System.out.println("Output: \n" + result);

            } catch (Exception e) {
                System.out.println(e.getMessage());
                exitCalc();
                break;
            }
        }
        sc.close();
    }
        private static void startCalc() {
            System.out.println("Добро пожаловать в Калькулятор, он работает только с целыми арабскими и римскими цифрами от 1 до 10");
            System.out.println("Калькулятор выполняет только следующие операции:");
            System.out.println("Сложение(+), Вычитание(-), Умножение(*), Деление(/)");
        }

        private static void exitCalc() {

            System.out.println("Выход");

        }
    }
        class  Number {

     private int value;
       private NumberType type;

        Number(int value, NumberType type) {
        this.value = value;
        this.type = type;
        }

        int getValue() {
        return value;
        }

        NumberType getType() {
        return type;
        }
        }

class SelectService {

    private final static TreeMap < Integer, String > romanString = new TreeMap<>();

    static {
        romanString.put(1, "I");
        romanString.put(4, "IV");
        romanString.put(5, "V");
        romanString.put(9, "IX");
        romanString.put(10, "X");
        romanString.put(40, "XL");
        romanString.put(50, "L");
        romanString.put(90, "XC");
        romanString.put(100, "C");
    }

    static Number parseAndValidate(String symbol) throws Exception {

        int value;
        NumberType type;

        try {
            value = Integer.parseInt(symbol);
            type = NumberType.ARABIC;
        }catch (NumberFormatException e) {
            value = toArabicNumber(symbol);
            type = NumberType.ROMAN;
        }

        if (value < 1 || value > 10) {
            throw new Exception("Неподходящее значение числа(ел), используйте числа от 1 до 10 включительно");
        }

        return new Number(value, type);
    }

    static Number parseAndValidate(String symbol, NumberType type) throws Exception {

        Number number = parseAndValidate(symbol);
        if (number.getType() != type) {
            throw new Exception("Используются одновременно разные системы счисления");
        }

        return number;
    }

    private static int letterToNumber(char letter) {

        int result = -1;

        for (Map.Entry < Integer, String > entry: romanString.entrySet()) {
            if (entry.getValue().equals(String.valueOf(letter))) result = entry.getKey();
        }
        return result;
    }

    static String toRomanNumber(Integer number) throws Exception {

        try {
            int i = romanString.floorKey(number);

            if (number == i) {
                return romanString.get(number);
            }
            return romanString.get(i) + toRomanNumber(number - i);
        }catch (NullPointerException e) {
            throw new Exception("В римской системе счисления нет нуля и отрицательных чисел.");
        }
    }

    static int toArabicNumber(String roman) throws Exception {
        int result = 0;
        int i = 0;

        while (i < roman.length()) {
            char letter = roman.charAt(i);
            int num = letterToNumber(letter);

            if (num <= 0) throw new Exception("Неверный римский символ");

            i++;
            if (i == roman.length()) {
                result += num;
            }else {
                int nextNum = letterToNumber(roman.charAt(i));
                if(nextNum > num) {
                    result += (nextNum - num);
                    i++;
                }
                else result += num;
            }
        }
        return result;
    }
}
