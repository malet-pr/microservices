// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WorkOrderData.proto

package org.acme.rules.grpc;

public interface WoJobDTOOrBuilder extends
    // @@protoc_insertion_point(interface_extends:WoJobDTO)
    com.google.protobuf.MessageOrBuilder {

  /**
   * <code>string woNumber = 1;</code>
   * @return The woNumber.
   */
  java.lang.String getWoNumber();
  /**
   * <code>string woNumber = 1;</code>
   * @return The bytes for woNumber.
   */
  com.google.protobuf.ByteString
      getWoNumberBytes();

  /**
   * <code>string jobCode = 2;</code>
   * @return The jobCode.
   */
  java.lang.String getJobCode();
  /**
   * <code>string jobCode = 2;</code>
   * @return The bytes for jobCode.
   */
  com.google.protobuf.ByteString
      getJobCodeBytes();

  /**
   * <code>int32 quantity = 3;</code>
   * @return The quantity.
   */
  int getQuantity();

  /**
   * <code>string activeStatus = 4;</code>
   * @return The activeStatus.
   */
  java.lang.String getActiveStatus();
  /**
   * <code>string activeStatus = 4;</code>
   * @return The bytes for activeStatus.
   */
  com.google.protobuf.ByteString
      getActiveStatusBytes();

  /**
   * <code>string appliedRule = 5;</code>
   * @return The appliedRule.
   */
  java.lang.String getAppliedRule();
  /**
   * <code>string appliedRule = 5;</code>
   * @return The bytes for appliedRule.
   */
  com.google.protobuf.ByteString
      getAppliedRuleBytes();
}
