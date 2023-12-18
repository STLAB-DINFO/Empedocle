function initTypehelper(typeId, component, initName) {
	$('.typehelper-container[component='+component+'] > .typehelper')
		.val( initName )
		.typeahead( {
			source : function (query, process) {
				if( query == null ) return;
				      
				phena = [];
				map = {};
				
				var url = window.location.origin + '/' +
						  window.location.pathname.split('/')[1] +
						  '/suggest?type='+typeId +
						  '&query='+query;
				
				$.get( url, function( data ) {
					$.each(data, function (i, phen) {
						map[phen.label] = phen.value;
						phena.push(phen.label);
					});
				        
					process(phena);
				});
			},
		
			updater : function(item) {
				selectedState = map[item];
				var fn = window['assignValue'+component];
				if (typeof fn === "function") fn.call(null, selectedState);
				return item;
			}
		} );
}