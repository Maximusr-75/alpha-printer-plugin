var exec = require('cordova/exec');

var POSPrinter = {
    print: function(ip, port, data) {
        return new Promise((resolve, reject) => {
            exec(resolve, reject, 'POSPrinter', 'print', [ip, port, data]);
        });
    },

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
