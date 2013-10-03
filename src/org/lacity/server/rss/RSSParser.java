package org.lacity.server.rss;

import java.io.Console;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.Scanner;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.DocumentBuilder;

import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;
import org.xml.sax.SAXParseException; 

public class RSSParser {

	  static final String TITLE = "title";
	  static final String DESCRIPTION = "description";
	  static final String LINK = "link";
	  static final String PUB_DATE = "pubDate";
	  static final String JOB_ID = "joblisting:jobId";
	  static final String MINIMUM_SALARY = "joblisting:minimumSalary";
	  static final String MAXIMUM_SALARY= "joblisting:maximumSalary";
	  static final String SALARY_CURRENCY= "joblisting:salaryCurrency";
	  static final String SALARY_INTERVAL= "joblisting:salaryInterval";
	  static final String JOB_TYPE= "joblisting:jobType";
	  static final String LOCATION= "joblisting:location";
	  static final String STATE= "joblisting:state";
	  static final String DEPARTMENT= "joblisting:department";
	  static final String CATEGORIES = "joblisting:categories";
	  static final String CATEGORY = "category";

	  final URL url;

	  public RSSParser() {
	    try {
	      this.url = new URL("http://agency.governmentjobs.com/jobfeed.cfm?agency=lacity");
	    } catch (MalformedURLException e) {
	      throw new RuntimeException(e);
	    }
	  
	  }
	  
	  private void RSSReader()
	  {
		  try
		  {
			  DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	          Document doc = docBuilder.parse ( url.openStream());
	
	          // normalize text representation
	          doc.getDocumentElement ().normalize ();
	
	          NodeList listOfJobs = doc.getElementsByTagName(TITLE);
	          int totalJobs = listOfJobs.getLength();
	          System.out.println("Total number of Jobs = " +totalJobs);
	
	          for(int s=0; s<totalJobs ; s++){
	
	              Node firstJobsNode = listOfJobs.item(s);
	              System.out.println("Title : " + firstJobsNode.getTextContent().trim());
	                  
	           }


	      }catch (SAXParseException err) {
	      System.out.println ("** Parsing error" + ", line " 
	           + err.getLineNumber () + ", uri " + err.getSystemId ());
	      System.out.println(" " + err.getMessage ());
	
	      }catch (SAXException e) {
	      Exception x = e.getException ();
	      ((x == null) ? e : x).printStackTrace ();
	
	      }catch (Throwable t) {
	      t.printStackTrace ();
	      }
	  
	  }
	  
	  private void RSSSearchByTitle(String title)
	  {
		  try
		  {
			  DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	          Document doc = docBuilder.parse ( url.openStream());
	
	          // normalize text representation
	          doc.getDocumentElement ().normalize ();
	
	          NodeList listOfJobs = doc.getElementsByTagName(TITLE);
	          int totalJobs = listOfJobs.getLength();
	          String nodeValue = null;
	          int flag = 0;
	          
	          System.out.println("Currently open jobs with the search string: ");
	          for(int s=0; s<totalJobs ; s++){
	
	              Node firstJobsNode = listOfJobs.item(s);
	              nodeValue = firstJobsNode.getTextContent().toLowerCase().trim();
	              
	              if (nodeValue.contains(title)){
	            	  System.out.println(nodeValue);
	            	  flag = 1;
	              }
	                  
	           }
	          if (flag == 0)
	        	  System.out.println("None");


	      }catch (SAXParseException err) {
	      System.out.println ("** Parsing error" + ", line " 
	           + err.getLineNumber () + ", uri " + err.getSystemId ());
	      System.out.println(" " + err.getMessage ());
	
	      }catch (SAXException e) {
	      Exception x = e.getException ();
	      ((x == null) ? e : x).printStackTrace ();
	
	      }catch (Throwable t) {
	      t.printStackTrace ();
	      }
	  
	  }
	  
	  private void RSSSearchByID(String id)
	  {
		  try
		  {
			  DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
	          DocumentBuilder docBuilder = docBuilderFactory.newDocumentBuilder();
	          Document doc = docBuilder.parse ( url.openStream());
	
	          // normalize text representation
	          doc.getDocumentElement ().normalize ();
	
	          NodeList listOfJobs = doc.getElementsByTagName(JOB_ID);
	          int totalJobs = listOfJobs.getLength();
	          String nodeValue = null;
	
	          for(int s=0; s<totalJobs ; s++){
	
	              Node firstJobsNode = listOfJobs.item(s);
	              nodeValue = firstJobsNode.getTextContent().toLowerCase().trim();

	              if (nodeValue.contentEquals(id)){
	            	  System.out.println("The job " +id +" is currently open");
	            	  return;
	              }
	                  
	           }
	          System.out.println("The job " +id +" is not open");



	      }catch (SAXParseException err) {
	      System.out.println ("** Parsing error" + ", line " 
	           + err.getLineNumber () + ", uri " + err.getSystemId ());
	      System.out.println(" " + err.getMessage ());
	
	      }catch (SAXException e) {
	      Exception x = e.getException ();
	      ((x == null) ? e : x).printStackTrace ();
	
	      }catch (Throwable t) {
	      t.printStackTrace ();
	      }
	  
	  }
	  
	  public static void main(String argv[]){
		  RSSParser parse = new RSSParser();
		  String title, id;
		  
		  parse.RSSReader();
		  System.out.println();
		  System.out.println("Title to search:");
		  Scanner s = new Scanner(System.in);
		  title = s.next().toString();
		  parse.RSSSearchByTitle(title.toLowerCase().trim());
		  System.out.println();
		  System.out.println("ID to search:");
		  id = s.next().toString();
		  parse.RSSSearchByID(id.toLowerCase().trim());
	  }
} 
