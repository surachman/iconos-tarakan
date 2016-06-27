/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade.local;

import com.pelindo.ebtos.model.db.BookingOnline;
import java.util.List;
import javax.ejb.Local;

/**
 *
 * @author dycoder
 */
@Local
public interface BookingOnlineFacadeLocal {

    void create(BookingOnline bookingOnline);

    void edit(BookingOnline bookingOnline);

    void remove(BookingOnline bookingOnline);

    BookingOnline find(Object id);

    List<BookingOnline> findAll();

    List<BookingOnline> findRange(int[] range);

    int count();

    List<Object[]> findBooking();

    List<Object[]> findBookingList(String cust_code);

    Integer findByBookingCode(String booking_code);

    public com.pelindo.ebtos.model.db.BookingOnline findByBooking(java.lang.String bookingCode);

}
