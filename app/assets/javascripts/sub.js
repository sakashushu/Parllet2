jQuery(function(){
//  $('[data-toggle=tooltip]').tooltip();
  $("[data-toggle=popover]").popover({
    trigger: 'click hover',
    html: 'true'
  });
});
