package net.kuchenmeister;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
        final List<String> friends = Arrays.asList("Jim", "Darrin", "Karen", "Joe", "Steve");
        friends.forEach(new Consumer<String>() {
            @Override
            public void accept(String name) {
                System.out.println(name);
            }
        });
        System.out.println("-----------");
        friends.forEach(System.out::println);
        System.out.println("-----------");
        friends.stream()
                .map(String::toUpperCase)
                .forEach(name -> System.out.println(name + " "));

        System.out.println("-----------");

        final List<String> startsWithJ = friends.stream()
                .filter(name -> name.startsWith("J"))
                .collect(Collectors.toList());
        System.out.println( String.format("Found %d names", startsWithJ.size()));
    }
}
