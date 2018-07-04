function test() {
    $.ajax({
        url: 'helloProcess.json',
        type: 'post',
        dataType: 'json',
        contentType: 'application/json',
        success: function (data) {
            console.log(data);
            //$('.msrp_text').html("<Strong>"+data.msrp+"</strong>");
        },
        //data:  JSON.stringify(configurations)
    });
};

//$('#helloworldbutton').onclick(test());
