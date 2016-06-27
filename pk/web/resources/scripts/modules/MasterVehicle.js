var masterVehicleForm = {
    validWeight: 0,
    calculate: function () {
        var truckWeight_InputText = jQuery('[id=formAddEdit:tonage]');

        if (jQuery.ebtosWeighing.isWeighingConnected == true && jQuery.ebtosWeighing.isStable == false) {
            truckWeight_InputText.val(0);
        }

        this.validWeight = truckWeight_InputText.val();

        if (this.isFormReady()) {
            if (jQuery("[id=formAddEdit:saveButton]").is(':disabled') == true) {
                saveButton_var.enable();
            }
        } else {
            if (jQuery("[id=formAddEdit:saveButton]").is(':disabled') == false) {
                saveButton_var.disable();
            }
        }
    },
    isFormReady: function() {
        return jQuery("[id=formAddEdit:vCode]").val() !== "" && parseFloat(jQuery("[id=formAddEdit:maxWeight]").val()) >= 0 && this.validWeight >= 2000;
    },
    resetUI: function() {
        jQuery('[id=formAddEdit:tonage]').val(jQuery.ebtosWeighing.value).trigger('change');
    }
}

jQuery.ebtosWeighing.resetUI = function() {
    masterVehicleForm.resetUI();
}

jQuery(document).ready(function() {
    jQuery('[id=formAddEdit:tonage], [id=formAddEdit:maxWeight]').livequery(function(){
        jQuery(this).change(function() {
            masterVehicleForm.calculate();
        }).keyup(function() {
            jQuery(this).trigger('change');
        });
    });
});