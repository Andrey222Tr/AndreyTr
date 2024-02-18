  class Select {

    public static String calculate(Number first, Number second, String select) throws Exception {

        int result;

        switch (select) {
            case "+":
                result = first.getValue() + second.getValue();
                break;
            case "-":
                result = first.getValue() - second.getValue();
                break;
            case "*":
                result = first.getValue() * second.getValue();
                break;
            case "/":
                result = first.getValue() / second.getValue();
                break;
            default:
                throw new Exception("Не правильно введен символ операции, используйте только +, -, *, /");
        }

        if (first.getType() == NumberType.ROMAN) {
            return SelectService.toRomanNumber(result);
        } else return String.valueOf(result);
    }
}
