# cch-rpc
基于SPI机制的可扩展RPC框架

### 通信协议
协议头(120)
+ Magic(16): cch3
+ MsgType(3)
  + 第一位
    + 0：请求
    + 1：响应
  + 后两位
    + 请求
      + 00: RPC调用
      + 01: 
      + 10: 
      + 11: 非RPC调用
    + 响应
      + 00: 请求成功
      + 01: 请求出错
      + 10: 
      + 11: 非RPC调用
+ SerializationId(5)
+ RequestId(64)
+ DataLength(32)

协议体(DataLength)
+ RequestBody/ResponseBody
  + 如果为请求，则为请求体
  + 如果为响应，则为响应体
