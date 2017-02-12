
const path = require('path');

module.exports = {
    entry: './examples/example.js',

    output: {
        filename: 'bundle.js',
        path: path.resolve('dist')
    }
};