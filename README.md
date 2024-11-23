# Alpha Anywhere POS Printer Plugin

A Cordova plugin for Alpha Anywhere to print to network POS printers using TSPL commands.

## Installation

```bash
cordova plugin add https://github.com/yourusername/alpha-printer-plugin.git
```

## Usage in Alpha Anywhere

```javascript
// Print content
function printContent(divId) {
    var content = document.getElementById(divId).innerText;
    var formattedData = POSPrinter.formatTSPL(content);
    
    POSPrinter.printData("192.168.1.210", 9100, formattedData)
        .then(function(result) {
            console.log("Print successful");
        })
        .catch(function(error) {
            console.error("Print failed:", error);
        });
}

// Test printer connection
function testPrinter() {
    POSPrinter.testConnection("192.168.1.210", 9100)
        .then(function(result) {
            alert("Printer connected!");
        })
        .catch(function(error) {
            alert("Printer connection failed: " + error);
        });
}

// HTML Usage:
// <button onclick="printContent('printDiv')">Print</button>
// <button onclick="testPrinter()">Test Connection</button>
// <div id="printDiv">Content to print</div>
```

## Features
- Direct network printing
- TSPL command support
- Connection testing
- Promise-based API
- Error handling

## Requirements
- Android 5.0 or higher
- Network-enabled POS printer
- Printer must support TSPL commands

## License
MIT
