package edu.cmu.deiis.types;

import java.util.Iterator;

import org.apache.uima.analysis_component.JCasAnnotator_ImplBase;
import org.apache.uima.analysis_engine.AnalysisEngineProcessException;
import org.apache.uima.cas.FSIndex;
import org.apache.uima.jcas.JCas;


public class QuestionAnnotator extends JCasAnnotator_ImplBase {

  @Override
  public void process(JCas aJCas) throws AnalysisEngineProcessException {
    // TODO Auto-generated method stub
    
    //Take text document and annotate the question
    
    String docText = aJCas.getDocumentText() ;
    String[] lines = docText.split("\n") ;
    String question = lines[0] ;
    Question annotation = new Question (aJCas) ;
    annotation.setBegin(2) ;
    annotation.setEnd(question.length() - 1) ;
    annotation.setCasProcessorId("QuestionAnnotator") ;
    annotation.setConfidence(1.0) ;
    annotation.addToIndexes() ;
    
    
  }

}
