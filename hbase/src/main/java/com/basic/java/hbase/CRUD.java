package com.basic.java.hbase;

import com.google.common.collect.Lists;
import java.io.IOException;
import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.hbase.HBaseConfiguration;
import org.apache.hadoop.hbase.TableName;
import org.apache.hadoop.hbase.client.Connection;
import org.apache.hadoop.hbase.client.ConnectionFactory;
import org.apache.hadoop.hbase.client.Get;
import org.apache.hadoop.hbase.client.Put;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.client.Table;
import org.apache.hadoop.hbase.util.Bytes;

public class CRUD {

    public static void main(String[] args) throws IOException {
//        putExample();
//        putExampleWithPartialError();
//        compareAndSet();
        getExample();
    }

    private static void putExample() throws IOException {
        Configuration conf = HBaseConfiguration.create(); // co PutExample-1-CreateConf Create the required configuration.

        // ^^ PutExample
//        HBaseHelper helper = HBaseHelper.getHelper(conf);
//        helper.dropTable("test_table");
//        helper.createTable("test_table", "colfam1");
        // vv PutExample
        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("test_table")); // co PutExample-2-NewTable Instantiate a new client.

        Put put = new Put(Bytes.toBytes("row3")); // co PutExample-3-NewPut Create put with specific row.

        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual1"),
                Bytes.toBytes("val1")); // co PutExample-4-AddCol1 Add a column, whose name is "colfam1:qual1", to the put.
        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual2"),
                Bytes.toBytes("val2")); // co PutExample-4-AddCol2 Add another column, whose name is "colfam1:qual2", to the put.

        table.put(put); // co PutExample-5-DoPut Store row with column into the HBase table.
        table.close(); // co PutExample-6-DoPut Close table and connection instances to free resources.
        connection.close();
        // ^^ PutExample
//        helper.close();
        // vv PutExample
    }

    private static void putExampleWithPartialError() throws IOException {
        Configuration conf = HBaseConfiguration.create(); // co PutExample-1-CreateConf Create the required configuration.

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("test_table")); // co PutExample-2-NewTable Instantiate a new client.

        // 针对同一行数据的修改,当colfam2不存在报错时,colfam1也不会插入成功
        /*Put put = new Put(Bytes.toBytes("row3"));

        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"),
                Bytes.toBytes("val3"));
        put.addColumn(Bytes.toBytes("colfam2"), Bytes.toBytes("qual"),
                Bytes.toBytes("val1"));
        table.put(put); */

        // 针对不同行数据的修改,当插入row4,colfam2不存在报错时,row3是会插入成功的
        Put put1 = new Put(Bytes.toBytes("row3"));
        put1.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"),
                Bytes.toBytes("val3"));

        Put put2 = new Put(Bytes.toBytes("row4"));
        put2.addColumn(Bytes.toBytes("colfam2"), Bytes.toBytes("qual"),
                Bytes.toBytes("val1"));


        table.put(Lists.newArrayList(put1,put2));
        table.close();
        connection.close();
    }

    private static void compareAndSet() throws IOException {
        Configuration conf = HBaseConfiguration.create(); // co PutExample-1-CreateConf Create the required configuration.

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("test_table")); // co PutExample-2-NewTable Instantiate a new client.

        // 针对同一行数据的修改,当colfam2不存在报错时,colfam1也不会插入成功
        /*Put put = new Put(Bytes.toBytes("row3"));

        put.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"),
                Bytes.toBytes("val3"));
        put.addColumn(Bytes.toBytes("colfam2"), Bytes.toBytes("qual"),
                Bytes.toBytes("val1"));
        table.put(put); */

        // 针对不同行数据的修改,当插入row4,colfam2不存在报错时,row3是会插入成功的
        Put put1 = new Put(Bytes.toBytes("row3"));
        put1.addColumn(Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"),
                Bytes.toBytes("newval3"));

        boolean result = table.checkAndPut(Bytes.toBytes("row3"), Bytes.toBytes("colfam1"), Bytes.toBytes("qual3"),
                Bytes.toBytes("val3"), put1);

        System.out.println(result);
        table.close();
        connection.close();
    }

    private static void getExample() throws IOException {
        Configuration conf = HBaseConfiguration.create();

        Connection connection = ConnectionFactory.createConnection(conf);
        Table table = connection.getTable(TableName.valueOf("test_table"));

        Get get = new Get(Bytes.toBytes("row3"));

        Result result = table.get(get);

        System.out.println(result);
    }
}
