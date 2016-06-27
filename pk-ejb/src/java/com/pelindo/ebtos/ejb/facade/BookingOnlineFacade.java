/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.pelindo.ebtos.ejb.facade;

import com.pelindo.ebtos.ejb.facade.local.BookingOnlineFacadeLocal;
import com.pelindo.ebtos.ejb.facade.remote.BookingOnlineFacadeRemote;
import com.pelindo.ebtos.model.db.BookingOnline;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 *
 * @author dycoder
 */
@Stateless
public class BookingOnlineFacade extends AbstractFacade<BookingOnline> implements BookingOnlineFacadeRemote, BookingOnlineFacadeLocal {
    @PersistenceContext(unitName = "pk-ejbPU")
    private EntityManager em;

    protected EntityManager getEntityManager() {
        return em;
    }

    public BookingOnlineFacade() {
        super(BookingOnline.class);
    }

    public List<Object[]> findBooking() {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("BookingOnline.Native.findBooking").getResultList();;
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }
    public List<Object[]> findBookingList(String cust_code) {
        List<Object[]> temp = new ArrayList<Object[]>();
        try {
            temp = getEntityManager().createNamedQuery("BookingOnline.Native.findBookingList").setParameter(1, cust_code).getResultList();;
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public Integer findByBookingCode(String booking_code) {
        Integer temp = null;
        try {
            temp = (Integer) getEntityManager().createNamedQuery("BookingOnline.Native.findByBookingCode").setParameter(1, booking_code).getSingleResult();
        } catch (NoResultException e) {
            temp = null;
        }
        return temp;
    }

    public BookingOnline findByBooking(String bookingCode) {
        try {
            return (BookingOnline) getEntityManager().createNamedQuery("BookingOnline.findByBookingCode")
                    .setParameter("bookingCode", bookingCode)
                    .getSingleResult();
        } catch (NoResultException nre) {
            return null;
        }
    }
}
