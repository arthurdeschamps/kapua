/*******************************************************************************
 * Copyright (c) 2011, 2017 Eurotech and/or its affiliates and others
 *
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Eurotech - initial API and implementation
 *******************************************************************************/
package org.eclipse.kapua.service.datastore.internal.schema;

import org.eclipse.kapua.service.datastore.client.DatamodelMappingException;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;

import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_ALL;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_DYNAMIC;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_ENABLED;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_FORMAT;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_INCLUDE_IN_ALL;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_INDEX;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_TYPE;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.KEY_SOURCE;

import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_BINARY;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_DATE;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_DOUBLE;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_GEO_POINT;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_INTEGER;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_IP;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_KEYWORD;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.TYPE_OBJECT;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.VALUE_FALSE;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.VALUE_TRUE;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.FIELD_NAME_POSITION;
import static org.eclipse.kapua.service.datastore.client.SchemaKeys.FIELD_NAME_PROPERTIES;

import org.eclipse.kapua.commons.util.KapuaDateUtils;

/**
 * Message schema definition
 * 
 * @since 1.0
 */
public class MessageSchema {

    private MessageSchema() {

    }

    /**
     * Message schema name
     */
    public final static String MESSAGE_TYPE_NAME = "message";
    /**
     * Message timestamp
     */
    public final static String MESSAGE_TIMESTAMP = "timestamp";
    /**
     * Message received on timestamp
     */
    public final static String MESSAGE_RECEIVED_ON = "received_on";
    /**
     * Message received by address
     */
    public final static String MESSAGE_IP_ADDRESS = "ip_address";
    /**
     * Message scope id
     */
    public static final String MESSAGE_SCOPE_ID = "scope_id";
    /**
     * Message device identifier
     */
    public final static String MESSAGE_DEVICE_ID = "device_id";
    /**
     * Message client identifier
     */
    public final static String MESSAGE_CLIENT_ID = "client_id";
    /**
     * Message channel
     */
    public final static String MESSAGE_CHANNEL = "channel";
    /**
     * Message channel parts
     */
    public final static String MESSAGE_CHANNEL_PARTS = "channel_parts";
    /**
     * Message captured on timestamp
     */
    public final static String MESSAGE_CAPTURED_ON = "captured_on";
    /**
     * Message sent on timestamp
     */
    public final static String MESSAGE_SENT_ON = "sent_on";
    /**
     * Message position - (composed object)
     */
    public final static String MESSAGE_POSITION = "position";
    /**
     * Message position - location (field name relative to the position object)
     */
    public final static String MESSAGE_POS_LOCATION = "location";
    /**
     * Message position - location (full field name)
     */
    public final static String MESSAGE_POS_LOCATION_FULL = "position.location";
    /**
     * Message position - altitude (field name relative to the position object)
     */
    public final static String MESSAGE_POS_ALT = "alt";
    /**
     * Message position - altitude (full field name)
     */
    public final static String MESSAGE_POS_ALT_FULL = "position.alt";
    /**
     * Message position - precision (field name relative to the position object)
     */
    public final static String MESSAGE_POS_PRECISION = "precision";
    /**
     * Message position - precision (full field name)
     */
    public final static String MESSAGE_POS_PRECISION_FULL = "position.precision";
    /**
     * Message position - heading (field name relative to the position object)
     */
    public final static String MESSAGE_POS_HEADING = "heading";
    /**
     * Message position - heading (full field name)
     */
    public final static String MESSAGE_POS_HEADING_FULL = "position.heading";
    /**
     * Message position - speed (field name relative to the position object)
     */
    public final static String MESSAGE_POS_SPEED = "speed";
    /**
     * Message position - speed (full field name)
     */
    public final static String MESSAGE_POS_SPEED_FULL = "position.speed";
    /**
     * Message position - timestamp (field name relative to the position object)
     */
    public final static String MESSAGE_POS_TIMESTAMP = "timestamp";
    /**
     * Message position - timestamp (full field name)
     */
    public final static String MESSAGE_POS_TIMESTAMP_FULL = "position.timestamp";
    /**
     * Message position - satellites (field name relative to the position object)
     */
    public final static String MESSAGE_POS_SATELLITES = "satellites";
    /**
     * Message position - satellites (full field name)
     */
    public final static String MESSAGE_POS_SATELLITES_FULL = "position.satellites";
    /**
     * Message position - status (field name relative to the position object)
     */
    public final static String MESSAGE_POS_STATUS = "status";
    /**
     * Message position - status (full field name)
     */
    public final static String MESSAGE_POS_STATUS_FULL = "position.status";
    /**
     * Message metrics
     */
    public final static String MESSAGE_METRICS = "metrics";
    /**
     * Message body
     */
    public final static String MESSAGE_BODY = "body";

    // position internal fields
    /**
     * Position latitude inner field
     */
    public final static String MESSAGE_POSITION_LATITUDE = "lat";
    /**
     * Position longitude inner field
     */
    public final static String MESSAGE_POSITION_LONGITUDE = "lon";

    /**
     * Create and return the Json representation of the message schema
     * 
     * @param allEnable
     * @param sourceEnable
     * @return
     * @throws DatamodelMappingException
     */
    public static JsonNode getMesageTypeSchema(boolean allEnable, boolean sourceEnable) throws DatamodelMappingException {
        ObjectNode messageNode = SchemaUtil.getObjectNode();
        ObjectNode sourceMessage = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_ENABLED, sourceEnable) });
        messageNode.set(KEY_SOURCE, sourceMessage);

        ObjectNode allMessage = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_ENABLED, allEnable) });
        messageNode.set(KEY_ALL, allMessage);

        ObjectNode propertiesNode = SchemaUtil.getObjectNode();
        ObjectNode messageTimestamp = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DATE), new KeyValueEntry(KEY_FORMAT, KapuaDateUtils.ISO_DATE_PATTERN) });
        propertiesNode.set(MESSAGE_TIMESTAMP, messageTimestamp);
        ObjectNode messageReceivedOn = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DATE), new KeyValueEntry(KEY_FORMAT, KapuaDateUtils.ISO_DATE_PATTERN) });
        propertiesNode.set(MESSAGE_RECEIVED_ON, messageReceivedOn);
        ObjectNode messageIp = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_IP) });
        propertiesNode.set(MESSAGE_IP_ADDRESS, messageIp);
        ObjectNode messageScopeId = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_KEYWORD), new KeyValueEntry(KEY_INDEX, VALUE_TRUE) });
        propertiesNode.set(MESSAGE_SCOPE_ID, messageScopeId);
        ObjectNode messageDeviceId = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_KEYWORD), new KeyValueEntry(KEY_INDEX, VALUE_TRUE) });
        propertiesNode.set(MESSAGE_DEVICE_ID, messageDeviceId);
        ObjectNode messageClientId = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_KEYWORD), new KeyValueEntry(KEY_INDEX, VALUE_TRUE) });
        propertiesNode.set(MESSAGE_CLIENT_ID, messageClientId);
        ObjectNode messageChannel = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_KEYWORD), new KeyValueEntry(KEY_INDEX, VALUE_TRUE) });
        propertiesNode.set(MESSAGE_CHANNEL, messageChannel);
        ObjectNode messageCapturedOn = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DATE), new KeyValueEntry(KEY_FORMAT, KapuaDateUtils.ISO_DATE_PATTERN) });
        propertiesNode.set(MESSAGE_CAPTURED_ON, messageCapturedOn);
        ObjectNode messageSentOn = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DATE), new KeyValueEntry(KEY_FORMAT, KapuaDateUtils.ISO_DATE_PATTERN) });
        propertiesNode.set(MESSAGE_SENT_ON, messageSentOn);

        ObjectNode positionNode = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_OBJECT), new KeyValueEntry(KEY_ENABLED, true),
                        new KeyValueEntry(KEY_DYNAMIC, false), new KeyValueEntry(KEY_INCLUDE_IN_ALL, false)});
                
        ObjectNode positionPropertiesNode = SchemaUtil.getObjectNode();
        ObjectNode messagePositionPropLocation = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_GEO_POINT) });
        positionPropertiesNode.set(MESSAGE_POS_LOCATION, messagePositionPropLocation);
        ObjectNode messagePositionPropAlt = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DOUBLE) });
        positionPropertiesNode.set(MESSAGE_POS_ALT, messagePositionPropAlt);
        ObjectNode messagePositionPropPrec = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DOUBLE) });
        positionPropertiesNode.set(MESSAGE_POS_PRECISION, messagePositionPropPrec);
        ObjectNode messagePositionPropHead = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DOUBLE) });
        positionPropertiesNode.set(MESSAGE_POS_HEADING, messagePositionPropHead);
        ObjectNode messagePositionPropSpeed = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DOUBLE) });
        positionPropertiesNode.set(MESSAGE_POS_SPEED, messagePositionPropSpeed);
        ObjectNode messagePositionPropTime = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_DATE), new KeyValueEntry(KEY_FORMAT, KapuaDateUtils.ISO_DATE_PATTERN) });
        positionPropertiesNode.set(MESSAGE_POS_TIMESTAMP, messagePositionPropTime);
        ObjectNode messagePositionPropSat = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_INTEGER) });
        positionPropertiesNode.set(MESSAGE_POS_SATELLITES, messagePositionPropSat);
        ObjectNode messagePositionPropStat = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_INTEGER) });
        positionPropertiesNode.set(MESSAGE_POS_STATUS, messagePositionPropStat);
        positionNode.set(FIELD_NAME_PROPERTIES, positionPropertiesNode);
        propertiesNode.set(FIELD_NAME_POSITION, positionNode);
        messageNode.set(FIELD_NAME_PROPERTIES, propertiesNode);

        ObjectNode messageMetrics = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_OBJECT), new KeyValueEntry(KEY_ENABLED, true), 
                        new KeyValueEntry(KEY_DYNAMIC, true), new KeyValueEntry(KEY_INCLUDE_IN_ALL, false) });
        propertiesNode.set(MESSAGE_METRICS, messageMetrics);

        ObjectNode messageBody = SchemaUtil.getField(
                new KeyValueEntry[] { new KeyValueEntry(KEY_TYPE, TYPE_BINARY), new KeyValueEntry(KEY_INDEX, VALUE_FALSE) });
        propertiesNode.set(MESSAGE_BODY, messageBody);

        return messageNode;
    }

}
