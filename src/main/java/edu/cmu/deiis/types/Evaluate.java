

/* First created by JCasGen Sun Sep 22 19:00:09 EDT 2013 */
package edu.cmu.deiis.types;

import org.apache.uima.jcas.JCas; 
import org.apache.uima.jcas.JCasRegistry;
import org.apache.uima.jcas.cas.TOP_Type;



/** Type containing the precision feature which evaluates the information processing pipeline.
 * Updated by JCasGen Sun Sep 22 19:02:56 EDT 2013
 * XML source: C:/Users/DISHAN/Desktop/workspace/hw2-dishang/src/main/resources/descriptors/deiis_types.xml
 * @generated */
public class Evaluate extends Annotation {
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int typeIndexID = JCasRegistry.register(Evaluate.class);
  /** @generated
   * @ordered 
   */
  @SuppressWarnings ("hiding")
  public final static int type = typeIndexID;
  /** @generated  */
  @Override
  public              int getTypeIndexID() {return typeIndexID;}
 
  /** Never called.  Disable default constructor
   * @generated */
  protected Evaluate() {/* intentionally empty block */}
    
  /** Internal - constructor used by generator 
   * @generated */
  public Evaluate(int addr, TOP_Type type) {
    super(addr, type);
    readObject();
  }
  
  /** @generated */
  public Evaluate(JCas jcas) {
    super(jcas);
    readObject();   
  } 

  /** @generated */  
  public Evaluate(JCas jcas, int begin, int end) {
    super(jcas);
    setBegin(begin);
    setEnd(end);
    readObject();
  }   

  /** <!-- begin-user-doc -->
    * Write your own initialization here
    * <!-- end-user-doc -->
  @generated modifiable */
  private void readObject() {/*default - does nothing empty block */}
     
 
    
  //*--------------*
  //* Feature: precision

  /** getter for precision - gets Stores the precision value computed by the system.
   * @generated */
  public double getPrecision() {
    if (Evaluate_Type.featOkTst && ((Evaluate_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluate");
    return jcasType.ll_cas.ll_getDoubleValue(addr, ((Evaluate_Type)jcasType).casFeatCode_precision);}
    
  /** setter for precision - sets Stores the precision value computed by the system. 
   * @generated */
  public void setPrecision(double v) {
    if (Evaluate_Type.featOkTst && ((Evaluate_Type)jcasType).casFeat_precision == null)
      jcasType.jcas.throwFeatMissing("precision", "edu.cmu.deiis.types.Evaluate");
    jcasType.ll_cas.ll_setDoubleValue(addr, ((Evaluate_Type)jcasType).casFeatCode_precision, v);}    
  }

    