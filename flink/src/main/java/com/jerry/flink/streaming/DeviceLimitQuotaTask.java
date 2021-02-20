package com.jerry.flink.streaming;

import com.google.common.base.Joiner;
import com.google.common.base.Splitter;
import org.apache.flink.api.common.functions.FilterFunction;
import org.apache.flink.api.common.functions.MapFunction;
import org.apache.flink.api.common.functions.ReduceFunction;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.streaming.api.datastream.DataStream;
import org.apache.flink.streaming.api.datastream.SingleOutputStreamOperator;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.api.functions.sink.PrintSinkFunction;
import org.apache.flink.streaming.api.functions.sink.RichSinkFunction;
import org.apache.flink.streaming.api.functions.sink.SinkFunction;
import org.apache.flink.streaming.api.operators.StreamingRuntimeContext;
import org.apache.flink.streaming.api.windowing.assigners.TumblingProcessingTimeWindows;
import org.apache.flink.streaming.api.windowing.time.Time;
import scala.Tuple2;
import scala.Tuple3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class DeviceLimitQuotaTask {
    public static void main(String[] args) throws Exception {
        StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();
        DataStream<String> text = env.socketTextStream("127.0.0.1", 9999);

        // appId userId env
        text.map(new MapFunction<String, Tuple2<Tuple2<String, String>, String>>() {
            @Override
            public Tuple2<Tuple2<String, String>, String> map(String s) throws Exception {
                List<String> strings = Splitter.on(" ").splitToList(s);
                return new Tuple2<>(new Tuple2<>(strings.get(0), strings.get(1)), strings.get(2));
            }
        }).keyBy(new KeySelector<Tuple2<Tuple2<String, String>, String>, String>() {
            @Override
            public String getKey(Tuple2<Tuple2<String, String>, String> tuple2) throws Exception {
                return tuple2._1()._1 + tuple2._1()._2;
            }
        }).window(TumblingProcessingTimeWindows.of(Time.seconds(5))).reduce(new ReduceFunction<Tuple2<Tuple2<String, String>, String>>() {
            @Override
            public Tuple2<Tuple2<String, String>, String> reduce(Tuple2<Tuple2<String, String>, String> tuple2, Tuple2<Tuple2<String, String>, String> t1) {
                return t1;
            }
        }).filter(new FilterFunction<Tuple2<Tuple2<String, String>, String>>() {
            @Override
            public boolean filter(Tuple2<Tuple2<String, String>, String> tuple2) throws Exception {
                Thread.sleep(10000);
                return !AckStatisticsCacheByDevice.isUserIdAcked(Long.parseLong(tuple2._1._1), Long.parseLong(tuple2._1._2));
            }
        }).map(new MapFunction<Tuple2<Tuple2<String, String>, String>, Tuple2<String, Map<String, String>>>() {
            @Override
            public Tuple2<String, Map<String, String>> map(Tuple2<Tuple2<String, String>, String> tuple2) throws Exception {
                Map<String, String> map = new HashMap<>();
                map.put(AckStatisticsCacheByDevice.getRecordByUserIdSubKey(Long.parseLong(tuple2._1._1), Long.parseLong(tuple2._1._2)), "1");
                return new Tuple2<>(AckStatisticsCacheByDevice.getRecordByUserIdKey(Long.parseLong(tuple2._1._2)), map);
            }
        }).keyBy(new KeySelector<Tuple2<String, Map<String, String>>, String>() {
            @Override
            public String getKey(Tuple2<String, Map<String, String>> tuple2) throws Exception {
                return tuple2._1;
            }
        }).reduce(new ReduceFunction<Tuple2<String, Map<String, String>>>() {
            @Override
            public Tuple2<String, Map<String, String>> reduce(Tuple2<String, Map<String, String>> t1, Tuple2<String, Map<String, String>> t2) throws Exception {
                t1._2.putAll(t2._2);
                return t1;
            }
        }).addSink(new RichSinkFunction<Tuple2<String, Map<String, String>>>() {

            // 记录两个,1:记录userId,2:记录account
            @Override
            public void invoke(Tuple2<String, Map<String, String>> value, Context context) throws Exception {
                System.out.println(context.currentProcessingTime() + "\t"+value._1+"\t"+ Joiner.on(",").join(value._2.entrySet()));
            }
        });


                /*addSink(new RichSinkFunction<Tuple2<String, Map<String, String>>>() {
                    @Override
                    public void invoke(Tuple2<String, Map<String, String>> value, Context context) throws Exception {
                        AckStatisticsCacheByDevice.recordByUserId(value._1, value._2);
                    }
                });*/


        env.execute();
    }
}
