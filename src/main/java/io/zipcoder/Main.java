package io.zipcoder;

import javafx.scene.Group;
import org.apache.commons.io.IOUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.System.out;
import static java.lang.System.setOut;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.toList;


public class Main {

    public String readRawDataToString() throws Exception{
        ClassLoader classLoader = getClass().getClassLoader();
        String result = IOUtils.toString(classLoader.getResourceAsStream("RawData.txt"));
        return result;
    }

    public static void main(String[] args) throws Exception{
        String output = (new Main()).readRawDataToString();
        ItemParser itemParser = new ItemParser();
        ArrayList<String> lines = itemParser.parseRawDataIntoStringArray(output);
        Item item=null;


        List<Item> itemList = new ArrayList<>();
        for(String line:lines){
            try {
                item = itemParser.parseStringIntoItem(line);
                itemList.add(item);
            }catch (Exception e){
                e.printStackTrace();
            }
        }


  //      itemList.stream().forEach(out::println);

        /*Map<Integer, List<String>> reverseMap = results.entrySet().stream().collect(Collectors.
                groupingBy(Map.Entry::getValue, Collectors.mapping(Map.Entry::getKey, Collectors.toList())));
*/

        Map<String, List<Double>> results = itemList.stream().collect(Collectors.groupingBy(Item::getName,
                Collectors.mapping(Item::getPrice, Collectors.toList())));



        results.forEach((String k, List<Double> v) -> {

            Map<Double, Long> collect = v.stream().collect(groupingBy(Function.identity(), Collectors.counting()));

            System.out.println("Name:   "  + k + "\t" + "seen: " + v.size() + " times");
            System.out.println("============= " + "  =============");

            collect.forEach((price,times)->{
                System.out.println("Price:  " + price + "\t"+ "seen: " + times + " times");
                System.out.println("-------------" + "  -------------");
            });

        });

        System.out.println("Errors "+itemParser.getErrors());


    }
}
