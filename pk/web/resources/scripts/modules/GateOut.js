var deliveryForm = {
    validWeight: 0,
    calculate: function () {
        var contNumber_InputText = jQuery('input[name=gateIn:contNumber]:checked');
        var grossWeight_InputText = jQuery('[id=gateIn:weight]');
        var truckWeight_InputText = jQuery('[id=gateIn:tonage]');
        var nettWeight_InputText = jQuery('[id=gateIn:contWeight]');
        var nettWeight = grossWeight_InputText.val() - truckWeight_InputText.val();
//        nettWeight_InputText.val(nettWeight);
        
        if(contNumber_InputText.val() == "2"){
            nettWeight = nettWeight/2;
        }else if(contNumber_InputText.val() == "1"){
            nettWeight = grossWeight_InputText.val() - truckWeight_InputText.val();
        }

        if (jQuery.ebtosWeighing.isWeighingConnected == true && jQuery.ebtosWeighing.isStable == false) {
            grossWeight_InputText.val(0);
            nettWeight = 0;
        }

        this.validWeight = grossWeight_InputText.val();

        nettWeight_InputText.val(nettWeight);

        if (this.isFormReady()) {
            if (jQuery("[id=gateIn:deliveryConfirm]").is(':disabled') == true) {
                deliveryConfirm_var.enable();
            }
        } else {
            if (jQuery("[id=gateIn:deliveryConfirm]").is(':disabled') == false) {
//                perubahan tombol confirm tanpa disabled by ade chelsea tanggal 28 april 2014 10:09 jayapura
//                deliveryConfirm_var.disable();
            }
        }
    },
    isFormReady: function() {
        return jQuery("[id=gateIn:truckType]").val() !== "" && jQuery("[id=gateIn:contNo]").val() !== "" && this.validWeight >= 2000;
    },
    resetUI: function() {
        jQuery('[id=gateIn:weight]').val(jQuery.ebtosWeighing.value).trigger('change');
    }
}

jQuery.ebtosWeighing.forms = [deliveryForm, '', '', ''];
jQuery.ebtosWeighing.resetUI = function() {
    var index = gateOutTabView_var.getActiveIndex();
    var form = this.forms[index];
    if (form !== '') {
        form.resetUI();
    }
}

jQuery(document).ready(function() {
    jQuery('[id=gateIn:weight]').livequery(function(){
        jQuery(this).change(function() {
            deliveryForm.calculate();
        }).keyup(function() {
            jQuery(this).trigger('change');
        });
    });
});