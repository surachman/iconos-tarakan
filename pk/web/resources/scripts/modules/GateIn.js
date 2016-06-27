var receivingForm = {
    validWeight: 0,
    calculate: function () {
        var grossWeight_InputText = jQuery('[id=gateIn:weight]');
        var truckWeight_InputText = jQuery('[id=gateIn:tonage]');
        var estSecondContWeight_InputText = jQuery('[id=gateIn:estContWeight]');
        var nettWeight_InputText = jQuery('[id=gateIn:contWeight]');
        var nettWeight = grossWeight_InputText.val() - truckWeight_InputText.val();

        if (estSecondContWeight_InputText.val() != '') {
            nettWeight = nettWeight - estSecondContWeight_InputText.val();
        }

        if (jQuery.ebtosWeighing.isWeighingConnected == true && jQuery.ebtosWeighing.isStable == false) {
            grossWeight_InputText.val(0);
            nettWeight = 0;
        }

        this.validWeight = grossWeight_InputText.val();

        nettWeight_InputText.val(nettWeight);

        if (this.isFormReady()) {
            if (jQuery("[id=gateIn:receivingConfirm]").is(':disabled') == true) {
                receivingConfirm_var.enable();
            }
        } else {
            if (jQuery("[id=gateIn:receivingConfirm]").is(':disabled') == false) {
                receivingConfirm_var.disable();
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

var deliveryForm = {
    validWeight: 0,
    calculate: function () {
        var truckWeight_InputText = jQuery('[id=gateInDelivery:tonage]');

        if (jQuery.ebtosWeighing.isWeighingConnected == true && jQuery.ebtosWeighing.isStable == false) {
            truckWeight_InputText.val(0);
        }

        this.validWeight = truckWeight_InputText.val();

        if (this.isFormReady()) {
            if (jQuery("[id=gateInDelivery:deliveryConfirm]").is(':disabled') == true) {
                deliveryConfirm_var.enable();
            }
        } else {
            if (jQuery("[id=gateInDelivery:deliveryConfirm]").is(':disabled') == false) {
                deliveryConfirm_var.disable();
            }
        }
    },
    isFormReady: function() {
        return jQuery("[id=gateInDelivery:truckType]").val() !== "" && jQuery("[id=gateInDelivery:contNo]").val() !== "" && this.validWeight >= 2000;
    },
    resetUI: function() {
        jQuery('[id=gateInDelivery:tonage]').val(jQuery.ebtosWeighing.value).trigger('change');
    }
}

jQuery.ebtosWeighing.forms = [deliveryForm, receivingForm, '', ''];
jQuery.ebtosWeighing.resetUI = function() {
    var index = gateInTabView_var.getActiveIndex();
    var form = this.forms[index];
    if (form !== '') {
        form.resetUI();
    }
}

jQuery(document).ready(function() {
    jQuery('[id=gateIn:weight], [id=gateIn:estContWeight], [id=gateIn:tonage]').livequery(function(){
        jQuery(this).change(function() {
            receivingForm.calculate();
        }).keyup(function() {
            jQuery(this).trigger('change');
        });
    });

    jQuery('[id=gateInDelivery:tonage]').livequery(function(){
        jQuery(this).change(function() {
            deliveryForm.calculate();
        }).keyup(function() {
            jQuery(this).trigger('change');
        });
    });
});