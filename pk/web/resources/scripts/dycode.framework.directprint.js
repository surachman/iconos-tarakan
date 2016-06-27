/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function handlePrintRequest(args) {
    if(!(args.validationFailed || !args.doPrint)) {
        var pdf = document.getElementById(args.target);
        pdf.setAttribute('src',unescape(args.url));
        jQuery(pdf).load(function (){
            pdf.focus();
            pdf.contentWindow.print();
        });
    }
}