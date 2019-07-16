/* package jsPackage {
class jsCommon { */
var charSize = 8;
var completeExpression = 0;
var _path = ["scrAvailWidth", "scrAvailHeight"];
//var err = error;

var blackList = ["scrDeviceXDPI", "scrColorDepth", "scrWidth", "scrHeight"];
var groupedSelectors = {
	browserLanguage: "q4f3",
	hasLiedResolution: "3neK",
	browserName: "-UVA",
	localStorage: "XM7l",
	touchSupport: "wNLf",
	mimeTypes: "jp76",
	scrDeviceXDPI: "3jCe",
	os: "hAqN",
	userAgent: "0aew",
	webSmartID: "E3gR",
	online: "9vyE",
	hasLiedLanguages: "j5po",
	appMinorVersion: "qBVW",
	openDatabase: "V8vl",
	historyList: "kU5z",
	systemLanguage: "e6OK",
	srcScreenSize: "tOHY",
	scrHeight: "5Jwy",
	browserVersion: "d435",
	hasLiedBrowser: "2xC5",
	indexedDb: "3sw-",
	scrAvailHeight: "88tV",
	plugins: "ks0Q",
	scrAvailSize: "TeRS",
	scrAvailWidth: "E-lJ",
	hasLiedOs: "ci5c",
	userLanguage: "hLzX",
	jsFonts: "EOQP",
	cpuClass: "Md7A",
	flashVersion: "dzuS",
	javaEnabled: "yD16",
	localCode: "lEnu",
	adblock: "FMQw",
	timeZone: "q5aJ",
	scrWidth: "ssI5",
	appcodeName: "qT7b",
	cookieCode: "VySQ",
	cookieEnabled: "VPIf",
	sessionStorage: "HVia",
	doNotTrack: "VEek",
	storeDb: "Fvje",
	scrColorDepth: "qmyu"
};

function x64LeftShift(dataAndEvents, opt_attributes) {
	opt_attributes %= 64;
	return 0 === opt_attributes ? dataAndEvents : 32 > opt_attributes ? [dataAndEvents[0] << opt_attributes | dataAndEvents[1] >>> 32 - opt_attributes, dataAndEvents[1] << opt_attributes] : [dataAndEvents[1] << opt_attributes - 32, 0];
}

function x64Xor(deepDataAndEvents, dataAndEvents) {
	return [deepDataAndEvents[0] ^ dataAndEvents[0], deepDataAndEvents[1] ^ dataAndEvents[1]];
}

function x64Multiply(deepDataAndEvents, opt_attributes) {
	/** @type {Array} */
	deepDataAndEvents = [deepDataAndEvents[0] >>> 16, deepDataAndEvents[0] & 65535, deepDataAndEvents[1] >>> 16, deepDataAndEvents[1] & 65535];
	/** @type {Array} */
	opt_attributes = [opt_attributes[0] >>> 16, opt_attributes[0] & 65535, opt_attributes[1] >>> 16, opt_attributes[1] & 65535];
	/** @type {Array} */
	var c = [0, 0, 0, 0];
	c[3] += deepDataAndEvents[3] * opt_attributes[3];
	c[2] += c[3] >>> 16;
	c[3] &= 65535;
	c[2] += deepDataAndEvents[2] * opt_attributes[3];
	c[1] += c[2] >>> 16;
	c[2] &= 65535;
	c[2] += deepDataAndEvents[3] * opt_attributes[2];
	c[1] += c[2] >>> 16;
	c[2] &= 65535;
	c[1] += deepDataAndEvents[1] * opt_attributes[3];
	c[0] += c[1] >>> 16;
	c[1] &= 65535;
	c[1] += deepDataAndEvents[2] * opt_attributes[2];
	c[0] += c[1] >>> 16;
	c[1] &= 65535;
	c[1] += deepDataAndEvents[3] * opt_attributes[1];
	c[0] += c[1] >>> 16;
	c[1] &= 65535;
	c[0] += deepDataAndEvents[0] * opt_attributes[3] + deepDataAndEvents[1] * opt_attributes[2] + deepDataAndEvents[2] * opt_attributes[1] + deepDataAndEvents[3] * opt_attributes[0];
	c[0] &= 65535;
	return [c[0] << 16 | c[1], c[2] << 16 | c[3]];
}

function x64Rotl(dataAndEvents, opt_attributes) {
	opt_attributes %= 64;
	if (32 === opt_attributes) {
		return [dataAndEvents[1], dataAndEvents[0]];
	}
	if (32 > opt_attributes) {
		return [dataAndEvents[0] << opt_attributes | dataAndEvents[1] >>> 32 - opt_attributes, dataAndEvents[1] << opt_attributes | dataAndEvents[0] >>> 32 - opt_attributes];
	}
	opt_attributes -= 32;
	return [dataAndEvents[1] << opt_attributes | dataAndEvents[0] >>> 32 - opt_attributes, dataAndEvents[0] << opt_attributes | dataAndEvents[1] >>> 32 - opt_attributes];
}

function x64Add(deepDataAndEvents, opt_attributes) {
	/** @type {Array} */
	deepDataAndEvents = [deepDataAndEvents[0] >>> 16, deepDataAndEvents[0] & 65535, deepDataAndEvents[1] >>> 16, deepDataAndEvents[1] & 65535];
	/** @type {Array} */
	opt_attributes = [opt_attributes[0] >>> 16, opt_attributes[0] & 65535, opt_attributes[1] >>> 16, opt_attributes[1] & 65535];
	/** @type {Array} */
	var c = [0, 0, 0, 0];
	c[3] += deepDataAndEvents[3] + opt_attributes[3];
	c[2] += c[3] >>> 16;
	c[3] &= 65535;
	c[2] += deepDataAndEvents[2] + opt_attributes[2];
	c[1] += c[2] >>> 16;
	c[2] &= 65535;
	c[1] += deepDataAndEvents[1] + opt_attributes[1];
	c[0] += c[1] >>> 16;
	c[1] &= 65535;
	c[0] += deepDataAndEvents[0] + opt_attributes[0];
	c[0] &= 65535;
	return [c[0] << 16 | c[1], c[2] << 16 | c[3]];
}

function x64Fmix(deepDataAndEvents) {
	deepDataAndEvents = this.x64Xor(deepDataAndEvents, [0, deepDataAndEvents[0] >>> 1]);
	deepDataAndEvents = this.x64Multiply(deepDataAndEvents, [4283543511, 3981806797]);
	deepDataAndEvents = this.x64Xor(deepDataAndEvents, [0, deepDataAndEvents[0] >>> 1]);
	deepDataAndEvents = this.x64Multiply(deepDataAndEvents, [3301882366, 444984403]);
	return deepDataAndEvents = this.x64Xor(deepDataAndEvents, [0, deepDataAndEvents[0] >>> 1]);
}

function x64hash128(input, opt_attributes) {
	input = input || "";
	opt_attributes = opt_attributes || 0;
	/** @type {number} */
	var max = input.length % 16;
	/** @type {number} */
	var n = input.length - max;
	/** @type {Array} */
	var fragment = [0, opt_attributes];
	/** @type {Array} */
	var deepDataAndEvents = [0, opt_attributes];
	var dataAndEvents;
	var node;
	/** @type {Array} */
	var attributes = [2277735313, 289559509];
	/** @type {Array} */
	var preNode = [1291169091, 658871167];
	/** @type {number} */
	var i = 0;
	for (; i < n; i += 16) {
		/** @type {Array} */
		dataAndEvents = [input.charCodeAt(i + 4) & 255 | (input.charCodeAt(i + 5) & 255) << 8 | (input.charCodeAt(i + 6) & 255) << 16 | (input.charCodeAt(i + 7) & 255) << 24, input.charCodeAt(i) & 255 | (input.charCodeAt(i + 1) & 255) << 8 | (input.charCodeAt(i + 2) & 255) << 16 | (input.charCodeAt(i + 3) & 255) << 24];
		/** @type {Array} */
		node = [input.charCodeAt(i + 12) & 255 | (input.charCodeAt(i + 13) & 255) << 8 | (input.charCodeAt(i + 14) & 255) << 16 | (input.charCodeAt(i + 15) & 255) << 24, input.charCodeAt(i + 8) & 255 | (input.charCodeAt(i + 9) & 255) << 8 | (input.charCodeAt(i + 10) & 255) << 16 | (input.charCodeAt(i + 11) & 255) << 24];
		dataAndEvents = this.x64Multiply(dataAndEvents, attributes);
		dataAndEvents = this.x64Rotl(dataAndEvents, 31);
		dataAndEvents = this.x64Multiply(dataAndEvents, preNode);
		fragment = this.x64Xor(fragment, dataAndEvents);
		fragment = this.x64Rotl(fragment, 27);
		fragment = this.x64Add(fragment, deepDataAndEvents);
		fragment = this.x64Add(this.x64Multiply(fragment, [0, 5]), [0, 1390208809]);
		node = this.x64Multiply(node, preNode);
		node = this.x64Rotl(node, 33);
		node = this.x64Multiply(node, attributes);
		deepDataAndEvents = this.x64Xor(deepDataAndEvents, node);
		deepDataAndEvents = this.x64Rotl(deepDataAndEvents, 31);
		deepDataAndEvents = this.x64Add(deepDataAndEvents, fragment);
		deepDataAndEvents = this.x64Add(this.x64Multiply(deepDataAndEvents, [0, 5]), [0, 944331445]);
	}
	/** @type {Array} */
	dataAndEvents = [0, 0];
	/** @type {Array} */
	node = [0, 0];
	switch (max) {
	case 15:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 14)], 48));
	case 14:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 13)], 40));
	case 13:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 12)], 32));
	case 12:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 11)], 24));
	case 11:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 10)], 16));
	case 10:
		node = this.x64Xor(node, this.x64LeftShift([0, input.charCodeAt(i + 9)], 8));
	case 9:
		node = this.x64Xor(node, [0, input.charCodeAt(i + 8)]);
		node = this.x64Multiply(node, preNode);
		node = this.x64Rotl(node, 33);
		node = this.x64Multiply(node, attributes);
		deepDataAndEvents = this.x64Xor(deepDataAndEvents, node);
	case 8:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 7)], 56));
	case 7:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 6)], 48));
	case 6:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 5)], 40));
	case 5:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 4)], 32));
	case 4:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 3)], 24));
	case 3:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 2)], 16));
	case 2:
		dataAndEvents = this.x64Xor(dataAndEvents, this.x64LeftShift([0, input.charCodeAt(i + 1)], 8));
	case 1:
		dataAndEvents = this.x64Xor(dataAndEvents, [0, input.charCodeAt(i)]);
		dataAndEvents = this.x64Multiply(dataAndEvents, attributes);
		dataAndEvents = this.x64Rotl(dataAndEvents, 31);
		dataAndEvents = this.x64Multiply(dataAndEvents, preNode);
		fragment = this.x64Xor(fragment, dataAndEvents);
	}
	fragment = this.x64Xor(fragment, [0, input.length]);
	deepDataAndEvents = this.x64Xor(deepDataAndEvents, [0, input.length]);
	fragment = this.x64Add(fragment, deepDataAndEvents);
	deepDataAndEvents = this.x64Add(deepDataAndEvents, fragment);
	fragment = this.x64Fmix(fragment);
	deepDataAndEvents = this.x64Fmix(deepDataAndEvents);
	fragment = this.x64Add(fragment, deepDataAndEvents);
	deepDataAndEvents = this.x64Add(deepDataAndEvents, fragment);
	return ("00000000" + (fragment[0] >>> 0).toString(16)).slice(-8) + ("00000000" + (fragment[1] >>> 0).toString(16)).slice(-8) + ("00000000" + (deepDataAndEvents[0] >>> 0).toString(16)).slice(-8) + ("00000000" + (deepDataAndEvents[1] >>> 0).toString(16)).slice(-8);
}

function callback(input, string, label, data, k, opt_attributes, replacementHash) {
	return escape(createDom(escape(escape(input, string & label | ~string & data), escape(k, replacementHash)), opt_attributes), string);
}

function createDom(type, opt_attributes) {
	return type << opt_attributes | type >>> 32 - opt_attributes;
}

function escape(string, object) {
	/** @type {number} */
	var c = (string & 65535) + (object & 65535);
	return (string >> 16) + (object >> 16) + (c >> 16) << 16 | c & 65535;
}

function trim(string, str, text, s, v, opt_attributes, replacementHash) {
	return escape(createDom(escape(escape(string, str & s | text & ~s), escape(v, replacementHash)), opt_attributes), str);
}

function assertEquals(string, a, input, callback, message, replacementHash, opt_attributes) {
	return escape(createDom(escape(escape(string, input ^ (a | ~callback)), escape(message, opt_attributes)), replacementHash), a);
}

function String(string, type, name, tag, k, opt_attributes, replacementHash) {
	return escape(createDom(escape(escape(string, type ^ name ^ tag), escape(k, replacementHash)), opt_attributes), type);
}

function format(string) {
	/** @type {Array} */
	var codeSegments = [];
	/** @type {number} */

	var source = (1 << charSize) - 1;
	/** @type {number} */
	var value = 0;
	for (; value < string.length * charSize; value += charSize) {
		codeSegments[value >> 5] |= (string.charCodeAt(value / charSize) & source) << value % 32;
	}
	/** @type {number} */
	string = string.length * charSize;
	codeSegments[string >> 5] |= 128 << string % 32;
	/** @type {number} */
	codeSegments[(string + 64 >>> 9 << 4) + 14] = string;
	/** @type {number} */
	string = 1732584193;
	/** @type {number} */
	source = -271733879;
	/** @type {number} */
	value = -1732584194;
	/** @type {number} */
	var text = 271733878;
	/** @type {number} */
	var i = 0;
	for (; i < codeSegments.length; i += 16) {
		/** @type {number} */
		var variables = string;
		var which = source;
		var method = value;
		var result = text;
		string = callback(string, source, value, text, codeSegments[i + 0], 7, -680876936);
		text = callback(text, string, source, value, codeSegments[i + 1], 12, -389564586);
		value = callback(value, text, string, source, codeSegments[i + 2], 17, 606105819);
		source = callback(source, value, text, string, codeSegments[i + 3], 22, -1044525330);
		string = callback(string, source, value, text, codeSegments[i + 4], 7, -176418897);
		text = callback(text, string, source, value, codeSegments[i + 5], 12, 1200080426);
		value = callback(value, text, string, source, codeSegments[i + 6], 17, -1473231341);
		source = callback(source, value, text, string, codeSegments[i + 7], 22, -45705983);
		string = callback(string, source, value, text, codeSegments[i + 8], 7, 1770035416);
		text = callback(text, string, source, value, codeSegments[i + 9], 12, -1958414417);
		value = callback(value, text, string, source, codeSegments[i + 10], 17, -42063);
		source = callback(source, value, text, string, codeSegments[i + 11], 22, -1990404162);
		string = callback(string, source, value, text, codeSegments[i + 12], 7, 1804603682);
		text = callback(text, string, source, value, codeSegments[i + 13], 12, -40341101);
		value = callback(value, text, string, source, codeSegments[i + 14], 17, -1502002290);
		source = callback(source, value, text, string, codeSegments[i + 15], 22, 1236535329);
		string = trim(string, source, value, text, codeSegments[i + 1], 5, -165796510);
		text = trim(text, string, source, value, codeSegments[i + 6], 9, -1069501632);
		value = trim(value, text, string, source, codeSegments[i + 11], 14, 643717713);
		source = trim(source, value, text, string, codeSegments[i + 0], 20, -373897302);
		string = trim(string, source, value, text, codeSegments[i + 5], 5, -701558691);
		text = trim(text, string, source, value, codeSegments[i + 10], 9, 38016083);
		value = trim(value, text, string, source, codeSegments[i + 15], 14, -660478335);
		source = trim(source, value, text, string, codeSegments[i + 4], 20, -405537848);
		string = trim(string, source, value, text, codeSegments[i + 9], 5, 568446438);
		text = trim(text, string, source, value, codeSegments[i + 14], 9, -1019803690);
		value = trim(value, text, string, source, codeSegments[i + 3], 14, -187363961);
		source = trim(source, value, text, string, codeSegments[i + 8], 20, 1163531501);
		string = trim(string, source, value, text, codeSegments[i + 13], 5, -1444681467);
		text = trim(text, string, source, value, codeSegments[i + 2], 9, -51403784);
		value = trim(value, text, string, source, codeSegments[i + 7], 14, 1735328473);
		source = trim(source, value, text, string, codeSegments[i + 12], 20, -1926607734);
		string = String(string, source, value, text, codeSegments[i + 5], 4, -378558);
		text = String(text, string, source, value, codeSegments[i + 8], 11, -2022574463);
		value = String(value, text, string, source, codeSegments[i + 11], 16, 1839030562);
		source = String(source, value, text, string, codeSegments[i + 14], 23, -35309556);
		string = String(string, source, value, text, codeSegments[i + 1], 4, -1530992060);
		text = String(text, string, source, value, codeSegments[i + 4], 11, 1272893353);
		value = String(value, text, string, source, codeSegments[i + 7], 16, -155497632);
		source = String(source, value, text, string, codeSegments[i + 10], 23, -1094730640);
		string = String(string, source, value, text, codeSegments[i + 13], 4, 681279174);
		text = String(text, string, source, value, codeSegments[i + 0], 11, -358537222);
		value = String(value, text, string, source, codeSegments[i + 3], 16, -722521979);
		source = String(source, value, text, string, codeSegments[i + 6], 23, 76029189);
		string = String(string, source, value, text, codeSegments[i + 9], 4, -640364487);
		text = String(text, string, source, value, codeSegments[i + 12], 11, -421815835);
		value = String(value, text, string, source, codeSegments[i + 15], 16, 530742520);
		source = String(source, value, text, string, codeSegments[i + 2], 23, -995338651);
		string = assertEquals(string, source, value, text, codeSegments[i + 0], 6, -198630844);
		text = assertEquals(text, string, source, value, codeSegments[i + 7], 10, 1126891415);
		value = assertEquals(value, text, string, source, codeSegments[i + 14], 15, -1416354905);
		source = assertEquals(source, value, text, string, codeSegments[i + 5], 21, -57434055);
		string = assertEquals(string, source, value, text, codeSegments[i + 12], 6, 1700485571);
		text = assertEquals(text, string, source, value, codeSegments[i + 3], 10, -1894986606);
		value = assertEquals(value, text, string, source, codeSegments[i + 10], 15, -1051523);
		source = assertEquals(source, value, text, string, codeSegments[i + 1], 21, -2054922799);
		string = assertEquals(string, source, value, text, codeSegments[i + 8], 6, 1873313359);
		text = assertEquals(text, string, source, value, codeSegments[i + 15], 10, -30611744);
		value = assertEquals(value, text, string, source, codeSegments[i + 6], 15, -1560198380);
		source = assertEquals(source, value, text, string, codeSegments[i + 13], 21, 1309151649);
		string = assertEquals(string, source, value, text, codeSegments[i + 4], 6, -145523070);
		text = assertEquals(text, string, source, value, codeSegments[i + 11], 10, -1120210379);
		value = assertEquals(value, text, string, source, codeSegments[i + 2], 15, 718787259);
		source = assertEquals(source, value, text, string, codeSegments[i + 9], 21, -343485551);
		string = escape(string, variables);
		source = escape(source, which);
		value = escape(value, method);
		text = escape(text, result);
	}
	/** @type {Array} */
	codeSegments = [string, source, value, text];
	/** @type {string} */
	string = completeExpression ? "0123456789ABCDEF" : "0123456789abcdef";
	/** @type {string} */
	source = "";
	/** @type {number} */
	value = 0;
	for (; value < 4 * codeSegments.length; value++) {
		source += string.charAt(codeSegments[value >> 2] >> value % 4 * 8 + 4 & 15) + string.charAt(codeSegments[value >> 2] >> value % 4 * 8 & 15);
	}
	return source;
}

function KV(key, val) {
	var kv = new Object;
	kv.key = key;
	kv.value = val;
	return kv;
}

function each(obj, iterator, context) {
	if (null !== obj) {
		if (obj.length === +obj.length) {
			/** @type {number} */
			var i = 0;
			var l = obj.length;
			for (; i < l && iterator.call(context, obj[i], i, obj) !== {}; i++) {}
		} else {
			for (i in obj) {
				if (obj.hasOwnProperty(i) && iterator.call(context, obj[i], i, obj) === {}) {
					break;
				}
			}
		}
	}
}

function getMimeTypes() {
	var hex = "application/pdf#application/x-google-chrome-pdf#application/x-nacl#application/x-pnacl#application/x-ppapi-widevine-cdm#";
	return KV("mimeTypes", format(hex.substr(0, hex.length - 1)));
}
function getWebSmartID(paramsArray) {
	var out = [];
	each(paramsArray, function (v) {
		var copies = v.value;
		if ("undefined" !== typeof v.value.join) {
			copies = v.value.join(";");
		}
		out.push(copies);
	}, null);
	return x64hash128(out.join("~~~"), 31);
}
function getSessionStorage(arg) {
	return KV("sessionStorage", arg);
}
function getDoNotTrack(arg) {
	return KV("doNotTrack", "unspecified");//TODO
}
function getLocalStorage(arg) {
	return KV("localStorage", arg);
}
function getIndexedDb(arg) {
	return KV("indexedDb", arg);
}
function getOpenDatabase(arg) {
	return KV("openDatabase", arg);
}
function getPlugins(plugins) {

	var str2 = "Chrome PDF Plugin#Chrome PDF Viewer#Native Client#Widevine Content Decryption Module#";
	var ks0Q = format(str2); //plugins;b9a555dce60346a48de933b3e16ebd6e

	return KV("plugins", format(str2));
}
function getAdblock(arg) {
	return KV("adblock", arg);
}
function getHasLiedLanguages(arg) {
	return KV("hasLiedLanguages", arg);
}
function getHasLiedResolution(arg) {
	return KV("hasLiedResolution", arg);
}
function getHasLiedOs(arg) {
	return KV("hasLiedOs", arg);
}
function getHasLiedBrowser(arg) {
	return KV("hasLiedBrowser", arg);
}
function getTouchSupport(arg) {
	return KV("touchSupport", format(arg.replace(RegExp(",", "gm"), "#")));
}
function getJsFonts(arg) {
	return KV("jsFonts", format(arg.replace(RegExp(",", "gm"), "#")));
}

function pad(str) {
	var i = str.length;
	/** @type {number} */
	var block_size = 0 == i % 3 ? parseInt(i / 3) : parseInt(i / 3) + 1;
	if (3 > i) {
		return str;
	}
	var s = str.substring(0, 1 * block_size);
	var inner = str.substring(1 * block_size, 2 * block_size);
	return str.substring(2 * block_size, i) + s + inner;
}

function parseColor(s) {
	var codeSegments = s.split(".");
	if (4 !== codeSegments.length) {
		throw Error("Invalid format -- expecting a.b.c.d");
	}
	/** @type {number} */
	var i = s = 0;
	for (; i < codeSegments.length; ++i) {
		/** @type {number} */
		var b = parseInt(codeSegments[i], 10);
		if ((0 > b || 255 < b)) {
			throw Error("Each octet must be between 0 and 255");
		}
		s |= b << 8 * (codeSegments.length - i - 1);
		s >>>= 0;
	}
	return s;
}

function encodeUriSegment(val) {
	return 4294967296 * (val - (val | 0)) | 0;
}

var WEB = ["WEB", "WAP"];
var error;
if (!(error = err)) {
	var math = Math;
	var opts = {};
	var C_lib = opts.lib = {};

	var F = function () {};
	var Base = C_lib.Base = {
		clone: function () {
			return this.init.prototype.extend(this);
		},
		init: function () {},
		mixIn: function (properties) {
			var entry;
			for (entry in properties) {
				if (properties.hasOwnProperty(entry)) {
					this[entry] = properties[entry];
				}
			}
			if (properties.hasOwnProperty("toString")) {
				this.toString = properties.toString;
			}
		},
		create: function () {
			var instance = this.extend();
			instance.init.apply(instance, arguments);
			return instance;
		},
		extend: function (overrides) {
			F.prototype = this;
			var subtype = new F;
			if (overrides) {
				subtype.mixIn(overrides);
			}
			if (!subtype.hasOwnProperty("init")) {
				/**
				 * @return {undefined}
				 */
				subtype.init = function () {
					subtype.$super.init.apply(this, arguments);
				};
			}
			subtype.init.prototype = subtype;
			subtype.$super = this;
			return subtype;
		}
	};

	var nodes = C_lib.WordArray = Base.extend({
			concat: function (key) {
				var newArgs = this.words;
				var words = key.words;
				var thisSigBytes = this.sigBytes;
				key = key.sigBytes;
				this.clamp();
				if (thisSigBytes % 4) {
					/** @type {number} */
					var i = 0;
					for (; i < key; i++) {
						newArgs[thisSigBytes + i >>> 2] |= (words[i >>> 2] >>> 24 - i % 4 * 8 & 255) << 24 - (thisSigBytes + i) % 4 * 8;
					}
				} else {
					if (65535 < words.length) {
						/** @type {number} */
						i = 0;
						for (; i < key; i += 4) {
							newArgs[thisSigBytes + i >>> 2] = words[i >>> 2];
						}
					} else {
						newArgs.push.apply(newArgs, words);
					}
				}
				this.sigBytes += key;
				return this;
			},
			clone: function () {
				var clone = Base.clone.call(this);
				clone.words = this.words.slice(0);
				return clone;
			},
			random: function (key) {
				/** @type {Array} */
				var expr = [];
				/** @type {number} */
				var leftKey = 0;
				for (; leftKey < key; leftKey += 4) {
					expr.push(4294967296 * math.random() | 0);
				}
				return new nodes.init(expr, key);
			},
			clamp: function () {
				var words = this.words;
				var sigBytes = this.sigBytes;
				words[sigBytes >>> 2] &= 4294967295 << 32 - sigBytes % 4 * 8;
				/** @type {number} */
				words.length = math.ceil(sigBytes / 4);
			},
			toString: function (opt_attributes) {
				return (opt_attributes || wb).stringify(this);
			},
			init: function (words, allBindingsAccessor) {
				words = this.words = words || [];
				this.sigBytes = void 0 != allBindingsAccessor ? allBindingsAccessor : 4 * words.length;
			}
		});
	var C_enc = opts.enc = {};
	var wb = C_enc.Hex = {
		parse: function (values) {
			var valuesLen = values.length;
			/** @type {Array} */
			var selector = [];
			/** @type {number} */
			var i = 0;
			for (; i < valuesLen; i += 2) {
				selector[i >>> 3] |= parseInt(values.substr(i, 2), 16) << 24 - i % 8 * 4;
			}
			return new nodes.init(selector, valuesLen / 2);
		},
		stringify: function (wordArray) {
			var words = wordArray.words;
			wordArray = wordArray.sigBytes;
			/** @type {Array} */
			var tagNameArr = [];
			/** @type {number} */
			var i = 0;
			for (; i < wordArray; i++) {
				/** @type {number} */
				var bite = words[i >>> 2] >>> 24 - i % 4 * 8 & 255;
				tagNameArr.push((bite >>> 4).toString(16));
				tagNameArr.push((bite & 15).toString(16));
			}
			return tagNameArr.join("");
		}
	};
	var Latin1 = C_enc.Latin1 = {
		stringify: function (wordArray) {
			var words = wordArray.words;
			wordArray = wordArray.sigBytes;
			/** @type {Array} */
			var tagNameArr = [];
			/** @type {number} */
			var i = 0;
			for (; i < wordArray; i++) {
				tagNameArr.push(String.fromCharCode(words[i >>> 2] >>> 24 - i % 4 * 8 & 255));
			}
			return tagNameArr.join("");
		},
		parse: function (str) {
			var n = str.length;
			/** @type {Array} */
			var selector = [];
			/** @type {number} */
			var i = 0;
			for (; i < n; i++) {
				selector[i >>> 2] |= (str.charCodeAt(i) & 255) << 24 - i % 4 * 8;
			}
			return new nodes.init(selector, n);
		}
	};
	var fmt = C_enc.Utf8 = {
		parse: function (s) {
			return Latin1.parse(unescape(encodeURIComponent(s)));
		},
		/**
		 * @param {number} wordArray
		 * @return {?}
		 */
		stringify: function (wordArray) {
			try {
				return decodeURIComponent(escape(Latin1.stringify(wordArray)));
			} catch (b) {
				throw Error("Malformed UTF-8 data");
			}
		}
	};
	var collection = C_lib.BufferedBlockAlgorithm = Base.extend({
			_process: function (index) {
				var data = this._data;
				var dataWords = data.words;
				var key = data.sigBytes;
				var n = this.blockSize;
				/** @type {number} */
				var value = key / (4 * n);
				/** @type {number} */
				value = index ? math.ceil(value) : math.max((value | 0) - this._minBufferSize, 0);
				/** @type {number} */
				index = value * n;
				/** @type {number} */
				key = math.min(4 * index, key);
				if (index) {
					/** @type {number} */
					var pos = 0;
					for (; pos < index; pos += n) {
						this._doProcessBlock(dataWords, pos);
					}
					pos = dataWords.splice(0, index);
					data.sigBytes -= key;
				}
				return new nodes.init(pos, key);
			},
			/**
			 * @param {(number|string)} data
			 * @return {undefined}
			 */
			_append: function (data) {
				if ("string" == typeof data) {
					data = fmt.parse(data);
				}
				this._data.concat(data);
				this._nDataBytes += data.sigBytes;
			},
			/**
			 * @return {undefined}
			 */
			reset: function () {
				this._data = new nodes.init;
				/** @type {number} */
				this._nDataBytes = 0;
			},
			_minBufferSize: 0,
			/**
			 * @return {?}
			 */
			clone: function () {
				var clone = Base.clone.call(this);
				clone._data = this._data.clone();
				return clone;
			}
		});
	C_lib.Hasher = collection.extend({
			reset: function () {
				collection.reset.call(this);
				this._doReset();
			},
			_createHmacHelper: function (hasher) {
				return function (messageUpdate, key) {
					return (new C_algo.HMAC.init(hasher, key)).finalize(messageUpdate);
				};
			},
			cfg: Base.extend(),
			blockSize: 16,
			update: function (messageUpdate) {
				this._append(messageUpdate);
				this._process();
				return this;
			},
			finalize: function (messageUpdate) {
				if (messageUpdate) {
					this._append(messageUpdate);
				}
				return this._doFinalize();
			},
			_createHelper: function (hasher) {
				return function (messageUpdate, cfg) {
					return (new hasher.init(cfg)).finalize(messageUpdate);
				};
			},
			init: function (cfg) {
				this.cfg = this.cfg.extend(cfg);
				this.reset();
			}
		});
	var C_algo = opts.algo = {};
	error = opts;
}
var err = error;
var m = Math;
var C = err;
var SHA512 = C.lib;
var WordArray = SHA512.WordArray;
var Hasher = SHA512.Hasher;
SHA512 = C.algo;

var segments = [];
var prevSources = [];
var distY = 2;
var i = 0;
for (; 64 > i; ) {
	var sx;
	a: {
		/** @type {number} */
		sx = distY;
		/** @type {number} */
		var firingIndex = m.sqrt(sx);
		/** @type {number} */
		var index = 2;
		for (; index <= firingIndex; index++) {
			if (!(sx % index)) {
				/** @type {boolean} */
				sx = false;
				break a;
			}
		}
		/** @type {boolean} */
		sx = true;
	}
	if (sx) {
		if (8 > i) {
			segments[i] = encodeUriSegment(m.pow(distY, 0.5));
		}
		prevSources[i] = encodeUriSegment(m.pow(distY, 1 / 3));
		i++;
	}
	distY++;
}
/** @type {Array} */
var tokens = [];
SHA512 = SHA512.SHA256 = Hasher.extend({
		/**
		 * @return {undefined}
		 */
		_doReset: function () {
			this._hash = new WordArray.init(segments.slice(0));
		},
		/**
		 * @param {?} M
		 * @param {number} offset
		 * @return {undefined}
		 */
		_doProcessBlock: function (M, offset) {
			var H = this._hash.words;
			var a = H[0];
			var b = H[1];
			var c = H[2];
			var A = H[3];
			var B = H[4];
			var C = H[5];
			var D = H[6];
			var E = H[7];
			/** @type {number} */
			var i = 0;
			for (; 64 > i; i++) {
				if (16 > i) {
					/** @type {number} */
					tokens[i] = M[offset + i] | 0;
				} else {
					var j = tokens[i - 15];
					var t = tokens[i - 2];
					tokens[i] = ((j << 25 | j >>> 7) ^ (j << 14 | j >>> 18) ^ j >>> 3) + tokens[i - 7] + ((t << 15 | t >>> 17) ^ (t << 13 | t >>> 19) ^ t >>> 10) + tokens[i - 16];
				}
				j = E + ((B << 26 | B >>> 6) ^ (B << 21 | B >>> 11) ^ (B << 7 | B >>> 25)) + (B & C ^ ~B & D) + prevSources[i] + tokens[i];
				/** @type {number} */
				t = ((a << 30 | a >>> 2) ^ (a << 19 | a >>> 13) ^ (a << 10 | a >>> 22)) + (a & b ^ a & c ^ b & c);
				E = D;
				D = C;
				C = B;
				/** @type {number} */
				B = A + j | 0;
				A = c;
				c = b;
				b = a;
				/** @type {number} */
				a = j + t | 0;
			}
			/** @type {number} */
			H[0] = H[0] + a | 0;
			/** @type {number} */
			H[1] = H[1] + b | 0;
			/** @type {number} */
			H[2] = H[2] + c | 0;
			/** @type {number} */
			H[3] = H[3] + A | 0;
			/** @type {number} */
			H[4] = H[4] + B | 0;
			/** @type {number} */
			H[5] = H[5] + C | 0;
			/** @type {number} */
			H[6] = H[6] + D | 0;
			/** @type {number} */
			H[7] = H[7] + E | 0;
		},
		/**
		 * @return {?}
		 */
		_doFinalize: function () {
			var data = this._data;
			var dataWords = data.words;
			/** @type {number} */
			var nBitsTotal = 8 * this._nDataBytes;
			/** @type {number} */
			var nBitsLeft = 8 * data.sigBytes;
			dataWords[nBitsLeft >>> 5] |= 128 << 24 - nBitsLeft % 32;
			/** @type {number} */
			dataWords[(nBitsLeft + 64 >>> 9 << 4) + 14] = m.floor(nBitsTotal / 4294967296);
			/** @type {number} */
			dataWords[(nBitsLeft + 64 >>> 9 << 4) + 15] = nBitsTotal;
			/** @type {number} */
			data.sigBytes = 4 * dataWords.length;
			this._process();
			return this._hash;
		},
		/**
		 * @return {?}
		 */
		clone: function () {
			var clone = Hasher.clone.call(this);
			clone._hash = this._hash.clone();
			return clone;
		}
	});
C.SHA256 = Hasher._createHelper(SHA512);
C.HmacSHA256 = Hasher._createHmacHelper(SHA512);
var CryptoJS = err;
var _ = CryptoJS.lib.WordArray;
CryptoJS.enc.Base64 = {
	/**
	 * @param {Array} key
	 * @return {?}
	 */
	stringify: function (key) {
		var buf = key.words;
		var len = key.sigBytes;
		var map = this._map;
		key.clamp();
		/** @type {Array} */
		key = [];
		/** @type {number} */
		var i = 0;
		for (; i < len; i += 3) {
			/** @type {number} */
			var f = (buf[i >>> 2] >>> 24 - i % 4 * 8 & 255) << 16 | (buf[i + 1 >>> 2] >>> 24 - (i + 1) % 4 * 8 & 255) << 8 | buf[i + 2 >>> 2] >>> 24 - (i + 2) % 4 * 8 & 255;
			/** @type {number} */
			var HOUR = 0;
			for (; 4 > HOUR && i + 0.75 * HOUR < len; HOUR++) {
				key.push(map.charAt(f >>> 6 * (3 - HOUR) & 63));
			}
		}
		if (buf = map.charAt(64)) {
			for (; key.length % 4; ) {
				key.push(buf);
			}
		}
		return key.join("");
	},
	_map: "ABCDEFGHIJKLMNOPQRSTUVWXYZabcdefghijklmnopqrstuvwxyz0123456789-_",
	/**
	 * @param {string} chars
	 * @return {?}
	 */
	parse: function (chars) {
		var numChars = chars.length;
		var map = this._map;
		var other = map.charAt(64);
		if (other) {
			other = chars.indexOf(other);
			if (-1 != other) {
				numChars = other;
			}
		}
		/** @type {Array} */
		other = [];
		/** @type {number} */
		var title = 0;
		/** @type {number} */
		var i = 0;
		for (; i < numChars; i++) {
			if (i % 4) {
				/** @type {number} */
				var b1 = map.indexOf(chars.charAt(i - 1)) << i % 4 * 2;
				/** @type {number} */
				var b2 = map.indexOf(chars.charAt(i)) >>> 6 - i % 4 * 2;
				other[title >>> 2] |= (b1 | b2) << 24 - title % 4 * 8;
				title++;
			}
		}
		return _.create(other, title);
	}
};

function hashAlg(result, keepData, n) {
	result.sort(function (obj, p) {
		var a;
		var b;
		if ("object" === typeof obj && ("object" === typeof p && (obj && p))) {
			return a = obj.key,
			b = p.key,
			a === b ? 0 : typeof a === typeof b ? a < b ? -1 : 1 : typeof a < typeof b ? -1 : 1;
		}
		throw "error";
	});
	/** @type {number} */
	var c = 0;
	for (; c < result.length; c++) {
		var i = result[c].key.replace(RegExp("%", "gm"), "");
		/** @type {string} */
		var v = "";
		v = "string" == typeof result[c].value ? result[c].value.replace(RegExp("%", "gm"), "") : result[c].value;
		if ("" !== v) {
			n += i + v;
			keepData += "&" + (void 0 == groupedSelectors[i] ? i : groupedSelectors[i]) + "=" + v;
		}
	}
	result = pad(n);
	n = result.length;
	c = result.split("");
	/** @type {number} */
	i = 0;
	for (; i < parseInt(n / 2); i++) {
		if (0 == i % 2) {
			v = result.charAt(i);
			c[i] = c[n - 1 - i];
			c[n - 1 - i] = v;
		}
	}
	result = c.join("");
	result = pad(result);
	result = pad(result);

	n = "";
	c = result.length - 1;
	for (; 0 <= c; c--) {
		n += result.charAt(c);
	}
	
	n = err.SHA256(n).toString(err.enc.Base64);
	return KV(keepData, n);
}

function Test() {
	// var userAgent = "Mozilla/5.0 (Windows NT 6.1; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/63.0.3239.108 Safari/537.36";
	var userAgent = "Mozilla/5.0 (Macintosh; Intel Mac OS X 10.14; rv:67.0) Gecko/20100101 Firefox/67.0";//TODO
	var scrHeight = 1080;
	var scrWidth = 1920;
	var scrAvailHeight = 1040;
	var scrAvailWidth = scrWidth;
	var colorDepth = 24;

	var bucket = [];
	bucket.push(KV("cookieCode", "FGGL0-O5s6AoRG9XXAM37HtL-VdrEsSd"));//TODO
	bucket.push(KV("userAgent", userAgent));
	bucket.push(KV("scrHeight", scrHeight.toString()));
	bucket.push(KV("scrWidth", scrWidth.toString()));
	bucket.push(KV("scrAvailHeight", scrAvailHeight.toString()));
	bucket.push(KV("scrAvailWidth", scrAvailWidth.toString()));
	bucket.push(KV("scrColorDepth", colorDepth.toString()));
	bucket.push(KV("scrDeviceXDPI", ""));
	bucket.push(KV("appCodeName", "Mozilla"));
	bucket.push(KV("appName", "Netscape"));
	bucket.push(KV("javaEnabled", "0"));
	bucket.push(getMimeTypes()); //mimeTypes;e237f9703f53d448d77c858b634154a5
	bucket.push(KV("os", "MacIntel"));
	bucket.push(KV("appMinorVersion", ""));
	bucket.push(KV("browserLanguage", "zh-CN"));
	bucket.push(KV("cookieEnabled", "1"));
	bucket.push(KV("cpuClass", ""));
	bucket.push(KV("onLine", "true"));
	bucket.push(KV("systemLanguage", ""));
	bucket.push(KV("userLanguage", ""));
	bucket.push(KV("timeZone", -8));
	bucket.push(KV("flashVersion", "32.0 r0"));
	bucket.push(KV("historyList", 1));
	bucket.push(KV("custID", "133"));
	bucket.push(KV("platform", "WEB"));

	var o2 = [];
	o2.push(KV("user_agent", userAgent.replace(RegExp("/", "gm"), ""))); // "Mozilla5.0 (Windows NT 6.1; Win64; x64) AppleWebKit537.36 (KHTML, like Gecko) Chrome63.0.3239.108 Safari537.36"));
	o2.push(KV("language", "zh-CN"));
	o2.push(KV("color_depth", colorDepth));
	o2.push(KV("pixel_ratio", 1));
	o2.push(KV("resolution", new Array(scrWidth, scrHeight)));
	o2.push(KV("available_resolution", new Array(scrAvailWidth, scrAvailHeight)));
	o2.push(KV("timezone_offset", -480));
	o2.push(KV("session_storage", 1));
	o2.push(KV("local_storage", 1));
	o2.push(KV("indexed_db", 1));
	o2.push(KV("open_database", 1));
	o2.push(KV("cpu_class", "unknown"));
	o2.push(KV("navigator_platform", "Win32"));
	o2.push(KV("do_not_track", "unknown"));
	o2.push(KV("regular_plugins", new Array("Chrome PDF Plugin::Portable Document Format::application/x-google-chrome-pdf~pdf",
				"Chrome PDF Viewer::::application/pdf~pdf",
				"Native Client::::application/x-nacl~,application/x-pnacl~",
				"Widevine Content Decryption Module::Enables Widevine licenses for playback of HTML audio/video content. (version: 1.4.8.1029)::application/x-ppapi-widevine-cdm~")));
	o2.push(KV("canvas", "canvas winding:yes~canvas fp:data:image/png;base64,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kryd5M0iRXtNrNEiCBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgMAgFBlSB3qtMv6x7Iv3GJDNyyYbOPLBsf5ZsbsrwruZ+PevDDTuydOZPefWJS3Pwlup73Dcl2VydOC9S/Nyva/FlBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQINCnwIAt0HvvtvtR79UyvSWTPxiem18fncmtTZm+b1KmHBtzRvOxdegv+WJ8W75t2ZUNCzry3ZxDSbYl2VikqP7sIkCAAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAIEaExg0Bfr/uVdSGZF5K6fmpvebctXOK3Ko0pzZuy9IMjYHG0bnvK7xGdbj00eTHE9yrGFvRnZ1JNmfdROO5KLGHflh0p58NLctax/eWqT4vcbO2nIIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECBA4BQCg75Alw4CBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQIFAVUKDLAQECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQUKDLAAECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQKAVMoEsCAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBBQoMsAAQIECBAgQIAAAQIECBAgQIAAAQIECBAgQIAAAQIECBAoBUygSwIBAgQIECBAgAABAgQIECBAgAABAgQIECBAgAABAgQIEFCgywABAgQIECDd0eY9AAAASElEQVRAgAABAgQIECBAgAABAgQIECBAgAABAgQIECgFTKBLAgECBAgQIECAAAECBAgQIECAAAECBAgQIECAAAECBAgQ+FvgLxQc216Yo/QbAAAAAElFTkSuQmCC"));
	o2.push(KV("webgl", "data:image/png;base64,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~extensions:ANGLE_instanced_arrays;EXT_blend_minmax;EXT_color_buffer_half_float;EXT_disjoint_timer_query;EXT_frag_depth;EXT_shader_texture_lod;EXT_texture_filter_anisotropic;WEBKIT_EXT_texture_filter_anisotropic;EXT_sRGB;OES_element_index_uint;OES_standard_derivatives;OES_texture_float;OES_texture_float_linear;OES_texture_half_float;OES_texture_half_float_linear;OES_vertex_array_object;WEBGL_color_buffer_float;WEBGL_compressed_texture_s3tc;WEBKIT_WEBGL_compressed_texture_s3tc;WEBGL_compressed_texture_s3tc_srgb;WEBGL_debug_renderer_info;WEBGL_debug_shaders;WEBGL_depth_texture;WEBKIT_WEBGL_depth_texture;WEBGL_draw_buffers;WEBGL_lose_context;WEBKIT_WEBGL_lose_context~webgl aliased line width range:[1, 1]~webgl aliased point size range:[1, 1024]~webgl alpha bits:8~webgl antialiasing:yes~webgl blue bits:8~webgl depth bits:24~webgl green bits:8~webgl max anisotropy:16~webgl max combined texture image units:32~webgl max cube map texture size:16384~webgl max fragment uniform vectors:1024~webgl max render buffer size:16384~webgl max texture image units:16~webgl max texture size:16384~webgl max varying vectors:30~webgl max vertex attribs:16~webgl max vertex texture image units:16~webgl max vertex uniform vectors:4096~webgl max viewport dims:[16384, 16384]~webgl red bits:8~webgl renderer:WebKit WebGL~webgl shading language version:WebGL GLSL ES 1.0 (OpenGL ES GLSL ES 1.0 Chromium)~webgl stencil bits:0~webgl vendor:WebKit~webgl version:WebGL 1.0 (OpenGL ES 2.0 Chromium)~webgl vertex shader high float precision:23~webgl vertex shader high float precision rangeMin:127~webgl vertex shader high float precision rangeMax:127~webgl vertex shader medium float precision:23~webgl vertex shader medium float precision rangeMin:127~webgl vertex shader medium float precision rangeMax:127~webgl vertex shader low float precision:23~webgl vertex shader low float precision rangeMin:127~webgl vertex shader low float precision rangeMax:127~webgl fragment shader high float precision:23~webgl fragment shader high float precision rangeMin:127~webgl fragment shader high float precision rangeMax:127~webgl fragment shader medium float precision:23~webgl fragment shader medium float precision rangeMin:127~webgl fragment shader medium float precision rangeMax:127~webgl fragment shader low float precision:23~webgl fragment shader low float precision rangeMin:127~webgl fragment shader low float precision rangeMax:127~webgl vertex shader high int precision:0~webgl vertex shader high int precision rangeMin:31~webgl vertex shader high int precision rangeMax:30~webgl vertex shader medium int precision:0~webgl vertex shader medium int precision rangeMin:31~webgl vertex shader medium int precision rangeMax:30~webgl vertex shader low int precision:0~webgl vertex shader low int precision rangeMin:31~webgl vertex shader low int precision rangeMax:30~webgl fragment shader high int precision:0~webgl fragment shader high int precision rangeMin:31~webgl fragment shader high int precision rangeMax:30~webgl fragment shader medium int precision:0~webgl fragment shader medium int precision rangeMin:31~webgl fragment shader medium int precision rangeMax:30~webgl fragment shader low int precision:0~webgl fragment shader low int precision rangeMin:31~webgl fragment shader low int precision rangeMax:30"));
	o2.push(KV("adblock", "0"));
	o2.push(KV("has_lied_languages", false));
	o2.push(KV("has_lied_resolution", false));
	o2.push(KV("has_lied_os", false));
	o2.push(KV("has_lied_browser", false));
	o2.push(KV("touch_support", new Array(0, false, false)));
	o2.push(KV("js_fonts", new Array("Arial", "Arial Black", "Arial Narrow", "Arial Unicode MS", "Book Antiqua", "Bookman Old Style", "Calibri", "Cambria", "Cambria Math", "Century", "Century Gothic", "Comic Sans MS", "Consolas", "Courier", "Courier New", "Garamond", "Georgia", "Helvetica", "Impact", "Lucida Bright", "Lucida Calligraphy", "Lucida Console", "Lucida Fax", "Lucida Handwriting", "Lucida Sans Unicode", "Microsoft Sans Serif", "Monotype Corsiva", "MS Gothic", "MS PGothic", "MS Reference Sans Serif", "MS Sans Serif", "MS Serif", "Palatino Linotype", "Segoe Print", "Segoe Script", "Segoe UI", "Segoe UI Light", "Segoe UI Semibold", "Segoe UI Symbol", "Tahoma", "Times", "Times New Roman", "Trebuchet MS", "Verdana", "Wingdings", "Wingdings 2", "Wingdings 3")));

	var moreInfoArray = [];
	moreInfoArray.push(KV("webSmartID", getWebSmartID(o2))); //webSmartID;718f892be8f4c1333b4862bf74eb108b

	for (var i = 0; i < o2.length; i++) {
		var result = o2[i].key;
		var until = o2[i].value + "";
		switch (result) {
		case "session_storage":
			moreInfoArray.push(getSessionStorage(until));
			break;
		case "local_storage":
			moreInfoArray.push(getLocalStorage(until));
			break;
		case "indexed_db":
			moreInfoArray.push(getIndexedDb(until));
			break;
		case "open_database":
			moreInfoArray.push(getOpenDatabase(until));
			break;
		case "do_not_track":
			moreInfoArray.push(getDoNotTrack(until));
			break;
		case "ie_plugins":
			moreInfoArray.push(getPlugins(until));
			break;
		case "regular_plugins":
			moreInfoArray.push(getPlugins(""));
			break;
		case "adblock":
			moreInfoArray.push(getAdblock(until));
			break;
		case "has_lied_languages":
			moreInfoArray.push(getHasLiedLanguages(until));
			break;
		case "has_lied_resolution":
			moreInfoArray.push(getHasLiedResolution(until));
			break;
		case "has_lied_os":
			moreInfoArray.push(getHasLiedOs(until));
			break;
		case "has_lied_browser":
			moreInfoArray.push(getHasLiedBrowser(until));
			break;
		case "touch_support":
			moreInfoArray.push(getTouchSupport(until));
			break;
		case "js_fonts":
			moreInfoArray.push(getJsFonts(until));
		}
	}
	bucket = bucket.concat(moreInfoArray);
	bucket.sort(function (obj, p) {
		var a;
		var b;
		if ("object" === typeof obj && ("object" === typeof p && (obj && p))) {
			return a = obj.key,
			b = p.key,
			a === b ? 0 : typeof a === typeof b ? a < b ? -1 : 1 : typeof a < typeof b ? -1 : 1;
		}
		throw "error";
	});

	var sorted = "appCodeName appMinorVersion appName cpuClass onLine systemLanguage userLanguage historyList hasLiedLanguages hasLiedResolution hasLiedOs hasLiedBrowser".split(" ");
	var keys = ["sessionStorage", "localStorage", "indexedDb", "openDatabase"];
	var _path = ["scrAvailWidth", "scrAvailHeight"];
	var c = "192.168.0.131";
	var v = bucket;
	var p = [];
	var val = [];
	var vals = [];
	var options = [];
	var j = 0;
	for (; j < v.length; j++) {
		if ("new" != v[j].value) {
			if (-1 == sorted.indexOf(v[j].key)) {
				if (-1 != keys.indexOf(v[j].key)) {
					val.push(v[j]);
				} else {
					if (-1 != _path.indexOf(v[j].key)) {
						vals.push(v[j]);
					} else {
						if (-1 != blackList.indexOf(v[j].key)) {
							options.push(v[j]);
						} else {
							p.push(v[j]);
						}
					}
				}
			}
		}
	}
	v = "";
	j = 0;
	for (; j < val.length; j++) {
		v = v + val[j].key.charAt(0) + val[j].value;
	}
	val = "";
	j = 0;
	for (; j < options.length; j++) {
		val = 0 == j ? val + options[j].value : val + "x" + options[j].value;
	}
	options = "";
	j = 0;
	for (; j < vals.length; j++) {
		options = 0 == j ? options + vals[j].value : options + "x" + vals[j].value;
	}
	// p.push(KV("storeDb", v));
	p.push(KV("storeDb", "i1l1s1"));//TODO
	p.push(KV("srcScreenSize", val));
	p.push(KV("scrAvailSize", options));
	if ("" != c) {
		p.push(KV("localCode", parseColor(c)));
	}
	var key = "";
	var data = "";
	data = hashAlg(p, key, data);
	key = data.key;
	data = data.value;
	key += "&timestamp=" + (new Date).getTime();
	return "/otn/HttpZF/logdevice" + ("?algID=%s&hashCode=" + data + key);
}
/* 	}
} */
