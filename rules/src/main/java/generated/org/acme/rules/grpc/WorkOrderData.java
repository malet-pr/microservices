// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: WorkOrderData.proto

package org.acme.rules.grpc;

public final class WorkOrderData {
  private WorkOrderData() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WorkOrderRequest_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WorkOrderRequest_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WorkOrderResponse_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WorkOrderResponse_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_WoJobDTO_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_WoJobDTO_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\023WorkOrderData.proto\032\033google/protobuf/e" +
      "mpty.proto\032\036google/protobuf/wrappers.pro" +
      "to\032\037google/protobuf/timestamp.proto\"\207\002\n\020" +
      "WorkOrderRequest\022\020\n\010woNumber\030\001 \001(\t\022\034\n\two" +
      "JobDTOs\030\002 \003(\0132\t.WoJobDTO\022\023\n\013jobTypeCode\030" +
      "\003 \001(\t\022\017\n\007address\030\004 \001(\t\022\014\n\004city\030\005 \001(\t\0222\n\016" +
      "woCreationDate\030\006 \001(\0132\032.google.protobuf.T" +
      "imestamp\0224\n\020woCompletionDate\030\007 \001(\0132\032.goo" +
      "gle.protobuf.Timestamp\022\020\n\010clientId\030\010 \001(\t" +
      "\022\023\n\013appliedRule\030\t \001(\t\"\210\002\n\021WorkOrderRespo" +
      "nse\022\020\n\010woNumber\030\001 \001(\t\022\034\n\twoJobDTOs\030\002 \003(\013" +
      "2\t.WoJobDTO\022\023\n\013jobTypeCode\030\003 \001(\t\022\017\n\007addr" +
      "ess\030\004 \001(\t\022\014\n\004city\030\005 \001(\t\0222\n\016woCreationDat" +
      "e\030\006 \001(\0132\032.google.protobuf.Timestamp\0224\n\020w" +
      "oCompletionDate\030\007 \001(\0132\032.google.protobuf." +
      "Timestamp\022\020\n\010clientId\030\010 \001(\t\022\023\n\013appliedRu" +
      "le\030\t \001(\t\"j\n\010WoJobDTO\022\020\n\010woNumber\030\001 \001(\t\022\017" +
      "\n\007jobCode\030\002 \001(\t\022\020\n\010quantity\030\003 \001(\005\022\024\n\014act" +
      "iveStatus\030\004 \001(\t\022\023\n\013appliedRule\030\005 \001(\t2K\n\020" +
      "WorkOrderService\0227\n\014runRulesToWO\022\021.WorkO" +
      "rderRequest\032\022.WorkOrderResponse\"\000B&\n\023org" +
      ".acme.rules.grpcB\rWorkOrderDataP\001b\006proto" +
      "3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
          com.google.protobuf.WrappersProto.getDescriptor(),
          com.google.protobuf.TimestampProto.getDescriptor(),
        });
    internal_static_WorkOrderRequest_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_WorkOrderRequest_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WorkOrderRequest_descriptor,
        new java.lang.String[] { "WoNumber", "WoJobDTOs", "JobTypeCode", "Address", "City", "WoCreationDate", "WoCompletionDate", "ClientId", "AppliedRule", });
    internal_static_WorkOrderResponse_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_WorkOrderResponse_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WorkOrderResponse_descriptor,
        new java.lang.String[] { "WoNumber", "WoJobDTOs", "JobTypeCode", "Address", "City", "WoCreationDate", "WoCompletionDate", "ClientId", "AppliedRule", });
    internal_static_WoJobDTO_descriptor =
      getDescriptor().getMessageTypes().get(2);
    internal_static_WoJobDTO_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_WoJobDTO_descriptor,
        new java.lang.String[] { "WoNumber", "JobCode", "Quantity", "ActiveStatus", "AppliedRule", });
    com.google.protobuf.EmptyProto.getDescriptor();
    com.google.protobuf.WrappersProto.getDescriptor();
    com.google.protobuf.TimestampProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
