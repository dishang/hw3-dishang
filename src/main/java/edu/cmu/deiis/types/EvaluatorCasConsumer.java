package edu.cmu.deiis.types;
           
 
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

import org.apache.uima.cas.CAS;
import org.apache.uima.cas.CASException;
import org.apache.uima.cas.FSIterator;
import org.apache.uima.cas.impl.XCASSerializer;
import org.apache.uima.collection.CasConsumer_ImplBase;
import org.apache.uima.examples.SourceDocumentInformation;
import org.apache.uima.jcas.JCas;
import org.apache.uima.resource.ResourceInitializationException;
import org.apache.uima.resource.ResourceProcessException;
import org.apache.uima.util.XMLSerializer;
import org.xml.sax.SAXException;


public class EvaluatorCasConsumer extends CasConsumer_ImplBase {
  
  private int mDocNum;

  public void initialize() throws ResourceInitializationException {
    mDocNum = 0;
    
  }

 
  public void processCas(CAS aCAS) throws ResourceProcessException {
    JCas jcas;
    try {
      jcas = aCAS.getJCas();
    } catch (CASException e) {
      throw new ResourceProcessException(e);
    }

    // retrieve the filename of the input file from the CAS
    FSIterator it = jcas.getAnnotationIndex(Evaluate.type).iterator();
    
    if (it.hasNext()) {
      mDocNum ++ ;
      Evaluate docScore = (Evaluate) it.next();
      System.out.println("Document Number:"+ mDocNum + " , Precision Score:" + docScore.getPrecision()) ;
    }
  }
  
}
