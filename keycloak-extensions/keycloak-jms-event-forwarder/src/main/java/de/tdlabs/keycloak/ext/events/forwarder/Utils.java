package de.tdlabs.keycloak.ext.events.forwarder;

import java.lang.management.ManagementFactory;
import java.net.Inet4Address;
import java.net.InetAddress;

public class Utils {

	static final String INSTANCE_NAME = getInstanceName();
	
	static String toComponentIdString(Object object) {

		if (object == null) {
			return null;
		}

		return object.getClass().getSimpleName() + System.identityHashCode(object);
	}

	private static String getInstanceName() {

		String instanceName = System.getenv("INSTANCE_NAME");
		if (instanceName != null) {
			return instanceName;
		}

		return generateInstaceName();
	}

	private static String generateInstaceName() {

		String hostIp = System.getenv("HOST_IP");

		try {
			InetAddress localhost = Inet4Address.getLocalHost();
			if (hostIp == null) {
				hostIp = localhost.getHostAddress();
			}
		} catch (Exception ignore) {
			// ignored
		}

		String vmName = ManagementFactory.getRuntimeMXBean().getName();

		return vmName + ":" + hostIp;
	}
}
