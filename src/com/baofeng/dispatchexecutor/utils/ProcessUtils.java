package com.baofeng.dispatchexecutor.utils;

import java.lang.management.ManagementFactory;
import java.lang.management.RuntimeMXBean;

public class ProcessUtils {
	public static int getPid(){
		RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        String name = runtime.getName(); // format: "pid@hostname"
        try {
            return Integer.parseInt(name.substring(0, name.indexOf('@')));
        } catch (Exception e) {
            return -1;
        }
	}
}
