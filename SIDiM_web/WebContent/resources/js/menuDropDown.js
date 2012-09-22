/*!
 * Menu Master DropDown 
 * 
 */
menuHover = function(nav) {
	var sfEls = document.getElementById(nav).getElementsByTagName("li");
	for ( var i = 0; i < sfEls.length; i++) {
		sfEls[i].onmouseover = function() {
			this.className += " sfhover";
		}
		sfEls[i].onmouseout = function() {
			this.className = this.className.replace(
					new RegExp("\\s?sfhover\\b"), "");
		}
	}

	var listItem = document.getElementById(nav).getElementsByTagName('ul');
	for ( var i = 0; i < listItem.length; i++) {
		listItem[i].onmouseover = function() {
			var changeStyle = this.parentNode.getElementsByTagName('a');
			changeStyle[0].className += " active";
		}

		listItem[i].onmouseout = function() {
			var changeStyle = this.parentNode.getElementsByTagName('a');
			changeStyle[0].className = this.className.replace(new RegExp(
					"\\s?active\\b"), "");
		}
	}
}

function addEvent(obj, type, fn) {
	if (obj.attachEvent) {
		obj['e' + type + fn] = fn;
		obj[type + fn] = function() {
			obj['e' + type + fn](window.event);
		}
		obj.attachEvent('on' + type, obj[type + fn]);
	} else
		obj.addEventListener(type, fn, false);
}
function removeEvent(obj, type, fn) {
	if (obj.detachEvent) {
		obj.detachEvent('on' + type, obj[type + fn]);
		obj[type + fn] = null;
	} else
		obj.removeEventListener(type, fn, false);
}

addEvent(window, 'load', function() {
	menuHover('menu');
});

// function menuDrowDow(menu) {
// $(menu).mouseover(function () {
// $(this).parent().find("ul.subnav").slideDown('fast').show();
// $(this).parent().hover(function () {
// }, function () {
// $(this).parent().find("ul.subnav").slideUp('slow');
// });
// }).hover(function () {
// $(this).addClass("subhover");
// }, function () {
// $(this).removeClass("subhover");
// });
// }

