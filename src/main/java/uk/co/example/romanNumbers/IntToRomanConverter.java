package uk.co.example.romanNumbers;

import java.io.*;
import java.util.*;
import org.json.simple.*;
import org.json.simple.parser.*;
import java.io.IOException;
import org.json.simple.parser.ParseException;

import java.lang.Math;


public class IntToRomanConverter {
    private final long BASE = 10;
    private static final String ARGUMENT_ERROR_MESSAGE = "Expected as an input parameter of a valid positive integer greater than 0, and less than ";

    /**
     * An entry point that takes a decimal string and prints out the roman number to stdout
     * @param args expects amd array of at least one string value. That value should be a representation of an integer.
     */
    public static void main(String[] args) {
        IntToRomanConverter converter = IntToRomanConverter.getInstance();
        long limit = converter.getTopLimit();
        try {
            final long value = (args.length > 0) ? Long.parseLong(args[0]) : 0;
            if(!converter.validate(value)){
                System.out.println(ARGUMENT_ERROR_MESSAGE + limit);
                return;
            }
            System.out.println(converter.convert(value));
        } catch (Exception e){
            System.out.println(ARGUMENT_ERROR_MESSAGE + limit);
        }
    }
    private ArrayList<RomanPowerOfTen> romanPowers;
    private long limit;

    /**
     * Returns the instance of IntToRomanConverter
     * Making this class follow the singleton pattern.
     * This is to reduce the amount of times the configuration files have to be read.
     * Outside the constructor and this synchronised function none of the class member values are ever changed.
     * @return IntToRomanConverter
     */
    public synchronized static IntToRomanConverter getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new IntToRomanConverter();
        }
        return INSTANCE;
    }
    private static IntToRomanConverter INSTANCE;

    private IntToRomanConverter(){
        romanPowers = new ArrayList<>();
        // Read definitions of Roman 'digit character strings' from a json configuration file
        JSONParser parser = new JSONParser();
        try {
            Object obj = parser.parse(new FileReader("src/main/resources/definitions/romanNumbers.json"));
            JSONObject jsonObject = (JSONObject)obj;
            JSONArray romanNumbers = (JSONArray) jsonObject.get("romanNumbers");
            Iterator romanNumbersIterator = romanNumbers.iterator();
            while (romanNumbersIterator.hasNext()) {
                Object ob = romanNumbersIterator.next();
                JSONObject romanObject = (JSONObject)ob;
                final long unit = (Long) romanObject.get("unit");
                if(!romanNumbersIterator.hasNext()) {
                    this.limit = unit * BASE;
                }
                JSONArray romans = (JSONArray) romanObject.get("romans");
                ArrayList<String> romansList = new ArrayList<>();
                Iterator romansIterator = romans.iterator();
                while(romansIterator.hasNext()){
                    romansList.add((String) romansIterator.next());
                }
                RomanPowerOfTen rpt = new RomanPowerOfTen(unit,romansList);
                romanPowers.add(rpt);
            }

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("Error reading configuration file");
            System.exit(1);
        }
        catch (ParseException e) {
            e.printStackTrace();
            System.out.println("Error parsing configuration file");
            System.exit(2);
        }
        catch(Exception e){
            e.printStackTrace();
            System.out.println("Other exception");
            System.exit(3);
        }
    }

    /**
     * Convert actually return a string representing the long parameter as a roman number
     * @param decimal a long integer more than zero and less a limit defined by the configuration files.
     * @return The string representing the value in Roman characters.
     */
    public String convert(long decimal){
        // I should really check if Math.log!0() is actually quicker than the n empty iterations above the result it avoids
        final int startIndex = (int) Math.log10(decimal);
        StringBuilder result = new StringBuilder();
        ListIterator<RomanPowerOfTen> iterator = romanPowers.listIterator(startIndex < romanPowers.size()? startIndex+1 : romanPowers.size());
        while(iterator.hasPrevious()){
            RomanPowerOfTen rpt = iterator.previous();
            final long unit = rpt.getUnit();
            final long previousUnit = unit * BASE;
            final long remainder = decimal % previousUnit;
            // Avoid empty iterations below the result
            if (remainder == 0L) break;
            // indexOfString: always a value in the range 0-9
            final int indexOfString = (int) (remainder / unit);
            result.append(rpt.getRoman(indexOfString));
        }
        return result.toString();
    }

    /**
     * Get the limit one beyond the maximum value the configuration files will handle
     * @return limit
     */
    public long getTopLimit(){
        return limit;
    }

    /**
     * Validate that the long integer is within the range that can be represented
     * @param decimal The number to be validated.
     * @return  true or false
     */
    public boolean validate(long decimal){
        final long limit = getTopLimit();
        if(decimal <= 0 || decimal >= limit){
            return false;
        }
        return true;
    }
}
