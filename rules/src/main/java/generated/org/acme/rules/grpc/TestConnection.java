// Generated by the protocol buffer compiler.  DO NOT EDIT!
// source: TestConnection.proto

package org.acme.rules.grpc;

public final class TestConnection {
  private TestConnection() {}
  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistryLite registry) {
  }

  public static void registerAllExtensions(
      com.google.protobuf.ExtensionRegistry registry) {
    registerAllExtensions(
        (com.google.protobuf.ExtensionRegistryLite) registry);
  }
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TestGo_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TestGo_fieldAccessorTable;
  static final com.google.protobuf.Descriptors.Descriptor
    internal_static_TestBack_descriptor;
  static final 
    com.google.protobuf.GeneratedMessageV3.FieldAccessorTable
      internal_static_TestBack_fieldAccessorTable;

  public static com.google.protobuf.Descriptors.FileDescriptor
      getDescriptor() {
    return descriptor;
  }
  private static  com.google.protobuf.Descriptors.FileDescriptor
      descriptor;
  static {
    java.lang.String[] descriptorData = {
      "\n\024TestConnection.proto\032\033google/protobuf/" +
      "empty.proto\032\036google/protobuf/wrappers.pr" +
      "oto\"\030\n\006TestGo\022\016\n\006msgOut\030\001 \001(\t\"\031\n\010TestBac" +
      "k\022\r\n\005msgIn\030\001 \001(\t2+\n\013TestService\022\034\n\004test\022" +
      "\007.TestGo\032\t.TestBack\"\000B\'\n\023org.acme.rules." +
      "grpcB\016TestConnectionP\001b\006proto3"
    };
    descriptor = com.google.protobuf.Descriptors.FileDescriptor
      .internalBuildGeneratedFileFrom(descriptorData,
        new com.google.protobuf.Descriptors.FileDescriptor[] {
          com.google.protobuf.EmptyProto.getDescriptor(),
          com.google.protobuf.WrappersProto.getDescriptor(),
        });
    internal_static_TestGo_descriptor =
      getDescriptor().getMessageTypes().get(0);
    internal_static_TestGo_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TestGo_descriptor,
        new java.lang.String[] { "MsgOut", });
    internal_static_TestBack_descriptor =
      getDescriptor().getMessageTypes().get(1);
    internal_static_TestBack_fieldAccessorTable = new
      com.google.protobuf.GeneratedMessageV3.FieldAccessorTable(
        internal_static_TestBack_descriptor,
        new java.lang.String[] { "MsgIn", });
    com.google.protobuf.EmptyProto.getDescriptor();
    com.google.protobuf.WrappersProto.getDescriptor();
  }

  // @@protoc_insertion_point(outer_class_scope)
}
