package jvm;

import java.util.HashMap;
import java.util.Map;
//-Xms16m -Xmx16m -XX:+HeapDumpOnOutOfMemoryError -XX:HeapDumpPath=/home/mi/ -XX:OnOutOfMemoryError="echo 'outofmemoryerror' >> /home/mi/error.log"
public class OutOfMemory {
    public static void main(String[] args) {
        Map<String,String> map = new HashMap<>();
        for(long i=0;i<Long.MAX_VALUE;i++){
            map.put(String.valueOf(i),String.valueOf(i));
        }
    }
}