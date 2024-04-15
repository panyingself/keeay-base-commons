package com.keeay.anepoch.base.commons.filter;

import com.keeay.anepoch.base.commons.filter.constants.TraceConstants;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;
import java.net.Inet4Address;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.concurrent.atomic.AtomicInteger;

public final class TraceIdHelper {
    private TraceIdHelper() {

    }

    /**
     * ip 地址
     */
    private static final InetAddress HOST_IP = getINetAddress();
    /**
     * 进程id
     */
    private static final int PROCESS_ID = getProcessID();
    /**
     * 原子计数
     */
    private static AtomicInteger SEQ = new AtomicInteger();


    private static void writeInt(byte[] data, int i, int val) {
        data[i + 3] = (byte) val;
        data[i + 2] = (byte) (val >> 8);
        data[i + 1] = (byte) (val >> 16);
        data[i] = (byte) (val >> 24);
    }

    private static void writeShort(byte[] data, int i, short val) {
        data[i + 1] = (byte) val;
        data[i + 0] = (byte) (val >> 8);
    }

    private static String toHexString(byte[] b) {
        StringBuilder ret = new StringBuilder();
        for (int i = 0; i < b.length; i++) {
            String hex = Integer.toHexString(b[i] & 0xFF);
            if (hex.length() == 1) {
                hex = '0' + hex;
            }
            ret.append(hex);
        }
        return ret.toString();
    }


    private static InetAddress getINetAddress() {
        try {
            Enumeration allNetInterfaces = NetworkInterface.getNetworkInterfaces();
            InetAddress ip = null;
            while (allNetInterfaces.hasMoreElements()) {
                NetworkInterface netInterface = (NetworkInterface) allNetInterfaces.nextElement();
                Enumeration addresses = netInterface.getInetAddresses();
                while (addresses.hasMoreElements()) {
                    ip = (InetAddress) addresses.nextElement();
                    if (ip instanceof Inet4Address) {
                        return ip;
                    }
                }
            }
            return ip;
        } catch (SocketException e) {
            throw new RuntimeException("can not get valid IP Address", e);

        }
    }

    private static int getProcessID() {
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        return Integer.valueOf(runtimeMXBean.getName().split("@")[0]);
    }

    private static short getThreadID() {
        return (short) Thread.currentThread().getId();
    }

    /**
     * 生成traceId的方法
     *
     * @return traceId
     */
    public static String generate() {
        //ip + pid + timestamp + seq
        byte[] data = new byte[16];

        byte[] ip = HOST_IP.getAddress();
        System.arraycopy(ip, ip.length - 4, data, 0, 4);
        writeInt(data, 4, PROCESS_ID);
        writeInt(data, 8, (int) (System.currentTimeMillis() / 1000));
        writeInt(data, 12, SEQ.incrementAndGet());

        return toHexString(data);
    }

//    /**
//     * 生成traceId的方法
//     *
//     * @return traceId
//     */
//    public static String generate() {
//        return UUID.randomUUID().toString().replace("-", "").toLowerCase();
//    }

    public static String getCurrentTraceId() {
        return MDC.get(TraceConstants.TRACE_ID);
    }

    public static void setTraceId(String traceId) {
        if (StringUtils.isNotEmpty(traceId)) {
            MDC.put(TraceConstants.TRACE_ID, traceId);
            return;
        }
        MDC.put(TraceConstants.TRACE_ID, generate());
    }
}
