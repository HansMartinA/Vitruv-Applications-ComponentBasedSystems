package mir.routines.javaToUml;

import java.io.IOException;
import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Parameter;
import org.emftext.language.java.parameters.OrdinaryParameter;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RenameJavaParameterRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RenameJavaParameterRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getCorrepondenceSourceUParam(final OrdinaryParameter jParam) {
      return jParam;
    }
    
    public EObject getElement1(final OrdinaryParameter jParam, final Parameter uParam) {
      return uParam;
    }
    
    public void update0Element(final OrdinaryParameter jParam, final Parameter uParam) {
      String _name = jParam.getName();
      uParam.setName(_name);
    }
  }
  
  public RenameJavaParameterRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final OrdinaryParameter jParam) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.javaToUml.RenameJavaParameterRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.javaToUml.RoutinesFacade(getExecutionState(), this);
    this.jParam = jParam;
  }
  
  private OrdinaryParameter jParam;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RenameJavaParameterRoutine with input:");
    getLogger().debug("   OrdinaryParameter: " + this.jParam);
    
    Parameter uParam = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceUParam(jParam), // correspondence source supplier
    	Parameter.class,
    	(Parameter _element) -> true, // correspondence precondition checker
    	null);
    if (uParam == null) {
    	return;
    }
    initializeRetrieveElementState(uParam);
    // val updatedElement userExecution.getElement1(jParam, uParam);
    userExecution.update0Element(jParam, uParam);
    
    postprocessElementStates();
  }
}
