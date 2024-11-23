var exec = require('cordova/exec');

var PrinterPlugin = {
    printData: function(ip, port, data, success, error) {
        exec(success, error, 'PrinterPlugin', 'printData', [ip, port, data]);
    },
    
    testPrinter: function(ip, port, success, error) {
        exec(success, error, 'PrinterPlugin', 'testPrinter', [ip, port]);
    }
};

module.exports = PrinterPlugin;
