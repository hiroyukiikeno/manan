/**
 * Actions for DOM events in course.jsp
 */
(function(){
	
	$(document).ready(function(){
		$('.text1').css("color","#ffff00");
	});
	
	var parms = {};
	
	/* action to toggle details of list item */
	$('.item-name').click(function(ev){
		var courseitem = $(ev.target).parent();
		$(courseitem).find('.catalog-item-detail').toggleClass('noshow');
		
		var coursestatusval = $(courseitem).find('#coursestatusvalue').text();
		if(coursestatusval){
			$(courseitem).find('#coursestatus').removeClass('noshow');
		}
		
		var userstatusval = $(courseitem).find('#userstatusvalue').text();
		if(userstatusval){
			$(courseitem).find('#userstatus').removeClass('noshow');
		}
		
		var peoplestatusval = $(courseitem).find('#peoplestatusvalue').text();
		if(peoplestatusval){
			$(courseitem).find('#peoplestatus').removeClass('noshow');
		}
		
		var accesscodereqval = $(courseitem).find('#accesscodereqvalue').text();
		if(accesscodereqval){
			$(courseitem).find('#accesscodereq').removeClass('noshow');
		}
		
	});
	
	/* action when course start button clicked */
	$('.catalog-item-action').click(function(ev){
		ev.preventDefault();
		var coursename = $(ev.target).parent().parent().find('.item-title').text();
		var accesscd = $(ev.target).parent().parent().find('#acccode').val();
		parms.course = coursename;
		parms.accesscode = accesscd;
		doSelect(parms);
	});
	
	/* action when home link icon clicked */
	$('#listlink').click(function(){
		window.location.href = "/";
	});
	
	/* display total score */
	window.setTimeout(function(){
		if(window.innerWidth > 600){
			$('#totalscore').removeClass('noshow');
		}
	},2000);
	$('.coin').click(function(ev){
		$('#totalscore').toggleClass('noshow');
	});
	$('#totalscore').click(function(ev){
		$('#totalscore').toggleClass('noshow');
	});
	
}());