package com.xmlSchema.XMLSchema;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;
import javax.xml.datatype.DatatypeConfigurationException;
import javax.xml.datatype.DatatypeFactory;
import javax.xml.datatype.XMLGregorianCalendar;

import com.fasterxml.jackson.core.JsonGenerationException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

public class App {

	public static void main(String[] args) throws JAXBException, ParseException, DatatypeConfigurationException, JsonGenerationException, JsonMappingException, FileNotFoundException, IOException {
		marshalling();
		unmarshalling();
		serialize();
		deserialize();
		
	}

	private static void marshalling() throws JAXBException, ParseException, DatatypeConfigurationException {
		try {
			File file = new File(
					"C:\\WinSoftwareInstall\\workspace\\firstProject\\XmlSchema\\src\\main\\java\\com\\xmlSchema\\XMLSchema\\output.xml");
			JAXBContext schema = JAXBContext.newInstance(Customer.class);
			Marshaller m = schema.createMarshaller();
			m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
			Customer customer = createCustomer();
			m.marshal(customer, file);
		} catch (Exception e) {
			e.printStackTrace();
		}

	}

	private static Customer createCustomer() throws ParseException, DatatypeConfigurationException {
		
		Address address=new Address();
		address.setStreetName("Colorado Blvd");
		address.setApt("23");
		address.setCity("Denton");
		address.setState("Texas");
		address.setZipCode("76208");
		
		Payment payment=new Payment();
		payment.setCardNumber(100000);
		payment.setCardName("Chase");
		payment.setCardType("debitcard");
		
		XMLGregorianCalendar dateFrom = dateTodateString("2014-04-10T00:00:00");
    	payment.setDateFrom(dateFrom);
    	
    	XMLGregorianCalendar dateTill =  dateTodateString("2015-04-10T00:00:00");
    	payment.setDateFrom(dateTill);
		
		Customer customer=new Customer();
		customer.setName("Tom Cruse");
		customer.setId(101);
		customer.setAnnualSalary(2001.01);
		XMLGregorianCalendar dob = dateTodateString("1990-04-23T00:00:00");
    	customer.setDateOfBirth(dob);
	
    	return customer;
		}

	 private static XMLGregorianCalendar dateTodateString(String string) throws ParseException, DatatypeConfigurationException {
	    	
	    	XMLGregorianCalendar xgc = null;
			Date d;
			SimpleDateFormat sdm;
			GregorianCalendar gc;
			 
			sdm = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
			d = sdm.parse(string);        
			gc = (GregorianCalendar)GregorianCalendar.getInstance();
		    gc.setTime(d);
			xgc = DatatypeFactory.newInstance().newXMLGregorianCalendar(gc);
		    return xgc;
		}
	 
	  private static void unmarshalling() throws JAXBException {
	    	
	    	try {
	    	File file = new File("C:\\\\WinSoftwareInstall\\\\workspace\\\\firstProject\\\\XmlSchema\\\\src\\\\main\\\\java\\\\com\\\\xmlSchema\\\\XMLSchema\\\\output.xml");
			JAXBContext jc = JAXBContext.newInstance(Customer.class);
			Unmarshaller um = jc.createUnmarshaller();
			Customer customer = (Customer) um.unmarshal(file);
			System.out.println("" + customer.toString());
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	 
	 private static void serialize() throws ParseException, DatatypeConfigurationException, JsonGenerationException, JsonMappingException, FileNotFoundException, IOException {
	    	try {
		    	ObjectMapper obj = new ObjectMapper();
		    	Customer customer = createCustomer();
		    	obj.configure(SerializationFeature.INDENT_OUTPUT, true);
				obj.writeValue(System.out, customer);
		        obj.writeValue(new PrintWriter("C:\\WinSoftwareInstall\\workspace\\firstProject\\XmlSchema\\src\\main\\java\\com\\xmlSchema\\XMLSchema\\customer.json"), customer);
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }
	    
	    private static void deserialize() throws IOException {
	    	try {
	    		byte[] data = Files.readAllBytes(Paths.get("C:\\\\WinSoftwareInstall\\\\workspace\\\\firstProject\\\\XmlSchema\\\\src\\\\main\\\\java\\\\com\\\\xmlSchema\\\\XMLSchema\\\\customer.json"));
	        	ObjectMapper obj = new ObjectMapper();
	        	Customer customer = obj.readValue(data, Customer.class);
	        	System.out.println(customer.toString());
	    	} catch(Exception e) {
	    		e.printStackTrace();
	    	}
	    }

	

}
