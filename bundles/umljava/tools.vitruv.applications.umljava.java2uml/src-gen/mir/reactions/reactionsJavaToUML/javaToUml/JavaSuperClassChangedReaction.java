package mir.reactions.reactionsJavaToUML.javaToUml;

import mir.routines.javaToUml.RoutinesFacade;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.types.TypeReference;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractReactionRealization;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;
import tools.vitruv.framework.change.echange.EChange;
import tools.vitruv.framework.change.echange.feature.reference.ReplaceSingleValuedEReference;
import tools.vitruv.framework.userinteraction.UserInteracting;

@SuppressWarnings("all")
class JavaSuperClassChangedReaction extends AbstractReactionRealization {
  public JavaSuperClassChangedReaction(final UserInteracting userInteracting) {
    super(userInteracting);
  }
  
  public void executeReaction(final EChange change) {
    ReplaceSingleValuedEReference<org.emftext.language.java.classifiers.Class, TypeReference> typedChange = (ReplaceSingleValuedEReference<org.emftext.language.java.classifiers.Class, TypeReference>)change;
    org.emftext.language.java.classifiers.Class affectedEObject = typedChange.getAffectedEObject();
    EReference affectedFeature = typedChange.getAffectedFeature();
    TypeReference oldValue = typedChange.getOldValue();
    TypeReference newValue = typedChange.getNewValue();
    mir.routines.javaToUml.RoutinesFacade routinesFacade = new mir.routines.javaToUml.RoutinesFacade(this.executionState, this);
    mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassChangedReaction.ActionUserExecution userExecution = new mir.reactions.reactionsJavaToUML.javaToUml.JavaSuperClassChangedReaction.ActionUserExecution(this.executionState, this);
    userExecution.callRoutine1(affectedEObject, affectedFeature, oldValue, newValue, routinesFacade);
  }
  
  public static Class<? extends EChange> getExpectedChangeType() {
    return ReplaceSingleValuedEReference.class;
  }
  
  private boolean checkChangeProperties(final EChange change) {
    ReplaceSingleValuedEReference<org.emftext.language.java.classifiers.Class, TypeReference> relevantChange = (ReplaceSingleValuedEReference<org.emftext.language.java.classifiers.Class, TypeReference>)change;
    if (!(relevantChange.getAffectedEObject() instanceof org.emftext.language.java.classifiers.Class)) {
    	return false;
    }
    if (!relevantChange.getAffectedFeature().getName().equals("extends")) {
    	return false;
    }
    if (relevantChange.isFromNonDefaultValue() && !(relevantChange.getOldValue() instanceof TypeReference)) {
    	return false;
    }
    if (relevantChange.isToNonDefaultValue() && !(relevantChange.getNewValue() instanceof TypeReference)) {
    	return false;
    }
    return true;
  }
  
  public boolean checkPrecondition(final EChange change) {
    if (!(change instanceof ReplaceSingleValuedEReference)) {
    	return false;
    }
    getLogger().debug("Passed change type check of reaction " + this.getClass().getName());
    if (!checkChangeProperties(change)) {
    	return false;
    }
    getLogger().debug("Passed change properties check of reaction " + this.getClass().getName());
    getLogger().debug("Passed complete precondition check of reaction " + this.getClass().getName());
    return true;
  }
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final org.emftext.language.java.classifiers.Class affectedEObject, final EReference affectedFeature, final TypeReference oldValue, final TypeReference newValue, @Extension final RoutinesFacade _routinesFacade) {
      _routinesFacade.clearUmlSuperClassifiers(affectedEObject);
      _routinesFacade.addUmlSuperClassifier(affectedEObject, newValue);
    }
  }
}
