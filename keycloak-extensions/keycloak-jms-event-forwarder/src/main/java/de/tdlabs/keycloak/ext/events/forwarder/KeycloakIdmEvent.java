package de.tdlabs.keycloak.ext.events.forwarder;

import static java.util.Collections.emptyMap;

import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

import org.keycloak.events.Event;
import org.keycloak.events.admin.AdminEvent;

import lombok.Data;

/**
 * Vereinheitlicht Keycloak {@link Event Event's} und {@link AdminEvent
 * AdminEvent's}.
 * 
 * @author tdarimont
 */
@Data
public class KeycloakIdmEvent {

	/**
	 * {@link UUID} ID des Events als {@link String}.
	 */
	private String eventId;
	
	/**
	 * Name der Keycloak Instanz welche das Event erzeugt hat.
	 */
	private String instanceName;

	/**
	 * Technische Name des Realms.
	 */
	private String realmId;

	/**
	 * {@link UUID} ID des Users als {@link String}.
	 */
	private String userId;

	/**
	 * IdmEvent {@link Type}.
	 */
	private Type type;

	/**
	 * UNIX Timestamp des Events.
	 */
	private long timestamp;

	/**
	 * Kontext in dem das Event stattgefunden hat.
	 */
	private String contextId;

	/**
	 * Kontext Aktion die das {@link KeycloakIdmEvent} ausgelöst hat.
	 */
	private String contextAction;

	/**
	 * Weitere Daten zum Kontext, wie etwa eine Datenänderung.
	 */
	private Map<String, Object> contextData = emptyMap();

	/**
	 * Audit Informationen wer das Event verursacht hat.
	 */
	private AuditInfo auditInfo;

	/**
	 * Informationen zum Benutzer für den das Event gilt.
	 */
	private UserInfo userInfo;

	public KeycloakIdmEvent(AdminEvent adminEvent) {
		this(null, adminEvent.getRealmId(), adminEvent.getTime(), Type.ADMIN);
	}

	public KeycloakIdmEvent(Event userEvent) {
		this(userEvent.getUserId(), userEvent.getRealmId(), userEvent.getTime(), Type.USER);
	}

	private KeycloakIdmEvent(String userId, String realmId, long timestamp, Type type) {

		this.eventId = UUID.randomUUID().toString();
		this.userId = userId;
		this.realmId = realmId;
		this.timestamp = timestamp;
		this.type = type;
		this.contextData = new LinkedHashMap<>();
	}

	/**
	 * Der Typ des Keycloak Events.
	 * 
	 * @author tdarimont
	 */
	enum Type {
		/**
		 * {@code USER USER Event's} sind Keycloak Events die durch
		 * Benutzeraktionen ausgelöst werden.
		 */
		USER,

		/**
		 * {@code ADMIN AdminEvent's} sind Keycloak Events die durch
		 * Administratoraktionen ausgelöst werden.
		 */
		ADMIN
	}
}
