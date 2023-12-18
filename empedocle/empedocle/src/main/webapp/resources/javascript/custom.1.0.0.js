function disableReturn(e, t) {
    if (null == e)
        e = window.event ;
    if (e.keyCode == 13)  {
	    t.blur();
        return false;
    }
}

function insertTab(o, e) {
    var kC = e.keyCode ? e.keyCode : e.charCode ? e.charCode : e.which;
    if (kC == 9 && !e.shiftKey && !e.ctrlKey && !e.altKey) {
        var oS = o.scrollTop;
        if (o.setSelectionRange) {
            var sS = o.selectionStart;
            var sE = o.selectionEnd;
            o.value = o.value.substring(0, sS) + "\t" + o.value.substr(sE);
            o.setSelectionRange(sS + 1, sS + 1);
            o.focus();
        }
        else if (o.createTextRange) {
            document.selection.createRange().text = "\t";
            e.returnValue = false;
        }
        o.scrollTop = oS;
        if (e.preventDefault) {
            e.preventDefault();
        }
        return false;
    }
    return true;
}

function initializeDatepicker() {
	$('.datepicker').datepicker({format: 'dd/mm/yyyy', 
								autoclose: 'true', 
								todayHighlight: 'true', 
								language: 'it',
								keyboardNavigation: 'false',
								todayBtn: 'linked'})
						.on('changeDate', function(ev){
						$('.datepicker').datepicker('hide');
});
}

function initializeTooltip() {
	$('.tltip').tooltip();
	$('.tltip-left').tooltip({placement: 'left'});
	$('.tltip-right').tooltip({placement: 'right'});
}



function initializeSuggestionList(){
	$(".suggestionList").css("visibility","hidden");
	
	$(".suggestionListInput").focusin(function(){
		$(".suggestionList").css("visibility","hidden");
		$(this).next().next().css("visibility","visible");
	});
	
	$('html').click(function() {
		$(".suggestionList").css("visibility","hidden");
	});

	$('.suggestionList, .suggestionListInput').click(function(event){
	    event.stopPropagation();
	});
}

function capitalizeText() {
	var value = $(".capitalize").text();
	$(".capitalize").text(
		function(){
	        var split = value.split(' ');
	        for (var i = 0, len = split.length; i < len; i++) {
	            split[i] = split[i].charAt(0).toUpperCase() + split[i].slice(1).toLowerCase();
	        }
	        return split.join(' ');						
		}
	);
}

//function placeFooter(){
//	var view = $(window).height(); 
//	var doc = $('html').height();
//	if(doc < view)
//		$('.footer').addClass('footer-fixed').css('width', $(window).width() - 40);
//	else
//		$('.footer').removeClass('footer-fixed').css('width', 'auto');
//}

function antiLamerLogin(){
	$('form[action="j_security_check"]').submit(function(){
	    $('form').bind("keypress", function (e) {
	    	if (e.keyCode == 13) return false;
		});
	    $('button[type=submit]', this).attr('disabled', 'disabled');
	});	
}

function richAutocompleteFix(){
	$('link[href^="/empedocle/org.richfaces.resources/javax.faces.resource/org.richfaces/Autocomplete.ecss"]').remove();

}

function highlightRow(name){
	$("div[id$=tree-structure] span").css('background-color', 'transparent');
	$("div[id$=tree-structure] span.info-hidden:contains("+name+")").parent().css('background-color', '#E2DFDF');
}

function addNavigatorListeners() {
	
	window.addEventListener('online', function(e) {
		console.log('online');
		$('.conn-notify').empty()
					.html('<div class="alert alert-success">'+
							'La connessione è stata ripristinata'+
							'</div>');
		
		setTimeout( function(){ $('.conn-notify').empty(); }, 5000 );
		
	}, false);

	window.addEventListener('offline', function(e) {
		console.log('offline');
		$('.conn-notify').empty()
					.html('<div class="alert alert-error">'+
							'<strong>Attenzione!</strong> Sembra che la connessione alla rete sia assente.<br/>'+
							'I dati inseriti potrebbero andare persi. Si consiglia di attendere la riconnessione.'+
							'</div>');
		
	}, false);
}



//onload functions
$(function(){ 
//	 placeFooter();
	 initializeTooltip();
	 capitalizeText();
	 initializeSuggestionList();
	 richAutocompleteFix();
	 addNavigatorListeners();
});

//onresize functions
//$(window).resize(function(){ 
//	 placeFooter();
//});

