package org.acme.rules.grpc;

import static io.grpc.MethodDescriptor.generateFullMethodName;

/**
 */
@javax.annotation.Generated(
    value = "by gRPC proto compiler (version 1.62.2)",
    comments = "Source: WorkOrderData.proto")
@io.grpc.stub.annotations.GrpcGenerated
public final class WorkOrderServiceGrpc {

  private WorkOrderServiceGrpc() {}

  public static final java.lang.String SERVICE_NAME = "WorkOrderService";

  // Static method descriptors that strictly reflect the proto.
  private static volatile io.grpc.MethodDescriptor<org.acme.rules.grpc.WorkOrderRequest,
      com.google.protobuf.Empty> getWoWithRulesMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "woWithRules",
      requestType = org.acme.rules.grpc.WorkOrderRequest.class,
      responseType = com.google.protobuf.Empty.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.acme.rules.grpc.WorkOrderRequest,
      com.google.protobuf.Empty> getWoWithRulesMethod() {
    io.grpc.MethodDescriptor<org.acme.rules.grpc.WorkOrderRequest, com.google.protobuf.Empty> getWoWithRulesMethod;
    if ((getWoWithRulesMethod = WorkOrderServiceGrpc.getWoWithRulesMethod) == null) {
      synchronized (WorkOrderServiceGrpc.class) {
        if ((getWoWithRulesMethod = WorkOrderServiceGrpc.getWoWithRulesMethod) == null) {
          WorkOrderServiceGrpc.getWoWithRulesMethod = getWoWithRulesMethod =
              io.grpc.MethodDescriptor.<org.acme.rules.grpc.WorkOrderRequest, com.google.protobuf.Empty>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "woWithRules"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.acme.rules.grpc.WorkOrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  com.google.protobuf.Empty.getDefaultInstance()))
              .setSchemaDescriptor(new WorkOrderServiceMethodDescriptorSupplier("woWithRules"))
              .build();
        }
      }
    }
    return getWoWithRulesMethod;
  }

  /**
   * Creates a new async stub that supports all call types for the service
   */
  public static WorkOrderServiceStub newStub(io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceStub>() {
        @java.lang.Override
        public WorkOrderServiceStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkOrderServiceStub(channel, callOptions);
        }
      };
    return WorkOrderServiceStub.newStub(factory, channel);
  }

  /**
   * Creates a new blocking-style stub that supports unary and streaming output calls on the service
   */
  public static WorkOrderServiceBlockingStub newBlockingStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceBlockingStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceBlockingStub>() {
        @java.lang.Override
        public WorkOrderServiceBlockingStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkOrderServiceBlockingStub(channel, callOptions);
        }
      };
    return WorkOrderServiceBlockingStub.newStub(factory, channel);
  }

  /**
   * Creates a new ListenableFuture-style stub that supports unary calls on the service
   */
  public static WorkOrderServiceFutureStub newFutureStub(
      io.grpc.Channel channel) {
    io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceFutureStub> factory =
      new io.grpc.stub.AbstractStub.StubFactory<WorkOrderServiceFutureStub>() {
        @java.lang.Override
        public WorkOrderServiceFutureStub newStub(io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
          return new WorkOrderServiceFutureStub(channel, callOptions);
        }
      };
    return WorkOrderServiceFutureStub.newStub(factory, channel);
  }

  /**
   */
  public interface AsyncService {

    /**
     */
    default void woWithRules(org.acme.rules.grpc.WorkOrderRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getWoWithRulesMethod(), responseObserver);
    }
  }

  /**
   * Base class for the server implementation of the service WorkOrderService.
   */
  public static abstract class WorkOrderServiceImplBase
      implements io.grpc.BindableService, AsyncService {

    @java.lang.Override public final io.grpc.ServerServiceDefinition bindService() {
      return WorkOrderServiceGrpc.bindService(this);
    }
  }

  /**
   * A stub to allow clients to do asynchronous rpc calls to service WorkOrderService.
   */
  public static final class WorkOrderServiceStub
      extends io.grpc.stub.AbstractAsyncStub<WorkOrderServiceStub> {
    private WorkOrderServiceStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkOrderServiceStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkOrderServiceStub(channel, callOptions);
    }

    /**
     */
    public void woWithRules(org.acme.rules.grpc.WorkOrderRequest request,
        io.grpc.stub.StreamObserver<com.google.protobuf.Empty> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getWoWithRulesMethod(), getCallOptions()), request, responseObserver);
    }
  }

  /**
   * A stub to allow clients to do synchronous rpc calls to service WorkOrderService.
   */
  public static final class WorkOrderServiceBlockingStub
      extends io.grpc.stub.AbstractBlockingStub<WorkOrderServiceBlockingStub> {
    private WorkOrderServiceBlockingStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkOrderServiceBlockingStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkOrderServiceBlockingStub(channel, callOptions);
    }

    /**
     */
    public com.google.protobuf.Empty woWithRules(org.acme.rules.grpc.WorkOrderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getWoWithRulesMethod(), getCallOptions(), request);
    }
  }

  /**
   * A stub to allow clients to do ListenableFuture-style rpc calls to service WorkOrderService.
   */
  public static final class WorkOrderServiceFutureStub
      extends io.grpc.stub.AbstractFutureStub<WorkOrderServiceFutureStub> {
    private WorkOrderServiceFutureStub(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      super(channel, callOptions);
    }

    @java.lang.Override
    protected WorkOrderServiceFutureStub build(
        io.grpc.Channel channel, io.grpc.CallOptions callOptions) {
      return new WorkOrderServiceFutureStub(channel, callOptions);
    }

    /**
     */
    public com.google.common.util.concurrent.ListenableFuture<com.google.protobuf.Empty> woWithRules(
        org.acme.rules.grpc.WorkOrderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getWoWithRulesMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_WO_WITH_RULES = 0;

  private static final class MethodHandlers<Req, Resp> implements
      io.grpc.stub.ServerCalls.UnaryMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ServerStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.ClientStreamingMethod<Req, Resp>,
      io.grpc.stub.ServerCalls.BidiStreamingMethod<Req, Resp> {
    private final AsyncService serviceImpl;
    private final int methodId;

    MethodHandlers(AsyncService serviceImpl, int methodId) {
      this.serviceImpl = serviceImpl;
      this.methodId = methodId;
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public void invoke(Req request, io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        case METHODID_WO_WITH_RULES:
          serviceImpl.woWithRules((org.acme.rules.grpc.WorkOrderRequest) request,
              (io.grpc.stub.StreamObserver<com.google.protobuf.Empty>) responseObserver);
          break;
        default:
          throw new AssertionError();
      }
    }

    @java.lang.Override
    @java.lang.SuppressWarnings("unchecked")
    public io.grpc.stub.StreamObserver<Req> invoke(
        io.grpc.stub.StreamObserver<Resp> responseObserver) {
      switch (methodId) {
        default:
          throw new AssertionError();
      }
    }
  }

  public static final io.grpc.ServerServiceDefinition bindService(AsyncService service) {
    return io.grpc.ServerServiceDefinition.builder(getServiceDescriptor())
        .addMethod(
          getWoWithRulesMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.acme.rules.grpc.WorkOrderRequest,
              com.google.protobuf.Empty>(
                service, METHODID_WO_WITH_RULES)))
        .build();
  }

  private static abstract class WorkOrderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WorkOrderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.acme.rules.grpc.WorkOrderData.getDescriptor();
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.ServiceDescriptor getServiceDescriptor() {
      return getFileDescriptor().findServiceByName("WorkOrderService");
    }
  }

  private static final class WorkOrderServiceFileDescriptorSupplier
      extends WorkOrderServiceBaseDescriptorSupplier {
    WorkOrderServiceFileDescriptorSupplier() {}
  }

  private static final class WorkOrderServiceMethodDescriptorSupplier
      extends WorkOrderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoMethodDescriptorSupplier {
    private final java.lang.String methodName;

    WorkOrderServiceMethodDescriptorSupplier(java.lang.String methodName) {
      this.methodName = methodName;
    }

    @java.lang.Override
    public com.google.protobuf.Descriptors.MethodDescriptor getMethodDescriptor() {
      return getServiceDescriptor().findMethodByName(methodName);
    }
  }

  private static volatile io.grpc.ServiceDescriptor serviceDescriptor;

  public static io.grpc.ServiceDescriptor getServiceDescriptor() {
    io.grpc.ServiceDescriptor result = serviceDescriptor;
    if (result == null) {
      synchronized (WorkOrderServiceGrpc.class) {
        result = serviceDescriptor;
        if (result == null) {
          serviceDescriptor = result = io.grpc.ServiceDescriptor.newBuilder(SERVICE_NAME)
              .setSchemaDescriptor(new WorkOrderServiceFileDescriptorSupplier())
              .addMethod(getWoWithRulesMethod())
              .build();
        }
      }
    }
    return result;
  }
}
