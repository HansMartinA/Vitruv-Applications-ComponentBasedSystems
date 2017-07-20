package mir.routines.pcmToUml;

import java.io.IOException;
import mir.routines.pcmToUml.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.uml2.uml.Classifier;
import org.eclipse.uml2.uml.DataType;
import org.palladiosimulator.pcm.repository.CompositeDataType;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class RemoveCompositeDataTypeParentRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private RemoveCompositeDataTypeParentRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final CompositeDataType dataType, final CompositeDataType parent, final DataType compositeType, final DataType parentType) {
      return compositeType;
    }
    
    public void update0Element(final CompositeDataType dataType, final CompositeDataType parent, final DataType compositeType, final DataType parentType) {
      EList<Classifier> _parents = compositeType.parents();
      _parents.remove(parentType);
    }
    
    public EObject getCorrepondenceSourceCompositeType(final CompositeDataType dataType, final CompositeDataType parent) {
      return dataType;
    }
    
    public EObject getCorrepondenceSourceParentType(final CompositeDataType dataType, final CompositeDataType parent, final DataType compositeType) {
      return parent;
    }
  }
  
  public RemoveCompositeDataTypeParentRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final CompositeDataType dataType, final CompositeDataType parent) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.pcmToUml.RemoveCompositeDataTypeParentRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.pcmToUml.RoutinesFacade(getExecutionState(), this);
    this.dataType = dataType;this.parent = parent;
  }
  
  private CompositeDataType dataType;
  
  private CompositeDataType parent;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine RemoveCompositeDataTypeParentRoutine with input:");
    getLogger().debug("   CompositeDataType: " + this.dataType);
    getLogger().debug("   CompositeDataType: " + this.parent);
    
    DataType compositeType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceCompositeType(dataType, parent), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (compositeType == null) {
    	return;
    }
    registerObjectUnderModification(compositeType);
    DataType parentType = getCorrespondingElement(
    	userExecution.getCorrepondenceSourceParentType(dataType, parent, compositeType), // correspondence source supplier
    	DataType.class,
    	(DataType _element) -> true, // correspondence precondition checker
    	null);
    if (parentType == null) {
    	return;
    }
    registerObjectUnderModification(parentType);
    // val updatedElement userExecution.getElement1(dataType, parent, compositeType, parentType);
    userExecution.update0Element(dataType, parent, compositeType, parentType);
    
    postprocessElements();
  }
}