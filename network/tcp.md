## tcp_tw_recycle和tcp_timestamp的问题

# 关于一次 staging 服务器上开启了 `net.ipv4.tcp_tw_recycle` 导致部分机器访问不了的原因：

在 **NAT** 网络下所以客户端的 IP 是一样的，在客户端和服务端都开启了 `net.ipv4.tcp_timestamps` 参数情况下，客户端发送的 SYN IP 包中会携带 `TSval` `TSecr` 参数表示发送数据包的时间，server 端的这个 connection (五元组)在进入 TIME_WAIT 时会记录 IP 和时间戳信息到 peer 中，这时处在 NAT 网络下的另一台客户端(src IP相同)访问 server，server 会根据 IP 和路由信息找到对应的 peer 再比较这个新连接的时间戳 与 peer 中保存的时间戳，如果新连接的到达（即收到 SYN）的时间减去保存的连接时间小于 `TCP_PAWS_MSL` (60s) 时间 (`(u32)get_seconds() - peer->tcp_ts_stamp < TCP_PAWS_MSL`) 同时 peer 中的最近通讯时间减去新的连接的 TSval 时间大于 `TCP_PAWS_WINDOW`(1s) 的时间 (`(s32)(peer->tcp_ts - req->ts_recent) > TCP_PAWS_WINDOW`) ，即 **60s 内收到了非递增的数据包**，就会丢弃数据包释放相关内存，对于客户段来说就是无响应。

# Reference

- https://hustcat.github.io/tcp_tw_recycle-and-tcp_timestamp/

# RFC

- [TCP Extensions for High Performance](https://www.ietf.org/rfc/rfc1323.txt)
- [TRANSMISSION CONTROL PROTOCOL](https://www.rfc-editor.org/rfc/rfc793.txt)
- [RFC 1122 - Requirements for Internet Hosts - Communication Layers](https://tools.ietf.org/html/rfc1122)
- [Congestion Control in IP/TCP Internetworks](https://tools.ietf.org/html/rfc896)
