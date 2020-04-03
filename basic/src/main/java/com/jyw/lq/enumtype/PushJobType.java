/**
 * PushJobType.java
 * [CopyRight]
 *
 * @author leo [liuy@xiaomi.com]
 * @date Aug 19, 2013 6:21:16 PM
 */
package com.jyw.lq.enumtype;

/**
 * @author leo
 * 跟server-api的PushJobType同步，如果增加/修改job类型需要同时修改
 */
public enum PushJobType {
    Invalid(0, ' '),
    Topic(1, 't'),
    Common(2, 's'),
    Alias(3, 'a'),
    BatchAlias(4, 'a'),
    BatchRegId(5, 'r'),
    UserAccount(7, 'u'),
    BatchUserAccount(8, 'u'),
    DeviceId(9, 'd'),
    ImeiMd5(10, 'i'),
    PublicWelfare(11, 'p'),   // 公益消息
    Miid(12, 'm'),
    BatchMiid(13, 'm'),  // mi id 消息
    Region(14, 'r'),
    GeoFencing(15, 'x'), // 已废弃
    GeoGroup(16, 'x'),   // 已废弃
    Gaid(17, 'g'),
    BatchImeiMd5(18, 'i'),
    BatchGaid(19, 'g'),
    Oaid(20, 'o');

    private final byte value;
    private final char prefix;
    private static PushJobType[] VALID_JOB_TYPES = {Topic, Common, Alias, UserAccount, ImeiMd5, Miid, Gaid};

    private PushJobType(int value, char prefix) {
        this((byte) value, prefix);
    }

    private PushJobType(byte value, char prefix) {
        this.value = value;
        this.prefix = prefix;
    }

    public byte value() {
        return value;
    }

    public char prefix() {
        return prefix;
    }

    static PushJobType of(char c) {
        for (PushJobType type : VALID_JOB_TYPES) {
            if (type.prefix == c) {
                return type;
            }
        }
        return Invalid;
    }

    /**
     * Get push job type by the string prefix.
     *
     * @param s
     * @return
     */
    public static PushJobType of(String s) {
        if (s == null || s.length() < 1) {
            return Invalid;
        }
        return of(s.charAt(0));
    }

    public static PushJobType from(byte value) {
        for (PushJobType type : VALID_JOB_TYPES) {
            if (type.value == value) {
                return type;
            }
        }
        return Invalid;
    }

    public static void main(String[] args) {
        System.out.println(PushJobType.Topic.equals(PushJobType.of("")));
    }
}
