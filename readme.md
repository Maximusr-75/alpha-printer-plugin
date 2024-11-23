# Alpha Printer Plugin

A Node.js plugin for Alpha Anywhere to print to network POS printers using TSPL commands.

## Installation

```bash
npm install alpha-printer-plugin
```

## Usage

```javascript
const AlphaPrinter = require('alpha-printer-plugin');

// Create printer instance
const printer = new AlphaPrinter('192.168.1.210', 9100);

// Test connection
printer.testConnection()
    .then(() => console.log('Printer connected'))
    .catch(error => console.error('Connection failed:', error));

// Print content
const content = "Order #123\nProduct: Test Item\nPrice: $19.99";
const command = printer.formatTSPL(content);

printer.print(command)
    .then(result => console.log(result))
    .catch(error => console.error('Print failed:', error));
```

## Features
- Direct network printing
- TSPL command support
- Connection testing
- Promise-based API
- Error handling

## Requirements
- Node.js
- Network-enabled POS printer
- Printer must support TSPL commands

## License
MIT