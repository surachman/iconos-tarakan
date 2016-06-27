/* 
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

function is_int(value){
    if((parseFloat(value) == parseInt(value)) && !isNaN(value)){
        return true;
    } else {
        return false;
    }
}

function is_float(value){
    if((parseFloat(value)) && !isNaN(value)){
        return true;
    } else {
        return false;
    }
}
function genTitle(id, title){
    var element = '#ui-dialog-title-' + id;
    jQuery(element).text(title);
}
