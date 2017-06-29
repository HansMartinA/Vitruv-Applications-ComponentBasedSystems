package mir.routines.umlToJavaMethod;

import java.io.IOException;
import mir.routines.umlToJavaMethod.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Operation;
import org.emftext.language.java.members.ClassMethod;
import tools.vitruv.applications.umljava.util.java.JavaModifierUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class SetJavaMethodFinalRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private SetJavaMethodFinalRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceJMethod(final Operation uOperation, final Boolean isFinal) {
      return uOperation;
    }
    
    public EObject getElement1(final Operation uOperation, final Boolean isFinal, final ClassMethod jMethod) {
      return jMethod;
    }
    
    public void update0Element(final Operation uOperation, final Boolean isFinal, final ClassMethod jMethod) {
      JavaModifierUtil.setFinal(jMethod, (isFinal).booleanValue());
    }
  }
  
  public SetJavaMethodFinalRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Operation uOperation, final Boolean isFinal) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaMethod.SetJavaMethodFinalRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaMethod.RoutinesFacade(getExecutionState(), this);
    this.uOperation = uOperation;this.isFinal = isFinal;
  }
  
  private Operation uOperation;
  
  private Boolean isFinal;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine SetJavaMethodFinalRoutine with input:");
    getLogger().debug("   Operation: " + this.uOperation);
    getLogger().debug("   Boolean: " + this.isFinal);
    
    ClassMethod jMethod = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceJMethod(uOperation, isFinal), // correspondence source supplier
    	ClassMethod.class,
    	(ClassMethod _element) -> true, // correspondence precondition checker
    	null);
    if (jMethod == null) {
    	return;
    }
    registerObjectUnderModification(jMethod);
    // val updatedElement userExecution.getElement1(uOperation, isFinal, jMethod);
    userExecution.update0Element(uOperation, isFinal, jMethod);
    
    postprocessElements();
  }
}