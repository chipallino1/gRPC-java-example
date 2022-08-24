package com.grpc.example;

import io.grpc.stub.StreamObserver;

import java.util.ArrayList;
import java.util.List;

public class CustomerServiceImpl extends CustomerServiceGrpc.CustomerServiceImplBase {
    private static final List<Customer> CUSTOMERS = new ArrayList<>();

    public CustomerServiceImpl() {
        Customer customer1 = Customer.newBuilder()
                .setId(Id.newBuilder().setId("1"))
                .setFirstName(FirstName.newBuilder().setFirstName("Egor"))
                .setLastName(LastName.newBuilder().setLastName("Skorupich"))
                .setAddress("Home address")
                .setPhone("2342354524")
                .setAge(23).build();
        Customer customer2 = Customer.newBuilder()
                .setId(Id.newBuilder().setId("2"))
                .setFirstName(FirstName.newBuilder().setFirstName("Egor"))
                .setLastName(LastName.newBuilder().setLastName("Krukovich"))
                .setAddress("Home address")
                .setPhone("2wewer4")
                .setAge(21).build();
        Customer customer3 = Customer.newBuilder()
                .setId(Id.newBuilder().setId("3"))
                .setFirstName(FirstName.newBuilder().setFirstName("Vanya"))
                .setLastName(LastName.newBuilder().setLastName("Skorupich"))
                .setAddress("Home address")
                .setPhone("2wewer4")
                .setAge(17).build();
        Customer customer4 = Customer.newBuilder()
                .setId(Id.newBuilder().setId("4"))
                .setFirstName(FirstName.newBuilder().setFirstName("Vanya"))
                .setLastName(LastName.newBuilder().setLastName("Skorupich"))
                .setAddress("Home address")
                .setPhone("2wewer4")
                .setAge(17).build();
        Customer customer5 = Customer.newBuilder()
                .setId(Id.newBuilder().setId("5"))
                .setFirstName(FirstName.newBuilder().setFirstName("Darya"))
                .setLastName(LastName.newBuilder().setLastName("Krukovich"))
                .setAddress("Home address")
                .setPhone("2wewer4")
                .setAge(21).build();
        CUSTOMERS.add(customer1);
        CUSTOMERS.add(customer2);
        CUSTOMERS.add(customer3);
        CUSTOMERS.add(customer4);
        CUSTOMERS.add(customer5);
    }

    @Override
    public void getCustomerById(Id request, StreamObserver<Customer> responseObserver) {
        for (Customer customer : CUSTOMERS) {
            if (customer.getId().getId().equals(request.getId())) {
                responseObserver.onNext(customer);
                responseObserver.onCompleted();
                break;
            }
        }
    }

    @Override
    public void getCustomersByFirstName(FirstName request, StreamObserver<Customer> responseObserver) {
        for (Customer customer : CUSTOMERS) {
            if (customer.getFirstName().getFirstName().equals(request.getFirstName())) {
                responseObserver.onNext(customer);
            }
        }
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<Id> getCustomersByIds(final StreamObserver<Customer> responseObserver) {
        return new StreamObserver<Id>() {
            @Override
            public void onNext(Id id) {
                for (Customer customer : CUSTOMERS) {
                    if (customer.getId().getId().equals(id.getId())) {
                        responseObserver.onNext(customer);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error occurred: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onCompleted();
            }
        };
    }

    @Override
    public void createCustomer(Customer request, StreamObserver<Customer> responseObserver) {
        CUSTOMERS.add(request);
        responseObserver.onNext(request);
        responseObserver.onCompleted();
    }

    @Override
    public StreamObserver<LastName> getCustomersByLastName(final StreamObserver<CustomersResponse> responseObserver) {
        return new StreamObserver<LastName>() {
            private final CustomersResponse.Builder customersResponseBuilder = CustomersResponse.newBuilder();

            @Override
            public void onNext(LastName lastName) {
                for (Customer customer : CUSTOMERS) {
                    if (customer.getLastName().getLastName().equals(lastName.getLastName())) {
                        customersResponseBuilder.addCustomers(customer);
                    }
                }
            }

            @Override
            public void onError(Throwable throwable) {
                System.out.println("Error occurred: " + throwable.getMessage());
            }

            @Override
            public void onCompleted() {
                responseObserver.onNext(customersResponseBuilder.build());
            }
        };
    }
}
