package tools.vitruv.applications.umljava.tests.java2uml

import static extension tools.vitruv.applications.util.temporary.java.JavaMemberAndParameterUtil.*
import static tools.vitruv.applications.util.temporary.java.JavaTypeUtil.*
import static tools.vitruv.applications.umljava.tests.util.UmlTestUtil.*
import static tools.vitruv.applications.umljava.tests.util.TestUtil.*
import org.junit.jupiter.api.Test
import org.eclipse.uml2.uml.VisibilityKind
import org.eclipse.emf.ecore.util.EcoreUtil
import org.junit.jupiter.api.BeforeEach

import static org.junit.jupiter.api.Assertions.assertNotNull
import static org.junit.jupiter.api.Assertions.assertEquals

/**
 * This class contains test cases for the creation, renaming and deleting of interface methods.
 * Plus, it checks the change of parameters and return types of interface methods.
 * 
 * @author Fei
 */
class JavaToUmlInterfaceMethodTest extends JavaToUmlTransformationTest {
    static val INTERFACE_NAME = "InterfaceName";
    static val TYPE_NAME = "TypeName";
    static val IOPERATION_NAME = "interfaceMethod";
    static val STANDARD_IOPERATION_NAME = "standardInterfaceMethod";
    static val IOPERATION_RENAME = "interfaceMethodRenamed";
    static val PARAMETER_NAME = "parameterName";
    
    static var org.emftext.language.java.classifiers.Interface jInterface;
    static var org.emftext.language.java.classifiers.Class typeClass;
    static var org.emftext.language.java.members.InterfaceMethod jMeth;
    
    @BeforeEach
    def void before() {
        jInterface = createSimpleJavaInterfaceWithCompilationUnit(INTERFACE_NAME)
        typeClass = createSimpleJavaClassWithCompilationUnit(TYPE_NAME)
        jMeth = createJavaInterfaceMethod(IOPERATION_NAME, null, null)
        jInterface.members += jMeth
        saveAndSynchronizeChanges(jInterface)
    }
    
    @Test
    def void testCreateInterfaceMethod() {
        val interfaceMethod = createJavaInterfaceMethod(STANDARD_IOPERATION_NAME, null, null)
        jInterface.members += interfaceMethod
        saveAndSynchronizeChanges(jInterface)
        
        val uOperation = getCorrespondingMethod(interfaceMethod)
        val uInterface = getCorrespondingInterface(jInterface)
        assertUmlOperationTraits(uOperation, STANDARD_IOPERATION_NAME, VisibilityKind.PUBLIC_LITERAL, null, false, true,
            uInterface, null)
        assertInterfaceMethodEquals(uOperation, interfaceMethod)
    }
     
    @Test
    def void testRenameInterfaceMethod() {
        jMeth.name = IOPERATION_RENAME
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        val uInterface = getCorrespondingInterface(jInterface)
        assertEquals(IOPERATION_RENAME, uOperation.name)
        assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME)
        assertInterfaceMethodEquals(uOperation, jMeth)
        
    }
    
    @Test
    def testDeleteInterfaceMethod() {
        assertNotNull(getCorrespondingMethod(jMeth))
        EcoreUtil.delete(jMeth)
        saveAndSynchronizeChanges(jInterface)
        
        val uInterface = getCorrespondingInterface(jInterface)
        assertUmlInterfaceDontHaveOperation(uInterface, IOPERATION_NAME)
    }
    
    @Test
    def testChangeInterfaceMethodReturnType() {
        jMeth.typeReference = createNamespaceReferenceFromClassifier(typeClass)
        saveAndSynchronizeChanges(jMeth)
        
        val uOperation = getCorrespondingMethod(jMeth)
        val utypeClass = getCorrespondingClass(typeClass)
        assertUmlOperationHasReturntype(uOperation, utypeClass)
        assertInterfaceMethodEquals(uOperation, jMeth)
    }
    
    @Test
    def testCreateInterfaceParameter() {
        val jParam = createJavaParameter(PARAMETER_NAME, createNamespaceReferenceFromClassifier(typeClass))
        jMeth.parameters += jParam
        saveAndSynchronizeChanges(jMeth)

        val uOperation = getCorrespondingMethod(jMeth)
        assertUmlOperationHasUniqueParameter(uOperation, PARAMETER_NAME)
        assertInterfaceMethodEquals(uOperation, jMeth)
    }
}