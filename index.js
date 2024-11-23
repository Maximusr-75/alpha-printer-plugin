class AlphaPrinter {
    constructor(ip = '192.168.1.210', port = 9100) {
        this.ip = ip;
        this.port = port;
    }

    print(data) {
        return new Promise((resolve, reject) => {
            try {
                const client = new net.Socket();
                
                client.connect(this.port, this.ip, () => {
                    client.write(data);
                    client.end();
                    resolve('Print successful');
                });

                client.on('error', (error) => {
                    reject('Print failed: ' + error.message);
                });
            } catch (error) {
                reject('Print error: ' + error.message);
            }
        });
    }

    formatTSPL(content) {
        let command = "SIZE 80 mm, 60 mm\n" +
                     "GAP 3 mm, 0 mm\n" +
                     "DIRECTION 1\n" +
                     "CLS\n";
        
        let yPos = 10;
        content.split('\n').forEach(line => {
            if(line.trim()) {
                command += `TEXT 10,${yPos},"3",0,1,1,"${line.trim()}"\n`;
                yPos += 25;
            }
        });
        
        return command + "PRINT 1\n";
    }

    testConnection() {
        return new Promise((resolve, reject) => {
            try {
                const client = new net.Socket();
                client.connect(this.port, this.ip, () => {
                    client.end();
                    resolve('Printer connected');
                });

                client.on('error', () => {
                    reject('Printer connection failed');
                });
            } catch (error) {
                reject('Connection error: ' + error.message);
            }
        });
    }
}

module.exports = AlphaPrinter;