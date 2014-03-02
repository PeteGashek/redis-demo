$(function(){
    if (!!window.EventSource) {
        var source = new EventSource("/liveData");
        source.addEventListener('message', function(e) {
            console.log("parsing json\n" + e.data);
            var obj = $.parseJSON( e.data.replace(/\\u00A0/g, ' ').replace(/\\/g, '') );
            ractive.set(obj);
        });
    } else {
        alert("Sorry. This browser doesn't seem to support Server sent event.");
    }

    var ractive = new Ractive({
        el: 'container',
        template: '#theTemplate',
        data: {totalAmount: 0, totalCnt: 0}
    })
});




