convertToObject = function (e) {
    var t = new DataView(e)
        , n = {
        body: []
    };
    if (n.packetLen = t.getInt32(i.a.WS_PACKAGE_OFFSET),
        this.wsBinaryHeaderList.forEach((function (e) {
                4 === e.bytes ? n[e.key] = t.getInt32(e.offset) : 2 === e.bytes && (n[e.key] = t.getInt16(e.offset))
            }
        )),
    n.packetLen < e.byteLength && this.convertToObject(e.slice(0, n.packetLen)),
    this.decoder || (this.decoder = o.a.getDecoder()),
    !n.op || i.a.WS_OP_MESSAGE !== n.op && n.op !== i.a.WS_OP_CONNECT_SUCCESS)
        n.op && i.a.WS_OP_HEARTBEAT_REPLY === n.op && (n.body = {
            count: t.getInt32(i.a.WS_PACKAGE_HEADER_TOTAL_LENGTH)
        });
    else
        for (var r = i.a.WS_PACKAGE_OFFSET, s = n.packetLen, l = "", c = ""; r < e.byteLength; r += s) {
            s = t.getInt32(r),
                l = t.getInt16(r + i.a.WS_HEADER_OFFSET);
            try {
                if (n.ver === i.a.WS_BODY_PROTOCOL_VERSION_DEFLATE) {
                    var u = e.slice(r + l, r + s)
                        , d = a(new Uint8Array(u));
                    c = this.convertToObject(d.buffer).body
                } else {
                    var h = this.decoder.decode(e.slice(r + l, r + s));
                    c = 0 !== h.length ? JSON.parse(h) : null
                }
                c && n.body.push(c)
            } catch (t) {
                console.error("decode body error:", new Uint8Array(e), n, t)
            }
        }
    return n
}