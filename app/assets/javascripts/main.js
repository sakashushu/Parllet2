/*global require, requirejs*/

requirejs.config({
	paths:  {
		jquery: '../lib/jquery/jquery',
		bootstrap: '../lib/bootstrap/js/bootstrap',
		sub: 'sub'
	},
	shim: {
		jquery: {
			exports: '$'
		},
		bootstrap: {
			deps: ['jquery']
		},
		sub: {
			deps: ['jquery']
		}
	}
});

require(["jquery", "bootstrap", "sub"], function ($) {
  "use strict";

  console.log($.fn.jquery);
});