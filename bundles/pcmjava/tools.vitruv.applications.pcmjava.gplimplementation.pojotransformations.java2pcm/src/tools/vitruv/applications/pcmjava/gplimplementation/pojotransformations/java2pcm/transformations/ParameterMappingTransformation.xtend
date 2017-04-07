package tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.java2pcm.transformations

import tools.vitruv.applications.pcmjava.gplimplementation.pojotransformations.util.transformationexecutor.EmptyEObjectMappingTransformation
import org.eclipse.emf.ecore.EAttribute
import org.eclipse.emf.ecore.EObject
import org.emftext.language.java.parameters.Parameter
import org.palladiosimulator.pcm.repository.RepositoryFactory

import static extension tools.vitruv.framework.util.bridges.CollectionBridge.*
import tools.vitruv.applications.pcmjava.util.java2pcm.TypeReferenceCorrespondenceHelper
import tools.vitruv.framework.util.command.ChangePropagationResult
import tools.vitruv.applications.pcmjava.util.java2pcm.Java2PcmUtils

class ParameterMappingTransformation extends EmptyEObjectMappingTransformation {

	override getClassOfMappedEObject() {
		Parameter
	}

	override setCorrespondenceForFeatures() {
		Java2PcmUtils.addName2EntityNameCorrespondence(featureCorrespondenceMap)
	}

	override updateSingleValuedEAttribute(EObject affectedEObject, EAttribute affectedAttribute, Object oldValue,
		Object newValue) {
		val transformationResult = new ChangePropagationResult
		Java2PcmUtils.updateNameAsSingleValuedEAttribute(affectedEObject, affectedAttribute, oldValue, newValue,
			featureCorrespondenceMap, correspondenceModel, transformationResult)
		return transformationResult
	}

	/**
	 * called when a parameter has been created
	 */
	override createEObject(EObject eObject) {
		var jaMoPPParam = eObject as Parameter
		val pcmParameter = RepositoryFactory.eINSTANCE.createParameter
		pcmParameter.dataType__Parameter = TypeReferenceCorrespondenceHelper.
			getCorrespondingPCMDataTypeForTypeReference(jaMoPPParam.typeReference, correspondenceModel,
				userInteracting, null, jaMoPPParam.arrayDimension)
		pcmParameter.entityName = jaMoPPParam.name
		return pcmParameter.toList
	}

	/**
	 * called when a parameter type has been changed
	 */
	 override removeEObject(EObject eObject){
	 	return null 
	 }
}
