(function($)
{
    $.fn.blink = function(options)
    {
        var defaults = { delay:500 };
        var options = $.extend(defaults, options);

        return this.each(function()
        {
            var obj = $(this);
            setInterval(function()
            {
                $(obj).effect("highlight", {}, 1000);
            }, options.delay);
        });
    }
}(jQuery))