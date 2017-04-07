package tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.repository;

import org.junit.Test;
import org.palladiosimulator.pcm.repository.InnerDeclaration;
import org.palladiosimulator.pcm.repository.PrimitiveDataType;
import org.palladiosimulator.pcm.repository.PrimitiveTypeEnum;
import org.palladiosimulator.pcm.repository.RepositoryFactory;

import tools.vitruv.applications.pcmjava.tests.pojotransformations.pcm2java.Pcm2JavaTransformationTest;
import tools.vitruv.applications.pcmjava.tests.util.Pcm2JavaTestUtils;
import tools.vitruv.framework.util.datatypes.VURI;

public class InnerDeclarationMappingTransformationTest extends Pcm2JavaTransformationTest {

    @Test
    public void testAddInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testRenameInnerDeclaration() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        innerDec.setEntityName(Pcm2JavaTestUtils.INNER_DEC_NAME + Pcm2JavaTestUtils.RENAME);
        super.triggerSynchronization(VURI.getInstance(innerDec.eResource()));

        this.assertInnerDeclaration(innerDec);
    }

    @Test
    public void testChangeInnerDeclarationType() throws Throwable {
        final InnerDeclaration innerDec = this.createAndSyncRepositoryCompositeDataTypeAndInnerDeclaration();

        final PrimitiveDataType newPDT = RepositoryFactory.eINSTANCE.createPrimitiveDataType();
        newPDT.setType(PrimitiveTypeEnum.STRING);
        innerDec.setDatatype_InnerDeclaration(newPDT);
        super.triggerSynchronization(VURI.getInstance(innerDec.eResource()));

        this.assertInnerDeclaration(innerDec);
    }

}
