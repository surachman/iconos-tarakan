function cekTgl(tgl,errMsg, property) {

    document.getElementById(errMsg).innerHTML = '';
    var tglInput = document.getElementById(tgl).value;

                


//alert (ambilJamInput);
	

    //alert(tgl);
    //if empty string, no action
    if (tglInput.length < 1) {
        return true;
    }

    if (tglInput.length == 13) {
        var day = tglInput.substring(0, 2);
        var month = tglInput.substring(2, 4);
        var year = tglInput.substring(4, 8);

        var dayParse;
        var monthParse;

        if (day.substring(0, 1) == '0') {
            dayParse = day.substring(1, 2);
        } else {
            dayParse = day;
        }

        if (month.substring(0, 1) == '0') {
            monthParse = month.substring(1, 2);
        } else {
            monthParse = month;
        }

        if (parseInt(dayParse) > 0 && parseInt(dayParse) <= 31) {
            if (parseInt(monthParse) > 0 && parseInt(monthParse) <= 12) {
                
                

	
	
	
	var ambilJamInput = document.getElementById(tgl).value;
    var jamInput = ambilJamInput.substr(9, 4);
    var jam = ambilJamInput.substr(9, 2);
    var menit = ambilJamInput.substr(11, 2);
    var jamParse;
    var menitParse;
    //if empty string, no action
    if (jamInput.length < 1) {
        return true;
    }

    if (jamInput.length == 4) {
       

        if (jam.substring(0, 1) == '0') {
            jamParse = jam.substring(1, 2);
        } else {
            jamParse = jam;
        }

        if (menit.substring(0, 1) == '0') {
            menitParse = menit.substring(1, 2);
        } else {
            menitParse = menit;
        }

        if (parseInt(jamParse) >= 0 && parseInt(jamParse) <= 24) {

            if (parseInt(menitParse) >= 0 && parseInt(menitParse) <= 59) {
                
				
                            document.getElementById(tgl).value = day + '/' + month + '/' + year + ' ' + jam + ':' + menit;
			
               
                            //document.getElementById(tgl).value = jam + ':' + menit;
			
                return true;
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
			
                document.getElementById(tgl).focus();

                return false;
            }
        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';

            document.getElementById(tgl).focus();
            return false;
        }
    } else {
        if (jamInput.length == 5) {
            var jam = jamInput.substr(0, 2);
            var menit = jamInput.substr(2, 2);

            if (!isNaN(jam) && !isNaN(menit)) {

                var jamParse;
                var menitParse;

                if (jam.substring(0, 1) == '0') {
                    jamParse = jam.substring(1, 2);
                } else {
                    jamParse = jam;
                }

                if (menit.substring(0, 1) == '0') {
                    menitParse = menit.substring(1, 2);
                } else {
                    menitParse = menit;
                }

                if (parseInt(jamParse) >= 0 && parseInt(jamParse) <= 24) {
                    if (parseInt(menitParse) >= 0 && parseInt(menitParse) <= 59) {
                       document.getElementById(tgl).value = day + '/' + month + '/' + year + ' ' + jam + ':' + menit;
				
                        return true;
                    } else {
                        document.getElementById(errMsg).innerHTML = 'Invalid format Date';
	
                        document.getElementById(tgl).focus();
                        return false;
                    }
                } else {
                    document.getElementById(errMsg).innerHTML = 'Invalid format Date';
				
                    document.getElementById(tgl).focus();
                    return false;
                }
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
			
                document.getElementById(tgl).focus();
                return false;
            }

        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';
	
            document.getElementById(tgl).focus();
            return false;
        }
    }

                
                
                
                
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
                document.getElementById(tgl).focus();
			
                return false;
            }
        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';
			
            document.getElementById(tgl).focus();
            return false;
        }
    } else {
        if (tglInput.length == 16) {
            var day = tglInput.substring(0, 2);
            var month = tglInput.substring(3, 5);
            var year = tglInput.substring(6, 10);
            if (!isNaN(day) && !isNaN(month) && !isNaN(year)) {

                var dayParse;
                var monthParse;

                if (day.substring(0, 1) == '0') {
                    dayParse = day.substring(1, 2);
                } else {
                    dayParse = day;
                }

                if (month.substring(0, 1) == '0') {
                    monthParse = month.substring(1, 2);
                } else {
                    monthParse = month;
                }

                if (parseInt(dayParse) > 0 && parseInt(dayParse) <= 31) {
                    if (parseInt(monthParse) > 0 && parseInt(monthParse) <= 12) {
                        //document.getElementById(tgl).value = day + '/' + month + '/' + year;
                        
                        
                        
                        
    var ambilJamInput = document.getElementById(tgl).value;
    var jamInput = ambilJamInput.substr(11, 5);
                        
                        
                        
                         //if empty string, no action
    if (jamInput.length < 1) {
        return true;
    }

    if (jamInput.length == 4) {
       

	var jam = ambilJamInput.substr(11, 2);
    var menit = ambilJamInput.substr(13, 2);
    var jamParse;
    var menitParse;

        if (jam.substring(0, 1) == '0') {
            jamParse = jam.substring(1, 2);
        } else {
            jamParse = jam;
        }

        if (menit.substring(0, 1) == '0') {
            menitParse = menit.substring(1, 2);
        } else {
            menitParse = menit;
        }

        if (parseInt(jamParse) >= 0 && parseInt(jamParse) <= 24) {

            if (parseInt(menitParse) >= 0 && parseInt(menitParse) <= 59) {
                
				
                            document.getElementById(tgl).value = day + '/' + month + '/' + year + ' ' + jam + ':' + menit;
			
               
                            //document.getElementById(tgl).value = jam + ':' + menit;
			
                return true;
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
			
                document.getElementById(tgl).focus();

                return false;
            }
        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';

            document.getElementById(tgl).focus();
            return false;
        }
    } else {
        if (jamInput.length == 5) {
   var jam = ambilJamInput.substr(11, 2);
    var menit = ambilJamInput.substr(14, 2);
    var jamParse;
    var menitParse;

            if (!isNaN(jam) && !isNaN(menit)) {

              

                if (jam.substring(0, 1) == '0') {
                    jamParse = jam.substring(1, 2);
                } else {
                    jamParse = jam;
                }

                if (menit.substring(0, 1) == '0') {
                    menitParse = menit.substring(1, 2);
                } else {
                    menitParse = menit;
                }

                if (parseInt(jamParse) >= 0 && parseInt(jamParse) <= 24) {
                    if (parseInt(menitParse) >= 0 && parseInt(menitParse) <= 59) {
                       document.getElementById(tgl).value = day + '/' + month + '/' + year + ' ' + jam + ':' + menit;
				
                        return true;
                    } else {
                        document.getElementById(errMsg).innerHTML = 'Invalid format Date';
	
                        document.getElementById(tgl).focus();
                        return false;
                    }
                } else {
                    document.getElementById(errMsg).innerHTML = 'Invalid format Date';
				
                    document.getElementById(tgl).focus();
                    return false;
                }
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
			
                document.getElementById(tgl).focus();
                return false;
            }

        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';
	
            document.getElementById(tgl).focus();
            return false;
        }
    }
                        
                        
                        
                        
                        
                        
                        
					
                       
                    } else {
                        document.getElementById(errMsg).innerHTML = 'Invalid format Date';
						
                        document.getElementById(tgl).focus();
                        return false;
                    }
                } else {
                    document.getElementById(errMsg).innerHTML = 'Invalid format Date';
					
                    document.getElementById(tgl).focus();
                    return false;
                }
            } else {
                document.getElementById(errMsg).innerHTML = 'Invalid format Date';
		
                document.getElementById(tgl).focus();
                return false;
            }

        } else {
            document.getElementById(errMsg).innerHTML = 'Invalid format Date';
		
            document.getElementById(tgl).focus();
            return false;
        }
    }


}