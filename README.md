# CCH-RPC
基于SPI机制的可扩展RPC框架
### Quick Start
//TODO
### 系统架构
//TODO
### CCH协议
协议头(120)
+ Magic(16): 0xcce3
+ MsgType(3)
  + 第一位
    + 0：请求
    + 1：响应
  + 后两位
    + 具体的消息类型
    + 为请求消息时
      + 00: RPC调用请求
    + 为响应消息时
      + 00: 请求成功响应
      + 01: 请求失败响应
+ SerializationId(5)
  + 00000: gson
+ RequestId(64)
+ BodyLength(32)

协议体(BodyLength)
+ Body
  + 如果为请求，则为请求体
  + 如果为响应，则为响应体
  + 根据MsgType的不同，Body有不同的协议
#### RPC调用请求体
//TODO
#### RPC调用响应体
//TODO
### Licence
//TODO

