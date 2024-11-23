var exec = require('cordova/exec');

var POSPrinter = {
    printData: function(ip, port, data) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, 'POSPrinterPlugin', 'printData', [ip, port, data]);
        });
    },

    testConnection: function(ip, port) {
        return new Promise(function(resolve, reject) {
            exec(resolve, reject, 'POSPrinterPlugin', 'testConnection', [ip, port]);
        });
    },

    // Helper function to format TSPL commands
    formatTSPL: function(content) {
        var command = "SIZE 80 mm, 60 mm\n" +
                     "GAP 3 mm, 0 mm\n" +
                     "DIRECTION 1\n" +
                     "CLS\n";
        
        var yPos = 10;
        content.split('\n').forEach(function(line) {
            if(line.trim()) {
                command += 'TEXT 10,' + yPos + ',"3",0,1,1,"' + line.trim() + '"\n';
                yPos += 25;
            }
        });
        
        return command + "PRINT 1\n";
    }
};

module.exports = POSPrinter;
