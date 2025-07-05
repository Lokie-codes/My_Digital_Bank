package com.bank.account.grpc;

import com.bank.account.service.AccountService;
import com.bank.account.model.Statement;
import com.bank.account.grpc.AccountProto.*;
import com.bank.account.grpc.AccountServiceGrpc.AccountServiceImplBase;
import io.grpc.stub.StreamObserver;
import org.lognet.springboot.grpc.GRpcService;

import java.util.List;

@GRpcService
public class AccountServiceGrpcImpl extends AccountServiceImplBase {
  private final AccountService svc;

  public AccountServiceGrpcImpl(AccountService svc) {
    this.svc = svc;
  }

  @Override
  public void getBalance(GetBalanceRequest req, StreamObserver<GetBalanceResponse> obs) {
    double bal = svc.getBalance(req.getAccountId());
    GetBalanceResponse res = GetBalanceResponse.newBuilder()
      .setAccountId(req.getAccountId())
      .setBalance(bal)
      .build();
    obs.onNext(res);
    obs.onCompleted();
  }

  @Override
  public void updateBalance(UpdateBalanceRequest req, StreamObserver<UpdateBalanceResponse> obs) {
    double newBal = svc.updateBalance(
      req.getAccountId(),
      req.getAmount(),
      req.getType()
    );
    UpdateBalanceResponse res = UpdateBalanceResponse.newBuilder()
      .setAccountId(req.getAccountId())
      .setNewBalance(newBal)
      .build();
    obs.onNext(res);
    obs.onCompleted();
  }

  @Override
  public void listStatements(ListStatementsRequest req, StreamObserver<ListStatementsResponse> obs) {
    List<Statement> stmts = svc.listStatements(req.getAccountId());
    ListStatementsResponse.Builder b = ListStatementsResponse.newBuilder();
    for (Statement s : stmts) {
      b.addStatements(Statement.newBuilder()
        .setId(s.getId().toString())
        .setType(s.getType())
        .setAmount(s.getAmount())
        .setTimestamp(s.getTimestamp().toEpochMilli())
        .build());
    }
    obs.onNext(b.build());
    obs.onCompleted();
  }
}
