jQuery.ebtosWeighing = {
    value: 0,
    isStable: false,
    isWeighingConnected: false,
    resetUI: function() {}
};

function ebtosWeighingValueChanged(value, isStable, isWeighingConnected) {
    jQuery.ebtosWeighing.value = parseFloat(value);
    jQuery.ebtosWeighing.isStable = isStable;
    jQuery.ebtosWeighing.isWeighingConnected = isWeighingConnected;
    jQuery.ebtosWeighing.resetUI();
}