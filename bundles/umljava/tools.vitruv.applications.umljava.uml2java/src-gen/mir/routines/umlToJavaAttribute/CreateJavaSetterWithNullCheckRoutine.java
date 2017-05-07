package mir.routines.umlToJavaAttribute;

import java.io.IOException;
import mir.routines.umlToJavaAttribute.RoutinesFacade;
import org.eclipse.xtext.xbase.lib.Extension;
import org.emftext.language.java.members.Field;
import tools.vitruv.applications.umljava.uml2java.UmlToJavaHelper;
import tools.vitruv.applications.umljava.util.java.JavaMemberAndParameterUtil;
import tools.vitruv.extensions.dslsruntime.reactions.AbstractRepairRoutineRealization;
import tools.vitruv.extensions.dslsruntime.reactions.ReactionExecutionState;
import tools.vitruv.extensions.dslsruntime.reactions.structure.CallHierarchyHaving;

@SuppressWarnings("all")
public class CreateJavaSetterWithNullCheckRoutine extends AbstractRepairRoutineRealization {
  private RoutinesFacade actionsFacade;
  
  private CreateJavaSetterWithNullCheckRoutine.ActionUserExecution userExecution;
  
  private static class ActionUserExecution extends AbstractRepairRoutineRealization.UserExecution {
    public ActionUserExecution(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy) {
      super(reactionExecutionState);
    }
    
    public void callRoutine1(final Field jAttribute, @Extension final RoutinesFacade _routinesFacade) {
      boolean _javaSetterForAttributeExists = JavaMemberAndParameterUtil.javaSetterForAttributeExists(jAttribute);
      boolean _not = (!_javaSetterForAttributeExists);
      if (_not) {
        UmlToJavaHelper.createSetterForAttributeWithNullCheck(jAttribute);
      }
    }
  }
  
  public CreateJavaSetterWithNullCheckRoutine(final ReactionExecutionState reactionExecutionState, final CallHierarchyHaving calledBy, final Field jAttribute) {
    super(reactionExecutionState, calledBy);
    this.userExecution = new mir.routines.umlToJavaAttribute.CreateJavaSetterWithNullCheckRoutine.ActionUserExecution(getExecutionState(), this);
    this.actionsFacade = new mir.routines.umlToJavaAttribute.RoutinesFacade(getExecutionState(), this);
    this.jAttribute = jAttribute;
  }
  
  private Field jAttribute;
  
  protected void executeRoutine() throws IOException {
    getLogger().debug("Called routine CreateJavaSetterWithNullCheckRoutine with input:");
    getLogger().debug("   Field: " + this.jAttribute);
    
    userExecution.callRoutine1(jAttribute, actionsFacade);
    
    postprocessElements();
  }
}
