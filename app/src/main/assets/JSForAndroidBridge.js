(function (win) {
    var hasOwnProperty = Object.prototype.hasOwnProperty;
    var bridge = win.bridge || (win.bridge = {});
    var JSBRIDGE_PROTOCOL = 'bestvike';
    //js暴漏出来的可以供外部使用的方法
    var Inner = {
        callbacks: {},
        call: function (obj, method, params, callback) {
            console.log(obj+" "+method+" "+params+" "+callback);
            var port = Util.getPort();
            console.log(port);
            this.callbacks[port] = callback;
            var uri=Util.getUri(obj,method,params,port);
            console.log(uri);
            //window.prompt(uri, “”)将uri传递到native层，以次对应onJsPrompt方法中的message、defaultValue参数，
            //prompt方法中第一个参数可以传送约定好的特定方法标识，prompt方法中第二个参数可以传入对应的参数序列
           var flag= window.prompt(uri, "");
//           alert(JSON.stringify("调用成功"+flag));
            //如果只是简单的操作，可以直接传个特殊标示也行，不过我没有处理，这样不好，有js注入的漏洞，别改
            //window.prompt("标志flag", "");
        },


        //用于native回传的port值和执行结果
        onFinish: function (port, jsonObj){
            var callback = this.callbacks[port];
            callback && callback(jsonObj);
            delete this.callbacks[port];
        },
        //供java主动调用的方法
         callJs: function (flag, jsonObj){
        alert(JSON.stringify("java调用js成功"+flag+jsonObj));

       },
    };
    var Util = {
        getPort: function () {
            return Math.floor(Math.random() * (1 << 30));
        },
        getUri:function(obj, method, params, port){
            params = this.getParam(params);
            var uri = JSBRIDGE_PROTOCOL + '://' + obj + ':' + port + '/' + method + '?' + params;
            return uri;
        },
        getParam:function(obj){
            if (obj && typeof obj === 'object') {
            //stringify()用于从一个对象解析出字符串
                return JSON.stringify(obj);
            } else {
                return obj || '';
            }
        }
    };
    for (var key in Inner) {
        if (!hasOwnProperty.call(bridge, key)) {
            bridge[key] = Inner[key];
        }
    }
})(window);
