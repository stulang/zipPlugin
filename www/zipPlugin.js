var exec = require('cordova/exec');
var zipPlugin   = {
  unzip:function (arg0, success, error) {
    exec(success, error, 'zipPlugin', 'unzip', [arg0]);
	}
};
module.exports = zipPlugin;