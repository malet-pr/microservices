package org.acme.work_order.grpc;

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
  private static volatile io.grpc.MethodDescriptor<org.acme.work_order.grpc.WorkOrderRequest,
      org.acme.work_order.grpc.WorkOrderResponse> getRunRulesToWOMethod;

  @io.grpc.stub.annotations.RpcMethod(
      fullMethodName = SERVICE_NAME + '/' + "runRulesToWO",
      requestType = org.acme.work_order.grpc.WorkOrderRequest.class,
      responseType = org.acme.work_order.grpc.WorkOrderResponse.class,
      methodType = io.grpc.MethodDescriptor.MethodType.UNARY)
  public static io.grpc.MethodDescriptor<org.acme.work_order.grpc.WorkOrderRequest,
      org.acme.work_order.grpc.WorkOrderResponse> getRunRulesToWOMethod() {
    io.grpc.MethodDescriptor<org.acme.work_order.grpc.WorkOrderRequest, org.acme.work_order.grpc.WorkOrderResponse> getRunRulesToWOMethod;
    if ((getRunRulesToWOMethod = WorkOrderServiceGrpc.getRunRulesToWOMethod) == null) {
      synchronized (WorkOrderServiceGrpc.class) {
        if ((getRunRulesToWOMethod = WorkOrderServiceGrpc.getRunRulesToWOMethod) == null) {
          WorkOrderServiceGrpc.getRunRulesToWOMethod = getRunRulesToWOMethod =
              io.grpc.MethodDescriptor.<org.acme.work_order.grpc.WorkOrderRequest, org.acme.work_order.grpc.WorkOrderResponse>newBuilder()
              .setType(io.grpc.MethodDescriptor.MethodType.UNARY)
              .setFullMethodName(generateFullMethodName(SERVICE_NAME, "runRulesToWO"))
              .setSampledToLocalTracing(true)
              .setRequestMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.acme.work_order.grpc.WorkOrderRequest.getDefaultInstance()))
              .setResponseMarshaller(io.grpc.protobuf.ProtoUtils.marshaller(
                  org.acme.work_order.grpc.WorkOrderResponse.getDefaultInstance()))
              .setSchemaDescriptor(new WorkOrderServiceMethodDescriptorSupplier("runRulesToWO"))
              .build();
        }
      }
    }
    return getRunRulesToWOMethod;
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
    default void runRulesToWO(org.acme.work_order.grpc.WorkOrderRequest request,
        io.grpc.stub.StreamObserver<org.acme.work_order.grpc.WorkOrderResponse> responseObserver) {
      io.grpc.stub.ServerCalls.asyncUnimplementedUnaryCall(getRunRulesToWOMethod(), responseObserver);
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
    public void runRulesToWO(org.acme.work_order.grpc.WorkOrderRequest request,
        io.grpc.stub.StreamObserver<org.acme.work_order.grpc.WorkOrderResponse> responseObserver) {
      io.grpc.stub.ClientCalls.asyncUnaryCall(
          getChannel().newCall(getRunRulesToWOMethod(), getCallOptions()), request, responseObserver);
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
    public org.acme.work_order.grpc.WorkOrderResponse runRulesToWO(org.acme.work_order.grpc.WorkOrderRequest request) {
      return io.grpc.stub.ClientCalls.blockingUnaryCall(
          getChannel(), getRunRulesToWOMethod(), getCallOptions(), request);
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
    public com.google.common.util.concurrent.ListenableFuture<org.acme.work_order.grpc.WorkOrderResponse> runRulesToWO(
        org.acme.work_order.grpc.WorkOrderRequest request) {
      return io.grpc.stub.ClientCalls.futureUnaryCall(
          getChannel().newCall(getRunRulesToWOMethod(), getCallOptions()), request);
    }
  }

  private static final int METHODID_RUN_RULES_TO_WO = 0;

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
        case METHODID_RUN_RULES_TO_WO:
          serviceImpl.runRulesToWO((org.acme.work_order.grpc.WorkOrderRequest) request,
              (io.grpc.stub.StreamObserver<org.acme.work_order.grpc.WorkOrderResponse>) responseObserver);
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
          getRunRulesToWOMethod(),
          io.grpc.stub.ServerCalls.asyncUnaryCall(
            new MethodHandlers<
              org.acme.work_order.grpc.WorkOrderRequest,
              org.acme.work_order.grpc.WorkOrderResponse>(
                service, METHODID_RUN_RULES_TO_WO)))
        .build();
  }

  private static abstract class WorkOrderServiceBaseDescriptorSupplier
      implements io.grpc.protobuf.ProtoFileDescriptorSupplier, io.grpc.protobuf.ProtoServiceDescriptorSupplier {
    WorkOrderServiceBaseDescriptorSupplier() {}

    @java.lang.Override
    public com.google.protobuf.Descriptors.FileDescriptor getFileDescriptor() {
      return org.acme.work_order.grpc.WorkOrderData.getDescriptor();
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
              .addMethod(getRunRulesToWOMethod())
              .build();
        }
      }
    }
    return result;
  }
}
