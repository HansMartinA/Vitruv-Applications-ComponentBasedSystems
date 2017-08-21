package mir.routines.java2PcmMethod;

import java.io.IOException;
import mir.routines.java2PcmMethod.RoutinesFacade;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.emftext.language.java.classifiers.ConcreteClassifier;
import org.emftext.language.java.members.InterfaceMethod;
import org.palladiosimulator.pcm.repository.OperationInterface;
import org.palladiosimulator.pcm.repository.OperationSignature;
import org.palladiosimulator.pcm.repository.impl.RepositoryFactoryImpl;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreatePCMSignatureRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreatePCMSignatureRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public EObject getElement1(final InterfaceMethod method, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return pcmInterface;
    }
    
    public void updateOperationSignatureElement(final InterfaceMethod method, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      operationSignature.setEntityName(method.getName());
      operationSignature.setInterface__OperationSignature(pcmInterface);
    }
    
    public void update0Element(final InterfaceMethod method, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      EList<OperationSignature> _signatures__OperationInterface = pcmInterface.getSignatures__OperationInterface();
      _signatures__OperationInterface.add(operationSignature);
    }
    
    public EObject getCorrepondenceSourcePcmInterface(final InterfaceMethod method) {
      ConcreteClassifier _containingConcreteClassifier = method.getContainingConcreteClassifier();
      return _containingConcreteClassifier;
    }
    
    public EObject getElement2(final InterfaceMethod method, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return operationSignature;
    }
    
    public EObject getElement3(final InterfaceMethod method, final OperationInterface pcmInterface, final OperationSignature operationSignature) {
      return method;
    }
  }
  
  public CreatePCMSignatureRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final InterfaceMethod method) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.java2PcmMethod.CreatePCMSignatureRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.java2PcmMethod.RoutinesFacade(getExecutionState(), this);
    this.method = method;
  }
  
  private InterfaceMethod method;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreatePCMSignatureRoutine with input:");
    getLogger().debug("   InterfaceMethod: " + this.method);
    
    OperationInterface pcmInterface = getCorrespondingElement(
    	userExecution.getCorrepondenceSourcePcmInterface(method), // correspondence source supplier
    	OperationInterface.class,
    	(OperationInterface _element) -> true, // correspondence precondition checker
    	null);
    if (pcmInterface == null) {
    	return;
    }
    registerObjectUnderModification(pcmInterface);
    OperationSignature operationSignature = RepositoryFactoryImpl.eINSTANCE.createOperationSignature();
    notifyObjectCreated(operationSignature);
    userExecution.updateOperationSignatureElement(method, pcmInterface, operationSignature);
    
    // val updatedElement userExecution.getElement1(method, pcmInterface, operationSignature);
    userExecution.update0Element(method, pcmInterface, operationSignature);
    
    addCorrespondenceBetween(userExecution.getElement2(method, pcmInterface, operationSignature), userExecution.getElement3(method, pcmInterface, operationSignature), "");
    
    postprocessElements();
  }
}
