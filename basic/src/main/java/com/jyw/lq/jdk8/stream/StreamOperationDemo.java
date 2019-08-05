package com.jyw.lq.jdk8.stream;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/*
* stream流只能用于Collection接口，即只能用于list和set，不能用于map
* */
public class StreamOperationDemo {

    private static List<String> initList(){
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(String.valueOf(i));
        }
        return list;
    }

    private static String[] initArray(){
        String[] strs = new String[10];
        List<String> list = new ArrayList<>();
        for(int i=0;i<10;i++){
            list.add(String.valueOf(i));
        }
        return list.toArray(strs);
    }

    //针对流的操作总是产生一个新的流而不是改变原来流中的元素
    //每一个中间操作都必须在有一个最终action的时候才会执行
    public static void main(String[] args) {
//        easyOperation();
//        flatMap1();
        flatMap2();

    }

    private static void easyOperation(){
        List<String> list = initList();
        //allMatch  stream中的所有元素都符合Predicate表达式
        boolean allMatch = list.stream().allMatch(str -> Integer.parseInt(str) < 10);
        System.out.println(allMatch);

        //anyMatch 只要任意一个满足就返回true
        boolean anyMatch = list.stream().anyMatch(str -> Integer.parseInt(str) < 0);
        System.out.println(anyMatch);

        System.out.println(list.stream().findAny().get());
        System.out.println(list.stream().findFirst().get());

        //reduce 与spark中reduce操作类似，当使用并行流的时候需要注意所定义的函数使用在元素中时需要满足结合律
        System.out.println(list.stream().reduce((str1,str2)->str1.concat("#").concat(str2)).get());

        //排序只是产生新的流使得该流内部的元素有序
        //可以直接使用Comparator.reverseOrder()
        list.stream().sorted((str1,str2)->str2.compareTo(str1)).forEach(System.out::println);
        //list.stream().sorted(Comparator.naturalOrder()).forEach(System.out::println);

        //map操作不会改变原来的list，只是产生新的流
        list.stream().map(str->Integer.parseInt(str)+10).forEach(System.out::println);


        //peek：产生一个与源流一模一样的新流，不过每次都会拿取一个元素执行指定的函数
        //结果：20 0 21 1 22 2 23 3 ......
        list.stream().peek(str -> {
            System.out.println(Integer.parseInt(str) + 20);
        }).forEach(System.out::println);
    }

    //flatMap：和map类似，不同的是其每个元素转换得到的是Stream对象，会把子Stream中的元素压缩到父集合中；
    //应用场景：当传递过来的函数作用于元素后产生的是一个包含多个元素的Stream，但是我们只想要一个子元素的Stream时可以使用
    private static void flatMap1(){
        List<String> teamIndia = Arrays.asList("Virat", "Dhoni", "Jadeja");
        List<String> teamAustralia = Arrays.asList("Warner", "Watson", "Smith");
        List<String> teamEngland = Arrays.asList("Alex", "Bell", "Broad");
        List<String> teamNewZeland = Arrays.asList("Kane", "Nathan", "Vettori");
        List<String> teamSouthAfrica = Arrays.asList("AB", "Amla", "Faf");
        List<String> teamWestIndies = Arrays.asList("Sammy", "Gayle", "Narine");
        List<String> teamSriLanka = Arrays.asList("Mahela", "Sanga", "Dilshan");
        List<String> teamPakistan = Arrays.asList("Misbah", "Afridi", "Shehzad");

        List<List<String>> playersInWorldCup2016 = new ArrayList<>();
        playersInWorldCup2016.add(teamIndia);
        playersInWorldCup2016.add(teamAustralia);
        playersInWorldCup2016.add(teamEngland);
        playersInWorldCup2016.add(teamNewZeland);
        playersInWorldCup2016.add(teamSouthAfrica);
        playersInWorldCup2016.add(teamWestIndies);
        playersInWorldCup2016.add(teamSriLanka);
        playersInWorldCup2016.add(teamPakistan);

        // Let's print all players before Java 8
        List<String> listOfAllPlayers = new ArrayList<>();

        for(List<String> team : playersInWorldCup2016){
            for(String name : team){
                listOfAllPlayers.add(name);
            }
        }

        System.out.println("Players playing in world cup 2016");
        System.out.println(listOfAllPlayers);

        Stream<List<String>> stream = playersInWorldCup2016.stream();


        // Now let's do this in Java 8 using FlatMap
        List<String> flatMapList = playersInWorldCup2016.stream()
                .flatMap(pList -> pList.stream())
                .collect(Collectors.toList());

        System.out.println("List of all Players using Java 8");
        System.out.println(flatMapList);
    }


    //当有一个函数f返回的是一个Optional<T>类型，函数G需要一个T类型的参数，当我们需要调用f之后再将结果结果传递给G函数时，即G(f)
    //正常情况下显然是不可以的，但是我们可以使用f().flatMap(G),当f返回的Optional不为空的时候就会去调用G，否则不会调用，直接返回一个Optional.empty()
    private static void flatMap2(){
        double a = 0;
        double b = 1;
        inverse(a).flatMap(StreamOperationDemo::squartRoot).ifPresent(System.out::println);
        inverse(b).flatMap(StreamOperationDemo::squartRoot).ifPresent(System.out::println);
    }

    private static Optional<Double> inverse(double x){
        return x==0?Optional.empty():Optional.of(1/x);
    }

    private static Optional<Double> squartRoot(double x){
        return x<0?Optional.empty():Optional.of(Math.sqrt(x));
    }
}
