package com.springRestHbn.service;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.springRestHbn.model.RequestDTO;
import com.springRestHbn.model.ResponseDTO;


enum SubscriptionType {
	DAILY,
	WEEKLY,
	MONTHLY
}
enum DayOfWeek {
	SUNDAY,
	MONDAY,
	TUESDAY,
	WEDNESDAY,
	THURSDAY,
	FRIDAY,
	SATURDAY
}

@Service("SubscriptionService")
@Transactional
public class SubscriptionServiceImpl implements SubscriptionService {
	DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
	
	public ResponseDTO subscription(RequestDTO request) {
		ResponseDTO resp = new ResponseDTO();
		
		//set invoice date
		calculateInvoiceDate(resp, request);
		
		if(resp.getStatus()==null || resp.getStatus().isEmpty()) {
			//set amount
			resp.setAmount(request.getAmount());
			
			//set subscription type
			resp.setSubscriptionType(request.getSubscriptionType());
			
			//set status : success
			resp.setStatus("SUCCESS");
		}
		
		return resp;
	}

	private void calculateInvoiceDate(ResponseDTO resp, RequestDTO request) {
		SubscriptionType subscriptionType;
		try {
			subscriptionType = SubscriptionType.valueOf(request.getSubscriptionType());
		} catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Subscription Type");
	    	return;
		}
		
		switch(subscriptionType) {
			case DAILY:
				calculateDailyInvoiceDate(resp, request);
				break;
			case WEEKLY:
				calculateWeeklyInvoiceDate(resp, request);
				break;
			case MONTHLY:
				calculateMonthlyInvoiceDate(resp, request);
				break;
				
		}
	}
	
	private void calculateDailyInvoiceDate(ResponseDTO resp, RequestDTO request) {
		List<String> invoiceDate = new ArrayList<String>();
		
		Calendar calendar = new GregorianCalendar();
	    Calendar endCalendar = new GregorianCalendar();
	    
	    try {
		    calendar.setTime(convertStringToDate(request.getStartSubscriptionDate()));
		    
		    endCalendar.setTime(convertStringToDate(request.getEndSubscriptionDate()));
		    endCalendar.add(Calendar.DATE, 1);
	    } catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Date");
	    	return;
	    }
	 
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        invoiceDate.add(convertDateToString(result));
	        calendar.add(Calendar.DATE, 1);
	    }
	    
	    resp.setInvoiceDates(invoiceDate);
	}
	
	private void calculateWeeklyInvoiceDate(ResponseDTO resp, RequestDTO request) {
		List<String> invoiceDate = new ArrayList<String>();

		DayOfWeek dayOfWeek;
		try {
			dayOfWeek = DayOfWeek.valueOf(request.getPaymentDay());
		} catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Day Of Week");
	    	return;
		}
		
		int dayOfWeekInt = 0;
		switch(dayOfWeek) {
			case SATURDAY:
				dayOfWeekInt++;
			case FRIDAY:
				dayOfWeekInt++;
			case THURSDAY:
				dayOfWeekInt++;
			case WEDNESDAY:
				dayOfWeekInt++;
			case TUESDAY:
				dayOfWeekInt++;
			case MONDAY:
				dayOfWeekInt++;
			case SUNDAY:
				dayOfWeekInt++;
				break;
		}
		
		Calendar calendar = new GregorianCalendar();
	    Calendar endCalendar = new GregorianCalendar();
	    
	    try {
		    calendar.setTime(convertStringToDate(request.getStartSubscriptionDate()));
		    
		    endCalendar.setTime(convertStringToDate(request.getEndSubscriptionDate()));
		    endCalendar.add(Calendar.DATE, 1);
	    } catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Date");
	    	return;
	    }
	    
	    int day = calendar.get(Calendar.DAY_OF_WEEK);
	    int diffDay = dayOfWeekInt - day;
	    if(diffDay < 0) {
	    	diffDay += 7;
	    }
	    
	    calendar.add(Calendar.DATE, diffDay);
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        invoiceDate.add(convertDateToString(result));
	        calendar.add(Calendar.DATE, 7);
	    }
	    
	    resp.setInvoiceDates(invoiceDate);
	}
	
	private void calculateMonthlyInvoiceDate(ResponseDTO resp, RequestDTO request) {
		List<String> invoiceDate = new ArrayList<String>();

		int dateOfMonth = 0;
		try {
			dateOfMonth = Integer.parseInt(request.getPaymentDay());
		} catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Date Of Month");
	    	return;
		}
		if(dateOfMonth < 1 || dateOfMonth > 31) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Date Of Month");
	    	return;
		}
		
		Calendar calendar = new GregorianCalendar();
	    Calendar endCalendar = new GregorianCalendar();
	    
	    try {
		    calendar.setTime(convertStringToDate(request.getStartSubscriptionDate()));
		    
		    endCalendar.setTime(convertStringToDate(request.getEndSubscriptionDate()));
		    endCalendar.add(Calendar.DATE, 1);
	    } catch(Exception e) {
	    	resp.setStatus("FAIL");
	    	resp.setMessage("Invalid Date");
	    	return;
	    }
	    
	    int day = calendar.get(Calendar.DAY_OF_MONTH);
	    int month = calendar.get(Calendar.MONTH);
	    if(dateOfMonth > day) {
	    	calendar.set(GregorianCalendar.DATE, dateOfMonth);
	    	while(month != calendar.get(Calendar.MONTH)) {
	    		calendar.add(Calendar.DATE, -1);
	    	}
	    }
	    if(dateOfMonth < day) {
	    	calendar.add(Calendar.DATE, dateOfMonth - day);
	    	calendar.add(Calendar.MONTH, 1);
	    }
	    
	    while (calendar.before(endCalendar)) {
	        Date result = calendar.getTime();
	        invoiceDate.add(convertDateToString(result));
	        int currDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH);
	        if(calendar.get(Calendar.DAY_OF_MONTH) != dateOfMonth) {
	        	calendar.add(Calendar.MONTH, 1);
	        	calendar.add(Calendar.DATE, dateOfMonth - currDayOfMonth);
	        } else {
	        	calendar.add(Calendar.MONTH, 1);
	        }
	    }
	    
	    resp.setInvoiceDates(invoiceDate);
	}
	
	private String convertDateToString(Date date) {
		return dateFormat.format(date);  
	}
	
	private Date convertStringToDate(String date) throws ParseException {
		return dateFormat.parse(date); 
	}
	
}
