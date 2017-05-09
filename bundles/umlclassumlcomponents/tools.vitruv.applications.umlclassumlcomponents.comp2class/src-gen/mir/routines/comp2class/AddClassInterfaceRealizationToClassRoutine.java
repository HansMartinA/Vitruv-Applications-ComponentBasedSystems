package mir.routines.comp2class;

import java.io.IOException;
import mir.routines.comp2class.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Component;
import org.eclipse.uml2.uml.Interface;
import org.eclipse.uml2.uml.InterfaceRealization;
import org.eclipse.uml2.uml.NamedElement;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class AddClassInterfaceRealizationToClassRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private AddClassInterfaceRealizationToClassRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp, final InterfaceRealization classIFRealization, final Interface classInterface) {
      return classIFRealization;
    }
    
    public void update0Element(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp, final InterfaceRealization classIFRealization, final Interface classInterface) {
      EList<NamedElement> _suppliers = classIFRealization.getSuppliers();
      _suppliers.add(classInterface);
      classIFRealization.setContract(classInterface);
    }
    
    public EObject getCorrepondenceSourceClassInterface(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp, final InterfaceRealization classIFRealization) {
      return compInterface;
    }
    
    public EObject getCorrepondenceSourceClassIFRealization(final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp) {
      return iFRealizationOrUsage;
    }
  }
  
  public AddClassInterfaceRealizationToClassRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final NamedElement iFRealizationOrUsage, final Interface compInterface, final Component umlComp) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.comp2class.AddClassInterfaceRealizationToClassRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.comp2class.RoutinesFacade(getExecutionState(), this);
    this.iFRealizationOrUsage = iFRealizationOrUsage;this.compInterface = compInterface;this.umlComp = umlComp;
  }
  
  private NamedElement iFRealizationOrUsage;
  
  private Interface compInterface;
  
  private Component umlComp;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine AddClassInterfaceRealizationToClassRoutine with input:");
    getLogger().debug("   NamedElement: " + this.iFRealizationOrUsage);
    getLogger().debug("   Interface: " + this.compInterface);
    getLogger().debug("   Component: " + this.umlComp);
    
    InterfaceRealization classIFRealization = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassIFRealization(iFRealizationOrUsage, compInterface, umlComp), // correspondence source supplier
    	InterfaceRealization.class,
    	(InterfaceRealization _element) -> true, // correspondence precondition checker
    	null);
    if (classIFRealization == null) {
    	return;
    }
    registerObjectUnderModification(classIFRealization);
    Interface classInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceClassInterface(iFRealizationOrUsage, compInterface, umlComp, classIFRealization), // correspondence source supplier
    	Interface.class,
    	(Interface _element) -> true, // correspondence precondition checker
    	null);
    if (classInterface == null) {
    	return;
    }
    registerObjectUnderModification(classInterface);
    // val updatedElement userExecution.getElement1(iFRealizationOrUsage, compInterface, umlComp, classIFRealization, classInterface);
    userExecution.update0Element(iFRealizationOrUsage, compInterface, umlComp, classIFRealization, classInterface);
    
    postprocessElements();
  }
}
